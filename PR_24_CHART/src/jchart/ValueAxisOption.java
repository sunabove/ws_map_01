package jchart;

/**
 * Title:           ValueAxisOption 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author          withhim
 * @version         1.0
 * Description:     ValueAxis 클래스의 자료를 담고 있는 클래스로 AxisOption을 상속받는다.
 */
import jcosmos.Utility;


public class ValueAxisOption extends AxisOption{

    public ValueAxisOption() {
		super();

		this.hasMajorGridLineGroup = true;
		this.hasMinorGridLineGroup = false;
    }

	/**
	 * Axis의 TickLabel에 들어갈 최대값과 최소값 그리고 단위를 구한다.
	 *
	 * 사용자가 임이의 값으로 TickLabel에 maxScale, minScale, majorUnit, minorUnit
	 * 변수에 저장함으로 사용이 가능하고 각각의 xxxIsAuto변수에 의해 자동으로 계산이 될
	 * 수도 있다.
	 * 자동으로 계산될 때 maxScale, minScale, majorScale은 각각의 변수의 값을 참조한다.
	 * xxxIsAuto의 값에 따라 위의 변수의 값을 계산 하는 순서가 정해진다.
	 *  maxScaleIsAuto | minScaleIsAuto | majorScaleIsAuto
	 * 1     T               T                   T           major -> min -> max
	 * 2     T               T                   F           major -> min -> max
	 * 3     T               F                   T           min -> major -> max
	 * 4     T               F                   F           major -> min -> max
	 * 5     F               T                   T           max -> min -> major(체크)
	 * 6     F               T                   F           major -> max -> min
	 * 7     F               F                   T           min -> max -> major
	 * 8     F               F                   F           major -> min -> max
	 *
	 * minorScale은 IsAuto가 true이면 majorScale의 값의 1/5의 값을 가진다.
	 *
	 */
    public void calibrateAxisMinMaxValueAndUnit( double min, double max) {

		if ( ( this.maxScaleIsAuto == false )
			&& ( this.minScaleIsAuto == false )
			&& ( this.majorUnitIsAuto == false )
			&& ( this.minorUnitIsAuto == false ) ) {

			return;

		}


		// maxScaleIsAuto가 ture일때 항상 최대값은 data의 최대값의 1.05배보다 크다.
		double expectedMaxScale = max * 1.05;
		double expectedMinScale = ( min < 0 ) ? min : 0 ;

		final double interval =  expectedMaxScale - expectedMinScale;

		// minScale 계산
		if ( this.minScaleIsAuto == true ) {

			this.setMinorUnit( expectedMinScale );

		}



	    // majorUnit 계산
		if ( this.majorUnitIsAuto == true ) {

	        final double logTen = ChartType.logTen;

            final double digitNum = Math.floor( Math.log( interval ) / logTen ); // 십의 자리수

            final double defaultUnit = Math.pow( 10, digitNum - 1 );

			double quota = Double.MAX_VALUE;

			double unit = defaultUnit;

			final int [] factors = { 1, 2, 5 };

			for ( int i = 0; quota> 10 ; i++ ) {

				for ( int k = 0; k < 3 && quota > 10 ; k++ ) {

					unit = defaultUnit * factors[k] * Math.pow( 10, i );

				    quota = interval / unit;

//					System.out.println("unit = " + unit);
//				    System.out.println("quota = " + quota);

				}

		    }

			this.setMajorUnit( unit );

		}

		// maxScale 계산
		if ( this.maxScaleIsAuto == true ) {

		    double scale = this.majorUnit + this.minScale;

		    // maxScaleIsAuto가 ture일때 항상 최대값은 data의 최대값의 1.05배보다 크다.
			while ( scale <= expectedMaxScale ) {

			    scale = scale + majorUnit;

			}

			this.setMaxScale( scale );


		}

		// minorUnit 계산
		if ( this.minorUnitIsAuto == true ) {

			this.setMinorUnit( majorUnit / 5 );

		}

//		Utility.debug(this, "max = " + max);
//		Utility.debug(this, "min = " + min);
//		Utility.debug(this, "interval = " + interval);
//	    Utility.debug(this, "maxScale = " + this.maxScale);
//		Utility.debug(this, "minScale = " + this.minScale);
//		Utility.debug(this, "majorUnit = " + this.majorUnit);

    }


}