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
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SubScreen extends AppCompatActivity {
    LinearLayout door22;
    AppPrefs app;
    GridView gridView;
    CustomSubScreenLayout customSubScreenLayout;
    ArrayList<Bean_SubScreen> bean_subScreens=new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson="";
    ArrayList<String> array_image;
    String egalleryID;
TextView egalleryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_screen);
        app=new AppPrefs(SubScreen.this);
        egalleryID=app.getEgalleryid();
        fetchid();
        setActionBar();
        new GetSubScreenList().execute();
    }
    private void fetchid() {
        egalleryName=(TextView)findViewById(R.id.egallery_ID);
        gridView = (GridView) findViewById(R.id.gridView1);
        customSubScreenLayout=new CustomSubScreenLayout(SubScreen.this, bean_subScreens , SubScreen.this,array_image);
        gridView.setAdapter(customSubScreenLayout);
//        door22=(LinearLayout)findViewById(R.id.door22);
//        door22.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(SubScreen.this, EditingScreen.class);
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
                Intent i = new Intent(SubScreen.this,E_GalleryScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubScreen.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public class GetSubScreenList extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(SubScreen.this, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("egallery_id", egalleryID));


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/EgalleryCategory/App_Get_Egallary_Data",ServiceHandler.POST,parameters);

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
                    Globals.CustomToast(SubScreen.this, "Server Error", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(SubScreen.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        String Message1 = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        Globals.CustomToast(SubScreen.this,""+Message1, getLayoutInflater());
                        loadingView.dismiss();

                        JSONArray dataArray=jObj.getJSONArray("data");
                        bean_subScreens.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            Log.e("hhhh","hhhhh");
                            JSONObject subObject=dataArray.getJSONObject(i);
                            JSONObject egalleryObj=subObject.getJSONObject("EgallerySubCategory");
                            Bean_SubScreen beanSub=new Bean_SubScreen();
                            beanSub.setSub_id(egalleryObj.getString("id"));
                            //Log.e("CATEGORYYYYYYID",""+beanSub.getEgallery_id());
                            beanSub.setEgallery_id(egalleryObj.getString("egallery_cat_id"));
                            Log.e("CATEGORYYYYYYNAMEEEE",""+beanSub.getEgallery_id());
                            beanSub.setSub_images(egalleryObj.getString("image"));
                            Log.e("egallery image",""+beanSub.getSub_images());
                            beanSub.setSub_screen_name(egalleryObj.getString("name"));

                            bean_subScreens.add(beanSub);
                            egalleryName.setText(egalleryObj.getString("name"));
//                            app=new AppPrefs(getApplicationContext());
//                            egalleryName.setText(app.getCategory_name());

                        }
                        customSubScreenLayout.notifyDataSetChanged();
                        Log.e("Size",bean_subScreens.size()+"");
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
        Intent intent=new Intent(SubScreen.this,E_GalleryScreen.class);
        startActivity(intent);
        finish();
    }

}
