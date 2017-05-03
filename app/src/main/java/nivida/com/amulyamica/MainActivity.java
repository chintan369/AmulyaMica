package nivida.com.amulyamica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

import static android.Manifest.permission.CALL_PHONE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle mDrawerToggle;
    TextView notification_unread;
    boolean isMdevice;
    boolean pstatus;
    int code=1;
    public static int display_width = 0;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    ImageSwitcher imageSwitcher;
    ArrayList<Integer> Categoryimg = new ArrayList<Integer>();
    ArrayList<Integer> offergrid = new ArrayList<Integer>();
    ArrayList<String> name = new ArrayList<String>();
    //ArrayList<Bean_Slider> slider_arra =new ArrayList<Bean_Slider>();
    ViewPager customviewpager;
    GridView gridView1, gridView_new;
    String[] perms = { android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE,CALL_PHONE };
    ImageView image_drawer;
    ImageView img_drwaer_header;
    NavigationView navigationView;
    LinearLayout l3;
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    TextView marqueetextview;
    LinearLayout lmain1, lmain2;
    ArrayList<Integer> list_data = new ArrayList<Integer>();
    ArrayList<Integer> sticker_pro = new ArrayList<Integer>();
    ArrayList<String> list_data_name = new ArrayList<String>();
    LinearLayout.LayoutParams params;
    EditText et_search;
    int count = 0;
    String json = new String();

    //ImageView img_a1,img_b1,img_c1;
    // TextView title,title_b,title_c;
    String aa,bb,cc;

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;


    private boolean mIntentInProgress;

    private boolean mSignInClicked;

//    private ConnectionResult mConnectionResult;
//
//    private GoogleApiClient mGoogleApiClient;

    private static MainActivity instance;

    AppPrefs app;
    GridView gridView;
    CustomGridAdapter customGridAdapter;
    ArrayList<Bean_Categories> bean_categoriesList=new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson="";
    ArrayList<String> array_image;

    TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();

        if(intent.getBooleanExtra("fromNotification",false)){
            android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle(intent.getStringExtra("title"));
            builder.setMessage(intent.getStringExtra("message"));
            builder.setPositiveButton("CLOSE",null);

            android.support.v7.app.AlertDialog dialog=builder.create();
            dialog.show();

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        fetchid();
        setActionBar();
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        app = new AppPrefs(MainActivity.this);
        app.setPage("main");
        if(!Globals.isConnectingToInternet1(MainActivity.this)){

            Globals.CustomToast(MainActivity.this,"No Internet Connection",getLayoutInflater());

        }else {

            new GetCategoryData().execute();
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        app = new AppPrefs(MainActivity.this);
        Log.e("loggged in",""+app.isLoggedin());
        if (app.isLoggedin()) {
            Menu menu = navigationView.getMenu();

            MenuItem bedMenuItem = menu.findItem(R.id.login);
            bedMenuItem.setVisible(false);

            MenuItem bed1MenuItem = menu.findItem(R.id.logout);
            bed1MenuItem.setVisible(true);

        } else {
            Menu menu = navigationView.getMenu();

            MenuItem bedMenuItem = menu.findItem(R.id.login);
            MenuItem bed1MenuItem = menu.findItem(R.id.logout);

            bedMenuItem.setVisible(true);
//            bedMenuItem.setTitle("Login");
//            bedMenuItem.setIcon(R.drawable.login);

            bed1MenuItem.setVisible(false);
        }

    }

    private void fetchid() {
        gridView = (GridView) findViewById(R.id.gridView1);
        txtTitle = (TextView) findViewById(R.id.cate);
        customGridAdapter=new CustomGridAdapter(MainActivity.this, bean_categoriesList , MainActivity.this,array_image);
        gridView.setAdapter(customGridAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);
        View mCustomView = mActionBar.getCustomView();
        image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
        img_home.setVisibility(View.GONE);
        ImageView img_search=(ImageView)mCustomView.findViewById(R.id.img_search);
        ImageView img_fav=(ImageView)mCustomView.findViewById(R.id.img_fav);
        ImageView img_virtualRoom=(ImageView)mCustomView.findViewById(R.id.img_virtualRoom);
        img_fav.setVisibility(View.VISIBLE);
        img_search.setVisibility(View.VISIBLE);
        img_virtualRoom.setVisibility(View.VISIBLE);

        img_virtualRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),VirtualRoomActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        img_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FavouriteList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Search.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    image_drawer.setImageResource(R.drawable.menuicon);

                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    image_drawer.setImageResource(R.drawable.back);
                }

                mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.drawable.menuicon, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
                {
                    public void onDrawerClosed(View view)
                    {
                        Log.d("drawerToggle", "Drawer closed");
                        super.onDrawerClosed(view);
                        image_drawer.setImageResource(R.drawable.menuicon);
                        invalidateOptionsMenu(); //Creates call to onPrepareOptionsMenu()
                    }
                    public void onDrawerOpened(View drawerView)
                    {
                        Log.d("drawerToggle", "Drawer opened");
                        super.onDrawerOpened(drawerView);
                        image_drawer.setImageResource(R.drawable.back);
//                        getActionBar().setTitle("NavigationDrawer");
                        invalidateOptionsMenu();
                    }
                };
                mDrawerLayout.setDrawerListener(mDrawerToggle);
            }
        });
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Are you sure want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            System.exit(0);
                            app.setLoggedin(false);
                        }
                    })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }



    public class GetCategoryData extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(MainActivity.this, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/LaminateType/App_get_laminate_category",ServiceHandler.POST);

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

                if (result_1==null || result_1.isEmpty()) {
                    Log.e("dddd","dddddd");
                    Globals.CustomToast(MainActivity.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,"Available Laminate Categories", getLayoutInflater());
                        loadingView.dismiss();

                        JSONArray dataArray=jObj.getJSONArray("data");
                        bean_categoriesList.clear();

                        for(int i=0; i<dataArray.length(); i++){
                            Log.e("hhhh","hhhhh");
                            JSONObject subObject=dataArray.getJSONObject(i);
                            Bean_Categories beanCat=new Bean_Categories();
                            beanCat.setCategory_id(subObject.getString("id"));
                            Log.e("CATEGORYYYYYYID",""+beanCat.getCategory_id());
                            beanCat.setCategory_name(subObject.getString("name"));
                            //app.setCategory_name(subObject.getString("name"));
                            Log.e("CATEGORYYYYYYNAMEEEE",""+beanCat.getCategory_name());
                            beanCat.setCategory_image(subObject.getString("image"));

                            bean_categoriesList.add(beanCat);

                        }
                        customGridAdapter.notifyDataSetChanged();
                        Log.e("Size",bean_categoriesList.size()+"");
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
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Home) {
            //Globals.CustomToast(getApplicationContext(),"HOME", getLayoutInflater());
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.e_gallery) {
            //Globals.CustomToast(getApplicationContext(),"Gallery", getLayoutInflater());
            Intent i = new Intent(MainActivity.this, VirtualRoomActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else if(id==R.id.aboutCompany)
        {
            Intent i = new Intent(MainActivity.this, AboutCompany.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

        else if(id==R.id.aboutDirectors)
        {
            Intent intent=new Intent(getApplicationContext(),ContactUSActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("pageID","8");
            startActivity(intent);
        }
        else if(id==R.id.categories)
        {
            Intent i = new Intent(MainActivity.this, DrawerSample.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else if (id == R.id.vision) {

            Intent i = new Intent(MainActivity.this, Vision.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.mission) {

            Intent i = new Intent(MainActivity.this, Mission.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else if (id == R.id.csrActivity) {
            Intent i = new Intent(MainActivity.this, DrawerSample.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);

        } else if (id == R.id.recentActivity) {
            Intent i = new Intent(MainActivity.this, DrawerSample.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }
        else if (id == R.id.logout) {
            //app = new AppPrefs(MainActivity.this);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    MainActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("Logout application?");
            // Setting Dialog Message
            alertDialog
                    .setMessage("Are you sure you want to logout?");

            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {

                            app = new AppPrefs(MainActivity.this);
                            app.setLoggedin(false);
                            app.setUserid("");
                           // if (apps.getUser_LoginWith().equalsIgnoreCase("0")) {
                                Intent i = new Intent(MainActivity.this, Login.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(i);
                                finish();
                           // }

                        }
                    });
            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();


        }
        else if (id == R.id.nav_share) {
            try {
                String shareBody = Globals.share;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            } catch (Exception e) {

            }
        }
        else if( id == R.id.contactus){
            Intent intent=new Intent(getApplicationContext(),ContactUSActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("pageID","10");
            startActivity(intent);
        }
        else if( id == R.id.newsevent){
            Intent intent=new Intent(getApplicationContext(),ContactUSActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("pageID","13");
            startActivity(intent);
        }
        else if( id == R.id.certificateawards){
            Intent intent=new Intent(getApplicationContext(),CertificatesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("pageID","16");
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

