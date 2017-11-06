package com.github.wreulicke.dropwizard.user;

import java.util.List;

import io.dropwizard.hibernate.UnitOfWork;

public class UserService {
  private final UserDAO userDAO;

  public UserService(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @UnitOfWork
  public List<User> findAll() {
    return userDAO.findAll();
  }

  @UnitOfWork
  public User find(String name) {
    return userDAO.find(name);
  }

  @UnitOfWork
  public User delete(String name) {
    return userDAO.delete(name);
  }

  @UnitOfWork
  public User create(User user) {
    return userDAO.create(user);
  }
}
