package com.github.theimplementer.lucenefacetedsearch.books;


import com.insightfullogic.lambdabehave.JunitSuiteRunner;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.insightfullogic.lambdabehave.Suite.describe;
import static org.apache.lucene.index.DirectoryReader.open;
import static org.apache.lucene.util.Version.LUCENE_4_9;

@RunWith(JunitSuiteRunner.class)
public class LuceneBooksRepositoryTest {
    {

        describe("the lucene books repository", it -> {

            final BooksRepository booksRepository = new LuceneBooksRepository(getTestReader());

            it.should("return an empty list if there are no books available", expect -> {
                final List<Book> books = booksRepository.list();

                expect.that(books.size()).is(0);
            });
        });
    }

    private static DirectoryReader getTestReader() {
        try {
            final RAMDirectory ramDirectory = new RAMDirectory();
            final IndexWriter indexWriter = new IndexWriter(ramDirectory, new IndexWriterConfig(LUCENE_4_9, new StandardAnalyzer(LUCENE_4_9)));
            indexWriter.commit();
            indexWriter.close();
            return open(ramDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}