package com.mich.aforismi;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.R.attr.data;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by user1 on 7/9/2016.
 */
public class StoreInDB {

    Context context;
    private Document htmlDocument;
    private Element table;
    private Elements rows;
    private Elements cols = null;
    ArrayList<String> listItems = new ArrayList<>();
    private InputStream input = null;
    private String autore = "no name";
    private int quoteIndex = -1;
    private String quote = "nothing";
    //    private String file;
    private String[] listOfFiles;

    public StoreInDB(Context context) {
        this.context = context;
    }

    public void insertToDB() {
        DBHandler db = new DBHandler(context);

        try {
            listOfFiles = context.getResources().getAssets().list("");

            for (String file : listOfFiles) {
                if (file.toLowerCase().endsWith(".html")) {
//                    System.out.println(file);

                    input = context.getResources().getAssets().open(file);
                    htmlDocument = Jsoup.parse(input, "ISO-8859-1", "");
//                    autore = htmlDocument.select("title").get(0).text();

//                    if (autore.toLowerCase().contains("frasi celebri di")) {
//                        autore = autore.substring(17, autore.length()).trim();
//                    } else if (autore.toLowerCase().contains("omaggio ad")) {
//                        autore = autore.substring(11, autore.length()).trim();
//                    } else if (autore.toLowerCase().contains("omaggio alla")) {
//                        autore = autore.substring(13, autore.length()).trim();
//                    } else if (autore.toLowerCase().contains("omaggio a")) {
//                        autore = autore.substring(10, autore.length()).trim();
//                    }

                    //DA RIVEDERE!
//                    System.out.println("SSSSSIMILI: "+StringUtils.getJaroWinklerDistance(file,"Berlusconi"));


//                    db.getWritableDatabase();
//                    db.getQuote("");


                    htmlDocument = new Cleaner(Whitelist.relaxed().removeTags("b", "span")).clean(htmlDocument);

                    if (htmlDocument.select("table").size() > 0) {

                        table = htmlDocument.select("table").get(0); //select the first table.
                        rows = table.select("tr");

                        for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
                            Element row = rows.get(i);
                            cols = row.select("p");
                            for (int j = 0; j < cols.size(); j++) {
                                quote = cols.get(j).text().replaceAll("\u00a0", "").trim();
                                quoteIndex = quote.indexOf(".");
                                if (quoteIndex >= 0) {
                                    quoteIndex = Integer.valueOf(quote.substring(0, quoteIndex));
                                    quote = quote.substring((String.valueOf(quoteIndex).length() + 1), quote.length());
                                }

                                Log.d("Insert: ", "Inserting ..");
//                                db.getWritableDatabase();
//                                db.addQuote(new Quote(quoteIndex, autore, quote));

//                        listItems.add(cols.get(j).text().replaceAll("\u00a0", "").trim());
//                        System.out.println(cols.get(j).text().trim());
                                System.out.println(quoteIndex + " * " + autore + " * " + quote);


                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
