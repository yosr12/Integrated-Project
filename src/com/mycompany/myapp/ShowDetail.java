/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.mycompany.entities.Voyage;
import com.mycompany.services.VoyageService;
import static com.mycompany.utils.Statics.BASE_URL;
import java.util.ArrayList;

/**
 *
 * @author House_Info
 */
public class ShowDetail extends Form {

    Component m;
    Form form;

    Form f;
    SpanLabel lb;
    Form f2;

    public ShowDetail(Form current, Voyage v) {

        System.out.println(VoyageService.getInstance().getAllVoyage());

        Container cnt1 = new Container(BoxLayout.y());
        Container cnt2 = new Container(BoxLayout.x());

        Button back = new Button("back");

        SpanLabel SLnom = new SpanLabel("Destination : " + v.getDestination());
        //SpanLabel SLnom = new SpanLabel("Prix :"+e.getPrix());
        SpanLabel SLqut = new SpanLabel("Programme : " + v.getProgramme());

        SpanLabel SLqut1 = new SpanLabel("Inclut : " + v.getInclut());
        SpanLabel SLqut2 = new SpanLabel("Ninclut : " + v.getNinclut());

        EncodedImage enc = EncodedImage.createFromImage(Image.createImage(1600, 800), true);
        String url = BASE_URL + "/uploads/" + v.getImage();
        ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/") + 1, url.length()), url));
        img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
        Button rent = new Button("Reserver");

        cnt1.add(img);
        cnt1.add(SLnom);

        cnt1.add(SLqut);
        cnt1.add(SLqut1);
        cnt1.add(SLqut2);
        cnt1.add(rent);
        cnt1.add(back);
        back.addActionListener((evt) -> {
            new showVoyage(current).show();
        });

        cnt2.add(cnt1);

        add(cnt2);
    }}
//add();

