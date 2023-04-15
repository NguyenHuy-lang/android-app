package com.example.testth2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class Book implements Serializable {
    private static int lastId = 0;
    private int id;
    private String name;
    private String status; // status
    private String content; // content
    private String dateRelease;
    private String cor; // cor

    public Book(String name, String status, String content, String dateRelease, String cor
    ) {
        lastId++;
        this.id = lastId;
        this.name = name;
        this.status = status;
        this.content = content;
        this.cor = cor;
        this.dateRelease = dateRelease;
    }

    public Book(int id, String name, String status, String content,  String dateRelease, String cor) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.content = content;
        this.cor = cor;
        this.dateRelease = dateRelease;
    }

    public Book() {
        lastId++;
        this.id = lastId;
    }

    public static void setLastId(int lastId) {
        Book.lastId = lastId;
    }

//    public static void sortBooksByPriceDescending(List<Book> bookList) {
//        Comparator<Book> priceComparator = new Comparator<Book>() {
//            @Override
//            public int compare(Book book1, Book book2) {
//                if (book1.() < book2.getPrice()) {
//                    return 1;
//                } else if (book1.getPrice() > book2.getPrice()) {
//                    return -1;
//                } else {
//                    return 0;
//                }
//            }
//        };
//
//        bookList.sort(priceComparator);
//    }

    public static int getLastId() {
        return lastId;
    }

    public static void sortBooksByDate(List<Book> mBookList) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getDateRelease() {
        return dateRelease;
    }

    public void setDateRelease(String dateRelease) {
        this.dateRelease = dateRelease;
    }
}
