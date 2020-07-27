package org.github.boziroland.services.impl;

import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.SpecificAPIData1;
import org.github.boziroland.repositories.IAPIData1Repository;
import org.github.boziroland.services.IAPIData1Service;

import java.util.List;
import java.util.Optional;

public class APIData1Service implements IAPIData1Service {

	IAPIData1Repository data1Repository;

	public APIData1Service() {
	}

	@Override
	public void createOrUpdate(String token, String username, int userID) {
		data1Repository.save(new SpecificAPIData1(token));
	}

	@Override
	public Optional<SpecificAPIData1> findById(int id) {
		return data1Repository.findById(id);
	}

	@Override
	public List<SpecificAPIData1> list() {
		return data1Repository.findAll();
	}

	@Override
	public void deleteById(int id) {
		data1Repository.deleteById(id);
	}

	@Override
	public void requestInformation(String accountId, GeneralAPIData location) {
	}
}
