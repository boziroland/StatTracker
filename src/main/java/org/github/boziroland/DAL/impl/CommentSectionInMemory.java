package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.ICommentSectionDAO;
import org.github.boziroland.entities.CommentSection;
import org.github.boziroland.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CommentSectionInMemory implements ICommentSectionDAO {

    Map<String, CommentSection> idCommentSectionMap = new HashMap<>();

    @Override
    public void createOrUpdate(CommentSection commentSection) {
        idCommentSectionMap.put(commentSection.getId(), commentSection);
    }

    @Override
    public Optional<CommentSection> findById(String id) {
        return Optional.ofNullable(idCommentSectionMap.get(id));
    }

    @Override
    public Optional<CommentSection> findByUser(User user) {

        for(var cs : idCommentSectionMap.entrySet()){
            if(cs.getValue().getUser().getID() == user.getID())
                return Optional.ofNullable(cs.getValue());
        }

        return Optional.empty();
    }
}
