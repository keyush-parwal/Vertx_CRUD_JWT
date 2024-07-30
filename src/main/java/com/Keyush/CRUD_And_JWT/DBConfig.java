package com.Keyush.CRUD_And_JWT;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import io.ebean.config.ServerConfig;
import io.ebean.datasource.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConfig {

  private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

  public static void setup() {
    // Create and configure the ServerConfig
    ServerConfig config = new ServerConfig();
    config.setName("default");
    config.setDefaultServer(true);

    // Configure data source
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/StudentCrud");
    dataSourceConfig.setUsername("Your_UserName");
    dataSourceConfig.setPassword("Your_Password");
    dataSourceConfig.setDriver("com.mysql.cj.jdbc.Driver");

    config.setDataSourceConfig(dataSourceConfig);

    // Set Ebean properties
    config.setDdlGenerate(true);
    config.setDdlRun(true);
    config.addPackage("com.Keyush.CRUD_And_JWT"); // Adjust to your package

    // Add model classes
    config.addClass(Student.class);

    // Create and initialize Ebean server
    EbeanServer ebeanServer = EbeanServerFactory.create(config);

    // Optionally, you can set the created server as the default
    Ebean.getServer(config.getName()); // This will make it the default server
  }
}


