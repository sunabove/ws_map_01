
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jchartui;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;
import jchart.*;
import jcosmos.*;

public // fill color combobox renderer.
  class FillColorComboBoxRenderer extends JLabel implements ListCellRenderer {
    private ImageIcon icon = null;
    private BufferedImage iconImage;
    private Color fillColor = Color.white;
    private FillEffect fillEffect = null;
    private String nullMessage = "¾øÀ½";

    public FillColorComboBoxRenderer() {
      setOpaque(true);
      setBorder(new javax.swing.border.EmptyBorder (1, 1, 1, 1));
    }

    public Color getFillColor() {
      return this.fillColor;
    }

    public FillEffect getFillEffect() {
      return this.fillEffect;
    }

    private void createIcon() {
       int w = 71, h = 12;
       this.iconImage = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );
       Graphics2D g2 = (Graphics2D) this.iconImage.getGraphics();
       Rectangle2D rect = new Rectangle2D.Double(0, 0, w, h);
       if( this.fillColor != null ) {
         Utility.debug(this, "Setting Fill Color ......" );
         g2.setColor( this.fillColor );
         g2.fill( rect );
       } else if ( this.fillEffect != null ) {
         this.fillEffect.setBounds( w, h );
         Paint gp = this.fillEffect.getGradientPaint();
         TexturePaint tp = this.fillEffect.getTexturePaint();
         if( gp != null ) {
            g2.setPaint( gp );
            Utility.debug(this, "Setting Gradient Paint ......" );
         } else {
            g2.setPaint( tp );
            Utility.debug(this, "Setting Texture Paint ......" );
         }
         g2.fill( rect );
       } else {
         Utility.debug(this, this.nullMessage );
         g2.setColor( Color.white );
         g2.fill( rect );
         g2.setColor( Color.black );
         g2.drawString( this.nullMessage , 5, 10);
       }
       this.icon = new ImageIcon( this.iconImage );
    }

    public void changeColor( Color c ) {
      this.fillColor = c;
      this.fillEffect = null ;
      this.repaint();
    }

    public void changeFillEffect(FillEffect fillEffect) {
      this.fillEffect = fillEffect;
      if( fillEffect != null ) {
         this.fillColor = null;
      }
      this.repaint();
    }

    public Component getListCellRendererComponent( JList list,
           Object value, int index, boolean isSelected, boolean cellHasFocus) {
      String str = value.toString();
      if( str.equals("") ) {
        try {
           this.createIcon();
           this.setIcon( this.icon );
        } catch (Exception e) {
           e.printStackTrace();
        }
        return this;
      }
      if( index == 1 ) {
         this.nullMessage = str;
      }
      Utility.debug(this, str );
      JButton btn = new JButton( str );
      btn.setBorder(new javax.swing.border.EtchedBorder( javax.swing.border.EtchedBorder.LOWERED ) );

      return btn;
    }
  }