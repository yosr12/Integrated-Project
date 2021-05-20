/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author user
 */
public class Transport {
    private int id;
    private String description,disponibilite,type;
    private double price;

    public Transport() {
    }

    
    public Transport(int id, String description, String disponibilite, String type, double price) {
        this.id = id;
        this.description = description;
        this.disponibilite = disponibilite;
        this.type = type;
        this.price = price;
    }

    public Transport(String description, String disponibilite, double price,String type) {
        this.description = description;
        this.disponibilite = disponibilite;
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    

    @Override
    public String toString() {
        return "Transport{" + "id=" + id + ", description=" + description + ", disponibilite=" + disponibilite + ", price=" + price + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
