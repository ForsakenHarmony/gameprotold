package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class FlyingThing2 {
	private Game game;
	private int Length = 10;
	private int Width = 10;
	private double a = 0;
	@SuppressWarnings("unused")
	private double v = 0;
	private double x = 50;
	private double y = 50;
	private int fx = 50;
	int fy = 50;
	private double xa = 0;
	private double ya = 0;
	private int vdeg = 0;
	private int deg = 0;
	private int degv = 0;
	private int rdeg = 0;
	private double g = 0.01;
	private int ve = 0;
	private double ax = 0;
	private double ay = 0;
	private boolean stop = true;
	private boolean s = false;
	private char team = 'r';

	public FlyingThing2(Game game) {
		this.game = game;
		// x = game.getWidth() - 50;
		// y = game.getHeight() / 2;
		// System.out.println(x + " " + y);
		v = ve;
		Width = game.getflyWidth();
		Length = game.getflyLength();
		g = game.getG();
	}

	public void move() {
		v = (xa / Math.cos(Math.toRadians(vdeg)) + ya
				/ Math.sin(Math.toRadians(vdeg))) / 2;
		if (degv != 0) {
			if (a == 0) {
				deg = deg + (degv * 7);
			} else {
				deg = deg + degv;
			}
			if (deg >= 360)
				deg = deg - 360;
			else if (deg < 0)
				deg = 360 + deg;
		}
		if (!stop) {
			if (a != 0) {
				vdeg = deg;
			}
			// else if(a == 0 && v >= 0.5 + ve)
			// v = v -0.5;
			ax = a * Math.cos(Math.toRadians(vdeg));
			ay = g + a * Math.sin(Math.toRadians(vdeg));
			ya = (ay + ya);
			xa = (ax + xa);
			if (x + xa > 0 && x + xa < game.getWidth() - Width) {
				x = x + xa;
				fx = (int) x;
			} else
				xa = 0;
			if (y + ya > 0 && y + ya < game.getHeight() - Width) {
				y = y + ya;
				fy = (int) y;
			} else if (y == game.getHeight() - Width) {
				xa = 0;
				ya = 0;
			} else
				ya = 0;
		}
		if (stop) {
			xa = 0;
			ya = 0;
		}
		if (s) {
			Random generator = new Random();
			rdeg = (int) (generator.nextInt(6) - 3);
			rdeg = rdeg + deg;
			game.fire(team, fx + Length / 2, fy + Width / 2, rdeg);
		}

		// System.out.println("deg " + deg);
		// System.out.println("v " + v);
		// System.out.println("xa " + xa);
		// System.out.println("ya " + ya);
		// System.out.println("ax " + ax);
		// System.out.println("ay " + ay);
		// System.out.println("fx " + fx);
		// System.out.println("fy " + fy);
	}

	public void paint(Graphics2D g) {
		g.setTransform(AffineTransform.getRotateInstance(Math.toRadians(deg),
				fx + Length / 2, fy + Width / 2));
		g.fillRect(fx, fy, Length, Width);
		g.fillPolygon(new int[] { fx + Length, fx + Length,
				fx + Length + (Length / 3) }, new int[] { fy, fy + Width, fy + (Width / 2) }, 3);
		g.fillPolygon(new int[] { fx - (Length / 6), fx - (Length / 6),
				fx + (Length / 6) }, new int[] { fy, fy + Width, fy + (Width / 2) }, 3);
		if(a > 0){
			g.setColor(Color.YELLOW);
			g.fillPolygon(new int[] { fx - (Length / 6), fx - (Length / 6),
					fx - ((Length / 6) + Length) }, new int[] { fy, fy + Width, fy + (Width / 2) }, 3);
			g.setColor(Color.RED);
			g.fillPolygon(new int[] { fx - (Length / 6), fx - (Length / 6),
					fx - ((Length / 6) + (Length / 2)) }, new int[] { fy + (Width / 4), fy + Width - (Width / 4), fy + (Width / 2) }, 3);
		}
	}

	public Shape getBounds() {
		Rectangle rect = new Rectangle(fx, fy, Length, Width);
		AffineTransform at = AffineTransform.getRotateInstance(
				Math.toRadians(deg), fx + Length / 2, fy + Width / 2);
		Shape rotatedRect = at.createTransformedShape(rect);
		return rotatedRect;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A)
			degv = -1;
		if (e.getKeyCode() == KeyEvent.VK_D)
			degv = 1;
		if (e.getKeyCode() == KeyEvent.VK_W) {
			a = 0.5;
			stop = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S)
			stop = true;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			s = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
			degv = 0;
		if (e.getKeyCode() == KeyEvent.VK_W)
			a = 0;
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			s = false;
	}

	public int getDeg() {
		return deg;
	}

}
