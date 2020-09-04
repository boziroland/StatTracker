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
import java.util.List;

@SpringView(name = SearchView.NAME)
@SpringComponent
public class SearchView extends VerticalLayout implements View {

	public static final String NAME = "search";

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		Label searchLabel = new Label("Felhasználó keresés");
		searchLabel.setStyleName("searchBar");
		addComponent(searchLabel);

		VerticalLayout results = new VerticalLayout();
		HorizontalLayout searchBar = new HorizontalLayout();
		searchBar.setStyleName("searchBar");
		results.setWidthFull();
		searchBar.setWidthFull();
		TextField searchField = new TextField();
		searchField.setPlaceholder("Felhasználó neve pl. Bonifác");
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
			results.removeAllComponents();
			if (name.equals("")) {
				results.addComponent(new Label("Nincs találat."));
			} else {
				List<User> resultList = userService.findByNameContaining(name);


				if (resultList.size() == 0) {
					results.addComponent(new Label("Nincs találat."));
				} else {
					for (var user : resultList) {
						Button result = new Button(user.getName());
						if (!user.getProfilePublic()) {
							result.setEnabled(false);
							result.setStyleName(ValoTheme.BUTTON_BORDERLESS);
						} else {
							result.setStyleName(ValoTheme.BUTTON_LINK);
						}
						result.addClickListener(event1 -> {
							getUI().getNavigator().navigateTo(MainView.NAME + "/" + user.getName());
						});
						results.addComponent(result);
					}
				}
			}
		});

		searchBar.addComponent(searchField);
		searchBar.addComponent(searchButton);
		addComponent(searchBar);
		addComponent(results);
	}

}
