/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.views.DoughnutChart;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import com.codename1.charts.util.ColorUtil;
import com.mycompany.myapp.services.ServiceTransport;
import static com.mycompany.myapp.services.ServiceTransport.a;
import static com.mycompany.myapp.services.ServiceTransport.b;
import static com.mycompany.myapp.services.ServiceTransport.o;
import static com.mycompany.myapp.services.ServiceTransport.tr;
import static com.mycompany.myapp.services.ServiceTransport.v;





/**
 * Budget demo pie chart.
 */
public class Chart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
    return "Budget chart for several years";
  }

  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The budget per project for several years (doughnut chart)";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
      ServiceTransport st=new ServiceTransport();
    List<double[]> values = new ArrayList<double[]>();
    values.add(new double[] { b,a,tr,v,o });
    //values.add(new double[] { b+1, 0, tr+1, 0, o+1 });
    List<String[]> titles = new ArrayList<String[]>();
    //titles.add(new String[] { "b", "a", "tr", "v", "o" });
    titles.add(new String[] { "bus", "avion", "train", "voiture", "other" });
    int[] colors = new int[] { ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA, ColorUtil.YELLOW, ColorUtil.CYAN };

    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setApplyBackgroundColor(true);
    renderer.setBackgroundColor(ColorUtil.rgb(222, 222, 200));
    renderer.setLabelsColor(ColorUtil.BLACK);
    
    DoughnutChart chart = new DoughnutChart(buildMultipleCategoryDataset("Project budget", titles, values), renderer);
    ChartComponent c = new ChartComponent(chart);
    return wrap("Transport Chart By Type", c);
    
  }

}