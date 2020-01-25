package com.example.classdream;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DictionaryRequest extends AsyncTask<String, Integer, String> {

    Context c;
    TextView text;
    ProgressBar progress;

    DictionaryRequest(Context c,TextView text , ProgressBar progress)
    {
        this.c = c;
        this.text=text;
        this.progress=progress;
    }

    protected String doInBackground(String... params) {

        //TODO: replace with your own app id and app key
        final String app_id = "3befc4be";
        final String app_key = "4bf9377e3c483558b153c214e7039f6e";
        try {
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

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
            progress.setVisibility(View.INVISIBLE);
            text.setText(def);
        } catch (JSONException e) {
            e.printStackTrace();
        }

}
    }