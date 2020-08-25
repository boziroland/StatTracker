package org.github.boziroland.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.NAME)
@SpringComponent
@Scope(scopeName = "vaadin-ui")
public class DefaultView extends VerticalLayout implements View {

	public static final String NAME = "";

	@PostConstruct
	void init() {

		Label label = new Label("ÜDV AZ OLDALON OwO");

		addComponent(label);

	}
}
