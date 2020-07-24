package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.Vector;
import jcosmos.*;

public class Test3 extends Panel {

	int x = 50, y = 50, w = 200, h = 200;

	double start = 30, extent = 120;

	Shape arc = getArc();

	private Label label = new Label( "coords" );
	private Label labelForAngel = new Label( "angle");
	private Button rot = new Button( "Rot" );
	private Button incAngle = new Button( "+" );
	private Button decAngle = new Button( "-" );

	private Arc2D getArc() {

		return new Arc2D.Double( x, y, w, h, start, extent, Arc2D.PIE );

	}

	public Test3() {

		setLayout( new BorderLayout() );
		add( label, BorderLayout.SOUTH );
		add( label, BorderLayout.SOUTH );
		add( rot, BorderLayout.EAST );
		add( incAngle, BorderLayout.WEST );
		add( decAngle, BorderLayout.NORTH );

		addMouseMotionListener( new MouseMotionAdapter() {

			public void mouseMoved(MouseEvent e) {

			Dimension size = getSize();

			label.setText( "" + e.getX() + ", " + e.getY() );

			}

			} );

		rot.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			start += 10;

			arc = getArc();

			repaint();

			}
			});

		incAngle.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			extent += 10;

			arc = getArc();

			repaint();

			}
			});

		decAngle.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {

			extent -= 10;

			arc = getArc();

			repaint();

			}
			});

	}

	public void paint(Graphics g) {

		AffineTransform at = AffineTransform.getScaleInstance(1.3, 1.5);
		arc = at.createTransformedShape(arc);


		Rectangle2D arcBounds = arc.getBounds2D();

		double ax = arcBounds.getX(), ay = arcBounds.getY(),
			aw = arcBounds.getWidth(), ah = arcBounds.getHeight();

		Rectangle2D rect = new Rectangle2D.Double( ax, ay - 4, aw, ah + 8);


		Graphics2D g2 = (Graphics2D) g;

		g2.draw( arc );
		Point2D [] p = ShapeUtilities.getPieControlPoints( arc );
		int x1 = (int) p[0].getX(), x2 = (int) p[1].getX(), x3 = (int) p[2].getX();
		int y1 = (int) p[0].getY(), y2 = (int) p[1].getY(), y3 = (int) p[2].getY();

		g2.setColor( Color.red );
		g2.fillRect(x1 - 2, y1 - 2, 5, 5);  // root --> red
		g2.setColor( Color.yellow );
		g2.fillRect(x2 - 2, y2 - 2, 5, 5);  // other --> yellow
		g2.setColor( Color.blue );
		g2.fillRect(x3 - 2, y3 - 2, 5, 5);  //  feedback --> blue

		g2.draw( rect );

		//Rectangle2D.Double rec = Utility.getBoundOfArc( p[2], p[0], p[1]);

		//g2.setColor( Color.green );
		//g2.draw( rec);


		Line2D.Double line1 = new Line2D.Double();
		Line2D.Double line2 = new Line2D.Double();
		line1.setLine( x1, y1, x1, y3);
		line2.setLine( x3, y3, x1, y3);

		g2.setColor( Color.blue );
		g2.draw(line1);
		g2.draw(line2);

//		double angle1 = Utility.getAngle(1, 0);
//		double angle2 = Utility.getAngle(0, 1);
//		double angle3 = Utility.getAngle(-1, 0);
//		double angle4 = Utility.getAngle(0, -1);
//		System.out.println( "Angle 1 :" + Unit.convertRadianToDegree( angle1 ) );
//		System.out.println( "Angle 2 :" + Unit.convertRadianToDegree( angle2 ) );
//		System.out.println( "Angle 3 :" + Unit.convertRadianToDegree( angle3 ) );
//		System.out.println( "Angle 4 :" + Unit.convertRadianToDegree( angle4 ) );

		double angle = Utility.getAngle(x3, y3, x1, y1);
		double angleForArc = Utility.getAngleForArc(x3, y3, x1, y1);
		double withinAngle = Utility.getAngleForArc(x3, y3, x2, y2, x1, y1);

		label.setText( "angle : " + angle + ",   angleForArc : " + angleForArc + ", withinAngle : " + withinAngle);

//		System.out.println( "Angle 1 :" + Unit.convertRadianToDegree( angle ) );
	}





	public Point [] getVertexesPathIterator(Shape shape ) {

		Vector vertexes = new Vector();

		PathIterator pi = shape.getPathIterator( null ); // path iterator

		while( ! pi.isDone() ) {

			double [] coords = new double[ 6 ];

			Point vertex = null;

			int type = pi.currentSegment( coords );

			switch( type ) {

				case PathIterator.SEG_MOVETO :
					print( "MOVE_TO", coords );
					vertex = new Point( (int) coords[0], (int) coords[1] );
					break;

				case PathIterator.SEG_LINETO :
					print( "LINE_TO", coords );
					vertex = new Point( (int) coords[0], (int) coords[1] );
					break;

				case PathIterator.SEG_QUADTO :
					print( "QUAD_TO", coords );
					vertex = new Point( (int) coords[0], (int) coords[1] );
					break;

				case PathIterator.SEG_CUBICTO :
					print( "CUBIC_TO", coords );
					vertex = new Point( (int) coords[4], (int) coords[5] );
					break;

				case PathIterator.SEG_CLOSE :
					print( "CLOSE", coords );
					vertex = null;
					break;

			}

			if( vertex != null ) {
				vertexes.addElement( vertex );
			}

			pi.next();

		}

		Point [] points = new Point[ 3 ];

		int size = vertexes.size();
		Utility.debug(this, "" + size);

		points[ 0 ] = (Point) vertexes.elementAt(0);            // otherVertex
		points[ 1 ] = (Point) vertexes.elementAt(size - 2);     // feedbackVertex
		points[ 2 ] = (Point) vertexes.elementAt(size - 1);     // rootVertex

		return points;



	}

	public void print(String type, double coords [] ) {
		String msg = type + " : ";

		for(int i = 0; i < 6; i ++ ) {

			msg += coords[i] + ", " ;

		}

		System.out.println( msg );

	}

	public static void main(String[] args) {

		Frame f = new Frame();
		f.setSize( 500, 500 );
		f.setLayout( new BorderLayout( 1, 1) );

		Test3 test = new Test3();
		f.add( test, BorderLayout.CENTER );
		f.setVisible( true );

	}

}