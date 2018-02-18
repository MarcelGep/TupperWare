package com.tupperware.marcel.tupperware;

/**
 * Created by Marcel on 07.12.2016.
 */

public class Articles {

    private int id;
    private String artnr;
    private String description;
    private String dimensions;
    private String content;
    private String price;
    private String color;
    private String quantity;
    private String note;

    public Articles(int id, String artnr, String description, String dimensions, String content, String price, String color, String quantity, String note){
        this.id = id;
        this.artnr = artnr;
        this.description = description;
        this.dimensions = dimensions;
        this.content = content;
        this.price = price;
        this.color = color;
        this.quantity = quantity;
        this.note = note;
    }

    @Override
    public String toString() {
        String output = description;
        return output;
    }

    public String getArticleInfo(){
        String artinfo = "ArtNr:\t\t\t\t" + artnr + "\n"
                + "Bezeichung:\t\t" + description + "\n"
                + "Maße\t\t\t\t" + dimensions + "\n"
                + "Inhalt\t\t\t\t" + content + "\n"
                + "Preis\t\t\t\t" + price + " €\n"
                + "Farbe\t\t\t\t" + color + "\n"
                + "Bestand\t\t\t" + quantity + " St.\n"
                + "Notiz\t\t\t\t" + note;
        return artinfo;
    }

    public String getArtnr() {
        return artnr;
    }

    public void setArtnr(String artnr) {
        this.artnr = artnr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicId() {
        String artnr = getArtnr();
        String picID = (artnr.split(" ")[0] + artnr.split(" ")[1]).toLowerCase();
        return picID;
    }
}
