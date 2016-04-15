package com.yodlee.analyser;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.SlidingCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

//import experimental.sliding_category.MyDemoPanel;

/**
 * A demo for the {@link SlidingCategoryDataset}.  We use a JScrollBar to
 * control the subset of a dataset that is made visible via the
 * {@link SlidingCategoryDataset} - this simulates scrolling by redrawing
 * the chart with different bars visible.  It isn't ideal, because there is
 * no reliable way to align the JScrollBar with the data area on the chart,
 * but it is likely to be good enough for many applications.
 */
public class sliding_category extends ApplicationFrame {
	

    static class MyDemoPanel extends DemoPanel implements ChangeListener {

        /** A scrollbar to update the dataset value. */
        JScrollBar scroller;

        /** The dataset. */
        SlidingCategoryDataset dataset;
        public static  int column_val;

        /**
         * Creates a new demo panel.
         */
        public MyDemoPanel(DefaultCategoryDataset datasets,String cl) {
            super(new BorderLayout());
           
            int max_column=0;
            JFreeChart chart;
            if(datasets.getColumnCount()>=20)max_column=20;
            else max_column=datasets.getColumnCount();
            if(cl.equals("http")){
            this.dataset = new SlidingCategoryDataset(createDataset(), 0,max_column);
             chart = createChart(this.dataset);
            }
            else
            	{
            	this.dataset = new SlidingCategoryDataset(createDataset_nothttp(), 0,max_column);
            	 chart = createChart_nothttp(this.dataset);
            	}
            // get data for diagrams
            
            
            
            addChart(chart);
            ChartPanel cp1 = new ChartPanel(chart);
            cp1.setPreferredSize(new Dimension(1000,400));
            cp1.setDismissDelay(40000);
            System.out.println("Col "+datasets.getColumnCount());
            this.scroller = new JScrollBar(SwingConstants.VERTICAL, 0, max_column, 0,
            		datasets.getColumnCount());
            add(cp1);
            this.scroller.getModel().addChangeListener(this);
            JPanel scrollPanel = new JPanel(new BorderLayout());
            scrollPanel.add(this.scroller);
            scrollPanel.setBorder(BorderFactory.createEmptyBorder(66, 2, 2, 2));
            scrollPanel.setBackground(Color.white);
            add(scrollPanel, BorderLayout.EAST);
        }

        /**
         * Returns a sample dataset.
         *
         * @return The dataset.
         */
        private static CategoryDataset createDataset() {
            /*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i <25; i++) {
                dataset.addValue(Math.random() * 100.0, "S1", "Series " + i);
            }*/
            return date_ex.dataset;
        }
        private static CategoryDataset createDataset_nothttp() {
            /*DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i <25; i++) {
                dataset.addValue(Math.random() * 100.0, "S1", "Series " + i);
            }*/
            return date_ex_not_http.dataset;
        }

        /**
         * Creates a sample chart.
         *
         * @param dataset  the dataset.
         *
         * @return The chart.
         */
        private static JFreeChart createChart(CategoryDataset dataset) {

            // create the chart...
        	JFreeChart chart = ChartFactory.createStackedBarChart(
    	            null,
    	            "THREAD NAME",                  // domain axis label
    	            "THREAD STATES time duration --->",  // range axis label
    	            dataset,                     // data
    	            PlotOrientation.HORIZONTAL,  // the plot orientation
    	            true,                        // include legend
    	            true,                        // tooltips
    	            false                        // urls
    	        );

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
	        CategoryDataset cd = chart.getCategoryPlot().getDataset();

            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
          //  domainAxis.
            domainAxis.setLowerMargin(0.02);
            domainAxis.setUpperMargin(0.02);
            
            int rc = cd.getRowCount();
            column_val=cd.getColumnCount();
	        System.out.println(rc);
	        for (int i = 0; i < rc; i++) {
	            String egName = cd.getRowKey(i).toString();
	            System.out.println(egName);
	            Color color = date_ex.engGroupColor.get(egName);
	            renderer.setSeriesPaint(i, color);
	            renderer.setSeriesToolTipGenerator(i, new CustomToolTipGenerator(date_ex.stack_trace.get(i)));
	        }
	        plot.setRenderer(renderer);
	        ((CategoryPlot)chart.getPlot()).setFixedLegendItems( date_ex.getPrincipalAndInterestLegendItemCollection());
            // set the range axis to display integers only...
            /*NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setRange(0.0, 100.0);*/

            // disable bar outlines...
            BarRenderer renderer1 = (BarRenderer) plot.getRenderer();
            renderer1.setDrawBarOutline(false);

            // set up gradient paints for series...
            /*GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                    0.0f, 0.0f, new Color(0, 0, 64));
            renderer.setSeriesPaint(0, gp0);*/
            
            

            return chart;

        }
        
        private static JFreeChart createChart_nothttp(CategoryDataset dataset) {

            // create the chart...
        	JFreeChart chart = ChartFactory.createStackedBarChart(
    	            null,
    	            "THREAD NAME",                  // domain axis label
    	            "THREAD STATES time duration --->",  // range axis label
    	            dataset,                     // data
    	            PlotOrientation.HORIZONTAL,  // the plot orientation
    	            true,                        // include legend
    	            true,                        // tooltips
    	            false                        // urls
    	        );

            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
	        CategoryDataset cd = chart.getCategoryPlot().getDataset();

            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
          //  domainAxis.
            domainAxis.setLowerMargin(0.02);
            domainAxis.setUpperMargin(0.02);
            
            int rc = cd.getRowCount();
            column_val=cd.getColumnCount();
	        System.out.println(rc);
	        for (int i = 0; i < rc; i++) {
	            String egName = cd.getRowKey(i).toString();
	            System.out.println(egName);
	            Color color = date_ex_not_http.engGroupColor.get(egName);
	            renderer.setSeriesPaint(i, color);
	            renderer.setSeriesToolTipGenerator(i, new CustomToolTipGenerator(date_ex_not_http.stack_trace.get(i)));
	        }
	        plot.setRenderer(renderer);
	        ((CategoryPlot)chart.getPlot()).setFixedLegendItems( date_ex_not_http.getPrincipalAndInterestLegendItemCollection());
            // set the range axis to display integers only...
            /*NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
            rangeAxis.setRange(0.0, 100.0);*/

            // disable bar outlines...
            BarRenderer renderer1 = (BarRenderer) plot.getRenderer();
            renderer1.setDrawBarOutline(false);

            // set up gradient paints for series...
            /*GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                    0.0f, 0.0f, new Color(0, 0, 64));
            renderer.setSeriesPaint(0, gp0);*/
            
            

            return chart;

        }

        /**
         * Handle a change in the slider by updating the dataset value.  This
         * automatically triggers a chart repaint.
         *
         * @param e  the event.
         */
        public void stateChanged(ChangeEvent e) {
            this.dataset.setFirstCategoryIndex(this.scroller.getValue());
        }

    }

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public sliding_category(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createDemoPanel());
    }

    /**
     * Creates a demo panel.  This method is called by SuperDemo.java.
     *
     * @return A demo panel.
     */
    public static JTabbedPane createDemoPanel() {
    	final JTabbedPane jtp = new JTabbedPane();
        jtp.add("http_threads", new MyDemoPanel(date_ex.dataset,"http"));
        //final JTabbedPane jtp1 = new JTabbedPane();
        jtp.add("non_httpThread", new MyDemoPanel(date_ex_not_http.dataset,"not_http"));
       JPanel jpr=new JPanel();
       jpr.add(jtp);
     
       return jtp;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void create_barChart() {
        sliding_category demo = new sliding_category(
                "THREAD DUMP ANALYSER");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

	

}

