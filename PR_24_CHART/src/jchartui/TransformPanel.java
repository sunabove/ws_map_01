package jchartui;

/******************************************************************************
 *
 *	FILE NAME	: TransformPanel.java
 *
 *				  현재 파일에 TransformPanel 클래스와
 *							  TransformDegreeChangeEvent
 *							  TransformDegreeChangeListener가 함께 정의되어 있슴...
 *
 *				  엉뚱한 곳에서 위의 클래스를 찾지 마시길...
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
 *					미완성인듯 싶은 Custom Control...
 *					엄청 많이 손 보아야 할 듯...
 *				  2000-08-15(WildRain)
 *					엄청 많이 손 보았음.
 *					전용 이벤트와 리스너 추가
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
	String			vectorStr;								// Vector내에 저장되는 문자

	int				baseX;					// 원점 X
	int				baseY;					// 원점 Y
	int				drawingX;				// 텍스트가 그려질 X
	int 			drawingY;				// 텍스트가 그려질 Y
	int				fontAscent;
	int				fontDescent;
	int				fontLeading;
	int				fontHeight;
	boolean			enabled			= true;
	double			theta			= 0;	// 텍스트의 각도
	int				dotInfoArray[][]= new int[13][5];
	boolean			isDotInfoArrayInit = false;

	private Vector	listeners = new Vector();	// 이벤트 리스너를 처리할 벡터

//=============================================================================
//	생성자
//=============================================================================
	public TransformPanel(){

		// Transformpanel에서 폰트의 크기나 종류가 바뀔일은 없으므로
		// 여기에서 Dialog 폰트체의 ascent, decent, leading를 구해 놓는다.
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
			// 이벤트 리스너를 추가한다.
			this.addListeners();
			// 반원으로 둘러쌓인 점들의 색깔을 검정색으로 바꾼다...
		}
		else {
			// 이벤트 리스너들을 제거한다.
			this.removeMouseListener(this);
			this.removeMouseMotionListener(this);
			// 반원으로 둘러쌓인 점들의 색깔을 회색으로 바꾼다...

		}
	}

	public void initDotInfoArray() {
		Point point;

		point = this.getRotatedPoint(baseX + 55, baseY, baseX, baseY, 90);
		dotInfoArray[0][0] = -90;			// 각도
		dotInfoArray[0][1] = point.x - 2;	// Dot의 시작 X
		dotInfoArray[0][2] = point.y - 2;	// Dot의 시작 Y
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
//	이벤트 처리
//=============================================================================
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	public void mousePressed(MouseEvent e){
		int		panelX		= e.getX();
		int		panelY		= e.getY();
		boolean isDotArea	= false;

		// 패널에 둘러쌓인 dot들을 클릭했을 때 자동으로 해당 각도를 지정하기 위하여
		// dotInfoArray를 뺑뺑이 돌면서 현재 마우스 클릭위치가 dot의 위치인지 검사하고
		// dot area안에서 마우스 클릭이 발생했다면 해당 dot의 각도를 지정해준다.
		for (int i=0; i<13; i++) {
			if (dotInfoArray[i][1] < panelX &&							// dotArea의 시작 X보다 panelX가 크고
				dotInfoArray[i][2] < panelY &&							// dotArea의 시작 Y보다 panelY가 크고
				dotInfoArray[i][1] + dotInfoArray[i][3] > panelX &&		// dotArea의 끝 X보다 panelX가 작고
				dotInfoArray[i][2] + dotInfoArray[i][4] > panelY ) {	// dotArea의 끝 Y보다 panelY가 작으면...

				theta = dotInfoArray[i][0];
				isDotArea = true;										// 여기서 theta각도 지정이 이루어지면 아래에서는 스킵하기 위하여 사용되는 변수에다 true를 입력
				break;
			}
		}

		// 위의 for 루프에서 theta가 지정되었다면 여기서는 스킵하기 위해서
		// isDotArea가 참이면 스킵...
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

		// 기준점 xy, 원점 xy, 글자가 적히는 xy
		theta = this.getTheta(baseX + 40, baseY, baseX, baseY, panelX, panelY);

		if (theta >= 90)	theta = 90;
		if (theta <= -90)	theta = -90;

		this.repaint();
		notifyListeners();
	}

	public void setTextLayout(){
		for(int i=0; i<text.length() ; i++){
			vector.addElement(new String().valueOf(text.charAt(i))); // Vector에 'text'를 한개씩 잘라서 넣는다.
			vectorStr = new String().valueOf(vector.elementAt(i));
		}
	}

//=============================================================================
//	각도 구하기 (standardXY - 기준점, cxy - 중심점, mxy - 마우스 포인트)
//=============================================================================
//	AutoShapeMold의 getTheta()함수를 변형하여 만들었음.
	public double getTheta(int standardX, int standardY, int cx, int cy, int mx, int my){
		int		ox		= standardX - cx;		// 각도를 구하기 위해 기준점 좌표를 원점으로 이동
		int		oy		= standardY - cy;
		int		omx		= mx - cx;				// 각도를 구하기 위해 회전이동점 좌표를 원점으로 이동
		int		omy		= my - cy;
		int		detA	= ox * omy - oy * omx;	// 회전 방향 판별식, 음수가 나오면 시계방향-양수면 반시계방향

		// 드래그 될 때마다의 회전된 각도 계산
		double	theta	= (double)(Math.acos((ox*omx+oy*omy)/(Math.sqrt(ox*ox+oy*oy)*Math.sqrt(omx*omx+omy*omy))) *180/Math.PI);

		if (detA <= 0) theta = theta * -1;

		return theta;
	}

//=========================================================================
// AutoShapeMold의 getRotatePoint함수를 변형하여 만들었음
//=========================================================================
	public Point getRotatedPoint(int x, int y, int cx, int cy, int theta){
							//   ---+-  ----+  -----+  -----+  -----+---
							//		|		|		|		|		+--- ->	각도
							//		|		|		|		+----------- -> 중심점의 Y
							//		|		|		+------------------- -> 중심점의 X
							//		|		+--------------------------- -> 기준점의 Y
							//		+----------------------------------- -> 기준점의 X
							//
							//	각도가 theta인 중심점 x, y에서 기준점 x, y만큼 떨어진 곳의 좌표를 구하는 함수
		double r;
		double resultX;
		double resultY;

		r = theta*Math.PI/180;		// 회전각도를 라디안 값으로 변환

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

		// 화면이 깨어지는 것을 방지하기 위해 맨처음에 한번 전체적으로 칠해준다.
		g2.setColor(Color.white);
		g2.fillRect(0,0, this.getWidth(), this.getHeight());

		// 그리드라인을 그려준다.
		g2.setColor(new Color(200, 200, 200));		// 회색으로...
		g2.drawLine(baseX, 0, baseX, this.getHeight());
		g2.drawLine(0, baseY, this.getWidth(), baseY);

		//둥근 원위에 찍힌 점들.
		g2.setColor(Color.black);

		// isDotInfoArrayInit가 false라면... 즉, dotInfoArray에 정보가 입력되어있지 않다면
		// initDotInfoArray()함수를 호출하여 dotInfoArray에 도트가 그려질 좌표와 각도 정보를 입력하고
		// isDotInfoArrayInit를 true로 만들어주면... 딱 한번만 dotInfoArray가 초기화 될것이다!!!
		if (!isDotInfoArrayInit) {
			initDotInfoArray();
			isDotInfoArrayInit = true;
		}

		// 위에서 초기화된 dot 정보를 이용하여 뺑뺑이를 돌면서 dot를 그려준다.
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
		// 회전변환을 설정
		g2.setTransform(atf);
		for(int i =0; i < text.length(); i++){
			TextLayout textTl = new TextLayout(new String().valueOf(vector.elementAt(i)), font, new FontRenderContext(null, false, false));
			textTl.draw(g2, textAreaIncreaseX + drawingX, drawingY);
			textAreaIncreaseX = textAreaIncreaseX + fm.stringWidth(new String().valueOf(vector.elementAt(i)));
		}

//		System.out.println("뭐야?? 라인도 안그려져??");

		//원래상태로 돌려준다.
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
	 * Panel에 보여줄 값을 설정
	 */
	public final void setDegreeValue(double degree) {

        this.theta = degree * -1;

        repaint();
    }

//=============================================================================
//	TransformDegreeChange 이벤트를 발생시키기 위하여...
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



