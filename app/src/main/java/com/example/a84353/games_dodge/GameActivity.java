/*
package com.example.a84353.games_dodge;


import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84353.games_dodge.R;
import com.example.a84353.games_dodge.ScoreActivity;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    static ImageView lion,bear,circle;
    static TextView tv_score;
    static boolean running;
    static ConstraintLayout area;
    static Timer RunningBear;
    static int WaitingLion;
    final int wtime=10;
    static int score;
    double lionX,lionY,lionS;
    double lastOffX,lastOffY;
    static boolean caught;
    private static Handler timerHandler=new Handler();

    //------------> x
   //\
    //\   .
    //\     .
    //\
   // y


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84353.games_dodge.R;
import com.example.a84353.games_dodge.ScoreActivity;

import java.util.Timer;
import java.util.TimerTask;

private void RunBear(){
        running=true;
        RunningBear=new Timer();
        final TimerTask mvTask=new TimerTask() {
            @Override
            public void run() {
                float nx=(float)(bear.getX()+lastOffX);
                float ny=(float)(bear.getY()+lastOffY);
                if(nx<0)nx=0.0f;
                if(nx+bear.getWidth()>area.getWidth())nx=area.getWidth()-bear.getWidth();
                if(ny<0)ny=0.0f;
                if(ny+bear.getHeight()>area.getHeight())ny=area.getHeight()-bear.getHeight();
                bear.setX(nx);
                bear.setY(ny);
            }
        };
        RunningBear.scheduleAtFixedRate(mvTask,100,40);
    }
    private void StopBear(){
        if (running)
        RunningBear.cancel();
        running=false;
    }
    private class CircleTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent me){
            if(me.getAction()==MotionEvent.ACTION_UP){
                if(running)
                    StopBear();
            }
            else {
                if(caught)return true;
                double tx=me.getX(),ty=me.getY(),ox,oy,r,dx,dy,len;
                int wid=view.getWidth(),het=view.getHeight();
                ox=wid/2.0;oy=het/2.0;
                r=wid/2.0;
                dx=tx-ox;
                dy=ty-oy;
                len=Math.sqrt(dx*dx+dy*dy);
                Log.i("debug","touch "+me.getX()+","+me.getY()+","+ox+","+oy);
                if(len>r) {
                    dx=dx*r/len;
                    dy=dy*r/len;
                    len=r;
                }
                lastOffX=dx/5.0;
                lastOffY=dy/5.0;

                if(!running)RunBear();

            }

            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        score=0;WaitingLion=0;lionS=20;caught=false;running=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        circle=(ImageView) findViewById(R.id.circle);
        lion=(ImageView)findViewById(R.id.imageLion);
        bear=(ImageView)findViewById(R.id.imageBear);
        area=(ConstraintLayout) findViewById(R.id.gameArea);
        tv_score=(TextView)findViewById(R.id.score);
        circle.setOnTouchListener(new CircleTouchListener());
        lion.setX((float)(area.getWidth()-lion.getWidth()));
        lion.setY((float)(area.getHeight()-lion.getHeight()));
        double ang=Math.random()*Math.PI/2;
        lionX=lionS*Math.sin(ang);
        lionY=lionS*Math.cos(ang);
        final Timer scTimer=new Timer();
        final TimerTask scTask=new TimerTask(){
            @Override
            public void run(){
                score+=(1+lionS/30);
                timerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_score.setText("your score: "+score);
                    }
                });
            }
        };
        scTimer.scheduleAtFixedRate(scTask,50,500);

        tv_score.setText(new String(""+score));
        final Timer mvTimer = new Timer();
        final TimerTask mvTask=new TimerTask() {
            @Override
            public void run() {
                if(WaitingLion>0)WaitingLion--;
                else{

                    double lx=lion.getX(),ly=lion.getY();
                    lx+=lionX;
                    ly+=lionY;
                    if(lx<0){
                        lx=0;
                        WaitingLion=wtime;
                        double ang=(1.0+179.0*Math.random())/180*Math.PI;
                        lionX=lionS*Math.sin(ang);
                        lionY=lionS*Math.cos(ang);
                    }
                    else if(lx+lion.getWidth()>area.getWidth()){
                        lx=area.getWidth()-lion.getWidth();
                        WaitingLion=wtime;
                        double ang=(1.0+179.0*Math.random())/180*Math.PI;
                        lionX=-lionS*Math.sin(ang);
                        lionY=lionS*Math.cos(ang);
                    }
                    if(ly<0){
                        ly=0;
                        WaitingLion=wtime;
                        double ang=(1.0+179.0*Math.random())/180*Math.PI;
                        lionY=lionS*Math.sin(ang);
                        lionX=lionS*Math.cos(ang);
                    }
                    else if(ly+lion.getHeight()>area.getHeight()){
                        ly=area.getHeight()-lion.getHeight();
                        WaitingLion=wtime;
                        double ang=(1.0+179.0*Math.random())/180*Math.PI;
                        lionY=-lionS*Math.sin(ang);
                        lionX=lionS*Math.cos(ang);
                    }
                    if(WaitingLion>0)lionS+=5;
                    lion.setX((float)lx);
                    lion.setY((float)ly);
                }


            }
        };
        mvTimer.scheduleAtFixedRate(mvTask,20,40);
        final Timer ckTimer=new Timer();
        TimerTask ckTask=new TimerTask() {
            @Override
            public void run() {
                double bx=bear.getX(),
                        by=bear.getY(),
                        bw=bear.getWidth(),
                        lx=lion.getX(),
                        ly=lion.getY(),
                        lw=lion.getWidth();
                double dx,dy,d,p;
                dx=bx-lx;dy=by-ly;
                d=Math.sqrt(dx*dx+dy*dy);
                p=lw/2+bw/2;
                if(d<p){
                    if(running)StopBear();
                    mvTimer.cancel();
                    scTimer.cancel();
                    ckTimer.cancel();
                    if(caught==false){
                        caught=true;
                        timerHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent gbn=new Intent(GameActivity.this, ScoreActivity.class);
                                gbn.putExtra("score",""+score);
                                startActivity(gbn);
                                finish();
                            }
                        });


                    }

                }
            }
        };
        ckTimer.scheduleAtFixedRate(ckTask,10,60);
    }

}
*/
package com.example.a84353.games_dodge;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {
    static ImageView circle,dash_btn;
    static TextView tv_score;
    static ConstraintLayout area=null;
    static Timer theTIMER;
    static int score;
    static boolean caught;
    private static Handler timerHandler;
    /*
    ------------> x
    \
    \   .
    \     .
    \
    y
    */
    private class bearControl{

        TimerTask bear_mv;
        double lastOffX,lastOffY;
        boolean is_Running;
        ImageView im_bear;
        double width,height,limitX,limitY;
        double x,y;
        bearControl(){
            is_Running=false;
            lastOffX=0.0;lastOffY=0.0;
            im_bear=findViewById(R.id.imageBear);
            x=0.0;y=0.0;
            if(area==null)area=findViewById(R.id.gameArea);
            width= im_bear.getWidth();
            height=im_bear.getHeight();
            limitX=area.getWidth()-width;
            limitY=area.getHeight()-height;
            im_bear.setY((float)y);
            im_bear.setX((float)x);
            //Log.i("debug","Bear init "+x+","+y+","+width+","+height+",");

        }
        void Run(){
            if(!is_Running) {
                is_Running=true;
                bear_mv=new TimerTask() {
                    @Override
                    public void run() {if(caught)this.cancel();


                        x=x+lastOffX;
                        y=y+lastOffY;
                        if(x<0)x=0.0;
                        else if(x>limitX)x=limitX;
                        if(y<0)y=0.0;
                        else if(y>limitY)y=limitY;
                       // Log.i("debug","bear "+x+","+y+","+limitX+","+limitY);
                        timerHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                im_bear.setX((float) x);im_bear.setY((float) y);
                            }
                        });
                    }
                };
                theTIMER.scheduleAtFixedRate(bear_mv,10,50);
            }
        }
        void Stop(){
            if(is_Running)
                bear_mv.cancel();
            is_Running=false;
        }
        void Dash(){
            x+=4*lastOffX;y+=4*lastOffY;
            if(x<0)x=0.0;
            else if(x>limitX)x=limitX;
            if(y<0)y=0.0;
            else if(y>limitY)y=limitY;
            // Log.i("debug","bear "+x+","+y+","+limitX+","+limitY);
            timerHandler.post(new Runnable() {
                @Override
                public void run() {
                    im_bear.setX((float) x);im_bear.setY((float) y);
                }
            });
        }

    }
    static bearControl BEAR;
    private class lionControl{
        int leftTime;
        TimerTask lion_mv;
        final int waitTime=10;
        double x,y,speed,dx,dy;
        final int ds=2;
        double width,height,limitX,limitY;
        ImageView im_lion;
        lionControl(){
            im_lion=findViewById(R.id.imageLion);
            speed=20;
            leftTime=0;
            double ang=Math.random()*Math.PI*0.5;
            dx=speed*Math.sin(ang);
            dy=speed*Math.cos(ang);
            width=im_lion.getWidth();
            height=im_lion.getHeight();
            limitX=area.getWidth()-width;
            limitY=area.getHeight()-height;
            im_lion.setX((float) (x=limitX));
            im_lion.setY((float) (y=limitY));
            //Log.i("debug","Lion init "+x+","+y+","+width+","+height+",");

        }
        void Run(){
            lion_mv=new TimerTask() {
                @Override
                public void run() {if(caught)this.cancel();

                    if(leftTime>0){
                        leftTime--;return;
                    }
                    x=x+dx;
                    y=y+dy;
                    if(x<0.0){
                        x=0.0;leftTime=waitTime;speed+=ds;
                        double angle=(Math.random()*179.0+1.0)/180.0*Math.PI;
                        dx=speed*Math.sin(angle);
                        dy=speed*Math.cos(angle);
                    }
                    else if(x>limitX){
                        x=limitX;leftTime=waitTime;speed+=ds;
                        double angle=(Math.random()*179.0+1.0)/180.0*Math.PI;
                        dx=-speed*Math.sin(angle);
                        dy=speed*Math.cos(angle);
                    }
                    else if(y<0.0){
                        y=0.0;leftTime=waitTime;speed+=ds;
                        double angle=(Math.random()*179.0+1.0)/180.0*Math.PI;
                        dx=speed*Math.cos(angle);
                        dy=speed*Math.sin(angle);
                    }
                    else if(y>limitY){
                        y=limitY;leftTime=waitTime;speed+=ds;
                        double angle=(Math.random()*179.0+1.0)/180.0*Math.PI;
                        dx=speed*Math.cos(angle);
                        dy=-speed*Math.sin(angle);
                    }
                    timerHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            im_lion.setX((float)x);
                            im_lion.setY((float)y);
                        }
                    });

                }
            };
            theTIMER.scheduleAtFixedRate(lion_mv,10,40);
        }
        void Stop(){
            lion_mv.cancel();
        }
    }
    static lionControl LION;
    private class CircleTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view,MotionEvent me){
            if(me.getAction()==MotionEvent.ACTION_UP){
                if(BEAR.is_Running)
                    BEAR.Stop();
            }
            else {
                if(caught)return true;
                double tx=me.getX(),ty=me.getY(),ox,oy,r,dx,dy,len;
                //Log.i("debug","touch "+tx+" "+ty);
                int wid=view.getWidth(),het=view.getHeight();
                ox=wid/2.0;oy=het/2.0;
                r=wid/2.0;
                dx=tx-ox;
                dy=ty-oy;
                len=Math.sqrt(dx*dx+dy*dy);
                if(len>r) {
                    dx=dx*r/len;
                    dy=dy*r/len;
                    len=r;
                }

                BEAR.lastOffX=dx/5.0;
                BEAR.lastOffY=dy/5.0;

                if(!BEAR.is_Running)BEAR.Run();
            }
            return true;
        }
    }
    private void getReady(){
        BEAR=new bearControl();
        LION=new lionControl();
        TimerTask scTask=new TimerTask(){
            @Override
            public void run(){
                if (caught)this.cancel();
                score+=(1+(int)LION.speed/30);
                timerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tv_score.setText("your score: "+score);
                    }
                });
            }
        };
        theTIMER.scheduleAtFixedRate(scTask,50,500);

        LION.Run();
        TimerTask ckTask=new TimerTask() {
            @Override
            public void run() {
                double bx=BEAR.x,
                        by=BEAR.y,
                        bw=BEAR.width,
                        lx=LION.x,
                        ly=LION.y,
                        lw=LION.width;
                double dx,dy,d,p;
                dx=bx-lx;dy=by-ly;
                d=Math.sqrt(dx*dx+dy*dy);
                p=lw/2+bw/2;
                if(d<p){
                    BEAR.Stop();
                    LION.Stop();
                    theTIMER.cancel();
                    if(!caught){
                        caught=true;
                        Intent gbn=new Intent(GameActivity.this,ScoreActivity.class);
                        gbn.putExtra("score",""+score);
                        startActivity(gbn);
                        finish();
                        this.cancel();
                    }
                }
            }
        };
        theTIMER.scheduleAtFixedRate(ckTask,10,60);
        circle.setOnTouchListener(new CircleTouchListener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        score=0;caught=false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        theTIMER=new Timer();
        timerHandler=new Handler();

        circle=(ImageView) findViewById(R.id.circle);
        dash_btn=(ImageView)findViewById(R.id.dashBtn) ;
        area=(ConstraintLayout) findViewById(R.id.gameArea);
        tv_score=(TextView)findViewById(R.id.score);
        ConstraintLayout hover=findViewById(R.id.hover);
        hover.bringToFront();

    }
    public void hoverClick(View view){
        view.setVisibility(View.GONE);
        getReady();
    }
    public void dash(View view){
        if (caught)return;
        dash_btn.setClickable(false);
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                timerHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        dash_btn.setClickable(true);
                    }
                });

            }
        };
        theTIMER.schedule(task,5000);
        BEAR.Dash();
    }
}
