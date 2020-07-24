package jchartui;

/*******************************************************************************
 * PROJECT     : JWord
 * CLASS       : SICSpindle
 * DESCRIPT    : 1개의 TextField와 2개의 Arrow버튼(위, 아래)으로 이루어진 것으로
 *               기본적으로 계산은 float형으로 행해지며
 *               실제적으로 보여지는 값은 int형 또는 float형이 될 수 있다.
 *
 * UPDATE      : Han, Won-gun(2000/09/28)
 * DESCRIPT    : CustomEvent 관련 추가
 *
 * LAST UPDATE : Park, Ho-young(2001/02/12)
 * DESCRIPT    : SICSpindle에 관한 Error처리와 Option에서 단위 변경시 처리부분 추가
 ********************************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.border.EtchedBorder;
import java.util.EventObject;
import java.util.Vector;
import java.util.Enumeration;
import java.lang.NumberFormatException;

public class SICSpindle extends JPanel implements KeyListener, ActionListener, FocusListener  {
	//UI관련
	//실제 입력이 되는 곳
	protected JTextField spinTextField;

	//ArrowUp버튼과 ArrowDown버튼이 올라가는 Panel
	protected JPanel buttonPanel    = new JPanel();
	protected ArrowButton arrowUp   = new ArrowButton(ArrowButton.ARROW_UP);
	protected ArrowButton arrowDown = new ArrowButton(ArrowButton.ARROW_DOWN);


	//단위 관련
	//단위를 보여줄 것인지의 여부
	protected boolean   isUnit = false;

	public static final String PERCENT = "\uFF05";	//비율(%) 표시
	public static final String DEGREE  = "\u00B0";	//각도 표시
	//Option에 따라 mm, cm, pixel중의 하나로 변하게 될 것이다.
	//Default로 보여지는 단위는 mm단위
	protected String unitText = "\u339C";

	protected char    availableChar; //입력이 허용된 문자를 나타냄
	protected boolean isFitIn;	     //입력범위초과시 값을 범위로 바꿈.
	protected boolean isInt;	     //int 형인지 아닌지..
	protected int     fraction;
	protected float   increment;     //Arrow버튼을 눌렀을 경우 기본적으로 증가, 감소하는 값

	protected float   val;           //각 경우에 따라 float형으로 계산되어진 Spindle의 값
	protected float   value;         //실제로 Spindle에 보여지는 값(int형 또는 float형)
	protected float   minValue;	     //최소값
	protected float   maxValue;      //최대값

	//listener를 저장할 Vector
	//사용법은 커스텀Event를 만드는 방법을 참조할것
	protected Vector listeners = new Vector();

	//현재 Spindle에서 보여줄 단위
	//protected byte unitMode;
	public byte unitMode;

	//최소값보다 작거나 최대값보다 큰 경우 Error Message를 띄워 줄때 사용하기 위함
	//최소, 최대값이 어느 경우에나 항상 같은 것이 아니므로 이 두 값이 동적으로 변함에
	//따라 그 값을 String으로 만들어서 사용한다.
	String minString = "0";
	String maxString = "558";


	//====================================================================
	//생성자는 크게 단위가 있는 경우와 없는 경우의 두 개로 나뉜다.
	//단위가 있는 경우는 unitMode에 따라 설정되는 값이 달라질 것이지만
	//단위가 없는 경우는 아예 unitMode를 무시해도 상관없기 때문이다.
	//====================================================================
	/**
	 * 단위가 있는 경우
	 * 현재 Spindle에서 사용할 단위만 인자로 받는 생성자
	 * 최소값과 최대값은 기본값이 설정되어질 것이다.
	 */
	public SICSpindle(byte unitMode) {
		this.unitMode = unitMode;

		minValue = 0.0F;
		maxValue = 558.7F;

		isInt   = true;
		isFitIn = true;
		isUnit  = true;

		init();
	}

	/**
	 * 단위가 있는 경우
	 * 최소값, 최대값, 현재 Spindle에서 사용할 단위를 인자로 가지는 생성자
	 */
	public SICSpindle(float min, float max, byte unitMode) {
		this.unitMode = unitMode;

		minValue = min;
		maxValue = max;

		isInt   = true;
		isFitIn = true;
		isUnit  = true;

		init();
	}


	/**
	 * 단위가 있는 경우
	 * 필요한 모든 값을 임의로 정해서 사용할 수있도록 전부 인자로 받는 생성자
	 */

	public SICSpindle(float min, float max, float increment, boolean isInt, byte unitMode) {
		this.unitMode = unitMode;

		minValue = min;
		maxValue = max;

		fraction = 10;
		this.increment = increment;
		this.isInt    = isInt;
		this.isUnit   = true;
		isFitIn  = true;

		init();
	}


	/**
	 * 단위가 없는 경우
	 * 최소값, 최대값, 증감값, intMode인지를 인자로 받는 생성자
	 */
	public SICSpindle(float min, float max, float increment, boolean isInt) {
		minValue = min;
		maxValue = max;

		this.increment = increment;
		this.isInt     = isInt;

		isFitIn  = true;
		isUnit   = false;

		this.init();
	}

	//========================================================================
	//지울 것
	//=========================================================================
	public SICSpindle() {

		fraction = 10;
		this.increment = 1.0F;
		this.isInt    = true;
		this.isUnit   = true;
		isFitIn  = true;

		init();
	}
	//==========================================================================


	/**
	 * 초기화
	 */
	public void init() {
		setSize(55, 23);
		setLayout(null);

		//===========================================================
		//모든 SICSpindle은 생성시에 mm를 기준으로 생성되어지므로
		//최대값과 최소값도 mm를 기준으로 설정되어진다. 그러므로
		//mm단위일 때를 제외하고는 cm나 pixel일 때는 단위에 맞게
		//최소값과 최대값을 계산을  해 주어야 한다.
		//===========================================================

		//System.out.println("SICSpindle에서 unitMode: " + this.unitMode);

		//==============================================================
		//단위가 있을 경우
		//==============================================================
		if (this.isUnit) {
			//mm단위일 때
			if (unitMode == 0) {
				unitText = "\u339C";

				//mm단위일 때는 정수형태로 나타내고
				//기본 증가값(또는 감소값)은 1(mm)이다.
				//isInt = true;
				increment = 1F;

			//cm단위일 때
			} else if (unitMode == 1) {
				unitText = "\u339D";

				//=======================================================
				//SICSpindle에서 기본적으로 float형을 나타낼 때는
				//소수 둘째 자리까지만 나타내기로 한다. 그러므로,
				//소수 셋째 자리이하는 무조건 버린다.
				//=======================================================
				minValue = getFloatValueToSecondPoint(this.minValue/10.0F);
				maxValue = getFloatValueToSecondPoint(this.maxValue/10.0F);

				//cm단위일 때는 무조건 실수 형태로 나타내고
				//기본 증가값(또는 감소값)은 0.1(cm)이다.
				isInt = false;
				increment = 0.1F;

			//pixel단위일 때
			} else if (unitMode == 2) {
				unitText = "pixel";

				//1mm는 2.8346pixel이다.
				minValue = getFloatValueToSecondPoint(minValue * 2.8346F);
				maxValue = getFloatValueToSecondPoint(maxValue * 2.8346F);

				//pixel단위일 때는 정수형태로 나타내고
				//기본 증가값(또는 감소값)은 1(pixel)이다.
				isInt = true;
				increment = 1.0F;
			}


		//==================================================================
		//단위가 없을 경우
		//==================================================================
		} else {
			unitText = "";
		}

		if(isInt) {
			spinTextField = new JTextField("0");

			this.minString = String.valueOf((int)minValue) + this.unitText;
			this.maxString = String.valueOf((int)maxValue) + this.unitText;
		} else {
			spinTextField = new JTextField("0.0");

			this.minString = String.valueOf(minValue) + this.unitText;
			this.maxString = String.valueOf(maxValue) + this.unitText;
		}


		setComponentBounds();
		addListeners();
	}


	/**
	 * Spindle위에 각 컴포넌트들의 크기와 배치 지정
	 */
	public void setComponentBounds() {
		//============================================================
		// Spindle의 크기에 따라 TextField나 ArrowButton들의 위치가
		// 동적으로 조절되어 원하는 크기대로 Spindle을 지정할 수 있다.
		//============================================================
		spinTextField.setBorder(BorderFactory.createEtchedBorder());
		spinTextField.setBounds(1, 1, getWidth() - 17, getHeight() - 2);

		buttonPanel.setLayout(null);
		buttonPanel.setBounds(getWidth() - 17, 1, getHeight() - 2, getHeight());

		arrowUp.setBounds(0, 0, 17, buttonPanel.getHeight() / 2);
		arrowDown.setBounds(0, buttonPanel.getHeight() / 2, 17, buttonPanel.getHeight() / 2 - 1);

		buttonPanel.add(arrowUp);
		buttonPanel.add(arrowDown);

		add(spinTextField);
		add(buttonPanel);
	}

	/**
	 * 각 컴포넌트에 필요한 이벤트 등록하기
	 */
	private void addListeners() {

		//TextField 관련
		spinTextField.addActionListener(this);
		spinTextField.addKeyListener(this);
		spinTextField.addFocusListener(this);

		//ArrowButton들 관련
		arrowUp.addActionListener(this);
		arrowUp.getTimer().addActionListener(this);

		arrowDown.addActionListener(this);
		arrowDown.getTimer().addActionListener(this);
	}

	/**
	 * Spindle의 크기를 지정하지 않는다면
	 * 최소사이즈로 (90, 22)로 크기가 정해진다.
	 */
	public Dimension getMinimumSize() {
		return new Dimension(90, 22);
	}

	/**
	 * 적정 사이즈로는 최소 사이즈를 반환
	 */
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}


	/**
	 * CustomEvent를 구현하기 위해 필요한 메소드
	 * SICSpindle로부터 통보를 받고자 하는 어떠한 객체라도,
	 * SICComponentValueListener를 구현할수 있다.
	 */
	public void addSICComponentValueListener(SICComponentValueListener listener){
		if (!listeners.contains(listener))
			listeners.addElement(listener);
	}

	public void removeSICComponentValueListener(SICComponentValueListener listener){
		listeners.removeElement(listener);
	}

	/**
	 * SICComponentValueEvent를 발생시켜 아래 메소드를 호출함으로써 통보를 하게 된다.
	 */
	public void notifyListeners(int state){
		// 복제본에서 수행하기 위해 listener들의 벡터를 복제
		Vector copyofLIsteners = (Vector)(listeners.clone());

		// 현재 값을 캡슐화하는 SICComponentValueEvent 생성
		SICComponentValueEvent event = new SICComponentValueEvent(this, state);

		// 각 listener에 알린다.
		Enumeration enumIt = copyofLIsteners.elements();
		while (enumIt.hasMoreElements()){
			SICComponentValueListener listener = (SICComponentValueListener)enumIt.nextElement();
			listener.sicComponentValueChanged(event);
		}
	}

	//+++++++++++++++++++++     FocusEvent 처리    ++++++++++++++++++++++++++//
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	public void focusGained(FocusEvent e) {}
	public void focusLost(FocusEvent e) {

		// 2000/9/28 원건
		// value가 바뀌었을때 event 발생
		notifyListeners(SICComponentValueEvent.SICCOMPONENT_VALUE_CHANGED);
	}


	//+++++++++++++++++++++++     KeyEvent 처리    ++++++++++++++++++++++++++//
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	public void keyTyped(KeyEvent keyevent) {}
    public void keyPressed(KeyEvent keyevent) {}
    public void keyReleased(KeyEvent e) {
		availableChar = e.getKeyChar();

		// keyReleased()에서도 value를 저장해야 한다.
		// operateValue()를 수정 후 호출한다.operateValueWongun()을 참고 한다.
		operateValue(true);
	}


	//+++++++++++++++++++     ActionEvent 처리    +++++++++++++++++++++++++++//
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	public void actionPerformed(ActionEvent e) {
		//System.out.println("getSpinValue()): " + getSpinValue());
		//System.out.println("Double.isNaN(getSpinValue()): " + Double.isNaN(getSpinValue()));

		//ArrowUp Button에 관한 처리
		if(isEnabled() && (e.getSource() == arrowUp || e.getSource() == arrowUp.getTimer())) {
			//=========================================================
			// NaN()이라는 값은 크기가 없이 0나누기 0과 같이 단지 어떤
			// 특정 실수 연산의 결과를 나타내기 위해 사용된다.
			// SICSpindle의 경우 아무 값도 입력되지 않은 상태,
			// 즉, TextField가 공백인 상태에서 ArrowUp 버튼을 눌렀을 때
			// isNan()이 true가 된다.
			//==========================================================
			if(Double.isNaN(getSpinValue())) {
				value = minValue;

				if(isInt) {
					spinTextField.setText(String.valueOf((new Float(value)).intValue()));

				} else {
					spinTextField.setText(String.valueOf(value));
				}

			//Spindle의 값이 최대값보다 작을 경우
			} else if(getSpinValue() < maxValue) {
				//System.out.println("value: " + value);
				//System.out.println("increment: " + this.increment);
				// 2000/9/29 원건 수정
				// key로 입력하였을때 최소값보다 적다면 최소값-1로 셋팅하여
				// 처음으로 arrow버튼을 누를때 최소값을 보여주고 상승시킨다.
				if (getSpinValue() < minValue) {
					value = minValue-1;
				}

				//ArrowUp 버튼을 누르고 나서 increment만큼 증가한 값을 구한다.
				//value = trimValue(value + increment);

				value = getFloatValueToSecondPoint(value + increment);

				//System.out.println("Up버튼을 누르고 난 후 value: " + value);

				//그 값이 유효한 값인지 확인한다.
				operateChar();
				operateValue(false);

				//Spindle의 값이 최대값보다 크다면
				if(value > maxValue) {
					//Spindle에는 최대값을 보여준다.
				    value = maxValue;
				    operateChar();
				}

			//Spindle의 값이 최대값보다 큰 값이 입력된 경우
			} else if (getSpinValue() > maxValue) {
				//Spindle의 값이 최대값보다 큰 값이면 최소값-1로 셋팅하여
				//처음으로 ArrowUp 버튼울 누를 때 최소값을 보여주고 상승시킨다.
				value = minValue-1;
			}

		//ArrowDown Button에 관한 처리
		} else if(isEnabled() && (e.getSource() == arrowDown || e.getSource() == arrowDown.getTimer())) {
			//현재 Spindle에 아무 값도 입력되어 있지 않은 상태
			//즉, 공백인 상태에서 ArrowDown 버튼을 눌렀을 경우
			if(Double.isNaN(getSpinValue())) {
			    value = minValue;
				if(isInt)
			        spinTextField.setText(String.valueOf((new Double(value)).intValue()));
			    else
			        spinTextField.setText(String.valueOf(value));

			//Spindle의 값이 최소값보다 클 경우
			} else if(getSpinValue() > minValue) {
				// 2000/9/29 원건 수정
				// key로 입력하였을때 최대값보다 적다면 최대값 + 1로 셋팅하여
				// 처음으로 Arrow버튼을 누를때 최대값을 보여주고 하강한다.
				if (getSpinValue() > maxValue) {
					value = maxValue + 1;
				}

				//value = (float)trimValue(value - increment);
				value = getFloatValueToSecondPoint(value - increment);

				operateChar();
			    operateValue(false);

				if(value < minValue) {
			        value = minValue;
			        operateChar();

			    }

			//Spindle의 값이 최소값보다 작은 값이 입력된 경우
			} else if (getSpinValue() < minValue) {
				value = maxValue + 1;
			}

		//TextField에 관한 처리
		} else if(e.getSource() == spinTextField && isFitIn) {
	        operateValue(false);
	        operateChar();
		}

		// 2000/9/28 원건
		// value가 바뀌었을때 event 발생
		notifyListeners(SICComponentValueEvent.SICCOMPONENT_VALUE_CHANGED);

	    fireSpinValueChangedEvent();

	    spinTextField.requestFocus();
		spinTextField.selectAll();
	}


	/** 입력된 값을 받아 그값이 유효한지 검사하여 적절히 setting한다.
	 *	isKeyReleased이 true이면 key가 눌러진 상태로 범위가 넘어섰다하더라도 max, min값을 셋팅하지 않는다.
	 */
	public boolean operateValue(boolean isKeyReleased) {
		try {
			//문자를 파싱하여 일단 double형태로 만든후 계산을 한다.
			String parse = spinTextField.getText().trim();

			char c[] = new char[parse.length()];
			int j = 0;
			String parsedouble = "";
			String unit;	//단위를 구분할 임시변수
			for(int i=0; i< (parse.length()) ; i++) {
				if(Character.isDigit(parse.charAt(i)) || parse.charAt(i) =='.' || parse.charAt(i)=='-') {	//숫자이면,소수점,음수체크
					c[j] = parse.charAt(i);			//c[]를 하나씩증가시키면서 저장
					j++;
				}else {
					break;
				}

			}

			parsedouble = String.valueOf(c);

			if(parsedouble.equals("")) {
				value = (0.0F / 0.0F);
				return true;
			}

			val = Float.valueOf(parsedouble).floatValue();

			if(isFitIn)	{
				// 2000/9/29 원건 수정
				if (maxValue < val && !isKeyReleased){
					value = maxValue;

				} else if (minValue > val && !isKeyReleased){
					value = minValue;

				} else {
					value = val;
				}


			} else {
				value = val;
			}

			return true;

		} catch(NumberFormatException exc) {

			// NumberFormatException이 일어나면 우선 최소값-1으로 셋팅한다.
			value = this.minValue-1;

			//System.out.println("NumberFormatException: " + exc);


			return false;

		} catch(Exception ex) {

			//System.out.println("Exception");


			return false;
		}
	}


	//입력된 문자가 적절한 문자인지 검사후 값을 setting한다. - 현재 '-'값은 생각할필요없다.왜??
	void operateChar() {
		if(Double.isNaN(value) && !spinTextField.getText().equals("-")) {
			this.spinTextField.setText("");
			return;
		}
		if(isInt) {
		    if(!spinTextField.getText().equals("-")) {
				if(isUnit) {//단위가 있으면
					spinTextField.setText(String.valueOf((new Float(getSpinValue())).intValue())+unitText);
					return;
				} else {//단위가 없으면
					spinTextField.setText(String.valueOf((new Float(getSpinValue())).intValue()));
					return;
				}
		    }
		} else {
			try {
				if(!spinTextField.getText().endsWith(".") && spinTextField.getText().indexOf(".") != -1 || (new Double(spinTextField.getText())).doubleValue() != getSpinValue()) {
					if(isUnit) {
						spinTextField.setText(String.valueOf(getSpinValue())+unitText);
						return;
					} else {
						spinTextField.setText(String.valueOf(getSpinValue()));
						return;
					}
				}
			} catch(NumberFormatException ex) {
			    if(!spinTextField.getText().equals("-")) {
					if(isUnit) {
						spinTextField.setText(String.valueOf(getSpinValue()));
						return;
					} else {
						spinTextField.setText(String.valueOf(getSpinValue()));
						return;
					}
				}
			}	//catch
		}//else
	}


	/**
	 * Spindle의 값이 유효한 숫자인지 아닌지 판별
	 */
	public boolean isDecimal() {
		try {
			//여기는 무조건 숫자만 입력된 경우이다.
			//Spindle에 있는 값을 읽어온다.
			String spinString = this.spinTextField.getText();

			int intValue       = new Float(spinString).intValue();
			float floatValue = new Float(spinString).floatValue();

			if(isInt) {
				if(isUnit) {
					spinTextField.setText(String.valueOf(intValue) +unitText);
				} else {
					spinTextField.setText(String.valueOf(intValue));
				}


			} else {
				if(isUnit) {
					spinTextField.setText(String.valueOf(floatValue)+unitText);
				} else {
					spinTextField.setText(String.valueOf(floatValue));
				}
			}

			return true;

		} catch(NumberFormatException e) {

			//System.out.println("단위가 같이 입력된 상태인가? : " + this.isUnit);
			//System.out.println("Exception이 발생하는 경우 Spindle의 값  : " + this.getSpinValue());

			//Spindle에 아무값도 없을 경우
			if (spinTextField.getText().length() == 0) {
				//System.out.println("Spindle에 아무 값도 없을 경우(NaN이 true가 된다): " + this.getSpinValue());

				JOptionPane.showMessageDialog(this.getParent(),"You must input values","JWord", JOptionPane.WARNING_MESSAGE);
				this.spinTextField.requestFocus();
				this.spinTextField.setText("");

				return false;

			//Spindle에 숫자, 문자 및 공백 포함된 경우
			} else {
				//System.out.println("Spindle이 공백이 아닌 경우 ");
				//System.out.println("Spindle의 마지막값이 단위인가?: " + string.endsWith(this.unitText));

				//==========================================================
				//Spindle에서 단위를 찾아서 단위를 제외한 나머지들을 가지고
				//Error를 체크하는 부분이다.
				//==========================================================
				String string = spinTextField.getText().trim();
				int searchEndIndex = string.length()-1;


				//현재 단위가 pixel일 경우
				if (this.unitMode == 2) {
					String tempString = string.substring(string.length()-5, string.length());

					//System.out.println("string: " + string);
					//System.out.println("tempString: " + tempString);
					//System.out.println("unitText: " + unitText);
					//System.out.println("string.charAt(string.length()-5: " +  string.charAt(string.length()-5));
					//System.out.println("string.charAt(string.length()-6: " +  string.charAt(string.length()-6));

					if (tempString.equals(this.unitText)) {
						searchEndIndex = string.length()-5;
					}
			    } else {
					//Spindle의 마지막값이 단위라면
					if (string.endsWith(this.unitText)) {
						searchEndIndex = string.length()-1;
					}
				}


				//단위앞까지의 값을 검색해서 유효한 숫자인지를 판단한다.
				try {
					char c[] = new char[searchEndIndex];
					int j = 0;
					for (int i = 0; i < searchEndIndex; i++) {
						c[j] = string.charAt(i);
						j++;
					}

					//단위 앞까지의 값을 검색한 결과 전부 숫자였을 경우
					//Double형의 값을 구한다.
					this.val = Float.valueOf(String.valueOf(c)).floatValue();
					//System.out.println("계산하는 중에...." + val);

					if(isFitIn)	{
						//계산된 값이 최대값보다 크다면
						//Spindle에는 최대값이 보여지게 될 것이다.
						if (maxValue < val ){
							value = maxValue;

						//계산된 값이 최소값보다 작다면
						//Spindle에는 최소값이 보여지게 될 것이다.
						} else if (minValue > val){
							value = minValue;

						//계산된 값이 최소값과 최대값사이에 있다면
						//그 값이 Spindle에 보여줄 값이 된다.
						} else {
							value = val;
						}

					} else {
						value = val;
					}

					return true;

				} catch(NumberFormatException ex) {
					//System.out.println("단위없이 다른 문자가 입력된 경우 ");

					JOptionPane.showMessageDialog(this.getParent(),"Invalid number","JWord", JOptionPane.WARNING_MESSAGE);
					spinTextField.requestFocus();
					spinTextField.setText("");

					return false;
				}
			}
		}
	}

	/**
	 * 유효한 숫자가 입력된 경우 그 값이 최소값과 최대값 사이에 있는지 판별
	 */
	public boolean isEnableValue() {
		if(this.isDecimal()) {
			//입력된 숫자가 최소값과 최대값 사이에 있으면
	        //if((this.val >= this.minValue) && (this.val <= this.maxValue) ) {
			 if((this.value >= this.minValue) && (this.value <= this.maxValue) ) {
				//true를 반환하고 적용이 될 것이다.
				return true;

			//만약 최대값보다 크거나 최소값보다 작은 값이 입력되었다면
			//Error 메세지를 띄워준다.
			} else {
				String string1 = "Input number between ";
				String string2 = " and ";
				String string3 = ".";

				//이렇게 하는 이유는 최소값과 최대값이 어느 경우에나 다 같지 않으므로
				//최소값, 최대값에 대한 Error를 유동적으로 관리하기 위함.
				String errorString = string1 + minString + string2 + maxString + string3;

				JOptionPane.showMessageDialog(this.getParent(), errorString, "JWord", JOptionPane.WARNING_MESSAGE);

				spinTextField.requestFocus();
				//spinTextField.setText("");
				spinTextField.selectAll();
			}
		}
		return false;
	}

	/**
	 * 소수 셋째 자리에서 버림하는 함수
	 */
	public  float getFloatValueToSecondPoint(float value) {
		float floatValue = value *100.0F;
		float changedValue = ((int)floatValue)/100.0F;

		//float floatValue = value *10.0F;
		//float changedValue = ((int)floatValue)/10.0F;

		return  changedValue;
	}


	/**
	 * 소수 첫째 자리를 버림하여 실수를 정수로 나타내는 함수
	 */
	public double roundValue(float d1) {
		if(d1 > 0.0F)
			return (double)Math.round(d1 * Math.pow(10D, (double)fraction)) / Math.pow(10D, (double)fraction);
		else
			return (double)(-Math.round(-d1 * Math.pow(10D, (double)fraction + 1.0D))) / Math.pow(10D, (double)fraction + 1F);
	}


	/**
	 *
	 */
	public double trimValue(float d) {
		float trimming = d;	//값 세팅
		int trimmed = (int)trimming;	//정수부를 trimed에 저장
		float rest = trimming - (float)trimmed;	//나머지소수부를 rest에 저장
		rest = rest*10F;	//소수부에 10을 곱하여 나머지를 자른다. - 10은 추후 변수로 지정할것
		rest = Math.round(rest);
		rest = (float)roundValue(rest* 0.1F);

		trimming = (float)trimmed + rest;
		if(trimming > 0.0F)
			return (double)(trimming * Math.pow(10D, (double)fraction)) / Math.pow(10D, (double)fraction);
		else
			return (double)(-Math.round(-trimming * Math.pow(10D, (double)fraction + 1.0D))) / Math.pow(10D, (double)fraction + 1);

	}



	public void fireSpinValueChangedEvent() {
	/*	int i1 = j.size();
		SpinValueChangedEvent spinvaluechangedevent = new SpinValueChangedEvent(this, getSpinValue());
		for(int j1 = 0; j1 < i1; j1++)
			((SpinValueChangedListener)j.elementAt(j1)).spinValueChanged(spinvaluechangedevent);
	*/
	}

    /**
     * SICSpindle의 활성화, 비활성화를 설정
     */
	public final void setEnabled(boolean isEnabled) {
		//======================================================
		//SICSpindle은 하나의 Component로 이루어진 것이 아니라
		//여러개의 Component로 이루어졌으므로 SICSpindle이
		//포함되는 모든 Component들을 활성화/비활성화시켜주어야
		//SICSpindle전체가 적용이 된다.
		//======================================================
		super.setEnabled(isEnabled);

		//System.out.println("isEnabled: " + isEnabled);
		spinTextField.setEnabled(isEnabled);
		arrowUp.setEnabled(isEnabled);
		arrowDown.setEnabled(isEnabled);

		//활성화상태(default)
		if(isEnabled) {
			arrowUp.getTimer().addActionListener(this);
			arrowDown.getTimer().addActionListener(this);
			return;

		//비활성화상태
		} else {
			arrowUp.getTimer().removeActionListener(this);
			arrowDown.getTimer().removeActionListener(this);
			return;
		}
	}


	/**
	 * 최소값 설정
	 */
	public final void setMin(float min){
		minValue = min;

		if (this.isInt) {
			this.maxString = String.valueOf((int)minValue) + this.unitText;

		} else {
			this.maxString = String.valueOf(minValue) + this.unitText;
		}

	}

	/**
	 * 최소값 반환
	 */
	public final float getMin() {
		return minValue;
	}


	/**
	 * 최대값 설정
	 */
	public final void setMax(float max) {
		maxValue = max;

		if (this.isInt) {
			this.maxString = String.valueOf((int)maxValue) + this.unitText;

		} else {
			this.maxString = String.valueOf(maxValue) + this.unitText;
		}


	}

	/**
	 * 최대값 반환
	 */
	public final float getMax() {
		return maxValue;
	}


	/**
	 * 증감값을 설정
	 */
	public final void setIncrement(float incre) {
		increment = incre;
	}

	/**
	 * 현재 설정되어진 증감값을 반환
	 */
	public final float getIncrement() {
		return increment;
	}

	/**
	 * Spindle에 내용을 실제값 설정
	 */
	public final void setVal(float calcVal){
		val = calcVal;
	}

	/**
	 * Spindle의 내용을 계산한 실제값
	 */
	public final float getVal(){
	   return val;
	}


	/**
	 * Spindle에 보여줄 값을 설정
	 */
	public final void setSpinValue(float spinValue) {
        value = spinValue;

        operateChar();

        repaint();
    }

	/**
	 * pixel로 받아와서 현재 unitMode에 따라 spindle에 보여줄 value를 계산하는 루틴
	 */
	public final void setSpinValueForUnitMode(int spinValue) {
		if (unitMode == 0) {
			this.value = (float)this.pixelToMm(spinValue);

		} else if (unitMode == 1) {
			this.value = (float)pixelToMm(spinValue)/10F;

		} else if (unitMode == 2) {
			this.value = (float)spinValue;
		}

        operateChar();

        repaint();
    }

	/**
	 * 현재 선택된 단위에 따라 spindle에 보여지는 값을 pixel로 바꾸어주는 루틴
	 */
	public int pixelValueForUnitMode() {
		int returnPixelValue = 0;

		if (this.unitMode == 0) {
			returnPixelValue = this.mmToPixel(this.value);

		} else if (unitMode == 1) {
			returnPixelValue = this.cmToPixel(value);

		} else if (unitMode == 2) {
			returnPixelValue = (int)value;
		}

		return returnPixelValue;
	}

	static final double mmPerPixel				= 0.3528;
	static final double pixelPerMM				= 2.8346;

	/** pixel을 받아 mm단위(int)로 계산한다.*/
	public static int pixelToMm(int pixel) {
		return roundValue((double)pixel * mmPerPixel);
	}

	/** mm 를 받아 pixel단위로 계산한다.*/
	public static int mmToPixel(float mm) {
		//return roundValue(mm * Pages.pageWidth / A4Width);
		// 2000/9/18 원건 수정
		return roundValue(mm * pixelPerMM);
	}

	public static int cmToPixel(float cm){
		//return roundValue(cm * 10.0F * (float)Pages.pageWidth/(float)A4Width);
		// 2000/9/18 원건 수정
		return roundValue((double)cm * 10.0D * pixelPerMM);
	}



	/** 소수 첫째 자리에서 반올림하는 함수 */
	private static int roundValue(double value) {
		int intValue;
		intValue = (int)value;
		value = (double)(value-intValue);
		if(value >= 0.5) {  // 정수부분만을 뺀 값(소수부분의 값)이 0.5이상이면
			intValue++;     // 1 증가
		}
		return intValue;    // 1증가된 값을 리턴
	}


	/**
	 * 현재 Spindle에 보여지는 값을 반환
	 */
    public final float getSpinValue() {
		//공백일 경우를 신경쓰지 않는 이유는 만약 공백일 경우라면 이미
		//위의 NumberformatException에서 Exception처리를 다 하였을 것이다.
		return value;
    }

	/**
	 * 단위를 보여줄 것인지의 여부 결정
	 */
	public final void setUnitEnabled(boolean isUnitEnabled) {
		isUnit = isUnitEnabled;
	}

	/**
	 * 단위 설정
	 */
	public void setUnitText(String string) {
		unitText = string;
		spinTextField.setText(spinTextField.getText() + unitText);
	}

    /**
	 * 단위 반환
	 */
	public String getUnitText() {
		return unitText;
	}


	/**
	 * intMode인지 상태 설정
	 */
	public final void setIntMode(boolean isIntMode) {
		isInt = isIntMode;
	}

	/**
	 * 현재 intMode에 대한 설정된 내용을 반환
	 */
	public final boolean isIntMode() {
		return isInt;
	}


	/**
	 * Spindle의 TextField에 내용을 설정
	 */
	public void setTextInSpinTextField(String text) {
		spinTextField.setText(text);
	}

	/**
	 * Spindle의 TextField의 내용을 반환
	 */
	public JTextField getSpinTextField(){
		return spinTextField;
	}



}
