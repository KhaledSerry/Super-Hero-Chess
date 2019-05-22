package view;

import java.awt.BorderLayout;
import sun.audio.*;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.game.Game;

public class game extends JFrame implements ActionListener {

	private Game Game;
	private JTextField player1;
	private JLabel player1name;
	private JTextField player2;
	private JLabel player2name;
	private ImageIcon image;
	private JLabel label;
	private AudioStream audioStream;
	private JPanel panel1;
	private JPanel back;
	private JButton yawelcome;

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		new game();
	}

	public game() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		super("Welcome");
		final Image backgroundImage = ImageIO.read(getClass().getResource("RM2.jpg"));
		setContentPane(new JPanel(new BorderLayout()) {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage, 0, 0, null);
			}
		});
		
		


		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 1920, 1080);
		this.setLayout(new BorderLayout());

		yawelcome = new JButton("Start Game");
		yawelcome.addActionListener(this);
		panel1 = new JPanel();
		// panel1.setLocation(5, 7);
		panel1.setOpaque(false);
		// panel1.setBackground(Color.RED);
		panel1.setSize(200, 200);
		player1name = new JLabel("Enter player's 1 name");

		panel1.add(player1name);
		player1 = new JTextField(20);
		player1name.setForeground(Color.WHITE);
		panel1.add(player1);
		player2name = new JLabel("Enter player's 2 name");
		player2name.setForeground(Color.WHITE);
		panel1.add(player2name);
		player2 = new JTextField(20);
		panel1.add(player2);
		panel1.add(yawelcome);

		this.add(panel1, BorderLayout.CENTER);

		InputStream in = new FileInputStream("RMSONG.wav");
		audioStream = new AudioStream(in);
		AudioPlayer.player.start(audioStream);

		this.setVisible(true);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String a = player1.getText();
		String b = player2.getText();

		try {
			new game2(a, b);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioPlayer.player.stop(audioStream);
		this.dispose();
	}

}
