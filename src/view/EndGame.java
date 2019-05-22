package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class EndGame extends JFrame implements ActionListener {

	private JButton PlayAgain;
	private JButton Exit;
	private AudioStream audioStream;

	public EndGame(String a, boolean W) throws IOException {
		super(a);

		JLabel TRM = new JLabel();
		if (W) {
			this.setBounds(800, 400, 515, 340);
			ImageIcon abc = new ImageIcon(getClass().getResource("DANCE.gif"));
			TRM.setIcon(abc);
			InputStream in = new FileInputStream("WABA.wav");
			audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		} else {
			this.setBounds(800, 400, 490, 334);
			ImageIcon abc = new ImageIcon(getClass().getResource("EMW.gif"));
			TRM.setIcon(abc);
			InputStream in = new FileInputStream("RMLS.wav");
			audioStream = new AudioStream(in);
			AudioPlayer.player.start(audioStream);
		}

		this.add(TRM, BorderLayout.NORTH);

		JPanel pan = new JPanel();
		pan.setLayout(new GridLayout(1, 2));
		PlayAgain = new JButton("Play Again");
		PlayAgain.addActionListener(this);
		pan.add(PlayAgain);
		Exit = new JButton("Exit");
		Exit.addActionListener(this);
		pan.add(Exit);
		add(pan, BorderLayout.SOUTH);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton a = (JButton) e.getSource();

		if (a.equals(PlayAgain)) {
			try {
				new game();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		else {
			System.exit(0);
		}

		AudioPlayer.player.stop(audioStream);
	}

	public static void main(String[] args) throws IOException {
		new EndGame("a", true);
		//new EndGame("a", false);
	}
}
