package com.github.theimplementer.lucenefacetedsearch;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.glassfish.hk2.api.Factory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static org.apache.lucene.util.Version.LUCENE_4_9;

public class IndexWriterFactory implements Factory<IndexWriter> {

    private static final String LUCENE_INDEX_LOCATION_PROPERTY = "lucene.index.location";
    private static final String LUCENE_INDEX_TYPE_PROPERTIES = "lucene.index.type";
    public static final String IN_MEMORY = "mem";

    private final Properties properties;

    @Inject
    public IndexWriterFactory(Properties properties) {
        this.properties = properties;
    }

    @Override
    public IndexWriter provide() {
        final String indexType = properties.getProperty(LUCENE_INDEX_TYPE_PROPERTIES, IN_MEMORY);
        try {
            final Directory indexDirectory = getDirectory(indexType);
            final StandardAnalyzer analyzer = new StandardAnalyzer(LUCENE_4_9);
            final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(LUCENE_4_9, analyzer);
            return new IndexWriter(indexDirectory, indexWriterConfig);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose(IndexWriter indexWriter) {
        try {
            indexWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Directory getDirectory(String indexType) throws IOException {
        final Directory indexDirectory;
        if (IN_MEMORY.equals(indexType)) {
            indexDirectory = new RAMDirectory();
        } else {
            final String indexLocation = properties.getProperty(LUCENE_INDEX_LOCATION_PROPERTY);
            indexDirectory = new MMapDirectory(new File(indexLocation));
        }
        return indexDirectory;
    }
}
