package com.github.wreulicke.dropwizard;

import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;

import org.elasticsearch.metrics.ElasticsearchReporter;

import com.codahale.metrics.Slf4jReporter;
import com.github.wreulicke.dropwizard.user.User;
import com.github.wreulicke.dropwizard.user.UserDAO;
import com.github.wreulicke.dropwizard.user.UserService;
import com.netflix.spectator.gc.GcLogger;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MyApplication extends Application<MyConfiguration> {

  private final HibernateBundle<MyConfiguration> hibernateBundle = new HibernateBundle<MyConfiguration>(User.class) {
    @Override
    public PooledDataSourceFactory getDataSourceFactory(MyConfiguration configuration) {
      return configuration.getDataSourceFactory();
    }
  };

  @Override
  public void initialize(Bootstrap<MyConfiguration> bootstrap) {
    bootstrap.addBundle(new MigrationsBundle<MyConfiguration>() {
      @Override
      public PooledDataSourceFactory getDataSourceFactory(MyConfiguration configuration) {
        return configuration.getDataSourceFactory();
      }
    });

    bootstrap.setConfigurationSourceProvider(
      new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
        new EnvironmentVariableSubstitutor(false)));

    bootstrap.addBundle(hibernateBundle);
  }

  @Override
  public void run(MyConfiguration configuration, Environment environment) throws Exception {
    GcLogger gc = new GcLogger();
    gc.start(null);

    environment.healthChecks()
      .register("simple", new SimpleHealthCheck());

    UserDAO dao = new UserDAO(hibernateBundle.getSessionFactory());
    UserService userService = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(UserService.class, UserDAO.class, dao);
    environment.jersey()
      .register(new UserResource(userService));
    environment.jersey()
      .register(new HeavyResource());

    Slf4jReporter.forRegistry(environment.metrics())
      .outputTo(LoggerFactory.getLogger(MyApplication.class))
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .convertRatesTo(TimeUnit.SECONDS)
      .build()
      .start(1, TimeUnit.SECONDS);

    ElasticsearchReporter.forRegistry(environment.metrics())
      .filter((name, metric) -> name.startsWith("jvm"))
      .hosts("localhost:9200")
      .index("metrics")
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .convertRatesTo(TimeUnit.SECONDS)
      .build()
      .start(1, TimeUnit.SECONDS);

    ElasticsearchReporter.forRegistry(environment.metrics())
      .filter((name, metric) -> name.startsWith("io.dropwizard.jetty.MutableServletContextHandler") && name.endsWith("requests"))
      .hosts("localhost:9200")
      .index("requests")
      .convertDurationsTo(TimeUnit.MILLISECONDS)
      .convertRatesTo(TimeUnit.SECONDS)
      .build()
      .start(1, TimeUnit.SECONDS);

  }

  public static void main(String[] args) throws Exception {
    new MyApplication().run(args);
  }

}
