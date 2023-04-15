package com.example.testth2;

import static com.example.testth2.BookDatabaseHelper.COLUMN_ID;
import static com.example.testth2.BookDatabaseHelper.COLUMN_PUBLISHER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private SQLiteDatabase db;
    private final BookDatabaseHelper dbHelper;

    public BookDAO(Context context) {
        dbHelper = new BookDatabaseHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public int getMaxId() {
        open();
        int maxId = 0;

        Cursor cursor = db.rawQuery("SELECT MAX(id) FROM " + BookDatabaseHelper.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();
        close();

        return maxId;
    }


    public void addBook(Book book) {
        open();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, book.getId());
        values.put(BookDatabaseHelper.COLUMN_NAME, book.getName());
        values.put(BookDatabaseHelper.COLUMN_AUTHOR, book.getContent());
        values.put(BookDatabaseHelper.COLUMN_RELEASE_DATE, book.getDateRelease());
        values.put(COLUMN_PUBLISHER, book.getStatus());
        values.put(BookDatabaseHelper.COLUMN_PRICE, book.getCor());

        db.insert(BookDatabaseHelper.TABLE_NAME, null, values);
        close();
    }

    public void updateBook(Book book) {
        open();
        ContentValues values = new ContentValues();
        values.put(BookDatabaseHelper.COLUMN_NAME, book.getName());
        values.put(BookDatabaseHelper.COLUMN_AUTHOR, book.getContent());
        values.put(BookDatabaseHelper.COLUMN_RELEASE_DATE, book.getDateRelease());
        values.put(COLUMN_PUBLISHER, book.getStatus());
        values.put(BookDatabaseHelper.COLUMN_PRICE, book.getCor());

        db.update(BookDatabaseHelper.TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        close();
    }

    public void deleteBook(Book book) {
        open();
        db.delete(BookDatabaseHelper.TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(book.getId())});
        close();
    }

    public List<Book> getAllBooks() {
        open();
        List<Book> bookList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BookDatabaseHelper.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                String releaseDate = cursor.getString(3);
                String publisher = cursor.getString(4);
                String cor = cursor.getString(5);
                Book book = new Book(id, name, publisher, author, releaseDate, cor);
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return bookList;
    }

    public List<Book> searchByPrice(double minPrice, double maxPrice) {
        open();
        List<Book> books = new ArrayList<>();

        String[] columns = {COLUMN_ID, BookDatabaseHelper.COLUMN_NAME, BookDatabaseHelper.COLUMN_AUTHOR,
                BookDatabaseHelper.COLUMN_RELEASE_DATE, COLUMN_PUBLISHER, BookDatabaseHelper.COLUMN_PRICE};
        String selection = BookDatabaseHelper.COLUMN_PRICE + " >= ? AND " + BookDatabaseHelper.COLUMN_PRICE + " <= ?";
        String[] selectionArgs = {String.valueOf(minPrice), String.valueOf(maxPrice)};

        Cursor cursor = db.query(BookDatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                String releaseDate = cursor.getString(3);
                String publisher = cursor.getString(4);
                String cor = cursor.getString(5);

                Book book = new Book(id, name, publisher, author, releaseDate, cor);
                books.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return books;
    }


    public List<Book> searchByName(String nameJob) {
        open();
        List<Book> books = new ArrayList<>();

        String[] columns = {COLUMN_ID, BookDatabaseHelper.COLUMN_NAME, BookDatabaseHelper.COLUMN_AUTHOR,
                BookDatabaseHelper.COLUMN_RELEASE_DATE, COLUMN_PUBLISHER, BookDatabaseHelper.COLUMN_PRICE};
        String selection = BookDatabaseHelper.COLUMN_NAME + " = ?";
        String[] selectionArgs = {nameJob};

        Cursor cursor = db.query(BookDatabaseHelper.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                String releaseDate = cursor.getString(3);
                String publisher = cursor.getString(4);
                String cor = cursor.getString(5);

                Book book = new Book(id, name, publisher, author, releaseDate, cor);
                books.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return books;
    }

    public List<Book> searchByStatus(String statusJob) {
        open();
        List<Book> books = new ArrayList<>();

        String[] columns = {COLUMN_ID, BookDatabaseHelper.COLUMN_NAME, BookDatabaseHelper.COLUMN_AUTHOR,
                BookDatabaseHelper.COLUMN_RELEASE_DATE, COLUMN_PUBLISHER,
                BookDatabaseHelper.COLUMN_PRICE, "COUNT(" + COLUMN_ID + ")"};
        String selection = BookDatabaseHelper.COLUMN_NAME + " = ?";
        String[] selectionArgs = {statusJob};

        Cursor cursor = db.query(BookDatabaseHelper.TABLE_NAME,
                columns, selection,
                selectionArgs,
                COLUMN_PUBLISHER, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String author = cursor.getString(2);
                String releaseDate = cursor.getString(3);
                String publisher = cursor.getString(4);
                String cor = cursor.getString(5);
                Integer count = cursor.getInt(6);

                Book book = new Book(id, name, publisher, author, releaseDate, cor);
                books.add(book);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return books;
    }
}
