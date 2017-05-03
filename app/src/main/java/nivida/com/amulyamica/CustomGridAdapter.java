package nivida.com.amulyamica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEO on 10/21/2016.
 */

public class CustomGridAdapter extends BaseAdapter implements Serializable {
    List<Bean_Categories> bean_categoriesList;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    ArrayList<String> array_image;

    public CustomGridAdapter(Context context, List<Bean_Categories> bean_categoriesList, Activity activity, ArrayList<String> array_image){
        this.context=context;
        this.bean_categoriesList=bean_categoriesList;
        this.activity=activity;
        this.array_image = array_image;

    }


    @Override
    public int getCount() {
        return bean_categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_categoriesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.custom_grid_layout,parent,false);

        final Bean_Categories categories=bean_categoriesList.get(position);
        ImageView catImage=(ImageView) view.findViewById(R.id.cat_image);

        SimpleDraweeView frescoImageView=(SimpleDraweeView) view.findViewById(R.id.frescoImageView);
        TextView catName=(TextView) view.findViewById(R.id.cat_text);

        //catImage.setVisibility(View.GONE);
        //frescoImageView.setVisibility(View.VISIBLE);
        catName.setText(categories.getCategory_name());
       // prefs.setCategory_name(String.valueOf(catName));

        // imageloader.DisplayImage(Globals.server_link+str.get(count).getImg(), img_a1);

        Picasso.with(activity)
                .load(Globals.IMAGELINK +bean_categoriesList.get(position).getCategory_image())
                .placeholder(R.drawable.img_loding)
                .into(catImage);


        //Uri uri=Uri.parse(Globals.IMAGELINK +bean_categoriesList.get(position).getCategory_image());
        //frescoImageView.setImageURI(uri);


//        Glide.with(activity)
//                .load(Globals.IMAGELINK +bean_categoriesList.get(position).getCategory_image())
//                .centerCrop()
//                .placeholder(R.drawable.mica_watermark)
//                .crossFade()
//                .into(catImage);

        prefs=new AppPrefs(context);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                String catid=bean_categoriesList.get(0).getCategory_id();

                prefs.setCategoryid(bean_categoriesList.get(position).getCategory_id());
                Log.e("Category id",""+prefs.getCategoryid());
                Intent i = new Intent(context, LaminateList.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("category",bean_categoriesList.get(position));
                context.startActivity(i);

            }
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",bean_categoriesList.size()+"");
    }

    public void updateData(List<Bean_Categories> bean_categoriesList)
    {
        this.bean_categoriesList=bean_categoriesList;
        notifyDataSetChanged();
    }

}
