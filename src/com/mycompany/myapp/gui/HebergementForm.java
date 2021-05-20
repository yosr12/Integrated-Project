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
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.BaseForm;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Hotel;
import com.mycompany.myapp.services.ServiceHotel;
import java.util.List;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class HebergementForm extends BaseForm {
    HebergementForm instance;

    public HebergementForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Hebergement");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");

        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label();
        Label twitter = new Label();
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                facebook,
                                twitter
                        )
                )
        ));
        List<Hotel> liste = ServiceHotel.getInstance().getAllHotels();
        for (int i = 0; i < liste.size(); i++) {
            add(additem(liste.get(i)));
        }
        Button btnAdd = new Button("Add hotel");
        btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                new addHForm(res).show();
            }
        });
        add(btnAdd);
        Button btnrech = new Button("rech hotel");
        btnrech.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                new recherche(res).show();
            }
        });
        add(btnrech);

        

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
    public Container additem(Hotel t) {
        Container holder = new Container(BoxLayout.x());
        Container details = new Container(BoxLayout.y());

        Label l = new Label("Hotel id " + t.getId());
        Label nom = new Label(t.getNom());
        Label adr = new Label(t.getAdresse());
        Label prix = new Label(String.valueOf(t.getPrice()));
        Label desc = new Label(t.getDescription());
        Label dd = new Label(t.getDatedebut().toString());
        Label df = new Label(t.getDatefin().toString());
        //Label prix = new Label(""+voiture.getPrix());

        Label ll = new Label("--------------------------------------");
//        EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("load.jpg"), false);
        //Image img = URLImage.createToStorage(enc, "voiture_"+voiture.getMarque(), voiture.getImage(), URLImage.RESIZE_SCALE);
        //ImageViewer voitureimg = new ImageViewer(img);

        Button update = new Button("Update");
        update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                new updateHotel(MyApplication.theme,t).show();

            }
        });
        details.addAll(l, nom, adr, prix, desc, dd, df,update,ll);

        holder.addAll(details);

        nom.addPointerReleasedListener((evt) -> {
            if (Dialog.show("inforamtion", "voulez vous supprimer " + t.getNom(), "OUI", "NON")) {
                ServiceHotel.getInstance().removeHotel(t.getId());
                Dialog.show("Succes !", "Suppression validé", "Ok", null);
                new HebergementForm(MyApplication.theme).show();
            }
        });
        return holder;
    }
}
