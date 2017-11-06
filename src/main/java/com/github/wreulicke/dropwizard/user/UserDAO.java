package com.github.wreulicke.dropwizard.user;

import java.util.List;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;

public class UserDAO extends AbstractDAO<User> {

  public UserDAO(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<User> findAll() {
    return list(query("SELECT u FROM User u"));
  }

  public User find(String name) {
    return get(name);
  }

  public User delete(String name) {
    User user = find(name);
    if (user != null) {
      currentSession().delete(user);
      return user;
    }
    return null;
  }

  public User create(User user) {
    return persist(user);
  }
}
