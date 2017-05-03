package nivida.com.amulyamica;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Intent.CATEGORY_TEST;
import static com.android.volley.Request.Method.POST;

public class Login extends AppCompatActivity {
    Button btn_login,btn_signup,btn_submit,btn_reset;
    EditText edt_emailid,edt_mobileno,edt_otp;
    LinearLayout otp_layout;
    String email="",phone,otp="";
    boolean hasEmailChecked=false;
    boolean emailAvailable=false;
    boolean hasMobileChecked=false;
    boolean mobileavailable=false;
    String json,store_ref,store_userid;
    AppPrefs appPrefs;
    Custom_ProgressDialog loadingView;
    String REFERENCE,USERID;
    ProgressBar progressBar;
    String s_mobile = new String();
    String s_email = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fetchID();
    }

    private void fetchID() {
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        btn_submit=(Button) findViewById(R.id.btn_submit);
        btn_login=(Button) findViewById(R.id.btn_login);
        btn_reset=(Button) findViewById(R.id.btn_reset);
        btn_signup=(Button) findViewById(R.id.btn_signup);
        edt_emailid=(EditText) findViewById(R.id.edt_emailid);
        edt_mobileno=(EditText) findViewById(R.id.edt_mobileno);
        otp_layout=(LinearLayout)findViewById(R.id.otp_layout);
        edt_otp=(EditText) findViewById(R.id.edt_otp);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, SignUp.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });




        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_emailid.setText("");
                edt_mobileno.setText("");
                edt_otp.setText("");
                edt_mobileno.setEnabled(true);
                edt_emailid.setEnabled(true);
                btn_login.setVisibility(View.VISIBLE);
                otp_layout.setVisibility(View.GONE);
                btn_submit.setVisibility(View.GONE);
                btn_reset.setVisibility(View.GONE);
                edt_otp.setVisibility(View.GONE);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp = edt_otp.getText().toString().trim();
                if (edt_otp.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Login.this,"Please Enter OTP",getLayoutInflater());

                } else {


                    otp = edt_otp.getText().toString().trim();
                    new check_otp(Login.this,email,phone, REFERENCE, otp).execute();
                    //checkOTP();
                    Log.e("22222222", "" + REFERENCE);

                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=edt_emailid.getText().toString().trim();
                phone= edt_mobileno.getText().toString().trim();



                Log.e("1111","1111");
                if(email.equalsIgnoreCase("")){
                    Globals.CustomToast(getApplicationContext(), "Please enter Email Address", getLayoutInflater());
                    edt_emailid.requestFocus();
                }
                else if(!validateEmail1(email)){
                    Globals.CustomToast(getApplicationContext(), "Please enter Valid Email Address", getLayoutInflater());
                    edt_emailid.requestFocus();
                }

                else if (edt_mobileno.getText().toString().trim().equalsIgnoreCase("")) {
                    Globals.CustomToast(Login.this, "Please Enter Mobile Number", getLayoutInflater());
                    edt_mobileno.requestFocus();
                } else if (edt_mobileno.length() < 10 ) {
                    Globals.CustomToast(Login.this, "Please Enter Valid 10 Digit Mobile Number", getLayoutInflater());
                    edt_mobileno.requestFocus();
                }

                else
                {
                    Log.e("2222","2222");
                    if(Globals.isOnline(getApplicationContext())){
                        Log.e("3333","3333");
                        new Generate_OTP().execute();
                        //generateOTP();
                        //updatePassword();
                    }
                    else
                    {
                        Globals.CustomToast(Login.this, "Please check your Internet Connection", getLayoutInflater());
                    }

                }
            }
        });
    }

    public class Generate_OTP extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Login.this, "");

                loadingView.setCancelable(false);
                loadingView.show();
                //progressBar.setVisibility(View.VISIBLE);

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("4444","4444");


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("email_id", email));
                Log.e("EMAIL ID",""+email);
                parameters.add(new BasicNameValuePair("phone_no", phone));
                parameters.add(new BasicNameValuePair("device_id", FirebaseInstanceId.getInstance().getToken()));
                Log.e("MOBILE NO",""+phone);

                Log.e("","" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/HumanVerification/App_CheckUniqueAccount",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                Log.e("array: " ,""+ json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error1: " + e.toString());

                return json;

            }
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            Log.e("JSON login ",""+result_1);
            //progressBar.setVisibility(View.GONE);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);
                Log.e("5555",""+result_1);
                if (result_1==null
                        && (result_1.equalsIgnoreCase("")))
                {

                    Globals.CustomToast(Login.this, "SERVER ERROR", getLayoutInflater());
                    loadingView.dismiss();

                }
                else {
                    JSONObject jObj = new JSONObject(result_1);
                    Log.e("66666","66666");
                    Boolean date = jObj.getBoolean("status");

                    if (!date) {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {


                        int VERIFY = jObj.getInt("is_verified");
                        String Message = jObj.getString("message");
                        appPrefs=new AppPrefs(getApplicationContext());


                        if(VERIFY==1){
                            Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                            USERID=jObj.getString("user_id");
                            appPrefs.setUserid(USERID);
                            store_userid=appPrefs.getUserid();
                            Log.e("USERID PREFS",""+store_userid);
                            loadingView.dismiss();
                            appPrefs = new AppPrefs(Login.this);
                            appPrefs.setis_verify("1");
                            appPrefs.setLoggedin(true);
                            Log.e("umesh","bhai generate");
                            Log.e("VERIFICATION logg",""+appPrefs.isLoggedin());
                            Intent i = new Intent(Login.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                        else if(VERIFY==0){
                            Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                            REFERENCE=jObj.getString("ref_number");
                            appPrefs.setRefno(REFERENCE);
                            store_ref=appPrefs.getRefno();
                            Log.e("REFERENCE NO PREFS",""+store_ref);
                            Log.e("uuuuuuuuuuuu",""+REFERENCE);
                            loadingView.dismiss();
                            edt_otp.setVisibility(View.VISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);
                            btn_reset.setVisibility(View.VISIBLE);
                            otp_layout.setVisibility(View.VISIBLE);
                            edt_emailid.setEnabled(false);
                            edt_mobileno.setEnabled(false);
                            btn_login.setVisibility(View.GONE);

                        }
                    }
                }}

            catch(JSONException j){
                j.printStackTrace();
            }

        }
    }

    public class check_otp extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        //Custom_ProgressDialog loadingView;
        Activity activity;
        String email_ID;
        String mobile_ID;
        String ref_no;
        String otp_no;
        String jsonData="";
        //int position;

        public check_otp(Activity activity,String email_ID,String mobile_ID,String ref_no,String otp_no){
            this.activity=activity;
            this.email_ID=email_ID;
            this.mobile_ID=mobile_ID;
            this.ref_no=ref_no;
            this.otp_no=otp_no;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(
                        Login.this, "");
                loadingView.setCancelable(false);
                loadingView.show();

            } catch (Exception e) {

            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("email_id", email_ID));
                parameters.add(new BasicNameValuePair("phone_no", mobile_ID));
                parameters.add(new BasicNameValuePair("ref_number", ref_no));
                parameters.add(new BasicNameValuePair("otp", otp_no));


                Log.e("","" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/HumanVerification/App_VerifyOTP",ServiceHandler.POST,parameters);
                //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                System.out.println("array: " + json);
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error1: " , e.toString());

                return json;

            }
//            Log.e("result",result);


            //    return null;
        }

        @Override
        protected void onPostExecute(String result_1) {
            super.onPostExecute(result_1);
            //progressBar.setVisibility(View.GONE);

            try {

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Globals.CustomToast(Login.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    String date = jObj.getString("status");
                    if (date.equalsIgnoreCase("false")) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {

                        String Message = jObj.getString("message");
                        Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                        appPrefs = new AppPrefs(Login.this);
                        appPrefs.setis_verify("1");
                        USERID=jObj.getString("user_id");

                        Log.e("New UserID","---"+jObj.getString("user_id"));
                        appPrefs.setUserid(USERID);
                        store_userid=appPrefs.getUserid();
                        Log.e("USERID PREFS",""+store_userid);
                        appPrefs.setLoggedin(true);
                        Log.e("umesh","bhai check");
                        Log.e("VERIFICATION logg",""+appPrefs.isLoggedin());
                        Intent i = new Intent(Login.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();

                    }
                }}catch(JSONException j){
                j.printStackTrace();
            }

        }
    }
    private void updatePassword(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest strReq = new StringRequest(POST,Globals.server_link+"amulya/HumanVerification/App_CheckUniqueAccount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                Log.e("JSON",response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    Toast.makeText(Login.this,""+response.data+" "+response.statusCode,Toast.LENGTH_LONG).show();
                    //Additional cases
                }

                VolleyLog.d("Error: ", error.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email_id",email);
                params.put("phone_no",phone);
                Log.e("params",params.toString());
                return params;
            }
        };
        // Adding String request to request queue
        AmulyaApp.getInstance().addToRequestQueue(strReq);
    }

    public void generateOTP(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Globals.POST,Globals.server_link+"amulya/HumanVerification/App_CheckUniqueAccount" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Log.e("Json",response);
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")) {
                        int VERIFY = object.getInt("is_verified");
                        String Message = object.getString("message");
                        appPrefs=new AppPrefs(getApplicationContext());


                        if(VERIFY==1){
                            Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                            USERID=object.getString("user_id");
                            appPrefs.setUserid(USERID);
                            store_userid=appPrefs.getUserid();
                            Log.e("USERID PREFS",""+store_userid);
                            //loadingView.dismiss();
                            appPrefs = new AppPrefs(Login.this);
                            appPrefs.setis_verify("1");
                            appPrefs.setLoggedin(true);
                            Log.e("VERIFICATION logg",""+appPrefs.isLoggedin());
                            Intent i = new Intent(Login.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                        else if(VERIFY==0){
                            Globals.CustomToast(Login.this,""+Message, getLayoutInflater());
                            REFERENCE=object.getString("ref_number");
                            appPrefs.setRefno(REFERENCE);
                            store_ref=appPrefs.getRefno();
                            Log.e("REFERENCE NO PREFS",""+store_ref);


                            Log.e("uuuuuuuuuuuu",""+REFERENCE);
                            //loadingView.dismiss();
                            edt_otp.setVisibility(View.VISIBLE);
                            btn_submit.setVisibility(View.VISIBLE);
                            otp_layout.setVisibility(View.VISIBLE);

                            edt_emailid.setEnabled(false);
                            edt_mobileno.setEnabled(false);
                            btn_login.setVisibility(View.GONE);

                        }
                    } else {
                        //Toast(getApplicationContext(), object.getString("message"));
                        Globals.CustomToast(Login.this,object.getString("message"), getLayoutInflater());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Globals.CustomToast(Login.this,"Server Error", getLayoutInflater());
                    Log.e("Exception", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //serverError(getApplicationContext());
                Log.e("Exception",""+error.getMessage()+"");
                Globals.CustomToast(Login.this,"Server Error", getLayoutInflater());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email_id",email);
                params.put("phone_no", phone);
                Log.e("params",params.toString());
                return params;
            }
        };
        AmulyaApp.getInstance().addToRequestQueue(request);
    }

    public void checkOTP(){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(POST,Globals.server_link+"amulya/HumanVerification/App_VerifyOTP" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                Log.e("Json",response);
                try {
                    JSONObject object = new JSONObject(response);

                    if (object.getBoolean("status")) {
                        appPrefs = new AppPrefs(Login.this);
                        appPrefs.setis_verify("1");
                        USERID=object.getString("user_id");
                        appPrefs.setUserid(USERID);
                        store_userid=appPrefs.getUserid();
                        Log.e("USERID PREFS",""+store_userid);

                        appPrefs.setLoggedin(true);
                        Log.e("umesh","bhai check");
                        Log.e("VERIFICATION logg",""+appPrefs.isLoggedin());
                        Intent i = new Intent(Login.this, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        //Toast(getApplicationContext(), object.getString("message"));
                        Globals.CustomToast(Login.this,object.getString("message"), getLayoutInflater());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Globals.CustomToast(Login.this,"Server Error", getLayoutInflater());
                    Log.e("Exception", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                //serverError(getApplicationContext());
                Globals.CustomToast(Login.this,"Server Error", getLayoutInflater());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email_id",email);
                params.put("phone_no", phone);
                params.put("ref_number", REFERENCE);
                params.put("otp", otp);
                Log.e("params",params.toString());
                return params;
            }
        };
        AmulyaApp.getInstance().addToRequestQueue(request);
    }

    private boolean validateEmail1(String email_1) {
        // TODO Auto-generated method stub
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email_1);
        return matcher.matches();
    }
}
