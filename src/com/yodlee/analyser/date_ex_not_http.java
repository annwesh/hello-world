package com.yodlee.analyser;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;
import java.text.AttributedString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class date_ex_not_http  extends BarRenderer{

	/**
	 * @param args
	 */
	
	public static HashMap<String,Color> engGroupColor = new HashMap<String, Color>();
	public static HashMap<Integer,String> flag_val=new HashMap<Integer, String>();
    static {
        engGroupColor.put("RUNNABLE", new Color(21,255,4));
        engGroupColor.put("WAITING", new Color(255, 255, 4));
        engGroupColor.put("TIMED_WAITING", new Color(4, 4,255));
        engGroupColor.put("BLOCKED", new Color(255, 4, 4));
        engGroupColor.put("UNKNOWN", new Color(64, 64,64));
        flag_val.put(1,"BLOCKED");
        flag_val.put(2,"RUNNABLE");
        flag_val.put(3,"WAITING");
        flag_val.put(4, "TIMED_WAITING");
       }
    public static DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    public static HashMap<Integer,String> stack_trace=new HashMap<Integer, String>();
    public static int count_st=0;
	public static void generate(Map<String,HashMap<String,StringBuilder>> final_map,String t,HashMap<String,StringBuilder> timekey,TreeSet<String> tset){
		int flag=0;
		String start=null;
		String end=null;
		
		for(String tt:tset){
			String temp=timekey.get(tt).toString();
			System.out.println("temp "+temp);
			if(temp.contains("java.lang.Thread.State: WAITING")){
			    if(flag==0){
			    	start=tt;
			    	end=tt;
			    	flag=3;
			    }	
			    else {
			    	if(flag!=3){
			    		long diff=date_diff(start,end);
			    		if(diff==0){
			    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
			    			
			    		}
			    		else
				    		dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
			    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
			    		count_st++;
			    		//System.out.println(diff);
			    		long any_lag=date_diff(end,tt);
			    		start=end=tt;
			    		
			    		if(any_lag!=0){
			    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
			    			stack_trace.put(count_st,"unknown timespace");
			    			count_st++;
			    		}
			    		flag=3;
			    	}
			    	else{
			    		if(timekey.get(start).toString().equals(timekey.get(tt).toString()))
			    		end=tt;
			    		else{
			    			long diff=date_diff(start,end);
				    		if(diff==0){
				    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
				    		}
				    		else
				    			dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
				    		//System.out.println(diff);
				    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
				    		count_st++;
				    		long any_lag=date_diff(end,tt);
				    		start=end=tt;
				    		
				    		if(any_lag!=0){
				    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
				    			stack_trace.put(count_st,"unknown timespace");
					    		count_st++;
				    		}
			    		}
			    	}
			    }
			}
			
			else if(temp.contains("java.lang.Thread.State: RUNNABLE")){
			    if(flag==0){
			    	start=tt;
			    	end=tt;
			    	flag=2;
			    }	
			    else {
			    	if(flag!=2){
			    		//System.out.println("inside runnable");
			    		long diff=date_diff(start,end);
			    		System.out.println(diff);
			    		if(diff==0){
			    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
			    		}
			    		else
				    		dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
			    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
			    		count_st++;
			    		long any_lag=date_diff(end,tt);
			    		start=end=tt;
			    		//dataset.addValue((double)(diff/(10000)),new UniqueComparable(flag_val.get(flag)),t);
			    		if(any_lag!=0){
			    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
			    			stack_trace.put(count_st,"unknown time space");
				    		count_st++;
			    		}
			    		flag=2;
			    	}
			    	else{
			    		if(timekey.get(start).toString().equals(timekey.get(tt).toString()))
				    		end=tt;
				    		else{
				    			long diff=date_diff(start,end);
					    		if(diff==0){
					    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
					    		}
					    		else
					    			dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
					    		//System.out.println(diff);
					    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
					    		count_st++;
					    		long any_lag=date_diff(end,tt);
					    		start=end=tt;
					    		
					    		if(any_lag!=0){
					    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
					    			stack_trace.put(count_st,"unknown timespace");
						    		count_st++;
					    		}
				    		}
			    	}
			    }
			}
			 else if(temp.contains("java.lang.Thread.State: TIMED_WAITING")){
			    if(flag==0){
			    	start=tt;
			    	end=tt;
			    	flag=4;
			    }	
			    else {
			    	if(flag!=4){
			    		long diff=date_diff(start,end);
			    		System.out.println(diff);
			    		if(diff==0){
			    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
			    		}
			    		else
				    		dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
			    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
			    		count_st++;
			    		long any_lag=date_diff(end,tt);
			    		start=end=tt;
			    		//dataset.addValue((double)(diff/(10000)),new UniqueComparable(flag_val.get(flag)),t);
			    		if(any_lag!=0){
			    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
			    			stack_trace.put(count_st,"unknown spacetime");
				    		count_st++;
			    		}
			    		flag=4;
			    	}
			    	else{
			    		if(timekey.get(start).toString().equals(timekey.get(tt).toString()))
				    		end=tt;
				    		else{
				    			long diff=date_diff(start,end);
					    		if(diff==0){
					    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
					    			
					    		}
					    		else
					    			dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
					    		//System.out.println(diff);
					    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
					    		count_st++;
					    		long any_lag=date_diff(end,tt);
					    		start=end=tt;
					    		
					    		if(any_lag!=0){
					    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
					    			stack_trace.put(count_st,"unknown time space");
						    		count_st++;
					    		}
				    		}
			    	}
			    }
			}
			
			
			 else if(temp.contains("java.lang.Thread.State: BLOCKED")){
				    if(flag==0){
				    	start=tt;
				    	end=tt;
				    	flag=1;
				    }	
				    else {
				    	if(flag!=1){
				    		long diff=date_diff(start,end);
				    		System.out.println(diff);
				    		if(diff==0){
				    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
				    		}
				    		else
					    		dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
				    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
				    		count_st++;
				    		long any_lag=date_diff(end,tt);
				    		start=end=tt;
				    		//dataset.addValue((double)(diff/(10000)),new UniqueComparable(flag_val.get(flag)),t);
				    		if(any_lag!=0){
				    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
				    			stack_trace.put(count_st,"unknown spacetime");
					    		count_st++;
				    		}
				    		flag=1;
				    	}
				    	else{
				    		if(timekey.get(start).toString().equals(timekey.get(tt).toString()))
					    		end=tt;
					    		else{
					    			long diff=date_diff(start,end);
						    		if(diff==0){
						    			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
						    			
						    		}
						    		else
						    			dataset.addValue((double)(diff/(1000)),new UniqueComparable(flag_val.get(flag)),t);
						    		//System.out.println(diff);
						    		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
						    		count_st++;
						    		long any_lag=date_diff(end,tt);
						    		start=end=tt;
						    		
						    		if(any_lag!=0){
						    			dataset.addValue(0.7D,new UniqueComparable("UNKNOWN"),t);
						    			stack_trace.put(count_st,"unknown time space");
							    		count_st++;
						    		}
					    		}
				    	}
				    }
				}

		}
		//last iteration
		long diff=date_diff(start,end);
		System.out.println(diff);
		if(diff==0){
			dataset.addValue(0.1D,new UniqueComparable(flag_val.get(flag)),t);
		}
		//long any_lag=date_diff(end,tt);
		//start=end=tt;
		else dataset.addValue((double)(diff/(10000)),new UniqueComparable(flag_val.get(flag)),t);
			
		stack_trace.put(count_st,toolTipStackTrace(timekey,start));
		count_st++;
		
	}
	private static String toolTipStackTrace(HashMap<String,StringBuilder> timekey,String start){
		StringBuilder str=new StringBuilder();
		String source=timekey.get(start).toString();
		str.append("<html>"+start+"<br>");
		String arr[]=source.split("\\)at ");
		for(int i=0;i<arr.length;i++){
			str.append(arr[i]+"<br>");
		}
		str.append("</html>");
		return str.toString();
		
	}
	public static LegendItemCollection getPrincipalAndInterestLegendItemCollection()
    {
        LegendItemCollection result = new LegendItemCollection();
        Shape shape = new Rectangle( 10, 10);
        
        String name = "RUNNABLE";
        AttributedString label = new AttributedString( name);
        LegendItem runnable = new LegendItem( label, null, null, null, shape, new Color(21,255,4));
        
        name = "WAITING";
        label = new AttributedString(name);
        LegendItem waiting = new LegendItem( label, null, null, null, shape,new Color(255, 255, 4));
        
        name = "TIMED_WAITING";
        label = new AttributedString(name);
        LegendItem timed_waiting = new LegendItem( label, null, null, null, shape,new Color(4, 4,255));
        
        name = "BLOCKED";
        label = new AttributedString(name);
        LegendItem blocked = new LegendItem( label, null, null, null, shape,new Color(255, 4, 4));
        
        name = "UNKNOWN PERIOD";
        label = new AttributedString(name);
        LegendItem unknown = new LegendItem( label, null, null, null, shape,new Color(64, 64,64));
        
        
        
        result.add( runnable);
        result.add( waiting);
        result.add(timed_waiting);
        result.add(blocked);
        result.add(unknown);
        return result;
    }
	
	public static void display(){
		
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
		 CategoryPlot plot = chart.getCategoryPlot();
	       // plot.getRenderer().set
	        BarRenderer renderer = (BarRenderer) chart.getCategoryPlot().getRenderer();
	        CategoryDataset cd = chart.getCategoryPlot().getDataset();
	        System.out.println("col"+chart.getCategoryPlot().getDataset().getColumnCount());
	        int rc = cd.getRowCount();
	        System.out.println(rc);
	        for (int i = 0; i < rc; i++) {
	            String egName = cd.getRowKey(i).toString();
	            System.out.println(egName);
	            Color color = engGroupColor.get(egName);
	            renderer.setSeriesPaint(i, color);
	            renderer.setSeriesToolTipGenerator(i, new CustomToolTipGenerator(stack_trace.get(i)));
	        }
	        plot.setRenderer(renderer);
	        ((CategoryPlot)chart.getPlot()).setFixedLegendItems( getPrincipalAndInterestLegendItemCollection());
	        ChartPanel chartPanel = new ChartPanel(chart);
	       
	        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 100));
            chartPanel.setDomainZoomable(true);
            chartPanel.setDismissDelay(40000);
	        JFrame f  =new JFrame();
	        f.add(chartPanel);
	        f.pack();
	        f.setVisible(true);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static long date_diff(String start,String end){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date d1 = null;
		Date d2 = null;
		try{
			d1 = format.parse(start);
			d2 = format.parse(end);
			
		}
		catch(Exception e){}
		return (d2.getTime()-d1.getTime());
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*ArrayList<SimpleDateFormat> arr=new ArrayList<SimpleDateFormat>();
		SimpleDateFormat sd=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			Date dr=sd.parse("2013/10/15 16:16:39");
			System.out.println(dr.);
			System.out.println(dr.getHours());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}



