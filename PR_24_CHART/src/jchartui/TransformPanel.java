package jchartui;

/******************************************************************************
 *
 *	FILE NAME	: TransformPanel.java
 *
 *				  ���� ���Ͽ� TransformPanel Ŭ������
 *							  TransformDegreeChangeEvent
 *							  TransformDegreeChangeListener�� �Բ� ���ǵǾ� �ֽ�...
 *
 *				  ������ ������ ���� Ŭ������ ã�� ���ñ�...
 *
 ******************************************************************************/

/******************************************************************************
 *	PROJECT		: JCalc
 *	CLASS		: TransformPanel
 *	DESCRIPT	:
 *	AUTHOR		: ?
 *	CREATE DATE	: ?
 *
 *	LAST UPDATE	: 2000-08-12(WildRain)
 *					�̿ϼ��ε� ���� Custom Control...
 *					��û ���� �� ���ƾ� �� ��...
 *				  2000-08-15(WildRain)
 *					��û ���� �� ������.
 *					���� �̺�Ʈ�� ������ �߰�
 ******************************************************************************/

import java.util.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import jcosmos.Utility;

public class TransformPanel extends JPanel implements MouseListener, MouseMotionListener {

	int				x				= 100;
	int				y				=  50;
	Font			font			= new Font("Dialog", Font.PLAIN, 13);

	String			text			= "Text";
	Vector			vector			= new Vector();
	String			vectorStr;								// Vector���� ����Ǵ� ����

	int				baseX;					// ���� X
	int				baseY;					// ���� Y
	int				drawingX;				// �ؽ�Ʈ�� �׷��� X
	int 			drawingY;				// �ؽ�Ʈ�� �׷��� Y
	int				fontAscent;
	int				fontDescent;
	int				fontLeading;
	int				fontHeight;
	boolean			enabled			= true;
	double			theta			= 0;	// �ؽ�Ʈ�� ����
	int				dotInfoArray[][]= new int[13][5];
	boolean			isDotInfoArrayInit = false;

	private Vector	listeners = new Vector();	// �̺�Ʈ �����ʸ� ó���� ����

//=============================================================================
//	������
//=============================================================================
	public TransformPanel(){

		// Transformpanel���� ��Ʈ�� ũ�⳪ ������ �ٲ����� �����Ƿ�
		// ���⿡�� Dialog ��Ʈü�� ascent, decent, leading�� ���� ���´�.
		this.fontAscent		= 14;
		this.fontDescent	= 3;
		this.fontLeading	= 2;
		this.fontHeight		= 19;

		this.setLayout(null);

		setComponentsBounds();
		addListeners();

		setTextLayout();

	}

	public void setComponentsBounds(){

	}
	public void addListeners() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}

	public Dimension getPreferredSize() {
		return new Dimension(120, 200);
	}

	public void setEnabled(boolean bool) {

		super.setEnabled(bool);

		if (bool) {
			// �̺�Ʈ �����ʸ� �߰��Ѵ�.
			this.addListeners();
			// �ݿ����� �ѷ����� ������ ������ ���������� �ٲ۴�...
		}
		else {
			// �̺�Ʈ �����ʵ��� �����Ѵ�.
			this.removeMouseListener(this);
			this.removeMouseMotionListener(this);
			// �ݿ����� �ѷ����� ������ ������ ȸ������ �ٲ۴�...

		}
	}

	public void initDotInfoArray() {
		Point point;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 90);
		dotInfoArray[0][0] = -90;			// ����
		dotInfoArray[0][1] = point.x - 2;	// Dot�� ���� X
		dotInfoArray[0][2] = point.y - 2;	// Dot�� ���� Y
		dotInfoArray[0][3] = 5;				// WIDTH
		dotInfoArray[0][4] = 5;				// HEIGHT

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 75);
		dotInfoArray[1][0] = -75;
		dotInfoArray[1][1] = point.x - 1;
		dotInfoArray[1][2] = point.y - 1;
		dotInfoArray[1][3] = 2;
		dotInfoArray[1][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 60);
		dotInfoArray[2][0] = -60;
		dotInfoArray[2][1] = point.x - 1;
		dotInfoArray[2][2] = point.y - 1;
		dotInfoArray[2][3] = 2;
		dotInfoArray[2][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 45);
		dotInfoArray[3][0] = -45;
		dotInfoArray[3][1] = point.x - 2;
		dotInfoArray[3][2] = point.y - 2;
		dotInfoArray[3][3] = 5;
		dotInfoArray[3][4] = 5;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 30);
		dotInfoArray[4][0] = -30;
		dotInfoArray[4][1] = point.x - 1;
		dotInfoArray[4][2] = point.y - 1;
		dotInfoArray[4][3] = 2;
		dotInfoArray[4][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 15);
		dotInfoArray[5][0] = -15;
		dotInfoArray[5][1] = point.x - 1;
		dotInfoArray[5][2] = point.y - 1;
		dotInfoArray[5][3] = 2;
		dotInfoArray[5][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 0);
		dotInfoArray[6][0] = 0;
		dotInfoArray[6][1] = point.x - 2;
		dotInfoArray[6][2] = point.y - 2;
		dotInfoArray[6][3] = 5;
		dotInfoArray[6][4] = 5;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, -15);
		dotInfoArray[7][0] = 15;
		dotInfoArray[7][1] = point.x - 1;
		dotInfoArray[7][2] = point.y - 1;
		dotInfoArray[7][3] = 2;
		dotInfoArray[7][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, -30);
		dotInfoArray[8][0] = 30;
		dotInfoArray[8][1] = point.x - 1;
		dotInfoArray[8][2] = point.y - 1;
		dotInfoArray[8][3] = 2;
		dotInfoArray[8][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, -45);
		dotInfoArray[9][0] = 45;
		dotInfoArray[9][1] = point.x - 2;
		dotInfoArray[9][2] = point.y - 2;
		dotInfoArray[9][3] = 5;
		dotInfoArray[9][4] = 5;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, -60);
		dotInfoArray[10][0] = 60;
		dotInfoArray[10][1] = point.x - 1;
		dotInfoArray[10][2] = point.y - 1;
		dotInfoArray[10][3] = 2;
		dotInfoArray[10][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, -75);
		dotInfoArray[11][0] = 75;
		dotInfoArray[11][1] = point.x - 1;
		dotInfoArray[11][2] = point.y - 1;
		dotInfoArray[11][3] = 2;
		dotInfoArray[11][4] = 2;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, -90);
		dotInfoArray[12][0] = 90;
		dotInfoArray[12][1] = point.x - 2;
		dotInfoArray[12][2] = point.y - 2;
		dotInfoArray[12][3] = 5;
		dotInfoArray[12][4] = 5;


	}

//=============================================================================
//	�̺�Ʈ ó��
//=============================================================================
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	public void mousePressed(MouseEvent e){
		int		panelX		= e.getX();
		int		panelY		= e.getY();
		boolean isDotArea	= false;

		// �гο� �ѷ����� dot���� Ŭ������ �� �ڵ����� �ش� ������ �����ϱ� ���Ͽ�
		// dotInfoArray�� ������ ���鼭 ���� ���콺 Ŭ����ġ�� dot�� ��ġ���� �˻��ϰ�
		// dot area�ȿ��� ���콺 Ŭ���� �߻��ߴٸ� �ش� dot�� ������ �������ش�.
		for (int i=0; i<13; i++) {
			if (dotInfoArray[i][1] < panelX &&							// dotArea�� ���� X���� panelX�� ũ��
				dotInfoArray[i][2] < panelY &&							// dotArea�� ���� Y���� panelY�� ũ��
				dotInfoArray[i][1] + dotInfoArray[i][3] > panelX &&		// dotArea�� �� X���� panelX�� �۰�
				dotInfoArray[i][2] + dotInfoArray[i][4] > panelY ) {	// dotArea�� �� Y���� panelY�� ������...

				theta = dotInfoArray[i][0];
				isDotArea = true;										// ���⼭ theta���� ������ �̷������ �Ʒ������� ��ŵ�ϱ� ���Ͽ� ���Ǵ� �������� true�� �Է�
				break;
			}
		}

		// ���� for �������� theta�� �����Ǿ��ٸ� ���⼭�� ��ŵ�ϱ� ���ؼ�
		// isDotArea�� ���̸� ��ŵ...
		if (!isDotArea) theta = this.getTheta(baseX + 40, baseY, baseX, baseY, panelX, panelY);

		if (theta > 90)		theta = 90;
		if (theta < -90)	theta = -90;

		this.repaint();
		notifyListeners();
	}

	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}

	public void mouseDragged(MouseEvent e){
		int panelX = e.getX();
		int panelY = e.getY();

		// ������ xy, ���� xy, ���ڰ� ������ xy
		theta = this.getTheta(baseX + 40, baseY, baseX, baseY, panelX, panelY);

		if (theta >= 90)	theta = 90;
		if (theta <= -90)	theta = -90;

		this.repaint();
		notifyListeners();
	}

	public void setTextLayout(){
		for(int i=0; i<text.length() ; i++){
			vector.addElement(new String().valueOf(text.charAt(i))); // Vector�� 'text'�� �Ѱ��� �߶� �ִ´�.
			vectorStr = new String().valueOf(vector.elementAt(i));
		}
	}

//=============================================================================
//	���� ���ϱ� (standardXY - ������, cxy - �߽���, mxy - ���콺 ����Ʈ)
//=============================================================================
//	AutoShapeMold�� getTheta()�Լ��� �����Ͽ� �������.
	public double getTheta(int standardX, int standardY, int cx, int cy, int mx, int my){
		int		ox		= standardX - cx;		// ������ ���ϱ� ���� ������ ��ǥ�� �������� �̵�
		int		oy		= standardY - cy;
		int		omx		= mx - cx;				// ������ ���ϱ� ���� ȸ���̵��� ��ǥ�� �������� �̵�
		int		omy		= my - cy;
		int		detA	= ox * omy - oy * omx;	// ȸ�� ���� �Ǻ���, ������ ������ �ð����-����� �ݽð����

		// �巡�� �� �������� ȸ���� ���� ���
		double	theta	= (double)(Math.acos((ox*omx+oy*omy)/(Math.sqrt(ox*ox+oy*oy)*Math.sqrt(omx*omx+omy*omy))) *180/Math.PI);

		if (detA <= 0) theta = theta * -1;

		return theta;
	}

//=========================================================================
// AutoShapeMold�� getRotatePoint�Լ��� �����Ͽ� �������
//=========================================================================
	public Point getRotatedPoint(int x, int y, int cx, int cy, int theta){
							//   ---+-  ----+  -----+  -----+  -----+---
							//		|		|		|		|		+--- ->	����
							//		|		|		|		+----------- -> �߽����� Y
							//		|		|		+------------------- -> �߽����� X
							//		|		+--------------------------- -> �������� Y
							//		+----------------------------------- -> �������� X
							//
							//	������ theta�� �߽��� x, y���� ������ x, y��ŭ ������ ���� ��ǥ�� ���ϴ� �Լ�
		double r;
		double resultX;
		double resultY;

		r = theta*Math.PI/180;		// ȸ�������� ���� ������ ��ȯ

		resultX = (int)((x-cx)*Math.cos(r)+(y-cy)*Math.sin(r)+cx);
		resultY = (int)((y-cy)*Math.cos(r)-(x-cx)*Math.sin(r)+cy);

		return new Point((int)Math.round(resultX), (int)Math.round(resultY));
	}

//=============================================================================
//	Paint...
//=============================================================================
	Image offImage;
	boolean isFirst = true;
	public void paintComponent(Graphics g){
		if(this.isFirst) {
			offImage = this.createImage(this.getWidth(), this.getHeight());
			this.isFirst = false;
		}
		Graphics2D g2 = (Graphics2D)offImage.getGraphics();

		//Graphics2D	g2 = (Graphics2D)g;
		Point		point;

		this.baseX		= 10;
		this.baseY		= (int)(this.getHeight() /2);
		this.drawingX	= baseX;
		this.drawingY	= baseY + ((this.fontHeight - this.fontLeading) / 2) - this.fontDescent;

		// ȭ���� �������� ���� �����ϱ� ���� ��ó���� �ѹ� ��ü������ ĥ���ش�.
		g2.setColor(Color.white);
		g2.fillRect(0,0, this.getWidth(), this.getHeight());

		// �׸�������� �׷��ش�.
		g2.setColor(new Color(200, 200, 200));		// ȸ������...
		g2.drawLine(baseX, 0, baseX, this.getHeight());
		g2.drawLine(0, baseY, this.getWidth(), baseY);

		//�ձ� ������ ���� ����.
		g2.setColor(Color.black);

		// isDotInfoArrayInit�� false���... ��, dotInfoArray�� ������ �ԷµǾ����� �ʴٸ�
		// initDotInfoArray()�Լ��� ȣ���Ͽ� dotInfoArray�� ��Ʈ�� �׷��� ��ǥ�� ���� ������ �Է��ϰ�
		// isDotInfoArrayInit�� true�� ������ָ�... �� �ѹ��� dotInfoArray�� �ʱ�ȭ �ɰ��̴�!!!
		if (!isDotInfoArrayInit) {
			initDotInfoArray();
			isDotInfoArrayInit = true;
		}

		// ������ �ʱ�ȭ�� dot ������ �̿��Ͽ� �����̸� ���鼭 dot�� �׷��ش�.
		for (int i=0; i<13; i++) {
			if (this.isEnabled()) {
				if (dotInfoArray[i][0] == (int)theta)	g2.setColor(Color.red);
				else									g2.setColor(Color.black);
			}
			else g2.setColor(Color.gray);

			g2.fillRect(dotInfoArray[i][1], dotInfoArray[i][2], dotInfoArray[i][3], dotInfoArray[i][4]);
		}

		FontMetrics fm = this.getFontMetrics(font);
		int textLen = fm.stringWidth(text);
		int textAreaIncreaseX = 0;

		g2.setColor(Color.black);


		AffineTransform atf= AffineTransform.getRotateInstance(theta * Math.PI / 180, baseX, baseY);
		// ȸ����ȯ�� ����
		g2.setTransform(atf);
		for(int i =0; i < text.length(); i++){
			TextLayout textTl = new TextLayout(new String().valueOf(vector.elementAt(i)), font, new FontRenderContext(null, false, false));
			textTl.draw(g2, textAreaIncreaseX + drawingX, drawingY);
			textAreaIncreaseX = textAreaIncreaseX + fm.stringWidth(new String().valueOf(vector.elementAt(i)));
		}

//		System.out.println("����?? ���ε� �ȱ׷���??");

		//�������·� �����ش�.
		g2.setTransform(new AffineTransform());
		g.drawImage(offImage, 0, 0, this);

	}

	public double getDegree() {
		return this.theta * -1;
	}

	public void setDegree(double degree) {
		this.theta = degree * -1;
	}

	public void setDegree(int degree) {
		this.theta = (double)(degree * -1);
	}

	/**
	 * Panel�� ������ ���� ����
	 */
	public final void setDegreeValue(double degree) {

        this.theta = degree * -1;

        repaint();
    }

//=============================================================================
//	TransformDegreeChange �̺�Ʈ�� �߻���Ű�� ���Ͽ�...
//=============================================================================
	public void addTransformDegreeChangeListener(TransformDegreeChangeListener listener) {
		if (!listeners.contains(listener)) listeners.addElement(listener);
	}

	public void removeTransformDegreeChangeListener(TransformDegreeChangeListener listener) {
		listeners.removeElement(listener);
	}

	private void notifyListeners() {
		Vector copyOfListeners = (Vector)(listeners.clone());
		TransformDegreeChangeEvent event = new TransformDegreeChangeEvent(this, this.theta);

		Enumeration enumIt = copyOfListeners.elements();
		while (enumIt.hasMoreElements()) {
			TransformDegreeChangeListener listener = (TransformDegreeChangeListener)enumIt.nextElement();
			listener.transformDegreeChanged(event);
		}
	}

}



