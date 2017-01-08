package com.mich.aforismi;

/**
 * Created by user1 on 7/7/2016.
 */
public class Author {
    private String author;
    private String link;

    public Author() {
    }

    public Author(String author, String link) {
        this.author = author;
        this.link = link;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

}
