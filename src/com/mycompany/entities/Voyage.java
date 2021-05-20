/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author House_Info
 */
public class Voyage {
    private int id;
    private int promotion_id;
    private String destination;
    private String image;
    private String description;
    private double prix;
    private String date_debut;
    private String date_fin;
    private String categorie;
    private Double lat;
    private Double lng;
    private String programme;
    private String inclut;
    private String ninclut;

    public Voyage(String destination, String description, double prix, String date_debut, String date_fin, String programme, String inclut, String ninclut) {
        this.destination = destination;
        this.description = description;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
    }
    

    public Voyage() {
    }

    public Voyage(int id) {
        this.id = id;
    }

    public Voyage(int id, String destination, String image, String description, double prix, String categorie, String programme, String inclut, String ninclut, double lat, double lng) {
        this.id = id;
        this.destination = destination;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.categorie = categorie;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
        this.lat = lat;
        this.lng = lng;
    }

  
    
    
//     list.add( rs.getString(8),rs.getDouble(9), rs.getDouble(10),rs.getString(11),rs.getString(12),rs.getString(13)));
 public Voyage(int id, String destination, String image, String description, double prix,String date_debut,String date_fin,
         String categorie,int promotion_id,double lng,double lat, String programme, String inclut, String ninclut ) {
        this.id = id;
        this.destination = destination;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.categorie = categorie;
        this.promotion_id = promotion_id;
        this.lng = lng;
        this.lat = lat;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
        
 }

    public Voyage(String destination, String description,String image, double prix, String date_debut, String date_fin, String categorie, String programme, String inclut, String ninclut, double lat, double lng) {
       
        this.destination = destination;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.categorie = categorie;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
        this.lat = lat;
        this.lng = lng;
    }

    

    public Voyage(String destination, String image, String description, double prix, String date_debut, String date_fin, String categorie, double lat, double lng, String programme, String inclut, String ninclut) {
        this.destination = destination;
        this.image = image;
        this.description = description;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.categorie = categorie;
        this.lat = lat;
        this.lng = lng;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
    }

   //Voyage v1 = new Voyage(idDestination.getText(), idImage.getText(),idDescription.getText(),idPrix.getText(), Date.valueOf(idDatedeb.getValue()), Date.valueOf(idDateFin.getValue()),idCategorie.getText(),idProgramme.getText(),idInclut.getText(),idNinclut.getText(),Integer.parseInt(idLat.getText()),Integer.parseInt(idLong.getText()));

    public Voyage(String destination, String description, double prix, String programme, String inclut, String ninclut) {
        this.destination = destination;
        this.description = description;
        this.prix = prix;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
    }

    public Voyage(String destination, String description, String programme, String inclut, String ninclut) {
        this.destination = destination;
        this.description = description;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
    }

    public Voyage(int id, String destination, String description, String programme, String inclut, String ninclut) {
        this.id = id;
        this.destination = destination;
        this.description = description;
        this.programme = programme;
        this.inclut = inclut;
        this.ninclut = ninclut;
    }

   

   

  

   


 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPromotion_id() {
        return promotion_id;
    }

    public void setPromotion_id(int promotion_id) {
        this.promotion_id = promotion_id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }


    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getInclut() {
        return inclut;
    }

    public void setInclut(String inclut) {
        this.inclut = inclut;
    }

    public String getNinclut() {
        return ninclut;
    }

    public void setNinclut(String ninclut) {
        this.ninclut = ninclut;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Voyage{" + "id=" + id + ", promotion_id=" + promotion_id + ", destination=" + destination + ", image=" + image + ", description=" + description + ", prix=" + prix + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", categorie=" + categorie + ", programme=" + programme + ", inclut=" + inclut + ", ninclut=" + ninclut + ", lat=" + lat + ", lng=" + lng + '}';
    }

   
       
        
    
}
