package com.mich.aforismi;

import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by user1 on 6/25/2016.
 */
public class DownloadAsyncTaskFromDB extends AsyncTask<Void, Void, ArrayList<String>> {

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

    public DownloadAsyncTaskFromDB(IBattuteResponse listener, String c) {
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

//        StoreInDB store = new StoreInDB(getContext()); // da controllare il context


        for (int j = 0; j < cols.size(); j++) {
            listItems.add(cols.get(j).text().replaceAll("\u00a0", ""));
            System.out.println(cols.get(j).text());
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
