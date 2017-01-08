package com.mich.aforismi;

/**
 * Created by user1 on 7/7/2016.
 */
public class Quote {
    private String authorId;
    private String quote;
//    private int quoteIndex;

    public Quote() {
    }

    public Quote(int quoteIndex, String authorId, String quote) {
        this.authorId = authorId;
        this.quote = quote;
//        this.quoteIndex = quoteIndex;
    }


    public void setAuthorId(String author) {
        this.authorId = author;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

//    public void setQuoteIndex(int quoteIndex) {
//        this.quoteIndex = quoteIndex;
//    }


    public String getQuote() {
        return quote;
    }

    public String getAuthorId() {
        return authorId;
    }

//    public int getQuoteIndex() {
//        return quoteIndex;
//    }
}
