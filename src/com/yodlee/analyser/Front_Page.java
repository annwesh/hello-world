package com.yodlee.analyser;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;




@SuppressWarnings("serial")
public class Front_Page extends JFrame implements ActionListener {

    private JTextField t1;
   // private JTextField t2;
  //  private JTextField t3;
    private JLabel l1;
    private JButton b1;
    JProgressBar progressBar;
    int i = 0;

    public Front_Page() {

        setLayout(null);

        t1 = new JTextField(20);
        t1.setBounds(200,40,300,20);
       
        l1=new JLabel("Enter the path to the dump file");
        l1.setBounds(20, 30, 400, 30);
        b1 = new JButton("OK");
        b1.setBounds(250, 100, 60,30);
        b1.addActionListener(this);

        progressBar = new JProgressBar(1, 100);
        progressBar.setBounds(20, 200,550, 40);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.GRAY);
        progressBar.setBackground(Color.white);

        add(t1);
        add(l1);
        add(b1);
        add(progressBar);

        setSize(600, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    class worker1 implements Runnable{

    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		String path=t1.getText().trim();
    		try {
				ThreadDumpController.makechart(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int i = 0;
        if (e.getSource() == b1) {
            progressBar.setVisible(true);
            progressBar.setStringPainted(true);
            if (t1.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Empty Fields");
                System.exit(0);
            } else {
                try {
                    Thread t1=new Thread(new worker1());
                    Thread t2=new Thread(new worker2());
                    t1.start();
                    t2.start();
                } catch (Exception e1) {
                    System.out.print("Exception =" + e1);
                }
                progressBar.setValue(0);
            }
        }

    }
    
    
    class worker2 implements Runnable{
           int i=0;
    	@Override
    	public void run() {
    		// TODO Auto-generated method stub
    		while (i <= 100) {

                // int w = Integer.parseInt(t1.getText());
                 //int x = Integer.parseInt(t2.getText());
                 //int res = w + x;
                 //t3.setText("" + res);

                 try {
					Thread.sleep(900);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 progressBar.paintImmediately(0, 0, 550, 200);
                 progressBar.setValue(i);
                 progressBar.setString(i+ "%");
                 i++;
             }
             CloseFrame();
    	}
    	
    }
    public void CloseFrame(){
        super.dispose();
    }

    public static void main(String[] args) {
       // Progress p = new Progress();
    	Front_Page p=new Front_Page();
        p.setVisible(true);

    }

}