package nivida.com.amulyamica;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static nivida.com.amulyamica.Globals.LINK;
import static nivida.com.amulyamica.Globals.POST;

public class WhereToBuy extends AppCompatActivity implements Serializable {
    ListView listView;
    CustomStoreLayout customStoreLayout;
    ArrayList<Bean_Stores> beanStoresArrayList = new ArrayList<>();
    Spinner spn_state, spn_city, spn_area, spn_store;
    NetworkUtility networkUtility;
    Custom_ProgressDialog loadingView;
    ArrayList<String> areaid = new ArrayList<String>();
    ArrayList<String> areaname = new ArrayList<String>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    ArrayList<String> storesid = new ArrayList<String>();
    ArrayList<String> storesname = new ArrayList<String>();
    String position_area = new String();
    String position_state = new String();
    String position_areaname = new String();
    String position_statename = new String();
    String position_city = new String();
    String position_cityname = new String();
    String json = new String();
    String profile_array = "";
    String address_array = "";

    ArrayAdapter<String> adapter_state;
    ArrayAdapter<String> adapter_city;
    ArrayAdapter<String> adapter_area;
    ArrayAdapter<String> adapter_store;


    AppPrefs prefs;
    LinearLayout checkout, submit;
    Bean_Categories categories;
    String pincode = "";
    EditText pincode_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_where_to_buy);
        prefs = new AppPrefs(getApplicationContext());
        networkUtility = new NetworkUtility(getApplicationContext());
        Intent intent = getIntent();
        //bean_laminateList=(Bean_LaminateList) intent.getSerializableExtra("laminate");
        categories = (Bean_Categories) intent.getSerializableExtra("category");
        fetchID();
        setActionBar();
    }


    private void fetchID() {
        spn_state = (Spinner) findViewById(R.id.spn_state);
        spn_city = (Spinner) findViewById(R.id.spn_city);
        spn_area = (Spinner) findViewById(R.id.spn_area);
        spn_store = (Spinner) findViewById(R.id.spn_store);
        checkout = (LinearLayout) findViewById(R.id.checkout);
        pincode_txt = (EditText) findViewById(R.id.pincode_edit);
        //submit=(LinearLayout)findViewById(R.id.submit);

        adapter_state = new ArrayAdapter<String>(WhereToBuy.this, R.layout.custom_spinner_wheretobuy, statename);
        adapter_state.setDropDownViewResource(R.layout.custom_spinner_wheretobuy);
        spn_state.setAdapter(adapter_state);

        adapter_city = new ArrayAdapter<String>(WhereToBuy.this, R.layout.custom_spinner_wheretobuy, cityname);
        adapter_city.setDropDownViewResource(R.layout.custom_spinner_wheretobuy);
        spn_city.setAdapter(adapter_city);

        adapter_area = new ArrayAdapter<String>(WhereToBuy.this, R.layout.custom_spinner_wheretobuy, areaname);
        adapter_area.setDropDownViewResource(R.layout.custom_spinner_wheretobuy);
        spn_area.setAdapter(adapter_area);

        adapter_store = new ArrayAdapter<String>(WhereToBuy.this, R.layout.custom_spinner_wheretobuy, storesname);
        adapter_store.setDropDownViewResource(R.layout.custom_spinner_wheretobuy);
        spn_store.setAdapter(adapter_store);

        spn_state.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pincode_txt.getWindowToken(), 0);
                return false;
            }
        });

        spn_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pincode_txt.getWindowToken(), 0);
                return false;
            }
        });
        spn_area.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pincode_txt.getWindowToken(), 0);
                return false;
            }
        });


        if (networkUtility.isOnline()) {
            new send_state_Data().execute();
        } else {
            Globals.CustomToast(WhereToBuy.this, "Please check your Internet Connection", getLayoutInflater());
        }

        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                beanStoresArrayList.clear();
                customStoreLayout.notifyDataSetChanged();
                if (position == 0) {
                    cityid.clear();
                    cityname.clear();
                    adapter_city.notifyDataSetChanged();

                    areaid.clear();
                    areaname.clear();
                    adapter_area.notifyDataSetChanged();


                    storesid.clear();
                    storesname.clear();
                    adapter_store.clear();
                } else {
                    if (networkUtility.isOnline()) {
                        new send_city_Data().execute();
                    } else {
                        Toast.makeText(WhereToBuy.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spn_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_city = cityid.get(position);
                Log.e("abc", "xyz" + position_state);
                position_cityname = cityname.get(position);
                beanStoresArrayList.clear();
                customStoreLayout.notifyDataSetChanged();
                if (position == 0) {
                    areaid.clear();
                    areaname.clear();
                    adapter_area.clear();

                    storesid.clear();
                    storesname.clear();
                    adapter_store.notifyDataSetChanged();
                } else if (spn_city.getSelectedItemPosition() != 0) {
                    Log.e("pos stat ", "" + position_city);
                    new send_area_Data().execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        spn_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_area = areaid.get(position);
                position_areaname = areaname.get(position);
                beanStoresArrayList.clear();
                customStoreLayout.notifyDataSetChanged();
                if (position == 0) {
                    storesid.clear();
                    storesname.clear();
                    adapter_store.notifyDataSetChanged();
                } else {
                    new GetStoreNames(areaid.get(position)).execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beanStoresArrayList.clear();

                int areaPosition = spn_area.getSelectedItemPosition();
                int statePosition = spn_state.getSelectedItemPosition();
                int cityPosition = spn_city.getSelectedItemPosition();
                int storePosition = spn_store.getSelectedItemPosition();
                //pincode=pincode_txt.getText().toString().trim();

                if (statePosition == 0) {
                    Globals.CustomToast(WhereToBuy.this, "Please Select Your State", getLayoutInflater());
                    spn_state.setFocusable(true);
                } else if (cityPosition == 0) {
                    Globals.CustomToast(WhereToBuy.this, "Please Select Your City", getLayoutInflater());
                    spn_city.setFocusable(true);
                } else if (areaPosition == 0) {
                    Globals.CustomToast(WhereToBuy.this, "Please Select Your Area", getLayoutInflater());
                    spn_area.setFocusable(true);
                } else if (storePosition == 0) {
                    Globals.Toast(getApplicationContext(), "Please Select Store");
                } else {

                    List<NameValuePair> pairList = new ArrayList<NameValuePair>();

                    pairList.add(new BasicNameValuePair("area_id", areaid.get(areaPosition)));
                    pairList.add(new BasicNameValuePair("city_id", cityid.get(cityPosition)));
                    pairList.add(new BasicNameValuePair("state_id", stateid.get(statePosition)));
                    pairList.add(new BasicNameValuePair("store_id", storesid.get(storePosition)));

                    new Address_Data(pairList).execute();

                    System.out.println("" + profile_array);

                }


                //submit.setVisibility(View.VISIBLE);
            }
        });


        listView = (ListView) findViewById(R.id.listview_stores);
        customStoreLayout = new CustomStoreLayout(WhereToBuy.this, beanStoresArrayList, WhereToBuy.this);
        listView.setAdapter(customStoreLayout);

    }

    public class Address_Data extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        List<NameValuePair> pairList = new ArrayList<>();

        public Address_Data(List<NameValuePair> pairList) {
            this.pairList = pairList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(WhereToBuy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {



                /*List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                //parameters.add(new BasicNameValuePair("personal_info",profile_array));

                parameters.add(new BasicNameValuePair("state_id",position_state));
                parameters.add(new BasicNameValuePair("city_id",position_city));
                parameters.add(new BasicNameValuePair("area_id",position_area));
                parameters.add(new BasicNameValuePair("pincode",pincode));


                Log.e("stores parameters","" + parameters);*/

                Log.e("Params",pairList.toString());


                json = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/Store/App_GetStore", ServiceHandler.POST, pairList);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("arrayregistration: " + json);
                return json;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error: " + e.toString());
            }

            return json;

        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Globals.CustomToast(WhereToBuy.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(WhereToBuy.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        Log.e("ffff", "fffff");

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        Globals.CustomToast(WhereToBuy.this, "Available Stores", getLayoutInflater());
                        loadingView.dismiss();

                        JSONArray dataArray = jObj.getJSONArray("data");
                        beanStoresArrayList.clear();

                        for (int i = 0; i < dataArray.length(); i++) {
                            Log.e("hhhh", "hhhhh");
                            JSONObject subObject = dataArray.getJSONObject(i);
                            Bean_Stores beanStores = new Bean_Stores();
                            beanStores.setStore_id(subObject.getString("id"));
                            beanStores.setBranch_name(subObject.getString("branch_name"));
                            Log.e("BRANch NAME", "" + beanStores.getBranch_name());
                            beanStores.setPincode(subObject.getString("pincode"));
                            beanStores.setAddress(subObject.getString("address"));
                            beanStores.setCity(subObject.getString("city"));
                            beanStores.setState(subObject.getString("state"));
                            beanStores.setLatitude(subObject.getString("latitude"));
                            beanStores.setLongitude(subObject.getString("longitude"));
                            beanStores.setContact_person(subObject.getString("contact_person"));
                            beanStores.setPhone(subObject.getString("phone"));
                            beanStores.setMobile(subObject.getString("mobile"));
                            beanStores.setEmail_id(subObject.getString("email_id"));
                            beanStores.setMapLink(subObject.getString("map_link"));

                            beanStoresArrayList.add(beanStores);

                        }
                        customStoreLayout.notifyDataSetChanged();
                        Log.e("Size", beanStoresArrayList.size() + "");

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }
        }
    }

    public class send_state_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        WhereToBuy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

//
        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("country_id", "1"));

                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/State/App_GetState", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            stateid.clear();
            statename.clear();
            statename.add("Select State");
            stateid.add("0");

            cityid.clear();
            cityname.clear();
            cityname.add("Select City");
            cityid.add("0");

            areaid.clear();
            areaname.clear();
            areaname.add("Select Area");
            areaid.add("0");

            try {

                //db = new DatabaseHandler(());
                Log.e("json :", "" + result_1);
                System.out.println(result_1);

                if (result_1 == null) {
                    Globals.CustomToast(WhereToBuy.this, "No connection", getLayoutInflater());
                } else if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Globals.CustomToast(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(WhereToBuy.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(WhereToBuy.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                statename.add(sname);
                                stateid.add(sId);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

            adapter_state.notifyDataSetChanged();
            adapter_city.notifyDataSetChanged();
            adapter_area.notifyDataSetChanged();

        }
    }

    public class send_city_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        WhereToBuy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("state_id", position_state));


                Log.e("cityparametere", "city" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/City/App_GetCity", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("arraycity: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            cityid.clear();
            cityname.clear();
            cityname.add("Select City");
            cityid.add("0");

            areaid.clear();
            areaname.clear();
            areaname.add("Select Area");
            areaid.add("0");
            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Globals.CustomToast(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(WhereToBuy.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);
                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Globals.CustomToast(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(WhereToBuy.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);
                            }

                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }


            adapter_city.notifyDataSetChanged();
            //adapter_area.notifyDataSetChanged();
            spn_city.setSelection(0);
           // spn_area.setSelection(0);

        }
    }

    public class send_area_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        WhereToBuy.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("city_id", position_city));


                Log.e("cityparametere", "city" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/Area/App_GetArea", ServiceHandler.POST, parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("arrayarea: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);

            areaid.clear();
            areaname.clear();
            areaname.add("Select Area");
            areaid.add("0");

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Globals.CustomToast(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(WhereToBuy.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Globals.CustomToast(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(WhereToBuy.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");

                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String aId = catObj.getString("id");
                                String aname = catObj.getString("name");

                                areaname.add(aname);
                                areaid.add(aId);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

            adapter_area.notifyDataSetChanged();

            spn_area.setSelection(0);

        }
    }

    private class GetStoreNames extends AsyncTask<Void, Void, String> {

        String areaId = "";

        public GetStoreNames(String areaId) {
            this.areaId = areaId;
            loadingView = new Custom_ProgressDialog(
                    WhereToBuy.this, "");

            loadingView.setCancelable(false);
            loadingView.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> pairList = new ArrayList<>();
            pairList.add(new BasicNameValuePair("area_id", areaId));

            String json = new ServiceHandler().makeServiceCall(LINK + "Store/App_Get_Store_Name", ServiceHandler.POST, pairList);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("JSON", "-- " + s);

            storesid.clear();
            storesname.clear();

            storesid.add("0");
            storesname.add("Select Store");

            if (s != null && !s.isEmpty()) {
                try {
                    JSONObject main = new JSONObject(s);

                    if (main.getBoolean("status")) {
                        JSONArray data = main.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++) {
                            JSONObject storeObj = data.getJSONObject(i);

                            Log.e("Store Obj",storeObj.toString());

                            storesid.add(storeObj.getString("id"));
                            storesname.add(storeObj.getString("store_name"));
                        }
                    } else {
                        Globals.Toast(getApplicationContext(), main.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception", e.getMessage());
                }
            }

            adapter_store.notifyDataSetChanged();

            loadingView.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        prefs = new AppPrefs(getApplicationContext());
        /*if(prefs.getPage().equalsIgnoreCase("list"))
        {
            Intent intent=new Intent(WhereToBuy.this,LaminateList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }
        if(prefs.getPage().equalsIgnoreCase("descp"))
        {
            Intent intent=new Intent(WhereToBuy.this,DescriptionPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }
        if(prefs.getPage().equalsIgnoreCase("fav"))
        {
            Intent intent=new Intent(WhereToBuy.this,FavouriteList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }
        if(prefs.getPage().equalsIgnoreCase("search"))
        {
            Intent intent=new Intent(WhereToBuy.this,Search.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }*/
        finish();

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
                prefs = new AppPrefs(getApplicationContext());
                /*if(prefs.getPage().equalsIgnoreCase("list"))
                {
                    Intent intent=new Intent(WhereToBuy.this,LaminateList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }
                if(prefs.getPage().equalsIgnoreCase("descp"))
                {
                    Intent intent=new Intent(WhereToBuy.this,DescriptionPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }
                if(prefs.getPage().equalsIgnoreCase("fav"))
                {
                    Intent intent=new Intent(WhereToBuy.this,FavouriteList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }*/
                finish();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                app = new AppPrefs(LaminateList.this);
//                app.setCategoryid("");
                Intent i = new Intent(WhereToBuy.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

}
