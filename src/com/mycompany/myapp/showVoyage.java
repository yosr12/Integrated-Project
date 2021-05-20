/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;

import com.codename1.components.SpanLabel;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Slider;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextComponent;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Voyage;
import com.mycompany.services.VoyageService;
import static com.mycompany.utils.Statics.BASE_URL;
import java.util.ArrayList;

/**
 *
 * @author House_Info
 */
public class showVoyage extends Form {

    Component m;
    Form form;

    Form f;
    SpanLabel lb;
    Form f2;
    Form rech;
    private Resources theme;

    public showVoyage(Form current) {

        setTitle("List of Voyages");

        Button stat = new Button("Statistiques");
        stat.addActionListener((evt) -> {
            StatistiquesForm f = new StatistiquesForm(current);
            f.show();
        });
        add(stat);
        TextComponent recherche = new TextComponent().label("Recherche");

        Button btnc = new Button("ok");
        addAll(recherche, btnc);

        theme = UIManager.initFirstTheme("/theme");

        btnc.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                try {

                    rech = new Form("Les voyages", BoxLayout.y());

                    if (recherche.getText().length() > 0 || VoyageService.getInstance().getSug(recherche.getText()).size() > 0) {
                        ArrayList<Voyage> array = VoyageService.getInstance().getSug(recherche.getText());

                        for (Voyage v : array) {

                            System.out.print(v);
                            Container cnt1 = new Container(BoxLayout.y());
                            Button retour = new Button("Back");

                            Label lbdes = new Label("Destination : " + v.getDestination());
                            Label lbprix = new Label("Prix : " + v.getPrix() + " DT");
                            Label lbdesc = new Label("Description : " + v.getDescription());
                            Label lbdd = new Label("Date Debut : " + v.getDate_debut());
                            Label lbdf = new Label("Date Fin : " + v.getDate_fin());

                            EncodedImage enc = EncodedImage.createFromImage(Image.createImage(1530, 800), true);
                            String url = BASE_URL + "/uploads/" + v.getImage();
                            ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/") + 1, url.length()), url));
                            img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
                            Button btn = new Button("Voir");
                            cnt1.add(img);
                            cnt1.add(lbdes);
                            cnt1.add(lbprix);
                            cnt1.add(lbdesc);
                            cnt1.add(lbdd);
                            cnt1.add(lbdf);

                            cnt1.add(btn);

                            Container cnt2 = new Container(BoxLayout.x());
                            Label lbimg = new Label(theme.getImage(v.getDestination()));

                            cnt2.add(lbimg);
                            cnt2.add(cnt1);
                            rech.add(cnt2);
                            rech.show();
                            Button back = new Button("Back");
                            btn.addActionListener((e) -> {
                                ShowDetail f = new ShowDetail(rech, v);
                                f.show();
                            });

                        }

                    } else {
                        Dialog.show("Vide", "Acune Suggestion ", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                }

            }

        });

        theme = UIManager.initFirstTheme("/theme");

        System.out.println(VoyageService.getInstance().getAllVoyage());
        ArrayList<Voyage> Voyages = new ArrayList();
        Voyages = VoyageService.getInstance().getAllVoyage();
        for (Voyage v : Voyages) {

            Container cnt1 = new Container(BoxLayout.y());
            Container cnt2 = new Container(BoxLayout.x());

            SpanLabel SLnom = new SpanLabel("Destination : " + v.getDestination());
            SpanLabel SLprix = new SpanLabel("Prix :" + v.getPrix() + " DT");
            SpanLabel SLdesc = new SpanLabel("Description : " + v.getDescription());
            SpanLabel SLdd = new SpanLabel("Date début : " + v.getDate_debut());
            SpanLabel SLdf = new SpanLabel("Date fin : " + v.getDate_fin());

            EncodedImage enc = EncodedImage.createFromImage(Image.createImage(1530, 800), true);
            String url = BASE_URL + "/uploads/" + v.getImage();
            ImageViewer img = new ImageViewer(URLImage.createToStorage(enc, url.substring(url.lastIndexOf("/") + 1, url.length()), url));
            img.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FIT);
            Button rent = new Button("Voir plus");
            cnt1.add(img);
            cnt1.add(SLnom);
            cnt1.add(SLprix);
            cnt1.add(SLdesc);
            cnt1.add(SLdd);
            cnt1.add(SLdf);

            cnt1.add(rent);
            rent.addActionListener((evt) -> {
                new ShowDetail(current, v).show();
            });

            cnt2.add(cnt1);

            add(cnt2);
        }
    }
    
    

}
