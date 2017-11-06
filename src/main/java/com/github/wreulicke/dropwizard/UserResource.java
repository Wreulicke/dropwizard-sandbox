package com.github.wreulicke.dropwizard;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.github.wreulicke.dropwizard.user.User;
import com.github.wreulicke.dropwizard.user.UserService;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
public class UserResource {

  private UserService userService;

  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @POST
  public User create(User user) {
    return userService.create(user);
  }

  @GET
  public List<User> list() {
    return userService.findAll();
  }

  @Path("/{username}")
  @DELETE
  public User delete(@PathParam("username") String username) {
    return userService.delete(username);
  }

  @Path("/{username}")
  @GET
  public User get(@PathParam("username") String username) {
    return userService.find(username);
  }

}
