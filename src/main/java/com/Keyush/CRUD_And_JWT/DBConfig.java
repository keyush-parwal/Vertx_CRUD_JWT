package com.Keyush.CRUD_And_JWT;


import io.ebean.DB;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.ServerConfig;
import io.ebean.datasource.DataSourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class DBConfig {

  private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);

  public static Database setup() {
    DatabaseConfig config = new DatabaseConfig();
    Properties properties = new Properties();
    properties.put("datasource.db.username", "Your_Username");
    properties.put("datasource.db.password", "Your_Password");
    properties.put("datasource.db.databaseUrl", "jdbc:mysql://localhost:3306/StudentCrud");
    properties.put("datasource.db.databaseDriver", "com.mysql.cj.jdbc.Driver");

    config.setDefaultServer(true);
    config.setDdlCreateOnly(true);
    config.setDdlGenerate(true);
    config.setDdlRun(true);
    config.loadFromProperties(properties);
    config.addClass(Student.class);
    return DatabaseFactory.create(config);
  }

}

