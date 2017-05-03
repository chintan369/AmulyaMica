package nivida.com.amulyamica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEO on 12/13/2016.
 */

public class CustomEgalleryGrid extends BaseAdapter implements Serializable {
    List<Bean_Egallery> bean_egalleries;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    ArrayList<String> array_image;


    public CustomEgalleryGrid(Context context, List<Bean_Egallery> bean_egalleries, Activity activity, ArrayList array_image){
        this.context=context;
        this.bean_egalleries=bean_egalleries;
        this.activity=activity;
        this.array_image = array_image;

    }
    @Override
    public int getCount() {
        return bean_egalleries.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_egalleries.get(position);
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

        final Bean_Egallery categories=bean_egalleries.get(position);
        ImageView catImage=(ImageView) view.findViewById(R.id.cat_image);
        TextView catName=(TextView) view.findViewById(R.id.cat_text);

        catName.setText(categories.getEgallery_name());
        // prefs.setCategory_name(String.valueOf(catName));

        // imageloader.DisplayImage(Globals.server_link+str.get(count).getImg(), img_a1);
        Picasso.with(activity)
                .load(Globals.IMAGELINK +bean_egalleries.get(position).getEgallery_image())
                .placeholder(R.drawable.mica_watermark)
                .into(catImage);
        prefs=new AppPrefs(context);
        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefs.setEgalleryid(bean_egalleries.get(position).getEgallery_id());
                Log.e("Category id",""+prefs.getCategoryid());
                Intent i = new Intent(context, SubScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // i.putExtra("category",bean_egalleries.get(position));
                context.startActivity(i);
                activity.finish();

            }
        });

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",bean_egalleries.size()+"");
    }

    public void updateData(List<Bean_Egallery> bean_egalleries)
    {
        this.bean_egalleries=bean_egalleries;
        notifyDataSetChanged();
    }
}
