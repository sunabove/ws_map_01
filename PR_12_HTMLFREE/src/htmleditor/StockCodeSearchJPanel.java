package htmleditor;

/**
 * Title:        HTML FREE Editor V2.0
 * Description:  HTML FREE EDITOR
 * Copyright:    Copyright (c) 2002
 * Company:      Kuma System Co., Ltd.
 * @author Suhng  ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import jcosmos.*;

public class StockCodeSearchJPanel extends JBorderLayoutPanel {

  private StockCodeSearchJDialog stockCodeSearchJDialog;
  private String stockFindCgiText;

  private final Color bgBrightColor = new Color( 230,230,210 );
  private final Font bigFont = AppRegistry.STOCK_CODE_FIND_WINDOW_BIG_FONT;
  private final Border emptyBorder = BorderFactory.createEmptyBorder();
  private int labelWidth = 100, labelHeight = 30;

  public StockCodeSearchJPanel( final StockCodeSearchJDialog stockCodeSearchJDialog,
				final String stockFindCgiText,
				final Dimension size ) {

    super( 0, 0 );

    this.stockCodeSearchJDialog = stockCodeSearchJDialog;

    this.stockFindCgiText = stockFindCgiText;

    initUI( size );

  }

  private void initUI( final Dimension size ) {

    final JPanel contentPane = this;

    final int leftMargin = 20, rightMargin = 20;

    this.labelWidth = (size.width - leftMargin - rightMargin)/2;

    contentPane.setBorder( BorderFactory.createEmptyBorder( 10, leftMargin, 20, rightMargin ) );

    final Border blackLineBorder = BorderFactory.createLineBorder( Color.black, 1);

    final Color bgBrightColor = this.bgBrightColor;
    final Color bgDarkColor = new Color( 153,153,153);

    contentPane.setBackground( bgBrightColor );

    final JPanel northPane = new JBorderLayoutPanel( 0, 0 );

    final Font bigFont = this.bigFont;
    final Font smallFont = AppRegistry.STOCK_CODE_FIND_WINDOW_SMALL_FONT;

    final JLabel findItemLb = new JLabel( "종목 찾기", Utility.getResourceImageIcon( "search_stock.gif"), JLabel.LEFT );
    final JLabel closeLb = new JLabel( "", JLabel.RIGHT );
    final JLabel itemOrCodeLb = new JLabel( "종목 or 코드", JLabel.CENTER );
    final JTextField codeTf = new JTextField( 10 );
    final JButton findBtn = new JButton( " 찾기 " );

    final JPanel centerSecondRowPane = new JPanel();

    findBtn.setBorder( blackLineBorder );

    codeTf.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	findItemCode( codeTf, centerSecondRowPane );
      }
    });

    findBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	findItemCode( codeTf, centerSecondRowPane );
      }
    });

    findItemLb.setFont( bigFont );
    closeLb.setFont( smallFont );
    itemOrCodeLb.setForeground( Color.white );
    itemOrCodeLb.setFont( bigFont );
    codeTf.setFont( bigFont );
    findBtn.setFont( bigFont );

    northPane.setBackground( bgBrightColor );
    northPane.add( findItemLb, BorderLayout.WEST );
    northPane.add( closeLb, BorderLayout.EAST );

    final JPanel ctrPane = new JBorderLayoutPanel( 1, 1 );

    ctrPane.setBorder( blackLineBorder );

    final JPanel centerFirstRowPane = new JPanel();

    centerFirstRowPane.setLayout( new FlowLayout( FlowLayout.CENTER ) );

    centerFirstRowPane.setBackground( bgDarkColor );

    centerFirstRowPane.add( itemOrCodeLb );
    centerFirstRowPane.add( codeTf );
    centerFirstRowPane.add( findBtn );

    centerSecondRowPane.setBackground( Color.white );

    ctrPane.add( centerFirstRowPane, BorderLayout.NORTH );

    ctrPane.add( centerSecondRowPane, BorderLayout.CENTER );

    contentPane.add( northPane, BorderLayout.NORTH );

    contentPane.add( ctrPane, BorderLayout.CENTER );


  }

  private void findItemCode( final JTextField textField, final JPanel pane ) {

    final String stockCodeToFind = textField.getText();

    if( stockCodeToFind == null || stockCodeToFind.length() < 1 ) {

      return;

    }

    final String stockFindCgiText = this.stockFindCgiText + stockCodeToFind.trim();

    Utility.debug( this, "STOCK CODE CGI = " + stockFindCgiText );

    URL url = null;

    try {

      url = new URL( stockFindCgiText );

    } catch (MalformedURLException e) {

      JOptionPane.showMessageDialog( this, AppRegistry.WRONG_URL_MSG );

      return;

    }

    BufferedReader in = null;

    try {

      in = new BufferedReader( new InputStreamReader( url.openStream() ) );

    } catch (IOException e) {

      JOptionPane.showMessageDialog( this, AppRegistry.SERVER_COMM_FAIL_MSG );

      return;

    }

    final Vector lineList = new Vector();

    String lineText;

    try {

      while( ( lineText = in.readLine() ) != null ) {

	lineList.add( lineText );

      }

    } catch ( IOException e ) {

      Utility.debug( e );

    }

    final int size = lineList.size();

    final String lines [] = new String [ size ];

    System.arraycopy( lineList.toArray(), 0, lines, 0, size );

    addStockCodeList( lines, pane );

  }

  private void addStockCodeList( final String [] stockCodeTexts, final JPanel pane ) {

    final int size = stockCodeTexts.length;

    final StockCode [] stockCodes = new StockCode[ size ];

    int pipeIdx = -1;

    String stockCodeText;

    for( int i = 0; i < size; i ++ ) {

      stockCodeText = stockCodeTexts[ i ];

      pipeIdx = stockCodeText.indexOf( '|' );

      stockCodes[ i ] = new StockCode(
			    stockCodeText.substring( 0, pipeIdx ),
			    stockCodeText.substring( pipeIdx + 1 )
			  );

    }

    addStockCodeList( stockCodes, pane );

  }

  private void addStockCodeList( final StockCode [] stockCodes, final JPanel pane ) {

    pane.removeAll();

    pane.setLayout( new GridLayout( 1, 2 ) );

    final JPanel leftPane = new JPanel();
    final JPanel rightPane = new JPanel();

    leftPane.setBackground( Color.white );
    rightPane.setBackground( Color.white );

    pane.add( leftPane );
    pane.add( rightPane );

    final int len = stockCodes.length;

    for( int i = 0; i < len; i ++ ) {

      ( i%2 == 0 ? leftPane : rightPane).add( getStockCodeComponent( stockCodes[ i ] ) );

    }

    pane.setPreferredSize(
			    new Dimension(
				  labelWidth*2,
				  labelHeight*( len/2 + 3 ) )
			  );

//    pane.repaint( 1 );

    this.stockCodeSearchJDialog.validate();

  }

  private Component getStockCodeComponent( final StockCode stockCode ) {

    final JLabel btn = new JLabel( "  " + (char)(17) + "  " + stockCode.code + " " + stockCode.name, JLabel.LEFT );

    final Color normalBgColor = Color.white, normalFgColor = Color.blue ;

    btn.addMouseListener(new MouseAdapter() {

      public void mouseClicked( MouseEvent e ) {

	stockCodeSearchJDialog.setStockCode( stockCode );
	stockCodeSearchJDialog.setVisible( false );

      }

      public void mouseExited(MouseEvent e) {

	btn.setForeground( normalFgColor );

	btn.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR) );

      }

      public void mouseEntered(MouseEvent e) {

	btn.setForeground( Color.red );

	btn.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );

      }

    });

    btn.setAlignmentX( JButton.LEFT_ALIGNMENT );

    btn.setBackground( normalBgColor );

    btn.setForeground( normalFgColor );

    btn.setPreferredSize( new Dimension( labelWidth , labelHeight ) );

    btn.setFont( bigFont );

    btn.setBorder( emptyBorder );

    return btn;

  }

}