/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.de.GermanAnalyzer;

/**
 *
 * @author sfrey
 */
public class Constants {
  public static final String URI = "jdbc:postgresql://localhost:5432/postgres";
  public static final String USERNAME = "postgres";
  public static final String PASSWORD = "postgres";
  public static final String STATEMENT = "SELECT id, krs, lan FROM public.kreise";
  public static final Map<String, FieldType> FIELDS = new HashMap<>();
  static {
    FIELDS.put("id", FieldType.STRING);
    FIELDS.put("krs", FieldType.TEXT);
    FIELDS.put("lan", FieldType.TEXT);
  }

  public static final Analyzer ANALYZER = new GermanAnalyzer();
  public static final Path INDEX_PATH = Paths.get("/tmp/idx/kreise");
}
