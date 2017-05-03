package nivida.com.amulyamica;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Intent.ACTION_VIEW;
import static nivida.com.amulyamica.Globals.IMAGELINK;

public class DescriptionPage extends AppCompatActivity implements Serializable {
    private static final int MY_PERMISSION_WR_FILES = 3;
    ImageView desc_image;
    TextView deccription_text,
            sizetext,laminatenamedescp,thickness,txt_finish,txt_itemcode,edt_count, txt_designName,txt_laminateType;
    EditText edt_count_txt;
    ImageView increase,decrease;
    Button wheretobuy,buynow;
    Bean_LaminateList bean_laminateList;
    Bean_Categories categories;
    AppPrefs appPrefs;
    boolean click=false;
    ImageView fav,share,save,pdf;
    Custom_ProgressDialog loadingView;
    String laminate_ID,userid;
    ArrayList<Bean_Description> bean_description=new ArrayList<>();
    ArrayList<Bean_Favourite> bean_favourites=new ArrayList<>();
    String laminate_path,laminate_name,laminate_id,finish_type,design_name,design_no,design_code,pdf_path,laminate_type_name;
    ProgressDialog pDialog;
    final File myDir = new File("/sdcard/AmulyaMica/Gallery");
    //File myDir = new File("/sdcard/AmulyaMica/Photos");
    String filesToSend;
    ImageCacher imageCacher;
    String appName="AmulyaNivida";
    int current_position1=-1;
    String null_laminate="";
    String json;
    ArrayList<String> arrayjson = new ArrayList<String>();

    int minteger = 0;

    Spinner spn_laminateType;
    ArrayList<String> laminateTypeID=new ArrayList<>();
    ArrayList<String> finishName=new ArrayList<>();
    ArrayList<String> finishImage=new ArrayList<>();
    ArrayList<String> finishPDF=new ArrayList<>();

    ArrayAdapter<String> finishTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);
        setActionBar();

        appPrefs=new AppPrefs(DescriptionPage.this);
        appPrefs.setPage("descp");

        laminate_ID=appPrefs.getLaminateid();
        userid=appPrefs.getUserid();

        fetchid();
        imageCacher = new ImageCacher(this, -1);
        new Get_Description_Data().execute();

    }

    private void set_Image() {

        Log.e("uuuuuuuuuuuu","uuuuuuu");

        laminate_path = IMAGELINK+bean_description.get(0).getD_laminateImage();
        Log.e("Path",""+laminate_path);
        laminate_name = bean_description.get(0).getD_laminateName();
        Log.e("name",""+laminate_name);
        laminate_id=bean_description.get(0).getD_laminate_id();
        Log.e("id",""+laminate_id);
        finish_type=bean_description.get(0).getD_finishTypeName().toString();
        Log.e("finish_type",""+finish_type);
        design_name=bean_description.get(0).getD_designName().toString();
        Log.e("design_name",""+design_name);
       /* design_no=bean_description.get(0).getD_designNo();
        laminate_type_name=bean_description.get(0).getD_finishTypeName();*/
        design_code=bean_description.get(0).getD_designNo();
        Log.e("design_code",""+design_code);
        fetch_image_for_share(bean_description.get(0).getD_laminateImage());

    }

    private void fetchid() {
        increase=(ImageView)findViewById(R.id.increase);
        decrease=(ImageView)findViewById(R.id.decrease);
        edt_count_txt=(EditText)findViewById(R.id.edt_count);
//        edt_count=(TextView)findViewById(R.id.edt_count);
        //txt_finish=(TextView)findViewById(R.id.txt_finishtype);
        txt_itemcode=(TextView)findViewById(R.id.txt_itemcode);
        pdf=(ImageView)findViewById(R.id.img_pdf);
        save=(ImageView)findViewById(R.id.img_save);
        share=(ImageView)findViewById(R.id.share);
        fav=(ImageView)findViewById(R.id.fav);
        laminatenamedescp=(TextView)findViewById(R.id.laminatenamedescp);
        desc_image=(ImageView) findViewById(R.id.imageView7);
        desc_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Full_screen_image.class);
                i.putExtra("imagePaths",IMAGELINK+finishImage.get(spn_laminateType.getSelectedItemPosition()));
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        //desc_image.setDrawingCacheEnabled(true);

        deccription_text=(TextView)findViewById(R.id.description_text22);
        txt_designName=(TextView)findViewById(R.id.txt_designName);
        txt_laminateType=(TextView)findViewById(R.id.txt_laminateType);
        wheretobuy=(Button) findViewById(R.id.BTN_wheretobuy_list);
        buynow=(Button) findViewById(R.id.btn_buyonline);
        sizetext=(TextView)findViewById(R.id.textView2);
        thickness=(TextView)findViewById(R.id.thickness);

        spn_laminateType=(Spinner) findViewById(R.id.spn_laminateType);

        finishTypeAdapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.custom_spinner_wheretobuy,finishName);
        spn_laminateType.setAdapter(finishTypeAdapter);

        spn_laminateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Glide.with(getApplicationContext())
                        .load(IMAGELINK+finishImage.get(position))
                        .placeholder(R.drawable.img_loding)
                        .crossFade()
                        .into(desc_image);

                laminate_ID=laminateTypeID.get(position);

                if(finishPDF.get(position).isEmpty()){
                    pdf.setVisibility(View.GONE);
                }
                else {
                    pdf.setVisibility(View.VISIBLE);
                }

                if(arrayjson.contains(laminate_ID)){
                    click=true;
                    fav.setImageResource(R.drawable.heartfilled);
                }
                else {
                    click=false;
                    fav.setImageResource(R.drawable.heart_new);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInquiryDialog();
            }
        });

        wheretobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_count_txt.getText().toString().equals("")){
                    edt_count_txt.setText("1");
                    Globals.CustomToast(DescriptionPage.this, "Enter quantity please", getLayoutInflater());
                }
                else{
                    Intent i = new Intent(getApplicationContext(), WhereToBuy.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }


            }
        });

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(click)
                {
                    fav.setImageResource(R.drawable.heart_new);
                    click=false;
                    new DeleteFromFavourite().execute();
                }
                else
                {
                    fav.setImageResource(R.drawable.heartfilled);
                    click=true;
                    new AddToFavourite().execute();
                }
            }
        });



        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //set_Image();
                laminate_path = IMAGELINK+finishImage.get(spn_laminateType.getSelectedItemPosition());
                Log.e("Path",""+laminate_path);
                laminate_name = bean_description.get(0).getD_laminateName();
                Log.e("name",""+laminate_name);
                laminate_id=bean_description.get(0).getD_laminate_id();
                Log.e("id",""+laminate_id);
                finish_type=spn_laminateType.getSelectedItem().toString();
                Log.e("finish_type",""+finish_type);
                design_name= bean_description.get(0).getD_designName();
                Log.e("design_name",""+design_name);
       /* design_no=bean_description.get(0).getD_designNo();
        laminate_type_name=bean_description.get(0).getD_finishTypeName();*/
                design_code=bean_description.get(0).getD_designNo()+"_"+spn_laminateType.getSelectedItem().toString()+"_"+laminateTypeID.get(spn_laminateType.getSelectedItemPosition());
                Log.e("design_code",""+design_code);
                fetch_image_for_share(finishImage.get(spn_laminateType.getSelectedItemPosition()));
                Log.e("URVI","urvi");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                desc_image.setDrawingCacheEnabled(true);
                Bitmap bitmap=desc_image.getDrawingCache(true).copy(Bitmap.Config.RGB_565, false);
                desc_image.setDrawingCacheEnabled(false);
                desc_image.destroyDrawingCache();
                saveImage(bitmap);
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bean_description.size()>0){
                    Intent intent=new Intent(ACTION_VIEW);
                    intent.setData(Uri.parse(IMAGELINK+finishPDF.get(spn_laminateType.getSelectedItemPosition())));
                    intent.setPackage("com.android.chrome");
                    try{
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e){
                        intent.setPackage(null);
                        startActivity(intent);
                    }

                }
                else {
                    Globals.Toast(getApplicationContext(),"Sorry, File Not Available!");
                }
               /* new DownloadFileFromURL_a().execute();
                Log.e("URVI@@","1234567890");*/
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty=Integer.parseInt(edt_count_txt.getText().toString());
                edt_count_txt.setText(String.valueOf(qty + 1));
                edt_count_txt.setSelection(edt_count_txt.getText().toString().length());
            }
        });


        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty=Integer.parseInt(edt_count_txt.getText().toString());

                if(qty<=1){
                    Globals.CustomToast(DescriptionPage.this, "At leaset 1 quantity required to order.", getLayoutInflater());
                }
                else {
                    edt_count_txt.setText(String.valueOf(qty - 1));
                    edt_count_txt.setSelection(edt_count_txt.getText().toString().length());
                }
            }
        });

        edt_count_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(edt_count_txt.getText().toString().equals("")) {
                    // Do nothing here
                }
                else if(Integer.parseInt(edt_count_txt.getText().toString())==0){
                    edt_count_txt.setText("1");
                    edt_count_txt.setSelection(edt_count_txt.getText().toString().length());
                }
                else if(edt_count_txt.getText().toString().equals("0")){
                    edt_count_txt.setText("1");
                    edt_count_txt.setSelection(edt_count_txt.getText().toString().length());
                }
                else{
                    int qty=Integer.parseInt(edt_count_txt.getText().toString());
                }
            }
        });

        edt_count_txt.setSelection(edt_count_txt.getText().toString().length());

    }

    private void showInquiryDialog() {
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


        //builder.setPositiveButton("Submit",null);

       // builder.setNegativeButton("Cancel", null);

        final AlertDialog dialogB=builder.create();

        dialogB.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
             //   Button positive=dialogB.getButton(DialogInterface.BUTTON_POSITIVE);
               // Button negative=dialogB.getButton(DialogInterface.BUTTON_NEGATIVE);
                /*laminate_name = bean_description.get(0).getD_laminateName();
                design_no=bean_description.get(0).getD_designNo();
                laminate_type_name=bean_description.get(0).getD_finishTypeName();*/
                subj.setText("Subject : \nInquiry for "+bean_description.get(0).getD_finishTypeName()+" "+bean_description.get(0).getD_laminateName()+"-"+bean_description.get(0).getD_designNo());
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
                            pairList.add(new BasicNameValuePair("first_name",firstName));
                            pairList.add(new BasicNameValuePair("last_name",lastName));
                            pairList.add(new BasicNameValuePair("email",emailID));
                            pairList.add(new BasicNameValuePair("contact_no",mobileNum));
                            pairList.add(new BasicNameValuePair("message",message));
                            pairList.add(new BasicNameValuePair("laminate_id",laminate_ID));

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
            dialog=new Custom_ProgressDialog(DescriptionPage.this,"");
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

    private void fetch_image_for_share(String index)
    {
        Log.e("22222",""+index);
        new DownloadFileFromURL().execute(index);

    }


    public class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);
            Log.e("3333","100");
           /* loadingView = new Custom_ProgressDialog(
                    activity, "");

            loadingView.setCancelable(false);
            loadingView.show();*/

            pDialog = new ProgressDialog(DescriptionPage.this);
            pDialog.setMessage("Please Wait Downloading Image");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(true);
            pDialog.show();


        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... i) {

            try {


                //pDialog.setMessage("Please Wait Downloading Image");
                File temp_dir = new File (myDir+"/"+ design_code+".png");
                if(temp_dir.exists())
                {
                    filesToSend = myDir+"/"+ design_code+".png";
                }
                else
                {
                    File dir = new File (myDir + "");
                    if(dir.exists()==false) {
                        dir.mkdirs();
                    }

                    Log.e("4444444",""+laminate_path);
                    URL url = new URL(laminate_path);
                    Log.e("FILE_NAME", "File name is " + design_code+".png");
                    Log.e("FILE_URLLINK", "File URL is "+laminate_path);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(dir+"/" + design_code+".png"));

                    filesToSend = dir+"/"+ design_code+".png";
                    Log.e("", "File to send :- "+dir+"/" + design_code+".png");
                    byte data[] = new byte[1024];
                    long total = 0;
                    //int count;


                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //  publishProgress((int) ((total * 100)/fileLength));
                        long total1 = (total * 100)/fileLength;
                        publishProgress(total1+"");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

            }
            catch (Exception e) {
                Log.e("Error: ", ""+e);
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
       /* protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }*/

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //activity.dismissDialog(0);
            pDialog.dismiss();

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.putExtra(Intent.EXTRA_SUBJECT, ""+laminate_name);
            intent.putExtra(Intent.EXTRA_TEXT, "Laminate Name : "+laminate_name+" \nItem Code : "+bean_description.get(0).getD_designNo()+" \nFinish Type : "+finish_type);
            intent.setType("image/*");  //This example is sharing jpeg images.

            ArrayList<Uri> files = new ArrayList<Uri>();


            File file = new File(filesToSend);
            Uri uri = Uri.fromFile(file);
            files.add(uri);


            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, files);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        }

        /*public void execute(String s) {
        }*/
    }

    public class Get_Description_Data extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {
                loadingView = new Custom_ProgressDialog(DescriptionPage.this, "");
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

                parameters.add(new BasicNameValuePair("user_id", userid));
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));

                Log.e("My Log",parameters.toString());


                Log.e("laminatemainid", "" + laminate_ID);
                Log.e("", "" + parameters);

                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/Laminate/App_Get_Laminate_Description",ServiceHandler.POST,parameters);
                Log.e("222222222222222", Globals.server_link+"amulya/Laminate/App_Get_Laminate_Description");
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
                    Globals.CustomToast(DescriptionPage.this, "SERVER ERRER", getLayoutInflater());
                    loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(DescriptionPage.this,""+Message, getLayoutInflater());
                        loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
                        //Globals.CustomToast(DescriptionPage.this,"Available Laminate Categories", getLayoutInflater());
                        loadingView.dismiss();
                        Log.e("gggggg","gggggggggggg");
                        JSONObject dataArray=jObj.getJSONObject("data");
                        JSONObject lamiinateObj=dataArray.getJSONObject("Laminate");
                        JSONObject laminateType=dataArray.getJSONObject("LaminateType");
                        JSONArray finish_typeArray=jObj.getJSONArray("finish_type");

                        //bean_description.clear();

//                        for(int i=0; i<lamiinateObj.length(); i++){
//                            Log.e("hhhh","hhhhh");
//                            JSONObject subObject=lamiinateObj.getJSONObject(i);
                            Bean_Description beanDescp=new Bean_Description();

                            beanDescp.setD_laminate_id(lamiinateObj.getString("id"));
                            beanDescp.setD_finishTypeId(lamiinateObj.getString("finish_type_id"));
                            beanDescp.setD_laminateTypeId(lamiinateObj.getString("laminate_type_id"));
                            beanDescp.setD_laminateName(lamiinateObj.getString("design_name"));
                            beanDescp.setD_designName(lamiinateObj.getString("design_name"));
                            beanDescp.setD_designNo(lamiinateObj.getString("design_no"));
                            beanDescp.setD_description(lamiinateObj.getString("description"));
                            //beanDescp.setD_technicalSpecification(lamiinateObj.getString("technical_spec"));
                            beanDescp.setD_size(lamiinateObj.getString("size"));
                            Log.e("111111111111111111",""+lamiinateObj.getString("size"));
                            beanDescp.setD_laminateImage(lamiinateObj.getString("image"));
                        Log.e("4444444444444444444",""+lamiinateObj.getString("image"));
                            beanDescp.setD_sortOrder(lamiinateObj.getString("sort_order"));
                            beanDescp.setD_status(lamiinateObj.getString("status"));
                            beanDescp.setD_thickness(lamiinateObj.getString("thickness"));
                            beanDescp.setD_pdf(lamiinateObj.getString("laminate_pdf"));

                        JSONObject finishType=dataArray.getJSONObject("FinishType");
                        beanDescp.setD_finishTypeName(finishType.getString("name"));


                        if(beanDescp.getD_pdf().equalsIgnoreCase("null") || beanDescp.getD_pdf().isEmpty()){
                            pdf.setVisibility(View.GONE);
                        }
                        else {
                            pdf.setVisibility(View.VISIBLE);
                        }


                        bean_description.add(beanDescp);

                        laminateTypeID.clear();
                        finishName.clear();
                        finishImage.clear();
                        finishPDF.clear();

                        int laminatePosition =0;

                        for(int i=0; i<finish_typeArray.length(); i++){
                            JSONObject finishTypeObj=finish_typeArray.getJSONObject(i);

                            if(laminate_ID.equalsIgnoreCase(finishTypeObj.getString("laminate_id"))){
                                laminatePosition=i;
                            }

                            laminateTypeID.add(finishTypeObj.getString("laminate_id"));
                            finishName.add(finishTypeObj.getString("finish_name"));
                            finishImage.add(finishTypeObj.getString("laminate_image"));
                            finishPDF.add(finishTypeObj.getString("laminate_pdf").replace("null",""));

                        }

                        finishTypeAdapter.notifyDataSetChanged();
                        spn_laminateType.setSelection(laminatePosition);
                        //thickness.setText(bean_description.get(0).getD_thickness());

                        // deccription_text.setText(Html.fromHtml(bean_description.get(0).getD_description()).toString());
                        // laminatenamedescp.setText(bean_description.get(0).getD_laminateName());

//        Picasso.with(getApplicationContext())
//                .load(Globals.IMAGELINK +bean_description.get(0).getD_laminateImage())
//                .placeholder(R.drawable.mica_watermark)
//                .into(desc_image);

                        txt_designName.setText(lamiinateObj.getString("design_name"));
                        txt_laminateType.setText(laminateType.getString("name"));
                        txt_itemcode.setText(lamiinateObj.getString("design_no"));
                        //txt_finish.setText(finishType.getString("name").toString());
                        sizetext.setText(lamiinateObj.getString("size"));
                        thickness.setText(lamiinateObj.getString("thickness"));
                        deccription_text.setText(Html.fromHtml(lamiinateObj.getString("description")).toString());
                      //  laminatenamedescp.setText(lamiinateObj.getString("design_name"));

                        String titles[]=lamiinateObj.getString("design_name").split(" ");

                        String newTitle="";

                        for(int k=0; k<titles.length; k++){
                            if(k==0)
                                newTitle += "<font color=#666666>"+titles[k]+" </font>";
                            else
                                newTitle += "<font color=#e77716>"+titles[k]+" </font>";
                        }

                        laminatenamedescp.setText(Html.fromHtml(newTitle));



                        Glide.with(getApplicationContext())
                            .load(IMAGELINK +lamiinateObj.getString("image"))
                            .placeholder(R.drawable.img_loding)
                            .into(desc_image);
                        Log.e("Size",bean_description.size()+"");

                        JSONArray favID=jObj.getJSONArray("favourite_list");
                        for (int i = 0; i < favID.length(); i++) {
                            arrayjson.add(String.valueOf(favID.get(i)));
                        }
                        Log.e("ARRAY JSON",""+arrayjson.toString());

                        Log.e("LAMINATE JSON ID",""+laminate_ID);

                        if(arrayjson.contains(laminate_ID)){
                            fav.setImageResource(R.drawable.heartfilled);
                            click=true;
                        }
                        else{
                            fav.setImageResource(R.drawable.heart_new);
                            click=false;
                        }

                        /*for(String no : arrayjson){
                            if(no.equals(laminate_ID))
                            {
                                Log.e("FILLED","FILLED");
                                fav.setImageResource(R.drawable.heartfilled);
                                click=true;
                            }
                            else
                            {
                                Log.e("empty","empty");
                                fav.setImageResource(R.drawable.heart_new);
                                click=false;
                            }
                        }*/
                    }
                }
        }


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                loadingView.dismiss();
            }

        }
    }

    public class AddToFavourite extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {
//                loadingView = new Custom_ProgressDialog(DescriptionPage.this, "");
//                loadingView.setCancelable(false);
//                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", userid));
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));

                Log.e("My Log",parameters.toString());


                Log.e("laminatemainid", "" + laminate_ID);
                Log.e("", "" + parameters);

                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_Favourite_List",ServiceHandler.POST,parameters);
                Log.e("222222222222222", Globals.server_link+"amulya/Laminate/App_Get_Laminate_Description");
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
                    Globals.CustomToast(DescriptionPage.this, "Server Error", getLayoutInflater());
                    //loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(DescriptionPage.this,""+Message, getLayoutInflater());
                        //loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        arrayjson.clear();

                        JSONArray favID=jObj.getJSONArray("favourite_list");
                        for (int i = 0; i < favID.length(); i++) {
                            arrayjson.add(String.valueOf(favID.get(i)));
                        }

                        String Message = jObj.getString("message");
                       // Globals.CustomToast(DescriptionPage.this,""+Message, getLayoutInflater());
                        //loadingView.dismiss();
                        Log.e("gggggg","gggggggggggg");
//                        JSONObject dataArray=jObj.getJSONObject("data");
//                        JSONObject lamiinateObj=dataArray.getJSONObject("Laminate");
//                        bean_favourites.clear();
//
////                        for(int i=0; i<lamiinateObj.length(); i++){
////                            Log.e("hhhh","hhhhh");
////                            JSONObject subObject=lamiinateObj.getJSONObject(i);
//                        Bean_Favourite beanDescp=new Bean_Favourite();
//
//                        beanDescp.setD_laminate_id(lamiinateObj.getString("id"));
//                        Log.e("favvv id",""+lamiinateObj.getString("id"));
//                        beanDescp.setD_finishTypeId(lamiinateObj.getString("finish_type_id"));
//                        beanDescp.setD_laminateTypeId(lamiinateObj.getString("laminate_type_id"));
//                        beanDescp.setD_laminateName(lamiinateObj.getString("name"));
//                        beanDescp.setD_designName(lamiinateObj.getString("design_name"));
//                        beanDescp.setD_designNo(lamiinateObj.getString("design_no"));
//                        beanDescp.setD_description(lamiinateObj.getString("description"));
//                        beanDescp.setD_technicalSpecification(lamiinateObj.getString("technical_spec"));
//                        beanDescp.setD_size(lamiinateObj.getString("size"));
//                        Log.e("111111111111111111",""+lamiinateObj.getString("size"));
//                        beanDescp.setD_laminateImage(lamiinateObj.getString("image"));
//                        Log.e("4444444444444444444",""+lamiinateObj.getString("image"));
//                        beanDescp.setD_sortOrder(lamiinateObj.getString("sort_order"));
//                        beanDescp.setD_status(lamiinateObj.getString("status"));
//                        beanDescp.setD_thickness(lamiinateObj.getString("thickness"));
//                        beanDescp.setD_pdf(lamiinateObj.getString("laminate_pdf"));
//
//                        JSONObject finishType=dataArray.getJSONObject("FinishType");
//                        beanDescp.setD_finishTypeName(finishType.getString("name"));
//
//
//                        bean_favourites.add(beanDescp);
//
////                        sizetext.setText(lamiinateObj.getString("size").toString());
////                        thickness.setText(lamiinateObj.getString("thickness").toString());
////                        deccription_text.setText(Html.fromHtml(lamiinateObj.getString("description")).toString());
////                        laminatenamedescp.setText(lamiinateObj.getString("name").toString());
////                        Picasso.with(getApplicationContext())
////                                .load(Globals.IMAGELINK +lamiinateObj.getString("image").toString())
////                                .placeholder(R.drawable.mica_watermark)
////                                .into(desc_image);
//
//                        Log.e("Size",bean_description.size()+"");
//                        // SetData();
                    }
                }
            }


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                //loadingView.dismiss();
            }

        }
    }

    public class DeleteFromFavourite extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {
//                loadingView = new Custom_ProgressDialog(DescriptionPage.this, "");
//                loadingView.setCancelable(false);
//                loadingView.show();

            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", userid));
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));

                Log.e("My Log",parameters.toString());


                Log.e("laminatemainid", "" + laminate_ID);
                Log.e("", "" + parameters);

                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_Delete_Favourite_List",ServiceHandler.POST,parameters);
                Log.e("222222222222222", Globals.server_link+"amulya/Laminate/App_Get_Laminate_Description");
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
                    Globals.CustomToast(DescriptionPage.this, "Server Error", getLayoutInflater());
                    //loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(DescriptionPage.this,""+Message, getLayoutInflater());
                        //loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        arrayjson.clear();

                        JSONArray favID=jObj.getJSONArray("favourite_list");
                        for (int i = 0; i < favID.length(); i++) {
                            arrayjson.add(String.valueOf(favID.get(i)));
                        }

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(DescriptionPage.this,""+Message, getLayoutInflater());
                        //loadingView.dismiss();
                        Log.e("gggggg","gggggggggggg");
//                        JSONObject dataArray=jObj.getJSONObject("data");
//                        JSONObject lamiinateObj=dataArray.getJSONObject("Laminate");
//                        bean_favourites.clear();
//
////                        for(int i=0; i<lamiinateObj.length(); i++){
////                            Log.e("hhhh","hhhhh");
////                            JSONObject subObject=lamiinateObj.getJSONObject(i);
//                        Bean_Favourite beanDescp=new Bean_Favourite();
//
//                        beanDescp.setD_laminate_id(lamiinateObj.getString("id"));
//                        beanDescp.setD_finishTypeId(lamiinateObj.getString("finish_type_id"));
//                        beanDescp.setD_laminateTypeId(lamiinateObj.getString("laminate_type_id"));
//                        beanDescp.setD_laminateName(lamiinateObj.getString("name"));
//                        beanDescp.setD_designName(lamiinateObj.getString("design_name"));
//                        beanDescp.setD_designNo(lamiinateObj.getString("design_no"));
//                        beanDescp.setD_description(lamiinateObj.getString("description"));
//                        beanDescp.setD_technicalSpecification(lamiinateObj.getString("technical_spec"));
//                        beanDescp.setD_size(lamiinateObj.getString("size"));
//                        Log.e("111111111111111111",""+lamiinateObj.getString("size"));
//                        beanDescp.setD_laminateImage(lamiinateObj.getString("image"));
//                        Log.e("4444444444444444444",""+lamiinateObj.getString("image"));
//                        beanDescp.setD_sortOrder(lamiinateObj.getString("sort_order"));
//                        beanDescp.setD_status(lamiinateObj.getString("status"));
//                        beanDescp.setD_thickness(lamiinateObj.getString("thickness"));
//                        beanDescp.setD_pdf(lamiinateObj.getString("laminate_pdf"));
//
//                        JSONObject finishType=dataArray.getJSONObject("FinishType");
//                        beanDescp.setD_finishTypeName(finishType.getString("name"));
//
//
//                        bean_favourites.add(beanDescp);
//
////                        sizetext.setText(lamiinateObj.getString("size").toString());
////                        thickness.setText(lamiinateObj.getString("thickness").toString());
////                        deccription_text.setText(Html.fromHtml(lamiinateObj.getString("description")).toString());
////                        laminatenamedescp.setText(lamiinateObj.getString("name").toString());
////                        Picasso.with(getApplicationContext())
////                                .load(Globals.IMAGELINK +lamiinateObj.getString("image").toString())
////                                .placeholder(R.drawable.mica_watermark)
////                                .into(desc_image);
//
//                        Log.e("Size",bean_description.size()+"");
//                        // SetData();
                    }
                }
            }


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                //loadingView.dismiss();
            }

        }
    }

    @Override
    public void onBackPressed() {
       /* Log.e("entered","back");
       // appPrefs=new AppPrefs(getApplicationContext());
        if(appPrefs.getDescp_page().equalsIgnoreCase("list"))
        {
            Log.e("entered","list");
            Intent intent=new Intent(DescriptionPage.this,LaminateList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }
        if(appPrefs.getDescp_page().equalsIgnoreCase("search"))
        {
            Log.e("entered","search");
            Intent intent=new Intent(DescriptionPage.this,Search.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }
        if(appPrefs.getDescp_page().equalsIgnoreCase("fav"))
        {
            Log.e("entered","fav");
            Intent intent=new Intent(DescriptionPage.this,FavouriteList.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("category",categories);
            startActivity(intent);
            finish();
        }*/

        finish();
//        Intent intent=new Intent(DescriptionPage.this,LaminateList.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.putExtra("category",categories);
//        startActivity(intent);
//        finish();
    }
//
//    @Override
//    public Bean_Description getLaminateId(String laminateid) {
//        // TODO Auto-generated method stub
//        for(int i=0; i<arrayjson.size(); i++){
//            if (arrayjson.get(i).get() == productId) {
//                // Return this product.
//                fav.setImageResource(R.drawable.heartfilled);
//            }
//            else
//            {
//                fav.setImageResource(R.drawable.heart_new);
//            }
//            //System.out.println(arrayjson.get(i));
//            Log.e("arrraaayyyy",""+arrayjson.get(i));
//        }
//
//        return null;
//    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);
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
                Intent i = new Intent(getApplicationContext(), FavouriteList.class);
                appPrefs = new AppPrefs(getApplicationContext());
                appPrefs.setfav("detail");
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
                Log.e("actionentered","actionback");

                onBackPressed();

                /*appPrefs=new AppPrefs(getApplicationContext());
                if(appPrefs.getDescp_page().equalsIgnoreCase("list"))
                {
                    Log.e("actionentered","list");
                    Intent intent=new Intent(DescriptionPage.this,LaminateList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }
                if(appPrefs.getDescp_page().equalsIgnoreCase("search"))
                {
                    Log.e("actionentered","search");
                    Intent intent=new Intent(DescriptionPage.this,Search.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }
                if(appPrefs.getDescp_page().equalsIgnoreCase("fav"))
                {
                    Log.e("actionentered","fav");
                    Intent intent=new Intent(DescriptionPage.this,FavouriteList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }*/
//               // app =new AppPrefs(LaminateList.this);
//                Intent i = new Intent(DescriptionPage.this,LaminateList.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.putExtra("category",categories);
//                startActivity(i);
//                finish();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                app = new AppPrefs(LaminateList.this);
//                app.setCategoryid("");
                Intent i = new Intent(DescriptionPage.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveImage(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/AmulyaMica");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".png";
        File file = new File(myDir, fname);
        Log.i("File", "" + file);
        if (file.exists())
            file.delete();
        try {
            if (ContextCompat.checkSelfPermission(DescriptionPage.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(DescriptionPage.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {



                } else {

                    ActivityCompat.requestPermissions(DescriptionPage.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSION_WR_FILES);
                }
            }
            else {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                Globals.CustomToast(DescriptionPage.this, "Saved Successfully", getLayoutInflater());
                out.flush();
            }
        } catch (Exception e) {
            Log.e("Exception",e.getMessage());
            Globals.CustomToast(DescriptionPage.this, "Failed To Save Image", getLayoutInflater());
            e.printStackTrace();
        }

        MediaScannerConnection.scanFile(this, new String[] {

                        file.getAbsolutePath()},

                null, new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri)

                    {


                    }

                });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_WR_FILES: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    desc_image.setDrawingCacheEnabled(true);
                    Bitmap bitmap=desc_image.getDrawingCache(true).copy(Bitmap.Config.RGB_565, false);
                    desc_image.setDrawingCacheEnabled(false);
                    desc_image.destroyDrawingCache();
                    saveImage(bitmap);

                } else {
                    Globals.CustomToast(DescriptionPage.this, "Failed To Save Image", getLayoutInflater());
                }

                break;
            }
        }
    }

    public void downloadPdfContent(String pdf_path){

        try {

            String fileName="xyz";
            String fileExtension=".pdf";

//           download pdf file.

            URL url = new URL(pdf_path);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            //Log.e("FILE_URLLINK", "File URL is "+laminate_path);
            String PATH = Environment.getExternalStorageDirectory() + "/mydownload/";
            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, fileName+fileExtension);
            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            System.out.println("--pdf downloaded--ok--"+pdf_path);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    class DownloadFileFromURL_a extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //activity.showDialog(0);



            pDialog = new ProgressDialog(DescriptionPage.this);
            pDialog.setMessage("Please Wait Downloading Invoice PDF");
            pDialog.setIndeterminate(false);
            pDialog.setMax(100);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... i) {

            try {


                //pDialog.setMessage("Please Wait Downloading Image");
                File temp_dir = new File (myDir+"/"+ design_name+".pdf");
                if(temp_dir.exists())
                {
                    filesToSend = myDir+"/"+design_name+".pdf";


                }
                else
                {
                    File dir = new File (myDir + "");
                    if(dir.exists()==false) {
                        dir.mkdirs();
                    }


                    URL url = new URL(IMAGELINK+bean_description.get(0).getD_pdf());

                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a typical 0-100% progress bar
                    long fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(new File(myDir+"/"+design_name+".pdf"));

                    filesToSend = myDir+"/"+design_name+".pdf";
                    Log.e("", "out put :- "+myDir+""+design_name+".pdf");
                    byte data[] = new byte[1024];
                    long total = 0;
                    //int count;


                    int count;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        //  publishProgress((int) ((total * 100)/fileLength));
                        long total1 = (total * 100)/fileLength;
                        publishProgress(total1+"");
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                }

            }
            catch (Exception e) {
                Log.e("Error: ", ""+e);
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            //activity.dismissDialog(0);
            pDialog.dismiss();

            File file = new File(myDir+"/"+design_name+".pdf");
            Intent intent = new Intent(ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }

    }

}
