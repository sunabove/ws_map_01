package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class TendencyInformation {

  public static final int LINEAR_TYPE = 0, LOG_TYPE = 1, POLYNOMIAL_TYPE = 2, RASING_POWER_TYPE = 3,
			   EXPONETIAL_TYPE = 4;

  private int tendencyType = LINEAR_TYPE;

  private int intercept;

  public static final int AUTOMATIC_TENDENCY_NAME = 0, USER_DEFINED_TENDENCY_NAME = 1;
  private int tendencyNameType = AUTOMATIC_TENDENCY_NAME;

  private String tendencyName;

  private boolean showFunctionOnChart;
  private boolean showR2OnChart;

  public TendencyInformation( int tendencyType, boolean showFunctionOnChart, int intercept,
			      boolean showR2OnChart, int tendencyNameType, String tendencyName ) {

      this.tendencyType = tendencyType;
      this.showFunctionOnChart = showFunctionOnChart;
      this.intercept = intercept;
      this.showR2OnChart = showR2OnChart;
      this.tendencyNameType = tendencyNameType;
      this.tendencyName = tendencyName;

  }

  public int getIntercept() {

    return intercept;

  }

  public int getTendencyType() {

    return tendencyType;

  }

  public String getTendencyName() {

    return tendencyName;

  }

  public int getTendencyNameType() {

    return tendencyNameType;

  }

}