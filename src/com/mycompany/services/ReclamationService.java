///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.services;
//
//import com.codename1.io.CharArrayReader;
//import com.codename1.io.ConnectionRequest;
//import com.codename1.io.JSONParser;
//import com.codename1.io.NetworkEvent;
//import com.codename1.io.NetworkManager;
//import com.codename1.l10n.SimpleDateFormat;
//import com.codename1.ui.Dialog;
//import com.codename1.ui.events.ActionListener;
//import com.mycompany.entities.Reclamation;
//import com.mycompany.myapp.SignUpForm;
//import com.mycompany.utils.Statics;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// *
// * @author Abirn
// */
//public class ReclamationService {
//
//    //Singleton
//    public static ReclamationService instance = null;
//    public static boolean resultOk = true;
//    String json;
//
//    //Initialisation du connection request
//    private ConnectionRequest req;
//
//    public static ReclamationService getInstance() {
//
//        if (instance == null) {
//            instance = new ReclamationService();
//        }
//        return instance;
//    }
//
//    public ReclamationService() {
//        req = new ConnectionRequest();
//    }
//
//    //ADD
//    public void addReclamation(Reclamation reclamation) {
//
//        String url = Statics.BASE_URL + "/AddReclamJSON?sujet=" + reclamation.getSujet() + "&description=" + reclamation.getSujet();
//
//        req.setUrl(url);
//
//        //Execution de l'URL 
//        req.addResponseListener((e) -> {
//
//            String str = new String(req.getResponseData());  //Réponse JSON
//
//            System.out.println("data =>" + str);
//        }
//        );
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//    }
//
//    //List
//    public ArrayList<Reclamation> ListReclamation() {
//
//        ArrayList<Reclamation> result = new ArrayList<>();
//
//        String url = Statics.BASE_URL + "/listReclamJSON";
//        req.setUrl(url);
//
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//
//                JSONParser jsonp;
//                jsonp = new JSONParser();
//
//                try {
//
//                    Map<String, Object> mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
//                    List<Map<String, Object>> listOfMaps = (List<Map<String, Object>>) mapReclamations.get("root");
//
//                    for (Map<String, Object> obj : listOfMaps) {
//
//                        Reclamation re = new Reclamation();
//
//                        float id = Float.parseFloat(obj.get("id").toString());
//                        String sujet = obj.get("sujet").toString();
//                        String description = obj.get("description").toString();
//
//                        re.setId((int) id);
//                        re.setSujet(sujet);
//                        re.setDescription(description);
//                        re.setDate(obj.get("date").toString());
//
//                        //Date
////                        String DateConverter = obj.get("date").toString().substring(obj.get("date").toString().indexOf("timestamp") + 10, obj.get("obj").toString().lastIndexOf("}"));
////                        Date currentTime = new Date(Double.valueOf(DateConverter).longValue() * 1000);
////
////                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
////                        String dateString = formatter.format(currentTime);
////                        re.setDate(dateString);
//                        //Insertion du data dans l'ArrayList
//                        result.add(re);
//                    }
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//
//        return result;
//    }
//
//    //Show
////    public Reclamation ShowReclamation(int id, Reclamation reclamation) {
////
////        String url = Statics.BASE_URL + "/showReclamJSON/" + id;
////
////        req.setUrl(url);
////
////        String str = new String(req.getResponseData());
////        req.addResponseListener((e) -> {
////
////            JSONParser jsonp = new JSONParser();
////
////            try {
////
////                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(json.toCharArray()));
////
////                reclamation.setSujet(obj.get("sujet").toString());
////                reclamation.setDescription(obj.get("description").toString());
////
////            } catch (IOException ex) {
////                System.out.println("Error related to SQL : (" + ex.getMessage());
////            }
////            System.out.println("data=>" + str);
////        }
////        );
////
////        //Aprés l'exécution de l'URL on attend la réponse du serveur 
////        NetworkManager.getInstance().addToQueueAndWait(req);
////
////        return reclamation;
////    }
//
//    //Delete
//    public boolean deleteReclamation(int id) {
//        String url = Statics.BASE_URL + "/RemoveReclamJSON/" + id;
//
//        req.setUrl(url);
//
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//
//                req.removeResponseCodeListener(this);
//            }
//        });
//
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//
//        return resultOk;
//    }
//
//    //Update
//    public boolean UpdateReclamation(Reclamation reclamation) {
//
//        String url = Statics.BASE_URL + "/UpdateReclamJSON?id="+reclamation.getId()+"&sujet=" + reclamation.getSujet() + "&description=" + reclamation.getDescription()+ "&date=" + reclamation.getDate();
//
//        req.setUrl(url);
//
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOk = req.getResponseCode() == 200;
//                req.removeResponseListener(this);
//
//            }
//        });
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//
//        return resultOk;
//    }
//
//}
