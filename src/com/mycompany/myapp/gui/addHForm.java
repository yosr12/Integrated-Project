/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.notifications.LocalNotification;
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
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class addHForm extends BaseForm {

    public addHForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Add Hotel");
        getContentPane().setScrollVisible(false);

        tb.addSearchCommand(e -> {
        });

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

        TextField adresse = new TextField("", "Adresse", 20, TextField.ANY);
        adresse.setUIID("TextFieldBlack");
        addStringValue("Adresse", adresse);

        TextField desc = new TextField("", "Description", 20, TextField.ANY);
        desc.setUIID("TextFieldBlack");
        addStringValue("Description", desc);
        Picker datedebut = new Picker();
        datedebut.setUIID("TextFieldBlack");
        addStringValue("date debut", datedebut);

        Picker datefin = new Picker();
        datefin.setUIID("TextFieldBlack");
        addStringValue("date fin", datefin);

        TextField tfPrix = new TextField("", "Price", 20, TextField.DECIMAL);
        tfPrix.setUIID("TextFieldBlack");
        addStringValue("Price", tfPrix);

        Button btnValider = new Button("Add");

        btnValider.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((nom.getText().length() == 0) || (adresse.getText().length() == 0) || (desc.getText().length() == 0) || (tfPrix.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {
                        Hotel t = new Hotel(nom.getText(), adresse.getText(), Integer.parseInt(tfPrix.getText()), desc.getText(), datedebut.getDate(), datefin.getDate());
                        if (ServiceHotel.getInstance().addHotel(t)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                            LocalNotification n = new LocalNotification();
                            n.setId("Hotel");
                            n.setAlertBody("New Hotel is created !");
                            n.setAlertTitle("Break Time!");
                            n.setAlertSound("/notification_sound_bells.mp3"); //file name must begin with notification_sound

                            Display.getInstance().scheduleLocalNotification(
                                    n,
                                    System.currentTimeMillis() + 10 * 1000, // fire date/time
                                    LocalNotification.REPEAT_MINUTE // Whether to repeat and what frequency
                            );
                            new HebergementForm(MyApplication.theme).show();
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

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
