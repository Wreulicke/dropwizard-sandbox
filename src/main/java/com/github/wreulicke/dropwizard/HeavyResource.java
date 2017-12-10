package com.github.wreulicke.dropwizard;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/heavy")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Timed
public class HeavyResource {

  private final ConcurrentHashMap<String, HeavyModel> repository = new ConcurrentHashMap<>();


  @POST
  public HeavyModel create(HeavyModel model) {
    repository.putIfAbsent(model.getId(), model);
    return model;
  }

  @GET
  public Collection<HeavyModel> list() {
    return repository.values();
  }

  @Path("/{modelid}")
  @GET
  public HeavyModel get(@PathParam("modelid") String modelid) {
    return repository.get(modelid);
  }

  private static class HeavyModel {

    String id = UUID.randomUUID()
      .toString();

    /**
     * @return the id
     */
    public String getId() {
      return id;
    }

    String field1 = UUID.randomUUID()
      .toString();

    String field2 = UUID.randomUUID()
      .toString();

    String field3 = UUID.randomUUID()
      .toString();

    String field4 = UUID.randomUUID()
      .toString();

    String field5 = UUID.randomUUID()
      .toString();

    String field6 = UUID.randomUUID()
      .toString();

    String field7 = UUID.randomUUID()
      .toString();

    /**
     * @return the field1
     */
    public String getField1() {
      return field1;
    }

    /**
     * @return the field2
     */
    public String getField2() {
      return field2;
    }

    /**
     * @return the field3
     */
    public String getField3() {
      return field3;
    }

    /**
     * @return the field4
     */
    public String getField4() {
      return field4;
    }

    /**
     * @return the field5
     */
    public String getField5() {
      return field5;
    }

    /**
     * @return the field6
     */
    public String getField6() {
      return field6;
    }

    /**
     * @return the field7
     */
    public String getField7() {
      return field7;
    }
  }


}
