/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author user
 */
public class home extends Form{
Form current;
    public home() {
        current=this;
        setTitle("Home");
        setLayout(BoxLayout.y());
        add(new Label("Choose an option"));
        Button btnAddTask=new Button("Add Task");
        Button btnListTask =new Button ("List Tasks");
       // btnAddTask.addActionListener(e-> new addTask(current).show());
        //btnListTask.addActionListener(e-> new listTaskForm(current).show());
        
        addAll(btnAddTask,btnListTask);
    }
    
}