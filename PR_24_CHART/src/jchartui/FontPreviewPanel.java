//package com.techdigm.chart.editor;
package jchartui;

/******************************************************************************
 *	PROJECT		: JCalc
 *	CLASS		: FontPreview
 *	DESCRIPT	: 글꼴 미리보기... FormatCells 다이얼로그의 Font 탭에서 사용함.
 *	CREATE DATE	: 2000-04-??
 *	AUTHOR		: WildRain
 *
 *	LAST UPDATE	: 2000-08-09(WildRain)
 ******************************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.FontMetrics;
//import com.techdigm.editor.DefaultFont;
import jcosmos.DefaultFont;

//import jcalc.Metinfo;
import jcosmos.Metinfo;

public class FontPreviewPanel extends JPanel {

	public static final byte	SCRIPT_NONE			= 0;
	public static final byte	SCRIPT_SUPER		= 1;
	public static final byte	SCRIPT_SUB			= 2;
	public static final byte	UNDERLINE_NONE		= 0;
	public static final byte	UNDERLINE_SINGLE	= 1;
	public static final byte	UNDERLINE_DOUBLE	= 2;
	public static final byte	UNDERLINE_ACCSINGLE	= 3;
	public static final byte	UNDERLINE_ACCDOUBLE	= 4;


	Font	thisFont;			// Font
	String	fontName;			// Font Name
	int		fontStyle;			// Font Style
	boolean fontBold;
	boolean fontItalic;
	int		fontSize;			// Font Size
	byte	underline;			// Underline
	Color	fontColor;			// Color
	byte	script;				// SubScript, SuperScript
	boolean	strikeThrough;

	public String	string	= "AaBbCcYyZz";
//=============================================================================
//	생성자
//=============================================================================
	public FontPreviewPanel(int width, int height) {
		thisFont = DefaultFont.FONT;
		setSize(width, height);
		setBackground(Color.white);
		//Border fontPanelBorder = BorderFactory.createLineBorder(Color.black);
	}

	//일반 생성자...
	public FontPreviewPanel() {
		thisFont = DefaultFont.FONT;
		setBackground(Color.white);
	}
//=============================================================================
//	페인트
//=============================================================================
	public void paintComponent (Graphics g) {
        super.paintComponent( g );
		Graphics2D g2 = (Graphics2D) g;

		//-------------------------------------------------
		// baseX와 baseY를 구하기 위해서는 그려질 문자열의 width와 height가 필요함
		//-------------------------------------------------

		int			baseX;		// 기준선을 그릴 기준좌표로써 원래 크기의 폰트에 해당하는 좌표가 저장된다.
		int			baseY;
		int			baseAscent;
		int			baseDescent;
		int			baseLeading;
		int			baseHeight;
		int			baseWidth;
		int			drawingX;	// 문자를 그릴좌표로서 Script여부에 따라 영향을 받는다.
		int			drawingY;
		Rectangle2D	fontRec;
		int			baseLineEndX;	// 앞쪽 기준선의 끝 x좌표
		int			baseLineStartX;	// 뒷쪽 기준선의 시작 x좌표
		Metinfo		metInfo;

		//---------------------------------------------
		// Font 설정
		// Script 속성에 따라서 Font의 크기를 지정해야한다.
		//---------------------------------------------
		Font font	= new Font(this.fontName, this.fontStyle, this.fontSize);
		metInfo		= new Metinfo(font);
		fontRec		= font.getStringBounds(this.string, g2.getFontRenderContext());
		baseAscent	= metInfo.getAscent();	// 기본폰트의 Ascent
		baseDescent = metInfo.getDescent();	// 기본폰트의 Descent
		baseLeading = metInfo.getLeading();	// 기본폰트의 Leading
		baseHeight	= metInfo.getHeight();	// 기본폰트의 Height
		baseWidth	= (int)fontRec.getWidth();

		//-------------------------------------------------
		// baseX와 baseY를 정한다.
		//-------------------------------------------------
		baseX		= (this.getWidth() - baseWidth) / 2;
		baseY		= ((this.getHeight() - baseHeight) / 2) + baseAscent;
		drawingX	= 0;
		drawingY	= 0;
		//-------------------------------------------------
		// 기준선의 좌표를 정한다.
		//-------------------------------------------------
		if (baseX > 40) {
			baseLineEndX = 30;
			baseLineStartX = this.getWidth() - 30;
		}
		else {
			if ((baseX - 10) <= 0) {
				baseLineEndX = 0;
				baseLineStartX = this.getWidth();
			}
			else {
				baseLineEndX = baseX - 10;
				baseLineStartX = this.getWidth() - baseLineEndX;
			}
		}

		// SUBSCRIPT 일때
		switch (this.script) {
			case SCRIPT_SUPER :
				// 폰트의 크기 조정
				font = font.deriveFont(font.getSize() * 0.7f);
				// 새로지정된 폰트의 MetInfo를 구한다.
				metInfo = new Metinfo(font);
				// Super Script일때는 기준값에서 -(마이너스)한다.
				drawingY = (int)((baseY - baseAscent - (baseHeight * 0.02) + metInfo.getAscent()) - baseY);
				break;
			// SUPERSCIPT 일때
			case SCRIPT_SUB :
				font = font.deriveFont(font.getSize() * 0.7f);
				metInfo = new Metinfo(font);
				// 폰트의 크기변화에 따른 Y좌표 제설정이 있어야한다.
				// UNDERLINE 은 원래의 y위치에 그어져야하므로
				// 문자를 그리는 Y좌표와 Underbar를 그리는 Y좌표가 따로 있어야 한다...
				drawingY = (int)(baseHeight * 0.12);
		}

		fontRec		= font.getStringBounds(this.string, g2.getFontRenderContext());
		baseX	= (int)((this.getWidth() - fontRec.getWidth()) / 2);

		//-------------------------------------------------
		// 문자열 그리기
		//-------------------------------------------------
		// Color 지정
		g2.setColor(this.fontColor);
		// Font 지정
		g2.setFont(font);
		g2.drawString(this.string, baseX, baseY + drawingY);

		//-------------------------------------------------
		// 기준선 그리기
		//-------------------------------------------------
		g2.drawLine(0, baseY, baseLineEndX, baseY);
		g2.drawLine(baseLineStartX, baseY, this.getWidth(), baseY);

		//-------------------------------------------------
		// StrikeThrough 그리기
		//-------------------------------------------------
		if (this.strikeThrough) {
			// 통과선 Y좌표 구하기
			int throughY = (int)((baseY + drawingY) - (metInfo.getAscent() / 2) + (metInfo.getDescent()/2));
			g2.drawLine(baseX, throughY, (int)(baseX+fontRec.getWidth()), throughY);
		}

		//-------------------------------------------------
		// Underline 그리기
		//-------------------------------------------------
		if (this.underline > 0) {
			int lineWidth = (int)fontRec.getWidth();
			switch (this.underline) {
				case UNDERLINE_SINGLE :
					g2.drawLine(baseX, baseY+1, lineWidth + baseX-1, baseY+1);
					break;
				case UNDERLINE_DOUBLE :
					g2.drawLine(baseX, baseY-1, lineWidth + baseX-1, baseY-1);
					g2.drawLine(baseX, baseY+1, lineWidth + baseX-1, baseY+1);
					break;
				case UNDERLINE_ACCSINGLE :
					g2.drawLine(baseX, baseY+2, lineWidth + baseX-1, baseY+2);
					break;
				case UNDERLINE_ACCDOUBLE :
					g2.drawLine(baseX, baseY,	lineWidth + baseX-1, baseY  );
					g2.drawLine(baseX, baseY+2, lineWidth + baseX-1, baseY+2);
			}
		}

    }

	public void changeFont(String f, int st, int si){
        Integer newSize = new Integer(si);
        int size = newSize.intValue();
        thisFont = new Font(f, st, size);
    }

	public void changeFontSize(String size) {
		Integer newSize = new Integer(size);
		fontSize = newSize.intValue();
		changeFont(fontName, fontStyle, fontSize);
		repaint();
	}

	public void changeFontStyle(int style) {
		changeFont(this.fontName, style, this.fontSize);
		this.fontStyle = style;
		repaint();
	}

	public void changeFontName(String font) {
		fontName = font;
		changeFont(fontName, fontStyle, fontSize);
		repaint();
	}

//=============================================================================
//	Setter..
//=============================================================================
	public void setFontSize(int size) {
		this.fontSize = size;
	}

	// 삭제해도 되는 함수
	public void setFontStyle(int style) {
		this.fontStyle = style;
	}

	public void setFontBold(boolean bool) {
		this.fontBold = bool;
		if (this.fontBold && this.fontItalic)	this.setFontStyle(Font.BOLD + Font.ITALIC);
		else if (this.fontBold)					this.setFontStyle(Font.BOLD);
		else if (this.fontItalic)				this.setFontStyle(Font.ITALIC);
		else									this.setFontStyle(Font.PLAIN);
	}

	public void setFontItalic(boolean bool) {
		this.fontItalic = bool;
		if (this.fontBold && this.fontItalic)	this.setFontStyle(Font.BOLD + Font.ITALIC);
		else if (this.fontBold)					this.setFontStyle(Font.BOLD);
		else if (this.fontItalic)				this.setFontStyle(Font.ITALIC);
		else									this.setFontStyle(Font.PLAIN);
	}

	public void setFontName(String font) {
		this.fontName = font;
	}

	public void setUnderLine(byte type) {
		this.underline = type;
	}

	public void setFontColor(Color color) {
		this.fontColor = color;
	}

	public void setStrikeThrough(boolean bool) {
		this.strikeThrough = bool;
	}

	public void setScript(byte type) {
		this.script = type;
	}

	public void setString(String string) {
		this.string = string;
	}
}
