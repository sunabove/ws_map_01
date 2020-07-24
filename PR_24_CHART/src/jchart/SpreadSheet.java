
/**
* Title:        java chart project<p>
* Description:  jchart v1.0<p>
* Copyright:    Copyright (c) Suhng ByuhngMunn<p>
* Company:      JCOSMOS DEVELOPMENT<p>
* @author Suhng ByuhngMunn
* @version 1.0
*/
package jchart;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import jcosmos.*;
import java.util.*;
import java.awt.geom.*;
import jchartui.*;

public class SpreadSheet extends JTable implements TableModelListener, MouseListener, MouseMotionListener, KeyListener  {

	private static SpreadSheet currentSpreadSheet;  // current working spread sheet

	private LinkedList charts = new LinkedList();  // charts

	/**
	 * selected chart element when mouse pressed
	 */
	private ChartElement selectedChartElementWhenMousePressed;

	/**
	 * selected chart element when mouse moved
	 */
	private ChartElement selectedChartElementWhenMouseMoved;

	/**
	 * mouse when select chart event when mouse moved
	 */
	private MouseEvent mouseEventWhenSelectChartElementWhenMouseMoved;
	private MouseEvent lastMousePressed;  // last mouse event pressed



	/**
	 * working mode
	 */
	private int workMode = WAIT_MODE;

	private static final int WAIT_MODE = 0, MOVE_MODE = 1, RESIZE_MODE = 2,
							 TITLE_EDIT_MODE = 3, SET_3D_WIRE_FRAME_MODE = 4, FEEDBACK_MODE = 5;  // working modes
	private int workTopology; // work topology of selected chart element to the mouse location

	private Shape xorShape; // xor shape that use doing move selected chart element or resize when mouse dragging

	private Chart copiedChart; // copied chart
	private int pasteNum = 0;  // paste number

	private boolean controlWireFrameOnFrontPlane = false;

	private JFileChooser fileChooser;
	JPopupMenu gridLineFormatPopup = new JPopupMenu();
	JPopupMenu legendFormatPopup = new JPopupMenu();
	JMenuItem cutMenu = new JMenuItem();
	JMenuItem chartWindowMenu2 = new JMenuItem();
	JMenuItem threeDimViewMenu2 = new JMenuItem();
	JMenuItem locationMenu2 = new JMenuItem();
	JMenuItem chartOptionMenu4 = new JMenuItem();
	JMenuItem originalSourceDataMenu4 = new JMenuItem();
	JMenuItem chartTypeMenu4 = new JMenuItem();
	JPopupMenu chartTitleFormatPopup = new JPopupMenu();
	JMenuItem chartExtentStyleMenu = new JMenuItem();
	JMenuItem jMenuItem21 = new JMenuItem();
	JMenuItem legendItemSymbolFormatMenu = new JMenuItem();
	JMenuItem jMenuItem9 = new JMenuItem();
	JPopupMenu graphExtentStylePopup = new JPopupMenu();
	JMenuItem originalDataMenu = new JMenuItem();
	JPopupMenu legendItemFormatPopup = new JPopupMenu();
	JMenuItem axisRemoveMenu = new JMenuItem();
	JMenuItem chartTitleFormat = new JMenuItem();
	JMenuItem removeMenu = new JMenuItem();
	JMenuItem chartTypeMenu = new JMenuItem();
	JMenuItem tendencyLineMenu = new JMenuItem();
	JMenuItem jMenuItem2 = new JMenuItem();
	JMenuItem dataSeriesFormatMenu = new JMenuItem();
	JMenuItem jMenuItem19 = new JMenuItem();
	JPopupMenu dataSeriesFormatPopup = new JPopupMenu();
	JMenuItem legendItemFormatMenu = new JMenuItem();
	JMenuItem legendRemoveMenu = new JMenuItem();
	JMenuItem legendFormatMenu = new JMenuItem();
	JMenuItem removeMenu2 = new JMenuItem();
	JMenuItem tendencyLineMenu2 = new JMenuItem();
	JMenuItem originalSourceDataMenu2 = new JMenuItem();
	JMenuItem chartTitleRemoveMenu = new JMenuItem();
	JMenuItem chartTypeMenu2 = new JMenuItem();
	JMenuItem chartTitleFormatMenu = new JMenuItem();
	JMenuItem dataElementFormatMenu = new JMenuItem();
	JMenuItem graphExtentRemoveMenu = new JMenuItem();
	JMenuItem removeGridLine = new JMenuItem();
	JPopupMenu dataElementFormatPopup = new JPopupMenu();
	JMenuItem chartWindowMenu = new JMenuItem();
	JMenuItem gridLineMenu = new JMenuItem();
	JMenuItem threeDimViewMenu = new JMenuItem();
	JMenuItem locationMenu = new JMenuItem();
	JPopupMenu axisPopup = new JPopupMenu();
	JMenuItem axisFormatMenu = new JMenuItem();
	JPopupMenu legendItemSymbolFormatPopup = new JPopupMenu();
	JMenuItem chartOptionMenu = new JMenuItem();
	JMenuItem originalSourceDataMenu3 = new JMenuItem();
	JMenuItem chartTypeMenu3 = new JMenuItem();
	JMenuItem graphExtentStyleMenu = new JMenuItem();
	JMenuItem macroSetMenu = new JMenuItem();
	JMenuItem bottomMostMenu = new JMenuItem();
	JMenuItem topMostMenu = new JMenuItem();
	JMenuItem chartExtentRemoveMenu = new JMenuItem();
	JMenuItem pasteMenu = new JMenuItem();
	JMenuItem copyMenu = new JMenuItem();
	JPopupMenu dataTableStylePopup = new JPopupMenu();
	JPopupMenu chartExtentStylePopup = new JPopupMenu();
	JMenuItem axisTitleRemoveMenu = new JMenuItem();
	JMenuItem axisTitleFormatMenu = new JMenuItem();
	JPopupMenu axisTitlePopup = new JPopupMenu();// file chooser

	public SpreadSheet(TableModel tm) {

		super( tm );

		super.setCellSelectionEnabled( true );
		super.setColumnSelectionAllowed( true );

		tm.addTableModelListener( this );

		addMouseListener( this );
		addMouseMotionListener( this );
		addKeyListener( this );

		try {
			jbInit();
		} catch (Exception e ) {
			//Utility.debug( e );
		}
	}

	public void open(Component comp) {
		if( fileChooser == null ) {
			fileChooser = new JFileChooser();

			fileChooser.setDialogTitle("Select a data file, please!");
			fileChooser.setMultiSelectionEnabled( false );
			fileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] {".csv" },
																	  "csv (*.csv)") );
		}

		//JFrame frame = Utility.getJFrame( this );

		int option = fileChooser.showOpenDialog( comp );

		if( option == JFileChooser.APPROVE_OPTION ) {
			File file = fileChooser.getSelectedFile();

			if( ! file.exists() ) { return; }  // checks if file exists

			open( file );
		}
	}

	public void open(File file) {

		SpreadSheetTableModel model = (SpreadSheetTableModel)super.getModel();

		try {

			model.open( file );

		} catch (IOException e) {
			Utility.debug( e );
		}

	}

	public void save(Component comp) {
		if( fileChooser == null ) {
			fileChooser = new JFileChooser();

			fileChooser.setDialogTitle("Select or enter data file, please!");
			fileChooser.setMultiSelectionEnabled( false );
			fileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] {".csv" },
																	  "csv (*.csv)") );
		}

		//JFrame frame = Utility.getJFrame( this );

		int option = fileChooser.showSaveDialog( comp );

		if( option == JFileChooser.APPROVE_OPTION ) {
			File file = fileChooser.getSelectedFile();

			save( file );
		}
	}

	public void saveAs(Component comp) {
		if( fileChooser == null ) {
			fileChooser = new JFileChooser();

			fileChooser.setDialogTitle("Select or enter data file, please!");
			fileChooser.setMultiSelectionEnabled( false );
			fileChooser.addChoosableFileFilter( new SimpleFileFilter( new String [] {".csv" },
																	  "csv (*.csv)") );
		}

		//JFrame frame = Utility.getJFrame( this );

		int option = fileChooser.showSaveDialog( comp );

		if( option == JFileChooser.APPROVE_OPTION ) {
			File file = fileChooser.getSelectedFile();

			if( file.exists() ) {    // Overwrite confirmation
				option = JOptionPane.showConfirmDialog( comp, "" + file + " exists. Overwrite it? ", "Overwrite?", JOptionPane.YES_NO_OPTION );
				if( option == JOptionPane.OK_OPTION ) {
				} else {
					return;
				}
			}

			save( file );
		}
	}

	public void save(File file) {
		SpreadSheetTableModel model = (SpreadSheetTableModel)super.getModel();
		model.save( file );
	}

	public void tableChanged(TableModelEvent e) {
		super.tableChanged( e );
//		Utility.debug(this, "First row = " + e.getFirstRow() );
//		Utility.debug(this, "Last row = " + e.getLastRow() );
//		Utility.debug(this, "Column = " + e.getColumn() );
		Utility.debug(this, "selRow = " +  super.getSelectedRow());
		Utility.debug(this, "selColumn = " + super.getSelectedColumn() );
	}

	public Chart addChart() {
		Chart chart = null;

		int [] rows = getSelectedRows();
		int [] cols = getSelectedColumns();

		if( ! isValidChartData( rows, cols ) ) {
			Utility.warn( "íƮ �����͸� �����ϰ� �����Ͽ� �ּ���!", this );
			return chart;
		}

		Rectangle rect = nextChartArea(); // chart area

		ChartType type = ChartType.getChartType( "verticalbar", 0);

		ChartOption option = new ChartOption();

		chart = new Chart( this, new ChartData( this, rows, cols ), rect, type, option );

		charts.addLast( chart );

		setSelectedChartElement( chart, null );

		return chart;

	}

	public Rectangle nextChartArea() {
		Point loc = nextChartLocation();
		return new Rectangle( loc.x, loc.y, 450, 288 );
	}

	public Point nextChartLocation() {
		Chart lastChart = null;

		if( charts.size() > 0 ) {
			lastChart = (Chart) charts.getLast();
		}

		Dimension size = getSize();

		if( lastChart != null ) {
			Point point = lastChart.getLocation();
			return new Point( point.x + (int)(size.width/10), point.y + (int)(size.height/10) );
		} else {
			return new Point( (int)(size.width/5), (int)(size.height/3) );
		}
	}

	public void removeChart(Chart chart) {
		if( charts.contains( chart ) ) {
			charts.remove( chart );
		}
	}

	/**
	 * checks if valid chart data elements composition
	 */
	public boolean isValidChartData(int [] row, int [] col) {
		int rl = row.length; // row length
		int cl = col.length; // col length

		if( rl < 2 || cl < 2 ) {
			return false;
		}

		for( int i = 1; i < rl; i ++ ) {
			for( int j = 1; j < cl; j ++ ) {
				try {
					new Double( getDataAt( row[i], col[j] ) ).doubleValue();   // all data except headers are number
					//Utility.message(this, "" + d );
				} catch( Exception e) {
					return false;
				}
			}
		}

		return true;
	}

	public String getDataAt(int row, int col) {

		return (String)( getValueAt( row, col ) );

	}


	public void drawCharts(Graphics2D g2) {

		Iterator it = charts.iterator();

		int mode = this.workMode;

		final ChartElement sce = this.selectedChartElementWhenMousePressed;

		while( it.hasNext() ) {

			Chart chart = (Chart) it.next();

			if( mode == SET_3D_WIRE_FRAME_MODE ) {

			  if( sce != null && sce.getChart() == chart ) {

			    continue;

			  }

			}

			chart.paint( g2 );

		}

		drawSelectedChartElement( g2 );

	}

	/**
	 * ���õ� ChartElement�� �׸���.
	 */
	public void drawSelectedChartElement(Graphics2D g2) {

		ChartElement sce = this.selectedChartElementWhenMousePressed; // selected chart element

		if( sce != null ) {

			sce.paintSelected( g2 );

			//Utility.debug(this, "" + sce.getClass() + ", " + sce);

		}

	}

	/**
	 * Mouse Event�� ���� ChartElement�� ���ÿ��θ� �����Ѵ�.
	 */
	public void setSelectedChartElement(ChartElement chartElement, MouseEvent e) {

		if( e == null || e.getID() == e.MOUSE_PRESSED ) {

			setSelectedChartElementWhenMousePressed( chartElement );

		} else if( e != null) {

			setSelectedChartElementWhenMouseMoved( chartElement, e);

		}

	}

	/**
	 * MouseEvent�� move������ tooltip text�� �׸��� ���� chartElement�� �����Ѵ�.
	 */
	private void setSelectedChartElementWhenMouseMoved(ChartElement chartElement, MouseEvent e) {
		if( chartElement instanceof ParentToolTip ) {
			chartElement = chartElement.getParent();
		}

		this.selectedChartElementWhenMouseMoved = chartElement;

		this.mouseEventWhenSelectChartElementWhenMouseMoved = e;
	}

	/**
	 * MouseEvent�� pressed������ mouse�� ��ġ�� �ش��ϴ� chartElement�� �����Ѵ�.
	 */
	public void setSelectedChartElementWhenMousePressed(ChartElement chartElement ) {

		if( this.selectedChartElementWhenMousePressed != null ) {
			this.selectedChartElementWhenMousePressed.setSelected( false );
		}

		this.selectedChartElementWhenMousePressed = chartElement;

		if( chartElement != null ) {
			chartElement.setSelected( true );
		}

		this.selectedChartElementWhenMouseMoved = null;
		this.mouseEventWhenSelectChartElementWhenMouseMoved = null;
	}

	public ChartElement getSelectedChartElement() {
		return selectedChartElementWhenMousePressed;
	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		super.paint( g );

		drawCharts( g2 );

		drawToolTip( g2 );

	}

	private MouseEvent lastToolTipMouseEvent;
	private ChartElement lastToolTipElement;

	/**
	 *    checks the toolTipDrawability
	 *    it returns true if chart is topmost or it is not overlapped with any chart
	 */

	private boolean isToolTipDrawable(Chart chart) {

		if( isAnyPopupMenuShowingNow() ) {
			return false;
		}

		Iterator chartsIt = this.charts.iterator();  // charts iterator

		boolean overlapped = false;

		Chart refChart;
		Rectangle refArea;
		Rectangle area = chart.getArea();

		while( chartsIt.hasNext() ) {
			refChart = (Chart) chartsIt.next();

			if( refChart != chart ) {
				refArea = refChart.getArea();
				if( refArea.intersects( area ) ) {
					overlapped = true;
					break;
				}

				if( refArea.contains( area ) || area.contains( refArea ) ) {
					overlapped = true;
					break;
				}
			}

		}

		if( overlapped ) {
			if( this.charts.getLast() == chart ) {
				return true;
			} else {
				return false;
			}
		}

		return true;
	}

	private Chart toolTipChart;
	private Thread toolTipThread;

	private void drawToolTip(Chart chart, final MouseEvent e) {

		if( ! isToolTipDrawable( chart ) ) {
			return;
		}

		if( lastToolTipMouseEvent == null ) {
			lastToolTipMouseEvent = e;
			return;
		}

		toolTipChart = chart;

		long now = e.getWhen();
		final long then = lastToolTipMouseEvent.getWhen();

		long elapsedTime = Math.abs( now - then );

		if( elapsedTime < 2000 && toolTipThread == null && ! isAnyPopupMenuShowingNow() ) {   // less than two seconds
			toolTipThread = new Thread() {
				public void run() {

				   while( Math.abs( System.currentTimeMillis() - then ) < 2000 ) {
				     try {
				       sleep( 100 );
				     } catch (Exception e) {
				     }
				   }

				   if( ! isAnyPopupMenuShowingNow() ) {
				     drawToolTipRunnable( toolTipChart, e );
				   }
				   toolTipThread = null;
				}
			  };

			toolTipThread.start();
		} else {
			lastToolTipMouseEvent = e;
		}

	}

	private Chart toolTipChartDrawn;

	private void drawToolTipRunnable(Chart chart, MouseEvent e) {

		ChartElement toolTipElement = this.selectedChartElementWhenMouseMoved;

		if( toolTipElement instanceof ParentToolTip ) {
			toolTipElement = toolTipElement.getParent();
		}

		if( toolTipChartDrawn != null && toolTipElement == lastToolTipElement ) {
			return;
		}

		Graphics2D g2 = (Graphics2D) getGraphics();

		chart.paint( g2 );

		ChartElement selectedChartElement = this.selectedChartElementWhenMousePressed;

		if( selectedChartElement != null && chart.contains( selectedChartElement ) ) {
			drawSelectedChartElement( g2 );
		}

		this.drawToolTip( g2 );

		lastToolTipMouseEvent = e;
		lastToolTipElement = toolTipElement;
		toolTipChartDrawn = chart;

	}

	private void drawToolTip(Graphics2D g2) {

		//Utility.debug(this, "Drawing ToolTips ....." );

		ChartElement element = this.selectedChartElementWhenMouseMoved;

		if( element == null ) {
			return;
		}

		MouseEvent e = this.mouseEventWhenSelectChartElementWhenMouseMoved;

		if( e == null ) {
			return;
		}

		Chart chart = element.getChart();

		Rectangle cArea = chart.getArea();

		int cx = cArea.x, cy = cArea.y, cw = cArea.width, ch = cArea.height;

		int x = e.getX(), y = e.getY() + 20;

		g2.setFont( FontManager.getDefaultFont() );

		FontMetrics fm = g2.getFontMetrics();

		String toolTip = element.toString();

		if( toolTip == null || toolTip.length() < 1 ) {
			return;
		}

		int w = fm.stringWidth( toolTip ) + 6;  // tooltip rectangle width
		int h = fm.getHeight(); // tooltip rectangle height

		if( x < cx ) {
			x = cx;
		}

		if( x + w > cx + cw ) {
			x = cx + cw - w;
		}

		if( y < cy ) {
			y = cy;
		}

		if( y + h > cy + ch ) {
			y = cy + ch - h;
		}

		ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

		Rectangle textArea = new Rectangle( x, y, w, h );

		style.paint( g2, textArea );

		ChartElement.drawString( null, toolTip, g2, g2.getFont(), textArea );

	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}


	/**
	 * mouse event�� pressed�� ��쿡 ���õ� chartElement�� �̺�Ʈ ó���� �Ѵ�.
	 */
	public void mousePressed(MouseEvent e) {

		currentSpreadSheet = this;  // set current working spread sheet as this object

		// ���õ� ChartElement�� sce�� �Ҵ��Ѵ�.
		ChartElement sce = this.selectedChartElementWhenMousePressed;

		boolean processed = false; // checks if mouse event processed

		if( sce != null ) {

			int top = sce.getTopology( e );
			// cursor�� ��ġ�� ���õ� ChartElement�� marks�� ��ġ�� �ִ��� check�Ѵ�.
			// ���� cursor�� ��ġ�� ���õ� ChartElement�� �ۿ� ��ġ�Ѵٸ� ���õ� ChartElement��
			// null �� �����Ѵ�.

			if( sce instanceof ChildSelectable && SwingUtilities.isRightMouseButton( e ) ) {

			  // Do nothing!

			} else if( top == sce.OUT_OF_AREA ) {

				setSelectedChartElement( null, null );

			} else if( top == sce.INSIDE_AREA ) {

				// Do nothing!
				// ���ο� ���콺�� ���� ���� ����ؼ� ���õ� ChartElement�� �����Ѵ�.

			} else {

				// marks�� cursor�� ��ġ�Ͽ��� ��� processed�� true�� �����
				// �ؿ� code���� ����ؼ� ó���Ѵ�.
				processed = true;

			}

		}


		// dont process mouse event if it has been processed before this line
		if( ! processed ) {

			if( SwingUtilities.isRightMouseButton( e ) ) {

			  // Do nothing!

			} else if( sce instanceof ParentSelectable && sce.processMouseEvent( e, false ) ) {

				// Do nothing!

			} else {

				int len = charts.size();

				for( int i = len - 1 ; i > -1 ; i -- ) {

					Chart chart = (Chart) charts.get( i );

					if( chart.processMouseEvent( e, true ) ) {

						charts.remove(chart);
						charts.addLast( chart );

						break;

					}

				}

			}

		}

		sce = this.selectedChartElementWhenMousePressed; // reset selected chart element

		if( sce != null ) {

			int top = sce.getTopology( e );   // topology to the selected chart element of mouse location

			if( top == sce.INSIDE_AREA ) {

				if( sce instanceof Title ) {

					workMode = TITLE_EDIT_MODE;

				} else {

					workMode = MOVE_MODE;

				}

			} else if( top > sce.OUT_OF_AREA && top < sce.INSIDE_AREA ) {

				ChartType ct = sce.getChartType();

				if( sce instanceof GraphExtent && ( ct instanceof ChartType3D ) && ( ! ( ct instanceof CircularChartType ) ) ) {

					workMode = SET_3D_WIRE_FRAME_MODE;

					Chart chart = sce.getChart();

					PictureExtent pe = chart.getPictureExtent();

					Rectangle peRect = pe.getArea();

					Rectangle [] frontSelMarks = pe.getSelectedMarksMiddleOnBoundary( peRect );

					int x = e.getX();
					int y = e.getY();

					this.controlWireFrameOnFrontPlane = false;

					for( int i = 0, len = frontSelMarks.length ; i < len; i ++ ) {

					  if( frontSelMarks[ i ] != null && frontSelMarks[ i ].contains( x, y ) ) {

					    this.controlWireFrameOnFrontPlane = true;

					    break;

					  }

					}

				} else if( sce instanceof UnResizable ) {

				    // ���õ� ChartElement�� DataElementView�� ���
				    // ���� cursor�� ��ġ�� topology�� FEEDBACK_AREA �� ���� �ƴѰ��� ������ ������ �� �ִ�.
				    // FEEDBACK_AREA�� ��� workMode�� FEEDBACK_MODE�� �ٲٰ�
				    // �ƴ� ���� �ƹ��ϵ� ���� �ʴ´�.

				    if( ct instanceof FeedbackChartType && sce instanceof DataElementView ) {

					    if ( top == ct.getFeedbackTopology() ) {

						    workMode = FEEDBACK_MODE;

						workTopology = top;

					} else {

					  workMode = WAIT_MODE;

					}

					} else {

					workMode = MOVE_MODE;
				    }

				} else {

					workMode = RESIZE_MODE;

					workTopology = top;

				}

			} else {

				workMode = WAIT_MODE;

			}

		}

		lastMousePressed = e;

		Utility.debug( this, "SCE = " + sce );

		repaint();

		showPopupMenu( e );

	}

	/**
	 * show popup menus when right mouse pressed
	 */

	public void showPopupMenu(MouseEvent e) {

		int mod = e.getModifiers();

		if( mod != 4 || (this.selectedChartElementWhenMousePressed == null ) ) {
			// when no selected chart element and right button is not pressed
			return;
		}

		Utility.debug(this, "button 2 ");

		ChartElement sce = this.selectedChartElementWhenMousePressed;

		int x = e.getX(), y = e.getY();   // mouse coordinate

		if( sce instanceof DataSeriesView ) {
			dataSeriesFormatPopup.show(this, x, y);
		} else if ( sce instanceof DataElementView ) {
			dataElementFormatPopup.show(this, x, y);
		} else if( sce instanceof Axis ) {
			axisPopup.show(this, x, y);
		} else if ( sce instanceof GraphExtent ) {
			graphExtentStylePopup.show(this, x, y);
		} else if ( sce instanceof Chart ) {
			chartExtentStylePopup.show(this, x, y);
		} else if ( sce instanceof GridLineGroup ) {
			gridLineFormatPopup.show(this, x, y);
		} else if ( sce instanceof Legend ) {
			legendFormatPopup.show(this, x, y);
		} else if ( sce instanceof LegendItem ) {
			legendItemFormatPopup.show(this, x, y);
		} else if ( sce instanceof LegendItemSymbol ) {
			legendItemSymbolFormatPopup.show(this, x, y);
		} else if ( sce instanceof ChartTitle ) {
			chartTitleFormatPopup.show(this, x, y);
		} else if  (sce instanceof XAxisTitle || sce instanceof YAxisTitle) {
			axisTitlePopup.show(this, x, y);
		}

	}



	/**
	 * mouse event�� released�� ��쿡 ���õ� chartElement�� �̺�Ʈ ó���� �Ѵ�.
	 */
	public void mouseReleased(MouseEvent e) {

		ChartElement sce = this.selectedChartElementWhenMousePressed;   // selected chart element

		// ���õ� ChartElement�� ���ų� ������ �� ���� ChartElement�� ��� �ƹ��� �̺�Ʈ ó���� ���� �ʴ´�.
		if( sce == null || sce instanceof UnMovable ) {
			// do nothing
			return;
		}

		int mode = this.workMode;


		if( mode == MOVE_MODE || mode == TITLE_EDIT_MODE ) {   // In case of move mode

			// Draws dragged temporary chart element shape

			int x = e.getX(), y = e.getY();   // current x and y
			int px = lastMousePressed.getX(), py = lastMousePressed.getY();  // previous x and y

			int tx = x - px, ty = y - py;   // ������ ��ǥ

			Point mp = sce.getMovePoint( tx, ty );

			if( mode == TITLE_EDIT_MODE ) {
				if(  Math.abs( tx ) + Math.abs( ty ) > 4 ) {
					sce.moveBy( mp.x, mp.y );
				}
			} else if( tx != 0 && ty != 0 ) {
				sce.moveBy( mp.x, mp.y );
			}


		} else if( mode == RESIZE_MODE ) {  // In case of resize mode

			// Draws dragged temporary chart element shape

			sce.resizeAndMoveBy( lastMousePressed, e, this.workTopology );

		// feedback mode �� �� ���õ� DataElement�� ��Ʈ Ÿ�Կ� ���� feedEvent handling�� �ٸ��� ������
		} else if (mode == FEEDBACK_MODE) {

			Graphics2D g2 = (Graphics2D) getGraphics();

			int cmx = e.getX(), cmy = e.getY();   // current mouse x and y

			int pmx = lastMousePressed.getX(), pmy = lastMousePressed.getY();  // previous mouse x and y

		    sce.getChartType().feedbackEventHandling( g2, (DataElementView) sce, pmx, pmy, cmx, cmy);

		} else if( mode == SET_3D_WIRE_FRAME_MODE ) {  // ������ ���̾� ������ ����

			GraphExtent ge = (GraphExtent) sce;

			Graphics2D g2 = (Graphics2D) getGraphics();

			int mx = e.getX(), my = e.getY();   // current x and y

			int pmx = lastMousePressed.getX(), pmy = lastMousePressed.getY();  // previous x and y

			int tx = mx - pmx, ty = my - pmy;   // ������ ��ǥ

			ge.paintAndSet3DWireFrameMovedBy( g2, tx, ty, this.controlWireFrameOnFrontPlane );

		}

		lastMousePressed = null;

		if( sce != null ) {

			sce.setXorShape( null );

		}

		this.workMode = WAIT_MODE;

		if( sce != null ) {
			int top = sce.getTopology( e );
			setCursor( sce, top );
		} else {
			CursorManager.setCursor(this, CursorManager.def);
		}

		repaint();

		// Utility.debug(this, "mouse released." );
	}

	/**
	 * when mouse moved
	 */
	public void mouseMoved(MouseEvent e) {

		int id = e.getID();

		if( toolTipChartDrawn != null && ! isAnyPopupMenuShowingNow() ) {

			toolTipChartDrawn = null;

		}

		if( id == e.MOUSE_DRAGGED ) {
			// mouse Dragged event�� mouseDragged(MouseEvent)���� ó���Ѵ�.
			return;

		}

		// process mouse event to draw tool tips

//		int len = charts.size();
//
//		for( int i = len -1 ; i > -1 ; i -- ) {
//
//			Chart chart = (Chart) charts.get( i );
//
//			if( chart.processMouseEvent( e, true ) ) {
//
//				drawToolTip( chart, e );
//
//				break;
//
//			}
//
//		}

		// End of processing mouse event to draw tool tips

		// set mouse cursor when mouse moved and not dragged

		ChartElement sce = this.selectedChartElementWhenMousePressed; // selected chart element

		// ���õ� ChartElement�� ���ų� UnMovable �̸� ���콺�� move�� �� ��� ������ ������ �ʴ´�.
		if( sce == null || sce instanceof UnMovable ) {

			return; // returns and does nothing

		}

		// ������ �ƹ��� MouseEvent�� ������ ���(Wait mode) topology�� ���� ���� Cursor�� ����� �����Ѵ�.
		if( workMode == WAIT_MODE ) {

			int top = sce.getTopology( e );

			setCursor( sce, top );

		}

		// end of setting mouse cursor when mouse moved and not dragged

	}

	private void setCursor(ChartElement element, int topology) {

		ChartType ct = element.getChartType();

		// 3D chart type���� graph extent�� selected mark�� ���콺 Ŀ���� ������
		// Ŀ���� ����� ���ڰ��� �ٲ��ش�.

		if( element instanceof GraphExtent && (topology > -1 && topology < 8 ) ) {



			if( ( ct instanceof ChartType3D ) && ( ! ( ct instanceof CircularChartType ) ) ) {

				CursorManager.setCursor( this, CursorManager.cross );

				return;

			}

		}

		// Title ���ο� ���콺 Ŀ���� ���� �� text editing ������ Ŀ���� �������
		// �ٲپ� �ش�.
		if( element instanceof Title && topology == element.INSIDE_AREA ) {

			CursorManager.setCursor( this, CursorManager.text );

		// Feedback�� �����ϴ� ChartType�� ���Ͽ� ChartElement�� DataElementView��
	    // ���� �� ���⸸���θ� Resize�ȴ�.
		} else if( ct instanceof FeedbackChartType && element instanceof DataElementView ) {

			int ft = ct.getFeedbackTopology();

			if ( topology == element.OUT_OF_AREA || topology == element.INSIDE_AREA || topology == ft ) {
			// Do nothing!
			} else {

				topology = element.INSIDE_AREA;

			}
			// chart type�� circle�� ��� feedback mouse�� ����� ���ڰ� ������� �������ش�.
			if ( element.getChartType() instanceof ChartTypeCircle && topology == ft ) {

				CursorManager.setCursor( this, CursorManager.cross );

			// chart type�� circle�� �ƴ� feedback mouse�� ����� �������� ������� �������ش�.
			} else {

				CursorManager.setResizableCursor(this, topology, CursorManager.def);
			}





		// unresizable
		} else if( element instanceof UnResizable ) {
			if( topology == element.OUT_OF_AREA || topology == element.INSIDE_AREA ) {
			// Do nothing!
			} else {

			topology = element.INSIDE_AREA;

			}

			CursorManager.setResizableCursor(this, topology, CursorManager.def);

		} else {

			CursorManager.setResizableCursor(this, topology, CursorManager.def);

		}

	}

	/**
	 * when mouse dragged
	 */
	public void  mouseDragged(MouseEvent e)  {

		ChartElement sce = this.selectedChartElementWhenMousePressed;   // selected chart element

		if( sce == null || sce instanceof UnMovable ) {

			return;    // returns and does nothing

		}

		int mode = this.workMode;

		if( mode == MOVE_MODE || mode == TITLE_EDIT_MODE ) {

			// Draws dragged temporary chart element shape

			int mx = e.getX(), my = e.getY();   // current x and y
			int pmx = lastMousePressed.getX(), pmy = lastMousePressed.getY();  // previous x and y

			int tx = mx - pmx, ty = my - pmy;   // ������ ��ǥ

			Graphics2D g2 = (Graphics2D) getGraphics();

			sce.paintMoveMode( g2, tx, ty );

			// End of drawing dragged temporary chart element shape

		} else if( mode == RESIZE_MODE || mode == FEEDBACK_MODE ) {

			// Draws dragged temporary chart element shape

			int wt = this.workTopology; // working topology

			Graphics2D g2 = (Graphics2D) getGraphics();

			if ( mode == FEEDBACK_MODE) {

				int cmx = e.getX(), cmy = e.getY();   // current mouse x and y

				int pmx = lastMousePressed.getX(), pmy = lastMousePressed.getY();  // previous mouse x and y

				if ( sce.getChartType() instanceof ChartTypeCircle ) {

				      int tx = cmx - pmx, ty = cmy - pmy;   // ������ ��ǥ

				      sce.paintResizeMode( g2, cmx, cmy );

				} else {

					sce.paintResizeMode( g2, lastMousePressed, e, wt );

				}

				sce.getChartType().displayFeedbackTooltips(g2, (DataElementView) sce, pmx, pmy, cmx, cmy);

			} else {    // resize mode

				sce.paintResizeMode( g2, lastMousePressed, e, wt );

			}


			// End of drawing dragged temporary chart element shape

		} else if( mode == SET_3D_WIRE_FRAME_MODE ) {  // ������ ���̾� ������ ����

			GraphExtent ge = (GraphExtent) sce;

			Graphics2D g2 = (Graphics2D) getGraphics();

			int mx = e.getX(), my = e.getY();   // current x and y

			int pmx = lastMousePressed.getX(), pmy = lastMousePressed.getY();  // previous x and y

			int tx = mx - pmx, ty = my - pmy;   // ������ ��ǥ

			ge.paint3DWireFrameMovedBy( g2, tx, ty, this.controlWireFrameOnFrontPlane );

		}

	}

	public SpreadSheet() {

		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
		cutMenu.setMnemonic('T');
		cutMenu.setText("�߶󳻱� (T)");
		chartWindowMenu2.setMnemonic('A');
		chartWindowMenu2.setText("��Ʈâ(A)");
		chartWindowMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartWindowMenu2_mousePressed(e);
			}
			});
		threeDimViewMenu2.setMnemonic('V');
		threeDimViewMenu2.setText("3���� ����(V)...");
		threeDimViewMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			threeDimViewMenu2_mousePressed(e);
			}
			});
		locationMenu2.setMnemonic('L');
		locationMenu2.setText("��ġ(L)...");
		locationMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			locationMenu2_mousePressed(e);
			}
			});
		chartOptionMenu4.setMnemonic('O');
		chartOptionMenu4.setText("��Ʈ�ɼ�(O)...");
		chartOptionMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartOptionMenu4_mousePressed(e);
			}
			});
		originalSourceDataMenu4.setMnemonic('S');
		originalSourceDataMenu4.setText("���� ������ (S)...");
		originalSourceDataMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			originalSourceDataMenu4_mousePressed(e);
			}
			});
		chartTypeMenu4.setMnemonic('T');
		chartTypeMenu4.setText("��Ʈ ����(T)...");
		chartTypeMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartTypeMenu4_mousePressed(e);
			}
			});
		chartExtentStyleMenu.setMnemonic('O');
		chartExtentStyleMenu.setText("��Ʈ ���� ����(O)...");
		chartExtentStyleMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartExtentStyleMenu_mousePressed(e);
			}
			});
		jMenuItem21.setMnemonic('A');
		jMenuItem21.setText("�����(A)");
		legendItemSymbolFormatMenu.setMnemonic('O');
		legendItemSymbolFormatMenu.setText("���� ǥ�� ����(O)...");
	legendItemSymbolFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		legendItemSymbolFormatMenu_mousePressed(e);
	    }
	});
		jMenuItem9.setMnemonic('A');
		jMenuItem9.setText("�����(A)");
		originalDataMenu.setMnemonic('S');
		originalDataMenu.setText("���� ������(S)...");
		originalDataMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			//originalDataMenu_mousePressed(e);
			}
			});
		axisRemoveMenu.setMnemonic('A');
		axisRemoveMenu.setText("�����(A)...         ");
		axisFormatMenu.setMnemonic('O');
		axisFormatMenu.setText("�༭��(O)...");
		axisFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			axisFormatMenu_mousePressed(e);
			}
			});
		removeMenu.setMnemonic('A');
		removeMenu.setText("�����(A)...");
		chartTypeMenu.setMnemonic('T');
		chartTypeMenu.setText("��Ʈ����(T)...");
		chartTypeMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartTypeMenu_mousePressed(e);
			}
			});
		tendencyLineMenu.setMnemonic('R');
		tendencyLineMenu.setText("�߼��� �߰�(R)...");
		tendencyLineMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			tendencyLineMenu_mousePressed(e);
			}
			});
		jMenuItem2.setMnemonic('O');
		jMenuItem2.setText("������ ���̺� ����(O)...");
		dataSeriesFormatMenu.setMnemonic('O');
		dataSeriesFormatMenu.setText("������ �迭 ����(O)...");
		dataSeriesFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			dataSeriesFormatMenu_mousePressed(e);
			}
			});
		jMenuItem19.setMnemonic('A');
		jMenuItem19.setText("�����(A)");
		legendItemFormatMenu.setMnemonic('O');
		legendItemFormatMenu.setText("���� �׸� ����(O)...");
	legendItemFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		legendItemFormatMenu_mousePressed(e);
	    }
	});
		legendRemoveMenu.setMnemonic('A');
		legendRemoveMenu.setText("�����(A)");
		legendFormatMenu.setMnemonic('O');
		legendFormatMenu.setText("���� ����(O)...");
		legendFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			legendFormatMenu_mousePressed(e);
			}
			});
		removeMenu2.setMnemonic('A');
		removeMenu2.setText("�����(A)");
		tendencyLineMenu2.setMnemonic('R');
		tendencyLineMenu2.setText("�߼��� �߰�(R)...");
		tendencyLineMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			tendencyLineMenu2_mousePressed(e);
			}
			});
		originalSourceDataMenu2.setMnemonic('S');
		originalSourceDataMenu2.setText("����  ������(S)...");
		originalSourceDataMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			//originalSourceDataMenu2_mousePressed(e);
			}
			});
		chartTitleRemoveMenu.setMnemonic('A');
		chartTitleRemoveMenu.setText("�����(A)");
		chartTypeMenu2.setMnemonic('T');
		chartTypeMenu2.setText("��Ʈ ����(T)...");
		chartTypeMenu2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartTypeMenu2_mousePressed(e);
			}
			});
		chartTitleFormatMenu.setMnemonic('O');
		chartTitleFormatMenu.setText("��Ʈ ���� ����(O)...");
	chartTitleFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		chartTitleFormatMenu_mousePressed(e);
	    }
	});
		dataElementFormatMenu.setMnemonic('O');
		dataElementFormatMenu.setText("������ ��� ����(O)...");
		dataElementFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			dataElementFormatMenu_mousePressed(e);
			}
			});
		graphExtentRemoveMenu.setMnemonic('A');
		graphExtentRemoveMenu.setText("�����(A)");
		removeGridLine.setMnemonic('A');
		removeGridLine.setText("�����(A)");
		chartWindowMenu.setMnemonic('A');
		chartWindowMenu.setText("��Ʈâ(A)...");
		gridLineMenu.setMnemonic('O');
		gridLineMenu.setText("���ݼ� ����(O)...");
		gridLineMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			gridLineMenu_mousePressed(e);
			}
			});
		threeDimViewMenu.setMnemonic('V');
		threeDimViewMenu.setText("3���� ����(V)...");
		threeDimViewMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			threeDimViewMenu_mousePressed(e);
			}
			});
		locationMenu.setMnemonic('L');
		locationMenu.setText("��ġ(L)...");
		locationMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			locationMenu_mousePressed(e);
			}
			});
		chartOptionMenu.setMnemonic('O');
		chartOptionMenu.setText("��Ʈ �ɼ� (O)...");
		chartOptionMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartOptionMenu_mousePressed(e);
			}
			});
		originalSourceDataMenu3.setMnemonic('S');
		originalSourceDataMenu3.setText("���� ������(S)...");
		originalSourceDataMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			originalSourceDataMenu3_mousePressed(e);
			}
			});
		chartTypeMenu3.setMnemonic('T');
		chartTypeMenu3.setText("��Ʈ ����(T)...");
		chartTypeMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			chartTypeMenu3_mousePressed(e);
			}
			});
		graphExtentStyleMenu.setMnemonic('O');
		graphExtentStyleMenu.setText("�׸� ���� ����(O)...");
		graphExtentStyleMenu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			graphExtentStyleMenu_mousePressed(e);
			}
			});
		macroSetMenu.setMnemonic('N');
		macroSetMenu.setText("��ũ�� ����(N)...");
		bottomMostMenu.setMnemonic('K');
		bottomMostMenu.setText("�� �ڷ� ������(K)");
		topMostMenu.setMnemonic('T');
		topMostMenu.setText("�� ������ ��������(T)");
		chartExtentRemoveMenu.setMnemonic('A');
		chartExtentRemoveMenu.setText("�����(A)");
		pasteMenu.setMnemonic('P');
		pasteMenu.setText("�ٿ��ֱ� (P)");
		copyMenu.setMnemonic('C');
		copyMenu.setText("����(C)");
		axisTitleRemoveMenu.setMnemonic('A');
		axisTitleRemoveMenu.setText("�����(A)");
		axisTitleFormatMenu.setMnemonic('O');
		axisTitleFormatMenu.setText("�� ���� ����(O)...");
		axisTitleFormatMenu.addMouseListener(new java.awt.event.MouseAdapter() {
	    public void mousePressed(MouseEvent e) {
		axisTitleFormatMenu_mousePressed(e);
	    }
	});

	gridLineFormatPopup.add(gridLineMenu);
		gridLineFormatPopup.addSeparator();
		gridLineFormatPopup.add(removeGridLine);
		legendFormatPopup.add(legendFormatMenu);
		legendFormatPopup.addSeparator();
		legendFormatPopup.add(legendRemoveMenu);
		chartTitleFormatPopup.add(chartTitleFormatMenu);
		chartTitleFormatPopup.addSeparator();
		chartTitleFormatPopup.add(chartTitleRemoveMenu);
		graphExtentStylePopup.add(graphExtentStyleMenu);
		graphExtentStylePopup.addSeparator();
		graphExtentStylePopup.add(chartTypeMenu3);
		graphExtentStylePopup.add(originalSourceDataMenu3);
		graphExtentStylePopup.add(chartOptionMenu);
		graphExtentStylePopup.add(locationMenu);
		graphExtentStylePopup.addSeparator();
		graphExtentStylePopup.add(threeDimViewMenu);
		graphExtentStylePopup.add(chartWindowMenu);
		graphExtentStylePopup.addSeparator();
		graphExtentStylePopup.add(graphExtentRemoveMenu);
		legendItemFormatPopup.add(legendItemFormatMenu);
		legendItemFormatPopup.addSeparator();
		legendItemFormatPopup.add(jMenuItem19);
		dataSeriesFormatPopup.add(dataSeriesFormatMenu);
		dataSeriesFormatPopup.addSeparator();
		dataSeriesFormatPopup.add(chartTypeMenu);
		dataSeriesFormatPopup.add(originalDataMenu);
		dataSeriesFormatPopup.addSeparator();
		dataSeriesFormatPopup.add(tendencyLineMenu);
		dataSeriesFormatPopup.addSeparator();
		dataSeriesFormatPopup.add(removeMenu);
		dataElementFormatPopup.add(dataElementFormatMenu);
		dataElementFormatPopup.addSeparator();
		dataElementFormatPopup.add(chartTypeMenu2);
		dataElementFormatPopup.add(originalSourceDataMenu2);
		dataElementFormatPopup.addSeparator();
		dataElementFormatPopup.add(tendencyLineMenu2);
		dataElementFormatPopup.addSeparator();
		dataElementFormatPopup.add(removeMenu2);
		axisPopup.add(axisFormatMenu);
		axisPopup.addSeparator();
		axisPopup.add(axisRemoveMenu);
		legendItemSymbolFormatPopup.add(legendItemSymbolFormatMenu);
		legendItemSymbolFormatPopup.addSeparator();
		legendItemSymbolFormatPopup.add(jMenuItem21);
		dataTableStylePopup.add(jMenuItem2);
		dataTableStylePopup.addSeparator();
		dataTableStylePopup.add(jMenuItem9);
		chartExtentStylePopup.add(chartExtentStyleMenu);
		chartExtentStylePopup.addSeparator();
		chartExtentStylePopup.add(chartTypeMenu4);
		chartExtentStylePopup.add(originalSourceDataMenu4);
		chartExtentStylePopup.add(chartOptionMenu4);
		chartExtentStylePopup.add(locationMenu2);
		chartExtentStylePopup.addSeparator();
		chartExtentStylePopup.add(threeDimViewMenu2);
		chartExtentStylePopup.add(chartWindowMenu2);
		chartExtentStylePopup.addSeparator();
		chartExtentStylePopup.add(cutMenu);
		chartExtentStylePopup.add(copyMenu);
		chartExtentStylePopup.add(pasteMenu);
		chartExtentStylePopup.add(chartExtentRemoveMenu);
		chartExtentStylePopup.addSeparator();
		chartExtentStylePopup.add(topMostMenu);
		chartExtentStylePopup.add(bottomMostMenu);
		chartExtentStylePopup.addSeparator();
		chartExtentStylePopup.add(macroSetMenu);
		axisTitlePopup.add(axisTitleFormatMenu);
		axisTitlePopup.addSeparator();
		axisTitlePopup.add(axisTitleRemoveMenu);
	}

	/**
	 * checks if any popup menu is showing now
	 */

	private boolean isAnyPopupMenuShowingNow() {
		return
			dataSeriesFormatPopup.isShowing() ||
			dataElementFormatPopup.isShowing() ||
			axisPopup.isShowing() ||
			graphExtentStylePopup.isShowing() ||
			chartExtentStylePopup.isShowing() ||
			gridLineFormatPopup.isShowing() ||
			legendFormatPopup.isShowing() ||
			legendItemFormatPopup.isShowing() ||
			legendItemSymbolFormatPopup.isShowing() ||
			chartTitleFormatPopup.isShowing() ||
			axisTitlePopup.isShowing();
	}



	void chartTypeMenu_mousePressed(MouseEvent e) {
		EditorManager.show( ChartTypeEditor.class, e );
	}

//	void originalDataMenu_mousePressed(MouseEvent e) {
//		EditorManager.show( OriginalSourceDataEditor.class, e );
//	}

	void tendencyLineMenu_mousePressed(MouseEvent e) {
		EditorManager.show( TendencyEditor.class, e );
	}



	void chartTypeMenu2_mousePressed(MouseEvent e) {
		EditorManager.show( ChartTypeEditor.class, e );
	}

//	void originalSourceDataMenu2_mousePressed(MouseEvent e) {
//		EditorManager.show( OriginalSourceDataEditor.class, e );
//	}

	void tendencyLineMenu2_mousePressed(MouseEvent e) {
		EditorManager.show( TendencyEditor.class, e );
	}

	void axisFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( AxisFormatEditor.class, e );
	}
	// ���ݼ� ���� ���̾�α� �ڽ� Open
	void gridLineMenu_mousePressed(MouseEvent e) {
		EditorManager.show( GridLineFormatEditor.class, e );
	}

	// ���� ���� ���̾�α� �ڽ� Open
	void legendFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( LegendFormatEditor.class, e );
	}

	// ���� �׸� ���� ���̾�α� �ڽ� Open
	void legendItemFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( LegendItemFormatEditor.class, e );
    }

	// ���� ǥ�� ���� ���̾�α� �ڽ� Open
	void legendItemSymbolFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( LegendItemSymbolFormatEditor.class, e );
    }

	// ��Ʈ ���� ���� ���̾�α� �ڽ� Open
	void chartTitleFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( ChartTitleFormatEditor.class, e );
    }

	// �� ���� ���� ���̾�α� �ڽ� Open
	void axisTitleFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( AxisTitleFormatEditor.class, e );
    }

	// ������ ��� ���� ���̾�α� �ڽ� Open
	void dataElementFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( DataElementFormatEditor.class, e );
	}
	// ������ �迭 ���� ���̾�α� �ڽ� Open
	void dataSeriesFormatMenu_mousePressed(MouseEvent e) {
		EditorManager.show( DataSeriesFormatEditor.class, e );
	}

	void graphExtentStyleMenu_mousePressed(MouseEvent e) {
		EditorManager.show( GraphExtentStyleEditor.class, e );
	}

	void chartTypeMenu3_mousePressed(MouseEvent e) {
		EditorManager.show( ChartTypeEditor.class, e );
	}

	void originalSourceDataMenu3_mousePressed(MouseEvent e) {
//		EditorManager.show( OriginalSourceDataEditor.class, e );
	}

	void chartOptionMenu_mousePressed(MouseEvent e) {
		EditorManager.show( ChartOptionEditor.class, e );
	}

	void locationMenu_mousePressed(MouseEvent e) {
		EditorManager.show( ChartLocationEditor.class, e );
	}

	void threeDimViewMenu_mousePressed(MouseEvent e) {
		EditorManager.show( ThreeDimensionEditor.class, e );
	}

	void chartExtentStyleMenu_mousePressed(MouseEvent e) {
		EditorManager.show( ChartExtentStyleEditor.class, e );
	}

	void chartTypeMenu4_mousePressed(MouseEvent e) {
		EditorManager.show( ChartTypeEditor.class, e );
	}

	void originalSourceDataMenu4_mousePressed(MouseEvent e) {
//		EditorManager.show( OriginalSourceDataEditor.class, e );
	}

	void chartOptionMenu4_mousePressed(MouseEvent e) {
		EditorManager.show( ChartOptionEditor.class, e );
	}

	void locationMenu2_mousePressed(MouseEvent e) {
		EditorManager.show( ChartLocationEditor.class, e );
	}

	void threeDimViewMenu2_mousePressed(MouseEvent e) {
		EditorManager.show( ThreeDimensionEditor.class, e );
	}

	void chartWindowMenu2_mousePressed(MouseEvent e) {

	}

	public static SpreadSheet getCurrentSpreadSheet() {
		return currentSpreadSheet;
	}

	public static void applyChartType( ChartType chartType ) {

		SpreadSheet css = getCurrentSpreadSheet(); // current spread sheet

		if( css == null ) { return; }

		ChartElement sce = css.getSelectedChartElement(); // selected chart element

		if( sce == null ) { return; }

		Chart chart = sce.getChart(); // chart selected

		chart.applyChartType( chartType ); // apply new chart type

		css.repaint(); // repaint the spread sheet
	}

	public static void applyChartOption(ChartOption chartOption) {
		SpreadSheet css = getCurrentSpreadSheet(); // current spread sheet

		if( css == null ) { return; }

		ChartElement sce = css.getSelectedChartElement(); // selected chart element

		if( sce == null ) { return; }

		Chart chart = sce.getChart(); // chart selected

		chart.applyChartOption( chartOption );

		css.repaint(); // repaint the spread sheet
	}

	public void keyPressed(KeyEvent e) {

		char c = e.getKeyChar();

		boolean ctrlDown = e.isControlDown();

		Utility.debug(this, "Key = " + c + ", CTRL = " + ctrlDown );

		if( ctrlDown && (c == 'C' || c == 'c' ) ) {

			copySelectedChart();

		}  else if( ctrlDown && ( c == 'V' || c == 'v' ) ) {

			pasteCopiedChart();

		}

	}

	public void copySelectedChart() {

		ChartElement sce = this.selectedChartElementWhenMousePressed;

		if( sce != null ) {

			Chart chart = sce.getChart();

			this.copiedChart = chart.cloneChart( this );

			pasteNum = 0;

		}

	}

	public void pasteCopiedChart() {

		Chart chart = this.copiedChart;

		if( chart != null ) {

			Chart cloneChart = chart.cloneChart( this );

			pasteNum ++;

			cloneChart.moveBy( 10*pasteNum, 10*pasteNum );

			this.charts.addLast( cloneChart );

			repaint();

		}

	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {

		char c = e.getKeyChar();

		if( c == e.VK_ENTER && selectedChartElementWhenMousePressed == null ) {

		    Utility.debug(this, "ENTER" );

			int size = charts.size();

			for (int i = 0; i < size; i++) {

			    Chart chart = (Chart) charts.get(i);
				chart.reCreateGraphExtentWhenModifiedValue(chart.getChartType(), null);

				this.repaint();

		    }
		}
	}

	/**
	 * DataElementView�� �ش��ϴ� ���� ���� value�� �����ϴ� �Լ��̴�.
	 *
	 * @param   dataElementView : ���� ��ġ�� ��Ÿ���� DataElement ��ü
	 * @param   value : ������ ���� �ش��ϴ� Object ��ü
	 * @see     ChartType�� feedbackEventHandling(Graphics2D, DataElementView, int, int)
	 */
	public void setValueAtDataElementView(DataElementView dataElementView, Object value) {

	    SpreadSheet sheet = dataElementView.getSheet();

		SpreadSheetTableModel model = (SpreadSheetTableModel) sheet.getModel();

		DataElement dataElement = dataElementView.getDataElement();

		// dataElementView�� ����Ű�� ���� ��ġ�� �����´�.
		int row = dataElement.getRow();
		int col = dataElement.getColumn();

		model.setValueAt(value, row, col);

	}

	public void setSelectedChartElementColor() {

	    if( selectedChartElementWhenMousePressed == null ) {

		   return;

		}

		selectedChartElementWhenMousePressed.getShapeStyle().setFillColor( Color.black );

		repaint();

	}










}
