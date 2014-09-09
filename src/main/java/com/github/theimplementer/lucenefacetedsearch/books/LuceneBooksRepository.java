package com.github.theimplementer.lucenefacetedsearch.books;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.*;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

import static com.github.theimplementer.lucenefacetedsearch.books.Book.book;
import static com.github.theimplementer.lucenefacetedsearch.books.BookDefinition.TITLE;
import static java.lang.Math.max;
import static org.apache.lucene.index.DirectoryReader.open;

public class LuceneBooksRepository implements BooksRepository {

    private final IndexWriter indexWriter;

    @Inject
    public LuceneBooksRepository(IndexWriter indexWriter) {
        this.indexWriter = indexWriter;
    }

    @Override
    public List<Book> list() throws Exception {
        final IndexSearcher indexSearcher = new IndexSearcher(open(indexWriter.getDirectory()));
        final TopDocs allDocs = indexSearcher.search(new MatchAllDocsQuery(), max(1, indexWriter.numDocs()));
        final List<Book> books = new LinkedList<>();
        for (ScoreDoc scoreDoc : allDocs.scoreDocs) {
            final Document document = indexSearcher.doc(scoreDoc.doc);
            final String title = document.get(TITLE);
            books.add(book(title));
        }
        return books;
    }

    @Override
    public void add(Book book) throws Exception {
        final Document bookDocument = new Document();
        bookDocument.add(new TextField(TITLE, book.getTitle(), Field.Store.YES));
        indexWriter.addDocument(bookDocument);
        indexWriter.commit();
    }
}
