package nivida.com.amulyamica.utils;

/**
 * Created by prince on 7/21/2016.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    public Canvas mCanvas;
    public Path mPath;
    Context context;
    public Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;

    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();

    private ArrayList<Float> startXPoints=new ArrayList<>();
    private ArrayList<Float> startYPoints=new ArrayList<>();
    private ArrayList<Float> endXPoints=new ArrayList<>();
    private ArrayList<Float> endYPoints=new ArrayList<>();

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;

        // we set a new Path
        mPath = new Path();



        // and we set a new Paint with the desired attributes
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }



    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw the mPath with the mPaint on the canvas when onDraw
        canvas.drawPath(mPath, mPaint);

    }

    // when ACTION_DOWN start touch according to the x,y values
    public void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
        startXPoints.add(x);
        startYPoints.add(y);
    }

    // when ACTION_MOVE move touch according to the x,y values
    public void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        mPath.reset();
        invalidate();
    }

    // when ACTION_UP stop touch
    public void upTouch(float x, float y) {
        mPath.lineTo(mX, mY);
        endXPoints.add(x);
        endYPoints.add(y);
    }


    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                upTouch(x,y);
                invalidate();
                break;
        }
        return false;
    }

    public Path getPathPoints(){
        return mPath;
    }


}