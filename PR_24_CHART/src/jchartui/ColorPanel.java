//package com.techdigm.chart.editor;
package jchartui;

/******************************************************************************

	PROJECT		: JCalc
	CLASS		: ColorPanel
	DESCRIPT	:
	CREATE DATE	: 2000, 1, 29
	AUTHOR		: 김세훈

		기존에 만들어 쓰던 ColorPanel의 문제점을 보완하여
		ActionEvent를 발생시키는 ColorPanel을 새로만듬.
		현제까지 발견된 문제점 - 마우스 Press후 화면영역을 벋어날 경우 cancel되야하나,
								 Press된 상황에서의 색을 받아온다.(미비한 문제.)

	LAST UPDATE	: 2000-08-08(WildRain)
		Column과 Row 크기를 지정하고 Color배열을 받아서 생성하는 생성자 추가
********************************************************************************/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;

class ColorPanel extends JPanel{
	public String name = null;
	public ActionListener actionListener = null;
	public Color basicColor[] = {		//Color 배열 - 기본색지정
			new Color(0  ,0    , 0),	//first row
			new Color(156,49   , 0),
			new Color(49 ,49   , 0),
			new Color(0  ,49   , 0),
			new Color(0  ,49  , 99),
			new Color(0  ,0  , 132),
			new Color(49 ,49 , 156),
			new Color(49 ,49  , 49),

			new Color(132,  0   ,0),	//second row
			new Color(255, 99   ,0),
			new Color(132,132   ,0),
			new Color(0  ,132   ,0),
			new Color(0  ,132, 132),
			new Color(0  ,  0, 255),
			new Color(99 , 99, 156),
			new Color(132,132, 132),

			new Color(255,  0 , 0),		//third row
			new Color(255,156,  0),
			new Color(156,206,  0),
			new Color(49 ,156, 99),
			new Color(49 ,206,206),
			new Color(49 , 99,255),
			new Color(132,  0,132),
			new Color(148,148,148),

			new Color(255,  0,255),		//fouth row
			new Color(255,206,  0),
			new Color(255,255,  0),
			new Color(0  ,255,  0),
			new Color(0  ,255,255),
			new Color(0  ,206,255),
			new Color(156, 49, 99),
			new Color(192,192,192),

			new Color(255,156,206),		//fifth row
			new Color(255,206,156),
			new Color(255,255,156),
			new Color(206,255,206),
			new Color(206,255,255),
			new Color(156,206,255),
			new Color(206,156,255),
			new Color(255,255,255)

			};

	protected Border m_unselectedBorder;
	protected Border m_selectedBorder;
	protected Border m_activeBorder;

	protected Hashtable m_panes;
	protected ColorPane m_selected;

//=============================================================================
//	생성자
//=============================================================================
	public ColorPanel() {
		//this.name = name;
		//super(name);

		this.borderSetUp();

		JPanel p = new JPanel();
		//p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setBorder(new EmptyBorder(1, 1, 1, 1));
		p.setLayout(new GridLayout(5, 8, 2, 2));
		m_panes = new Hashtable();

		for(int y =0; y<5; y++) {
			for(int x=0; x<8; x++) {
				Color c = (basicColor[y*8+x]);
				ColorPane pn = new ColorPane(c);
				p.add(pn);
				m_panes.put(c, pn);
			}
		}

		add(p);
		//this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}

//=============================================================================
//	Color와 Row, Column을 받아서 생성하는 생성자 - WildRain 추가
//=============================================================================
	public ColorPanel(int row, int column, Color color[][]) {

		this.borderSetUp();

		JPanel p = new JPanel();
		//p.setBorder(new EmptyBorder(5, 5, 5, 5));
		p.setBorder(new EmptyBorder(1, 1, 1, 1));
		p.setLayout(new GridLayout(row, column, 2, 2));
		m_panes = new Hashtable();

		for(int y =0; y<row; y++) {
			for(int x=0; x<column; x++) {
				Color c = (color[x][y]);
				ColorPane pn = new ColorPane(c);
				p.add(pn);
				m_panes.put(c, pn);
			}
		}

		add(p);

//		setDefaultColor(color[1][1]);
//		setColor(color[0][0]);

	}

	void borderSetUp() {
		m_unselectedBorder	= new CompoundBorder(
									new MatteBorder(1, 1, 1, 1, getBackground()),
									new BevelBorder(BevelBorder.LOWERED,
									Color.white, Color.gray));
		m_selectedBorder	= new CompoundBorder(
									//new MatteBorder(2, 2, 2, 2, Color.red),
									new MatteBorder(2, 2, 2, 2, Color.blue),
									new MatteBorder(1, 1, 1, 1, getBackground()));
		m_activeBorder		= new CompoundBorder(
									//new MatteBorder(2, 2, 2, 2, Color.blue),
									new MatteBorder(2, 2, 2, 2, UIManager.getColor("Button.focus")),
									new MatteBorder(1, 1, 1, 1, getBackground()));
	}

//=============================================================================
//	컬러 선택 초기값 지정
//=============================================================================
	public void setDefaultColor(Color color) {
		setColor(color);
		m_selected.setBorder(m_selectedBorder);
	}

/*	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}
*/
	public void setColor(Color c) {
		Object obj = m_panes.get(c);
		if (obj == null)
			return;
		if (m_selected != null)
			m_selected.setSelected(false);

		m_selected = (ColorPane)obj;
		m_selected.setSelected(true);
	}

	public Color getColor() {
		if (m_selected == null)
			return null;
		return m_selected.getColor();
	}

	// 외부에서 리스너를 부착하기 위함
	public void addActionListener(ActionListener listener) {
		actionListener = AWTEventMulticaster.add(actionListener,listener);
		//enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}

	// 외부에서 리스터를 제거하기 위함
	public void removeActionListener(ActionListener listener) {
		actionListener = AWTEventMulticaster.remove(actionListener,listener);
	}

	public void fireListener(ActionEvent e) {
		if(actionListener != null)
			actionListener.actionPerformed(e);
	}

	public void doSelection() {
		//fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getActionCommand()));
		actionListener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED, name));
		//this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);
	}


	class ColorPane extends JPanel implements MouseListener {
		protected Color m_c;
		protected boolean m_selected;

		public ColorPane(Color c) {
			m_c = c;
			setBackground(c);
			setBorder(m_unselectedBorder);
			//this.setBorderPainted(false);
			String msg = "R "+c.getRed()+", G "+c.getGreen()+
			  ", B "+c.getBlue();
			setToolTipText(msg);
			addMouseListener(this);
			//addActionListener(this);
		}

		public Color getColor() { return m_c; }

		public Dimension getPreferredSize() {
			return new Dimension(16, 16);
			//return new Dimension(18, 18);
		}
		public Dimension getMaximumSize() { return getPreferredSize(); }
		public Dimension getMinimumSize() { return getPreferredSize(); }

		public void setSelected(boolean selected) {
			m_selected = selected;
			if (m_selected)
				//setBorder(m_selectedBorder);
				setBorder(m_unselectedBorder);
				//setBorderPainted(true);
			else
				//setBorderPainted(false);
				setBorder(m_unselectedBorder);
		}

		public boolean isSelected() { return m_selected; }

		public void mousePressed(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {
			setColor(m_c);
			MenuSelectionManager.defaultManager().clearSelectedPath();
			doSelection();
		}

		public void mouseEntered(MouseEvent e) {
			setBorder(m_activeBorder);
			//setBorderPainted(true);
		}

		public void mouseExited(MouseEvent e) {
			//setBorder(m_selected ? m_selectedBorder : m_unselectedBorder);

			setBorder(m_selected ? m_selectedBorder : m_unselectedBorder);
			//setBorderPainted(m_selected ? true : false);
		}
	}
}