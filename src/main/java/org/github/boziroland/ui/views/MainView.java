package org.github.boziroland.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.impl.CommentService;
import org.github.boziroland.services.impl.MilestoneService;
import org.github.boziroland.services.impl.UserService;
import org.github.boziroland.ui.ChartCreator;
import org.github.boziroland.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@SpringView(name = MainView.NAME)
@SpringComponent
public class MainView extends VerticalLayout implements View {

	public static final String NAME = "main";

	@Autowired
	private UserService userService;

	@Autowired
	private MilestoneService milestoneService;

	@Autowired
	private CommentService commentService;

	private final TabSheet tabSheet = new TabSheet();

	private User user;

	private final VerticalLayout commentSectionlayout = new VerticalLayout();

	TextArea commentArea = new TextArea();
	Button sendMessageButton = new Button("Elküld");

	@PostConstruct
	void init() {

		addAttachListener(this::onAttach);

		tabSheet.setSizeFull();

		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		addComponent(tabSheet);
	}

	private void onAttach(AttachEvent attachEvent) {
		if (user == null)
			user = ((MainUI) getUI()).getUser();
	}

	private VerticalLayout createMilestoneSection() {
		VerticalLayout ret = new VerticalLayout();
		ret.setStyleName("setLiterallyEveryPaddingToZero");
		var milestones = milestoneService.getMilestonesAsList();
		for (var m : milestones) {
			HorizontalLayout milestoneLayout = new HorizontalLayout();
			milestoneLayout.setStyleName("tabSheetData");

			Image image = new Image();
			if (user.getMilestoneNameUserPointMap().containsKey(m.getName()))
				image.setSource(new ThemeResource("images/achievement_notdone.png"));
			else
				image.setSource(new ThemeResource("images/achievement_done.png"));
			image.setHeight("100%");
			image.setWidth("100%");

			milestoneLayout.addComponent(image);

			milestoneLayout.addComponent(new Label(m.getName() + "<br><i>" + m.getDescription() + "</i>", ContentMode.HTML));
			ret.addComponent(milestoneLayout);
		}
		Label line = new Label("<hr>", ContentMode.HTML);
		line.setWidthFull();
		ret.addComponent(line);
		return ret;
	}

	private VerticalLayout createCommentSection() {
		HorizontalLayout messageWritingLayout = new HorizontalLayout();
		commentSectionlayout.addComponent(messageWritingLayout);
		commentSectionlayout.setStyleName("commentSectionPadding");
		commentSectionlayout.setSizeUndefined();

		commentArea.setPlaceholder("Üzenet helye");
		messageWritingLayout.addComponent(commentArea);

		messageWritingLayout.addComponent(sendMessageButton);
		messageWritingLayout.setComponentAlignment(sendMessageButton, Alignment.MIDDLE_CENTER);

		sendMessageButton.addClickListener(event -> {
			String msg = commentArea.getValue();

			addComment(msg);
		});

		return commentSectionlayout;
	}

	private void addComment(String message) {
		User sender = ((MainUI) getUI()).getUser();
		commentService.sendComment(sender, user, message);
		addComment(sender, message, LocalDateTime.now());
	}

	private void addComment(User sender, String message, LocalDateTime time) {
		TextArea listenerSender = new TextArea();
		listenerSender.setEnabled(false);
		listenerSender.setValue(sender.getName() + "\n" + time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		listenerSender.setStyleName("commentSenderName");

		TextArea listenerMessage = new TextArea();
		listenerMessage.setEnabled(false);
		listenerMessage.setValue(message);
		listenerMessage.setStyleName("commentColumn");

		commentSectionlayout.addComponent(new HorizontalLayout(listenerSender, listenerMessage));
	}

	private void setLeagueTabInformation() {
		final GridLayout gridLayout = new GridLayout();
		final FormLayout formLayout = new FormLayout();
		setupLayouts(gridLayout, formLayout);

		formLayout.addComponent(createDataTextField(gridLayout, "Profile level:", user.hasLeagueData() ? convert(user.getLeagueData().getPlayer().getSummonerLevel()) : "-", user::getLeagueLevelList));
		formLayout.addComponent(createDataTextField(gridLayout, "Played matches:", user.hasLeagueData() ? convert(user.getLeagueData().getLastTenMatches().size()) : "-", user::getLeaguePlayedMatchesList));

		tabSheet.addTab(gridLayout, "League of Legends");
	}

	private void setOWTabInformation() {
		final GridLayout gridLayout = new GridLayout();
		final FormLayout formLayout = new FormLayout();
		setupLayouts(gridLayout, formLayout);

		formLayout.addComponent(createDataTextField(gridLayout, "Profile level:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getLevel()) : "-", user::getOverwatchLevelList));
		formLayout.addComponent(createDataTextField(gridLayout, "Played competitive matches:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getGamesCompetitivePlayed()) : "-", user::getOverwatchCompetitiveMatchesList));
		formLayout.addComponent(createDataTextField(gridLayout, "Won competitive matches:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getGamesCompetitiveWon()) : "-", user::getOverwatchCompetitiveMatchesWonList));
		formLayout.addComponent(createDataTextField(gridLayout, "Won casual matches:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getGamesQuickplayWon()) : "-", user::getOverwatchQuickplayMatchesWonList));
		formLayout.addComponent(createDataTextField(gridLayout, "Competitive playtime:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getPlaytimeCompetitive()) : "-", user::getOverwatchCompetitivePlaytimeList));
		formLayout.addComponent(createDataTextField(gridLayout, "Quickplay playtime:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getPlaytimeQuickplay()) : "-", user::getOverwatchQuickplayPlaytimeList));

		tabSheet.addTab(gridLayout, "Overwatch");
	}

	private Component createDataTextField(GridLayout parent, String text, String fieldData, Supplier data) {
		final HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setStyleName("setLiterallyEveryPaddingToZero");
		final TextField textField = new TextField(text, fieldData);
		textField.setStyleName("dataColumn");
		textField.setEnabled(false);
		horizontalLayout.addComponent(textField);

		final Button showGraphButton = new Button("Show graph", (event) -> {

			Image image = new Image();
			image.setSource(new ThemeResource("images/loading.svg"));
			parent.removeComponent(1, 0);
			parent.addComponent(image, 1, 0);
			parent.setComponentAlignment(image, Alignment.MIDDLE_CENTER);

			var chart = ChartCreator.createChart(text, (List<Long>) data.get());

			parent.removeComponent(1, 0);
			parent.addComponent(chart, 1, 0);
			parent.setComponentAlignment(chart, Alignment.MIDDLE_CENTER);
		});

		showGraphButton.setEnabled(!fieldData.equals("-"));
		horizontalLayout.addComponent(showGraphButton);

		return horizontalLayout;
	}

	private void setupLayouts(GridLayout gridLayout, FormLayout formLayout) {
		gridLayout.setColumns(2);
		gridLayout.setRows(1);
		gridLayout.setSizeFull();
		formLayout.setSizeUndefined();
		formLayout.setStyleName("tabSheetData");
		gridLayout.addComponent(formLayout);
	}

	private String convert(Integer number) {
		if (number == null || number < 0)
			return "-";
		return String.valueOf(number);
	}

	private String convert(MutableInt number) {
		if (number == null || number.getValue() < 0)
			return "-";
		return number.toString();
	}

	private String convert(Duration time) {
		if (time == null)
			return "-";
		return DurationFormatUtils.formatDuration(time.toMillis(), "HH:mm::ss", true);
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		String name = event.getParameters();
		if (user == null)
			sendMessageButton.setEnabled(false);

		if (!event.getParameters().equals("")) {
			var usr = userService.findByName(name);
			if (usr.isEmpty()) {
				getUI().getNavigator().navigateTo(LoginView.NAME);
			} else {
				user = usr.get();

				setLeagueTabInformation();
				setOWTabInformation();

				commentSectionlayout.removeAllComponents();

				addComponent(new Label(Objects.requireNonNull(event.getParameters()).isEmpty() ? "placeholder" : name), 0);
				addComponent(createMilestoneSection());
				addComponent(createCommentSection());

				List<Comment> commentsOnProfile = commentService.findByReceiver(user);

				for(int i = commentsOnProfile.size() - 1; i >= 0; i--){
					var comment = commentsOnProfile.get(i);
					User sender = userService.findById(comment.getSenderId()).get();
					addComment(sender, comment.getMessage(), comment.getTime());
				}
			}
		} else if (((MainUI) getUI()).getUser() != null) {
			getUI().getNavigator().navigateTo(MainView.NAME + "/" + ((MainUI) getUI()).getUser().getName());
		} else {
			getUI().getNavigator().navigateTo(LoginView.NAME);
		}
	}
}
