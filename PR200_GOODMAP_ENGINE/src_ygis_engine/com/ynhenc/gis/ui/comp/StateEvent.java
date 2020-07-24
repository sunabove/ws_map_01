package com.ynhenc.gis.ui.comp;

public class StateEvent {

  public StateEvent( double current, double total, Object obj ) {

     this.current = current;
     this.total = total; 
     //this.eventTime = System.currentTimeMillis() ;

  }
  
  public int getWorkPercent() {
	  if( total <= 0 ) {
		  return 0 ;
	  } else {
		  return (int) (100 * this.current / this.total);
	  }
  }

  public double current;
  public double total;   

}
