package nivida.com.amulyamica;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LaminateList extends AppCompatActivity implements Serializable, CustomLaminateListAdapter.OnViewClickListener {
    AppPrefs app;
    ListView listView;
    CustomLaminateListAdapter customLaminateListAdapter;
    ArrayList<Bean_LaminateList> bean_laminateLists=new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson="";
    ArrayList<String> array_image;
    ProgressBar progressBar;
    String json;
    String laminate_type_ID,user_id;
    int page_id=1;
    int maxPage=1;
    TextView cate1;
    String jsonLAminate="";
    Bean_Categories bean_categories;
    ArrayList<String> arrayjson = new ArrayList<String>();
    public static ArrayList<String> WishList =new ArrayList<String>();
    String pro_id = new String();
    ArrayList<String> favItemList=new ArrayList<>();
    boolean click=false;

    View footerLoading;
    boolean isFirstTime=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laminate_list);
        favItemList.clear();
        setActionBar();
        Intent intent=getIntent();
        bean_categories=(Bean_Categories) intent.getSerializableExtra("category");

        footerLoading=getLayoutInflater().inflate(R.layout.list_footer_loading,null);

        app=new AppPrefs(LaminateList.this);
        app.setPage("list");
        app.setDescp_page("list");
        laminate_type_ID=app.getCategoryid();
        user_id=app.getUserid();
        fetchid();
        Log.e("USER ID",""+user_id);
        new get_Product().execute();


    }

    private void fetchid() {
        cate1=(TextView)findViewById(R.id.cate);
        Log.e("name category",""+cate1);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        listView = (ListView) findViewById(R.id.listview_laminate);
        customLaminateListAdapter=new CustomLaminateListAdapter(LaminateList.this, bean_laminateLists , LaminateList.this,array_image,favItemList,this);
        listView.setAdapter(customLaminateListAdapter);
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
        ImageView img_virtualRoom = (ImageView) mCustomView.findViewById(R.id.img_virtualRoom);
        ImageView img_search=(ImageView)mCustomView.findViewById(R.id.img_search);
        ImageView img_fav=(ImageView)mCustomView.findViewById(R.id.img_fav);

        img_search.setVisibility(View.VISIBLE);
        img_fav.setVisibility(View.VISIBLE);
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
                Intent i = new Intent(getApplicationContext(), FavouriteList.class);
                app = new AppPrefs(getApplicationContext());
                app.setfav("list");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Search.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        image_drawer.setImageResource(R.drawable.back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(LaminateList.this);
                app.setCategoryid("");
                Intent i = new Intent(LaminateList.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onViewClickSetFavorite(String laminateID,String lamninate_type_name,String laminate_name,String design_no, boolean isFavourite) {

    }

    @Override
    public void onShowInquiryDialog(final String laminateID,final String laminate_type_name,final String laminate_name,final String design_no) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.layout_dialog_inquiry,null);
        builder.setView(view);
        final EditText edt_firstName,edt_lastName,edt_emailID,edt_mobileno,edt_message;
        final Button btn_submit,btn_cancel;
        final TextView subj;

        edt_firstName = (EditText) view.findViewById(R.id.edt_firstName);
        edt_lastName = (EditText)  view.findViewById(R.id.edt_lastName);
        edt_emailID = (EditText)  view.findViewById(R.id.edt_emailID);
        edt_mobileno = (EditText)  view.findViewById(R.id.edt_mobileno);
        edt_message = (EditText)  view.findViewById(R.id.edt_message);
        btn_submit=(Button)view.findViewById(R.id.btn_submit);
        btn_cancel=(Button)view.findViewById(R.id.btn_cancel);
        subj=(TextView)view.findViewById(R.id.subj);

        final AlertDialog dialogB=builder.create();

        dialogB.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                subj.setText("Subject : \nInquiry for "+laminate_type_name+" "+laminate_name+"-"+design_no);

                btn_submit.setOnClickListener(new View.OnClickListener() {
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
                            pairList.add(new BasicNameValuePair("laminate_id",laminateID));
                            pairList.add(new BasicNameValuePair("first_name",firstName));
                            pairList.add(new BasicNameValuePair("last_name",lastName));
                            pairList.add(new BasicNameValuePair("email",emailID));
                            pairList.add(new BasicNameValuePair("contact_no",mobileNum));
                            pairList.add(new BasicNameValuePair("message",message));

                            new SendInquiry(pairList).execute();
                            dialogB.dismiss();

                        }
                    }
                });

                btn_cancel.setOnClickListener(new View.OnClickListener() {
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
            dialog=new Custom_ProgressDialog(LaminateList.this,"");
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

    public class get_Product extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        protected void onPreExecute()
        {
            super.onPreExecute();

            if(isFirstTime){
                bean_laminateLists.clear();
                loadingView = new Custom_ProgressDialog(
                        LaminateList.this, "");

                loadingView.setCancelable(false);
                loadingView.show();
            }
            else {
                listView.addFooterView(footerLoading);
            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                parameters.add(new BasicNameValuePair("laminate_type_id", laminate_type_ID));

                parameters.add(new BasicNameValuePair("page", ""+page_id));

                parameters.add(new BasicNameValuePair("user_id", user_id));

                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/Laminate/App_Get_laminate_by_type",ServiceHandler.POST,parameters);

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

            boolean isSuccess=false;

            try {

                if (result_1==null || result_1.isEmpty()) {
                    Globals.CustomToast(LaminateList.this, "Server Error", getLayoutInflater());

                } else {
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(LaminateList.this,""+Message, getLayoutInflater());

                    } else {
                        String Message = jObj.getString("message");
                        isSuccess=true;
                        //Globals.CustomToast(LaminateList.this,"Available Laminates", getLayoutInflater());

                        JSONArray dataArray=jObj.getJSONArray("data");
                        maxPage=jObj.getInt("total_page_count");
                        JSONArray favList=jObj.getJSONArray("favourite_list");

                        Log.e("WishList",favList.toString());

                        favItemList=new ArrayList<>();

                        for(int i=0; i<favList.length(); i++){
                            favItemList.add(String.valueOf(favList.get(i)));
                        }

                        for(int i=0; i<dataArray.length(); i++){
                            JSONObject subObject=dataArray.getJSONObject(i);
                            Bean_LaminateList beanLam=new Bean_LaminateList();
                            JSONObject laminateobj=subObject.getJSONObject("Laminate");
                            beanLam.setLaminate_id(laminateobj.getString("id"));
                            beanLam.setFinishTypeId(laminateobj.getString("finish_type_id"));
                            beanLam.setLaminateTypeId(laminateobj.getString("laminate_type_id"));
                            beanLam.setLaminateName(laminateobj.getString("design_name"));
                            beanLam.setDesignName(laminateobj.getString("design_name"));
                            beanLam.setDesignNo(laminateobj.getString("design_no"));
                            beanLam.setDescription(laminateobj.getString("description"));
                            //beanLam.setTechnicalSpecification(laminateobj.getString("technical_spec"));
                            beanLam.setSize(laminateobj.getString("size"));
                            beanLam.setLaminateImage(laminateobj.getString("image"));
                            beanLam.setSortOrder(laminateobj.getString("sort_order"));

                            if(favItemList.contains(beanLam.getLaminate_id())){
                                beanLam.setFavourite_laminate(true);
                            }
                            else
                            {
                                beanLam.setFavourite_laminate(false);
                            }

                            JSONObject laminateType=subObject.getJSONObject("LaminateType");
                            beanLam.setCategoryName(laminateType.getString("name"));

                            JSONObject finishType=subObject.getJSONObject("FinishType");
                            beanLam.setFinishTypeName(finishType.getString("name"));


                            bean_laminateLists.add(beanLam);

                            if(laminateType.getString("name").toString().equalsIgnoreCase("") || laminateType.getString("name").toString().equalsIgnoreCase("null")){
                                String titles[] = "Category".split(" ");

                                String newTitle = "";

                                for (int k = 0; k < titles.length; k++) {
                                    if (k == 0)
                                        newTitle += "<font color=#666666>" + titles[k] + " </font>";
                                    else
                                        newTitle += "<font color=#e77716>" + titles[k] + " </font>";
                                }

                                cate1.setText(Html.fromHtml(newTitle));
                            }else {

                                String titles[] = laminateType.getString("name").split(" ");

                                String newTitle = "";

                                for (int k = 0; k < titles.length; k++) {
                                    if (k == 0)
                                        newTitle += "<font color=#666666>" + titles[k] + " </font>";
                                    else
                                        newTitle += "<font color=#e77716>" + titles[k] + " </font>";
                                }

                                cate1.setText(Html.fromHtml(newTitle));
                            }

                        }

                        customLaminateListAdapter.setFavList(favItemList);
                        customLaminateListAdapter.notifyDataSetChanged();

                    }
                }}


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                loadingView.dismiss();
                listView.removeFooterView(footerLoading);
            }

            if(page_id<maxPage && isSuccess){
                isFirstTime=false;
                page_id++;
                new get_Product().execute();
            }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
