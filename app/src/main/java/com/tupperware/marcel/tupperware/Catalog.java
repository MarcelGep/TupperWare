package com.tupperware.marcel.tupperware;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private int id;
    private String artnr;
    private String description;
    private String dimensions;
    private String content;
    private String price;
    private String color;
    private String info;

    public Catalog(int id, String artnr, String description, String dimensions, String content, String price, String color, String info){
        this.id = id;
        this.artnr = artnr;
        this.description = description;
        this.dimensions = dimensions;
        this.content = content;
        this.price = price;
        this.color = color;
        this.info = info;
    }

    @Override
    public String toString() {
        String output = description;
        return output;
    }

    public String getCatalogInfo(){
        String artinfo = "ArtNr:\t\t\t\t" + artnr + "\n"
                + "Bezeichung:\t\t" + description + "\n"
                + "Maße\t\t\t\t" + dimensions + "\n"
                + "Inhalt\t\t\t\t" + content + "\n"
                + "Preis\t\t\t\t" + price + " €\n"
                + "Farbe\t\t\t\t" + color + "\n"
                + "Info\t\t\t\t" + info;
        return artinfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price + " €";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtnr() {
        return artnr;
    }

    public void setArtnr(String artnr) {
        this.artnr = artnr;
    }

    public String getPicId() {
        String artnr = getArtnr();
        String picID = (artnr.split(" ")[0] + artnr.split(" ")[1]).toLowerCase();
        return picID;
    }
}
