 package com.example.classdream;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class dictionary extends AppCompatActivity {

    String url;
    Button search;
    EditText input;
    ProgressBar progres;
    TextView show;

    int model_no=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Main_Bar cal = new Main_Bar();

        search = findViewById(R.id.search);
        input = findViewById(R.id.input);
        show = findViewById(R.id.show);
        progres = findViewById(R.id.progress);
        progres.setVisibility(View.INVISIBLE);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!input.getText().toString().isEmpty())
                {
                    input.onEditorAction(EditorInfo.IME_ACTION_DONE );
                    progres.setVisibility(View.VISIBLE);
                    url = dictionaryEntries();
                        ClassObject();
                }
                else
                {
                    input.setFocusable(true);
                    input.setHint("Please Write Here...");
                }
            }
        });
    }

    /////////////////////API CONNECTIVITY//////////////////////////////////////
    private String dictionaryEntries() {
        final String language = "en-gb";
        final String word = input.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }
    private String dictionaryEntries1() {
        final String language = "UR";
        final String word = input.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }
    public void ClassObject()
    {
        DictionaryRequest mydictionaryrequest = new DictionaryRequest(this,show,progres);
        mydictionaryrequest.execute(url);


    }



    ////////////////////////////////////////call back method///////////////////////////////////////////////////
        /*
        try {
            JSONObject js = new JSONObject(result);
            JSONArray results1= js.getJSONArray("results");


            JSONObject ientries = results1.getJSONObject(0);
            JSONArray iarray= ientries.getJSONArray("lexicalEntries");



            JSONObject entriesw = iarray.getJSONObject(0);
            JSONArray e = entriesw.getJSONArray("entries");


            JSONObject jsObject = e.getJSONObject(0);
            JSONArray sensesArray = jsObject.getJSONArray("senses");

            JSONObject d = sensesArray.getJSONObject(0);
            JSONArray definationArray = d.getJSONArray("definitions");

            String def = definationArray.getString(0);

            input.setText(def);
            Toast.makeText(getApplicationContext(),def,Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }


*/
}

