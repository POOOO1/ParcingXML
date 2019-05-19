package com.example.parcingxml;

public class Product {
    private String name;
    private String price;

    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(String price){
        this.price = price;
    }
    public String toString(){
        return  "Product: " + name + " - " + price;
    }
}

