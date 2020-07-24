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
import javax.swing.*;

public class StockCodeSearchJDialog extends JDialog {

  private StockCode stockCode;

  public StockCodeSearchJDialog( final JFrame owner, final String title,
				  final String stockFindCgiText,
				  final Dimension size ) {

    super( owner, title, true );

    setSize( size );

    initUI( stockFindCgiText, size );

  }

  private void initUI( final String stockFindCgiText, final Dimension size ) {

    final Container con = getContentPane();

    final StockCodeSearchJPanel stockCodeSearchPane =
		new StockCodeSearchJPanel( this, stockFindCgiText, size );

    con.setLayout( new BorderLayout() );

    final JScrollPane scrollPane = new JScrollPane( stockCodeSearchPane );

    scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );

    con.add( scrollPane, BorderLayout.CENTER );

    setResizable( false );

  }

  public void setStockCode( final StockCode stockCode ) {

    this.stockCode = stockCode;

  }

  public StockCode getStockCode() {

    return stockCode;

  }

}