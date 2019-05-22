package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.game.Game;
import model.game.Player;

public class Version2View extends JFrame implements ActionListener{

	Player p1;
	Player p2;
	Game game;
	JButton C1;
	JButton C2;
	JButton C3;
	JButton C4;

	public Version2View() {
		/**
		 * Initializing a game object, where the first Player corresponds to p1
		 * object and the second player corresponds to p2 object
		 */
		super("Quiz");
		p1 = new Player("player 1");
		p2 = new Player("player 2");
		game = new Game(p1, p2);
		
		/**
		 * Your solution starts from here
		 */
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 500);
		this.setLayout(new GridLayout(2,2));
		C1=new JButton("Show First Hero");
		C2=new JButton("Show Second Hero");
		C3=new JButton("Show Third Hero");
		C4=new JButton("Show Fourth Hero");
		
		this.add(C1);
		C1.addActionListener(this);
		this.add(C2);
		C2.addActionListener(this);
		this.add(C3);
		C3.addActionListener(this);
		this.add(C4);
		C4.addActionListener(this);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new Version2View();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton a = (JButton) e.getSource();
		if(a==C1) {
			//C1.repaint();
			//C1.revalidate();
			C1.setText(game.getCellAt(5, 0).getPiece().getName());
		}
		if(a==C2) {
			C2.repaint();
			C2.revalidate();
			C2.setText(game.getCellAt(5, 1).getPiece().getName());
		}
		
		if(a==C3) {
			C3.repaint();
			C3.revalidate();
			C3.setText(game.getCellAt(5, 2).getPiece().getName());
		}
		
		if(a==C4) {
			C4.repaint();
			C4.revalidate();
			C4.setText(game.getCellAt(5, 3).getPiece().getName());
		}
		
	}

}
