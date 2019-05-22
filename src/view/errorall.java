package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class errorall extends JFrame{

	public errorall(String s) {
		super("Error");
		this.setBounds(800,400,200, 200);
		JLabel err=new JLabel(s);
		add(err,BorderLayout.CENTER);
		setVisible(true);
	}
public static void main(String[] args) {

}	
	
	
	
	
}
