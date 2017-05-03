package nivida.com.amulyamica;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.opengl.EGLDisplay;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.repackaged.retrofit_v1_9_0.retrofit.RestAdapter;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nivida.com.amulyamica.utils.CropView;
import nivida.com.amulyamica.utils.SimpleDrawingView;

public class EditingScreen extends AppCompatActivity implements Custom_Scroll_Grid_Laminate.OnClickLaminate {
    AppPrefs appPrefs;
    LinearLayout door, d1, d2, d3, d4;
    ImageView img1, img2, img3, img4, img_laminate;
    RelativeLayout frameSave;
    private ImageCacher imageCacher;
    String image = "image";
    String description = "desccription";
    TextView laminateName;
    GridView gridView;
    Custom_Scroll_Grid_Laminate custom_scroll_grid_laminate;
    ArrayList<Bean_Scroll_Laminate> bean_scroll_laminates = new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson = "";
    ArrayList<String> array_image;
    String subScreenID;
    String subCategoryID, scrollLaminateID;

    String sourceImageLink="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_screen);
        appPrefs = new AppPrefs(EditingScreen.this);
        subScreenID = appPrefs.getSubscreenid();
//        subCategoryID=appPrefs.getSubCategoryId();
        //  scrollLaminateID=appPrefs.getScrollLaminateId();
        fetchid();
        setActionBar();
        imageCacher = new ImageCacher(this, -1);
        new GetEditingScrollList().execute();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        ImageView img_save = (ImageView) mCustomView.findViewById(R.id.img_save);
        img_save.setVisibility(View.VISIBLE);
        ImageView img_order = (ImageView) mCustomView.findViewById(R.id.img_order);
        img_order.setVisibility(View.GONE);
        ImageView img_share = (ImageView) mCustomView.findViewById(R.id.img_share);
        img_share.setVisibility(View.VISIBLE);

        image_drawer.setImageResource(R.drawable.back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // app =new AppPrefs(EditingScreen.this);
                Intent i = new Intent(EditingScreen.this, SubScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //app = new AppPrefs(EditingScreen.this);
                //app.setCategoryid("");
                Intent i = new Intent(EditingScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        img_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                frameSave.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(frameSave.getDrawingCache());
                Log.e("bitmap", "" + bitmap);
                frameSave.setDrawingCacheEnabled(false);
                saveImage(bitmap);

            }
        });
        img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //app = new AppPrefs(EditingScreen.this);
                //app.setCategoryid("");
                Intent i = new Intent(EditingScreen.this, CartPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                //Globals.CustomToast(EditingScreen.this, "Saved Successfully", getLayoutInflater());
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameSave.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(frameSave.getDrawingCache());
                Log.e("bitmap", "" + bitmap);
                frameSave.setDrawingCacheEnabled(false);


                // Save this bitmap to a file.
                File cache = getApplicationContext().getExternalCacheDir();
                File sharefile = new File(cache, "toshare.png");
                try {
                    FileOutputStream out = new FileOutputStream(sharefile);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (IOException e) {

                }

                // Now send it out to share
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + sharefile));
                // share.putExtra(Intent.EXTRA_TEXT,"Greeto");
                try {
                    startActivity(Intent.createChooser(share, "Share photo"));
                } catch (Exception e) {

                }

            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }


    private void fetchid() {
        img_laminate = (ImageView) findViewById(R.id.img_Laminate);
        gridView = (GridView) findViewById(R.id.gridView1);
        custom_scroll_grid_laminate = new Custom_Scroll_Grid_Laminate(EditingScreen.this, bean_scroll_laminates, EditingScreen.this, array_image,this);
        gridView.setAdapter(custom_scroll_grid_laminate);
        door = (LinearLayout) findViewById(R.id.door);

        frameSave = (RelativeLayout) findViewById(R.id.frameLayout);
    }

    private void saveImage(Bitmap bitmap) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/AmulyaMica");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.i("File", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            Globals.CustomToast(EditingScreen.this, "Saved Successfully", getLayoutInflater());
            out.flush();
            out.close();
        } catch (Exception e) {
            Globals.CustomToast(EditingScreen.this, "Failed To Save Image", getLayoutInflater());
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[]{

                        file.getAbsolutePath()},

                null, new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri)

                    {


                    }

                });
    }

    @Override
    public void onLaminateClick(String laminateID, String subCategoryID) {
        new SetImageOfLaminate(laminateID,subCategoryID).execute();
    }

    public class GetEditingScrollList extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData = "";

        protected void onPreExecute() {
            Log.e("aaaa", "aaaa");
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(EditingScreen.this, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb", "bbbb");


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("egallery_sub_id", subScreenID));
                Log.e("ABCCC", "" + subScreenID);

                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/EgallerySubCategory/App_Get_Egallery_Laminate", ServiceHandler.POST, parameters);

                System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {
                Log.e("ccccc", "cccccc\n" + result_1);

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Log.e("dddd", "dddddd");
                    Globals.CustomToast(EditingScreen.this, "Server Error", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee", "eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status", "" + date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(EditingScreen.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff", "fffff");

                        String Message1 = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        Globals.CustomToast(EditingScreen.this, "" + Message1, getLayoutInflater());
                        loadingView.dismiss();

                        JSONObject dataObject = jObj.getJSONObject("data");
                        JSONObject mainImageObject = dataObject.getJSONObject("EgallerySubCategory");
                        Bean_Scroll_Laminate scrollLaminate = new Bean_Scroll_Laminate();
                        scrollLaminate.setEgallery_sub_id(mainImageObject.getString("id"));
                        scrollLaminate.setEgallery_sub_image(mainImageObject.getString("image"));
                        JSONArray scrollArray = dataObject.getJSONArray("EgalleryLaminate");

                        Picasso.with(getApplicationContext())
                                .load(Globals.IMAGELINK + mainImageObject.getString("image").toString())
                                .placeholder(R.drawable.mica_watermark)
                                .into(img_laminate);

                        sourceImageLink=Globals.IMAGELINK+mainImageObject.getString("image");

                        bean_scroll_laminates.clear();

                        for (int i = 0; i < scrollArray.length(); i++) {
                            Log.e("hhhh", "hhhhh");
                            JSONObject subObject = scrollArray.getJSONObject(i);
//                            JSONObject egalleryObj=subObject.getJSONObject("EgallerySubCategory");
                            Bean_Scroll_Laminate beanScrollLaminate = new Bean_Scroll_Laminate();
                            beanScrollLaminate.setScroll_laminate_id(subObject.getString("laminate_id"));
                            //Log.e("CATEGORYYYYYYID",""+beanSub.getEgallery_id());
                            beanScrollLaminate.setSubcategory_id(subObject.getString("subcategory_id"));

                            JSONObject laminateObject = subObject.getJSONObject("Laminate");
                            beanScrollLaminate.setScroll_laminate_name(laminateObject.getString("name"));
                            beanScrollLaminate.setScroll_laminate_image(laminateObject.getString("image"));
                            Log.e("IMAGE LINK 111", "" + laminateObject.getString("image"));

                            bean_scroll_laminates.add(beanScrollLaminate);

                            Log.e("IMAGE LINK", "" + mainImageObject.getString("image").toString());

                        }
                        custom_scroll_grid_laminate.notifyDataSetChanged();
                        Log.e("Size", bean_scroll_laminates.size() + "");
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("Exception", j.getMessage());
            } finally {
                loadingView.dismiss();
            }

        }
    }

    public class SetImageOfLaminate extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData = "";

        String laminate_ID = "0";
        String subCategory_ID = "0";
        int position = 0;

        Custom_ProgressDialog dialog;

        public SetImageOfLaminate(String laminate_ID, String subCategory_ID) {
            this.laminate_ID = laminate_ID;
            this.subCategory_ID = subCategory_ID;
            dialog=new Custom_ProgressDialog(EditingScreen.this,"");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }

        public SetImageOfLaminate(String laminate_ID, String subCategory_ID, int position) {
            this.laminate_ID = laminate_ID;
            this.subCategory_ID = subCategory_ID;
            this.position = position;
        }

        protected void onPreExecute() {
            Log.e("aaaa", "aaaa");
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(getApplicationContext(), "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb", "bbbb");
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));
                parameters.add(new BasicNameValuePair("sub_cat_id", subCategory_ID));


                Log.e("My Log", parameters.toString());


                //Log.e("laminatemainid", "" + laminate_ID);
                Log.e("", "" + parameters);

                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/EgallerySubCategory/App_Get_Egallery_Laminate_Image", ServiceHandler.POST, parameters);
                Log.e("222222222222222", Globals.server_link + "amulya/EgallerySubCategory/App_Get_Egallery_Laminate_Image");
                System.out.println("Data From Server " + jsonData);
                return jsonData;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception " + e.toString());

                return jsonData;
            }

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            dialog.dismiss();
            try {
                Log.e("ccccc", "cccccc\n" + result_1);

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Log.e("dddd", "dddddd");
                    Globals.CustomToast(getApplicationContext(), "Server Error", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee", "eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status", "" + date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(getApplicationContext(), "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff", "fffff");

                        String Message1 = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        //Globals.CustomToast(getApplicationContext(),""+Message1, getLayoutInflater());
                        loadingView.dismiss();

                        JSONArray dataArray = jObj.getJSONArray("data");

                        //bean_scroll_laminates.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            Log.e("hhhh", "hhhhh");
                            JSONObject subObject = dataArray.getJSONObject(i);
                            JSONObject imageObj = subObject.getJSONObject("EgalleryLaminate");
                            //Bean_Scroll_Laminate beanScrollLaminate=new Bean_Scroll_Laminate();
                            String lamID=imageObj.getString("id");
                            Log.e("clicked image id", "" + imageObj.getString("id"));
                            String laminateImage=imageObj.getString("laminate_image");
                            Log.e("clicked image pic", "" + imageObj.getString("laminate_image"));

                            Picasso.with(getApplicationContext())
                                    .load(Globals.IMAGELINK + laminateImage)
                                    .placeholder(R.drawable.mica_watermark)
                                    .error(R.drawable.error_img)
                                    .into(img_laminate);

                        }
                        //custom_scroll_grid_laminate.notifyDataSetChanged();
                        Log.e("Size", bean_scroll_laminates.size() + "");
                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
                Log.e("Exception", j.getMessage());
            } finally {
                loadingView.dismiss();
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditingScreen.this, SubScreen.class);
        startActivity(intent);
        finish();
    }

}
