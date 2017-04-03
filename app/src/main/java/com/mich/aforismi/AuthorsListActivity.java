package com.mich.aforismi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AuthorsListActivity extends AppCompatActivity implements IBattuteResponse {

    ListView listaAutori;
    private TextView listaAutoriTitolo;
    ArrayAdapter listaAutoriAdapter;
    ArrayList<String> authorsList = new ArrayList<>();
    ListView list;
    ArrayAdapter adapter;
    Character choice;
    ArrayList<String> listItems;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_laterale);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.lista_autori);
        View inflated = stub.inflate();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Toolbar di prova");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        listaAutori = (ListView) findViewById(R.id.lista_autori);
//        listaAutoriTitolo = (TextView) findViewById(R.id.titolo_autori);
        listItems = new ArrayList<>();

        choice = (Character) getIntent().getSerializableExtra("scelta");
        System.out.println(choice);

        listaAutori.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(AuthorsListActivity.this, QuotesListActivity.class);
                intent.putExtra("scelta",item);
//                intent.putExtra("autore",item.)
                startActivity(intent);
            }

        });

        DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask(AuthorsListActivity.this, choice.toString());
        downloadAsyncTask.execute();
    }

    @Override
    public void postResult(ArrayList<String> asyncresult) {
        Log.d("Numero di battute: ", "" + asyncresult.size());
        authorsList = asyncresult;
        listaAutoriAdapter = new ArrayAdapter(AuthorsListActivity.this, android.R.layout.simple_list_item_1, authorsList);
        listaAutori.setAdapter(listaAutoriAdapter);
//        listaAutoriAdapter.notifyDataSetChanged();
    }

}
