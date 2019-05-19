package com.example.parcingxml;

import org.xmlpull.v1.XmlPullParser;
import java.util.ArrayList;


public class ProductResourceParser {
    private ArrayList<Product> products;

    public ProductResourceParser(){
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts(){
        return  products;
    }

    public boolean parse(XmlPullParser xpp){
        boolean status = true;
        Product currentProduct = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("product".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentProduct = new Product();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("product".equalsIgnoreCase(tagName)){
                                products.add(currentProduct);
                                inEntry = false;
                            } else if("name".equalsIgnoreCase(tagName)){
                                currentProduct.setName(textValue);
                            } else if("price".equalsIgnoreCase(tagName)){
                                currentProduct.setPrice(textValue);
                            }
                        }
                        break;
                    default:
                }
                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;
    }
}
