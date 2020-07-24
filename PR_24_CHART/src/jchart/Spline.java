/********************************************************/
/*    cubic spline interpolation by Java                */
/*          Programed by Syunichi Kato                  */
/*          Last revised by K.Minemura on June 16, 1999.*/
/********************************************************/
package jchart;

import java.awt.*;
import java.awt.geom.*;

public class Spline {

   double sx_t0,sx_t1,sy_t0,sy_t1;   //\uFFFDグロ\uFFFDバル変数
   int    cWidth,cHeight;
   /*  計算条件   */
   int  NDATA =  6;      //\uFFFD節\uFFFD数（入力するデ\uFFFD\uFFFD数）
   int  NINT = 100;      //\uFFFD補間\uFFFD数
   int  i;
   int  bcs = 0;         // 始\uFFFD(s)は自然条件
   int  bce = 1;         // 終\uFFFD(e)は固定条件
   double ydashs = 0.0;  // 始\uFFFDの境界条件（1次導関数）←この場合は\uFFFD\uFFFD\uFFFD
   double ydashe = -1.0; // 終\uFFFDの境界条件（1次導関数）

   double xdata[];
   double ydata[];

   double x0=0.0, x1=1000.0, y0=0.0, y1=600.0;  // グラフの\uFFFD示範囲

   double xint[]=new double[NINT];   // xの補間値を格\uFFFDする配列
   double yint[]=new double[NINT];   // yの補間値を格\uFFFDする配列
   double ydash[]=new double[NDATA]; // 節\uFFFDにおける1次導関数を格\uFFFD

   SPLINT sp = new SPLINT();   // スプラインをオブジェクト sp とする

   public Spline(double xdata[], double ydata[], int cWidth, int cHeight ) {

      this.xdata = xdata;
      this.ydata = ydata;
      this.cWidth = cWidth;
      this.cHeight = cHeight;

      NDATA = xdata.length;

      init();

   }

   public void init(){   // メインル\uFFFD\uFFFDン
          input();
                              // 初期値の設定
          sp.S_spline(xdata,ydata,NDATA,// スプライン関数計算
                                  xint,yint,NINT,bcs,ydashs,bce,ydashe,ydash);
          //init_tr();
   }

   void input(){          //   初期値の設定
      double dx=(xdata[NDATA-1]-xdata[0])/(double) (NINT-1);
      xint[0] = xdata[0];
      for(int i=1; i<NINT; i++){
         xint[i]=xdata[0]+dx*(double) (i);
      }
   }

   /*************  グラフィック出力部  ***************/
   void init_tr(){	        // 座標変換パラメ\uFFFD\uFFFDの計算
          double sx0,sx1,sy0,sy1;
          double sx_size=0.8;
          double sy_size=0.8;
          sx0=(double)cWidth * (1.0-sx_size)*0.5;
          sx1=(double)cWidth -sx0;
          sy1=(double)cHeight *(1.0-sy_size)*0.5;
          sy0=(double)cHeight -sy1;
          sx_t0=(sx0*x1-sx1*x0)/(x1-x0);
          sx_t1=(sx1-sx0)/(x1-x0);
          sy_t0=(sy0*y1-sy1*y0)/(y1-y0);
          sy_t1=(sy1-sy0)/(y1-y0);
   }

   int xtr(double x){
           return(int)(sx_t1*x+sx_t0);
   }

   int ytr(double y){
           return(int)(sy_t1*y+sy_t0);
   }

   public Shape getSplineShape(){

          int  i, r0, d0;
          double dsize=0.015;                  // プロットする\uFFFDの大きさを設定

          if(cWidth < cHeight) d0=(int)(dsize*(double)cHeight);
          else		           d0=(int)(dsize*(double)cWidth);

          r0=(int)(d0/2);

          GeneralPath path = new GeneralPath();

          for(i=0; i<NINT; i++){

                 if( i == 0 ) {

                    path.moveTo( (int) xint[i], (int) yint[i] );

                 } else {

                     path.lineTo( (int) xint[i], (int) yint[i] );

                 }

          }

          for(i = NINT - 1 ; i > -1 ; i-- ){

                 if( i == 0 ) {

                    path.closePath();

                 } else {

                     path.lineTo( (int) xint[i], (int) yint[i] );

                 }

          }

          return path;

   }
}

class SPLINT{
        double a[],b[],c[],h[];
   /*********** 3次スプライン補間関数  spline  ************************/
   /*    bcs,bce;始\uFFFDと終\uFFFDの境界条件，0=自然，1=固定，2=周期         */
   /*    yds,yde;始\uFFFDと終\uFFFDの1次導関数（固定条件の場合）              */
   /*    x[],y[];節\uFFFD座標，xint[];補間のx座標，yint[]=yの補間値（出力）*/
   /*    ydash[];節\uFFFDの1次導関数（出力）, NINT=補間\uFFFD数，NDATA=節\uFFFD数  */
   /************************************* by K.Minemura *****************/
   void S_spline(double x[],double y[],int N,double xint[],double yint[],
         int NO,int bcs, double yds,int bce, double yde, double yda[]){
          int i,j;
      double ai,bi,ci,di,dx;
      a=new double[NO];
          b=new double[NO];
          c=new double[NO];
          h=new double[NO];

          for(i=0; i<N-1; i++) 	 h[i]=x[i+1]-x[i];

          /* 始\uFFFDの\uFFFD末条件の代入  */
          a[0]=0.0;
          if(bcs==0){
                  b[0]=2*h[0];
                  c[0]=h[0];
                  yda[0]=3.0*(y[1]-y[0]);
          }
          if(bcs==1){
                  b[0]=1.0;
                  c[0]=0.0;
                  yda[0]=yds;
          }

          /*  中間節\uFFFDの係数       */
          for(i=1; i<N-1; i++){
             a[i]=h[i];
                 b[i]=2.0*(h[i-1]+h[i]);
                 c[i]=h[i-1];
             yda[i]=3.0*((y[i]-y[i-1])*h[i]/h[i-1]+(y[i+1]-y[i])*h[i-1]/h[i]);
          }

          /* 終\uFFFDの\uFFFD末条件の代入   */
          c[N-1]=0.0;
          if(bce==0){
                  a[N-1]=h[N-2];
                  b[N-1]=2.0*h[N-2];
                  yda[N-1]=3.0*(yda[N-1]-yda[N-2]);
          }
          if(bce==1){
                  a[N-1]=0.0;
                  b[N-1]=1.0;
                  yda[N-1]=yde;
          }

      /* 3\uFFFD方程式の解\uFFFD */
          S_trid(b,c,a,yda,N,0);

          /* 補間値（出力) */
          for(j=0,i=0;i<N-1;i++){
             ai=(2.0*(y[i]-y[i+1])/h[i]+yda[i]+yda[i+1])/(h[i]*h[i]);
         bi=(3.0*(y[i+1]-y[i])/h[i]-2.0*yda[i]-yda[i+1])/h[i];
         ci=yda[i];
         di=y[i];
                 while(xint[j] < x[i+1]){
                dx=xint[j]-x[i];
            yint[j]=dx*(dx*(dx*ai+bi)+ci)+di;
                if (j < NO) j++;
         }
                 yint[NO-1]=y[N-1];
          }
   }

   /*********** 3\uFFFD方程式の解\uFFFD（周期条件のため一部改変）************** */
   void S_trid ( double a[], double b[],
                                                  double c[], double x[], int N,int k){
      int i;
      if(k==0){
                  b[0]=b[0]/a[0];
              for(i=1; i<N-1; i++){    /*  前進消去部  */
             a[i]=a[i]-b[i-1]*c[i];
             b[i]=b[i]/a[i];
                  }
                  a[N-1]=a[N-1]-b[N-2]*c[N-1];
          }
                //

      x[0]=x[0]/a[0];
      for(i=1; i<N; i++)    x[i]=(x[i]-c[i]*x[i-1])/a[i];

           //     後退代入部
      for(i=N-2; i>=0; i--) x[i]=x[i]-x[i+1]*b[i];
   }

}
