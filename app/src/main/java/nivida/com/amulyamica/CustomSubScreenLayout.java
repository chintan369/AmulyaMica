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

public class CustomSubScreenLayout extends BaseAdapter implements Serializable {
    List<Bean_SubScreen> bean_subScreens;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    ArrayList<String> array_image;


    public CustomSubScreenLayout(Context context, List<Bean_SubScreen> bean_subScreens, Activity activity, ArrayList array_image){
        this.context=context;
        this.bean_subScreens=bean_subScreens;
        this.activity=activity;
        this.array_image = array_image;

    }
    @Override
    public int getCount() {
        return bean_subScreens.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_subScreens.get(position);
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

        final Bean_SubScreen beanSubScreen=bean_subScreens.get(position);
        ImageView catImage=(ImageView) view.findViewById(R.id.cat_image);
        TextView catName=(TextView) view.findViewById(R.id.cat_text);

        catName.setText(beanSubScreen.getSub_screen_name());
        Picasso.with(activity)
                .load(Globals.IMAGELINK +bean_subScreens.get(position).getSub_images())
                .placeholder(R.drawable.mica_watermark)
                .into(catImage);
        prefs=new AppPrefs(context);
        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                prefs.setSubscreenid(bean_subScreens.get(position).getSub_id());
                Log.e("SUNSCREENCategory id",""+prefs.getCategoryid());
                Intent i = new Intent(context, EditingScreen.class);
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
        Log.d("Size in Adapter",bean_subScreens.size()+"");
    }

    public void updateData(List<Bean_SubScreen> bean_subScreens)
    {
        this.bean_subScreens=bean_subScreens;
        notifyDataSetChanged();
    }
}
