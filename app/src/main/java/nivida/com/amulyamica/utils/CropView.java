package nivida.com.amulyamica.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import nivida.com.amulyamica.R;

/**
 * Created by Nivida new on 05-Jan-17.
 */

public class CropView extends RelativeLayout {

    int baseh;
    int basew;
    int basex;
    int basey;
    ImageView img_pointXStart;
    ImageView img_pointXEnd;
    ImageView img_pointYEnd;
    ImageView img_pointYStart;
    RelativeLayout cropLayout;
    Context context;
    boolean freeze = false;
    int h;
    int i;
    ImageView image;
    String imageUri;
    boolean isShadow;
    int iv;
    RelativeLayout layBg;
    RelativeLayout layGroup;
    RelativeLayout.LayoutParams layoutParams;
    public LayoutInflater mInflater;
    int margl;
    int margt;
    float opacity = 1.0F;
    Bitmap originalBitmap;
    int pivx;
    int pivy;
    int pos;
    Bitmap shadowBitmap;
    float startDegree;
    String[] v;

    float startXPointTopLeft = 0;
    float startYPointTopLeft = 0;
    float endXPointTopRight = 0;
    float endYPointTopRight = 0;

    float startXPointBottomLeft = 0;
    float startYPointBottomLeft = 0;
    float endXPointBottomRight = 0;
    float endYPointBottomRight = 0;

    Paint paint = new Paint();

    public CropView(Context context) {
        super(context);
        this.context = context;
        layGroup = this;
        cropLayout=this;

        paint.setColor(Color.BLUE);

        basex = 0;
        basey = 0;
        pivx = 0;
        pivy = 0;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.layout_crop_image_with_edges, this, true);
        layoutParams = new RelativeLayout.LayoutParams(250, 250);
        layGroup.setLayoutParams(layoutParams);
        img_pointXStart = (ImageView) findViewById(R.id.img_pointXStart);
        img_pointXEnd = (ImageView) findViewById(R.id.img_pointXEnd);
        img_pointYEnd = (ImageView) findViewById(R.id.img_pointYEnd);
        img_pointYStart = (ImageView) findViewById(R.id.img_pointYStart);

        setOnTouchListener(new View.OnTouchListener() {
            final GestureDetector gestureDetector = new GestureDetector(CropView.this.context,
                    new GestureDetector.SimpleOnGestureListener() {
                        public boolean onDoubleTap(MotionEvent paramAnonymous2MotionEvent) {
                            return false;
                        }
                    });

            public boolean onTouch(View paramAnonymousView, MotionEvent event) {
                if (!CropView.this.freeze) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layGroup.invalidate();
                            gestureDetector.onTouchEvent(event);

                            layGroup.performClick();
                            basex = ((int) (event.getRawX() - layoutParams.leftMargin));
                            basey = ((int) (event.getRawY() - layoutParams.topMargin));
                            break;
                        case MotionEvent.ACTION_MOVE:
                            int i = (int) event.getRawX();
                            int j = (int) event.getRawY();
                            layBg = ((RelativeLayout) getParent());
                            if ((i - basex > -(layGroup.getWidth() * 2 / 3))
                                    && (i - basex < layBg.getWidth() - layGroup.getWidth() / 3)) {
                                layoutParams.leftMargin = (i - basex);
                            }
                            if ((j - basey > -(layGroup.getHeight() * 2 / 3))
                                    && (j - basey < layBg.getHeight() - layGroup.getHeight() / 3)) {
                                layoutParams.topMargin = (j - basey);
                            }
                            layoutParams.rightMargin = -1000;
                            layoutParams.bottomMargin = -1000;
                            layGroup.setLayoutParams(layoutParams);
                            break;

                    }

                    return true;
                }
                return true;
            }
        });

        img_pointXStart.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startXPointTopLeft = event.getX();
                startYPointTopLeft = event.getY();

                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        layGroup.invalidate();
                        int i = (int) event.getRawX();
                        int j = (int) event.getRawY();
                        layBg = ((RelativeLayout) getParent());
                        if ((i - basex > -(img_pointXStart.getWidth() * 2 / 3))
                                && (i - basex < layBg.getWidth() - img_pointXStart.getWidth() / 3)) {
                            layoutParams.leftMargin = (i - basex);
                        }
                        if ((j - basey > -(img_pointXStart.getHeight() * 2 / 3))
                                && (j - basey < layBg.getHeight() - img_pointXStart.getHeight() / 3)) {
                            layoutParams.topMargin = (j - basey);
                        }
                        layoutParams.rightMargin = -1000;
                        layoutParams.bottomMargin = -1000;
                        img_pointXStart.setLayoutParams(layoutParams);
                        break;
                }

                layGroup.invalidate();
                return true;
            }
        });
    }

    public CropView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(startXPointTopLeft,startYPointTopLeft,endXPointTopRight,endYPointTopRight,paint);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CropView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
