package org.github.boziroland.services;

import org.github.boziroland.entities.User;

public interface IScheduledInformationRetrieverService {
	void retrieve(User user, IAPIService service);
}
