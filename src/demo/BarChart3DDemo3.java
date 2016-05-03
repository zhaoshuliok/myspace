// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 2007-1-11 20:04:50
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 

package demo;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarChart3DDemo3 extends ApplicationFrame
{

    public BarChart3DDemo3(String s)
    {
        super(s);
        CategoryDataset categorydataset = createDataset();
        JFreeChart jfreechart = createChart(categorydataset);
        /**
         * 将jfreechart图形生成png的图片，放置到图片服务器上，然后页面上<img src="http://localhost:8082/image_elec/chart.png">
         */
        File file = new File("D:\\tomcat\\apache-tomcat-7.0.52\\webapps\\image_elec\\chart.png");
        try {
			ChartUtilities.saveChartAsPNG(file, jfreechart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartpanel);
    }

    private static CategoryDataset createDataset()
    {
        DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
        defaultcategorydataset.addValue(25D, "Series 1", "Category 1");
        defaultcategorydataset.addValue(34D, "Series 1", "Category 2");
        defaultcategorydataset.addValue(19D, "Series 2", "Category 1");
        defaultcategorydataset.addValue(29D, "Series 2", "Category 2");
        defaultcategorydataset.addValue(41D, "Series 3", "Category 1");
        defaultcategorydataset.addValue(33D, "Series 3", "Category 2");
        return defaultcategorydataset;
    }

    private static JFreeChart createChart(CategoryDataset categorydataset)
    {
        JFreeChart jfreechart = ChartFactory.createBarChart3D("3D Bar Chart Demo", "Category", "Value", categorydataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot categoryplot = jfreechart.getCategoryPlot();
        CategoryAxis categoryaxis = categoryplot.getDomainAxis();
        categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.39269908169872414D));
        CategoryItemRenderer categoryitemrenderer = categoryplot.getRenderer();
        categoryitemrenderer.setItemLabelsVisible(true);
        BarRenderer barrenderer = (BarRenderer)categoryitemrenderer;
        barrenderer.setItemMargin(0.20000000000000001D);
        return jfreechart;
    }

    public static JPanel createDemoPanel()
    {
        JFreeChart jfreechart = createChart(createDataset());
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[])
    {
        BarChart3DDemo3 barchart3ddemo3 = new BarChart3DDemo3("3D Bar Chart Demo 3");
        barchart3ddemo3.pack();
        RefineryUtilities.centerFrameOnScreen(barchart3ddemo3);
        barchart3ddemo3.setVisible(true);
    }
}