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
import com.mycompany.myapp.entities.Transport;
import com.mycompany.myapp.utils.statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class ServiceTransport {
    public static ArrayList<Transport> transports;
    
    public static ServiceTransport instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public static ArrayList <Transport> nbStat;
    public static int b=0;
    public static int a=0;
    public static int tr=0;
    public static int v=0;
    public static int o=0;
    

    public ServiceTransport() {
         req = new ConnectionRequest();
         
         for(int i=0;i<this.getAllTransports().size();i++){
             if(transports.get(i).getType().toLowerCase().equals("bus"))
                 b++;
             else if(transports.get(i).getType().toLowerCase().equals("avion"))
                 a++;
             else if(transports.get(i).getType().toLowerCase().equals("train"))
                 tr++;
             else if(transports.get(i).getType().toLowerCase().equals("voiture"))
                 v++;
             else
                 o++;
         }
    }

    public static ServiceTransport getInstance() {
        if (instance == null) {
            instance = new ServiceTransport();
        }
        return instance;
    }

    public boolean addTransport(Transport t) {
        String url = statics.BASE_URL + "/AddTransport/?description=" + t.getDescription() + "&disponibilite=" + t.getDisponibilite()+ "&price=" + t.getPrice()+ "&type=" + t.getType(); //création de l'URL
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

    public ArrayList<Transport> parseTransports(String jsonText){
        try {
            transports=new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json
            Map<String,Object> TransportsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)TransportsListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Transport t = new Transport();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setDescription(obj.get("description").toString());
                t.setDisponibilite(obj.get("disponibilite").toString());
                t.setPrice((int)Float.parseFloat(obj.get("price").toString()));                
                t.setType(obj.get("type").toString());

                transports.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return transports;
    }
    public boolean updateTransport(Transport t) {
        String url = statics.BASE_URL + "/UpdateTransportJSON/"+t.getId()+"?description=" + t.getDescription() + "&disponibilite=" + t.getDisponibilite()+ "&price=" + t.getPrice()+ "&type=" + t.getType(); //création de l'URL
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
    public boolean removeTransport(int id) {
        String url = statics.BASE_URL + "/RemoveTransportJSON/"+id; //création de l'URL
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

    
    public ArrayList<Transport> getAllTransports(){
        String url = statics.BASE_URL+"/listTransport";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                transports = parseTransports(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        nbStat=transports;
        return transports;
    }
    
}
