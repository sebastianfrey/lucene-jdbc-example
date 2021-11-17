/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author sfrey
 */
public interface Indexer extends AutoCloseable {

  public void clear() throws IOException;

  public void commit() throws IOException;

  public void index(Map<String, String> item) throws IOException;

  public void index(List<Map<String, String>> items) throws IOException;
}
