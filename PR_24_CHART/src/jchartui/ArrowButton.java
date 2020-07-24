package jchartui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicArrowButton;

public class ArrowButton extends BasicArrowButton implements ActionListener, MouseListener, SwingConstants
{
	private Timer timer;
	public static final int ARROW_UP = 1;
	public static final int ARROW_DOWN = 5;
	public ArrowButton(int i)
	{
		super(i);
		timer = new Timer(500,this);
		addMouseListener(this);

    }


    public void actionPerformed(ActionEvent actionevent)
    {
		timer.setDelay(100);
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
		timer.stop();
    }

    public void mousePressed(MouseEvent mouseevent)
    {
		timer.start();
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
    }

    public final Timer getTimer()
    {
        return timer;
    }

/*	public void requestFocus()
	{
	}
*/
	public boolean isFocusTraversable()
	{
		return false;
	}

}
