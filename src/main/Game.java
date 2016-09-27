package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel{
	
	public FlyingThing fly;
	public FlyingThing2 fly2;
	public double g = 0.1;
	public int sb = 0;
	public int sr = 0;
	public String team;
	private final static int flyWidth = 10;
	private final static int flyLength = 20;
	private final static int shDia = 5;
	private final static int shV = 20;
	private final static int arrayLength = 100;
	private final static int life = 100;
	private Bullets[] bb;
	private Bullets[] br;
	private int lr = 0;
	private int lb = 0;
	private double tr = 0;
	private double tb = 0;
	private int rt = 100;

	public Game() {
		System.out.println(this.getWidth() + " " + this.getHeight());
		fly = new FlyingThing(this);
		fly2 = new FlyingThing2(this);
		bb = new Bullets[arrayLength];
		br = new Bullets[arrayLength];
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				fly.keyReleased(e);
				fly2.keyReleased(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				fly.keyPressed(e);
				fly2.keyPressed(e);
			}
		});
		setFocusable(true);
	}
	
	private void move() {
		fly.move();
		fly2.move();
		for(int i = 0; i < arrayLength ;i++){
			if(bb[i] != null){
				bb[i].move();
			}
			if(br[i] != null){
				br[i].move();
			}
		}
	}
	
	public void fire(char team,int nx,int ny,int ndeg){
		int i = 0;
		switch(team){
			case('r'):
				while(lr < arrayLength && i < arrayLength && br[i] != null){
					i++;
				}
				if(lr < arrayLength && i < arrayLength && br[i] == null){
					br[i] = new Bullets(this, 'r', nx, ny, ndeg, i);
					lr++;
				}
				break;
			case('b'):
				while(lb < arrayLength && i < arrayLength && bb[i] != null){
					i++;
				}
				if(lb < arrayLength && i < arrayLength && bb[i] == null){
					bb[i] = new Bullets(this, 'b', nx, ny, ndeg, i);
					lb++;
				}
				break;
			
		}
	}
	
	public double getG(){
		return g;
	}
	
	public int getflyLength(){
		return flyLength;
	}
	
	
	public int getflyWidth(){
		return flyWidth;
	}
	
	public int getShDia(){
		return shDia;
	}
	
	
	@Override
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON );
		g2d.setColor(Color.BLUE);
		for(int i = 0; i < arrayLength ;i++){
			if(bb[i] != null){
				bb[i].paint(g2d);
			}
		}
		fly.paint(g2d);
		g2d.setColor(Color.RED);
		for(int i = 0; i < arrayLength ;i++){
			if(br[i] != null){
				br[i].paint(g2d);
			}
		}
		fly2.paint(g2d);
		
		g2d.setTransform(AffineTransform.getRotateInstance(0,0,0));

		g2d.setColor(Color.BLUE);
		g2d.setFont(new Font("Verdana", Font.BOLD, 30));
		g2d.drawString(String.valueOf(100 - getScore1()), 10, 30);
		g2d.setFont(new Font("Verdana", Font.BOLD, 10));
		g2d.drawString("(" + String.valueOf(100 - getScore2())  + ")", 10, 40);
		g2d.fillRect(10, 60, 20, (int) (rt - tb));
		
		g2d.setColor(Color.RED );
		g2d.setFont(new Font("Verdana", Font.BOLD, 30));
		g2d.drawString(String.valueOf(100 - getScore2()), this.getWidth() - 80, 30);
		g2d.setFont(new Font("Verdana", Font.BOLD, 10));
		g2d.drawString("(" + String.valueOf(100 - getScore1()) + ")", this.getWidth() - 80, 40);
		g2d.fillRect(this.getWidth() - 40, 60, 20, (int) (rt - tr));
		
	}
	
	public void gameOver(){
		JOptionPane.showMessageDialog(this, "Blue, remaining life is : " + (100 - getScore1()) + " Red, your remaining life is : " + (100 - getScore2()),
							"Game Over", JOptionPane.YES_NO_OPTION);
		System.exit(ABORT);
	}
	
	public int getScore1(){
		return sb;
	}
	
	public int getScore2(){
		return sr;
	}
	
	public void addBlue(){
		sr = sr + 1;
	}
	
	public void addRed(){
		sb = sb + 1;
	}
	
	public void gameLoop() throws InterruptedException{
		while(true){
			if(sb >= life || sr >= life)
				this.gameOver();
			move();
			repaint();
			Thread.sleep(20);
			if(lr == arrayLength){
				if(tr < rt){
					tr++;
				}
				else{
					tr = 0;
					lr = 0;
				}
			}
			if(lb == arrayLength){
				if(tb < rt)
					tb++;
				else{
					tb = 0;
					lb = 0;
				}
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException{
		JFrame frame = new JFrame("Game Prototype");
		Game game = new Game();
		frame.add(game);
		frame.setSize(1000,800);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.gameLoop();
	}

	public int getShV() {
		return shV;
	}

	public void delete(int n,char team) {
		switch(team){
		case('r'):
				br[n] = null;
			break;
		case('b'):
				bb[n] = null;
			break;
		
		}
		
	}

	public void addScore(char team) {
		switch(team){
		case('r'):
			 	this.addRed();
			break;
		case('b'):
				this.addBlue();
			break;
		
		}
	}
}