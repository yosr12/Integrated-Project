/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;


/**
 *
 * @author Abirn
 */
public class Reclamation {

    private int id;
    private String sujet;
    private String description;
    private String date;

    public Reclamation() {
    }

    public Reclamation(String sujet, String description, String date) {
        this.sujet = sujet;
        this.description = description;
        this.date = date;
    }

    public Reclamation(int id, String sujet, String description, String date) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", sujet=" + sujet + ", description=" + description + ", date=" + date + '}';
    }

    
}
