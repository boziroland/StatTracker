package org.github.boziroland.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;

@SpringView(name = MainView.NAME)
@SpringComponent
public class MainView extends VerticalLayout implements View {

	public static final String NAME = "main";

	private final TabSheet tabSheet = new TabSheet();

	@PostConstruct
	void init() {

		tabSheet.setSizeFull();

		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		setLeagueTabInformation();
		setOWTabInformation();

		addComponent(tabSheet);
		addComponent(createCommentSection());
	}

	private VerticalLayout createCommentSection() {
		HorizontalLayout messageWritingLayout = new HorizontalLayout();
		VerticalLayout commentSectionlayout = new VerticalLayout(messageWritingLayout);
		commentSectionlayout.setSizeUndefined();

		TextArea messageArea = new TextArea();
		messageArea.setValue("Üzenet helye");
		messageWritingLayout.addComponent(messageArea);

		Button sendMessageButton = new Button("Elküld");
		messageWritingLayout.addComponent(sendMessageButton);
		messageWritingLayout.setComponentAlignment(sendMessageButton, Alignment.MIDDLE_CENTER);
		sendMessageButton.addClickListener(event -> {
			//TODO felhasználó bekötése
			String sender = "Deserion";
			String msg = messageArea.getValue();

			TextArea listenerSender = new TextArea();
			listenerSender.setEnabled(false);
			listenerSender.setValue(sender);
			listenerSender.setStyleName("commentSenderName");

			TextArea listenerMessage = new TextArea();
			listenerMessage.setEnabled(false);
			listenerMessage.setValue(msg);
			listenerMessage.setStyleName("commentColumn");

			commentSectionlayout.addComponent(new HorizontalLayout(listenerSender, listenerMessage));
		});

		//TODO fake kommenteket kiszedni
		for (int i = 0; i < 3; i++) {
			HorizontalLayout commentLayout = new HorizontalLayout();
			TextArea sender = new TextArea();
			sender.setEnabled(false);
			sender.setValue("Felhasználó #" + i);
			sender.setStyleName("commentColumn");
			sender.setStyleName("commentSenderName");
			TextArea comment = new TextArea();
			comment.setEnabled(false);
			comment.setValue("Felhasználó #" + i + " kommentje");
			comment.setStyleName("commentColumn");
			commentLayout.addComponent(sender);
			commentLayout.addComponent(comment);

			commentSectionlayout.addComponent(commentLayout);
		}
		return commentSectionlayout;
	}

	private void setLeagueTabInformation() {
		final GridLayout gridLayout = new GridLayout();
		final FormLayout formLayout = new FormLayout();
		setupLayouts(gridLayout, formLayout);

		formLayout.addComponent(createDataTextField(gridLayout, "Profile level:", "69", "images/placeholder0.jpg", "tabSheetDatapaddingFirstElement"));
		formLayout.addComponent(createDataTextField(gridLayout, "Played matches:", "420", "images/placeholder1.jpg", "tabSheetDatapaddingMiddleElement"));

		tabSheet.addTab(gridLayout, "League of Legends");
	}

	private void setOWTabInformation() {
		final GridLayout gridLayout = new GridLayout();
		final FormLayout formLayout = new FormLayout();
		setupLayouts(gridLayout, formLayout);

		formLayout.addComponent(createDataTextField(gridLayout, "Profile level:", "69", "images/placeholder0.jpg", "tabSheetDatapaddingFirstElement"));
		formLayout.addComponent(createDataTextField(gridLayout, "Played matches:", "420", "images/placeholder1.jpg", "tabSheetDatapaddingMiddleElement"));

		tabSheet.addTab(gridLayout, "Overwatch");
	}

	private Component createDataTextField(GridLayout parent, String text, String data, String imageUrl, String cssStyle) {
		final HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setStyleName("setLiterallyEveryPaddingToZero");
		final TextField textField = new TextField(text, data);
		textField.setStyleName("dataColumn");
		textField.setEnabled(false);
		horizontalLayout.addComponent(textField);

		final Image image = new Image();
		image.setSource(new ThemeResource(imageUrl));

		horizontalLayout.addComponent(new Button("Show graph", (event) -> {
			parent.removeComponent(1, 0);
			parent.addComponent(image, 1, 0);
			parent.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
		}));

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

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {

	}
}
