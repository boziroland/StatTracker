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
import org.github.boziroland.Constants;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.impl.CommentService;
import org.github.boziroland.services.impl.MilestoneService;
import org.github.boziroland.services.impl.UserService;
import org.github.boziroland.ui.MainUI;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

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
			if(user.getMilestoneNameUserPointMap().containsKey(m.getName()))
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

		TextArea messageArea = new TextArea();
		messageArea.setPlaceholder("Üzenet helye");
		messageWritingLayout.addComponent(messageArea);

		Button sendMessageButton = new Button("Elküld");
		messageWritingLayout.addComponent(sendMessageButton);
		messageWritingLayout.setComponentAlignment(sendMessageButton, Alignment.MIDDLE_CENTER);

		sendMessageButton.addClickListener(event -> {
			String msg = messageArea.getValue();

			sendComment(msg);
		});

		return commentSectionlayout;
	}

	private void sendComment(String message) {
		User loggedInUser = ((MainUI) getUI()).getUser();
		sendComment(loggedInUser, message);
	}

	private void sendComment(User sender, String message) {
		TextArea listenerSender = new TextArea();
		listenerSender.setEnabled(false);
		listenerSender.setValue(sender.getName());
		listenerSender.setStyleName("commentSenderName");

		TextArea listenerMessage = new TextArea();
		listenerMessage.setEnabled(false);
		listenerMessage.setValue(message);
		listenerMessage.setStyleName("commentColumn");

		commentService.sendComment(sender, user, message);

		commentSectionlayout.addComponent(new HorizontalLayout(listenerSender, listenerMessage));
	}

	private void setLeagueTabInformation() {
		final GridLayout gridLayout = new GridLayout();
		final FormLayout formLayout = new FormLayout();
		setupLayouts(gridLayout, formLayout);

		formLayout.addComponent(createDataTextField(gridLayout, "Profile level:", user.hasLeagueData() ? convert(user.getLeagueData().getPlayer().getSummonerLevel()) : "-", "images/placeholder0.jpg"));
		formLayout.addComponent(createDataTextField(gridLayout, "Played matches:", user.hasLeagueData() ? convert(user.getLeagueData().getLastTenMatches().size()) : "-", "images/placeholder1.jpg"));

		tabSheet.addTab(gridLayout, "League of Legends");
	}

	private void setOWTabInformation() {
		final GridLayout gridLayout = new GridLayout();
		final FormLayout formLayout = new FormLayout();
		setupLayouts(gridLayout, formLayout);

		formLayout.addComponent(createDataTextField(gridLayout, "Profile level:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getLevel()) : "-", "images/placeholder0.jpg"));
		formLayout.addComponent(createDataTextField(gridLayout, "Played competitive matches:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getGamesCompetitivePlayed()) : "-", "images/placeholder1.jpg"));
		formLayout.addComponent(createDataTextField(gridLayout, "Won competitive matches:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getGamesCompetitiveWon()) : "-", "images/placeholder1.jpg"));
		formLayout.addComponent(createDataTextField(gridLayout, "Won casual matches:", user.hasOverwatchData() ? convert(user.getOverwatchData().getPlayer().getGamesQuickplayWon()) : "-", "images/placeholder1.jpg"));
		formLayout.addComponent(createDataTextField(gridLayout, "Competitive playtime:", user.hasOverwatchData() ? user.getOverwatchData().getPlayer().getPlaytimeCompetitive().toString() : "-", "images/placeholder1.jpg"));
		formLayout.addComponent(createDataTextField(gridLayout, "Quickplay playtime:", user.hasOverwatchData() ? user.getOverwatchData().getPlayer().getPlaytimeQuickplay().toString() : "-", "images/placeholder1.jpg"));

		tabSheet.addTab(gridLayout, "Overwatch");
	}

	private Component createDataTextField(GridLayout parent, String text, String data, String imageUrl) {
		final HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setStyleName("setLiterallyEveryPaddingToZero");
		final TextField textField = new TextField(text, data);
		textField.setStyleName("dataColumn");
		textField.setEnabled(false);
		horizontalLayout.addComponent(textField);

		final Image image = new Image();
		image.setSource(new ThemeResource(imageUrl));

		final Button showGraphButton = new Button("Show graph", (event) -> {
			parent.removeComponent(1, 0);
			parent.addComponent(image, 1, 0);
			parent.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		});

		showGraphButton.setEnabled(!data.equals("-"));
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
		if (number == null)
			return "-";
		return String.valueOf(number);
	}

	private String convert(MutableInt number) {
		if (number == null)
			return "-";
		return number.toString();
	}

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		String name = event.getParameters();
		if (event.getParameters() != null) {
			var usr = userService.findByName(name);
			usr.ifPresent(value -> user = value);
			setLeagueTabInformation();
			setOWTabInformation();

			commentSectionlayout.removeAllComponents();
			List<Comment> commentsOnProfile = commentService.findByReceiver(user);

			for (var comment : commentsOnProfile) {
				User sender = userService.findById(comment.getSenderId()).get();
				sendComment(sender, comment.getMessage());
			}

		}
		addComponent(new Label(Objects.requireNonNull(event.getParameters()).isEmpty() ? "placeholder" : name), 0);
		addComponent(createMilestoneSection());
		addComponent(createCommentSection());
	}
}
