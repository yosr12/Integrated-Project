///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.myapp;
//
//import com.codename1.components.FloatingHint;
//import com.codename1.ui.Button;
//import com.codename1.ui.Container;
//import com.codename1.ui.Form;
//import com.codename1.ui.Label;
//import com.codename1.ui.TextField;
//import com.codename1.ui.Toolbar;
//import com.codename1.ui.layouts.BoxLayout;
//import com.codename1.ui.util.Resources;
//import com.mycompany.entities.Reclamation;
//import com.mycompany.services.ReclamationService;
//
///**
// *
// * @author Abirn
// */
//public class UpdateReclamForm extends BaseForm {
//
//    Form current;
//
//    public UpdateReclamForm(Resources res, Reclamation r) {
//
//        super("Newsfeed", BoxLayout.y());
//        Toolbar tb = new Toolbar(true);
//        current = this;
//        setToolbar(tb);
//        getTitleArea().setUIID("Container");
//        setTitle("Update réclamation");
//        getContentPane().setScrollVisible(false);
//
//        super.addSideMenu(res);
//
//        TextField sujet = new TextField(r.getSujet(), "Sujet", 20, TextField.ANY);
//        TextField description = new TextField(r.getDescription(), "Description", 20, TextField.ANY);
//        TextField date = new TextField(r.getDate(), "Date", 20, TextField.ANY);
//
//        sujet.setUIID("NewsTopLine");
//        description.setUIID("NewsTopLine");
//        date.setUIID("NewsTopLine");
//
//        sujet.setSingleLineTextArea(true);
//        description.setSingleLineTextArea(true);
//        date.setSingleLineTextArea(true);
//
//        Button btnModifier = new Button("Modifier");
//        btnModifier.setUIID("Button");
//
//        //Event onClick btnModif
//        btnModifier.addPointerPressedListener((l) -> {
//            r.setSujet(sujet.getText());
//            r.setDescription(description.getText());
//            r.setDate(date.getText());
//
//   
//
//        //Appel
//        if (ReclamationService.getInstance().UpdateReclamation(r)) {
//            new ListReclamForm(res).show();
//        }
//             });
//        Button btnAnnuler = new Button("Annuler");
//        btnAnnuler.addActionListener((e) -> {
//            new ListReclamForm(res).show();
//        });
//
//        Label l2 = new Label("");
//        Label l3 = new Label("");
//        Label l4 = new Label("");
//        Label l5 = new Label("");
//
//        Label l1 = new Label();
//        Container content = BoxLayout.encloseY(
//                l1, l2,
//                new FloatingHint(sujet),
//                createLineSeparator(),
//                new FloatingHint(description),
//                createLineSeparator(),
//                new FloatingHint(date),
//                createLineSeparator(),
//                l4, l5,
//                btnModifier,
//                btnAnnuler
//        );
//        add(content);
//        show();
//    }
//}
