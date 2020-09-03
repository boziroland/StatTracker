package org.github.boziroland.ui.views;

import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;


public final class FormUtils {

	public static void createGameNameField(AbstractOrderedLayout layout, TextField nameField, ComboBox<String> regionBox) {
		nameField.addValueChangeListener(event -> {
			if (!nameField.isEmpty() && !regionBox.isEmpty())
				regionBox.setStyleName("red");
			else
				regionBox.removeStyleName("red");
		});
		regionBox.addValueChangeListener(event -> {
			if (!nameField.isEmpty()) {
				if (regionBox.isEmpty())
					regionBox.setStyleName("red");
				else
					regionBox.removeStyleName("red");
			}
		});
		layout.addComponent(nameField);
		layout.addComponent(regionBox);
	}

	public static void showMessage(TextArea messageField, String style, String message){
		messageField.setStyleName(style);//addSN ha lesz idő
		messageField.setValue(message);
	}

}
