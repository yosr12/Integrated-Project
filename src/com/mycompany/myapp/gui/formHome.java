/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.MyApplication;

/**
 *
 * @author user
 */
public class formHome extends Form{
    
    public formHome() {
         setLayout(BoxLayout.yCenter());
         setTitle("HOME");
         add(new Label("bienvenue "));
         getToolbar().addCommandToSideMenu("home",null,(evt) -> {
            
         });
         
         getToolbar().addCommandToSideMenu("Liste Hotels",null,(evt) -> {
             new HebergementForm(MyApplication.theme).show();
         });
         getToolbar().addCommandToSideMenu("Liste Transports",null,(evt) -> {
             new TransportForm(MyApplication.theme).show();
             
         });
        
         
        
    }
    
    
    
}
