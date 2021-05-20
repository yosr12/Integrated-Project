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
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.MyApplication;
import com.mycompany.myapp.entities.Transport;
import com.mycompany.myapp.gui.BaseForm;
import com.mycompany.myapp.services.ServiceTransport;
import java.util.List;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class TransportForm extends BaseForm {

    public TransportForm(Resources res) {
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Transport");
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
        List<Transport> tt = ServiceTransport.getInstance().getAllTransports();
        for (int i = 0; i < tt.size(); i++) {
            add(additem(tt.get(i)));
        }
        Button btnAdd = new Button("Add Transport");
        btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AddTForm(res).show();
            }
        });
        add(btnAdd);


    }
    public Container additem(Transport t) {
        Container holder = new Container(BoxLayout.x());
        Container details = new Container(BoxLayout.y());

        Label l = new Label("Transport id " + t.getId());
        Label desc = new Label(t.getDescription());
        Label dispo = new Label(t.getDisponibilite());
        Label prix = new Label(String.valueOf(t.getPrice()));
        Label type = new Label(t.getType());

        Label ll = new Label("--------------------------------------");
        details.addAll(l, desc, dispo, prix, type, ll);
//        EncodedImage enc = EncodedImage.createFromImage(MyApplication.theme.getImage("load.jpg"), false);
        //Image img = URLImage.createToStorage(enc, "voiture_"+voiture.getMarque(), voiture.getImage(), URLImage.RESIZE_SCALE);
        //ImageViewer voitureimg = new ImageViewer(img);
        holder.addAll(details);
        Button update = new Button("Update");
        update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

               new updateTransport(MyApplication.theme,t).show();

            }
        });
        holder.add(update);

        desc.addPointerReleasedListener((evt) -> {
            if (Dialog.show("inforamtion", "voulez vous supprimer " + t.getType(), "OUI", "NON")) {
                ServiceTransport.getInstance().removeTransport(t.getId());
                Dialog.show("Succes !", "Suppression validé", "Ok", null);
                new TransportForm(MyApplication.theme).show();
            }
        });

        //holder.setLeadComponent(marque);
        return holder;
        
    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
