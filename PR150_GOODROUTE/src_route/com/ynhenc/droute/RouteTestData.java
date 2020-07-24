package com.ynhenc.droute;

import java.awt.geom.Point2D;

public class RouteTestData {

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point2D getStart() {
		return start;
	}

	public void setStart(Point2D start) {
		this.start = start;
	}

	public Point2D getEnd() {
		return end;
	}

	public void setEnd(Point2D end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return this.getName();
	}

	private RouteTestData(String name, double sx, double sy, double ex, double ey ) {

		this.name = name;
		this.start = new Point2D.Double( sx, sy );
		this.end = new Point2D.Double( ex, ey );
	}

	public static RouteTestData [] getTestDataList() {
		return testDataList;
	}

	public static RouteTestData getTestData( int index ) {
		index = index%getTestDataList().length;
		index = index < 0 ? 0 : index;
		return getTestDataList( )[ index ];
	}

	private String name;
	private Point2D start;
	private Point2D end;

	private static final RouteTestData [] testDataList =
	{
		new RouteTestData( "서울지방경찰청->프레지던트호텔" , 126.971988 , 37.574922 , 126.979583 , 37.565728 ),
		new RouteTestData( "신반포아파트->서초제일빌딩" , 127.00962742473658 , 37.51583244383613 , 127.02315466055472 , 37.4905982598693 ),
		new RouteTestData( "경복궁역->오리엔탈호텔" , 126.97552424086206 , 37.575902766290326 , 126.98387614899252 , 37.560626626632896 ),
		new RouteTestData( "채널->독립문역" ,  127.000374 , 37.576168 , 126.960229 , 37.572199 ),
		new RouteTestData( "2009-10-16-02" ,  126.9507659490964 , 37.550418738917884 , 126.98570119558399 , 37.5211228150141 ),
		new RouteTestData( "2009-10-16-01" ,  126.9507659490964 , 37.550418738917884 ,  126.98570119558399 , 37.5211228150141 ),
		new RouteTestData( "봉화삼거리->시조사삼거리" ,  127.087147 , 37.6029633 , 127.0536296, 37.5881959 ),
	};

}
