package guoxuebing.bawei.com.circlemultitouchdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by acer on 2017/4/12.
 */
public class MyCircle extends View {

    private Context context;

    private Paint paint;

    private int radius = 30;

    private int offsetX ;
    private int offsetY ;

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public MyCircle(Context context) {
        super(context);
    }

    public MyCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        paint = new Paint();
    }

    public MyCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAlpha(205);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        int centerX = getWidth()/2;
        int centerY = getHeight()/2;

        radius = dip2px(context,radius);

        canvas.drawCircle(centerX + offsetX,centerY + offsetY,radius,paint);
    }

    //px与Dp的相互转换，此方法从dp转换为px
    private int dip2px(Context context,float dpValue) {
        //获取屏幕的相对密度，density：密度
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5f);
    }

    private int mode;

    //dist:距离
    private float oldDist;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

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
                    zoom(newDist / oldDist);
                    oldDist = newDist;
                    invalidate();
                }
                break;
        }
        return true;
    }

    private void zoom(float f){
        radius = (int) (radius * f)/2;
    }

    private float spacing(MotionEvent event){
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x*x + y*y);
    }
}
