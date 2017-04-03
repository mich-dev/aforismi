package com.mich.aforismi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//import static com.mich.aforismi.R.id.toolbar;

//import static com.mich.aforismi.R.id.toolbar;
//import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements IBattuteResponse {

    private TextView listaAlfabetoTitolo;

    ArrayList<Character> alphabeticalList = new ArrayList<>();
    ListView list, listaAlfabeto;
    ArrayAdapter adapter, listaAlfabetoAdapter;
    private String autoreFile = "";
    String[] autoreFileSplit = null;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] menu;
    RelativeLayout alfabeto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_laterale);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.lista_alfabeto);
        View inflated = stub.inflate();

//        LinearLayout mContainer = inflater.inflate(R.layout.linear_layout, null);
//        RelativeLayout relative = inflater.inflate(R.layout.lista_alfabeto, null);
        alfabeto = (RelativeLayout) findViewById(R.id.alfabeto);
        listaAlfabeto = (ListView) findViewById(R.id.lista_alfabeto);
//        listaAlfabeto.setBackgroundColor(Color.BLACK);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Toolbar di prova");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();


//        parsedHtmlNode = (TextView) findViewById(R.id.html_content);
//        listaAlfabetoTitolo = (TextView) findViewById(R.id.titolo_alfabeto);
//        listaAlfabetoTitolo.setText(getString(R.string.titolo_per_alfabeto));


/* READ A TXT FILE and extract authors and links
        InputStream input = null;
        try {
            input = this.getResources().getAssets().open("ListaAutoriELinkDrZapAggiornata.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            DBHandler db = new DBHandler(this);
            db.getWritableDatabase();

            if (input != null) {
                while ((autoreFile = reader.readLine()) != null) {
                    autoreFileSplit = autoreFile.split("\\*");
                    System.out.println("Autore: "+autoreFileSplit[0] + "\n");
                    db.addAuthor(new Author(autoreFileSplit[0],autoreFileSplit[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
            } catch (Throwable ignore) {
            }
        }
*/

//        StoreInDB store = new StoreInDB(this);
//        store.insertToDB();

        for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
            alphabeticalList.add(alphabet);
        }

        listaAlfabetoAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, alphabeticalList);

        listaAlfabeto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Character item = (Character) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, AuthorsListActivity.class);
                intent.putExtra("scelta", item);
                startActivity(intent);
            }

        });

        listaAlfabeto.setAdapter(listaAlfabetoAdapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postResult(ArrayList<String> asyncresult) {
//        Log.d("Numero di battute: ", "" + asyncresult.size());
        list.setAdapter(adapter);
    }


}
