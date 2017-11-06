package com.github.wreulicke.dropwizard;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class MyConfiguration extends Configuration {

  @Valid
  @NotNull
  @JsonProperty("database")
  private DataSourceFactory dataSourceFactory = new DataSourceFactory();

  /**
   * @return the dataSourceFactory
   */
  public DataSourceFactory getDataSourceFactory() {
    return dataSourceFactory;
  }

  /**
   * @param dataSourceFactory the dataSourceFactory to set
   */
  public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }



}
