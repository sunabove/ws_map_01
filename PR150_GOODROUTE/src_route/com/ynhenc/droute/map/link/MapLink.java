package com.ynhenc.droute.map.link;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.util.Iterator;

import com.ynhenc.comm.db.*;
import com.ynhenc.droute.*;
import com.ynhenc.droute.map.Map;
import com.ynhenc.droute.map.link.sql.SqlGetterRoute;
import com.ynhenc.droute.map.square.GeoSquare;

public class MapLink extends Map {


	public static MapLink getNewMapLink(  ) throws Exception {

		MapLink map = new MapLink();

		map.createWorld( -1 );

		map.runUpdaterThread();

		return map;

	}

	public void runUpdaterThread() {

		//final long sleepTime = 10*1000;
		final long sleepTime = 5*60*1000;

		Runnable runnable = new Runnable() {
			public void run() {
				while( true ) {
					try {
						MapLink.this.debug( "Running Thread ....");
						MapLink.this.updateLinkRealTimeInfo();
						MapLink.this.debug( "Done Running Thread !!!!");
						Thread.currentThread().sleep( sleepTime );
					} catch (Exception e ) {
						MapLink.this.debug( e );
					}
				}
			}
		};

		Thread thread = new Thread( runnable );
		thread.start();
	}

	@Override
	public NodeIndexer createNodeIndexer( ) throws Exception {

		boolean localDebug = false ;

		String src = "sql001_node_list.sql";

		SQL sql = this.getSQL( src );

		boolean sqlDebug = false;

		QueryResult qr = this.getQueryResult( sql.getSqlText() , sqlDebug );

		if( qr == null ) {
			this.debug( "Cannot get a query result : " + sql , localDebug );
			NodeIndexer nodeIndexer = new NodeIndexer( 0 );
			return nodeIndexer;
		} else {
			int indexSize = -1 ;

			GeoLinkRecord nodeRecord = new GeoLinkRecord();

			NodeIndexer nodeIndexer = null;
			Node node;

			int recNo = 0;

			final int maxRecNo = -1 ;

			int index = 0 ;

			while( qr.hasNext() && ( maxRecNo < 1 || recNo < maxRecNo ) ) {
				if( indexSize < 0 ) {
					indexSize = qr.getInteger( "link_cnt" );

					indexSize = maxRecNo > 0 ? maxRecNo :  indexSize;

					nodeIndexer = new NodeIndexer( indexSize );
				}

				nodeRecord.setDataFromQueryResult(qr);
				node = nodeRecord.toNode( index );
				nodeIndexer.add( node );

				this.debug( "recNo(" + recNo + ") " + nodeRecord, localDebug );
				index ++ ;
				recNo ++;
			}

			this.debug( "Done making nodeIndexer!!!", true );

			return nodeIndexer;
		}
	}

	@Override
	public void createLinkList( ) throws Exception {

		boolean localDebug = false ;

		String src = "sql002_link_list.sql";

		SQL sql = this.getSQL( src );

		boolean sqlDebug = true ;

		QueryResult qr = this.getQueryResult( sql.getSqlText() , sqlDebug );

		LinkList linkList = new LinkList();

		if( qr == null ) {
			this.debug( "Cannot get a query result : " + sql , localDebug );
		} else {
			int indexSize = -1 ;

			GeoTurnnRecord turnRecord = new GeoTurnnRecord();
			int recNo = 0;

			final int maxRecNo = -1 ;

			NodeIndexer nodeIndexer = this.getNodeIndexer();

			Link link;

			int index = 0;

			while( qr.hasNext() && ( maxRecNo < 1 || recNo < maxRecNo ) ) {
				turnRecord.setDataFromQueryResult(qr);
				link = turnRecord.toLink( index, nodeIndexer );
				linkList.add(link);
				this.debug( "recNo(" + recNo + ") " + turnRecord, localDebug );
				index ++;
				recNo ++;
			}

			this.debug( "Done making LinkList !!!", true );

		}

		this.linkList = linkList;

	}

	@Override
	public void updateLinkRealTimeInfo() throws Exception {

		boolean localDebug = false ;

		String src = "sql100_link_spd.sql";

		SQL sql = this.getSQL( src );

		boolean sqlDebug = false;

		QueryResult qr = this.getQueryResult( sql.getSqlText() , sqlDebug );

		if( qr == null ) {
			this.debug( "Cannot get a query result : " + sql , localDebug );
		} else {
			this.debug( "Making node real time info ...", true );

			NodeIndexer nodeIndexer = this.getNodeIndexer();
			Node node;
			RealTimeInfo realTimeInfo;

			int recNo = 0;
			final int maxRecNo = -1 ;

			Integer link_idx;
			while( qr.hasNext() && ( maxRecNo < 1 || recNo < maxRecNo ) ) {
				link_idx = qr.getInteger( "link_idx");
				if( link_idx != null ) {
					node = nodeIndexer.get( link_idx );
					if( node != null ) {
						realTimeInfo = node.getRealTimeInfo();
						realTimeInfo.setDataFromQueryResult(qr);
						this.debug( "link_idx(" + link_idx + ") " + realTimeInfo, localDebug );
					}
				}
				recNo ++;
			}

			this.debug( "Done making node real time info!!! (recNo=" + recNo + ")", true );
		}

	}

	@Override
	public Dimension getNodeSize() {
		if( this.nodeSize == null ) {
			this.nodeSize = new Dimension( 3, 3);
		}
		return this.nodeSize;
	}

	public static void main( String [] args ) throws Exception {
		MapLink.getNewMapLink();
		System.out.println( "Good bye!!!!" );
		System.exit( 0 );
	}

}
