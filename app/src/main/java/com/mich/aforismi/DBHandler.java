package com.mich.aforismi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.id;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "quotesDB";
    // Contacts table quotes
    private static final String TABLE_QUOTES = "quotes";
    private static final String TABLE_AUTHORS = "authors";
    // Quotes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR_ID = "author_id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_AUTHOR_LINK = "author_link";
    private static final String KEY_QUOTE = "quote";
    private static final String KEY_QUOTE_INDEX = "quoteIndex";
    private Cursor cursor;


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AUTHORS_TABLE = "CREATE TABLE " + TABLE_AUTHORS + "("
                + KEY_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AUTHOR + " TEXT, " + KEY_AUTHOR_LINK + " TEXT)";
        String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AUTHOR_ID + " INTEGER,"
                + KEY_QUOTE + " TEXT" + ")";
        db.execSQL(CREATE_AUTHORS_TABLE);
        db.execSQL(CREATE_QUOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHORS);
// Creating tables again
        onCreate(db);
    }

    // Adding new quote
    public void addQuote(Quote quote) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AUTHOR_ID, quote.getAuthorId()); // Quote Author
        values.put(KEY_QUOTE, quote.getQuote()); // Quote

// Inserting Row
        db.insert(TABLE_QUOTES, null, values);
        db.close(); // Closing database connection
    }

    // Getting one quote
    public Quote getQuoteById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(TABLE_QUOTES, new String[]{KEY_ID, KEY_AUTHOR_ID, KEY_QUOTE}, KEY_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return new Quote(/*Integer.parseInt(cursor.getString(0)), */Integer.parseInt(cursor.getString(1)),
                cursor.getString(2), cursor.getString(3));

    }


    // Getting one quote
    public Quote getQuote(String quote) {
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(TABLE_QUOTES, new String[]{KEY_QUOTE}, KEY_QUOTE + "= ? ", new String[]{quote}, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return new Quote(Integer.parseInt(cursor.getString(1)), cursor.getString(2), cursor.getString(3));
    }


    // Adding new author
    public void addAuthor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AUTHOR, author.getAuthor()); // Quote Author
        values.put(KEY_AUTHOR_LINK, author.getLink());

// Inserting Row
        db.insert(TABLE_AUTHORS, null, values);
        db.close(); // Closing database connection
    }

    // Getting All Quotes
    public List<Quote> getAllQuotes() {
        List<Quote> quoteList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_QUOTES;

        SQLiteDatabase db = this.getWritableDatabase();

        try {
            cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Quote quote = new Quote();
                    quote.setAuthorId(cursor.getString(1));
                    quote.setQuote(cursor.getString(2));
// Adding contact to list
                    quoteList.add(quote);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
// return contact list
        return quoteList;
    }

    // Getting quotes Count
    public int getQuotesCount() {
        String countQuery = "SELECT * FROM " + TABLE_QUOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a quote
//        public int updateQuote(Quote quote) {
//            SQLiteDatabase db = this.getWritableDatabase();

//            ContentValues values = new ContentValues();
//            values.put(KEY_QUOTE_INDEX, quote.getQuoteIndex());
//            values.put(KEY_AUTHOR, quote.getAuthor());
//            values.put(KEY_QUOTE, quote.getQuote());

// updating row
//            return db.update(TABLE_QUOTES, values, KEY_ID + " = ?",
//                    new String[]{String.valueOf(quote.getId())});
//        }

    // Deleting a quote
//        public void deleteQuote(Quote quote) {
//            SQLiteDatabase db = this.getWritableDatabase();
//            db.delete(TABLE_QUOTES, KEY_ID + " = ?",
//                    new String[] { String.valueOf(quote.getId()) });
//            db.close();
//        }
}
// CONTROLLARE QUESTI ULTIMI 2 METODI, getId non c e piu