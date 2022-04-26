package com.example.warehouse;

public class MainModel {

    String materialNO,description,qty,supplier,sloc,tloc;

    MainModel(){

    }

    //to insert record in Stock - sloc is null
    public MainModel(String materialNO, String description, String qty, String supplier) {
        this.materialNO = materialNO;
        this.description = description;
        this.qty = qty;
        this.supplier = supplier;
    }

    //For loading page to insert data
    public MainModel(String materialNO, String description, String qty, String supplier, String sloc , String tloc) {
        this.materialNO = materialNO;
        this.description = description;
        this.qty = qty;
        this.supplier = supplier;
        this.sloc = sloc;
        this.tloc = tloc;
    }

    public MainModel(String sloc) {
        this.sloc = sloc;
    }


    public String getMaterialNO() {
        return materialNO;
    }

    public void setMaterialNO(String materialNO) {
        this.materialNO = materialNO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSloc() {
        return sloc;
    }

    public void setSloc(String sloc) {
        this.sloc = sloc;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getTloc() { return tloc; }

    public void setTloc(String tloc) { this.tloc = tloc; }

}
