/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author sfrey
 */
public class Search {
    
  private final Path path;
  private final Analyzer analyzer;
  private final Map<String, FieldType> fields;
  
  public Search(Path path, Analyzer analyzer) {
    this.path = path;
    this.analyzer = analyzer;
    this.fields = Map.of();
  }
  
  public Search(Path path, Analyzer analyzer, Map<String, FieldType> fields) {
    this.path = path;
    this.analyzer = analyzer;
    this.fields = fields;
  }
  
  
  public Indexer createIndexer() throws IOException {    
    return LuceneIndexer.builder()
      .withWriter(createWriter(path, analyzer))
      .withFields(fields)
      .build();
  }
  
  
  private IndexWriter createWriter(Path path, Analyzer analyzer) throws IOException {
    Directory directory = FSDirectory.open(path);
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    return new IndexWriter(directory, config);
  }
  
  
  public IndexWriter writer() throws IOException {
    return this.createWriter(path, analyzer);
  }
  
  
  private IndexSearcher createSearcher(Path path) throws IOException {
    Directory dir = FSDirectory.open(path);
    IndexReader reader = DirectoryReader.open(dir);
    return new IndexSearcher(reader);
  }
  
  public List<Document> query(String queryString) throws IOException, ParseException {
    IndexSearcher searcher = createSearcher(path);

    QueryParser parser = new QueryParser("krs", analyzer);
    
    Query query = parser.parse(queryString);
    
    TopDocs result = searcher.search(query, searcher.getIndexReader().numDocs());
    
    List<Document> documents = new ArrayList<>();
    
    for (ScoreDoc scoreDoc : result.scoreDocs) {
      documents.add(searcher.doc(scoreDoc.doc));
    }
    
    return documents;
  }
}
