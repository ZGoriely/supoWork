package supoWork.supo7.graham;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;

public class GrahamCount extends JFrame {
	
	private ArrayList<Point2D.Double> points;
	private PointsPanel panel;
	
	public GrahamCount(int n) {
		// Set up frame
		super("Graham Scan");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1000, 1000);
		
		// Create random points
		points = new ArrayList<Point2D.Double>();
		for (int i = 0; i < n; i++) {
			Point2D.Double p = new Point2D.Double(Math.random(), Math.random());
			points.add(p);
		}
		
		// Add points to panel
		add(createPointsPanel());
	}
	
	private PointsPanel createPointsPanel() {
		panel = new PointsPanel(points);
		return panel;
	}
	
	private ArrayList<Point2D.Double> CreateHull() {		
		// Find coordinate with lowest y value
		Point2D.Double lowest = points.get(0);
		for (Point2D.Double p : points) {
			if (p.getY() < lowest.getY()) lowest = p;
		}
		points.remove(lowest);
		
		// Sort points by angle
		Collections.sort(points, new AngleComparator(lowest));
		
		// Create hull by scanning lines
		ArrayList<Point2D.Double> hull = new ArrayList<Point2D.Double>();
		hull.add(lowest);
		for (Point2D.Double p : points) {
			boolean onLeft = false;
			while (hull.size() > 1 && !onLeft) {
				Line2D.Double extension = new Line2D.Double(hull.get(hull.size()-2), hull.get(hull.size()-1));
				onLeft = extension.relativeCCW(p) == 1;
				if (!onLeft) hull.remove(hull.size()-1);
			}
			hull.add(p);
		}
		hull.add(lowest);
		points.add(lowest);
		
		return hull;
	}
	
	public void drawHull () {
		ArrayList<Point2D.Double> hull = CreateHull();
		int count = hull.size()-1;
		panel.setPoints(points);
		panel.setHull(hull);
		panel.setCount(count);
	}
	
	public int getCount () {
		return CreateHull().size()-1;
	}
	
	public static void main (String[] args) {
		
		GrahamCount pointArray = new GrahamCount(50);
		pointArray.drawHull();
		pointArray.setVisible(true);
		/*
		int averageCount = 0;
		for (int i = 0; i < 30; i++) {
			averageCount += new GrahamCount(1000000).getCount();
		}
		System.out.println(averageCount/30);
		*/
	}

}
