package nivida.com.amulyamica;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEO on 12/14/2016.
 */

public class Custom_Favourite_Layout extends BaseAdapter {
    List<Bean_Favourite> bean_favourites;
    //Bean_Categories bean_categories;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    ArrayList<String> array_Listimage;

    String userid;
    Custom_ProgressDialog loadingView;

    OnViewClickListener onViewClickListener;

    public Custom_Favourite_Layout(Context context, List<Bean_Favourite> bean_favourites, Activity activity, ArrayList array_Listimage, OnViewClickListener onViewClickListener){
        this.context=context;
        this.bean_favourites=bean_favourites;
        //this.bean_categories=bean_categories;
        this.activity=activity;
        this.array_Listimage = array_Listimage;
        this.onViewClickListener=onViewClickListener;
        prefs=new AppPrefs(context);

    }


    @Override
    public int getCount() {
        return bean_favourites.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_favourites.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        final View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_fav_list_layout,parent,false);



        final Bean_Favourite beanFavourite=bean_favourites.get(position);
        ImageView laminateImg=(ImageView) view.findViewById(R.id.image1);
        TextView laminateName=(TextView) view.findViewById(R.id.laminatename);
        TextView finishType=(TextView)view.findViewById(R.id.finishtype);
        TextView design_number=(TextView)view.findViewById(R.id.design_number);
        TextView designName=(TextView) view.findViewById(R.id.designname);
        LinearLayout wheretoBuy=(LinearLayout)view.findViewById(R.id.wheretobuy);
        LinearLayout buyNow=(LinearLayout)view.findViewById(R.id.buynow);
        final ImageView fav =(ImageView)view.findViewById(R.id.fav);

        if(beanFavourite.isFavourite()){
            fav.setImageResource(R.drawable.heartfilled);
        }
        else {
            fav.setImageResource(R.drawable.heart_new);
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bean_favourites.get(position).isFavourite()){
                    view.startAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_out));
                    new DeleteFromFavourite(beanFavourite.getD_laminate_id(),position).execute();
                }
            }
        });


        laminateName.setText(beanFavourite.getD_laminateName());
        finishType.setText(beanFavourite.getD_finishTypeName());
        designName.setText(beanFavourite.getD_designName());
        design_number.setText(beanFavourite.getD_designNo());


        Picasso.with(activity)
                .load(Globals.IMAGELINK +bean_favourites.get(position).getD_laminateImage())
                .placeholder(R.drawable.img_loding)
                .into(laminateImg);

        laminateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                prefs = new AppPrefs(context);
//                Intent i = new Intent(context, DescriptionPage.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(i);
//                activity.finish();
                prefs = new AppPrefs(context);
                prefs.setLaminateid(bean_favourites.get(position).getD_laminate_id());
                Log.e("LAMINTAE IDDD",""+prefs.getLaminateid());
                // app.setRef_Detail("ProductList");
                Intent i = new Intent(context, DescriptionPage.class);
                //i.putExtra("Pro_id",bean_product1.get(position).getPro_id().toString());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // i.putExtra("laminate",bean_favourites.get(position));
                // i.putExtra("category",bean_categories);
                context.startActivity(i);

            }
        });

        wheretoBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WhereToBuy.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onViewClickListener.onShowInquiryDialog(bean_favourites.get(position).getD_laminate_id());
            }
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",bean_favourites.size()+"");
    }

    public void updateData(List<Bean_Favourite> bean_favourites)
    {
        this.bean_favourites=bean_favourites;
        notifyDataSetChanged();
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
            try {
//                loadingView = new Custom_ProgressDialog(activity, "");
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
                parameters.add(new BasicNameValuePair("user_id", prefs.getUserid()));
                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));

                Log.e("My Log",parameters.toString());


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
                if (result_1==null || result_1.isEmpty()) {
                    Log.e("dddd","dddddd");
                    Globals.CustomToast(activity, "Server Error", activity.getLayoutInflater());
                    //loadingView.dismiss();

                } else {
                    Log.e("eeee","eeeee");
                    JSONObject jObj = new JSONObject(result_1);

                    boolean date = jObj.getBoolean("status");

                    Log.e("status",""+date);
                    if (!date) {
                        String Message = jObj.getString("message");
                        Globals.CustomToast(activity,""+Message, activity.getLayoutInflater());
                        //loadingView.dismiss();
                    } else {
                        Log.e("ffff","fffff");

                        String Message = jObj.getString("message");

                        bean_favourites.remove(position);
                        notifyDataSetChanged();
                        Globals.CustomToast(activity,Message,activity.getLayoutInflater());


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

            notifyDataSetChanged();

        }
    }

    public interface OnViewClickListener{
        void onViewClickSetFavorite(String laminateID,boolean isFavourite);
        void onShowInquiryDialog(String laminateID);
    }

}
