package nivida.com.amulyamica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FavouriteList extends AppCompatActivity implements Custom_Favourite_Layout.OnViewClickListener {
    AppPrefs app;
    ListView listView;
    Custom_Favourite_Layout custom_favourite_layout;
    ArrayList<Bean_Favourite> bean_favourites=new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson="";
    String null_laminate="";
    ArrayList<String> array_image;
    ProgressBar progressBar;
    String json;
    String laminate_type_ID,user_id;
    int page_id=1;
    TextView cate1;
    String jsonLAminate="";
    LinearLayout noimagefound;

    View footerViewLoading;

    int page=1;
    //Bean_Categories bean_categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        setActionBar();

        app=new AppPrefs(FavouriteList.this);
        app.setPage("fav");
        app.setDescp_page("fav");
        //laminate_type_ID=app.ge();
        user_id=app.getUserid();

        fetchid();
        Log.e("USER ID",""+user_id);
        new GetFavouriteList().execute();
    }

    private void fetchid() {

        footerViewLoading=getLayoutInflater().inflate(R.layout.list_footer_loading,null);

        noimagefound=(LinearLayout)findViewById(R.id.nofav_layout);
        cate1=(TextView)findViewById(R.id.cate);
        String[] headerTitle=cate1.getText().toString().split(" ");

        String newTitle="";

        for(int k=0; k<headerTitle.length; k++){
            if(k==0)
                newTitle += "<font color=#666666>"+headerTitle[k]+" </font>";
            else
                newTitle += "<font color=#e77716>"+headerTitle[k]+" </font>";
        }

        cate1.setText(Html.fromHtml(newTitle));

        Log.e("name category",""+cate1);
//        progressBar=(ProgressBar)findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.listview_laminate);
        custom_favourite_layout=new Custom_Favourite_Layout(FavouriteList.this, bean_favourites, FavouriteList.this,array_image,this);
        listView.setAdapter(custom_favourite_layout);
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
                app =new AppPrefs(FavouriteList.this);
                onBackPressed();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(FavouriteList.this);
                app.setCategoryid("");
                Intent i = new Intent(FavouriteList.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onViewClickSetFavorite(String laminateID, boolean isFavourite) {

    }

    @Override
    public void onShowInquiryDialog(final String laminateID) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.layout_dialog_inquiry,null);
        builder.setView(view);
        final EditText edt_firstName,edt_lastName,edt_emailID,edt_mobileno,edt_message;

        edt_firstName = (EditText) view.findViewById(R.id.edt_firstName);
        edt_lastName = (EditText)  view.findViewById(R.id.edt_lastName);
        edt_emailID = (EditText)  view.findViewById(R.id.edt_emailID);
        edt_mobileno = (EditText)  view.findViewById(R.id.edt_mobileno);
        edt_message = (EditText)  view.findViewById(R.id.edt_message);

        builder.setPositiveButton("Submit",null);

        builder.setNegativeButton("Cancel", null);

        final AlertDialog dialogB=builder.create();

        dialogB.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positive=dialogB.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negative=dialogB.getButton(DialogInterface.BUTTON_NEGATIVE);

                positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String firstName=edt_firstName.getText().toString().trim();
                        String lastName=edt_lastName.getText().toString().trim();
                        String emailID=edt_emailID.getText().toString().trim();
                        String mobileNum=edt_mobileno.getText().toString().trim();
                        String message=edt_message.getText().toString().trim();

                        if(firstName.isEmpty()){
                            Globals.Toast(getApplicationContext(),"Please Enter First Name");
                        }
                        else if(lastName.isEmpty()){
                            Globals.Toast(getApplicationContext(),"Please Enter Last Name");
                        }
                        else if(emailID.isEmpty() || !Globals.validEmail(emailID)){
                            Globals.Toast(getApplicationContext(),"Please Enter Valid Email ID");
                        }
                        else if(mobileNum.isEmpty() || mobileNum.length()<10){
                            Globals.Toast(getApplicationContext(),"Please Enter Valid 10 Digit Mobile Number");
                        }
                        else if(message.isEmpty()){
                            Globals.Toast(getApplicationContext(),"Please Enter Your Message");
                        }
                        else {
                            List<NameValuePair> pairList=new ArrayList<NameValuePair>();
                            pairList.add(new BasicNameValuePair("first_name",firstName));
                            pairList.add(new BasicNameValuePair("last_name",lastName));
                            pairList.add(new BasicNameValuePair("email",emailID));
                            pairList.add(new BasicNameValuePair("contact_no",mobileNum));
                            pairList.add(new BasicNameValuePair("message",message));
                            pairList.add(new BasicNameValuePair("laminate_id",laminateID));

                            new SendInquiry(pairList).execute();
                            dialogB.dismiss();

                        }
                    }
                });

                negative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogB.dismiss();
                    }
                });
            }
        });

        dialogB.show();

    }

    public class SendInquiry extends AsyncTask<Void,Void,String>{

        List<NameValuePair> pairList=new ArrayList<>();
        Custom_ProgressDialog dialog;

        public SendInquiry(List<NameValuePair> pairList) {
            this.pairList = pairList;
            dialog=new Custom_ProgressDialog(FavouriteList.this,"");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            String json=new ServiceHandler().makeServiceCall(Globals.LINK+Globals.SENDINQUIRY,ServiceHandler.POST,pairList);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();

            if(s==null || s.isEmpty()){
                Globals.Toast(getApplicationContext(),"Server Error");
            }
            else {
                try {
                    JSONObject object=new JSONObject(s);

                    Globals.Toast(getApplicationContext(),object.getString("message"));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Exception",e.getMessage());
                }

            }
        }
    }

    public class GetFavouriteList extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        public GetFavouriteList() {
                loadingView = new Custom_ProgressDialog(
                        FavouriteList.this, "");

                loadingView.setCancelable(false);
                loadingView.show();

        }

        protected void onPreExecute()
        {
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //parameters.add(new BasicNameValuePair("laminate_id",null_laminate ));
                Log.e("NULLLLLL",""+null_laminate);

                //parameters.add(new BasicNameValuePair("page", ""+page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id));

                Log.e("user_id_main", "" + user_id);
                Log.e("laminatemainid", "" + laminate_type_ID);
                Log.e("page_id", "" + page_id);
                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_Favourite_List",ServiceHandler.POST,parameters);

                System.out.println("array: " + json);
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
            //progressBar.setVisibility(View.GONE);
            try {
                Log.e("ccccc","cccccc\n"+result_1);

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Log.e("dddd","dddddd");
                    Globals.CustomToast(FavouriteList.this, "Server Error", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(FavouriteList.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff", "fffff");

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(FavouriteList.this, "" + Message, getLayoutInflater());
                        loadingView.dismiss();


                        bean_favourites.clear();
                        if(jObj.has("data")){

                            JSONArray dataArray = jObj.getJSONArray("data");

                            if(dataArray.length()>0){
                                //bean_favourites.clear();
                                noimagefound.setVisibility(View.GONE);
                                listView.setVisibility(View.VISIBLE);
                                //listView.setVisibility(View.VISIBLE);

                                for (int i = 0; i < dataArray.length(); i++) {
                                    Log.e("hhhh", "hhhhh");
                                    JSONObject subObject = dataArray.getJSONObject(i);
                                    Bean_Favourite beanLam = new Bean_Favourite();
                                    JSONObject laminateobj = subObject.getJSONObject("Laminate");
                                    beanLam.setD_laminate_id(laminateobj.getString("id"));
                                    beanLam.setD_finishTypeId(laminateobj.getString("finish_type_id"));
                                    beanLam.setD_laminateTypeId(laminateobj.getString("laminate_type_id"));
                                    beanLam.setD_laminateName(laminateobj.getString("design_name"));
                                    beanLam.setD_designName(laminateobj.getString("design_name"));
                                    beanLam.setD_designNo(laminateobj.getString("design_no"));
                                    beanLam.setD_description(laminateobj.getString("description"));
                                    //beanLam.setD_technicalSpecification(laminateobj.getString("technical_spec"));
                                    beanLam.setD_size(laminateobj.getString("size"));
                                    beanLam.setD_laminateImage(laminateobj.getString("image"));
                                    beanLam.setD_sortOrder(laminateobj.getString("sort_order"));

                                    JSONObject finishType = subObject.getJSONObject("FinishType");
                                    beanLam.setD_finishTypeName(finishType.getString("name"));

                                    beanLam.setFavourite(true);

                                    bean_favourites.add(beanLam);
                                    //cate1.setText(laminateType.getString("name").toString());

                                }
                                custom_favourite_layout.notifyDataSetChanged();
                                Log.e("Size", bean_favourites.size() + "");
                            }
                            else {
                                //yes
                                noimagefound.setVisibility(View.VISIBLE);
                                listView.setVisibility(View.GONE);
                            }

                        }
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
        app = new AppPrefs(getApplicationContext());
        if(app.getfav().toString().equalsIgnoreCase("detail")){
            Intent i = new Intent(getApplicationContext(), DescriptionPage.class);
            app = new AppPrefs(getApplicationContext());
            app.setfav("");
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else if(app.getfav().toString().equalsIgnoreCase("list")) {
            Intent i = new Intent(getApplicationContext(), LaminateList.class);
            app = new AppPrefs(getApplicationContext());
            app.setfav("");
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }else if(app.getfav().toString().equalsIgnoreCase("")) {
            finish();
        }
    }
}
