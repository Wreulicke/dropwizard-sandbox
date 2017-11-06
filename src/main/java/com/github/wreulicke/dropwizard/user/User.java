package com.github.wreulicke.dropwizard.user;

import java.beans.ConstructorProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  public String name;

  User() {}

  @ConstructorProperties("name")
  public User(String name) {
    this.name = name;
  }
}
