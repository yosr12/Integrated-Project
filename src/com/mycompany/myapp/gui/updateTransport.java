/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Transport;
import com.mycompany.myapp.services.ServiceTransport;

/**
 *
 * @author user
 */
public class updateTransport extends BaseForm {

    updateTransport instance;

    public updateTransport(Resources res, Transport t) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Update");
        getContentPane().setScrollVisible(false);
        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 4) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 4);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        add(LayeredLayout.encloseIn(
                sl
        ));

        TextField tfDescription = new TextField("", "Description", 20, TextField.ANY);
        tfDescription.setUIID("TextFieldBlack");
        tfDescription.setText(t.getDescription());
        addStringValue("Description", tfDescription);

        TextField tfdispo = new TextField("", "Dispo", 20, TextField.ANY);
        tfdispo.setUIID("TextFieldBlack");
        tfdispo.setText(t.getDisponibilite());
        addStringValue("Dispo", tfdispo);

        TextField tfType = new TextField("", "Type", 20, TextField.ANY);
        tfType.setUIID("TextFieldBlack");
        tfType.setText(t.getType());
        addStringValue("Type", tfType);

        TextField tfPrix = new TextField("", "Price", 20, TextField.DECIMAL);
        tfPrix.setUIID("TextFieldBlack");
        tfPrix.setText(Double.toString(t.getPrice()));
        addStringValue("Price", tfPrix);

        Button btnUpdate = new Button("Update");

        btnUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfDescription.getText().length() == 0) || (tfdispo.getText().length() == 0) || (tfType.getText().length() == 0) || (tfPrix.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {

                    t.setDescription(tfDescription.getText());
                    t.setDisponibilite(tfdispo.getText());
                    t.setPrice(Double.valueOf(tfPrix.getText()));
                    t.setType(tfType.getText());
                    ServiceTransport.getInstance().updateTransport(t);

                    Dialog.show("Success", "Connection accepted", new Command("OK"));
                    
                    new TransportForm(MyApplication.theme).show();

                }

            }
        });

        add(btnUpdate);

        getToolbar()
                .addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt
                    ) {
                        new TransportForm(MyApplication.theme).show();
                    }
                }
                );

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

}
