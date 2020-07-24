/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

package com.jwordart.model.wordart;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

import com.jwordart.model.fillEffect.*;
import com.jwordart.model.wordart.mappingType.*;
import com.jwordart.ui.comp.WordArtEditor;
import com.jwordart.ui.comp.WordArtGallery;
import com.jwordart.ui.comp.WordArtStringEditor;
import com.jwordart.ui.comp.WordArtStyleEditor;
import com.jwordart.ui.renderer.*;
import com.jwordart.util.*;

public class WordArtGroupManager implements MouseListener, MouseMotionListener, KeyListener {
	// �����Ʈ �׷� �Ŵ��� ����Ʈ
	public static Hashtable groupManagers = new Hashtable();

	// �� �۾� ���� ��Ʈ �׷� �Ŵ���
	public static WordArtGroupManager currentWorkingManager;

	private static final long pasteInterval = 3000; // Paster interval time 0.6
	// second
	private static final double pasteMarginX = 10, pasteMarginY = 10;
	private double pasteNum = 0;
	// ���� �߰��� ���� ��Ʈ�� �⺻ ���ڿ�
	private static String defaultStringOfInsertingWordArt = "���ڿ��� �Է��Ͻʽÿ�.";

	public static final int WAIT_MODE = 0, MOVE_MODE = 1, RESIZE_MODE = 2, ROTATE_MODE = 3, MOVE_CONTROL_POINT = 4;
	private int workMode = WAIT_MODE;

	private Hashtable wordArts = new Hashtable(); // �����Ʈ ��ü��
	private Component targetComponent; // �۾� ��� ������Ʈ
	private Hashtable selectedWordArts = new java.util.Hashtable(); // ���� ���õ� ����
	// ��Ʈ
	private Hashtable copiedWordArts = new java.util.Hashtable(); // ī�ǵ� ����
	// ��Ʈ��
	private long lastPasteTime = System.currentTimeMillis(); // ���� ���̽�Ʈ �ð�.
	private MouseEvent lastMouseEvent; // ���� ���콺 �̺�Ʈ

	private WordArt rotationRefWordArt, resizeRefWordArt; // ȸ����, ��������� ���� ����
	// ��Ʈ.
	private int resizeTopology; // �������� ����. ���콺 Ŀ�� Ÿ�� ������ �����Ѵ�.

	public WordArtGroupManager(Component comp) {
		this.targetComponent = comp;
	}

	// add an word art to selected Word art hashtable
	// after removing all word arts from the hashtable.
	public void selectAlone(WordArt wa) {
		if (!this.selectedWordArts.contains(wa)) {
			this.selectedWordArts.put(wa, wa);
		}
		Enumeration enumIt = this.selectedWordArts.elements();
		Object obj;
		while (enumIt.hasMoreElements()) {
			obj = enumIt.nextElement();
			if (obj != wa) {
				this.selectedWordArts.remove(obj);
			}
		}
		WordArtEditor.setStringEditButtonEnabledOrDisabled();
	}

	public void setWorkMode(int mode) {
		this.workMode = mode;
	}

	// de select word art from selected word art hashtable.
	public void deSelect(WordArt wa) {
		this.selectedWordArts.remove(wa);
		WordArtEditor.setStringEditButtonEnabledOrDisabled();
		if (this.getSelectedWordArtsNumber() == 0) {
			WordArtEditor.hideEditor();
		}
	}

	// de select all word art
	public void deSelectAll() {
		if (this.selectedWordArts != null && this.selectedWordArts.size() > 0) {
			Enumeration enumIt = this.selectedWordArts.elements();
			Object obj;
			while (enumIt.hasMoreElements()) {
				obj = enumIt.nextElement();
				this.selectedWordArts.remove(obj);
			}
			WordArtEditor.setStringEditButtonEnabledOrDisabled();
			WordArtEditor.hideEditor();
		}
	}

	// add word art to selected word art hashtable.
	public void selectAppend(WordArt wa) {
		this.selectedWordArts.put(wa, wa);
		WordArtEditor.setStringEditButtonEnabledOrDisabled();
	}

	public boolean isSelectedWordArt(WordArt wa) {
		return this.selectedWordArts.contains(wa);
	}

	// set copied word art by pressing Ctrl-C.
	public void copy(WordArt wa) {
		this.copiedWordArts.put(wa, wa);
		this.pasteNum = 0;
	}

	// paste copied word art by pressing Ctrl-V
	public void pasteCopiedWordArts() {
		if (!this.satisfyPasteInverval()) {
			// ���̽�Ʈ �ð� ������ �������� ������, �ƹ��� �ϵ� ���� �ʴ´�.
			return;
		}
		lastPasteTime = System.currentTimeMillis();
		this.pasteNum++;
		Enumeration enumIt = this.copiedWordArts.elements();
		WordArt wa, wac;
		Point2D p;

		while (enumIt.hasMoreElements()) {
			wa = (WordArt) enumIt.nextElement();
			boolean vertical = wa.isVertical();
			wa.setVertical(false);
			wac = wa.create();
			p = wa.getLocation();
			wa.setVertical(vertical);
			wac.setLocation(p.getX() + pasteNum * WordArtGroupManager.pasteMarginX, p.getY() + pasteNum
					* WordArtGroupManager.pasteMarginY);
			wac.setVertical(vertical);
			wac.parse(false);
			this.putWordArt(wac);
		}
		this.repaint();
	}

	public boolean satisfyPasteInverval() {
		long interval = System.currentTimeMillis() - lastPasteTime;

		if (interval > pasteInterval) {
			return true;
		}
		return false;
	}

	public static synchronized WordArtGroupManager getWordArtGroupManager(Component comp) {
		return (WordArtGroupManager) groupManagers.get(comp);
	}

	public static synchronized WordArtGroupManager putWordArtGroupManager(Component comp) {
		WordArtGroupManager manager = new WordArtGroupManager(comp);
		groupManagers.put(comp, manager);
		comp.addMouseListener(manager);
		comp.addMouseMotionListener(manager);
		comp.addKeyListener(manager);
		return manager;
	}

	public static WordArt getNewWordArt(Component comp) {
		if (comp == null) {
			return null;
		}
		;

		WordArtGallery.wordArtGallery.setTitle("WordArt ���� ����(W):");
		WordArtMappingTypeAndStyle newWordArtMappingTypeAndStyle = WordArtGallery.getWordArtMappingTypeAndStyle();

		if (newWordArtMappingTypeAndStyle == null) {
			return null;
		}

		WordArtStringEditor.wordArtStringEditor.setWordArtFont(newWordArtMappingTypeAndStyle.getWordArtStyle().getFont());
		WordArtStringEditor.wordArtStringEditor.setText(WordArtGroupManager.defaultStringOfInsertingWordArt);

		FontAndString newFontAndString = WordArtStringEditor.getFontAndString();

		if (newFontAndString == null) {
			return null;
		}

		WordArtGroupManager manager = getWordArtGroupManager(comp);
		if (manager == null) {
			manager = putWordArtGroupManager(comp);
		}

		WordArtGroupManager.currentWorkingManager = manager;

		Point2D newWordArtLocation = manager.getNewWordArtLocation();

		WordArt newWordArt = new WordArt(manager, newWordArtMappingTypeAndStyle, newFontAndString, newWordArtLocation);

		manager.putWordArt(newWordArt);

		WordArtEditor.setEditingMode(true);

		manager.repaint();

		return newWordArt;
	}

	public static void editWordArtString() {
		WordArt[] selWAs = currentWorkingManager.getSelectedWordArts();
		// ���ڿ� ������ �Ѱ��� ���� ��Ʈ�� ���õǾ��� ��쿡�� ����Ѵ�.
		if (selWAs.length != 1) {
			return;
		}

		WordArtStringEditor.wordArtStringEditor.setWordArtFont(selWAs[0].getFont());
		WordArtStringEditor.wordArtStringEditor.setText(selWAs[0].getString());

		// Get new font and string from word art string editor.
		FontAndString newFontAndString = WordArtStringEditor.getFontAndString();

		if (newFontAndString == null) {
			return;
		}

		if (currentWorkingManager == null) {
			return;
		}

		for (int i = 0; i < selWAs.length; i++) {
			selWAs[i].reparse(newFontAndString);
		}
		if (selWAs.length > 0) {
			currentWorkingManager.repaint();
		}
	}

	// ���õ� ���� ��Ʈ���� ��Ÿ�ϰ� ���� Ÿ�� �� ��Ʈ�� �ٲ۴�.
	public static void changeWordArtGallery() {
		WordArtGallery.wordArtGallery.setTitle("WordArt ���� �ٲٱ�(W):");
		WordArtMappingTypeAndStyle newWordArtMappingTypeAndStyle = WordArtGallery.getWordArtMappingTypeAndStyle();
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null || newWordArtMappingTypeAndStyle == null) {
			return;
		}
		WordArt[] selWAs = manager.getSelectedWordArts();
		for (int i = 0; i < selWAs.length; i++) {
			if (selWAs[i] == null) {
				continue;
			}
			selWAs[i].setVertical(false);
			Point2D loc = selWAs[i].getLocation();
			Rectangle2D dim = selWAs[i].getGlobalDimension();
			selWAs[i].changeWordArtGallery(newWordArtMappingTypeAndStyle);
			boolean vertical = selWAs[i].isVertical();
			selWAs[i].setVertical(false);
			selWAs[i].setGlobalDimension(dim);
			selWAs[i].setLocation(loc.getX(), loc.getY());
			selWAs[i].setVertical(vertical);
			selWAs[i].parse(false);
		}
		if (selWAs.length > 0) {
			manager.repaint();
		}
	}

	// ���õ� ���� ��Ʈ���� ��Ÿ���� �����Ѵ�. ��Ʈ�� �������� �ʴ´�.
	public static void changeWordArtStyle() {
		WordArtStyle newWordArtStyle = WordArtStyleEditor.getNewWordArtStyle();
		if (newWordArtStyle == null) {
			return;
		}
		WordArt[] selWAs = WordArtGroupManager.currentWorkingManager.getSelectedWordArts();
		Font fontOld;
		for (int i = 0; i < selWAs.length; i++) {
			fontOld = selWAs[i].getFont();
			selWAs[i].setWordArtStyle(newWordArtStyle.create());
			selWAs[i].setFont(fontOld);
		}
		WordArtGroupManager.currentWorkingManager.repaint();
	}

	// put new word art and draw it on the component.
	public WordArt putWordArt(WordArt wa) {
		this.wordArts.put(wa, wa);

		// ���õ� ���� ��Ʈ�� �ϳ��� ������ �߰��ϴ� ���� ��Ʈ�� ���� ��Ͽ� �߰��Ѵ�.
		if (this.getSelectedWordArtsNumber() == 0) {
			this.selectAlone(wa);
		}

		// wa.getComponent().addMouseListener( wa );
		// wa.getComponent().addMouseMotionListener( wa );

		return wa;
	}

	public void removeWordArt(WordArt wa) {
		if (this.selectedWordArts.contains(wa)) {
			this.selectedWordArts.remove(wa);
		}
		if (this.copiedWordArts.contains(wa)) {
			this.copiedWordArts.remove(wa);
			this.pasteNum = 0;
		}
		this.wordArts.remove(wa);
	}

	public void cut(WordArt wa) {
		if (this.selectedWordArts.contains(wa)) {
			this.removeWordArt(wa);
		}
		if (!this.copiedWordArts.contains(wa)) {
			this.copiedWordArts.put(wa, wa);
		}
	}

	public void delete(WordArt wa) {
		this.removeWordArt(wa);
	}

	public WordArt[] getSelectedWordArts() {
		WordArt[] wordArts;
		Enumeration enumIt = this.selectedWordArts.elements();
		wordArts = new WordArt[this.selectedWordArts.size()];
		int i = 0;
		while (enumIt.hasMoreElements()) {
			wordArts[i++] = (WordArt) enumIt.nextElement();
		}
		return wordArts;
	}

	public int getSelectedWordArtsNumber() {
		if (this.selectedWordArts == null) {
			return 0;
		} else {
			return this.selectedWordArts.size();
		}
	}

	public Point2D getNewWordArtLocation() {
		int x = targetComponent.getWidth() / 8, y = targetComponent.getHeight() / 4;

		return new Point2D.Float(x, y);
	}

	public static void repaint(Component comp) {
		WordArtGroupManager manager = getWordArtGroupManager(comp);
		if (manager != null) {
			manager.repaint();
		}
	}

	public void repaint() {
		targetComponent.update(targetComponent.getGraphics());
		this.repaintWithoutUpdate();
	}

	public void repaintWithoutUpdate() {

		Enumeration enumIt = wordArts.elements();
		while (enumIt.hasMoreElements()) {
			WordArt wa = (WordArt) enumIt.nextElement();
			if (wa != null) {
				wa.draw();
			}
		}
	}

	public Component getTargetComponent() {
		return this.targetComponent;
	}

	public void mouseClicked(MouseEvent e) {
		// ��Ŀ�� ȹ�� ( Ű���� �Է��� �޾� ���̱� ���ؼ�)
		Component comp = (Component) e.getSource();
		comp.requestFocus();
		// ���� Ŭ���� ���õ� ���� ��Ʈ�� �ϳ��̸�, ���� ��Ʈ ����Ʈ�� �ٿ��.
		if (e.getClickCount() == 1 && this.getSelectedWordArtsNumber() == 1) {
			WordArtEditor.showEditorOnFront();
		}
		// ���� Ŭ���� ���ñ� ���� ��Ʈ�� 2�� �̻��̸�, ��Ÿ�� �����͸� �ٿ��.
		if (e.getClickCount() == 2 && this.getSelectedWordArtsNumber() > 1) {
			WordArtGroupManager.changeWordArtStyle();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void drawSelectedWordArtsGlobalMappingOutLines() {
		// ���õ� ���� ��Ʈ�� �ܰ����� �׸���.
		WordArt[] selWAs = this.getSelectedWordArts();
		if (selWAs != null && selWAs[0] != null) {
			selWAs[0].drawMappingOutLines();
		}
		// ��. �ܰ��� �׸���
	}

	public void mousePressed(MouseEvent e) {

		int top = this.getWordArtTopology(e);

		if (top == WordArt.CONTROL_POINT) {
			this.setWorkMode(WordArtGroupManager.MOVE_CONTROL_POINT);
			this.setCursor(e);
			// ���õ� ���� ��Ʈ�� �ܰ����� �׸���.
			this.drawSelectedWordArtsGlobalMappingOutLines();
			// ��. �ܰ��� �׸���
			// ���콺 �̺�Ʈ �α� ���.
			this.lastMouseEvent = e;
			// ��. ���콺 �̺�Ʈ �α� ���.
			return;
		}
		// ���� ��Ʈ���� ȸ���� ���.
		else if (this.getWorkMode() == ROTATE_MODE && top == WordArt.ROTATE_ON) {
			// ���콺�� �������� Ű �Է��� �ޱ� ���ؼ� �ҽ��� ��Ŀ���� ��û�Ѵ�.
			Component comp = (Component) e.getSource();
			comp.requestFocus();
			// ��. ��Ŀ�� ��û.

			// ȸ�� ���� ���� ��Ʈ �˻�.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				if (selWAs[i].getTopology(e) == WordArt.ROTATE_ON) {
					this.rotationRefWordArt = selWAs[i];
					break;
				}
			}
			this.drawSelectedWordArtsGlobalMappingOutLines();
			// ��. ȸ�� �ⱺ ���� ��Ʈ �˻�.

			// ���콺 �̺�Ʈ �α� ���.
			this.lastMouseEvent = e;
			// ��. ���콺 �̺�Ʈ �α� ���.
			return;
		} else if (this.getWorkMode() == ROTATE_MODE && top == WordArt.EXT_OUT) {
			this.setWorkMode(WAIT_MODE);
			this.setCursor(e);
		} else if (top >= 0 && top <= 7) {
			// �������� ����� ���.
			this.setWorkMode(RESIZE_MODE);
			this.setCursor(e);

			// �������� ���� ���� ��Ʈ �˻�.
			WordArt[] selWAs = this.getSelectedWordArts();

			int resizeTop;

			for (int i = 0; i < selWAs.length; i++) {
				resizeTop = selWAs[i].getTopology(e);
				if (resizeTop >= 0 && resizeTop <= 7) {
					this.resizeRefWordArt = selWAs[i];
					break;
				}
			}

			this.drawSelectedWordArtsGlobalMappingOutLines();
			// ��. �������� �ⱺ ���� ��Ʈ �˻�.

			this.lastMouseEvent = e;
			return;
		}

		WordArt[] WAs = this.getWordArts();

		// �����̼� ��尡 �ƴϰ�, ���콺 ��ġ�� �����̼� ������ ������,
		// ���� ��Ʈ ���� ���콺 ������ �̺�Ʈ�� ���� ó���Ѵ�.
		if (WAs != null) {
			for (int i = 0; i < WAs.length; i++) {
				WAs[i].mousePressed(e);
			}
		}

		// ���콺 �̺�Ʈ�� ó���ϰ���,
		// ���콺�� ��� �ϳ��� ���� ��Ʈ ���� �ִ� �� �����Ѵ�.
		boolean mouseOver = this.mouseOverSelectedWordArts(e);

		if (!e.isShiftDown()) {
			// ��ƮŰ�� �������� ���� ä��,
			// ���콺�� ���� ��Ʈ �ܺο��� ��������, ���� ����� ����.
			// ������ ���� ��Ʈ�� �ϳ��� ������, ���� ����� ����.
			if (!mouseOver) {
				this.deSelectAll();
				// this.repaint();
			}
		}

		if (mouseOver && e.getModifiers() != InputEvent.BUTTON3_MASK) {
			// ���콺�� ���õ� ���� �Ƶ� ������ ���� ���콺 ��ư�� �������� ���� ��Ʈ�� �̵� �� �� �ִ�
			// ���� ����.
			this.workMode = MOVE_MODE;
		}

		if (this.getSelectedWordArtsNumber() == 0) {
			WordArtEditor.hideEditor();
		}

		if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
			// ���콺�� ���õ� ���� ��Ʈ ������ ���� �� ���콺 ��ư�� ������
			// ���� ��Ʈ ������ �˾� �޴��� ǥ���Ѵ�.
			com.jwordart.ui.comp.WordArtEditor.wordArtEditor.showWordArtEditorPopupMenu(e);
		} else {
			com.jwordart.ui.comp.WordArtEditor.wordArtEditor.showWordArtEditorPopupMenu(null);
		}

		this.repaint();

		// ���콺�� �������� Ű �Է��� �ޱ� ���ؼ� �ҽ��� ��Ŀ���� ��û�Ѵ�.
		Component comp = (Component) e.getSource();
		comp.requestFocus();
		// ��. ��Ŀ�� ��û.

		this.lastMouseEvent = e;
	}

	public boolean mouseOverSelectedWordArts(MouseEvent e) {
		WordArt[] selWAs = this.getSelectedWordArts();
		int top;
		for (int i = 0; i < selWAs.length; i++) {
			top = selWAs[i].getTopology(e);
			if (top == WordArt.GLYPH_ON || top == WordArt.ROTATE_ON) {
				return true;
			}
		}
		return false;
	}

	public void mouseReleased(MouseEvent e) {
		// ���콺�� ����, ���õ� ���� ��Ʈ���� �̵���Ų��.
		if (workMode == MOVE_MODE) {
			int dx = e.getX() - lastMouseEvent.getX(), dy = e.getY() - lastMouseEvent.getY();
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				selWAs[i].moveBy(dx, dy);
			}
			if (selWAs.length > 0) {
				this.repaint();
			}
			this.setWorkMode(WordArtGroupManager.WAIT_MODE);
		} else if (workMode == ROTATE_MODE) {
			// ȸ�� ������ ����Ѵ�.
			double radian = this.getRotationAngle(e);
			// ���õ� ���� ��Ʈ���� ȸ���Ѵ�.
			WordArtGroupManager.rotateSelectedWordArtsByAngle(radian);
			this.repaint();
			;
		} else if (workMode == RESIZE_MODE) {
			// ��ġ �� ũ�⸦ �� �����Ѵ�.
			this.resizeSelectedWordArts(e);
			// ��. ��ġ �� ũ�� �� ����.
			// ���õ� ���� ��Ʈ���� �۸��� �������� �Ѵ�.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				// ��ü ������ �缳������ �ʰ� ���õ� ���� ��Ʈ�� �Ľ��Ѵ�.
				selWAs[i].parse(false);
			}
			// ��. ���õ� ���� ��Ʈ �۸� ��������.
			// �� �������Ѵ�.
			this.repaint();
			// ��. �� ������.
			this.setWorkMode(WordArtGroupManager.WAIT_MODE);
		} else if (workMode == MOVE_CONTROL_POINT) {
			this.moveSelectedWordArtControlPointsBy(e);
			this.setWorkMode(WordArtGroupManager.WAIT_MODE);
			this.setCursor(e);
			WordArt[] selWAs = this.getSelectedWordArts();
			if (selWAs != null && selWAs[0] != null) {
				selWAs[0].parse(false);
			}
			this.repaint();
		}
		lastMouseEvent = e;
	}

	public int getWorkMode() {
		return this.workMode;
	}

	public void moveSelectedWordArtControlPointsBy(MouseEvent e) {
		WordArt[] selWAs = this.getSelectedWordArts();
		if (selWAs != null && selWAs[0] != null) {
			double dx = e.getX() - this.lastMouseEvent.getX(), dy = e.getY() - this.lastMouseEvent.getY();
			selWAs[0].moveControlPointsBy(dx, dy);
			selWAs[0].drawMappingOutLineMovedNrotatedNresizedBy(0, 0, 0, 0, 0);
		}
	}

	public void mouseDragged(MouseEvent e) {
		// ���� ��Ʈ �̵� ����̸� ���콺 �巡��ÿ��� ���� �ܰ������� �̵��Ѵ�.
		double workMode = this.workMode;

		if (workMode == MOVE_CONTROL_POINT) {
			this.moveSelectedWordArtControlPointsBy(e);
		} else if (workMode == MOVE_MODE) {
			// ���� ��Ʈ���� ���� �ܰ����� �̵��� ������ �����Ѵ�.
			int dx = e.getX() - lastMouseEvent.getX(), dy = e.getY() - lastMouseEvent.getY();
			// ���õ� ���� ��Ʈ���� ���� �ܰ����� �̵��Ѵ�.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				selWAs[i].drawMappingOutLineMovedNrotatedNresizedBy(dx, dy, 0, 0, 0);
			}
		} else if (workMode == ROTATE_MODE) {
			// ȸ�� ������ ����Ѵ�.
			double radian = this.getRotationAngle(e);
			// ���õ� ���� ��Ʈ���� ȸ���� ���� �ܰ����� �׸���.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				selWAs[i].drawMappingOutLineMovedNrotatedNresizedBy(0, 0, radian, 0, 0);
			}
		} else if (workMode == RESIZE_MODE) {
			this.resizeSelectedWordArts(e);
		}

		this.lastMouseEvent = e;
	}

	public void resizeSelectedWordArts(MouseEvent e) {
		// �������� ���� (���ʱ۷� ����)
		Shape L = new Rectangle.Double(e.getX() - lastMouseEvent.getX(), e.getY() - lastMouseEvent.getY(), 0, 0);
		// �������� ���͸� ȸ�� ���� ��ŭ ������ �ٽ� ȸ����.
		// �ⱺ �������� ȸ�� �Ǿ� ���� �ʱ� ������( ����Ʈ �ÿ��� �׷��Ƚ��� ȸ���ϱ� �׸���.)
		WordArt resizeRefWordArt = this.resizeRefWordArt;

		double radian = resizeRefWordArt.getWordArtStyle().getRotationRadianAngle();

		// ���� ���ڿ� ���� ����
		if (resizeRefWordArt.isVertical()) {
			radian -= Math.PI / 2.0;
		}
		// ��. ���� ���ڿ� ���� ����

		if (radian != 0) {
			AffineTransform at = AffineTransform.getRotateInstance(-radian);
			L = at.createTransformedShape(L);
		}

		// ��. �������� ���� �� ȸ��.
		// ��. �������� ���� ���ϱ�.

		// �������� ���ͷ� ���� �������� �� ���� ���ϱ�.
		double dx = L.getBounds2D().getX(), dy = L.getBounds2D().getY();

		/*
		 * if( resizeRefWordArt.isVertical() ) { double tempDx = dx; dx = dy; dy =
		 * tempDx; }
		 */

		double dw = 0, dh = 0;
		// ��. �������� �� ���� ���ϱ�.

		// �������� ���� ���� dx, dy, dw, dh �� ����.
		int top = this.getResizeTopology();

		switch (top) {
		case Cursor.NW_RESIZE_CURSOR:
			// �ϼ� ���� : ��ġ �� ũ�� �缳��
			// dx, dy, dw, dh ����
			dw = -dx;
			dh = -dy;
			break;
		case Cursor.N_RESIZE_CURSOR:
			// �� ���� : ��ġ �� ũ�� �缳��
			// dy, dh ����
			dh = -dy;
			dx = 0;
			dw = 0;
			break;
		case Cursor.NE_RESIZE_CURSOR:
			// �ϵ� ���� : ��ġ �� ũ�� �缳��
			// dy, dw, dh ����
			dw = dx;
			dh = -dy;
			dx = 0;
			break;
		case Cursor.W_RESIZE_CURSOR:
			// �� ���� : ��ġ �� ũ�� �缳��
			// dx, dw ����
			dw = -dx;
			dy = 0;
			dh = 0;
			break;
		case Cursor.E_RESIZE_CURSOR:
			// �� ���� : ũ�� �缳��
			// dw ����
			dw = dx;
			dx = dy = dh = 0;
			break;
		case Cursor.SW_RESIZE_CURSOR:
			// ���� ���� : ��ġ �� ũ�� �缳��
			// dx, dw, dh ����
			dw = -dx;
			dh = dy;
			dy = 0;
			break;
		case Cursor.S_RESIZE_CURSOR:
			// �� ���� : ũ�� �缳��
			// dh ����
			dh = dy;
			dx = dy = dw = 0;
			break;
		case Cursor.SE_RESIZE_CURSOR:
			// ���� ���� : ũ�� �缳��
			// dw, dh ����
			dw = dx;
			dh = dy;
			dx = dy = 0;
			break;
		}
		// ��. dx, dy, dw, dh �� ����

		// ���õ� ���� ��Ʈ���� ũ�� �� ��ġ�� �� �����Ѵ�.
		WordArt[] selWAs = this.getSelectedWordArts();
		for (int i = 0; i < selWAs.length; i++) {
			selWAs[i].drawMappingOutLineMovedNrotatedNresizedBy(dx, dy, 0, dw, dh);
		}
		// ��. ũ�� �� ��ġ �� ����.
	}

	// ȸ�� ���� ���� ��Ʈ�� �������� ȸ�� ������ ����Ѵ�.
	public double getRotationAngle(MouseEvent e) {
		WordArt refWA = this.rotationRefWordArt;

		if (refWA == null) {
			return 0;
		}

		double radian = 0;

		double ax, ay, bx, by;

		Point2D center = refWA.getCenterPoint(), loc = refWA.getLocation();

		ax = lastMouseEvent.getX() - loc.getX() - center.getX();
		ay = lastMouseEvent.getY() - loc.getY() - center.getY();
		bx = e.getX() - loc.getX() - center.getX();
		by = e.getY() - loc.getY() - center.getY();

		radian = (ax * by - bx * ay) / Math.sqrt((ax * ax + ay * ay) * (bx * bx + by * by));
		radian = Math.asin(radian);

		return radian;
	}

	public void mouseMoved(MouseEvent e) {
		if (this.workMode == WAIT_MODE || this.workMode == ROTATE_MODE || this.workMode == RESIZE_MODE) {
			this.setCursor(e);
		}
	}

	public WordArt[] getWordArts() {
		WordArt[] value = new WordArt[this.wordArts.size()];
		Enumeration enumIt = this.wordArts.elements();
		int index = 0;
		while (enumIt.hasMoreElements()) {
			value[index] = (WordArt) enumIt.nextElement();
			index++;
		}
		return value;
	}

	public int getWordArtTopology(MouseEvent e) {
		WordArt[] WAs = this.getWordArts();

		int topology = WordArt.EXT_OUT;

		for (int i = 0; i < WAs.length; i++) {
			topology = WAs[i].getTopology(e);
			if (topology == WordArt.GLYPH_ON) {
				break;
			} else if (topology == WordArt.ROTATE_ON) {
				break;
			} else if (topology >= 0 && topology <= 7) {
				break;
			} else if (topology == WordArt.CONTROL_POINT) {
				break;
			}
		}

		return topology;
	}

	public void setCursor(MouseEvent e) {
		int topology = this.getWordArtTopology(e);

		java.awt.Component comp = this.getTargetComponent();

		Cursor cursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);

		switch (topology) {
		case WordArt.GLYPH_ON:
			cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
			break;

		case WordArt.ROTATE_ON:
			cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			break;

		case WordArt.NW_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
			break;
		case WordArt.N_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
			break;
		case WordArt.NE_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
			break;

		case WordArt.W_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
			break;
		case WordArt.E_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
			break;

		case WordArt.SW_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
			break;
		case WordArt.S_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
			break;
		case WordArt.SE_RESIZE:
			cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
			break;
		case WordArt.CONTROL_POINT:
			cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
			break;
		}

		if (this.getWorkMode() == WordArtGroupManager.ROTATE_MODE) {
			if (topology == WordArt.GLYPH_ON) {
				// �����̼� ��忡�� ����ڰ� �۸����� Ŀ���� ��������,
				// Ŀ���� ����� ���� Ŀ���� �ٲ۴�.
				cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
			} else if (topology != WordArt.ROTATE_ON) {
				cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			}
		} else if (this.getWorkMode() == WordArtGroupManager.RESIZE_MODE) {
			// ����ڰ� ���콺�� ������ �������� ���� ����,
			// Ŀ���� ũ�ν� ��� Ŀ���� �ٲ۴�.
			if (topology >= 0 && topology <= 7) {
				// �������� ������ Ŀ�� Ÿ���� ������ �����Ѵ�.
				this.resizeTopology = cursor.getType();
				// ��. �������� ���� ����.
				// Ŀ���� ũ�ν� ��� Ŀ���� �ٲ۴�.
				cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
				// ��. ũ�ν� ��� Ŀ���� �ٲٱ�.
			}
		}

		comp.setCursor(cursor);
	}

	public int getResizeTopology() {
		return this.resizeTopology;
	}

	public void copySelectedWordArts() {
		WordArt[] WAs = this.getSelectedWordArts();
		// ���� ����� ����.
		this.copiedWordArts = new java.util.Hashtable();
		for (int i = 0; i < WAs.length; i++) {
			this.copy(WAs[i]);
		}
	}

	public void removeSelectedWordArts() {
		WordArt[] WAs = this.getSelectedWordArts();
		for (int i = 0; i < WAs.length; i++) {
			this.removeWordArt(WAs[i]);
		}
	}

	public void cutSelectedWordArts() {
		WordArt[] WAs = this.getSelectedWordArts();
		this.copiedWordArts = new java.util.Hashtable();
		for (int i = 0; i < WAs.length; i++) {
			this.cut(WAs[i]);
		}
	}

	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			this.removeSelectedWordArts();
			this.repaint();
		}
		// if pressed Ctrl-C
		else if (e.isControlDown() && e.getKeyChar() == '') {
			this.copySelectedWordArts();
			// this.repaint();
		}
		// if pressed Ctrl-X
		else if (e.isControlDown() && e.getKeyChar() == '') {
			this.cutSelectedWordArts();
			this.repaint();
		}
		// if pressed Ctrl-V
		if (e.isControlDown() && e.getKeyChar() == '') {
			this.pasteCopiedWordArts();
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void rotateSelectedWordArtsByAngle(double radian) {
		// if( radian == 0 ) { return ; }
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}
		WordArt[] selWAs = manager.getSelectedWordArts();
		if (selWAs == null) {
			return;
		}
		for (int i = 0; i < selWAs.length; i++) {
			selWAs[i].rotateBy(radian);
			selWAs[i].parse(false);
		}
	}

	public static void toggleSelectedWordArtsSameCharacterHeight() {
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}

		WordArt[] selWAs = manager.getSelectedWordArts();
		for (int i = 0; i < selWAs.length; i++) {
			if (!selWAs[i].isSameCharacterHeight()) {
				selWAs[i].setTheSameCharacterHeight(true);
			} else {
				selWAs[i].setTheSameCharacterHeight(false);
			}
			// ��ü ������ �缳�� ���� �ʰ� �� �Ľ� �Ѵ�.
			selWAs[i].parse(false);
			// ��. �� �Ľ�
		}
		// �ٽ� �׸���.
		manager.repaint();
		// ��. �ٽ� �׸���
	}

	public static void changeAdjustmentType(int pType) {
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}

		WordArt[] selWAs = manager.getSelectedWordArts();
		for (int i = 0; i < selWAs.length; i++) {
			if (selWAs[i].getAdjustmentType() == pType) {
				continue;
			}
			// ���� ����� �����Ѵ�.
			selWAs[i].setAdjustmentType(pType);
			// ��. ���� ��� ����
			// ��ü ������ �缳�� ���� �ʰ� �� �Ľ� �Ѵ�.
			selWAs[i].parse(false);
			// ��. �� �Ľ�
		}
		// �ٽ� �׸���.
		manager.repaint();
		// ��. �ٽ� �׸���
	}

	// ���õ� ���� ��Ʈ���� ���� ��带 ����Ѵ�.
	public static void toggleSeletecWordArtsVertical() {
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}

		WordArt[] selWAs = manager.getSelectedWordArts();
		for (int i = 0; i < selWAs.length; i++) {
			if (selWAs[i] == null) {
				continue;
			}
			boolean vertical = selWAs[i].isVertical();
			// ���� ��� ���
			selWAs[i].setVertical(!vertical);
			// ��. ���� ��� ���
			// ��ü ������ �缳������ �ʰ� �� �Ľ��Ѵ�.
			selWAs[i].parse(false);
			// ��. �� �Ľ�
		}
		manager.repaint();
	}

	// ���õ� ���� ��Ʈ���� ���� Ÿ���� �ٲ۴�.
	public static void changeSelectedWordArtsMappingType(int type) {
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}
		WordArt[] selWAs = manager.getSelectedWordArts();
		if (selWAs == null || selWAs.length == 0) {
			return;
		}
		WordArtMappingType mappingType = WordArtMappingType.getWordArtMappingType(type);
		if (mappingType == null) {
			return;
		}
		for (int i = 0; i < selWAs.length; i++) {
			selWAs[i].setMappingType(mappingType);
			selWAs[i].parse(false);
		}
		manager.repaint();
	}

	// ���õ� ���� ��Ʈ���� �۸� ������ �ٲ۴�.
	public static void setSelectedWordArtsGlyphGap(final double glyphGapRatio) {
		if (glyphGapRatio < 0) {
			return;
		}
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}
		WordArt[] selWAs = manager.getSelectedWordArts();
		if (selWAs == null || selWAs.length == 0) {
			return;
		}
		double currentGlyphGapRatio;
		for (int i = 0; i < selWAs.length; i++) {
			currentGlyphGapRatio = selWAs[i].getGlyphGapRatio();
			if (currentGlyphGapRatio == glyphGapRatio) {
				continue;
			}
			selWAs[i].setGlyphGapRatio(glyphGapRatio);
			selWAs[i].parse(false);
		}
		manager.repaint();
	}
}
