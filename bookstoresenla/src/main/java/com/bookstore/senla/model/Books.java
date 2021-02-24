package com.bookstore.senla.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Books")
@XmlAccessorType(XmlAccessType.FIELD)
public class Books {

    @XmlElement(name="Book")
    List<BookList> bookList;

    public List<BookList> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookList> bookList) {
        this.bookList = bookList;
    }

    @Override
    public String toString() {
        return "Books [book_list=" + bookList + "]";
    }



}