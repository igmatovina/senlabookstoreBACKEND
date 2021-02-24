package com.bookstore.senla.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table
@XmlRootElement(name="Book")
public class BookList {

    @Id
    @SequenceGenerator(
            name = "bookList_sequence",
            sequenceName = "bookList_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bookList_sequence"
    )

    private Long isbn;
    private String title;
    private String author;

public BookList() {

}
public BookList(Long isbn, String title, String author) {
    this.isbn = isbn;
    this.title = title;
    this.author = author;
}

public BookList(String title, String author) {
    this.title = title;
    this.author = author;
}


@XmlAttribute
public Long getIsbn() {
    return isbn;
}
public void setIsbn(Long isbn) {
    this.isbn = isbn;
}

@XmlElement
public String getTitle() {
    return title;
}
public void setTitle(String title) {
    this.title = title;
}

@XmlElement
public String getAuthor() {
    return author;
}
public void setAuthor(String author) {
    this.author = author;
}

@Override
public String toString() {
    return "BookList{" +
            "isbn=" + isbn +
            ", title='" + title + '\'' +
            ", author='" + author + '\'' +
            '}';
}
}