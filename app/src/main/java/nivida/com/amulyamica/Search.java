package nivida.com.amulyamica;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
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

public class Search extends AppCompatActivity implements Custom_Search_List.OnViewClickListener {
    AutoCompleteTextView auto_search;
    AppPrefs app;
    ListView listView;
    //CustomLaminateListAdapter customLaminateListAdapter;
    Custom_Search_List custom_search_list;
    ArrayList<Bean_LaminateList> bean_laminateLists=new ArrayList<>();
    Custom_ProgressDialog loadingView;
    String jsonPerson="";
    ArrayList<String> array_image;
    ArrayList<String> fvArrayList;
    ProgressBar progressBar;
    String json;
    String laminate_type_ID,user_id;
    int page_id=1;
    TextView cate1;
    String jsonLAminate="";
    Bean_Categories bean_categories;
    ArrayList<String> array_search=new ArrayList<>();
    ArrayList<String> favItemList=new ArrayList<>();

    LinearLayout layout_nosearch;

    int page=1;
    int totalPage=1;
    boolean isFirstTime=true;

    ImageView image_search_pro;

    View footerLoading;

    String currentSearch="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        favItemList.clear();
        app=new AppPrefs(Search.this);
        app.setPage("search");
        app.setDescp_page("search");
        laminate_type_ID=app.getCategoryid();
        user_id=app.getUserid();

        footerLoading=getLayoutInflater().inflate(R.layout.list_footer_loading,null);

        fetchid();
        Log.e("USER ID",""+user_id);
        //new Get_Product_By_Search().execute();
        setActionBar();
        for(int i=0; i<bean_laminateLists.size(); i++){
            array_search.add(bean_laminateLists.get(i).designName);

        }
    }
    private void fetchid() {
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        layout_nosearch=(LinearLayout) findViewById(R.id.layout_nosearch);
        listView = (ListView) findViewById(R.id.listview_laminate);
        custom_search_list=new Custom_Search_List(Search.this, bean_laminateLists, Search.this,array_image, favItemList,this);
        listView.setAdapter(custom_search_list);

        auto_search=(AutoCompleteTextView)findViewById(R.id.Search_Actv);
        image_search_pro=(ImageView) findViewById(R.id.image_search_pro);

        auto_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    String name=auto_search.getText().toString().toLowerCase();
                    if(name.isEmpty()){
                        Globals.Toast(getApplicationContext(),"Please Enter Laminate Name, Code, Finish type...");
                    }
                    else {
                        isFirstTime=true;
                        currentSearch=name;
                        new Get_Product_By_Search(name).execute();
                    }

                    return true;
                }

                return false;
            }
        });

        auto_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name=auto_search.getText().toString().toLowerCase();
                //setSearchResult(name);

            }
        });
        Log.e("name category",""+cate1);

        image_search_pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=auto_search.getText().toString().toLowerCase();
                if(name.isEmpty()){
                    Globals.Toast(getApplicationContext(),"Please Enter Laminate Name, Code, Finish type...");
                }
                else {
                    isFirstTime=true;
                    currentSearch=name;
                    new Get_Product_By_Search(name).execute();
                }
            }
        });

    }

    private void setSearchResult(String name) {
        ArrayList<Bean_LaminateList> searchList=new ArrayList<Bean_LaminateList>();


        if(name.equalsIgnoreCase("")){
            searchList=bean_laminateLists;
            //listView.setVisibility(View.GONE);

        }
        else {
            //listView.setVisibility(View.VISIBLE);
            for(int i=0; i<bean_laminateLists.size(); i++){
                if(bean_laminateLists.get(i).getLaminateName().toLowerCase().startsWith(name) ||
                        bean_laminateLists.get(i).getFinishTypeName().toLowerCase().startsWith(name) ||
                        bean_laminateLists.get(i).getDesignNo().toLowerCase().startsWith(name) ||
                        bean_laminateLists.get(i).getCategoryName().toLowerCase().startsWith(name)){
                    searchList.add(bean_laminateLists.get(i));
                }
            }
        }

        if(searchList.size()>0){
            layout_nosearch.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        else {
            if(page<totalPage){
                listView.setVisibility(View.VISIBLE);
            }
            else {
                layout_nosearch.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
        }

        custom_search_list.updateData(searchList);
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
                app =new AppPrefs(Search.this);
                onBackPressed();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app = new AppPrefs(Search.this);
                app.setCategoryid("");
                Intent i = new Intent(Search.this, MainActivity.class);
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
        for(int i=0; i<bean_laminateLists.size(); i++){
            if(bean_laminateLists.get(i).getLaminate_id().equalsIgnoreCase(laminateID)){
                bean_laminateLists.get(i).setFavourite_laminate(isFavourite);
            }
        }
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
            dialog=new Custom_ProgressDialog(Search.this,"");
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

    public class Get_Product_By_Search extends AsyncTask<Void,Void,String>
    {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;

        String searchString="";

        public Get_Product_By_Search(String searchString) {
            this.searchString = searchString;
        }

        public Get_Product_By_Search() {
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            if(isFirstTime){

                    loadingView = new Custom_ProgressDialog(
                            Search.this, "");

                    loadingView.setCancelable(false);
                    loadingView.show();
                page=1;
                totalPage=1;

                    //progressBar.setVisibility(View.VISIBLE);
            }
            else {
                listView.addFooterView(footerLoading);
            }

        }


        @Override
        protected String doInBackground(Void... params) {

            try {


                List<NameValuePair> parameters = new ArrayList<NameValuePair>();

                //parameters.add(new BasicNameValuePair("search_data", laminate_type_ID));
//
//                parameters.add(new BasicNameValuePair("page", ""+page_id));
//
                if(!searchString.isEmpty())
                    parameters.add(new BasicNameValuePair("search_data",searchString));
                parameters.add(new BasicNameValuePair("user_id", user_id));
                parameters.add(new BasicNameValuePair("page",String.valueOf(page)));

                Log.e("user_id_main", "" + user_id);
                Log.e("laminatemainid", "" + laminate_type_ID);
                Log.e("page_id", "" + page_id);
                Log.e("", "" + parameters);

                json = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/Laminate/App_Search_Laminate",ServiceHandler.POST,parameters);

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
            progressBar.setVisibility(View.GONE);

            boolean isToSetFirst=false;

            if(isFirstTime){
                isToSetFirst=true;
                bean_laminateLists.clear();
                custom_search_list.notifyDataSetChanged();
            }
            isFirstTime=false;


            boolean isSuccess=false;
            try {
                Log.e("ccccc","cccccc\n"+result_1);

                //db = new DatabaseHandler(());
                System.out.println(result_1);

                if (result_1.equalsIgnoreCase("")
                        || (result_1.equalsIgnoreCase(""))) {
                    Log.e("dddd","dddddd");
                    Globals.CustomToast(Search.this, "Server Error", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(Search.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        isSuccess=true;
                        String Message = jObj.getString("message");
                        //Globals.CustomToast(Search.this,"Available Laminates", getLayoutInflater());
                        loadingView.dismiss();

                        JSONArray dataArray=jObj.getJSONArray("data");
                        totalPage=jObj.getInt("total_page_count");
                        JSONArray favList=jObj.getJSONArray("favourite_list");

                        favItemList=new ArrayList<>();

                        for(int i=0; i<favList.length(); i++){
                            favItemList.add(String.valueOf(favList.get(i)));
                        }


                        for(int i=0; i<dataArray.length(); i++){
                            Log.e("hhhh","hhhhh");
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
                                Log.e("123","11111");
                            }
                            else
                            {
                                beanLam.setFavourite_laminate(false);
                                Log.e("123","22222");
                            }


                            JSONObject laminateType=subObject.getJSONObject("LaminateType");
                            beanLam.setCategoryName(laminateType.getString("name"));

                            JSONObject finishType=subObject.getJSONObject("FinishType");
                            beanLam.setFinishTypeName(finishType.getString("name"));


                            if(searchString.equalsIgnoreCase(currentSearch))
                                bean_laminateLists.add(beanLam);
                            //cate1.setText(laminateType.getString("name").toString());

                        }
                        custom_search_list.setFavList(favItemList);
                        custom_search_list.notifyDataSetChanged();
                        Log.e("Size",bean_laminateLists.size()+"");

                        if(page<totalPage){
                            isFirstTime=false;
                            page++;
                            if(!searchString.isEmpty() && searchString.equalsIgnoreCase(currentSearch))
                                new Get_Product_By_Search(searchString).execute();
                        }
                    }
                }}


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                listView.removeFooterView(footerLoading);
                String name=auto_search.getText().toString().toLowerCase();
                if(!name.isEmpty()){
                    //setSearchResult(name);
                }
            }

            if(!isSuccess){
                layout_nosearch.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }
            else {
                layout_nosearch.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                if(isToSetFirst) listView.smoothScrollToPosition(0);
            }

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
