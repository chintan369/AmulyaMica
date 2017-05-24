package nivida.com.amulyamica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEO on 10/27/2016.
 */

public class CustomLaminateListAdapter extends BaseAdapter {
    List<Bean_LaminateList> bean_laminateLists;
    Bean_Categories bean_categories;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    ArrayList<String> array_Listimage;
    Custom_ProgressDialog loadingView;
    String userid;
    boolean click=false;
    OnViewClickListener onViewClickListener;

    ArrayList<String> favItemList=new ArrayList<>();

    public CustomLaminateListAdapter(Context context, List<Bean_LaminateList> bean_laminateLists, Activity activity, ArrayList array_Listimage,ArrayList<String> fvArrayList,OnViewClickListener onViewClickListener){
        this.context=context;
        this.bean_laminateLists=bean_laminateLists;
        this.bean_categories=bean_categories;
        this.activity=activity;
        this.array_Listimage = array_Listimage;
        this.favItemList = fvArrayList;
        this.onViewClickListener=onViewClickListener;
        prefs=new AppPrefs(context);

    }


    @Override
    public int getCount() {
        return bean_laminateLists.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_laminateLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_laminate_list,parent,false);



        final Bean_LaminateList laminates=bean_laminateLists.get(position);
        ImageView laminateImg=(ImageView) view.findViewById(R.id.image1);
        TextView laminateName=(TextView) view.findViewById(R.id.laminatename);
        TextView finishType=(TextView)view.findViewById(R.id.finishtype);
        TextView design_number=(TextView)view.findViewById(R.id.design_number);
        TextView designName=(TextView) view.findViewById(R.id.designname);
        TextView txt_inquiry=(TextView) view.findViewById(R.id.txt_inquiry);
        LinearLayout wheretoBuy=(LinearLayout)view.findViewById(R.id.wheretobuy);
        LinearLayout buyNow=(LinearLayout)view.findViewById(R.id.buynow);
        ImageView fav =(ImageView)view.findViewById(R.id.fav);

        txt_inquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClickListener.onShowInquiryDialog(bean_laminateLists.get(position).getLaminate_id(),bean_laminateLists.get(position).getFinishTypeName(),bean_laminateLists.get(position).getLaminateName(),bean_laminateLists.get(position).getDesignNo());
            }
        });


       if(bean_laminateLists.get(position).isFavourite_laminate()){
           fav.setImageResource(R.drawable.heartfilled);
       }
        else {
           fav.setImageResource(R.drawable.heart_new);
       }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bean_laminateLists.get(position).isFavourite_laminate()){
                    new DeleteFromFavourite(bean_laminateLists.get(position).getLaminate_id(),position).execute();
                }
                else {
                    new AddToFavourite(bean_laminateLists.get(position).getLaminate_id(),position).execute();
                }
            }
        });


        laminateName.setText(laminates.getLaminateName());
        finishType.setText(laminates.getFinishTypeName());
        designName.setText(laminates.getDesignName());
        design_number.setText(laminates.getDesignNo());

        // imageloader.DisplayImage(Globals.server_link+str.get(count).getImg(), img_a1);
        Glide.with(activity)
                .load(Globals.IMAGELINK +bean_laminateLists.get(position).getLaminateImage())
                .placeholder(R.drawable.img_loding)
                .into(laminateImg);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefs = new AppPrefs(context);
                prefs.setLaminateid(bean_laminateLists.get(position).getLaminate_id());
               // app.setRef_Detail("ProductList");
                Intent i = new Intent(context, DescriptionPage.class);
                //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("laminate",bean_laminateLists.get(position));
               // i.putExtra("category",bean_categories);
                context.startActivity(i);
            }
        });

        wheretoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WhereToBuy.class);
                //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                // i.putExtra("laminate",bean_laminateLists.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("laminate",bean_laminateLists.get(position));
                //intent.putExtra("category",bean_categories);
                context.startActivity(intent);

            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CartPage.class);
                //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("laminate",bean_laminateLists.get(position));
//                i.putExtra("category",bean_categories);

                context.startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void updateData(List<Bean_LaminateList> bean_laminateLists)
    {
        this.bean_laminateLists=bean_laminateLists;
        notifyDataSetChanged();
    }

    public class AddToFavourite extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        String laminate_ID="0";
        int position=0;

        public AddToFavourite(String laminate_ID, int position) {
            this.laminate_ID = laminate_ID;
            this.position=position;
        }

        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
            try {


            } catch (Exception e) {

            }
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", prefs.getUserid()));
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));

                Log.e("My Log",parameters.toString());


                Log.e("laminatemainid", "" + laminate_ID);
                Log.e("", "" + parameters);

                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_Favourite_List",ServiceHandler.POST,parameters);

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

                if (result_1==null
                        || result_1.isEmpty()) {
                    Globals.CustomToast(context, "Server Error", activity.getLayoutInflater());
                    //loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(context,""+Message, activity.getLayoutInflater());
                        //loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(context,""+Message, activity.getLayoutInflater());
                        bean_laminateLists.get(position).setFavourite_laminate(true);
                        notifyDataSetChanged();
                        //loadingView.dismiss();
                    }
                }
            }


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
              //  loadingView.dismiss();
                notifyDataSetChanged();
            }

        }
    }

    public class DeleteFromFavourite extends AsyncTask<Void,Void,String> {
        boolean status;
        private String result;
        public StringBuilder sb;
        private InputStream is;
        String jsonData="";

        String laminate_ID="0";
        int position=0;

        public DeleteFromFavourite(String laminate_ID, int position) {
            this.laminate_ID = laminate_ID;
            this.position=position;
        }


        protected void onPreExecute() {
            Log.e("aaaa","aaaa");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.e("bb","bbbb");
                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                parameters.add(new BasicNameValuePair("user_id", prefs.getUserid()));
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));

                Log.e("My Log",parameters.toString());


                Log.e("laminatemainid", "" + laminate_ID);
                Log.e("", "" + parameters);

                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_Delete_Favourite_List",ServiceHandler.POST,parameters);
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
                    Globals.CustomToast(context, "Server Error", activity.getLayoutInflater());
                    //loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(context,""+Message, activity.getLayoutInflater());
                    } else {
                        Log.e("ffff","fffff");

                        String Message = jObj.getString("message");
                        //Globals.CustomToast(context,""+Message, activity.getLayoutInflater());
                        bean_laminateLists.get(position).setFavourite_laminate(false);
                    }
                }
            }


            catch(JSONException j){
                j.printStackTrace();
                Log.e("Exception",j.getMessage());
            }
            finally {
                notifyDataSetChanged();
            }

        }
    }

    public void setFavList(ArrayList<String> favList){
        this.favItemList=favList;
    }

    public interface OnViewClickListener{
        void onViewClickSetFavorite(String laminateID,String lamninate_type_name,String laminate_name,String design_no,boolean isFavourite);
        void onShowInquiryDialog(String laminateID,String lamninate_type_name,String laminate_name,String design_no);
    }

}
