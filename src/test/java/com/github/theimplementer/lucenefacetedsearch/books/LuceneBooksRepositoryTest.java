package com.github.theimplementer.lucenefacetedsearch.books;


import com.insightfullogic.lambdabehave.JunitSuiteRunner;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.github.theimplementer.lucenefacetedsearch.books.Book.book;
import static com.insightfullogic.lambdabehave.Suite.describe;
import static org.apache.lucene.util.Version.LUCENE_4_9;

@RunWith(JunitSuiteRunner.class)
public class LuceneBooksRepositoryTest {
    {

        describe("the lucene books repository", it -> {

            final IndexWriter indexWriter = getTestWriter();
            final BooksRepository repository = new LuceneBooksRepository(indexWriter);

            it.isSetupWith(() -> {
                indexWriter.deleteAll();
                indexWriter.commit();
            });

            it.should("allow saving books", expect -> {
                repository.add(book("A game of thrones"));

                expect.that(indexWriter.numDocs()).is(1);
            });

            it.should("return an empty list if there are no books available", expect -> {
                final List<Book> books = repository.list();

                expect.that(books.size()).is(0);
            });

            it.should("return a list with the available books", expect -> {
                repository.add(book("A game of thrones"));

                final List<Book> books = repository.list();

                expect.that(books.size()).is(1);
                expect.that(books.get(0).getTitle()).is("A game of thrones");
            });
        });
    }

    private static IndexWriter getTestWriter() {
        try {
            final RAMDirectory ramDirectory = new RAMDirectory();
            final IndexWriter indexWriter = new IndexWriter(ramDirectory, new IndexWriterConfig(LUCENE_4_9, new StandardAnalyzer(LUCENE_4_9)));
            indexWriter.commit();
            return indexWriter;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}