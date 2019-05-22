package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.spi.CurrencyNameProvider;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Speedster;
import model.pieces.heroes.Tech;
import model.pieces.sidekicks.SideKickP1;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class game2 extends JFrame implements ActionListener {

	private Player player1;
	private Player player2;
	private Game game;
	private JPanel Board;
	private JPanel MovPan;
	private JPanel DeadP1;
	private JPanel DeadP2;
	private JPanel pay1;
	private JPanel pay2;
	private JPanel Curpl;
	private JPanel P1;
	private JPanel P2;
	private JLabel sum;
	private JPanel P3;
	private JPanel PS;
	private AudioStream audioStream;
	private AudioStream audioStream2;
	private TextArea desc;
	private JLabel xx;
	private JLabel sum2;
	private JPanel P4;
	private JPanel P5;
	private ArrayList<JButton> heroButt;
	private ArrayList<Piece> Heroes;
	private ArrayList<Direction> Directarray;
	private ArrayList<JButton> Directbutt;
	private ArrayList<JButton> DeadB1;
	private ArrayList<JButton> DeadB2;
	private JButton power;
	private JButton SwitchTurns;
	private Piece currPiece;
	private Direction currDirection;
	private Boolean currPower = false;
	private JLabel playernow;
	private Piece currtarget;
	private Point currnewpos;
	private ArrayList<JButton> PayLD1;
	private ArrayList<JButton> PayLD2;

	public game2(String a, String b) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		super("game");
		// this.setLayout(new FlowLayout());
		final Image backgroundImage = ImageIO.read(getClass().getResource("HIN.jpg"));
		setContentPane(new JPanel(new BorderLayout()) {
			@Override
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage, 0, 0, null);
			}
		});
		
		

		InputStream in = new FileInputStream("BGSONG.wav");
		audioStream2 = new AudioStream(in);
		AudioPlayer.player.start(audioStream2);
		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 1960, 1040);
		this.setLayout(new BorderLayout());
		player1 = new Player(a);
		player2 = new Player(b);
		game = new Game(player1, player2);

		Board = new JPanel();
		Board.setOpaque(false);

		Board.setLayout(new GridLayout(game.getBoardHeight(), game.getBoardWidth()));
		heroButt = new ArrayList();
		Heroes = new ArrayList();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				JButton Pieces = new JButton();
				//Pieces.setBorder(new BevelBorder(BevelBorder.LOWERED));
				if (game.getCellAt(i, j).getPiece() != null) {
					Pieces.setToolTipText(game.getCellAt(i, j).getPiece().toString());
					ImageIcon icon = new ImageIcon(getClass().getResource(game.getCellAt(i, j).getPiece().getImage()));
					Image image = icon.getImage(); // transform it
					Image newimg = image.getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH); // scale it the
																									// smooth way
					icon = new ImageIcon(newimg); // transform it back
					Pieces.setIcon(icon);
				}
				Pieces.setOpaque(false);
				Pieces.setContentAreaFilled(false);
				heroButt.add(Pieces);
				Heroes.add(game.getCellAt(i, j).getPiece());
				Pieces.addActionListener(this);

				Board.add(Pieces);
			}
		}

		MovPan = new JPanel();
		MovPan.setOpaque(false);
		MovPan.setLayout(new GridLayout(3, 3));
		MovPan.setPreferredSize(new Dimension(100, 100));

		Directbutt = new ArrayList();
		Directarray = new ArrayList();

		JButton upleft = new JButton();
		upleft.setOpaque(false);
		upleft.addActionListener(this);
		upleft.setBorderPainted(false);
		upleft.setOpaque(false);
		upleft.setContentAreaFilled(false);
		ImageIcon icon = new ImageIcon(getClass().getResource("UPLEFT.jpg"));
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		upleft.setIcon(icon);
		// upleft.setBorderPainted(false);
		Directarray.add(Direction.UPLEFT);
		Directbutt.add(upleft);
		MovPan.add(upleft);

		JButton up = new JButton();
		up.addActionListener(this);
		up.setOpaque(false);
		up.setContentAreaFilled(false);
		up.setBorderPainted(false);
		icon = new ImageIcon(getClass().getResource("UP.png"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		up.setIcon(icon);
		Directarray.add(Direction.UP);
		Directbutt.add(up);
		MovPan.add(up);

		JButton upright = new JButton();
		upright.addActionListener(this);
		upright.setOpaque(false);
		upright.setContentAreaFilled(false);
		upright.setBorderPainted(false);
		ImageIcon icon2 = new ImageIcon(getClass().getResource("UPRIGHT.jpg"));
		Image image2 = icon2.getImage(); // transform it
		Image newimg2 = image2.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon2 = new ImageIcon(newimg2); // transform it back
		upright.setIcon(icon2);
		Directarray.add(Direction.UPRIGHT);
		Directbutt.add(upright);
		MovPan.add(upright);

		JButton left = new JButton();
		left.addActionListener(this);
		left.setOpaque(false);
		left.setContentAreaFilled(false);
		left.setBorderPainted(false);
		icon = new ImageIcon(getClass().getResource("Left.png"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		left.setIcon(icon);
		Directarray.add(Direction.LEFT);
		Directbutt.add(left);
		MovPan.add(left);

		power = new JButton();
		power.addActionListener(this);
		power.setOpaque(false);
		power.setContentAreaFilled(false);
		power.setBorderPainted(false);
		power.setToolTipText("Unlimited...");
		icon = new ImageIcon(getClass().getResource("SPO.png"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(85, 85, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		power.setIcon(icon);
		MovPan.add(power);

		JButton right = new JButton();
		right.addActionListener(this);
		right.setOpaque(false);
		right.setContentAreaFilled(false);
		right.setBorderPainted(false);
		right.setBorderPainted(false);
		icon = new ImageIcon(getClass().getResource("RIGHT.png"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		right.setIcon(icon);
		Directarray.add(Direction.RIGHT);
		Directbutt.add(right);
		MovPan.add(right);

		JButton downleft = new JButton();
		downleft.addActionListener(this);
		downleft.setOpaque(false);
		downleft.setContentAreaFilled(false);
		downleft.setBorderPainted(false);
		icon = new ImageIcon(getClass().getResource("DOWNLEFT.jpg"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		downleft.setIcon(icon);
		Directarray.add(Direction.DOWNLEFT);
		Directbutt.add(downleft);
		MovPan.add(downleft);

		JButton down = new JButton();
		down.addActionListener(this);
		down.setOpaque(false);
		down.setBorderPainted(false);
		down.setContentAreaFilled(false);
		icon = new ImageIcon(getClass().getResource("DOWN.png"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		down.setIcon(icon);
		Directarray.add(Direction.DOWN);
		Directbutt.add(down);
		MovPan.add(down);

		JButton downright = new JButton();
		downright.addActionListener(this);
		downright.setOpaque(false);
		downright.setContentAreaFilled(false);
		downright.setBorderPainted(false);
		icon = new ImageIcon(getClass().getResource("DOWNRIGHT.jpg"));
		image = icon.getImage(); // transform it
		newimg = image.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		downright.setIcon(icon);
		Directarray.add(Direction.DOWNRIGHT);
		Directbutt.add(downright);
		MovPan.add(downright);
		// this.add(MovPan);

		Curpl = new JPanel();
		Curpl.setOpaque(false);
		playernow = new JLabel("Current player: " + game.getCurrentPlayer().getName());
		playernow.setForeground(Color.WHITE);
		playernow.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		Curpl.add(playernow);
		this.add(Curpl, BorderLayout.PAGE_START);

		DeadP1 = new JPanel();
		DeadP1.setOpaque(false);
		JLabel tmp2 = new JLabel("First Player's Dead Characters");
		tmp2.setForeground(Color.WHITE);
		DeadP1.add(tmp2, BorderLayout.PAGE_START);
		DeadP1.setLayout(new GridLayout(0, 1));
		DeadB1 = new ArrayList();
		ArrayList<Piece> deadp1 = player1.getDeadCharacters();
		for (int j = 0; j < 9; j++) {
			JButton dead = new JButton();
			dead.setBorderPainted(false);
			dead.setOpaque(false);
			dead.setContentAreaFilled(false);
			dead.setPreferredSize(new Dimension(65, 65));
			if (!deadp1.isEmpty()) {
				Piece now = deadp1.get(j);
				deadp1.remove(j);
				dead = new JButton(now.getName());
				DeadB1.add(dead);
			}
			dead.setEnabled(false);
			DeadB1.add(dead);
			dead.addActionListener(this);
			DeadP1.add(dead);
		}

		DeadP2 = new JPanel();
		DeadP2.setOpaque(false);
		DeadP2.setLayout(new GridLayout(0, 1));
		DeadB2 = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			DeadB2.add(null);
		}
		ArrayList<Piece> deadp2 = player2.getDeadCharacters();
		for (int j = 0; j < 9; j++) {
			JButton dead = new JButton();
			dead.setBorderPainted(false);
			dead.setOpaque(false);
			dead.setContentAreaFilled(false);
			dead.setPreferredSize(new Dimension(65, 65));
			if (!deadp2.isEmpty()) {
				Piece now = deadp1.get(j);
				deadp2.remove(j);
				dead = new JButton(now.getName());
				DeadB2.set(8 - j, dead);
			}
			dead.setEnabled(false);
			DeadB2.set(8 - j, dead);
			dead.addActionListener(this);
			DeadP2.add(dead);
		}

		tmp2 = new JLabel("Second Player's Dead Characters");
		tmp2.setForeground(Color.WHITE);
		DeadP2.add(tmp2, BorderLayout.PAGE_END);

		pay1 = new JPanel();
		Border redline;
		redline = BorderFactory.createLineBorder(Color.BLUE);
		pay1.setOpaque(false);
		JLabel tmp = new JLabel("     1st player payload");
		tmp.setForeground(Color.WHITE);
		pay1.add(tmp, BorderLayout.PAGE_START);
		pay1.setPreferredSize(new Dimension(150, 400));
		pay1.setLayout(new GridLayout(0, 1));
		PayLD1 = new ArrayList<>();
		int x = player1.getPayloadPos();
		for (int j = 0; j < 6; j++, x--) {
			JButton dead = new JButton();
			dead.setOpaque(false);
			dead.setContentAreaFilled(false);
			// dead.setEnabled(false);
			dead.setBorderPainted(false);
			pay1.add(dead);
			PayLD1.add(dead);
		}

		pay2 = new JPanel();
		Border blackline;
		blackline = BorderFactory.createLineBorder(Color.red);

		pay2.setOpaque(false);

		pay2.setPreferredSize(new Dimension(150, 400));
		pay2.setLayout(new GridLayout(0, 1));
		PayLD2 = new ArrayList<>();
		int y = player2.getPayloadPos();
		for (int j = 0; j < 6; j++, y--) {
			JButton dead = new JButton();
			dead.setOpaque(false);
			dead.setContentAreaFilled(false);
			// dead.setEnabled(false);
			dead.setBorderPainted(false);
			PayLD2.add(dead);
			pay2.add(dead);
		}
		tmp = new JLabel("     2nd player payload");
		tmp.setForeground(Color.white);
		pay2.add(tmp, BorderLayout.PAGE_END);

		P5 = new JPanel();
		P5.setOpaque(false);
		P5.setLayout(new GridLayout(1, 2));

		SwitchTurns = new JButton("Switch Turns");
		SwitchTurns.setBorderPainted(false);
		SwitchTurns.addActionListener(this);
		SwitchTurns.setOpaque(false);
		SwitchTurns.setContentAreaFilled(false);
		SwitchTurns.setForeground(Color.white);
		P5.add(SwitchTurns);

		PS = new JPanel();
		PS.setLayout(new BorderLayout());
		ImageIcon abc = new ImageIcon(getClass().getResource("SMW.png"));
		// abc=this.resizeImageIcon(abc, 300, 300);
		sum = new JLabel(abc);

		PS.add(sum, BorderLayout.SOUTH);

		ImageIcon abc2 = new ImageIcon(getClass().getResource("SPB.png"));
		abc2 = this.resizeImageIcon(abc2, 400, 270);

		sum2 = new JLabel(abc2);
		sum2.setVisible(false);

		xx = new JLabel();

		// xx.setForeground(Color.WHITE);
		xx.setBounds(30, 485, 600, 80);
		this.add(xx);
		// sum.setPreferredSize(new Dimension(100, 100));
		PS.add(sum2, BorderLayout.CENTER);

		PS.setOpaque(false);

		P1 = new JPanel();
		P1.setOpaque(false);
		P2 = new JPanel();
		P2.setOpaque(false);
		P2.setPreferredSize(new Dimension(400, 800));
		P3 = new JPanel();
		P3.setOpaque(false);
		P3.setLayout(new BorderLayout());
		P2.add(pay1, BorderLayout.WEST);
		P2.add(pay2, BorderLayout.EAST);
		P1.add(DeadP1, BorderLayout.WEST);
		P1.add(DeadP2, BorderLayout.EAST);
		P3.add(P2, BorderLayout.NORTH);
		// P3.add(MovPan,BorderLayout.CENTER);
		P3.add(PS, BorderLayout.SOUTH);

		P4 = new JPanel();
		P4.setOpaque(false);
		P4.setLayout(new BorderLayout());
		P4.add(P1, BorderLayout.NORTH);
		P4.add(MovPan, BorderLayout.CENTER);
		// desc=new TextArea("Piece Description: ");
		// desc.setFont(new Font(Font.MONOSPACED, Font.ROMAN_BASELINE, 12));
		// desc.setEditable(false);
		// P4.add(desc,BorderLayout.CENTER);
		P4.add(P5, BorderLayout.SOUTH);

		this.add(Board, BorderLayout.CENTER);
		this.add(P4, BorderLayout.EAST);
		this.add(P3, BorderLayout.WEST);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		xx.setText("");
		sum2.setVisible(false);
		ImageIcon abc2 = new ImageIcon(getClass().getResource("SMW.png"));
		sum.setIcon(abc2);

		JButton btnClicked = (JButton) e.getSource();

		if (!btnClicked.equals(SwitchTurns)) {
			try {
				InputStream in = new FileInputStream("Click.wav");
				audioStream = new AudioStream(in);
				AudioPlayer.player.start(audioStream);
			} catch (IOException e72) {
			}
		}

		if (heroButt.contains(btnClicked)) {
			int pieceIndex = heroButt.indexOf(btnClicked);
			Piece nowado = Heroes.get(pieceIndex);
			if ((currPiece instanceof Tech || currPiece instanceof Medic) && currPower == true) {
				if (nowado != null && currtarget == null) {
					currtarget = nowado;
					
				} else {
					currnewpos = new Point(pieceIndex / 6, pieceIndex % 6);
				}
			} else {
				if(nowado!=null&&currPiece==null) {
					if(nowado instanceof SideKickP1) {
					//	btnClicked.setIcon(new ImageIcon(getClass().getResource("MRMIS.gif")));
						InputStream in;
						try {
							in = new FileInputStream("MES.wav");
							audioStream = new AudioStream(in);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						AudioPlayer.player.start(audioStream);
						currPiece = nowado;
						Border blackline = BorderFactory.createLineBorder(Color.ORANGE);
						Point y = new Point(pieceIndex / 6, pieceIndex % 6);
						Point x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UP);
						currPiece.adjustBounds(x);
						int k=(int) (x.getX()*6+x.getY());
						JButton plz =heroButt.get(k);
						//plz.setBackground(Color.LIGHT_GRAY);
						//plz.setOpaque(true);
						//plz.setContentAreaFilled(true);
						plz.setBorder(blackline);
						
						x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.RIGHT);
						currPiece.adjustBounds(x);
						k=(int) (x.getX()*6+x.getY());
						plz =heroButt.get(k);
						//plz.setBackground(Color.LIGHT_GRAY);
						//plz.setOpaque(true);
						//plz.setContentAreaFilled(true);
						plz.setBorder(blackline);
						
						x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.LEFT);
						currPiece.adjustBounds(x);
						k=(int) (x.getX()*6+x.getY());
						plz =heroButt.get(k);
						//plz.setBackground(Color.LIGHT_GRAY);
						//plz.setOpaque(true);
						//plz.setContentAreaFilled(true);
						plz.setBorder(blackline);
						
						x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UPLEFT);
						currPiece.adjustBounds(x);
						k=(int) (x.getX()*6+x.getY());
						plz =heroButt.get(k);
						//plz.setBackground(Color.LIGHT_GRAY);
						//plz.setOpaque(true);
						//plz.setContentAreaFilled(true);
						plz.setBorder(blackline);
						
						x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UPRIGHT);
						currPiece.adjustBounds(x);
						k=(int) (x.getX()*6+x.getY());
						plz =heroButt.get(k);
						//plz.setBackground(Color.LIGHT_GRAY);
						//plz.setOpaque(true);
						//plz.setContentAreaFilled(true);
						plz.setBorder(blackline);
					}
					
					
				currPiece = nowado;
				/*btnClicked.setBackground(Color.GRAY);
				btnClicked.setOpaque(true);
				btnClicked.setContentAreaFilled(true);*/
				
				}
				else {
					if(currPiece!=null) {
						if(!(currPiece instanceof Speedster)) {
					Point y = new Point(pieceIndex / 6, pieceIndex % 6);
					Point x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UP);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.UP;
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.DOWN);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.DOWN;
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.RIGHT);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.RIGHT;
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.LEFT);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.LEFT;
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UPLEFT);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.UPLEFT;
					
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UPRIGHT);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.UPRIGHT;
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.DOWNLEFT);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.DOWNLEFT;
					
					x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.DOWNRIGHT);
					currPiece.adjustBounds(x);
					if(y.equals(x)) currDirection= Direction.DOWNRIGHT;}
						else {
							Point y = new Point(pieceIndex / 6, pieceIndex % 6);
							Point x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UP);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.UP);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.UP;
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.DOWN);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.DOWN);

							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.DOWN;
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.RIGHT);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.RIGHT);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.RIGHT;
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.LEFT);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.LEFT);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.LEFT;
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UPLEFT);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.UPLEFT);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.UPLEFT;
							
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.UPRIGHT);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.UPRIGHT);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.UPRIGHT;
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.DOWNLEFT);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.DOWNLEFT);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.DOWNLEFT;
							
							x=currPiece.getDirectionPos(new Point(currPiece.getPosI(),currPiece.getPosJ()),Direction.DOWNRIGHT);
							x=currPiece.getDirectionPos(new Point((int)x.getX(),(int)x.getY()),Direction.DOWNRIGHT);
							currPiece.adjustBounds(x);
							if(y.equals(x)) currDirection= Direction.DOWNRIGHT;
						}
					
					if(currDirection==null) {
						if(nowado instanceof SideKickP1) {
							//btnClicked.setIcon(new ImageIcon(getClass().getResource("MRMIS.gif")));
							InputStream in;
							try {
								in = new FileInputStream("MES.wav");
								audioStream = new AudioStream(in);
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							AudioPlayer.player.start(audioStream);
						}
						currPiece = nowado;
						/*btnClicked.setBackground(Color.GRAY);
						btnClicked.setOpaque(true);
						btnClicked.setContentAreaFilled(true);*/
					}
					}
				}
			}

		}
		ArrayList<Piece> deadp1 = player1.getDeadCharacters();
		if (DeadB1.contains(btnClicked)) {
			int pieceIndex = DeadB1.indexOf(btnClicked);
			currtarget = deadp1.get(pieceIndex);
		}

		ArrayList<Piece> deadp2 = player2.getDeadCharacters();
		if (DeadB2.contains(btnClicked)) {
			int pieceIndex = DeadB2.indexOf(btnClicked);
			currtarget = deadp2.get(pieceIndex);
		}

		if (Directbutt.contains(btnClicked)) {
			int pieceIndex = Directbutt.indexOf(btnClicked);
			currDirection = Directarray.get(pieceIndex);
		}

		if (btnClicked.equals(power)) {
			currPower = !currPower;
			if (currPower == true) {
				power.setIcon(new ImageIcon(getClass().getResource("SPI.gif")));
				power.setToolTipText("...POWAAAA");
			} else {
				ImageIcon icon = new ImageIcon(getClass().getResource("SPO.png"));
				Image image = icon.getImage(); // transform it
				Image newimg = image.getScaledInstance(85, 85, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				icon = new ImageIcon(newimg); // transform it back
				power.setIcon(icon);
				power.setToolTipText("Unlimited...");
			}
		}

		if (btnClicked.equals(SwitchTurns)) {
			if (!currPower) {
				try {
					currPiece.move(currDirection);
					try {
						InputStream in = new FileInputStream("WOOSH.wav");
						audioStream = new AudioStream(in);
						AudioPlayer.player.start(audioStream);
					} catch (IOException e72) {
					}
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException e1) {
					xx.setText(e1.getMessage());
					sum2.setVisible(true);
					ImageIcon abc = new ImageIcon(getClass().getResource("SMWT.gif"));
					sum.setIcon(abc);

				} catch (NullPointerException e1) {
					xx.setText("Please Specify");
					sum2.setVisible(true);
					ImageIcon abc = new ImageIcon(getClass().getResource("SMWT.gif"));
					sum.setIcon(abc);
				}
			}
			if (currPower) {
				if (currPiece instanceof ActivatablePowerHero) {
					try {

						((ActivatablePowerHero) currPiece).usePower(currDirection, currtarget, currnewpos);
						try {
							InputStream in = new FileInputStream(currPiece.getSong());
							audioStream = new AudioStream(in);
							AudioPlayer.player.start(audioStream);
						} catch (IOException e72) {
						}
					} catch (InvalidPowerUseException | WrongTurnException e1) {
						xx.setText(e1.getMessage());
						sum2.setVisible(true);
						ImageIcon abc = new ImageIcon(getClass().getResource("SMWT.gif"));
						sum.setIcon(abc);
					} catch (NullPointerException e2) {
						xx.setText("Please Specify");
						sum2.setVisible(true);
						ImageIcon abc = new ImageIcon(getClass().getResource("SMWT.gif"));
						sum.setIcon(abc);
					}
				}
			}

			try {
				updateBoard();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	public void updateBoard() throws IOException {

		int z = 0;
		currPower = false;
		currDirection = null;
		currPiece = null;
		currnewpos = null;
		currtarget = null;

		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				JButton now = heroButt.get(z);
				if (game.getCellAt(i, j).getPiece() != null) {
					// now.setText(game.getCellAt(i, j).getPiece().getName());
					now.setToolTipText(game.getCellAt(i, j).getPiece().toString());
					ImageIcon icon = new ImageIcon(getClass().getResource(game.getCellAt(i, j).getPiece().getImage()));
					Image image = icon.getImage(); // transform it
					Image newimg = image.getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH); // scale it the
																						// smooth way
					icon = new ImageIcon(newimg); // transform it back
					now.setIcon(icon);
				} else {
					now.setToolTipText("");
					now.setIcon(null);
				}
				// now.addActionListener(this);
				now.setOpaque(false);
				now.setContentAreaFilled(false);
				Heroes.set(z, game.getCellAt(i, j).getPiece());
				heroButt.set(z, now);
				z++;
			}
		}
		ArrayList<Piece> deadp1 = player1.getDeadCharacters();
		for (int j = 0; j < 9; j++) {
			if (j < deadp1.size()) {
				JButton dead = DeadB1.get(j);
				Piece now = deadp1.get(j);
				ImageIcon icon = new ImageIcon(getClass().getResource(now.getImage()));
				Image image = icon.getImage(); // transform it
				Image newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				icon = new ImageIcon(newimg); // transform it back
				dead.setToolTipText(now.getName() + " (" + now.getType() + ")");
				dead.setIcon(icon);
				dead.setEnabled(true);
			} else {
				JButton dead = DeadB1.get(j);
				dead.setToolTipText("");
				dead.setIcon(null);
				dead.setEnabled(false);
			}

		}
		ArrayList<Piece> deadp2 = player2.getDeadCharacters();
		for (int j = 0; j < 9; j++) {
			if (j < deadp2.size()) {
				JButton dead = DeadB2.get(j);
				Piece now = deadp2.get(j);
				ImageIcon icon = new ImageIcon(getClass().getResource(now.getImage()));
				Image image = icon.getImage(); // transform it
				Image newimg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				icon = new ImageIcon(newimg); // transform it back
				dead.setToolTipText(now.getName() + " (" + now.getType() + ")");
				dead.setIcon(icon);
				dead.setEnabled(true);
			} else {
				JButton dead = DeadB2.get(j);
				dead.setToolTipText("");
				dead.setIcon(null);
				dead.setEnabled(false);
			}

		}

		int x = player1.getPayloadPos();
		if (x == 6) {
			new EndGame(player1.getName() + " Won", true);
			AudioPlayer.player.stop(audioStream2);
			this.dispose();
		}
		for (int j = 0; j < 6; j++, x--) {
			JButton dead = PayLD1.get(j);
			if (x > 0) {
				// dead.setOpaque(true);
				ImageIcon icon = new ImageIcon(getClass().getResource("RPL.png"));
				Image image = icon.getImage(); // transform it
				Image newimg = image.getScaledInstance(60, 50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				icon = new ImageIcon(newimg); // transform it back
				dead.setIcon(icon);
			}
			dead.setBorderPainted(false);
			// dead.setEnabled(false);
			PayLD1.set(j, dead);
		}

		int y = player2.getPayloadPos();
		if (y == 6) {

			new EndGame(player2.getName() + " Won", false);
			AudioPlayer.player.stop(audioStream2);
			this.dispose();
		}
		for (int j = 0; j < 6; j++, y--) {
			JButton dead = PayLD2.get(5 - j);
			if (y > 0) {
				ImageIcon icon = new ImageIcon(getClass().getResource("CPL.png"));
				Image image = icon.getImage(); // transform it
				Image newimg = image.getScaledInstance(60, 50, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
				icon = new ImageIcon(newimg); // transform it back
				dead.setIcon(icon);
			}
			dead.setBorderPainted(false);
			// dead.setEnabled(false);
			PayLD2.set(5 - j, dead);
		}
		ImageIcon icon = new ImageIcon(getClass().getResource("SPO.png"));
		Image image = icon.getImage(); // transform it
		Image newimg = image.getScaledInstance(85, 85, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		icon = new ImageIcon(newimg); // transform it back
		power.setIcon(icon);
		power.setToolTipText("Unlimited...");
		playernow.setText("Current player: " + game.getCurrentPlayer().getName());

	}

	public static ImageIcon resizeImageIcon(ImageIcon imageIcon, Integer width, Integer height) {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);

		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
		graphics2D.dispose();

		return new ImageIcon(bufferedImage, imageIcon.getDescription());
	}

	class OBut extends JButton {
		private float opacity;

		// ......
		public void setOpacity(float opacity) {
			this.opacity = opacity;
		}
	}
	
}
