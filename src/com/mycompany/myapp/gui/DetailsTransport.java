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
import com.mycompany.myapp.entities.Transport;

/**
 *
 * @author user
 */
public class DetailsTransport extends Form{
    
     public DetailsTransport(Transport t, Form previous) {
        setTitle("Details of Transport ");
        setLayout(new FlowLayout(CENTER));
        Container c= new Container(BoxLayout.y());
        c.add(new Label("Description="+t.getDescription()));
        c.add(new Label("Disponibilite="+t.getDisponibilite()));
        c.add(new Label("Type="+t.getType()));
        c.add(new Label("Prix= "+t.getPrice()+" DT"));
        
        this.add(c);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK,new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                previous.showBack();
            }
        });
    }
    
    
}
