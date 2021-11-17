# Lucene JDB Example

This repository contains a small example on how to use Apache Lucene with JDBC to write and read
from an search index.

## Getting started

1. Import the demo data set from `./data/kreise.csv ` into a PostgreSQL database instance.
2. Open project in Netbeans or your favorite IDE and install dependencies.
3. Run the `com.github.lucenejdbc.example.LuceneWriterExample` main, which writes an index to `/tmp/idx/kreise`.
4. Run the `com.github.lucenejdbc.example.LuceneReaderExample` main, which reads an index from `/tmp/idx/kreise`.

## Customization

If you want use a different database connection or change the index location, please modify the
`com/github/lucenejdbc/example/Constants.java` file.
