package com.github.theimplementer.lucenefacetedsearch.books;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.github.theimplementer.lucenefacetedsearch.books.Book.book;
import static org.apache.lucene.search.TopScoreDocCollector.create;

public class LuceneBooksRepository implements BooksRepository {

    private final DirectoryReader directoryReader;

    public LuceneBooksRepository(DirectoryReader directoryReader) {
        this.directoryReader = directoryReader;
    }

    @Override
    public List<Book> list() throws IOException {
        final IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        final TopScoreDocCollector collector = create(1, true);
        indexSearcher.search(new BooleanQuery(), collector);
        final TopDocs topDocs = collector.topDocs();
        final List<Book> books = new LinkedList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            final Document document = indexSearcher.doc(scoreDoc.doc);
            final String title = document.get("title");
            books.add(book(title));
        }
        return books;
    }
}
