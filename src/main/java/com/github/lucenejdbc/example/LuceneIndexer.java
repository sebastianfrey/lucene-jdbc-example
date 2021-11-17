/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.lucenejdbc.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;

/**
 *
 * @author sfrey
 */
public class LuceneIndexer implements Indexer {

  static class Options {
    public IndexWriter writer;
    public final Map<String, FieldType> fields = new HashMap<>();
  }

  public static class Builder {
    private Options options = new Options();


    Builder() {}


    public Builder withWriter(IndexWriter writer) {
      options.writer = writer;
      return this;
    }


    public Builder withField(String field, FieldType type) {
      options.fields.put(field, type);
      return this;
    }


    public Builder withFields(Map<String, FieldType> fields) {
      options.fields.putAll(fields);
      return this;
    }


    public LuceneIndexer build() throws IllegalArgumentException {
      if (options.writer == null) {
        throw new IllegalArgumentException("Expected writer to be defined.");
      }

      if (options.fields.isEmpty()) {
        throw new IllegalArgumentException("Expected at least one field.");
      }


      return new LuceneIndexer(options);
    }
  }


  public static Builder builder() {
    return new Builder();
  }


  private final IndexWriter writer;
  private final Map<String, FieldType> fields;
  private Boolean autoCommit = true;

  LuceneIndexer(Options options) {
    writer = options.writer;
    fields = options.fields;
  }


  public Boolean getAutoCommit() {
    return autoCommit;
  }


  public void setAutoCommit(Boolean autoCommit) {
    this.autoCommit = autoCommit;
  }


  @Override
  public void close() throws Exception {
    if (autoCommit) {
      commit();
    }

    writer.close();
  }


  @Override
  public void clear() throws IOException {
    writer.deleteAll();
  }

  @Override
  public void commit() throws IOException {
    writer.commit();
  }


  @Override
  public void index(Map<String, String> item) throws IOException {
    _index(item);
  }


  @Override
  public void index(List<Map<String, String>> items) throws IOException {
   for (Map<String, String> item : items) {
     _index(item);
   }
  }


  private void _index(Map<String, String> item) throws IOException {
    writer.addDocument(toDocument(item));
  }


  private Document toDocument(Map<String, String> item) {
    final Document document = new Document();

    for (String name : fields.keySet()) {
      if (item.containsKey(name)) {
        Field field = toField(name, item.get(name), fields.get(name));

        document.add(field);
      }
    }

    return document;
  }


  private Field toField(String name, String value, FieldType type) {
    switch (type) {
      case STRING:
        return new StringField(name, value, Field.Store.YES);

      case TEXT:
        return new TextField(name, value, Field.Store.YES);

      default:
        throw new IllegalArgumentException("Unsupported field type" + type);
    }
  }

}
