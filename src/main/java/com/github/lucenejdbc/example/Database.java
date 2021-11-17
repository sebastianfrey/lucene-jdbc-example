/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sfrey
 */
public class Database {
  
  private final String uri;
  private final String username;
  private final String password;
  
  public Database(String uri, String username, String password) {
    this.uri = uri;
    this.username = username;
    this.password = password;
  }
  
  public Connection connect() throws SQLException {
    Connection connection = DriverManager.getConnection(uri, username, password);
    
    if (connection == null) {
      throw new SQLException("Cannot connect to database.");
    }
    
    return connection;
  }
}
