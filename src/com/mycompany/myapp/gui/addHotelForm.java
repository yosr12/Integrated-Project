/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
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
public class addHotelForm extends BaseForm {

    addHotelForm instance;

    public addHotelForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add");
        getContentPane().setScrollVisible(false);

        TextField tfLibelle = new TextField("", "Nom");
        TextField tfAdresse = new TextField("", "Adresse");
        TextField tfDescription = new TextField("", "Description");
        Picker datedebut = new Picker();
        Picker datefin = new Picker();
        TextField tfPrix = new TextField("", "Prix", 20, TextField.DECIMAL);

        

        Image img = res.getImage("profile-background.jpg");

        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label();
        Label twitter = new Label();
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(
                sl
                
        ));

        addAll(tfLibelle, tfAdresse, tfDescription, datedebut, datefin, tfPrix);
        Button btnValider = new Button("Valider");

        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfLibelle.getText().length() == 0) || (tfAdresse.getText().length() == 0) || (tfDescription.getText().length() == 0) || (tfPrix.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Hotel t = new Hotel(tfLibelle.getText(), tfAdresse.getText(), Integer.parseInt(tfPrix.getText()), tfDescription.getText(), datedebut.getDate(), datefin.getDate());
                        if (ServiceHotel.getInstance().addHotel(t)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        System.out.println(e.getStackTrace());
                    }

                }

            }
        });

        add(btnValider);

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

}
