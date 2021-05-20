/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author user
 */
public class Hotel {
    private int id;
    private String nom,adresse,image,description;
    private double price;
    private Date Datedebut,Datefin;
    public Hotel(){
        
    }

    public Hotel(int id, String nom, String adresse, String image, String description,double price, Date Datedebut, Date Datefin) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.image = image;
        this.description = description;
        this.price=price;
        this.Datedebut = Datedebut;
        this.Datefin = Datefin;
    }

    public Hotel(String nom, String adresse, double price,String description, Date Datedebut, Date Datefin) {
        this.nom = nom;
        this.adresse = adresse;
        this.description = description;
        this.price = price;
        this.Datedebut = Datedebut;
        this.Datefin = Datefin;
    }

    public Hotel(String nom, String adresse, String image, String description,double price, Date Datedebut, Date Datefin) {
        this.nom = nom;
        this.adresse = adresse;
        this.image = image;
        this.description = description;
        this.price=price;
        this.Datedebut = Datedebut;
        this.Datefin = Datefin;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Hotel{" + "id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", image=" + image + ", description=" + description + ", price=" + price + ", Datedebut=" + Datedebut + ", Datefin=" + Datefin + '}';
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatedebut() {
        return Datedebut;
    }

    public void setDatedebut(Date Datedebut) {
        this.Datedebut = Datedebut;
    }

    public Date getDatefin() {
        return Datefin;
    }

    public void setDatefin(Date Datefin) {
        this.Datefin = Datefin;
    }

    
    
    
}
