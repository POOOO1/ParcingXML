package com.example.parcingxml;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.util.ArrayList;
import java.io.StringReader;

public class ProductXmlParser {

    private ArrayList<Product> products;

    public ProductXmlParser(){
        products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts(){
        return  products;
    }

    public boolean parse(String xmlData){
        boolean status = true;
        Product currentProduct = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
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
