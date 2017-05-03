package nivida.com.amulyamica;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/*import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;*/

import java.lang.reflect.Field;
import java.util.ArrayList;

import nivida.com.amulyamica.helpers.BitmapHelper;
import nivida.com.amulyamica.helpers.M;
import nivida.com.amulyamica.utils.CanvasView;

public class LaminateCuttingScreen extends AppCompatActivity {

    ImageView img_sourceImage;
    CanvasView canvas_draw;

    String souceImageLink="";

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laminate_cutting_screen);

        Intent intent=getIntent();
        souceImageLink=intent.getStringExtra("imageLink");

        System.loadLibrary("opencv_java3");

        fetchIDs();
    }

    /*private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i("OpenCV", "OpenCV loaded successfully");
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };*/

    @Override
    protected void onResume() {
        super.onResume();

        /*if (!OpenCVLoader.initDebug()) {
            Log.d("OpenCV", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d("OpenCV", "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }*/
    }

    private void fetchIDs() {
        img_sourceImage=(ImageView) findViewById(R.id.img_sourceImage);
        canvas_draw=(CanvasView) findViewById(R.id.canvas_draw1);

        canvas_draw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                findAndDraw(event.getX(),event.getY());

                return true;
            }
        });

        /*img_sourceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_sourceImage.setDrawingCacheEnabled(true);
                Bitmap bitmap= img_sourceImage.getDrawingCache();

                Mat rgba = new Mat();
                Utils.bitmapToMat(bitmap, rgba);

                Mat edges = new Mat(rgba.size(), CvType.CV_8UC1);
                Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4);
                Imgproc.Canny(edges, edges, 80, 100);

                // Don't do that at home or work it's for visualization purpose.
                Bitmap resultBitmap = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(edges, resultBitmap);
                img_sourceImage.setImageBitmap(resultBitmap);

                *//*Mat imgSource=new Mat(), imgCirclesOut=new Mat();

                Utils.bitmapToMat(resultBitmap,imgSource);

                Imgproc.cvtColor(imgSource,imgSource,Imgproc.COLOR_RGB2GRAY);
                Imgproc.GaussianBlur(imgSource,imgSource,new Size(9,9),2,2);
                Imgproc.HoughCircles(imgSource,imgCirclesOut,Imgproc.CV_HOUGH_GRADIENT,1,imgSource.rows()/8,200,100,0,0);

                float circles[]=new float[3];

                Log.e("Circle Counts","->"+imgCirclesOut.rows()+"--"+imgCirclesOut.cols());

                for(int i=0; i<imgCirclesOut.cols(); i++){
                    imgCirclesOut.get(0,i,circles);
                    Point center=new Point();
                    center.x=circles[0];
                    center.y=circles[1];
                    Imgproc.circle(imgSource,center,(int) circles[2],new Scalar(255,0,0,255),4);
                }

                Bitmap newBitmap=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                Utils.matToBitmap(imgSource,newBitmap);

                img_sourceImage.setImageBitmap(newBitmap);*//*
            }
        });

        Picasso.with(getApplicationContext())
                .load(souceImageLink)
                .placeholder(R.drawable.mica_watermark)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        img_sourceImage.setImageBitmap(bitmap);

                        img_sourceImage.setDrawingCacheEnabled(true);
                        //Bitmap bitmap= img_sourceImage.getDrawingCache();

                        Mat rgba = new Mat();
                        Utils.bitmapToMat(bitmap, rgba);

                        Mat edges = new Mat(rgba.size(), CvType.CV_8UC1);
                        Imgproc.cvtColor(rgba, edges, Imgproc.COLOR_RGB2GRAY, 4);
                        Imgproc.Canny(edges, edges, 80, 100);

                        // Don't do that at home or work it's for visualization purpose.
                        Bitmap resultBitmap = Bitmap.createBitmap(edges.cols(), edges.rows(), Bitmap.Config.ARGB_8888);
                        Utils.matToBitmap(edges, resultBitmap);
                        img_sourceImage.setImageBitmap(resultBitmap);

                        //Bitmap greyScale= M.createContrast(bitmap,100);
                        //img_sourceImage.setImageBitmap(greyScale);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });*/

    }

    private void findAndDraw(float x, float y) {
        /*Bitmap bitmap=img_sourceImage.getDrawingCache();

        ArrayList<Integer> xEdgePoints=new ArrayList<>();
        ArrayList<Integer> yEdgePoints=new ArrayList<>();

        Mat imageSource=new Mat();

        Utils.bitmapToMat(bitmap,imageSource);

        int[] centerPosition=new int[2];
        centerPosition[0]=(int)x;
        centerPosition[1]=(int)y;

        int bitmapWidth=bitmap.getWidth();
        int bitmapHeight=bitmap.getHeight();

        for(int i=centerPosition[0]; i>0; i--){
            if(bitmap.getPixel(i,centerPosition[1])== Color.WHITE){

                canvas_draw.startTouch((float)i,(float)centerPosition[1]);
                canvas_draw.invalidate();

                Toast.makeText(this, "("+i+","+centerPosition[1]+")", Toast.LENGTH_SHORT).show();
                int topNeighbourY=centerPosition[1]-1;
                int bottomNeighbourY=centerPosition[1]+1;

                if(topNeighbourY>0 && bitmap.getPixel(i,topNeighbourY)==Color.WHITE){
                    canvas_draw.moveTouch((float)i,(float)topNeighbourY);
                    canvas_draw.invalidate();
                    Toast.makeText(this, "("+i+","+topNeighbourY+")", Toast.LENGTH_SHORT).show();
                    for(int j=topNeighbourY; j>0; j--){
                        int leftNeighbourX=i-1;
                        int rightNeighnourX=i+1;

                        if(rightNeighnourX<bitmapWidth && bitmap.getPixel(rightNeighnourX,j)==Color.WHITE){
                            canvas_draw.moveTouch((float)rightNeighnourX,(float)j);
                            canvas_draw.invalidate();
                            Toast.makeText(this, "("+i+","+j+"),"+"("+rightNeighnourX+",", Toast.LENGTH_SHORT).show();

                            for(int k=rightNeighnourX; k<bitmapWidth; k++){

                                if(k<bitmapWidth && bitmap.getPixel(k,j)==Color.WHITE){
                                    canvas_draw.moveTouch((float)k,(float)j);
                                    canvas_draw.invalidate();
                                    int bottomNeighbourYFromTopEdge=j+1;

                                    if(bottomNeighbourYFromTopEdge<bitmapHeight && bitmap.getPixel(rightNeighnourX,bottomNeighbourYFromTopEdge)==Color.WHITE){
                                        canvas_draw.upTouch((float)rightNeighnourX,(float)bottomNeighbourYFromTopEdge);
                                        canvas_draw.invalidate();
                                        for(int l=bottomNeighbourYFromTopEdge; l<bitmapHeight; l++){
                                            if(l==centerPosition[1] && k==centerPosition[0]){
                                                Toast.makeText(this, "("+i+","+j+"),"+"("+k+","+l+")", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }*/
    }


}
