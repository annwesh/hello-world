package com.yodlee.analyser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadDumpController {

	public static final Map<String,HashMap<String,StringBuilder>> final_map=new HashMap<String, HashMap<String,StringBuilder>>();
	public static final Map<String,HashMap<String,StringBuilder>> final_map_nothttp=new HashMap<String, HashMap<String,StringBuilder>>();
	//public static Map<StringBuilder,Integer> stacktrace_checker=new HashMap<StringBuilder, Integer>();
	public static final Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	public static final Map<String, ArrayList<String>> map_not_http = new HashMap<String, ArrayList<String>>();
	public static String filePath=null;
	
	public static void main(String[] args) throws IOException {
		Scanner ob=new Scanner(System.in);
		System.out.println("enter the full path of the dump files");
		String filepath=ob.nextLine();
		
		File folder = new File(filepath);
		listFilesForFolder(folder);
		//readFile(new File("D:\\WORK!!!\\vineet\\dump\\Threaddump.172.17.22.107_8443.2016_10_08_00.html"));
       try {
		//display();
		//display_nothttp();
		sliding_category.create_barChart();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	
	public static void makechart(String path) throws IOException {
		//Scanner ob=new Scanner(System.in);
		
		
		File folder = new File(path);
		listFilesForFolder(folder);
		//readFile(new File("D:\\WORK!!!\\vineet\\dump\\Threaddump.172.17.22.107_8443.2016_10_08_00.html"));
       try {
		display(path);
		display_nothttp(path);
		sliding_category.create_barChart();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public static void display(String path) throws Exception{
		/*Scanner ob=new Scanner(System.in);
		System.out.println("enter the path to the output file");
		filePath=ob.next();*/
		PrintWriter out = new PrintWriter(new FileWriter(path+"\\"+"dump1.txt"));
		Set<String> keys=map.keySet();
		HashMap<String,StringBuilder> time_key;
		for(String key:keys)
		{
			ArrayList<String> temp1=map.get(key);
			//if(temp1.size()>1)System.out.println("contains");
			//System.out.println("KEY$$$ "+key.split("\\*")[0]);
			//System.out.println("KEY$$$ "+key);
			//if(temp1.size()>1)
			//{
				//System.out.println("THREAD_NAME$$ "+key.split("\\*")[0]);
			/*-----------THREAD KEY---------------*/
				out.println("THREAD_NAME$$ "+key.split("\\*")[0]);
				String thread_key=key.split("\\*")[0].trim();
				StringBuilder sr=new StringBuilder(key.split("\\*")[1]);
			/*-------------------------------------*/	
				
				if(final_map.containsKey(thread_key))
					time_key=final_map.get(thread_key);
				else
				{
				time_key=new HashMap<String, StringBuilder>();
				}
			for(String line1:temp1){
				//System.out.println(line1);
				out.println(line1);
				time_key.put(line1,sr);
			}
			
			final_map.put(thread_key,time_key);
			
			out.println("/*------------------STACK TRACE-------------------*/");
			out.println(key.split("\\*")[1]);
			out.println("\n\n\n");
			
			//}
		}
		out.close();
		display_newMap(final_map);
		
	}
	
	
	public static void display_nothttp(String path) throws Exception{
		/*Scanner ob=new Scanner(System.in);
		System.out.println("enter the path to the output file");
		filePath=ob.next();*/
		PrintWriter out = new PrintWriter(new FileWriter(path+"\\"+"temp2.txt"));
		Set<String> keys=map_not_http.keySet();
		HashMap<String,StringBuilder> time_key;
		for(String key:keys)
		{
			ArrayList<String> temp1=map_not_http.get(key);
			//if(temp1.size()>1)System.out.println("contains");
			//System.out.println("KEY$$$ "+key.split("\\*")[0]);
			//System.out.println("KEY$$$ "+key);
			//if(temp1.size()>1)
			//{
				//System.out.println("THREAD_NAME$$ "+key.split("\\*")[0]);
			/*-----------THREAD KEY---------------*/
				out.println("THREAD_NAME$$ "+key.split("\\*")[0]);
				String thread_key=key.split("\\*")[0].trim();
				StringBuilder sr=new StringBuilder(key.split("\\*")[1]);
			/*-------------------------------------*/	
				
				if(final_map_nothttp.containsKey(thread_key))
					time_key=final_map_nothttp.get(thread_key);
				else
				{
				time_key=new HashMap<String, StringBuilder>();
				}
			for(String line1:temp1){
				//System.out.println(line1);
				out.println(line1);
				time_key.put(line1,sr);
			}
			
			final_map_nothttp.put(thread_key,time_key);
			
			out.println("/*------------------STACK TRACE-------------------*/");
			out.println(key.split("\\*")[1]);
			out.println("\n\n\n");
			
			//}
		}
		out.close();
		display_newMap_nothttp(final_map_nothttp);
		
	}
	
	
	public static void display_newMap_nothttp(Map<String,HashMap<String,StringBuilder>> fi)
	{
		Set<String> keys=fi.keySet();
		for(String t:keys){
			System.out.println("key "+t);
			HashMap<String,StringBuilder> timekey=final_map_nothttp.get(t);
			Set<String> timekeys=timekey.keySet();
			
			TreeSet<String> tset=new TreeSet<String>(new Comparator<String>() {
				 DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        @Override
			        public int compare(String o1, String o2) {
			            try {
			                return f.parse(o1).compareTo(f.parse(o2));
			            } catch (ParseException e) {
			                throw new IllegalArgumentException(e);
			            }
			        }
			});
			for(String t1:timekeys){
				//System.out.println(t1+" "+timekey.get(t1));
				tset.add(t1);
			}
			for(String t2:tset){
				System.out.println(t2+" "+timekey.get(t2));
				//tset.add(t1);
			}
			date_ex_not_http.generate(final_map_nothttp,t,timekey,tset);
		}
		//date_ex.display();
		//sliding_category.create_barChart();
	}
	
	
	public static void display_newMap(Map<String,HashMap<String,StringBuilder>> fi)
	{
		Set<String> keys=fi.keySet();
		for(String t:keys){
			System.out.println("key "+t);
			HashMap<String,StringBuilder> timekey=final_map.get(t);
			Set<String> timekeys=timekey.keySet();
			
			TreeSet<String> tset=new TreeSet<String>(new Comparator<String>() {
				 DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        @Override
			        public int compare(String o1, String o2) {
			            try {
			                return f.parse(o1).compareTo(f.parse(o2));
			            } catch (ParseException e) {
			                throw new IllegalArgumentException(e);
			            }
			        }
			});
			for(String t1:timekeys){
				//System.out.println(t1+" "+timekey.get(t1));
				tset.add(t1);
			}
			for(String t2:tset){
				System.out.println(t2+" "+timekey.get(t2));
				//tset.add(t1);
			}
			date_ex.generate(final_map,t,timekey,tset);
		}
		//date_ex.display();
		//sliding_category.create_barChart();
	}

	
	public static void listFilesForFolder(final File folder) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				//System.out.println(fileEntry.getName());
				readFile(fileEntry);
			}
		}
	}
	static int  count=0;
	public static void readFile(File fileEntry) throws IOException {
		
		FileInputStream fis = new FileInputStream(fileEntry);

		// Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		String line = null;
		String pattern = "^\".*\\[0x[0-9a-f]{16,16}\\]$";
		Pattern r = Pattern.compile(pattern);
		String timer=br.readLine();//the first line which contains the timestamp of the file
		
		while ((line = br.readLine()) != null) {
			Matcher m = r.matcher(line);
			if (m.find()){
				
				ArrayList<String> temp=null;
				StringBuffer buff=new StringBuffer();
				
				String key=line.split("\"")[1];
				//System.out.println(key);
				buff.append(key.trim());
				buff.append("*");
				line=br.readLine();
				while(!line.equals("")){
					//if(substr_in_stcktrace!=null&&line.contains(substr_in_stcktrace))flag=1;
					//temp.add(line);
					
					buff.append(line.trim());
					line=br.readLine();
				}
				if(key.contains("http-0.0.0.0-8443"))
				{
				       if((map.containsKey(buff.toString())))
				       {
					//System.out.println("contains");
					      temp=map.get(buff.toString());
				       }
				else
				temp=new ArrayList<String>();
				
				temp.add(timer.trim());
				map.put(buff.toString(),temp);
				}
				else {
					if((map_not_http.containsKey(buff.toString())))
					{
						//System.out.println("contains");
						 temp=map_not_http.get(buff.toString());
					}
					else
					temp=new ArrayList<String>();
					
					temp.add(timer.trim());
					map_not_http.put(buff.toString(),temp);
				}
			}
		}
		//System.out.println(count);
		br.close();
	}
}
