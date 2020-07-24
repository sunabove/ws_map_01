/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import javax.swing.border.*;

import com.jwordart.model.wordart.WordArtMappingTypeAndStyle;
import com.jwordart.ui.resource.Resource_WordArt;
import com.ynhenc.comm.util.WinUtil;

public class WordArtGallery extends JDialog {
	public static WordArtGallery wordArtGallery = new WordArtGallery();

	// Data.
	WordArtMappingTypeAndStyle wordArtMappingTypeAndStyle;
	boolean showed = false;
	Component currSelComp, tempSelComp;
	int currSelType = -1, tempSelType = -1;
	private boolean neverShown = true;

	// GUI Components.
	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel2 = new JPanel();
	JButton veryfy = new JButton();
	FlowLayout flowLayout1 = new FlowLayout();
	JButton cancel = new JButton();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel galleryPanel = new JPanel();
	JPanel jPanel5 = new JPanel();
	JLabel wordArtGalleryLabel = new JLabel();

	class GalleryToggleButton extends JToggleButton {
		public GalleryToggleButton() {
			super.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					WordArtGallery.this.mouseDoubleClicked(e);
				}
			});
		}
	}

	JToggleButton gallery30 = new GalleryToggleButton();
	JToggleButton gallery25 = new GalleryToggleButton();
	JToggleButton gallery24 = new GalleryToggleButton();
	JToggleButton gallery23 = new GalleryToggleButton();
	JToggleButton gallery22 = new GalleryToggleButton();
	JToggleButton gallery21 = new GalleryToggleButton();
	JToggleButton gallery29 = new GalleryToggleButton();
	JToggleButton gallery20 = new GalleryToggleButton();
	JToggleButton gallery19 = new GalleryToggleButton();
	JToggleButton gallery18 = new GalleryToggleButton();
	JToggleButton gallery17 = new GalleryToggleButton();
	JToggleButton gallery16 = new GalleryToggleButton();
	JToggleButton gallery28 = new GalleryToggleButton();
	JToggleButton gallery15 = new GalleryToggleButton();
	JToggleButton gallery14 = new GalleryToggleButton();
	JToggleButton gallery13 = new GalleryToggleButton();
	JToggleButton gallery12 = new GalleryToggleButton();
	JToggleButton gallery11 = new GalleryToggleButton();
	JToggleButton gallery27 = new GalleryToggleButton();
	JToggleButton gallery10 = new GalleryToggleButton();
	JToggleButton gallery09 = new GalleryToggleButton();
	JToggleButton gallery08 = new GalleryToggleButton();
	JToggleButton gallery07 = new GalleryToggleButton();
	JToggleButton gallery06 = new GalleryToggleButton();
	JToggleButton gallery26 = new GalleryToggleButton();
	JToggleButton gallery05 = new GalleryToggleButton();
	JToggleButton gallery04 = new GalleryToggleButton();
	JToggleButton gallery03 = new GalleryToggleButton();
	JToggleButton gallery02 = new GalleryToggleButton();
	JToggleButton gallery01 = new GalleryToggleButton();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	GridLayout gridLayout1 = new GridLayout();
	FlowLayout flowLayout2 = new FlowLayout();
	Border border1;

	// Construct the frame
	public WordArtGallery() {
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.jbInit();
			// Code by sbmoon.
			currSelComp = tempSelComp = gallery01;
			currSelType = tempSelType = -1;
			// End of code by sbmoon.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		border1 = BorderFactory.createEmptyBorder(2, 2, 2, 2);
		contentPane.setLayout(borderLayout1);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(431, 330));
		this.addComponentListener(new java.awt.event.ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent e) {
				WordArtGallery.this.this_componentShown(e);
			}
		});
		this.setTitle("WordArt Gallery");
		this.setModal(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				WordArtGallery.this.this_windowClosing(e);
			}
		});
		jPanel2.setPreferredSize(new Dimension(10, 35));
		jPanel2.setLayout(flowLayout1);
		veryfy.setPreferredSize(new Dimension(75, 24));
		veryfy.setText("확인");
		veryfy.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.veryfy_actionPerformed(e);
			}
		});
		cancel.setPreferredSize(new Dimension(75, 24));
		cancel.setText("취소");
		cancel.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.cancel_actionPerformed(e);
			}
		});
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		jPanel3.setPreferredSize(new Dimension(9, 10));
		galleryPanel.setLayout(gridLayout1);
		jPanel5.setMinimumSize(new Dimension(156, 17));
		jPanel5.setPreferredSize(new Dimension(10, 30));
		jPanel5.setLayout(flowLayout2);
		wordArtGalleryLabel.setForeground(Color.black);
		wordArtGalleryLabel.setAlignmentX((float) 0.5);
		wordArtGalleryLabel.setPreferredSize(new Dimension(156, 20));
		wordArtGalleryLabel.setDisplayedMnemonic('W');
		wordArtGalleryLabel.setText("ordArt 유형 선택(W):");
		gallery30.setPreferredSize(new Dimension(63, 42));
		gallery30.setBackground(Color.white);
		gallery30.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery30.setRequestFocusEnabled(false);
		gallery30.addComponentListener(new java.awt.event.ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent e) {
				WordArtGallery.this.gallery30_componentShown(e);
			}
		});
		gallery30.setIcon(Resource_WordArt.getIcon("wordart", "gallery_30.gif"));
		gallery30.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery30_actionPerformed(e);
			}
		});
		gallery25.setPreferredSize(new Dimension(63, 42));
		gallery25.setBackground(Color.white);
		gallery25.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery25.setRequestFocusEnabled(false);
		gallery25.setIcon(Resource_WordArt.getIcon("wordart", "gallery_25.gif"));
		gallery25.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery25_actionPerformed(e);
			}
		});
		gallery24.setPreferredSize(new Dimension(63, 42));
		gallery24.setBackground(Color.white);
		gallery24.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery24.setRequestFocusEnabled(false);
		gallery24.setIcon(Resource_WordArt.getIcon("wordart", "gallery_24.gif"));
		gallery24.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery24_actionPerformed(e);
			}
		});
		gallery23.setPreferredSize(new Dimension(63, 42));
		gallery23.setBackground(Color.white);
		gallery23.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery23.setRequestFocusEnabled(false);
		gallery23.setIcon(Resource_WordArt.getIcon("wordart", "gallery_23.gif"));
		gallery23.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery23_actionPerformed(e);
			}
		});
		gallery22.setPreferredSize(new Dimension(63, 42));
		gallery22.setBackground(Color.white);
		gallery22.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery22.setRequestFocusEnabled(false);
		gallery22.setIcon(Resource_WordArt.getIcon("wordart", "gallery_22.gif"));
		gallery22.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery22_actionPerformed(e);
			}
		});
		gallery21.setPreferredSize(new Dimension(63, 42));
		gallery21.setBackground(Color.white);
		gallery21.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery21.setRequestFocusEnabled(false);
		gallery21.setIcon(Resource_WordArt.getIcon("wordart", "gallery_21.gif"));
		gallery21.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery21_actionPerformed(e);
			}
		});
		gallery29.setPreferredSize(new Dimension(63, 42));
		gallery29.setBackground(Color.white);
		gallery29.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery29.setRequestFocusEnabled(false);
		gallery29.setIcon(Resource_WordArt.getIcon("wordart", "gallery_29.gif"));
		gallery29.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery29_actionPerformed(e);
			}
		});
		gallery20.setPreferredSize(new Dimension(63, 42));
		gallery20.setBackground(Color.white);
		gallery20.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery20.setRequestFocusEnabled(false);
		gallery20.setIcon(Resource_WordArt.getIcon("wordart", "gallery_20.gif"));
		gallery20.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery20_actionPerformed(e);
			}
		});
		gallery19.setPreferredSize(new Dimension(63, 42));
		gallery19.setBackground(Color.white);
		gallery19.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery19.setRequestFocusEnabled(false);
		gallery19.setIcon(Resource_WordArt.getIcon("wordart", "gallery_19.gif"));
		gallery19.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery19_actionPerformed(e);
			}
		});
		gallery18.setPreferredSize(new Dimension(61, 42));
		gallery18.setBackground(Color.white);
		gallery18.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery18.setRequestFocusEnabled(false);
		gallery18.setIcon(Resource_WordArt.getIcon("wordart", "gallery_18.gif"));
		gallery18.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery18_actionPerformed(e);
			}
		});
		gallery17.setPreferredSize(new Dimension(63, 42));
		gallery17.setBackground(Color.white);
		gallery17.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery17.setRequestFocusEnabled(false);
		gallery17.setIcon(Resource_WordArt.getIcon("wordart", "gallery_17.gif"));
		gallery17.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery17_actionPerformed(e);
			}
		});
		gallery16.setPreferredSize(new Dimension(63, 42));
		gallery16.setBackground(Color.white);
		gallery16.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery16.setRequestFocusEnabled(false);
		gallery16.setIcon(Resource_WordArt.getIcon("wordart", "gallery_16.gif"));
		gallery16.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery16_actionPerformed(e);
			}
		});
		gallery28.setPreferredSize(new Dimension(63, 42));
		gallery28.setBackground(Color.white);
		gallery28.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery28.setRequestFocusEnabled(false);
		gallery28.setIcon(Resource_WordArt.getIcon("wordart", "gallery_28.gif"));
		gallery28.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery28_actionPerformed(e);
			}
		});
		gallery15.setPreferredSize(new Dimension(63, 42));
		gallery15.setBackground(Color.white);
		gallery15.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery15.setRequestFocusEnabled(false);
		gallery15.setIcon(Resource_WordArt.getIcon("wordart", "gallery_15.gif"));
		gallery15.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery15_actionPerformed(e);
			}
		});
		gallery14.setPreferredSize(new Dimension(63, 42));
		gallery14.setBackground(Color.white);
		gallery14.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery14.setRequestFocusEnabled(false);
		gallery14.setIcon(Resource_WordArt.getIcon("wordart", "gallery_14.gif"));
		gallery14.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery14_actionPerformed(e);
			}
		});
		gallery13.setPreferredSize(new Dimension(63, 42));
		gallery13.setBackground(Color.white);
		gallery13.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery13.setRequestFocusEnabled(false);
		gallery13.setIcon(Resource_WordArt.getIcon("wordart", "gallery_13.gif"));
		gallery13.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery13_actionPerformed(e);
			}
		});
		gallery12.setPreferredSize(new Dimension(63, 42));
		gallery12.setBackground(Color.white);
		gallery12.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery12.setRequestFocusEnabled(false);
		gallery12.setIcon(Resource_WordArt.getIcon("wordart", "gallery_12.gif"));
		gallery12.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery12_actionPerformed(e);
			}
		});
		gallery11.setPreferredSize(new Dimension(63, 42));
		gallery11.setBackground(Color.white);
		gallery11.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery11.setRequestFocusEnabled(false);
		gallery11.setIcon(Resource_WordArt.getIcon("wordart", "gallery_11.gif"));
		gallery11.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery11_actionPerformed(e);
			}
		});
		gallery27.setPreferredSize(new Dimension(63, 42));
		gallery27.setBackground(Color.white);
		gallery27.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery27.setRequestFocusEnabled(false);
		gallery27.setIcon(Resource_WordArt.getIcon("wordart", "gallery_27.gif"));
		gallery27.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery27_actionPerformed(e);
			}
		});
		gallery10.setPreferredSize(new Dimension(63, 42));
		gallery10.setBackground(Color.white);
		gallery10.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery10.setRequestFocusEnabled(false);
		gallery10.setIcon(Resource_WordArt.getIcon("wordart", "gallery_10.gif"));
		gallery10.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery10_actionPerformed(e);
			}
		});
		gallery09.setPreferredSize(new Dimension(63, 42));
		gallery09.setBackground(Color.white);
		gallery09.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery09.setRequestFocusEnabled(false);
		gallery09.setIcon(Resource_WordArt.getIcon("wordart", "gallery_09.gif"));
		gallery09.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery09_actionPerformed(e);
			}
		});
		gallery08.setPreferredSize(new Dimension(63, 42));
		gallery08.setBackground(Color.white);
		gallery08.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery08.setRequestFocusEnabled(false);
		gallery08.setIcon(Resource_WordArt.getIcon("wordart", "gallery_08.gif"));
		gallery08.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery08_actionPerformed(e);
			}
		});
		gallery07.setPreferredSize(new Dimension(63, 42));
		gallery07.setBackground(Color.white);
		gallery07.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery07.setRequestFocusEnabled(false);
		gallery07.setIcon(Resource_WordArt.getIcon("wordart", "gallery_07.gif"));
		gallery07.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery07_actionPerformed(e);
			}
		});
		gallery06.setPreferredSize(new Dimension(63, 42));
		gallery06.setBackground(Color.white);
		gallery06.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery06.setRequestFocusEnabled(false);
		gallery06.setIcon(Resource_WordArt.getIcon("wordart", "gallery_06.gif"));
		gallery06.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery06_actionPerformed(e);
			}
		});
		gallery26.setPreferredSize(new Dimension(63, 42));
		gallery26.setBackground(Color.white);
		gallery26.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery26.setRequestFocusEnabled(false);
		gallery26.setIcon(Resource_WordArt.getIcon("wordart", "gallery_26.gif"));
		gallery26.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery26_actionPerformed(e);
			}
		});
		gallery05.setPreferredSize(new Dimension(63, 42));
		gallery05.setBackground(Color.white);
		gallery05.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery05.setRequestFocusEnabled(false);
		gallery05.setIcon(Resource_WordArt.getIcon("wordart", "gallery_05.gif"));
		gallery05.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery05_actionPerformed(e);
			}
		});
		gallery04.setPreferredSize(new Dimension(63, 42));
		gallery04.setBackground(Color.white);
		gallery04.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery04.setRequestFocusEnabled(false);
		gallery04.setIcon(Resource_WordArt.getIcon("wordart", "gallery_04.gif"));
		gallery04.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery04_actionPerformed(e);
			}
		});
		gallery03.setPreferredSize(new Dimension(63, 42));
		gallery03.setBackground(Color.white);
		gallery03.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery03.setRequestFocusEnabled(false);
		gallery03.setIcon(Resource_WordArt.getIcon("wordart", "gallery_03.gif"));
		gallery03.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery03_actionPerformed(e);
			}
		});
		gallery02.setPreferredSize(new Dimension(63, 42));
		gallery02.setBackground(Color.white);
		gallery02.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery02.setRequestFocusEnabled(false);
		gallery02.setIcon(Resource_WordArt.getIcon("wordart", "gallery_02.gif"));
		gallery02.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery02_actionPerformed(e);
			}
		});
		gallery01.setPreferredSize(new Dimension(63, 42));
		gallery01.setBackground(Color.white);
		gallery01.setBorder(BorderFactory.createLoweredBevelBorder());
		gallery01.setRequestFocusEnabled(false);
		gallery01.setIcon(Resource_WordArt.getIcon("wordart", "gallery_01.gif"));
		gallery01.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtGallery.this.gallery01_actionPerformed(e);
			}
		});
		jLabel2.setText("   ");
		jLabel3.setForeground(Color.black);
		jLabel3.setPreferredSize(new Dimension(20, 20));
		jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabel3.setHorizontalTextPosition(SwingConstants.RIGHT);
		jLabel3.setText("W");
		gridLayout1.setRows(5);
		gridLayout1.setHgap(2);
		gridLayout1.setVgap(2);
		flowLayout2.setAlignment(FlowLayout.LEFT);
		flowLayout2.setHgap(0);
		galleryPanel.setAlignmentX((float) 3.0);
		galleryPanel.setAlignmentY((float) 3.0);
		galleryPanel.setBorder(border1);
		galleryPanel.setPreferredSize(new Dimension(350, 160));
		contentPane.add(jPanel2, BorderLayout.SOUTH);
		jPanel2.add(veryfy, null);
		jPanel2.add(cancel, null);
		jPanel2.add(jLabel2, null);
		contentPane.add(jPanel1, BorderLayout.EAST);
		contentPane.add(jPanel3, BorderLayout.WEST);
		contentPane.add(galleryPanel, BorderLayout.CENTER);
		galleryPanel.add(gallery01, null);
		galleryPanel.add(gallery02, null);
		galleryPanel.add(gallery03, null);
		galleryPanel.add(gallery04, null);
		galleryPanel.add(gallery05, null);
		galleryPanel.add(gallery26, null);
		galleryPanel.add(gallery06, null);
		galleryPanel.add(gallery07, null);
		galleryPanel.add(gallery08, null);
		galleryPanel.add(gallery09, null);
		galleryPanel.add(gallery10, null);
		galleryPanel.add(gallery27, null);
		galleryPanel.add(gallery11, null);
		galleryPanel.add(gallery12, null);
		galleryPanel.add(gallery13, null);
		galleryPanel.add(gallery14, null);
		galleryPanel.add(gallery15, null);
		galleryPanel.add(gallery28, null);
		galleryPanel.add(gallery16, null);
		galleryPanel.add(gallery17, null);
		galleryPanel.add(gallery18, null);
		galleryPanel.add(gallery19, null);
		galleryPanel.add(gallery20, null);
		galleryPanel.add(gallery29, null);
		galleryPanel.add(gallery21, null);
		galleryPanel.add(gallery22, null);
		galleryPanel.add(gallery23, null);
		galleryPanel.add(gallery24, null);
		galleryPanel.add(gallery25, null);
		galleryPanel.add(gallery30, null);
		contentPane.add(jPanel5, BorderLayout.NORTH);
		jPanel5.add(jLabel3, null);
		jPanel5.add(wordArtGalleryLabel, null);
	}

	// Overridden so we can exit when window is closed
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			// System.exit(0);
		}
	}

	void gallery30_actionPerformed(ActionEvent e) {
		this.selected(e, 30);
	}

	void gallery25_actionPerformed(ActionEvent e) {
		this.selected(e, 25);
	}

	void gallery24_actionPerformed(ActionEvent e) {
		this.selected(e, 24);
	}

	void gallery23_actionPerformed(ActionEvent e) {
		this.selected(e, 23);
	}

	void gallery22_actionPerformed(ActionEvent e) {
		this.selected(e, 22);
	}

	void gallery21_actionPerformed(ActionEvent e) {
		this.selected(e, 21);
	}

	void gallery29_actionPerformed(ActionEvent e) {
		this.selected(e, 29);
	}

	void gallery20_actionPerformed(ActionEvent e) {
		this.selected(e, 20);
	}

	void gallery19_actionPerformed(ActionEvent e) {
		this.selected(e, 19);
	}

	void gallery18_actionPerformed(ActionEvent e) {
		this.selected(e, 18);
	}

	void gallery17_actionPerformed(ActionEvent e) {
		this.selected(e, 17);
	}

	void gallery16_actionPerformed(ActionEvent e) {
		this.selected(e, 16);
	}

	void gallery28_actionPerformed(ActionEvent e) {
		this.selected(e, 28);
	}

	void gallery15_actionPerformed(ActionEvent e) {
		this.selected(e, 15);
	}

	void gallery14_actionPerformed(ActionEvent e) {
		this.selected(e, 14);
	}

	void gallery13_actionPerformed(ActionEvent e) {
		this.selected(e, 13);
	}

	void gallery12_actionPerformed(ActionEvent e) {
		this.selected(e, 12);
	}

	void gallery11_actionPerformed(ActionEvent e) {
		this.selected(e, 11);
	}

	void gallery27_actionPerformed(ActionEvent e) {
		this.selected(e, 27);
	}

	void gallery10_actionPerformed(ActionEvent e) {
		this.selected(e, 10);
	}

	void gallery09_actionPerformed(ActionEvent e) {
		this.selected(e, 9);
	}

	void gallery08_actionPerformed(ActionEvent e) {
		this.selected(e, 8);
	}

	void gallery07_actionPerformed(ActionEvent e) {
		this.selected(e, 7);
	}

	void gallery06_actionPerformed(ActionEvent e) {
		this.selected(e, 6);
	}

	void gallery26_actionPerformed(ActionEvent e) {
		this.selected(e, 26);
	}

	void gallery05_actionPerformed(ActionEvent e) {
		this.selected(e, 5);
	}

	void gallery04_actionPerformed(ActionEvent e) {
		this.selected(e, 4);
	}

	void gallery03_actionPerformed(ActionEvent e) {
		this.selected(e, 3);
	}

	void gallery02_actionPerformed(ActionEvent e) {
		this.selected(e, 2);
	}

	void gallery01_actionPerformed(ActionEvent e) {
		this.selected(e, 1);
	}

	void veryfy_actionPerformed(ActionEvent e) {
		if (this.tempSelType < 0) {
			return;
		}

		this.currSelComp = this.tempSelComp;
		this.currSelType = this.tempSelType;

		this.setVisible(false);
		synchronized (this) {
			this.notify();
		}
	}

	void cancel_actionPerformed(ActionEvent e) {
		this.wordArtMappingTypeAndStyle = null;
		this.tempSelComp = this.currSelComp;
		this.tempSelType = this.currSelType;

		this.setVisible(false);
		synchronized (this) {
			this.notify();
		}
	}

	public void setAppendMode(boolean b) {
		if (b) {
			this.wordArtGalleryLabel.setText("ordArt 유형 선택(W)");
		} else {
			this.wordArtGalleryLabel.setText("ordArt 유형 바꾸기(W)");
		}
	}

	public void selected(ActionEvent e, int type) {

		if (this.currSelType < 0) {
			return; // 초기화가 되지 않으면, 마우스를 클릭하여도 작동하지 않게 한다.
		}

		this.setWordArtMappingType(type);


		Component selComp = (Component) e.getSource();
		this.tempSelComp = selComp;
		this.tempSelType = type;
		if (galleryPanel.getGraphics() == null) {

			return;
		}
		galleryPanel.paint(galleryPanel.getGraphics());
		Graphics2D g2 = (Graphics2D) galleryPanel.getGraphics();
		int x = selComp.getX() - 1, y = selComp.getY() - 1, w = selComp.getWidth() + 2, h = selComp.getHeight() + 2;
		Rectangle2D rect1 = new Rectangle2D.Float();
		rect1.setRect(x, y, w, h);
		g2.draw(rect1);
		rect1.setRect(x - 1, y - 1, w + 2, h + 2);
		Stroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 1, 1 }, 0);
		g2.setStroke(stroke);
		g2.draw(rect1);
	}

	public void setWordArtMappingType(int type) {

		this.wordArtMappingTypeAndStyle = WordArtMappingTypeAndStyle.getWordArtMappingTypeAndStyle(type);
	}

	public static WordArtMappingTypeAndStyle getWordArtMappingTypeAndStyle() {
		if (wordArtGallery == null) {
			wordArtGallery = new WordArtGallery();
		}

		wordArtGallery.setVisible(true);

		return wordArtGallery.wordArtMappingTypeAndStyle;
	}

	void this_windowClosing(WindowEvent e) {
		this.cancel_actionPerformed(null);
	}

	void this_componentShown(ComponentEvent e) {
	}

	@Override
	public void setVisible(boolean b) {

		if (!b) {
			super.setVisible(b);
		} else {

			new Thread() {
				@Override
				public void run() {
					while (!WordArtGallery.this.isVisible()) {

						try {
							sleep(500);
						} catch (Exception e) {

						}
					}
					;
					try {
						sleep(500);
					} catch (Exception e) {

					}
					ActionEvent ae = new java.awt.event.ActionEvent(currSelComp, 0, "");
					if (currSelType < 0) {
						currSelType = 1;
					}
					WordArtGallery.this.selected(ae, currSelType);
				}
			}.start();

			if (neverShown) {
				this.reLocate();
				neverShown = false;
			}

			super.setVisible(true);
		}
	}

	public void reLocate() {
		WinUtil.locateOnTheScreenCenter(this);
	}

	void gallery30_componentShown(ComponentEvent e) {
		new Thread() {
			@Override
			public void run() {
				while (!WordArtGallery.this.isVisible()) {

					try {
						sleep(500);
					} catch (Exception e) {

					}
				}
				;
				try {
					sleep(500);
				} catch (Exception e) {

				}
				ActionEvent ae = new java.awt.event.ActionEvent(currSelComp, 0, "");
				WordArtGallery.this.selected(ae, currSelType);
			}
		}.start();
	}

	void mouseDoubleClicked(MouseEvent e) {
		if (e.getClickCount() >= 2) {
			this.veryfy_actionPerformed(null);
		}
	}
}