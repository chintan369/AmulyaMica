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

/**
 * Created by SEO on 12/23/2016.
 */

public class Custom_Scroll_Grid_Laminate extends BaseAdapter implements Serializable {

    List<Bean_Scroll_Laminate> bean_scroll_laminates;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    ArrayList<String> array_image;
    Custom_ProgressDialog loadingView;
    OnClickLaminate onClickLaminate;

    public Custom_Scroll_Grid_Laminate(Context context, List<Bean_Scroll_Laminate> bean_scroll_laminates, Activity activity, ArrayList array_image,
                                       OnClickLaminate onClickLaminate){
        this.context=context;
        this.bean_scroll_laminates=bean_scroll_laminates;
        this.activity=activity;
        this.array_image = array_image;
        this.onClickLaminate=onClickLaminate;

    }
    @Override
    public int getCount() {
        return bean_scroll_laminates.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_scroll_laminates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_scroll_laminate,parent,false);

        final Bean_Scroll_Laminate categories=bean_scroll_laminates.get(position);
        ImageView catImage=(ImageView) view.findViewById(R.id.cat_image);
        TextView catName=(TextView) view.findViewById(R.id.cat_text);

        catName.setText(categories.getScroll_laminate_name());
        // prefs.setCategory_name(String.valueOf(catName));

        // imageloader.DisplayImage(Globals.server_link+str.get(count).getImg(), img_a1);

        Log.e("SCROOLL IMAGE",""+bean_scroll_laminates.get(position).getScroll_laminate_image());
        Picasso.with(activity)
                .load(Globals.IMAGELINK +bean_scroll_laminates.get(position).getScroll_laminate_image())
                .placeholder(R.drawable.mica_watermark)
                .into(catImage);

//        catImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //prefs.setEgalleryid(bean_scroll_laminates.get(position).get());
//                prefs.setScrollLaminateId(bean_scroll_laminates.get(position).getScroll_laminate_id());
//                prefs.setSubCategoryId(bean_scroll_laminates.get(position).getSubcategory_id());
//                Log.e("Laminate id",""+prefs.getScrollLaminateId());
//                Log.e("SubCategory id",""+prefs.getSubCategoryId());
//                new SetImageOfLaminate(prefs.getScrollLaminateId(),prefs.getSubCategoryId(),position).execute();
////                Intent i = new Intent(context, EditingScreen.class);
////                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                // i.putExtra("category",bean_egalleries.get(position));
////                context.startActivity(i);
////                activity.finish();
//
//            }
//        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickLaminate.onLaminateClick(bean_scroll_laminates.get(position).getScroll_laminate_id(),
                        bean_scroll_laminates.get(position).getSubcategory_id());

                Log.e("IDs",bean_scroll_laminates.get(position).getScroll_laminate_id()+" -> "+
                        bean_scroll_laminates.get(position).getSubcategory_id());
            }
        });


        return view;
    }

//    public class SetImageOfLaminate extends AsyncTask<Void,Void,String> {
//        boolean status;
//        private String result;
//        public StringBuilder sb;
//        private InputStream is;
//        String jsonData="";
//
//        String laminate_ID="0";
//        String subCategory_ID="0";
//        int position=0;
//
//        public SetImageOfLaminate(String laminate_ID, String subCategory_ID,int position) {
//            this.laminate_ID = laminate_ID;
//            this.subCategory_ID = subCategory_ID;
//            this.position=position;
//        }
//
//        protected void onPreExecute() {
//            Log.e("aaaa","aaaa");
//            super.onPreExecute();
//            try {
////                loadingView = new Custom_ProgressDialog(context, "");
////                loadingView.setCancelable(false);
////                loadingView.show();
//
//            } catch (Exception e) {
//
//            }
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            try {
//                Log.e("bb","bbbb");
//                List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//                parameters.add(new BasicNameValuePair("laminate_id", laminate_ID));
//                parameters.add(new BasicNameValuePair("sub_cat_id", subCategory_ID));
//
//
//                Log.e("My Log",parameters.toString());
//
//
//                Log.e("laminatemainid", "" + laminate_ID);
//                Log.e("", "" + parameters);
//
//                // List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//
//
//                jsonData = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/EgallerySubCategory/App_Get_Egallery_Laminate_Image",ServiceHandler.POST,parameters);
//                Log.e("222222222222222", Globals.server_link+"amulya/EgallerySubCategory/App_Get_Egallery_Laminate_Image");
//                System.out.println("Data From Server " + jsonData);
//                return jsonData;
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println("Exception " + e.toString());
//
//                return jsonData;
//            }
//
//        }
//
//        @Override
//        protected void onPostExecute(String result_1) {
//            super.onPostExecute(result_1);
//
//            try {
//                Log.e("ccccc","cccccc\n"+result_1);
//
//                //db = new DatabaseHandler(());
//                System.out.println(result_1);
//
//                if (result_1.equalsIgnoreCase("")
//                        || (result_1.equalsIgnoreCase(""))) {
//                    Log.e("dddd","dddddd");
//                    Globals.CustomToast(context, "Server Error", activity.getLayoutInflater());
//                    //loadingView.dismiss();
//
//                } else {
//                    Log.e("eeee","eeeee");
//                    JSONObject jObj = new JSONObject(result_1);
//
//                    boolean date = jObj.getBoolean("status");
//
//                    Log.e("status",""+date);
//                    if (!date) {
//                        String Message = jObj.getString("message");
//                        Globals.CustomToast(context,""+Message, activity.getLayoutInflater());
//                        //loadingView.dismiss();
//                    } else {
//                        Log.e("ffff","fffff");
//
//                        String Message1 = jObj.getString("message");
//                        //Globals.CustomToast(MainActivity.this,""+Message, getLayoutInflater());
//                        Globals.CustomToast(context,""+Message1, activity.getLayoutInflater());
//                        loadingView.dismiss();
//
//                        JSONArray dataArray=jObj.getJSONArray("data");
//
//                        bean_scroll_laminates.clear();
//
//                        for(int i=0; i<dataArray.length(); i++){
//                            Log.e("hhhh","hhhhh");
//                            JSONObject subObject=dataArray.getJSONObject(i);
//                            JSONObject imageObj=subObject.getJSONObject("EgalleryLaminate");
//                            Bean_Scroll_Laminate beanScrollLaminate=new Bean_Scroll_Laminate();
//                            beanScrollLaminate.setScroll_laminate_id(subObject.getString("laminate_id"));
//                            //Log.e("CATEGORYYYYYYID",""+beanSub.getEgallery_id());
//                            beanScrollLaminate.setEgallery_sub_image(subObject.getString("sub_cat_id"));
//
////                            JSONObject laminateObject=subObject.getJSONObject("Laminate");
////                            beanScrollLaminate.setScroll_laminate_name(laminateObject.getString("name"));
////                            beanScrollLaminate.setScroll_laminate_image(laminateObject.getString("image"));
//
//
//                            bean_scroll_laminates.add(beanScrollLaminate);
//
//                            Log.e("IMAGE LINK",""+beanScrollLaminate.getEgallery_sub_image());
//
//                            Picasso.with(activity)
//                                    .load(Globals.IMAGELINK +subObject.getString("sub_cat_id").toString())
//                                    .placeholder(R.drawable.mica_watermark)
//                                    .into(img_laminate);
//
//                        }
//                        custom_scroll_grid_laminate.notifyDataSetChanged();
//                        Log.e("Size",bean_scroll_laminates.size()+"");
//                    }
//                }
//            }
//
//
//            catch(JSONException j){
//                j.printStackTrace();
//                Log.e("Exception",j.getMessage());
//            }
//            finally {
//                //  loadingView.dismiss();
//                notifyDataSetChanged();
//            }
//
//        }
//    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",bean_scroll_laminates.size()+"");
    }

    public void updateData(List<Bean_Scroll_Laminate> bean_scroll_laminates)
    {
        this.bean_scroll_laminates=bean_scroll_laminates;
        notifyDataSetChanged();
    }

    public interface OnClickLaminate{
        void onLaminateClick(String laminateID, String subCategoryID);
    }

}
