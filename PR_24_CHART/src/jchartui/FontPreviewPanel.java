//package com.techdigm.chart.editor;
package jchartui;

/******************************************************************************
 *	PROJECT		: JCalc
 *	CLASS		: FontPreview
 *	DESCRIPT	: �۲� �̸�����... FormatCells ���̾�α��� Font �ǿ��� �����.
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
//	������
//=============================================================================
	public FontPreviewPanel(int width, int height) {
		thisFont = DefaultFont.FONT;
		setSize(width, height);
		setBackground(Color.white);
		//Border fontPanelBorder = BorderFactory.createLineBorder(Color.black);
	}

	//�Ϲ� ������...
	public FontPreviewPanel() {
		thisFont = DefaultFont.FONT;
		setBackground(Color.white);
	}
//=============================================================================
//	����Ʈ
//=============================================================================
	public void paintComponent (Graphics g) {
        super.paintComponent( g );
		Graphics2D g2 = (Graphics2D) g;

		//-------------------------------------------------
		// baseX�� baseY�� ���ϱ� ���ؼ��� �׷��� ���ڿ��� width�� height�� �ʿ���
		//-------------------------------------------------

		int			baseX;		// ���ؼ��� �׸� ������ǥ�ν� ���� ũ���� ��Ʈ�� �ش��ϴ� ��ǥ�� ����ȴ�.
		int			baseY;
		int			baseAscent;
		int			baseDescent;
		int			baseLeading;
		int			baseHeight;
		int			baseWidth;
		int			drawingX;	// ���ڸ� �׸���ǥ�μ� Script���ο� ���� ������ �޴´�.
		int			drawingY;
		Rectangle2D	fontRec;
		int			baseLineEndX;	// ���� ���ؼ��� �� x��ǥ
		int			baseLineStartX;	// ���� ���ؼ��� ���� x��ǥ
		Metinfo		metInfo;

		//---------------------------------------------
		// Font ����
		// Script �Ӽ��� ���� Font�� ũ�⸦ �����ؾ��Ѵ�.
		//---------------------------------------------
		Font font	= new Font(this.fontName, this.fontStyle, this.fontSize);
		metInfo		= new Metinfo(font);
		fontRec		= font.getStringBounds(this.string, g2.getFontRenderContext());
		baseAscent	= metInfo.getAscent();	// �⺻��Ʈ�� Ascent
		baseDescent = metInfo.getDescent();	// �⺻��Ʈ�� Descent
		baseLeading = metInfo.getLeading();	// �⺻��Ʈ�� Leading
		baseHeight	= metInfo.getHeight();	// �⺻��Ʈ�� Height
		baseWidth	= (int)fontRec.getWidth();

		//-------------------------------------------------
		// baseX�� baseY�� ���Ѵ�.
		//-------------------------------------------------
		baseX		= (this.getWidth() - baseWidth) / 2;
		baseY		= ((this.getHeight() - baseHeight) / 2) + baseAscent;
		drawingX	= 0;
		drawingY	= 0;
		//-------------------------------------------------
		// ���ؼ��� ��ǥ�� ���Ѵ�.
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

		// SUBSCRIPT �϶�
		switch (this.script) {
			case SCRIPT_SUPER :
				// ��Ʈ�� ũ�� ����
				font = font.deriveFont(font.getSize() * 0.7f);
				// ���������� ��Ʈ�� MetInfo�� ���Ѵ�.
				metInfo = new Metinfo(font);
				// Super Script�϶��� ���ذ����� -(���̳ʽ�)�Ѵ�.
				drawingY = (int)((baseY - baseAscent - (baseHeight * 0.02) + metInfo.getAscent()) - baseY);
				break;
			// SUPERSCIPT �϶�
			case SCRIPT_SUB :
				font = font.deriveFont(font.getSize() * 0.7f);
				metInfo = new Metinfo(font);
				// ��Ʈ�� ũ�⺯ȭ�� ���� Y��ǥ �������� �־���Ѵ�.
				// UNDERLINE �� ������ y��ġ�� �׾������ϹǷ�
				// ���ڸ� �׸��� Y��ǥ�� Underbar�� �׸��� Y��ǥ�� ���� �־�� �Ѵ�...
				drawingY = (int)(baseHeight * 0.12);
		}

		fontRec		= font.getStringBounds(this.string, g2.getFontRenderContext());
		baseX	= (int)((this.getWidth() - fontRec.getWidth()) / 2);

		//-------------------------------------------------
		// ���ڿ� �׸���
		//-------------------------------------------------
		// Color ����
		g2.setColor(this.fontColor);
		// Font ����
		g2.setFont(font);
		g2.drawString(this.string, baseX, baseY + drawingY);

		//-------------------------------------------------
		// ���ؼ� �׸���
		//-------------------------------------------------
		g2.drawLine(0, baseY, baseLineEndX, baseY);
		g2.drawLine(baseLineStartX, baseY, this.getWidth(), baseY);

		//-------------------------------------------------
		// StrikeThrough �׸���
		//-------------------------------------------------
		if (this.strikeThrough) {
			// ����� Y��ǥ ���ϱ�
			int throughY = (int)((baseY + drawingY) - (metInfo.getAscent() / 2) + (metInfo.getDescent()/2));
			g2.drawLine(baseX, throughY, (int)(baseX+fontRec.getWidth()), throughY);
		}

		//-------------------------------------------------
		// Underline �׸���
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

	// �����ص� �Ǵ� �Լ�
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
