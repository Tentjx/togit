package com.example.myview;

import java.io.File;

import com.example.fragment.PlayFragment;
import com.example.lrc.LrcReader;
import com.example.myplayer.MainActivity;
import com.example.service.PlayMusicService;
import com.example.vo.LrcUtil;
import com.example.vo.MyConstent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class LrcView extends View{
	
	public LrcReader lrcreader;
    private float mX;       //屏幕X轴的中点，此值固定，保持歌词在X中间显示  
    private float offsetY;      //歌词在Y轴上的偏移量，此值会根据歌词的滚动变小  
    private static boolean blLrc=false;  
    public String path;
    private float touchY;   //当触摸歌词View时，保存为当前触点的Y轴坐标  
    private float touchX;  
    private boolean blScrollView=false;  
    private int lrcIndex=0; //保存歌词TreeMap的下标  
    private  int SIZEWORD=25;//显示歌词文字的大小值  
    private  int INTERVAL=30;//歌词每行的间隔 
    private float downposition; //记录每次touch事件down的位置
    private int savelrcindex;
    Paint paint=new Paint();//画笔，用于画不是高亮的歌词  
    Paint paintHL=new Paint();  //画笔，用于画高亮的歌词，即当前唱到这句歌词  
      
    public LrcView(Context context){  
        super(context);  
        init();  
    }  
      
    public LrcView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();  
    }  
      
    public boolean hasLrc(String path){
    	File file=new File(path);
    	if(!file.exists()){
    		blLrc=false;
    	}else{
    		lrcreader=new LrcReader(path);
    		blLrc=true;
    	}
    	return blLrc;
    }
    
    /* (non-Javadoc) 
     * @see android.view.View#onDraw(android.graphics.Canvas) 
     */  
    @Override  
    protected void onDraw(Canvas canvas) {  
        if(blLrc){  
            paintHL.setTextSize(SIZEWORD);  
            paint.setTextSize(SIZEWORD);  
//            LyricObject temp=lrc_map.get(lrcIndex); 
            String temp=lrcreader.lyricslist.get(lrcIndex).lrctext;
            canvas.drawText(temp, mX, offsetY+(SIZEWORD+INTERVAL)*lrcIndex, paintHL);  
            // 画当前歌词之前的歌词  
            for(int i=lrcIndex-1;i>=(0>lrcIndex-10?0:lrcIndex-10);i--){  
                temp=lrcreader.lyricslist.get(i).lrctext;  
                if(offsetY+(SIZEWORD+INTERVAL)*i<0){  
                    break;  
                }  
                canvas.drawText(temp, mX, offsetY+(SIZEWORD+INTERVAL)*i, paint);  
            }  
            // 画当前歌词之后的歌词  
            for(int i=lrcIndex+1;i<(lrcreader.lyricslist.size()<lrcIndex+10?lrcreader.lyricslist.size():lrcIndex+10);
            i++){  
                temp=lrcreader.lyricslist.get(i).lrctext;  
                if(offsetY+(SIZEWORD+INTERVAL)*i>600){  
                    break;  
                }  
                canvas.drawText(temp, mX, offsetY+(SIZEWORD+INTERVAL)*i, paint);  
            }  
        }  
        else{  
            paint.setTextSize(25);  
            canvas.drawText("没有本地歌词", this.getWidth()/2, this.getHeight()/2, paint);  
        }  
        super.onDraw(canvas);  
    }  
  
    /* (non-Javadoc) 
     * @see android.view.View#onTouchEvent(android.view.MotionEvent) 
     */  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        // TODO Auto-generated method stub  
        System.out.println("bllll==="+blScrollView);  
        float tt=event.getY(); 
        if(!blLrc){  
            //return super.onTouchEvent(event);  
            return super.onTouchEvent(event);  
        }  
        switch(event.getAction()){  
        case MotionEvent.ACTION_DOWN:  
            touchX=event.getX();
            savelrcindex=lrcIndex;
            downposition=event.getRawY();
            break;  
        case MotionEvent.ACTION_MOVE:  
//            touchY=tt-touchY;             
//            offsetY=offsetY+touchY;
            PlayFragment.isscroll=false;
        	float decrease=event.getRawY()-downposition;
//        	int decindex=(int)(touchY/(SIZEWORD+INTERVAL));
        	lrcIndex=savelrcindex-(int)(event.getRawY()-downposition)/(SIZEWORD+INTERVAL);
//        	offsetY+=decindex*(SIZEWORD+INTERVAL);
//        	Log.i("LrcView onTouch", "downposition"+downposition+"\tgetRawY:"+event.getRawY()+"\t移动距离:"+decindex);
        	if(lrcIndex<0)
        		lrcIndex=0;
        	else if(lrcIndex>=lrcreader.lyricslist.size())
        		lrcIndex=lrcreader.lyricslist.size()-1;
//        	this.invalidate();
        	PlayFragment.updateLrc.post(PlayFragment.mUpdateResults);
        	offsetY=250-lrcIndex*(SIZEWORD+INTERVAL);
            break;  
        case MotionEvent.ACTION_UP: 
        	if(savelrcindex==lrcIndex){
        		PlayFragment.isscroll=true;
        		break;
        	}
        	Intent intent=new Intent();
			intent.putExtra("user_option", MyConstent.FAST_FORWARD);
			intent.putExtra("fast_forward", lrcreader.lyricslist.get(lrcIndex).lrctime);
			intent.setClass(getContext(), PlayMusicService.class);
			getContext().startService(intent);
			PlayFragment.isscroll=true;
//			draw(canvas);
//            blScrollView=false;  
            break;        
        }  
        touchY=tt;  
        return true;  
    }  
  
    public void init(){ 
    	SharedPreferences shared=getContext().getSharedPreferences("lrcstate", Activity.MODE_PRIVATE);
    	lrcIndex=shared.getInt("lrcindex", 0);
        offsetY=shared.getFloat("offsety",this.getHeight()/2);      
          
        paint=new Paint();  
        paint.setTextAlign(Paint.Align.CENTER);  
        paint.setColor(0x424242);  
        paint.setAntiAlias(true);  
        paint.setDither(true);  
        paint.setAlpha(180);  
          
          
        paintHL=new Paint();  
        paintHL.setTextAlign(Paint.Align.CENTER);  
        paintHL.setTextSize(30);
        paintHL.setColor(0x000000);  
        paintHL.setAntiAlias(true);  
        paintHL.setAlpha(255);  
    }  
      
    /** 
     * 根据歌词里面最长的那句来确定歌词字体的大小 
     */  
      
    public void SetTextSize(){  
        if(!blLrc){  
            return;  
        }  
//        int max=0;  
//        for(int i=1;i<lrcreader.lyricslist.size();i++){  
//            String lrcStrLength=lrcreader.lyricslist.get(i).lrctext;  
////            if(max<lrcStrLength.length()){  
////                max=lrcStrLength.length();  
////            }  
//            max=max<lrcStrLength.length()?lrcStrLength.length():max;
//        }  
//        SIZEWORD=this.getWidth()*3/(lrcreader.maxlenth*4);
        SIZEWORD=25;
      
    }  
      
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        mX = w * 0.5f;  
        super.onSizeChanged(w, h, oldw, oldh);  
    }  
      
    /** 
     *  歌词滚动的速度 
     *  
     * @return 返回歌词滚动的速度 
     */  
    public Float SpeedLrc(){  
        float speed=0;  
        if(offsetY+(SIZEWORD+INTERVAL)*lrcIndex>220){  
            speed=((offsetY+(SIZEWORD+INTERVAL)*lrcIndex-220)/20);  
  
        } else if(offsetY+(SIZEWORD+INTERVAL)*lrcIndex < 120){  
            Log.i("speed", "speed is too fast!!!");  
            speed = 0;  
        }  
//      if(speed<0.2){  
//          speed=0.2f;  
//      }  
        return speed;  
    }  
      
    /** 
     * 按当前的歌曲的播放时间，从歌词里面获得那一句 
     * @param time 当前歌曲的播放时间 
     * @return 返回当前歌词的索引值 
     */  
    public int SelectIndex(int time){  
        if(!blLrc){  
            return 0;  
        }
		if (PlayFragment.isscroll) {
			for (int i = 0; i < lrcreader.lyricslist.size(); i++) {
				LrcUtil temp = lrcreader.lyricslist.get(i);
				if (temp.lrctime < time) {
					continue;
				} else {
					lrcIndex = --i;
					break;
				}
			}
			return (lrcIndex = lrcIndex < 0 ? 0 : lrcIndex);
		}else
			return lrcIndex;
      
    }  
      
    /** 
     * 读取歌词文件 
     * @param file 歌词的路径 
     *  
     */  
    
      
    /** 
     * @return the blLrc 
     */  
    public static boolean isBlLrc() {  
        return blLrc;  
    }  
  
    /** 
     * @return the offsetY 
     */  
    public float getOffsetY() {  
        return offsetY;  
    }  
  
    /** 
     * @param offsetY the offsetY to set 
     */  
    public void setOffsetY(float offsetY) {  
        this.offsetY = offsetY;  
    }  
  
    /** 
     * @return 返回歌词文字的大小 
     */  
    public int getSIZEWORD() {  
        return SIZEWORD;  
    }  
  
    /** 
     * 设置歌词文字的大小 
     * @param sIZEWORD the sIZEWORD to set 
     */  
    public void setSIZEWORD(int sIZEWORD) {  
        SIZEWORD = sIZEWORD;  
    }

	@Override
	protected void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		SharedPreferences lrcpreferences=getContext().getSharedPreferences("lrcstate", Activity.MODE_PRIVATE);
		SharedPreferences.Editor lrcediter=lrcpreferences.edit();
		lrcediter.putInt("lrcindex", lrcIndex);
		lrcediter.putFloat("offsety", offsetY);
		lrcediter.commit();
		super.onDetachedFromWindow();
	}  

    
    
    
}
