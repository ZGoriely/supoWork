package supoWork.supo7.graham;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Comparator;

public class AngleComparator implements Comparator<Point2D.Double> {
	
	Point2D.Double src;

	public AngleComparator(Point2D.Double p) {
		src = p;
	}
	
	private int LeftOfExtension (Point2D.Double p, Point2D.Double q) {
		Line2D.Double extension = new Line2D.Double(src, p);
		return -extension.relativeCCW(q);
	}

	@Override
	public int compare(Point2D.Double p1, Point2D.Double p2) {
		return LeftOfExtension(p1, p2);
	}

}
