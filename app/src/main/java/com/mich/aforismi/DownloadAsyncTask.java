package com.mich.aforismi;

import android.content.Context;
import android.graphics.Path;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;

import static android.R.attr.lines;


/**
 * Created by user1 on 6/25/2016.
 */
class DownloadAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

    private final IBattuteResponse mListener;
    private String choice;
    ArrayList<String> listItems = new ArrayList<>();
    private TextView parsedHtmlNode;
    private String htmlContentInStringFormat;
    private Element table;
    private Elements rows;
    private Document htmlDocument;
    private String htmlPageUrl = "http://www.drzap.it/O_Bombeck.htm";
    private boolean listaAutori = false;
    private Elements cols = null;

    public DownloadAsyncTask(IBattuteResponse listener, String c) {
        mListener = listener;
        choice = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {


        if ((choice.length() == 1) && ((int) choice.charAt(0) >= 65 && (int) choice.charAt(0) <= 90)) {
            listaAutori = true;
        }

        if (listaAutori) {
            htmlPageUrl = "http://www.drzap.it/" + choice + "_index.htm";
        } else {
            htmlPageUrl = "http://www.drzap.it/O_" + choice.split(",")[0] + ".htm";
        }
        try {
            htmlDocument = Jsoup.connect(htmlPageUrl).get();
            htmlContentInStringFormat = htmlDocument.title();
            htmlDocument = new Cleaner(Whitelist.relaxed().removeTags("b", "span")).clean(htmlDocument);


            if (htmlDocument.select("table").size() > 0) {
                table = htmlDocument.select("table").get(0); //select the first table.
                rows = table.select("tr");

                for (int i = 0; i < rows.size(); i++) { //first row is the col names so skip it.
                    Element row = rows.get(i);
                    if (listaAutori) {
                        cols = row.select("a");
                    } else {
                        cols = row.select("p");
                    }
                    for (int j = 0; j < cols.size(); j++) {
                        listItems.add(cols.get(j).text().replaceAll("\u00a0", ""));
//                        System.out.println(cols.get(j).text());
//                        if(cols.get(j).attr("href")!="") {
//                        System.out.println(cols.get(j).attr("href"));
//                        } else {
//                            System.out.println("VUOTO");
//                        }
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (listaAutori) {
            Collections.sort(listItems);
        }
        return listItems;
    }

    protected void onPostExecute(ArrayList<String> listItems) {
//        parsedHtmlNode.setText(htmlContentInStringFormat);
        if (mListener != null) {
            mListener.postResult(listItems);
        }
    }
}
