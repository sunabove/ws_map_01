//package com.techdigm.chart.editor;
package jchartui;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.Icon;

import javax.swing.JToggleButton;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Frame;
import java.awt.Color;
import java.awt.TexturePaint;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class JTiledButton extends JToggleButton {

	  ImageIcon nonTiledImageIcon = null;
      ImageIcon orgNonTiledImageIcon = null;
      Image processedTileImage = null;
      boolean neverShown = true;

	  public static Color firstTileColor = Color.black;
	  public static Color secondTileColor = Color.white;

      public JTiledButton() {
         addMouseListener( new MouseAdapter() {
               public void mouseClicked(MouseEvent e ) {
//                   lastActionEventTime = System.currentTimeMillis();
//                   FillEffectEditor.this.selectedTiledToggleButton = JTiledButton.this;
                   JTiledButton.this.paint(JTiledButton.this.getGraphics());
               }
            }
         );
      }

      public void setIcon(Icon icon) {
         if( nonTiledImageIcon == null ) {
            // nonTiledImageIcon = (ImageIcon) icon;
            if( orgNonTiledImageIcon == null ) {
              orgNonTiledImageIcon = (ImageIcon) icon;
              neverShown = true;
            }
         }
         if( icon == null ) {
            nonTiledImageIcon = null;
            neverShown = true;
         }
         if( icon != null ) {
           super.setIcon( icon );
         }
      }

      public void setNonTiledImageIcon(ImageIcon icon) {
         this.nonTiledImageIcon = icon;
      }

      public void paint(Graphics g) {

         // 넌 타일드 이미지 아이콘을 변경할 경우.
         if( nonTiledImageIcon == null ) {
           if( orgNonTiledImageIcon != null ) {
             Frame f = new Frame();
             f.addNotify();
             int ow = orgNonTiledImageIcon.getImage().getWidth(f),
                 oh = orgNonTiledImageIcon.getImage().getHeight(f);
             BufferedImage image = craeteTexturePaintedTiledImage(ow, oh, orgNonTiledImageIcon );
             processedTileImage = image;
             this.nonTiledImageIcon = orgNonTiledImageIcon;
           } else {
             super.paint(g);
             return;
           }
         }
         // 끝. 넌 타일드 이미지 아이콘 변경.

         int w = super.getWidth(), h = super.getHeight();
         // Utility.message(this, "tw = " + w + ", th = " + h);

         if( w > 0 && h > 0 ) {
         } else {
           super.paint(g);
           return;
         }

         // 타일드 이미지 아이콘 세팅.
         if( this.neverShown ) {
           BufferedImage tiledImage = craeteTexturePaintedTiledImage(w, h, nonTiledImageIcon );
           super.setIcon( new ImageIcon( tiledImage ) );
           this.neverShown = false;
         }
         // 끝. 타일드 이미지 아이콘 세팅.

//         if( FillEffectPatternTab.selectedButton != null && FillEffectPatternTab.selectedButton == this ) {
//           FillEffectPatternTab.selectedButton.setIcon(new ImageIcon(this.processedTileImage));
//           FillEffectEditor.this.showSelectedFillEffect();
//         }

         super.paint(g);
      }


	public static BufferedImage getImageChangedColor(BufferedImage image, Color firstColor, Color secondColor) {

		int w = image.getWidth(), h = image.getHeight();

		BufferedImage processedImage = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );

		Graphics2D g2 = (Graphics2D) processedImage.getGraphics();

		int srcPixel, dstPixel,
		whitePixel = Color.white.getRGB(), blackPixel = Color.black.getRGB(),
		firstColorPixel = firstColor.getRGB(),
		secondColorPixel = secondColor.getRGB() ;

		for(int y = 0; y < h; y ++ ) {
			for(int x = 0; x < w; x ++ ) {
				srcPixel = image.getRGB(x, y);
				if( srcPixel == whitePixel ) {
				    dstPixel = firstColorPixel;
				} else {
				    dstPixel = secondColorPixel;
				}
				processedImage.setRGB(x, y, dstPixel);
			}
		}

		return processedImage;
	}

  BufferedImage craeteTexturePaintedTiledImage(int w, int h, ImageIcon imageIcon) {
     // 기준 버퍼드 이미지 생성
     Image refImage = imageIcon.getImage();
     Frame f = new Frame();
     f.addNotify();
     int rw = refImage.getWidth(f), rh = refImage.getHeight(f);
     BufferedImage refBImage = new BufferedImage( rw, rh, BufferedImage.TYPE_INT_RGB );
     Graphics2D g2 = (Graphics2D) refBImage.getGraphics();
     g2.drawImage( refImage, 0, 0, f);
     // 끝. 기준 버퍼드 이미지 생성

     BufferedImage processedImage = this.getImageChangedColor( refBImage,
                                     this.firstTileColor, this.secondTileColor );

     // 타일드 버퍼드 이미지 생성
     BufferedImage tiledImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB );
     Rectangle2D tr = new Rectangle2D.Double(0, 0, rw, rh);
     TexturePaint tp = new TexturePaint( processedImage, tr );
     Rectangle2D rect = new Rectangle2D.Double(0, 0, w, h);
     g2 = (Graphics2D) tiledImage.getGraphics();
     g2.setPaint( tp );
     g2.fill( rect );
     // 끝. 타일드 버퍼드 이미지 생성

     return tiledImage ;
  }
}