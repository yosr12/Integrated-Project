/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Hotel;
import com.mycompany.myapp.utils.statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author user
 */
public class ServiceHotel {

   public ArrayList<Hotel> hotels;
    
    public static ServiceHotel instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public NetworkManager nm;

    private ServiceHotel() {
         req = new ConnectionRequest();
    }

    public static ServiceHotel getInstance() {
        if (instance == null) {
            instance = new ServiceHotel();
        }
        return instance;
    }

    public boolean addHotel(Hotel h) {
        String url = statics.BASE_URL + "/AddHotel/?nom=" + h.getNom() + "&adresse=" + h.getAdresse()+ "&price=" + h.getPrice()+ "&description=" + h.getDescription()+ "&datedebut=" + h.getDatedebut()+ "&datefin=" + h.getDatefin(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     public boolean updateHotel(Hotel h) {
        String url = statics.BASE_URL + "/UpdateHotelJSON/"+h.getId()+"?nom=" + h.getNom() + "&adresse=" + h.getAdresse()+"&description="+h.getDescription()+"&price=" + h.getPrice()+ "&datedebut=" +h.getDatedebut()+"&datefin="+h.getDatefin(); //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    public boolean removeHotel(int id) {
        String url = statics.BASE_URL + "/RemoveHotel/"+id; //création de l'URL
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); 
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }


    public ArrayList<Hotel> parsehotels(String jsonText) throws ParseException{
        try {
            hotels=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Hotel t = new Hotel();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setNom(obj.get("nom").toString());
                t.setAdresse(obj.get("adresse").toString());
                t.setPrice((int)Float.parseFloat(obj.get("price").toString()));                
                t.setDescription(obj.get("description").toString());
                String datedebut = (String)obj.get("datedebut").toString();
                SimpleDateFormat ddf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'mm:ss");
                Date dd = ddf.parse(datedebut);
                t.setDatedebut(dd);
                String datefin = (String)obj.get("datefin").toString();
                SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+'mm:ss");
                Date df = dff.parse(datedebut);
                t.setDatefin(df);

                hotels.add(t);
            }
            
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            
        }
         
        return hotels;
    }
    
    public ArrayList<Hotel> getAllHotels(){
        String url = statics.BASE_URL+"/listHotelJSON";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    hotels = parsehotels(new String(req.getResponseData()));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
                req.removeResponseListener(this);
            }
        });
        nm.getInstance().addToQueueAndWait(req);
        return hotels;
    }
    public ArrayList<Hotel> getHotel(String s){
        String url = statics.BASE_URL+"/RechercheHotelJson/"+s;
        req.setUrl(url);
        req.setPost(false);
       
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String reponsejson=new String(req.getResponseData());
               
                try {
                    hotels = parsehotels(reponsejson);
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
               
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return hotels;
    }
}
