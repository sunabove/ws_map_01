package gmlviewer.gis.comp;

import java.awt.*;
import javax.swing.*;
import gmlviewer.gis.util.*;

public class RealProgressBar extends JProgressBar {

  private Component refComp;
  private JWindow progressBarWindow;

  public RealProgressBar(Component refComp, Dimension size ) {
    this( refComp, size, 0, 100 );
  }

  public RealProgressBar(Component refComp, Dimension size, int min, int max) {

    super( min, max );

    setValue( min );

    setStringPainted( true );

    this.refComp = refComp;

    progressBarWindow = new JWindow( WinUtil.getJFrame( refComp ) );

    progressBarWindow.setSize( size );

    Container con = progressBarWindow.getContentPane();

    con.setLayout( new BorderLayout() );

    con.add( this, BorderLayout.CENTER );

    progressBarWindow.setVisible( false );

  }


  public void setValue(int n) {

      super.setValue( n );

      //multics.sbmoon.Util.debug(this, "PROGRESS = " + n );

      if( progressBarWindow == null ) {

	   return;

      }

      if( progressBarWindow.isShowing() ) {

	if( n == getMaximum() || n == getMinimum() ) {

	  progressBarWindow.toBack();

	  progressBarWindow.setVisible( false );

	}

	// Do nothing!

      } else if( getMinimum() < n && n < getMaximum() ) {

	Component refComp = this.refComp;

	Point cp = refComp.getLocationOnScreen();
	Dimension cSize = refComp.getSize();

	Dimension wSize =  progressBarWindow.getSize();

	progressBarWindow.setLocation( cp.x + cSize.width/2 - wSize.width/2,
				       cp.y + cSize.height/2 - wSize.height/2);

	if( ! progressBarWindow.isVisible() ) {

	      progressBarWindow.setVisible( true );

	}

	progressBarWindow.toFront();

      }

      paint( getGraphics() );

    }

}
