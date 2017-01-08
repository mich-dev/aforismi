package com.mich.aforismi;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuotesListActivity extends AppCompatActivity implements IBattuteResponse {

    ListView listaBattute;
    private TextView listaBattuteTitolo;
    ArrayAdapter listaBattuteAdapter;
    ArrayList<String> quotesList = new ArrayList<>();
    ListView list;
    ArrayAdapter adapter;
    String autore;
    ArrayList<String> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_battute);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.appBarLayout);
//        setSupportActionBar(toolbar);

        autore = (String) getIntent().getSerializableExtra("scelta");

        listaBattute = (ListView) findViewById(R.id.lista_battute);
        listaBattuteTitolo = (TextView) findViewById(R.id.titolo_battute);
        listaBattuteTitolo.setText(autore);
        listaBattuteTitolo.setBackgroundColor(Color.parseColor("#9fe7ff"));
//        Typeface titleTypeFace = Typeface.createFromAsset(this.getAssets(), "fonts/JosefinSans-Bold.ttf");
//        listaBattuteTitolo.setTypeface(titleTypeFace);

        listItems = new ArrayList<>();
        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask(QuotesListActivity.this, autore);
        downloadAsyncTask.execute();
    }

    @Override
    public void postResult(ArrayList<String> asyncresult) {
        Log.d("Numero di battute: ", "" + asyncresult.size());
        quotesList = asyncresult;
        listaBattuteAdapter = new ArrayAdapter(QuotesListActivity.this, android.R.layout.simple_list_item_1, quotesList);
        listaBattute.setAdapter(listaBattuteAdapter);
//        listaAutoriAdapter.notifyDataSetChanged();
    }

}
