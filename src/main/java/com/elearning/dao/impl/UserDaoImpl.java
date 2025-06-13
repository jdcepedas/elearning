package com.elearning.dao.impl;

import com.elearning.dao.UserDao;
import com.elearning.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return getEntityManager().createQuery("SELECT u FROM User u WHERE u.username =:username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }
}
