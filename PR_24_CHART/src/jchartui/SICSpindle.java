package jchartui;

/*******************************************************************************
 * PROJECT     : JWord
 * CLASS       : SICSpindle
 * DESCRIPT    : 1���� TextField�� 2���� Arrow��ư(��, �Ʒ�)���� �̷���� ������
 *               �⺻������ ����� float������ ��������
 *               ���������� �������� ���� int�� �Ǵ� float���� �� �� �ִ�.
 *
 * UPDATE      : Han, Won-gun(2000/09/28)
 * DESCRIPT    : CustomEvent ���� �߰�
 *
 * LAST UPDATE : Park, Ho-young(2001/02/12)
 * DESCRIPT    : SICSpindle�� ���� Erroró���� Option���� ���� ����� ó���κ� �߰�
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
	//UI����
	//���� �Է��� �Ǵ� ��
	protected JTextField spinTextField;

	//ArrowUp��ư�� ArrowDown��ư�� �ö󰡴� Panel
	protected JPanel buttonPanel    = new JPanel();
	protected ArrowButton arrowUp   = new ArrowButton(ArrowButton.ARROW_UP);
	protected ArrowButton arrowDown = new ArrowButton(ArrowButton.ARROW_DOWN);


	//���� ����
	//������ ������ �������� ����
	protected boolean   isUnit = false;

	public static final String PERCENT = "\uFF05";	//����(%) ǥ��
	public static final String DEGREE  = "\u00B0";	//���� ǥ��
	//Option�� ���� mm, cm, pixel���� �ϳ��� ���ϰ� �� ���̴�.
	//Default�� �������� ������ mm����
	protected String unitText = "\u339C";

	protected char    availableChar; //�Է��� ���� ���ڸ� ��Ÿ��
	protected boolean isFitIn;	     //�Է¹����ʰ��� ���� ������ �ٲ�.
	protected boolean isInt;	     //int ������ �ƴ���..
	protected int     fraction;
	protected float   increment;     //Arrow��ư�� ������ ��� �⺻������ ����, �����ϴ� ��

	protected float   val;           //�� ��쿡 ���� float������ ���Ǿ��� Spindle�� ��
	protected float   value;         //������ Spindle�� �������� ��(int�� �Ǵ� float��)
	protected float   minValue;	     //�ּҰ�
	protected float   maxValue;      //�ִ밪

	//listener�� ������ Vector
	//������ Ŀ����Event�� ����� ����� �����Ұ�
	protected Vector listeners = new Vector();

	//���� Spindle���� ������ ����
	//protected byte unitMode;
	public byte unitMode;

	//�ּҰ����� �۰ų� �ִ밪���� ū ��� Error Message�� ��� �ٶ� ����ϱ� ����
	//�ּ�, �ִ밪�� ��� ��쿡�� �׻� ���� ���� �ƴϹǷ� �� �� ���� �������� ���Կ�
	//���� �� ���� String���� ���� ����Ѵ�.
	String minString = "0";
	String maxString = "558";


	//====================================================================
	//�����ڴ� ũ�� ������ �ִ� ���� ���� ����� �� ���� ������.
	//������ �ִ� ���� unitMode�� ���� �����Ǵ� ���� �޶��� ��������
	//������ ���� ���� �ƿ� unitMode�� �����ص� ������� �����̴�.
	//====================================================================
	/**
	 * ������ �ִ� ���
	 * ���� Spindle���� ����� ������ ���ڷ� �޴� ������
	 * �ּҰ��� �ִ밪�� �⺻���� �����Ǿ��� ���̴�.
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
	 * ������ �ִ� ���
	 * �ּҰ�, �ִ밪, ���� Spindle���� ����� ������ ���ڷ� ������ ������
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
	 * ������ �ִ� ���
	 * �ʿ��� ��� ���� ���Ƿ� ���ؼ� ����� ���ֵ��� ���� ���ڷ� �޴� ������
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
	 * ������ ���� ���
	 * �ּҰ�, �ִ밪, ������, intMode������ ���ڷ� �޴� ������
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
	//���� ��
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
	 * �ʱ�ȭ
	 */
	public void init() {
		setSize(55, 23);
		setLayout(null);

		//===========================================================
		//��� SICSpindle�� �����ÿ� mm�� �������� �����Ǿ����Ƿ�
		//�ִ밪�� �ּҰ��� mm�� �������� �����Ǿ�����. �׷��Ƿ�
		//mm������ ���� �����ϰ�� cm�� pixel�� ���� ������ �°�
		//�ּҰ��� �ִ밪�� �����  �� �־�� �Ѵ�.
		//===========================================================

		//System.out.println("SICSpindle���� unitMode: " + this.unitMode);

		//==============================================================
		//������ ���� ���
		//==============================================================
		if (this.isUnit) {
			//mm������ ��
			if (unitMode == 0) {
				unitText = "\u339C";

				//mm������ ���� �������·� ��Ÿ����
				//�⺻ ������(�Ǵ� ���Ұ�)�� 1(mm)�̴�.
				//isInt = true;
				increment = 1F;

			//cm������ ��
			} else if (unitMode == 1) {
				unitText = "\u339D";

				//=======================================================
				//SICSpindle���� �⺻������ float���� ��Ÿ�� ����
				//�Ҽ� ��° �ڸ������� ��Ÿ����� �Ѵ�. �׷��Ƿ�,
				//�Ҽ� ��° �ڸ����ϴ� ������ ������.
				//=======================================================
				minValue = getFloatValueToSecondPoint(this.minValue/10.0F);
				maxValue = getFloatValueToSecondPoint(this.maxValue/10.0F);

				//cm������ ���� ������ �Ǽ� ���·� ��Ÿ����
				//�⺻ ������(�Ǵ� ���Ұ�)�� 0.1(cm)�̴�.
				isInt = false;
				increment = 0.1F;

			//pixel������ ��
			} else if (unitMode == 2) {
				unitText = "pixel";

				//1mm�� 2.8346pixel�̴�.
				minValue = getFloatValueToSecondPoint(minValue * 2.8346F);
				maxValue = getFloatValueToSecondPoint(maxValue * 2.8346F);

				//pixel������ ���� �������·� ��Ÿ����
				//�⺻ ������(�Ǵ� ���Ұ�)�� 1(pixel)�̴�.
				isInt = true;
				increment = 1.0F;
			}


		//==================================================================
		//������ ���� ���
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
	 * Spindle���� �� ������Ʈ���� ũ��� ��ġ ����
	 */
	public void setComponentBounds() {
		//============================================================
		// Spindle�� ũ�⿡ ���� TextField�� ArrowButton���� ��ġ��
		// �������� �����Ǿ� ���ϴ� ũ���� Spindle�� ������ �� �ִ�.
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
	 * �� ������Ʈ�� �ʿ��� �̺�Ʈ ����ϱ�
	 */
	private void addListeners() {

		//TextField ����
		spinTextField.addActionListener(this);
		spinTextField.addKeyListener(this);
		spinTextField.addFocusListener(this);

		//ArrowButton�� ����
		arrowUp.addActionListener(this);
		arrowUp.getTimer().addActionListener(this);

		arrowDown.addActionListener(this);
		arrowDown.getTimer().addActionListener(this);
	}

	/**
	 * Spindle�� ũ�⸦ �������� �ʴ´ٸ�
	 * �ּһ������ (90, 22)�� ũ�Ⱑ ��������.
	 */
	public Dimension getMinimumSize() {
		return new Dimension(90, 22);
	}

	/**
	 * ���� ������δ� �ּ� ����� ��ȯ
	 */
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}


	/**
	 * CustomEvent�� �����ϱ� ���� �ʿ��� �޼ҵ�
	 * SICSpindle�κ��� �뺸�� �ް��� �ϴ� ��� ��ü��,
	 * SICComponentValueListener�� �����Ҽ� �ִ�.
	 */
	public void addSICComponentValueListener(SICComponentValueListener listener){
		if (!listeners.contains(listener))
			listeners.addElement(listener);
	}

	public void removeSICComponentValueListener(SICComponentValueListener listener){
		listeners.removeElement(listener);
	}

	/**
	 * SICComponentValueEvent�� �߻����� �Ʒ� �޼ҵ带 ȣ�������ν� �뺸�� �ϰ� �ȴ�.
	 */
	public void notifyListeners(int state){
		// ���������� �����ϱ� ���� listener���� ���͸� ����
		Vector copyofLIsteners = (Vector)(listeners.clone());

		// ���� ���� ĸ��ȭ�ϴ� SICComponentValueEvent ����
		SICComponentValueEvent event = new SICComponentValueEvent(this, state);

		// �� listener�� �˸���.
		Enumeration enumIt = copyofLIsteners.elements();
		while (enumIt.hasMoreElements()){
			SICComponentValueListener listener = (SICComponentValueListener)enumIt.nextElement();
			listener.sicComponentValueChanged(event);
		}
	}

	//+++++++++++++++++++++     FocusEvent ó��    ++++++++++++++++++++++++++//
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	public void focusGained(FocusEvent e) {}
	public void focusLost(FocusEvent e) {

		// 2000/9/28 ����
		// value�� �ٲ������ event �߻�
		notifyListeners(SICComponentValueEvent.SICCOMPONENT_VALUE_CHANGED);
	}


	//+++++++++++++++++++++++     KeyEvent ó��    ++++++++++++++++++++++++++//
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	public void keyTyped(KeyEvent keyevent) {}
    public void keyPressed(KeyEvent keyevent) {}
    public void keyReleased(KeyEvent e) {
		availableChar = e.getKeyChar();

		// keyReleased()������ value�� �����ؾ� �Ѵ�.
		// operateValue()�� ���� �� ȣ���Ѵ�.operateValueWongun()�� ���� �Ѵ�.
		operateValue(true);
	}


	//+++++++++++++++++++     ActionEvent ó��    +++++++++++++++++++++++++++//
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	public void actionPerformed(ActionEvent e) {
		//System.out.println("getSpinValue()): " + getSpinValue());
		//System.out.println("Double.isNaN(getSpinValue()): " + Double.isNaN(getSpinValue()));

		//ArrowUp Button�� ���� ó��
		if(isEnabled() && (e.getSource() == arrowUp || e.getSource() == arrowUp.getTimer())) {
			//=========================================================
			// NaN()�̶�� ���� ũ�Ⱑ ���� 0������ 0�� ���� ���� �
			// Ư�� �Ǽ� ������ ����� ��Ÿ���� ���� ���ȴ�.
			// SICSpindle�� ��� �ƹ� ���� �Էµ��� ���� ����,
			// ��, TextField�� ������ ���¿��� ArrowUp ��ư�� ������ ��
			// isNan()�� true�� �ȴ�.
			//==========================================================
			if(Double.isNaN(getSpinValue())) {
				value = minValue;

				if(isInt) {
					spinTextField.setText(String.valueOf((new Float(value)).intValue()));

				} else {
					spinTextField.setText(String.valueOf(value));
				}

			//Spindle�� ���� �ִ밪���� ���� ���
			} else if(getSpinValue() < maxValue) {
				//System.out.println("value: " + value);
				//System.out.println("increment: " + this.increment);
				// 2000/9/29 ���� ����
				// key�� �Է��Ͽ����� �ּҰ����� ���ٸ� �ּҰ�-1�� �����Ͽ�
				// ó������ arrow��ư�� ������ �ּҰ��� �����ְ� ��½�Ų��.
				if (getSpinValue() < minValue) {
					value = minValue-1;
				}

				//ArrowUp ��ư�� ������ ���� increment��ŭ ������ ���� ���Ѵ�.
				//value = trimValue(value + increment);

				value = getFloatValueToSecondPoint(value + increment);

				//System.out.println("Up��ư�� ������ �� �� value: " + value);

				//�� ���� ��ȿ�� ������ Ȯ���Ѵ�.
				operateChar();
				operateValue(false);

				//Spindle�� ���� �ִ밪���� ũ�ٸ�
				if(value > maxValue) {
					//Spindle���� �ִ밪�� �����ش�.
				    value = maxValue;
				    operateChar();
				}

			//Spindle�� ���� �ִ밪���� ū ���� �Էµ� ���
			} else if (getSpinValue() > maxValue) {
				//Spindle�� ���� �ִ밪���� ū ���̸� �ּҰ�-1�� �����Ͽ�
				//ó������ ArrowUp ��ư�� ���� �� �ּҰ��� �����ְ� ��½�Ų��.
				value = minValue-1;
			}

		//ArrowDown Button�� ���� ó��
		} else if(isEnabled() && (e.getSource() == arrowDown || e.getSource() == arrowDown.getTimer())) {
			//���� Spindle�� �ƹ� ���� �ԷµǾ� ���� ���� ����
			//��, ������ ���¿��� ArrowDown ��ư�� ������ ���
			if(Double.isNaN(getSpinValue())) {
			    value = minValue;
				if(isInt)
			        spinTextField.setText(String.valueOf((new Double(value)).intValue()));
			    else
			        spinTextField.setText(String.valueOf(value));

			//Spindle�� ���� �ּҰ����� Ŭ ���
			} else if(getSpinValue() > minValue) {
				// 2000/9/29 ���� ����
				// key�� �Է��Ͽ����� �ִ밪���� ���ٸ� �ִ밪 + 1�� �����Ͽ�
				// ó������ Arrow��ư�� ������ �ִ밪�� �����ְ� �ϰ��Ѵ�.
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

			//Spindle�� ���� �ּҰ����� ���� ���� �Էµ� ���
			} else if (getSpinValue() < minValue) {
				value = maxValue + 1;
			}

		//TextField�� ���� ó��
		} else if(e.getSource() == spinTextField && isFitIn) {
	        operateValue(false);
	        operateChar();
		}

		// 2000/9/28 ����
		// value�� �ٲ������ event �߻�
		notifyListeners(SICComponentValueEvent.SICCOMPONENT_VALUE_CHANGED);

	    fireSpinValueChangedEvent();

	    spinTextField.requestFocus();
		spinTextField.selectAll();
	}


	/** �Էµ� ���� �޾� �װ��� ��ȿ���� �˻��Ͽ� ������ setting�Ѵ�.
	 *	isKeyReleased�� true�̸� key�� ������ ���·� ������ �Ѿ���ϴ��� max, min���� �������� �ʴ´�.
	 */
	public boolean operateValue(boolean isKeyReleased) {
		try {
			//���ڸ� �Ľ��Ͽ� �ϴ� double���·� ������ ����� �Ѵ�.
			String parse = spinTextField.getText().trim();

			char c[] = new char[parse.length()];
			int j = 0;
			String parsedouble = "";
			String unit;	//������ ������ �ӽú���
			for(int i=0; i< (parse.length()) ; i++) {
				if(Character.isDigit(parse.charAt(i)) || parse.charAt(i) =='.' || parse.charAt(i)=='-') {	//�����̸�,�Ҽ���,����üũ
					c[j] = parse.charAt(i);			//c[]�� �ϳ���������Ű�鼭 ����
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
				// 2000/9/29 ���� ����
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

			// NumberFormatException�� �Ͼ�� �켱 �ּҰ�-1���� �����Ѵ�.
			value = this.minValue-1;

			//System.out.println("NumberFormatException: " + exc);


			return false;

		} catch(Exception ex) {

			//System.out.println("Exception");


			return false;
		}
	}


	//�Էµ� ���ڰ� ������ �������� �˻��� ���� setting�Ѵ�. - ���� '-'���� �������ʿ����.��??
	void operateChar() {
		if(Double.isNaN(value) && !spinTextField.getText().equals("-")) {
			this.spinTextField.setText("");
			return;
		}
		if(isInt) {
		    if(!spinTextField.getText().equals("-")) {
				if(isUnit) {//������ ������
					spinTextField.setText(String.valueOf((new Float(getSpinValue())).intValue())+unitText);
					return;
				} else {//������ ������
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
	 * Spindle�� ���� ��ȿ�� �������� �ƴ��� �Ǻ�
	 */
	public boolean isDecimal() {
		try {
			//����� ������ ���ڸ� �Էµ� ����̴�.
			//Spindle�� �ִ� ���� �о�´�.
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

			//System.out.println("������ ���� �Էµ� �����ΰ�? : " + this.isUnit);
			//System.out.println("Exception�� �߻��ϴ� ��� Spindle�� ��  : " + this.getSpinValue());

			//Spindle�� �ƹ����� ���� ���
			if (spinTextField.getText().length() == 0) {
				//System.out.println("Spindle�� �ƹ� ���� ���� ���(NaN�� true�� �ȴ�): " + this.getSpinValue());

				JOptionPane.showMessageDialog(this.getParent(),"You must input values","JWord", JOptionPane.WARNING_MESSAGE);
				this.spinTextField.requestFocus();
				this.spinTextField.setText("");

				return false;

			//Spindle�� ����, ���� �� ���� ���Ե� ���
			} else {
				//System.out.println("Spindle�� ������ �ƴ� ��� ");
				//System.out.println("Spindle�� ���������� �����ΰ�?: " + string.endsWith(this.unitText));

				//==========================================================
				//Spindle���� ������ ã�Ƽ� ������ ������ ���������� ������
				//Error�� üũ�ϴ� �κ��̴�.
				//==========================================================
				String string = spinTextField.getText().trim();
				int searchEndIndex = string.length()-1;


				//���� ������ pixel�� ���
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
					//Spindle�� ���������� �������
					if (string.endsWith(this.unitText)) {
						searchEndIndex = string.length()-1;
					}
				}


				//�����ձ����� ���� �˻��ؼ� ��ȿ�� ���������� �Ǵ��Ѵ�.
				try {
					char c[] = new char[searchEndIndex];
					int j = 0;
					for (int i = 0; i < searchEndIndex; i++) {
						c[j] = string.charAt(i);
						j++;
					}

					//���� �ձ����� ���� �˻��� ��� ���� ���ڿ��� ���
					//Double���� ���� ���Ѵ�.
					this.val = Float.valueOf(String.valueOf(c)).floatValue();
					//System.out.println("����ϴ� �߿�...." + val);

					if(isFitIn)	{
						//���� ���� �ִ밪���� ũ�ٸ�
						//Spindle���� �ִ밪�� �������� �� ���̴�.
						if (maxValue < val ){
							value = maxValue;

						//���� ���� �ּҰ����� �۴ٸ�
						//Spindle���� �ּҰ��� �������� �� ���̴�.
						} else if (minValue > val){
							value = minValue;

						//���� ���� �ּҰ��� �ִ밪���̿� �ִٸ�
						//�� ���� Spindle�� ������ ���� �ȴ�.
						} else {
							value = val;
						}

					} else {
						value = val;
					}

					return true;

				} catch(NumberFormatException ex) {
					//System.out.println("�������� �ٸ� ���ڰ� �Էµ� ��� ");

					JOptionPane.showMessageDialog(this.getParent(),"Invalid number","JWord", JOptionPane.WARNING_MESSAGE);
					spinTextField.requestFocus();
					spinTextField.setText("");

					return false;
				}
			}
		}
	}

	/**
	 * ��ȿ�� ���ڰ� �Էµ� ��� �� ���� �ּҰ��� �ִ밪 ���̿� �ִ��� �Ǻ�
	 */
	public boolean isEnableValue() {
		if(this.isDecimal()) {
			//�Էµ� ���ڰ� �ּҰ��� �ִ밪 ���̿� ������
	        //if((this.val >= this.minValue) && (this.val <= this.maxValue) ) {
			 if((this.value >= this.minValue) && (this.value <= this.maxValue) ) {
				//true�� ��ȯ�ϰ� ������ �� ���̴�.
				return true;

			//���� �ִ밪���� ũ�ų� �ּҰ����� ���� ���� �ԷµǾ��ٸ�
			//Error �޼����� ����ش�.
			} else {
				String string1 = "Input number between ";
				String string2 = " and ";
				String string3 = ".";

				//�̷��� �ϴ� ������ �ּҰ��� �ִ밪�� ��� ��쿡�� �� ���� �����Ƿ�
				//�ּҰ�, �ִ밪�� ���� Error�� ���������� �����ϱ� ����.
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
	 * �Ҽ� ��° �ڸ����� �����ϴ� �Լ�
	 */
	public  float getFloatValueToSecondPoint(float value) {
		float floatValue = value *100.0F;
		float changedValue = ((int)floatValue)/100.0F;

		//float floatValue = value *10.0F;
		//float changedValue = ((int)floatValue)/10.0F;

		return  changedValue;
	}


	/**
	 * �Ҽ� ù° �ڸ��� �����Ͽ� �Ǽ��� ������ ��Ÿ���� �Լ�
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
		float trimming = d;	//�� ����
		int trimmed = (int)trimming;	//�����θ� trimed�� ����
		float rest = trimming - (float)trimmed;	//�������Ҽ��θ� rest�� ����
		rest = rest*10F;	//�Ҽ��ο� 10�� ���Ͽ� �������� �ڸ���. - 10�� ���� ������ �����Ұ�
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
     * SICSpindle�� Ȱ��ȭ, ��Ȱ��ȭ�� ����
     */
	public final void setEnabled(boolean isEnabled) {
		//======================================================
		//SICSpindle�� �ϳ��� Component�� �̷���� ���� �ƴ϶�
		//�������� Component�� �̷�������Ƿ� SICSpindle��
		//���ԵǴ� ��� Component���� Ȱ��ȭ/��Ȱ��ȭ�����־��
		//SICSpindle��ü�� ������ �ȴ�.
		//======================================================
		super.setEnabled(isEnabled);

		//System.out.println("isEnabled: " + isEnabled);
		spinTextField.setEnabled(isEnabled);
		arrowUp.setEnabled(isEnabled);
		arrowDown.setEnabled(isEnabled);

		//Ȱ��ȭ����(default)
		if(isEnabled) {
			arrowUp.getTimer().addActionListener(this);
			arrowDown.getTimer().addActionListener(this);
			return;

		//��Ȱ��ȭ����
		} else {
			arrowUp.getTimer().removeActionListener(this);
			arrowDown.getTimer().removeActionListener(this);
			return;
		}
	}


	/**
	 * �ּҰ� ����
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
	 * �ּҰ� ��ȯ
	 */
	public final float getMin() {
		return minValue;
	}


	/**
	 * �ִ밪 ����
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
	 * �ִ밪 ��ȯ
	 */
	public final float getMax() {
		return maxValue;
	}


	/**
	 * �������� ����
	 */
	public final void setIncrement(float incre) {
		increment = incre;
	}

	/**
	 * ���� �����Ǿ��� �������� ��ȯ
	 */
	public final float getIncrement() {
		return increment;
	}

	/**
	 * Spindle�� ������ ������ ����
	 */
	public final void setVal(float calcVal){
		val = calcVal;
	}

	/**
	 * Spindle�� ������ ����� ������
	 */
	public final float getVal(){
	   return val;
	}


	/**
	 * Spindle�� ������ ���� ����
	 */
	public final void setSpinValue(float spinValue) {
        value = spinValue;

        operateChar();

        repaint();
    }

	/**
	 * pixel�� �޾ƿͼ� ���� unitMode�� ���� spindle�� ������ value�� ����ϴ� ��ƾ
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
	 * ���� ���õ� ������ ���� spindle�� �������� ���� pixel�� �ٲپ��ִ� ��ƾ
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

	/** pixel�� �޾� mm����(int)�� ����Ѵ�.*/
	public static int pixelToMm(int pixel) {
		return roundValue((double)pixel * mmPerPixel);
	}

	/** mm �� �޾� pixel������ ����Ѵ�.*/
	public static int mmToPixel(float mm) {
		//return roundValue(mm * Pages.pageWidth / A4Width);
		// 2000/9/18 ���� ����
		return roundValue(mm * pixelPerMM);
	}

	public static int cmToPixel(float cm){
		//return roundValue(cm * 10.0F * (float)Pages.pageWidth/(float)A4Width);
		// 2000/9/18 ���� ����
		return roundValue((double)cm * 10.0D * pixelPerMM);
	}



	/** �Ҽ� ù° �ڸ����� �ݿø��ϴ� �Լ� */
	private static int roundValue(double value) {
		int intValue;
		intValue = (int)value;
		value = (double)(value-intValue);
		if(value >= 0.5) {  // �����κи��� �� ��(�Ҽ��κ��� ��)�� 0.5�̻��̸�
			intValue++;     // 1 ����
		}
		return intValue;    // 1������ ���� ����
	}


	/**
	 * ���� Spindle�� �������� ���� ��ȯ
	 */
    public final float getSpinValue() {
		//������ ��츦 �Ű澲�� �ʴ� ������ ���� ������ ����� �̹�
		//���� NumberformatException���� Exceptionó���� �� �Ͽ��� ���̴�.
		return value;
    }

	/**
	 * ������ ������ �������� ���� ����
	 */
	public final void setUnitEnabled(boolean isUnitEnabled) {
		isUnit = isUnitEnabled;
	}

	/**
	 * ���� ����
	 */
	public void setUnitText(String string) {
		unitText = string;
		spinTextField.setText(spinTextField.getText() + unitText);
	}

    /**
	 * ���� ��ȯ
	 */
	public String getUnitText() {
		return unitText;
	}


	/**
	 * intMode���� ���� ����
	 */
	public final void setIntMode(boolean isIntMode) {
		isInt = isIntMode;
	}

	/**
	 * ���� intMode�� ���� ������ ������ ��ȯ
	 */
	public final boolean isIntMode() {
		return isInt;
	}


	/**
	 * Spindle�� TextField�� ������ ����
	 */
	public void setTextInSpinTextField(String text) {
		spinTextField.setText(text);
	}

	/**
	 * Spindle�� TextField�� ������ ��ȯ
	 */
	public JTextField getSpinTextField(){
		return spinTextField;
	}



}
