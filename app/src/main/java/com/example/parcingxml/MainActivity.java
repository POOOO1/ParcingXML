package com.example.parcingxml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XmlPullParser xpp = getResources().getXml(R.xml.products);
        ProductResourceParser parser = new ProductResourceParser();
        if(parser.parse(xpp))
        {
            for(Product prod: parser.getProducts()){
                Log.d("XML", prod.toString());
            }
        }
    }
}