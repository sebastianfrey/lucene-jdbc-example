/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.util.List;
import org.apache.lucene.document.Document;


/**
 *
 * @author sfrey
 */
public class LuceneReaderExample {
  public static void main(String[] args) throws Exception {
    Search search = new Search(Constants.INDEX_PATH, Constants.ANALYZER);
    
    List<Document> documents = search.query("krs:weiheim~");
    
    for (Document document : documents) {
      System.out.println(document.getField("krs").stringValue());
    }
  }
  
}
