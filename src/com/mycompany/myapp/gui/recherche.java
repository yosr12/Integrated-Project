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
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
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
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.BaseForm;
import com.mycompany.myapp.MyApplication;
import static com.mycompany.myapp.MyApplication.theme;
import com.mycompany.myapp.entities.Hotel;
import com.mycompany.myapp.services.ServiceHotel;
import java.util.ArrayList;
import java.util.List;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class recherche extends BaseForm {

    HebergementForm instance;

    public recherche(Resources res) {
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
        TextComponent recherche = new TextComponent().label("Recherche");
        Button btnc = new Button("ok");
        addAll(recherche, btnc);
        theme = UIManager.initFirstTheme("/theme");
        btnc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    Form rech = new Form("Hotels", BoxLayout.y());

                    if (recherche.getText().length() > 0 || ServiceHotel.getInstance().getHotel(recherche.getText()).size() > 0) {
                        ArrayList<Hotel> array = ServiceHotel.getInstance().getHotel(recherche.getText());
                        for (Hotel s : array) {

                            System.out.print(s);
                            Container cnt1 = new Container(BoxLayout.y());
                            Label lbdes = new Label("Nom :" + s.getNom());
                            Label lbtadr = new Label("Adresse :" + s.getAdresse());
                            Label lbtDesc = new Label("Description :" + s.getDescription());
                            Label lbprice = new Label("Prix :" + s.getPrice());
                            Label lbdd = new Label("Date début :" + s.getDatedebut());
                            Label lbdf = new Label("Date fin :" + s.getDatefin());
                            cnt1.add(lbdes);
                            cnt1.add(lbtadr);
                            cnt1.add(lbtDesc);
                            cnt1.add(lbprice);
                            cnt1.add(lbdd);
                            cnt1.add(lbdf);

                            Toolbar tb = getToolbar();

                            Container cnt2 = new Container(BoxLayout.x());

                            cnt2.add(cnt1);
                            rech.add(cnt2);
                            tb.addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent evt
                                ) {
                                    new TransportForm(MyApplication.theme).show();
                                }
                            }
                            );
                            rech.setToolbar(tb);
                            rech.show();
//           rech.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                            //   , e1-> new ListActForm(current).show());
                        }
                    } else {
                        Dialog.show("Vide", "Acune Suggestion de ce type", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }

            }

        });

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

                new updateHotel(MyApplication.theme, t).show();

            }
        });
        details.addAll(l, nom, adr, prix, desc, dd, df, update, ll);

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
