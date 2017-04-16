package guoxuebing.bawei.com.circlemultitouchdemo;

import android.content.Context;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by acer on 2017/4/12.
 *
 * MotionEvent.ACTION_DOWN：在第一个点被按下时触发
 * MotionEvent.ACTION_UP:当屏幕上唯一的点被放开时触发
 * MotionEvent.ACTION_POINTER_DOWN:当屏幕上已经有一个点被按住，此时再按下其他点时触发。
 * MotionEvent.ACTION_POINTER_UP:当屏幕上有多个点被按住，松开其中一个点时触发
 * （即非最后一个点被放开时）。
 * MotionEvent.ACTION_MOVE：当有点在屏幕上移动时触发。值得注意的是，由于它的灵敏度很高，
 * 而我们的手指又不可能完全静止（即使我们感觉不到移动，但其实我们的手指也在不停地抖动），
 * 所以实际的情况是，基本上只要有点在屏幕上，此事件就会一直不停地被触发。
 */
public class CircleMultiTouchListener implements View.OnTouchListener {

    private int mode;

    //dist:距离
    private float oldDist;

    private MyCircle myCircle;
    private int radius = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        myCircle = (MyCircle) v;
        if (radius == 0){
            radius = myCircle.getRadius();
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mode = 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                mode = 2;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == 2){
                    float newDist = spacing(event);
                    if (newDist > oldDist +1){
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                        /*if (radius <= 300 ){
                            myCircle.invalidate();
                        }*/
                        myCircle.invalidate();
                    }
                    if (newDist < oldDist -1){
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                        /*if (radius >= 50){
                            myCircle.invalidate();
                        }*/
                        myCircle.invalidate();
                    }
                }
                break;
        }
        return true;
    }

    private void zoom(float f){
        radius = (int) ((radius * f) /2);
        myCircle.setRadius(radius);
    }

    private float spacing(MotionEvent event){
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x*x + y*y);
    }
}
