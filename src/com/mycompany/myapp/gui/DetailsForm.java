/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.entities.Hotel;

/**
 *
 * @author user
 */
public class DetailsForm extends Form{
    
     public DetailsForm(Hotel h, Form previous) {
        setTitle("Details of Hotel "+h.getNom());
        setLayout(new FlowLayout(CENTER));
        Container c= new Container(BoxLayout.y());
        c.add(new Label("Nom="+h.getNom()));
        c.add(new Label("Adresse="+h.getAdresse()));
        c.add(new Label("Description="+h.getDescription()));
        c.add(new Label("Date debut="+h.getDatedebut()));
        c.add(new Label("Date fin="+h.getDatefin()));
        c.add(new Label("Prix= "+h.getPrice()+" DT"));
        
        this.add(c);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                previous.showBack();
            }
        });
    }
    
    
}
