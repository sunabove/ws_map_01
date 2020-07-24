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
	// 워드아트 그룹 매니저 리스트
	public static Hashtable groupManagers = new Hashtable();

	// 현 작업 워드 아트 그룹 매니저
	public static WordArtGroupManager currentWorkingManager;

	private static final long pasteInterval = 3000; // Paster interval time 0.6
	// second
	private static final double pasteMarginX = 10, pasteMarginY = 10;
	private double pasteNum = 0;
	// 새로 추가할 워드 아트의 기본 문자열
	private static String defaultStringOfInsertingWordArt = "문자열을 입력하십시오.";

	public static final int WAIT_MODE = 0, MOVE_MODE = 1, RESIZE_MODE = 2, ROTATE_MODE = 3, MOVE_CONTROL_POINT = 4;
	private int workMode = WAIT_MODE;

	private Hashtable wordArts = new Hashtable(); // 워드아트 객체들
	private Component targetComponent; // 작업 대상 컴포넌트
	private Hashtable selectedWordArts = new java.util.Hashtable(); // 현재 선택된 워드
	// 아트
	private Hashtable copiedWordArts = new java.util.Hashtable(); // 카피된 워드
	// 아트들
	private long lastPasteTime = System.currentTimeMillis(); // 최종 페이스트 시각.
	private MouseEvent lastMouseEvent; // 최종 마우스 이벤트

	private WordArt rotationRefWordArt, resizeRefWordArt; // 회전시, 리사이즈시 기준 워드
	// 아트.
	private int resizeTopology; // 리사이즈 위상. 마우스 커스 타입 값으로 설정한다.

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
			// 페이스트 시간 간격을 만족하지 않으면, 아무런 일도 하지 않는다.
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

		WordArtGallery.wordArtGallery.setTitle("WordArt 유형 선택(W):");
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
		// 문자열 편집은 한개의 워드 아트가 선택되었을 경우에만 허용한다.
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

	// 선택된 워드 아트들의 스타일과 매핑 타입 및 폰트를 바꾼다.
	public static void changeWordArtGallery() {
		WordArtGallery.wordArtGallery.setTitle("WordArt 유형 바꾸기(W):");
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

	// 선택된 워드 아트들의 스타일을 변경한다. 폰트는 변경하지 않는다.
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

		// 선택된 워드 아트가 하나도 없으면 추가하는 워드 아트를 선택 목록에 추가한다.
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
		// 포커스 획득 ( 키보드 입력을 받아 들이기 위해서)
		Component comp = (Component) e.getSource();
		comp.requestFocus();
		// 더블 클릭시 선택된 워드 아트가 하나이면, 워드 아트 에디트를 뛰운다.
		if (e.getClickCount() == 1 && this.getSelectedWordArtsNumber() == 1) {
			WordArtEditor.showEditorOnFront();
		}
		// 더블 클릭시 선택괸 워드 아트가 2개 이상이면, 스타일 에디터를 뛰운다.
		if (e.getClickCount() == 2 && this.getSelectedWordArtsNumber() > 1) {
			WordArtGroupManager.changeWordArtStyle();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void drawSelectedWordArtsGlobalMappingOutLines() {
		// 선택된 워드 아트의 외곽선을 그린다.
		WordArt[] selWAs = this.getSelectedWordArts();
		if (selWAs != null && selWAs[0] != null) {
			selWAs[0].drawMappingOutLines();
		}
		// 끝. 외곽선 그리기
	}

	public void mousePressed(MouseEvent e) {

		int top = this.getWordArtTopology(e);

		if (top == WordArt.CONTROL_POINT) {
			this.setWorkMode(WordArtGroupManager.MOVE_CONTROL_POINT);
			this.setCursor(e);
			// 선택된 워드 아트의 외곽선을 그린다.
			this.drawSelectedWordArtsGlobalMappingOutLines();
			// 끝. 외곽선 그리기
			// 마우스 이벤트 로그 기록.
			this.lastMouseEvent = e;
			// 끝. 마우스 이벤트 로그 기록.
			return;
		}
		// 워드 아트들을 회전할 경우.
		else if (this.getWorkMode() == ROTATE_MODE && top == WordArt.ROTATE_ON) {
			// 마우스가 눌러지면 키 입력을 받기 위해서 소스에 포커스를 요청한다.
			Component comp = (Component) e.getSource();
			comp.requestFocus();
			// 끝. 포커스 요청.

			// 회전 기준 워드 아트 검색.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				if (selWAs[i].getTopology(e) == WordArt.ROTATE_ON) {
					this.rotationRefWordArt = selWAs[i];
					break;
				}
			}
			this.drawSelectedWordArtsGlobalMappingOutLines();
			// 끝. 회전 기군 워드 아트 검색.

			// 마우스 이벤트 로그 기록.
			this.lastMouseEvent = e;
			// 끝. 마우스 이벤트 로그 기록.
			return;
		} else if (this.getWorkMode() == ROTATE_MODE && top == WordArt.EXT_OUT) {
			this.setWorkMode(WAIT_MODE);
			this.setCursor(e);
		} else if (top >= 0 && top <= 7) {
			// 리사이즈 모드인 경우.
			this.setWorkMode(RESIZE_MODE);
			this.setCursor(e);

			// 리사이즈 기준 워드 아트 검색.
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
			// 끝. 리사이즈 기군 워드 아트 검색.

			this.lastMouseEvent = e;
			return;
		}

		WordArt[] WAs = this.getWordArts();

		// 로테이션 모드가 아니고, 마우스 위치가 로테이션 원위에 없으면,
		// 워드 아트 에서 마우스 프레스 이벤트를 먼저 처리한다.
		if (WAs != null) {
			for (int i = 0; i < WAs.length; i++) {
				WAs[i].mousePressed(e);
			}
		}

		// 마우스 이벤트를 처리하고나면,
		// 마우스가 적어도 하나의 워드 아트 위에 있는 지 조산한다.
		boolean mouseOver = this.mouseOverSelectedWordArts(e);

		if (!e.isShiftDown()) {
			// 슆트키가 눌러지지 않은 채로,
			// 마우스가 워드 아트 외부에서 눌러지면, 선택 목록을 비운다.
			// 눌러진 워드 아트가 하나도 없으면, 선택 목록을 비운다.
			if (!mouseOver) {
				this.deSelectAll();
				// this.repaint();
			}
		}

		if (mouseOver && e.getModifiers() != InputEvent.BUTTON3_MASK) {
			// 마우스가 선택된 워드 아드 위에서 왼쪽 마우스 버튼이 눌러지면 워드 아트를 이동 할 수 있는
			// 모드로 들어간다.
			this.workMode = MOVE_MODE;
		}

		if (this.getSelectedWordArtsNumber() == 0) {
			WordArtEditor.hideEditor();
		}

		if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
			// 마우스가 선택된 워드 아트 위에서 오른 쪽 마우스 버튼을 누르면
			// 워드 아트 에디터 팝업 메뉴를 표시한다.
			com.jwordart.ui.comp.WordArtEditor.wordArtEditor.showWordArtEditorPopupMenu(e);
		} else {
			com.jwordart.ui.comp.WordArtEditor.wordArtEditor.showWordArtEditorPopupMenu(null);
		}

		this.repaint();

		// 마우스가 눌러지면 키 입력을 받기 위해서 소스에 포커스를 요청한다.
		Component comp = (Component) e.getSource();
		comp.requestFocus();
		// 끝. 포커스 요청.

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
		// 마우스를 떼면, 선택된 워드 아트들을 이동시킨다.
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
			// 회전 각도를 계산한다.
			double radian = this.getRotationAngle(e);
			// 선택된 워드 아트들을 회전한다.
			WordArtGroupManager.rotateSelectedWordArtsByAngle(radian);
			this.repaint();
			;
		} else if (workMode == RESIZE_MODE) {
			// 위치 및 크기를 재 조정한다.
			this.resizeSelectedWordArts(e);
			// 끝. 위치 및 크기 재 조정.
			// 선택된 워드 아트들을 글립을 리스케일 한다.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				// 전체 영역을 재설정하지 않고 선택된 워드 아트를 파싱한다.
				selWAs[i].parse(false);
			}
			// 끝. 선택된 워드 아트 글립 리스케일.
			// 재 페인팅한다.
			this.repaint();
			// 끝. 재 페인팅.
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
		// 워드 아트 이동 모드이면 마우스 드래깅시에는 매핑 외관선만을 이동한다.
		double workMode = this.workMode;

		if (workMode == MOVE_CONTROL_POINT) {
			this.moveSelectedWordArtControlPointsBy(e);
		} else if (workMode == MOVE_MODE) {
			// 워드 아트들의 매핑 외곽선이 이동한 간격을 설정한다.
			int dx = e.getX() - lastMouseEvent.getX(), dy = e.getY() - lastMouseEvent.getY();
			// 선택된 워드 아트들의 매핑 외곽선을 이동한다.
			WordArt[] selWAs = this.getSelectedWordArts();
			for (int i = 0; i < selWAs.length; i++) {
				selWAs[i].drawMappingOutLineMovedNrotatedNresizedBy(dx, dy, 0, 0, 0);
			}
		} else if (workMode == ROTATE_MODE) {
			// 회전 각도를 계산한다.
			double radian = this.getRotationAngle(e);
			// 선택된 워드 아트들을 회전한 다음 외곽선을 그린다.
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
		// 리사이즈 벡터 (렉탱글로 구현)
		Shape L = new Rectangle.Double(e.getX() - lastMouseEvent.getX(), e.getY() - lastMouseEvent.getY(), 0, 0);
		// 리사이즈 벡터를 회전 각도 만큼 역으로 다시 회전함.
		// 기군 도형들은 회전 되어 있지 않기 때문에( 페인트 시에만 그래픽스를 회전하기 그린다.)
		WordArt resizeRefWordArt = this.resizeRefWordArt;

		double radian = resizeRefWordArt.getWordArtStyle().getRotationRadianAngle();

		// 수직 문자열 각도 보정
		if (resizeRefWordArt.isVertical()) {
			radian -= Math.PI / 2.0;
		}
		// 끝. 수직 문자열 각도 보정

		if (radian != 0) {
			AffineTransform at = AffineTransform.getRotateInstance(-radian);
			L = at.createTransformedShape(L);
		}

		// 끝. 리사이즈 벡터 역 회전.
		// 끝. 리사이즈 벡터 구하기.

		// 리사이즈 벡터로 부터 리사이즈 값 증분 구하기.
		double dx = L.getBounds2D().getX(), dy = L.getBounds2D().getY();

		/*
		 * if( resizeRefWordArt.isVertical() ) { double tempDx = dx; dx = dy; dy =
		 * tempDx; }
		 */

		double dw = 0, dh = 0;
		// 끝. 리사이즈 값 증분 구하기.

		// 리사이즈 위상에 따라서 dx, dy, dw, dh 값 설정.
		int top = this.getResizeTopology();

		switch (top) {
		case Cursor.NW_RESIZE_CURSOR:
			// 북서 방향 : 위치 및 크기 재설정
			// dx, dy, dw, dh 설정
			dw = -dx;
			dh = -dy;
			break;
		case Cursor.N_RESIZE_CURSOR:
			// 북 방향 : 위치 및 크기 재설정
			// dy, dh 설정
			dh = -dy;
			dx = 0;
			dw = 0;
			break;
		case Cursor.NE_RESIZE_CURSOR:
			// 북동 방향 : 위치 및 크기 재설정
			// dy, dw, dh 설정
			dw = dx;
			dh = -dy;
			dx = 0;
			break;
		case Cursor.W_RESIZE_CURSOR:
			// 서 방향 : 위치 및 크기 재설정
			// dx, dw 설정
			dw = -dx;
			dy = 0;
			dh = 0;
			break;
		case Cursor.E_RESIZE_CURSOR:
			// 동 방향 : 크기 재설정
			// dw 설정
			dw = dx;
			dx = dy = dh = 0;
			break;
		case Cursor.SW_RESIZE_CURSOR:
			// 남서 방향 : 위치 및 크기 재설정
			// dx, dw, dh 설정
			dw = -dx;
			dh = dy;
			dy = 0;
			break;
		case Cursor.S_RESIZE_CURSOR:
			// 남 뱡향 : 크기 재설정
			// dh 설정
			dh = dy;
			dx = dy = dw = 0;
			break;
		case Cursor.SE_RESIZE_CURSOR:
			// 남동 방향 : 크기 재설정
			// dw, dh 설정
			dw = dx;
			dh = dy;
			dx = dy = 0;
			break;
		}
		// 끝. dx, dy, dw, dh 값 설정

		// 선택된 워드 아트들의 크기 및 위치를 재 설정한다.
		WordArt[] selWAs = this.getSelectedWordArts();
		for (int i = 0; i < selWAs.length; i++) {
			selWAs[i].drawMappingOutLineMovedNrotatedNresizedBy(dx, dy, 0, dw, dh);
		}
		// 끝. 크기 및 위치 재 설정.
	}

	// 회전 기준 워드 아트를 기준으로 회전 각도를 계산한다.
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
				// 로테이션 모드에서 사용자가 글립위에 커서를 가져오면,
				// 커서의 모양을 무브 커서로 바꾼다.
				cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
			} else if (topology != WordArt.ROTATE_ON) {
				cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			}
		} else if (this.getWorkMode() == WordArtGroupManager.RESIZE_MODE) {
			// 사용자가 마우스를 눌러서 리사이즈 모드로 들어가면,
			// 커서를 크로스 헤어 커서로 바꾼다.
			if (topology >= 0 && topology <= 7) {
				// 리사이즈 위상을 커서 타입의 값으로 설정한다.
				this.resizeTopology = cursor.getType();
				// 끝. 리사이즈 위상 설정.
				// 커스를 크로스 헤어 커서로 바꾼다.
				cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
				// 끝. 크로스 헤어 커서로 바꾸기.
			}
		}

		comp.setCursor(cursor);
	}

	public int getResizeTopology() {
		return this.resizeTopology;
	}

	public void copySelectedWordArts() {
		WordArt[] WAs = this.getSelectedWordArts();
		// 복사 목록을 비운다.
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
			// 전체 영역을 재설정 하지 않고서 재 파싱 한다.
			selWAs[i].parse(false);
			// 끝. 재 파싱
		}
		// 다시 그린다.
		manager.repaint();
		// 끝. 다시 그리기
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
			// 정렬 방식을 설정한다.
			selWAs[i].setAdjustmentType(pType);
			// 끝. 정렬 방식 설정
			// 전체 영역을 재설정 하지 않고서 재 파싱 한다.
			selWAs[i].parse(false);
			// 끝. 재 파싱
		}
		// 다시 그린다.
		manager.repaint();
		// 끝. 다시 그리기
	}

	// 선택된 워드 아트들의 수직 모드를 토글한다.
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
			// 수직 모드 토글
			selWAs[i].setVertical(!vertical);
			// 끝. 수직 모드 토글
			// 전체 영역을 재설정하지 않고서 재 파싱한다.
			selWAs[i].parse(false);
			// 끝. 재 파싱
		}
		manager.repaint();
	}

	// 선택된 워드 아트들의 매핑 타입을 바꾼다.
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

	// 선택된 워드 아트들의 글립 간격을 바꾼다.
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
