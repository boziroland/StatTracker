package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.SpecificAPIDataSource1;
import org.github.boziroland.services.IAPIData1Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class APIData1Service implements IAPIData1Service {

    IAPIData1DAO dao;

    public APIData1Service(IAPIData1DAO dao) {
        this.dao = dao;
    }

    @Override
    public void createOrUpdate(String token, String username, String userID) {
        dao.createOrUpdate(new SpecificAPIDataSource1(token, username, userID));
    }

    @Override
    public List<SpecificAPIDataSource1> findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public Optional<SpecificAPIDataSource1> findByID(String id) {
        return dao.findByID(id);
    }

    @Override
    public List<SpecificAPIDataSource1> list() {
        return dao.list();
    }

    @Override
    public void deleteByName(String name) {
        dao.deleteByName(name);
    }

    @Override
    public void deleteByID(String id) {
        dao.deleteByID(id);
    }

    @Override
    public void requestToken() {

    }

    @Override
    public Map<String, String> requestInformation(String accountId) {
        return null;
    }
}
