///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.services;
//
//
//import com.codename1.io.CharArrayReader;
//import com.codename1.io.ConnectionRequest;
//import com.codename1.io.JSONParser;
//import com.codename1.io.NetworkEvent;
//import com.codename1.io.NetworkManager;
//import com.codename1.ui.ComboBox;
//import com.codename1.ui.Dialog;
//import com.codename1.ui.TextField;
//import com.codename1.ui.events.ActionListener;
//import com.codename1.ui.util.Resources;
//import com.mycompany.myapp.AddReclamForm;
//import com.mycompany.myapp.NewsfeedForm;
//import com.mycompany.myapp.SignUpForm;
//import com.mycompany.utils.Statics;
//import java.util.Map;
//
//import java.util.Vector;
//
///**
// *
// * @author Abirn
// */
//public class UserService {
//
//    //Singleton
//    public static UserService instance = null;
//    public static boolean resultOk = true;
//    String json ;
//
//    //Initialisation du connection request
//    private ConnectionRequest req;
//
//    public static UserService getInstance() {
//
//        if (instance == null) {
//            instance = new UserService();
//        }
//        return instance;
//    }
//
//    public UserService() {
//        req = new ConnectionRequest();
//    }
//
//    //Register
//    public void signup(TextField username, TextField userfname, TextField num, TextField email, TextField password, TextField confirmPassword, ComboBox gender, Resources res) {
//
//        String url = Statics.BASE_URL+"/AddJSON?name=" + username.getText().toString() + "&fname=" + userfname.getText().toString()
//                + "&gender=" + gender.getSelectedItem().toString() + "&num=" + Integer.parseInt(num.getText())
//                + "&email=" + email.getText().toString() + "&password=" + password.getText().toString();
//
//        req.setUrl(url);
//
//        // Controle de saisie
//        if (username.getText().equals(" ") && userfname.getText().equals(" ") && gender.getSelectedItem().equals(" ") && num.getText().equals(" ") && email.getText().equals(" ") && password.getText().equals(" ")) {
//
//            Dialog.show("ERREUR", "Veuillez remplir les champs", "OK", null);
//        }
//
//        //Execution de l'URL 
//        req.addResponseListener((e) -> {
//
//            byte[] data = (byte[]) e.getMetaData();
//            String responsableData = new String(data);
//
//            System.out.println("data =>" + responsableData);
//        }
//        );
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//    }
//
//    //Login
//    public void signin(TextField email, TextField password, Resources rs) {
//
//        String url = Statics.BASE_URL+"/LoginJSON?email=" + email.getText().toString()
//                + "&password=" + password.getText().toString();
//        
//        req = new ConnectionRequest(url,false);//l'URL pas encore envoyer au serveur
//        
//        req.setUrl(url);
//        req.addResponseListener((e) -> {
//            
//            JSONParser j = new JSONParser();
//            String json = new String (req.getResponseData())+ "";
//            
//            try{
//                
//            if (json.equals("failed")){
//                Dialog.show("Echec d'authentification", "Veuillez vérifier votre MAIL ou PASSWORD", "OK", null);
//            }
//            else{
//                System.out.println("data=>"+json);
//                Map <String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));
//                
//                if (user.size() > 0)//User found
//                 new AddReclamForm(rs).show();  
//            }
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        });
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//    }
//    
//     //Forfot Password
//    public String getPasswordByEmail(String email,Resources rs) {
//
//        String url = Statics.BASE_URL+"/getPasswordByEmail?email=" + email ;
//        
//        req = new ConnectionRequest(url,false);//l'URL pas encore envoyer au serveur
//        
//        req.setUrl(url);
//        req.addResponseListener((e) -> {
//            
//            JSONParser j = new JSONParser();
//            json = new String (req.getResponseData())+ "";
//            
//            try{
//                
//                System.out.println("data=>"+json);
//                Map <String,Object> password = j.parseJSON(new CharArrayReader(json.toCharArray()));
//                
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        });
//        
//        //Aprés l'exécution de l'URL on attend la réponse du serveur 
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return json;
//    }
//
//
//}
