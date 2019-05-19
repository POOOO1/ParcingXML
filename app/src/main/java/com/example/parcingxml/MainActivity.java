package com.example.parcingxml;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://example.com/products.xml");
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";
        private Throwable e;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "xml document: " + s);
            ProductXmlParser parser = new ProductXmlParser();
            if(s!=null && parser.parse(s))
            {
                for(Product prod: parser.getProducts()){
                    Log.d("XML", prod.toString());
                }
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String content = new String();
            try{
                content = downloadXML(strings[0]);
            }
            catch (IOException ex){
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }
            return content;
        }

        private String downloadXML(String urlPath) throws IOException {
            StringBuilder xmlResult = new StringBuilder();
            BufferedReader reader = null;
            try {
                URL url = new URL(urlPath);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line=null;
                while ((line=reader.readLine()) != null) {
                    xmlResult.append(line);
                }
                return xmlResult.toString();
            } catch(MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch(IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch(SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
            return null;
        }
    }
}
