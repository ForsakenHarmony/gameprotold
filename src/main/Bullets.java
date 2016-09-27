package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Bullets {

	private int Diameter = 10;
	private double a = 0;
	private double v = 0;
	private double x = 0;
	private double y = 0;
	private int fx = 0;
	private int fy = 0;
	private double xa = 0;
	private double ya = 0;
	private int vdeg = 0;
	private double g = 0.01;
	private double ax = 0;
	private double ay = 0;
	private Game game;
	private int n;
	private char team;

	// private double t;

	public Bullets(Game game, char nteam, int nx, int ny, int ndeg, int nn) {
		this.game = game;
		g = game.getG();
		Diameter = game.getShDia();
		this.v = game.getShV();
		this.x = nx;
		this.y = ny;
		this.vdeg = ndeg;
		this.xa = v * Math.cos(Math.toRadians(vdeg));
		this.ya = v * Math.sin(Math.toRadians(vdeg));
		this.team = nteam;
		this.n = nn;
	}

	public void unsetMoving() {
		game.delete(n, team);
	}

	public void move() {
		// t++;
		// if(t > 200)
		// this.unsetMoving();
		v = (xa / Math.cos(Math.toRadians(vdeg)) + ya
				/ Math.sin(Math.toRadians(vdeg))) / 2;
		// System.out.println(v);
		ax = a * Math.cos(Math.toRadians(vdeg));
		ay = g + a * Math.sin(Math.toRadians(vdeg));
		ya = ay + ya;
		xa = ax + xa;
		if (x + xa > 0 && x + xa < game.getWidth() - Diameter) {
			x = x + xa;
			fx = (int) x;
		} else {
			xa = 0;
			this.unsetMoving();
		}
		if (y + ya > 0 && y + ya < game.getHeight() - Diameter) {
			y = y + ya;
			fy = (int) y;
		} else {
			ya = 0;
			this.unsetMoving();
		}
		if (collision()) {
			this.unsetMoving();
			game.addScore(team);
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

	private boolean collision() {
		boolean collision = false;
		switch (team) {
		case ('b'):
			collision = game.fly2.getBounds().intersects(
					(Rectangle2D) this.getBounds());
			break;
		case ('r'):
			collision = game.fly.getBounds().intersects(
					(Rectangle2D) this.getBounds());
			break;
		}
		return collision;
	}

	public Shape getBounds() {
		Rectangle cir = new Rectangle(fx, fy, Diameter, Diameter);
		return cir;
	}

	public void paint(Graphics2D g) {
		g.setTransform(AffineTransform.getRotateInstance(0, 0, 0));
		g.fillOval(fx, fy, Diameter, Diameter);

	}
}
