/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sfrey
 */
public class LuceneWriterExample {

  public static void main(String[] args) {
    Database db = new Database(Constants.URI, Constants.USERNAME, Constants.PASSWORD);
    Search search = new Search(Constants.INDEX_PATH, Constants.ANALYZER, Constants.FIELDS);
    
    try (Connection connection = db.connect();
      Indexer indexer = search.createIndexer()) {
      
      indexer.clear();
      
      try (
        PreparedStatement pstmt = connection.prepareStatement(Constants.STATEMENT);
        ResultSet rs = pstmt.executeQuery();
       ) {
        while(rs.next()) {
          int i = 1;
          Integer id = rs.getInt(i++);
          String krs = rs.getString(i++);
          String lan = rs.getString(i++);

          Map<String, String> item = new HashMap<>();
          
          item.put("id", id.toString());
          item.put("krs", krs);
          item.put("lan", lan);
          
          indexer.index(item);
        }
      }
    } catch (SQLException | IOException e) {
      System.err.format("SQL State: %s", e);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
