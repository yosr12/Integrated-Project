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
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Hotel;
import com.mycompany.myapp.services.ServiceHotel;

/**
 *
 * @author user
 */
public class updateHotel extends BaseForm {

    updateHotel instance;

    public updateHotel(Resources res, Hotel h) {
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

        TextField nom = new TextField("", "Nom", 20, TextField.ANY);
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);
        nom.setText(h.getNom());

        TextField adresse = new TextField("", "Adresse", 20, TextField.ANY);
        adresse.setUIID("TextFieldBlack");
        addStringValue("Adresse", adresse);
        adresse.setText(h.getAdresse());

        TextField desc = new TextField("", "Description", 20, TextField.ANY);
        desc.setUIID("TextFieldBlack");
        addStringValue("Description", desc);
        desc.setText(h.getDescription());

        Picker datedebut = new Picker();
        datedebut.setUIID("TextFieldBlack");
        addStringValue("date debut", datedebut);
        datedebut.setDate(h.getDatedebut());

        Picker datefin = new Picker();
        datefin.setUIID("TextFieldBlack");
        addStringValue("date fin", datefin);
        datefin.setDate(h.getDatefin());

        TextField tfPrix = new TextField("", "Price", 20, TextField.DECIMAL);
        tfPrix.setUIID("TextFieldBlack");
        addStringValue("Price", tfPrix);
        tfPrix.setText(Double.toString(h.getPrice()));

        Button btnUpdate = new Button("Update");

        btnUpdate.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0) || (adresse.getText().length() == 0) || (desc.getText().length() == 0) || (tfPrix.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {

                    h.setNom(nom.getText());
                    h.setAdresse(adresse.getText());
                    h.setPrice(Double.valueOf(tfPrix.getText()));
                    h.setDescription(desc.getText());

                    h.setDatedebut(datedebut.getDate());
                    h.setDatefin(datefin.getDate());
                    ServiceHotel.getInstance().updateHotel(h);
                    Dialog.show("Success", "Connection accepted", new Command("OK"));
                   
                    new HebergementForm(MyApplication.theme).show();

                }

            }
        });

        add(btnUpdate);

        getToolbar()
                .addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt
                    ) {
                        new HebergementForm(MyApplication.theme).show();
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
