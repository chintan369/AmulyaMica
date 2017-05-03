package nivida.com.amulyamica;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText edt_fname,edt_lname,edt_emailid,edt_mobileno,edt_address,edt_pincode,edt_password,edt_confirmpassword;
    TextView textView;
    Spinner spn_state,spn_city,spn_area;
    Button btn_signup;
    ImageView back;
    Typeface tf;
    NetworkUtility networkUtility;
    Custom_ProgressDialog loadingView;
    ArrayList<String> areaid = new ArrayList<String>();
    ArrayList<String> areaname = new ArrayList<String>();
    ArrayList<String> stateid = new ArrayList<String>();
    ArrayList<String> statename = new ArrayList<String>();
    ArrayList<String> cityid = new ArrayList<String>();
    ArrayList<String> cityname = new ArrayList<String>();
    String position_area = new String();
    String position_state = new String();
    String position_areaname = new String();
    String position_statename = new String();
    String position_city = new String();
    String position_cityname = new String();
    String json = new String();
    String profile_array="";
    String address_array="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        tf= Typeface.createFromAsset(getAssets(), "fonts/Raleway-Regular.ttf");
        networkUtility=new NetworkUtility(getApplicationContext());
        fetchID();
    }
    private void fetchID() {
        textView=(TextView)findViewById(R.id.textView);
        edt_fname=(EditText)findViewById(R.id.edt_fname);
        edt_lname=(EditText)findViewById(R.id.edt_lname);
        edt_emailid=(EditText)findViewById(R.id.edt_emailid);
        edt_mobileno=(EditText)findViewById(R.id.edt_mobileno);
        edt_address=(EditText)findViewById(R.id.edt_address);
        edt_pincode=(EditText)findViewById(R.id.edt_pincode);
        edt_password=(EditText)findViewById(R.id.edt_password);
        edt_confirmpassword=(EditText)findViewById(R.id.edt_confirmpassword);
        spn_state=(Spinner)findViewById(R.id.spn_state);
        spn_city=(Spinner)findViewById(R.id.spn_city);
        spn_area=(Spinner)findViewById(R.id.spn_area);
        btn_signup=(Button)findViewById(R.id.btn_signup);
        back=(ImageView) findViewById(R.id.back);


        textView.setTypeface(tf);
        edt_fname.setTypeface(tf);
        btn_signup.setTypeface(tf);
        edt_lname.setTypeface(tf);
        edt_emailid.setTypeface(tf);
        edt_mobileno.setTypeface(tf);
        edt_address.setTypeface(tf);
        edt_pincode.setTypeface(tf);
        edt_password.setTypeface(tf);
        edt_confirmpassword.setTypeface(tf);

        if(networkUtility.isOnline()){
            new send_state_Data().execute();
        }
        else
        {
            Globals.CustomToast(SignUp.this, "Please check your Internet Connection", getLayoutInflater());
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this, Login.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        spn_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                // position1=""+position;
                position_state = stateid.get(position);
                position_statename = statename.get(position);
                if (position_state.equalsIgnoreCase("0")) {
//Globals.CustomToast(Add_Newuser_Address.this,"Please Select State",getLayoutInflater());
                } else {
                    if(networkUtility.isOnline())
                    {
                        new send_city_Data().execute();
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
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
                Log.e("abc","xyz"+position_state);
                position_cityname = cityname.get(position);
                if (position_city.equalsIgnoreCase("0")) {
                }
                else if(spn_city.getSelectedItemPosition()!=0) {
                    Log.e("pos stat ",""+position_city);
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
                if (position_area.equalsIgnoreCase("0")) {
//                    Globals.CustomToast(Add_Newuser_Address.this,"Please Select City",getLayoutInflater());
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(SignUp.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
//                String fname=edt_fname.getText().toString();
//                String lname=edt_lname.getText().toString();
//                String email=edt_emailid.getText().toString();
//                String mobile=edt_mobileno.getText().toString();
//                int areaPosition=spn_area.getSelectedItemPosition();
//                int statePosition=spn_state.getSelectedItemPosition();
//                int cityPosition=spn_city.getSelectedItemPosition();
//                String address=edt_address.getText().toString();
//                String pincode=edt_pincode.getText().toString();
//                String password=edt_password.getText().toString();
//                String confirmPassword=edt_confirmpassword.getText().toString();
//
//
//                if (fname.equals("")){
//                    Globals.CustomToast(SignUp.this, "Please Enter Your First Name", getLayoutInflater());
//
//                    edt_fname.setFocusable(true);
//                }
//                else if(lname.equals("")){
//                    Globals.CustomToast(SignUp.this, "Please Enter Your Last Name", getLayoutInflater());
//                    edt_lname.setFocusable(true);
//                }
//                else if(email.equals("")){
//                    Globals.CustomToast(SignUp.this, "Please Enter Your Email ID", getLayoutInflater());
//                    edt_emailid.setFocusable(true);
//                }
//                else if(!(isEmailValid(email))){
//                    Globals.CustomToast(SignUp.this, "Please Enter Valid Email ID", getLayoutInflater());
//                    edt_emailid.setFocusable(true);
//                }
//                else if(mobile.equals("")){
//                    Globals.CustomToast(SignUp.this, "Please Enter Your Mobile Number", getLayoutInflater());
//                    edt_mobileno.setFocusable(true);
//                }
//                else if(statePosition==0){
//                    Globals.CustomToast(SignUp.this, "Please Select Your State", getLayoutInflater());
//                    spn_state.setFocusable(true);
//                }
//                else if(cityPosition==0){
//                    Globals.CustomToast(SignUp.this, "Please Select Your City", getLayoutInflater());
//                    spn_city.setFocusable(true);
//                }
//                else if(areaPosition==0){
//                    Globals.CustomToast(SignUp.this, "Please Select Your Area", getLayoutInflater());
//                    spn_area.setFocusable(true);
//                }
//                else if(address.equals("")){
//                    Globals.CustomToast(SignUp.this, "Please Enter Your Address", getLayoutInflater());
//                    edt_address.setFocusable(true);
//                }
////                else if(pincode.equals("")){
////                    Globals.CustomToast(SignUpActivity.this, "Please Enter Your Area Pincode", getLayoutInflater());
////                    edt_pincode.setFocusable(true);
////                }
//                else if(password.equals("") || password.length()<6){
//                    Globals.CustomToast(SignUp.this, "Please Enter Your Password", getLayoutInflater());
//                    edt_password.setFocusable(true);
//                }
//                else if(!edt_confirmpassword.getText().toString().equals(edt_password.getText().toString())){
//                    Globals.CustomToast(SignUp.this, "Please Enter Correct Password", getLayoutInflater());
//                    edt_confirmpassword.setFocusable(true);
//                }
//                else {
//
//                    try{
//                        JSONArray jObjectMain1 = new JSONArray();
//                        JSONArray jObjectMain2= new JSONArray();
//
//                        for (int i = 0; i < 1; i++) {
//                            JSONObject jObjectData = new JSONObject();
//                            jObjectData.put("first_name", fname);
//                            jObjectData.put("last_name", lname);
//                            jObjectData.put("email_id", email);
//                            jObjectData.put("phone_no", mobile);
//                            jObjectData.put("password", password);
//
//                            jObjectMain1.put(jObjectData);
//                            profile_array = jObjectMain1.toString();
//                        }
//
//                        for (int j = 0; j < 1; j++) {
//
//                            JSONObject jObjectData1 = new JSONObject();
//                            jObjectData1.put("area_id", position_area);
//                            jObjectData1.put("state_id", position_state);
//                            jObjectData1.put("city_id", position_city);
//                            jObjectData1.put("address", address);
//                            jObjectData1.put("pincode", pincode);
//
//                            jObjectMain2.put(jObjectData1);
//                            address_array = jObjectMain1.toString();
//
//                            new Register_Data().execute();
//                        }
//
//                        System.out.println("" + profile_array);
//                    }
//                    catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                }

            }
        });

    }

    public class Register_Data extends AsyncTask<Void, Void, String> {

        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(SignUp.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }

        @Override
        protected String doInBackground(Void... params) {

            try {



                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("personal_info",profile_array));

                parameters.add(new BasicNameValuePair("address_info",address_array));

                Log.e("","" + profile_array);
                Log.e("","" + address_array);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_Registration", ServiceHandler.POST,parameters);
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
                    Globals.CustomToast(SignUp.this,"SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(SignUp.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(SignUp.this,""+Message, getLayoutInflater());
                        Intent i = new Intent(SignUp.this, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }

    }

    //http used

    public class send_state_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        SignUp.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {

                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("country_id", "1"));


                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/State/App_GetState", ServiceHandler.POST, parameters);
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

            try {

                //db = new DatabaseHandler(());
                Log.e("json :",""+result_1);
                System.out.println(result_1);

                if(result_1==null){
                    Globals.CustomToast(SignUp.this,"No connection", getLayoutInflater());
                }
                else if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Globals.CustomToast(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(SignUp.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                }
                else
                {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(SignUp.this, "" + Message,getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            statename.add("Select State");
                            stateid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String sId = catObj.getString("id");
                                String sname = catObj.getString("name");

                                statename.add(sname);
                                stateid.add(sId);


                                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(SignUp.this, R.layout.custom_spinner_state_country, statename);
                                adapter_state.setDropDownViewResource(R.layout.custom_spinner_state_country);
                                spn_state.setAdapter(adapter_state);

                            }
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    //http used

    public class send_city_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        SignUp.this, "");

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

                json = new ServiceHandler().makeServiceCall(Globals.server_link+ "amulya/City/App_GetCity", ServiceHandler.POST,parameters);
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

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Globals.CustomToast(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(SignUp.this, "SERVER ERRER",getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);
                    cityid.clear();
                    cityname.clear();
                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Globals.CustomToast(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(SignUp.this, "" + Message,getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            cityname.add("Select City");
                            cityid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String cId = catObj.getString("id");
                                String cname = catObj.getString("name");

                                cityname.add(cname);
                                cityid.add(cId);

                            }
                            ArrayAdapter<String> adapter_city = new ArrayAdapter<String>(SignUp.this, R.layout.custom_spinner_state_country, cityname);
                            adapter_city.setDropDownViewResource(R.layout.custom_spinner_state_country);
                            spn_city.setAdapter(adapter_city);
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    //http used

    public class send_area_Data extends AsyncTask<Void, Void, String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        SignUp.this, "");

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

                json = new ServiceHandler().makeServiceCall(Globals.server_link + "amulya/Area/App_GetArea", ServiceHandler.POST,parameters);
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

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    /*Globals.CustomToast(Business_Registration.this, "SERVER ERRER",
                            Toast.LENGTH_SHORT).show();*/
                    Globals.CustomToast(SignUp.this, "SERVER ERRER",getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);
                    cityid.clear();
                    cityname.clear();
                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        //Globals.CustomToast(Business_Registration.this,""+Message,Toast.LENGTH_LONG).show();
                        Globals.CustomToast(SignUp.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        JSONObject jobj = new JSONObject(result_1);

                        if (jobj != null) {
                            JSONArray categories = jObj.getJSONArray("data");
                            areaname.add("Select Area");
                            areaid.add("0");
                            for (int i = 0; i < categories.length(); i++) {
                                JSONObject catObj = (JSONObject) categories.get(i);
                                String aId = catObj.getString("id");
                                String aname = catObj.getString("name");

                                areaname.add(aname);
                                areaid.add(aId);

                            }
                            ArrayAdapter<String> adapter_area = new ArrayAdapter<String>(SignUp.this, R.layout.custom_spinner_state_country, areaname);
                            adapter_area.setDropDownViewResource(R.layout.custom_spinner_state_country);
                            spn_area.setAdapter(adapter_area);
                        }
                        loadingView.dismiss();

                    }
                }
            } catch (JSONException j) {
                j.printStackTrace();
            }

        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(SignUp.this,Login.class);
        startActivity(intent);
        finish();
    }

}
