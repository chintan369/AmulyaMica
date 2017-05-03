package nivida.com.amulyamica;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class  E_GalleryScreen extends AppCompatActivity {
    LinearLayout door11,door01,door02,door03,door04,door10;
    AppPrefs app;
    GridView gridView;
    CustomEgalleryGrid customEgalleryGrid;
    ArrayList<Bean_Egallery> bean_egalleries=new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson="";
    ArrayList<String> array_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e__gallery_screen);
        fetchid();
        setActionBar();
        new GetEgalleryList().execute();
    }

    private void fetchid()  {
        gridView = (GridView) findViewById(R.id.gridView1);
        customEgalleryGrid=new CustomEgalleryGrid(E_GalleryScreen.this, bean_egalleries , E_GalleryScreen.this,array_image);
        gridView.setAdapter(customEgalleryGrid);
//        door11=(LinearLayout)findViewById(R.id.door11);
//        door01=(LinearLayout)findViewById(R.id.door01);
//        door02=(LinearLayout)findViewById(R.id.door02);
//        door03=(LinearLayout)findViewById(R.id.door03);
//        door04=(LinearLayout)findViewById(R.id.door04);
//        door10=(LinearLayout)findViewById(R.id.door10);

//        door11.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(E_GalleryScreen.this, SubScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            }
//        });
//        door02.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(E_GalleryScreen.this, SubScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            }
//        });
//        door01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(E_GalleryScreen.this, SubScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            }
//        });
//        door03.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(E_GalleryScreen.this, SubScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            }
//        });
//        door04.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(E_GalleryScreen.this, SubScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            }
//        });
//        door10.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(E_GalleryScreen.this, SubScreen.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(i);
//                finish();
//            }
//        });

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

        image_drawer.setImageResource(R.drawable.back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // app =new AppPrefs(E_GalleryScreen.this);
                Intent i = new Intent(E_GalleryScreen.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(E_GalleryScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public class GetEgalleryList extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(E_GalleryScreen.this, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");


                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/EgalleryCategory/App_EgalleryCategory_List",ServiceHandler.POST);

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
                Log.e("ccccc","cccccc\n"+result_1);

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Log.e("dddd","dddddd");
                    Globals.CustomToast(E_GalleryScreen.this, "Server Error", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(E_GalleryScreen.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        String Message1 = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        Globals.CustomToast(E_GalleryScreen.this,""+Message1, getLayoutInflater());
                        loadingView.dismiss();

                        JSONArray dataArray=jObj.getJSONArray("data");
                        bean_egalleries.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            Log.e("hhhh","hhhhh");
                            JSONObject subObject=dataArray.getJSONObject(i);
                            JSONObject egalleryObj=subObject.getJSONObject("EgalleryCategory");
                            Bean_Egallery beanEgallery=new Bean_Egallery();
                            beanEgallery.setEgallery_id(egalleryObj.getString("id"));
                            Log.e("CATEGORYYYYYYID",""+beanEgallery.getEgallery_id());
                            beanEgallery.setEgallery_name(egalleryObj.getString("name"));
                            //app.setCategory_name(egalleryObj.getString("name"));
                            Log.e("CATEGORYYYYYYNAMEEEE",""+beanEgallery.getEgallery_name());
                            beanEgallery.setEgallery_image(egalleryObj.getString("image"));
                            Log.e("egallery image",""+beanEgallery.getEgallery_image());
//                            beanEgallery.setG_laminate_id(egalleryObj.getString("laminate_type_id"));
//                            beanEgallery.setG_finish_id(egalleryObj.getString("finish_type_id"));

                            bean_egalleries.add(beanEgallery);

                        }
                        customEgalleryGrid.notifyDataSetChanged();
                        Log.e("Size",bean_egalleries.size()+"");
                    }
                }}


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                loadingView.dismiss();
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(E_GalleryScreen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
