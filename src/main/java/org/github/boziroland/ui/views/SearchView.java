package org.github.boziroland.ui.views;

import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = SearchView.NAME)
@SpringComponent
public class SearchView extends VerticalLayout implements View {

	public static final String NAME = "search";

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		VerticalLayout results = new VerticalLayout();
		HorizontalLayout searchBar = new HorizontalLayout();
		searchBar.setStyleName("searchBar");
		results.setWidthFull();
		searchBar.setWidthFull();
		TextField searchField = new TextField();
		searchField.setWidthFull();
		Button searchButton = new Button("Keresés");
		searchButton.addShortcutListener(new ShortcutListener("Search", ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				searchButton.click();
			}
		});
		searchButton.addClickListener(event -> {
			String name = searchField.getValue();
			List<User> resultList = userService.findByNameContaining(name);

			results.removeAllComponents();
			for (var r : resultList) {
				Button result = new Button(r.getName());
				result.setStyleName(ValoTheme.BUTTON_LINK);
				result.addClickListener(event1 -> {
					getUI().getNavigator().navigateTo(MainView.NAME + "/" + r.getName());
				});
				results.addComponent(result);
			}
		});
		searchBar.addComponent(searchField);
		searchBar.addComponent(searchButton);
		addComponent(searchBar);
		addComponent(results);
	}

}
