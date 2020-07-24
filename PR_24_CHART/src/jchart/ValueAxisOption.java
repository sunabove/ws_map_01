package jchart;

/**
 * Title:           ValueAxisOption Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author          withhim
 * @version         1.0
 * Description:     ValueAxis Ŭ������ �ڷḦ ��� �ִ� Ŭ������ AxisOption�� ��ӹ޴´�.
 */
import jcosmos.Utility;


public class ValueAxisOption extends AxisOption{

    public ValueAxisOption() {
		super();

		this.hasMajorGridLineGroup = true;
		this.hasMinorGridLineGroup = false;
    }

	/**
	 * Axis�� TickLabel�� �� �ִ밪�� �ּҰ� �׸��� ������ ���Ѵ�.
	 *
	 * ����ڰ� ������ ������ TickLabel�� maxScale, minScale, majorUnit, minorUnit
	 * ������ ���������� ����� �����ϰ� ������ xxxIsAuto������ ���� �ڵ����� ����� ��
	 * ���� �ִ�.
	 * �ڵ����� ���� �� maxScale, minScale, majorScale�� ������ ������ ���� �����Ѵ�.
	 * xxxIsAuto�� ���� ���� ���� ������ ���� ��� �ϴ� ������ ��������.
	 *  maxScaleIsAuto | minScaleIsAuto | majorScaleIsAuto
	 * 1     T               T                   T           major -> min -> max
	 * 2     T               T                   F           major -> min -> max
	 * 3     T               F                   T           min -> major -> max
	 * 4     T               F                   F           major -> min -> max
	 * 5     F               T                   T           max -> min -> major(üũ)
	 * 6     F               T                   F           major -> max -> min
	 * 7     F               F                   T           min -> max -> major
	 * 8     F               F                   F           major -> min -> max
	 *
	 * minorScale�� IsAuto�� true�̸� majorScale�� ���� 1/5�� ���� ������.
	 *
	 */
    public void calibrateAxisMinMaxValueAndUnit( double min, double max) {

		if ( ( this.maxScaleIsAuto == false )
			&& ( this.minScaleIsAuto == false )
			&& ( this.majorUnitIsAuto == false )
			&& ( this.minorUnitIsAuto == false ) ) {

			return;

		}


		// maxScaleIsAuto�� ture�϶� �׻� �ִ밪�� data�� �ִ밪�� 1.05�躸�� ũ��.
		double expectedMaxScale = max * 1.05;
		double expectedMinScale = ( min < 0 ) ? min : 0 ;

		final double interval =  expectedMaxScale - expectedMinScale;

		// minScale ���
		if ( this.minScaleIsAuto == true ) {

			this.setMinorUnit( expectedMinScale );

		}



	    // majorUnit ���
		if ( this.majorUnitIsAuto == true ) {

	        final double logTen = ChartType.logTen;

            final double digitNum = Math.floor( Math.log( interval ) / logTen ); // ���� �ڸ���

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

		// maxScale ���
		if ( this.maxScaleIsAuto == true ) {

		    double scale = this.majorUnit + this.minScale;

		    // maxScaleIsAuto�� ture�϶� �׻� �ִ밪�� data�� �ִ밪�� 1.05�躸�� ũ��.
			while ( scale <= expectedMaxScale ) {

			    scale = scale + majorUnit;

			}

			this.setMaxScale( scale );


		}

		// minorUnit ���
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