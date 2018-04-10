package supoWork.supo7.graham;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PointsPanel extends JPanel {
	
	private ArrayList<Point2D.Double> points;
	private ArrayList<Point2D.Double> hull; 
	private String count;
	private int dotThickness = 4;
	private int offset = 50;

	public PointsPanel(ArrayList<Point2D.Double> p) {
		setPoints(p);
	}
	
	public void setPoints (ArrayList<Point2D.Double> p) {
		points = p;
	}
	
	public void setHull (ArrayList<Point2D.Double> p) {
		hull = p;
	}
	
	public void setCount (int c) {
		count = Integer.toString(c);
	}

	@Override
	protected void paintComponent(java.awt.Graphics gr) {
		
		Graphics2D g = (Graphics2D) gr;
		
		// Get some useful values
		int sWidth = this.getWidth()-offset*2;
		int sHeight = this.getHeight()-offset*2;

		// Paint the background white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, sWidth, sHeight);
		
		int d = 0;
		if (points != null) {
			g.setColor(Color.BLACK);
			for (Point2D p : points) {
				g.fillOval((int)(p.getX()*sWidth)+offset, (int)(p.getY()*sHeight)+offset, dotThickness, dotThickness);
				//g.drawString(Integer.toString(d++), (int)(p.getX()*sWidth), (int)(p.getY()*sHeight));
			}
		}
		
		if (hull != null) {
			g.setColor(Color.RED);
			BasicStroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10f, new float[] {5, 5}, 5f);
			g.setStroke(dashed);
			/*
			for (int i = 0; i < points.size()-1; i++) {
				Point2D p1 = points.get(i);
				Point2D p2 = points.get(points.size()-1);
				g.drawLine((int)(p1.getX()*sWidth), (int)(p1.getY()*sHeight), (int)(p2.getX()*sWidth), (int)(p2.getY()*sHeight));
			}
			*/
			g.setColor(Color.BLUE);
			g.setStroke(new BasicStroke(5));
			for (int i = 0; i < hull.size()-1; i++) {
				Point2D p1 = hull.get(i);
				Point2D p2 = hull.get(i+1);
				g.drawLine((int)(p1.getX()*sWidth)+offset, (int)(p1.getY()*sHeight)+offset, (int)(p2.getX()*sWidth)+offset, (int)(p2.getY()*sHeight)+offset);
			}
		}
		
		if (count != null) {
			g.setColor(Color.BLACK);
			g.drawString("Corner Count: " + count, 10, 10);
		}
		
	}

}
