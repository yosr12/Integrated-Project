/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Voyage;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;

/**
 *
 * @author House_Info
 */
public class VoyageService {
 public ArrayList<Voyage> Voyages;
    public static VoyageService instance=null;
    public boolean resultOK;
    private final ConnectionRequest req;
    
        public static VoyageService getInstance() {
        if (instance == null) {
            instance = new VoyageService();
        }
        return instance;
    }
    
    
    public VoyageService() {
         req = new ConnectionRequest();
    }
     public ArrayList<Voyage> parseEvents(String jsonText) {
        try {
            Voyages=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> associationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));            
            java.util.List<Map<String,Object>> list = (java.util.List<Map<String,Object>>)associationsListJson.get("root");
            for(Map<String,Object> obj : list){
                Voyage v = new Voyage();              
                float id = Float.parseFloat(obj.get("id").toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                v.setId((int)id);               
                v.setDestination(obj.get("destination").toString());
                v.setDescription(obj.get("description").toString());
//                v.setImage(obj.get("image").toString());
                v.setProgramme(obj.get("programme").toString());
                v.setInclut(obj.get("inclut").toString());
                v.setNinclut(obj.get("ninclut").toString());
                v.setImage(obj.get("image").toString());
                 v.setCategorie(obj.get("categorie").toString());
//                  v.getPrix(prix);
                ;
                 float prix = Float.parseFloat(obj.get("prix").toString());
                 v.setPrix(prix);
                 v.setDate_debut(obj.get("dateDebut").toString());
                   v.setDate_fin(obj.get("dateFin").toString());
               
                
                //float id = Float.parseFloat(obj.get("id").toString());
                //m.setId((int)id)
              //   m.setNbrmatrres(Integer.parseInt(obj.get("nbrmatrres").toString()));
               //  m.setQuantity(Integer.parseInt(obj.get("quantity").toString()));
                 
                Voyages.add(v);
                
                
            }
            
            
        } catch (IOException ex) {

        }
        return Voyages;
     }
     
    
    public ArrayList<Voyage> getAllVoyage(){
        String url = Statics.BASE_URL+"/voyage/listjson/fatma";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Voyages = parseEvents(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Voyages;
    }
    
    
    
    public ArrayList<Voyage> getSug(String s){
        String url = Statics.BASE_URL+"/voyage/Recherche/"+s;
        req.setUrl(url);
        req.setPost(false);
       
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String reponsejson=new String(req.getResponseData());
               
               Voyages = parseEvents(reponsejson);
               
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Voyages;
    }
    
    
    
    
    

}
