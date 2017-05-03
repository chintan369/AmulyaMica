package nivida.com.amulyamica.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nivida.com.amulyamica.R;
import nivida.com.amulyamica.TouchImageView;

import static nivida.com.amulyamica.Globals.IMAGELINK;

public class ImageSliderAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<String> imageList=new ArrayList<>();
    Activity activity;
    boolean fromOutside=false;

    public ImageSliderAdapter() {

    }

    public ImageSliderAdapter(Context context, ArrayList<String> imageList, Activity activity) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        TouchImageView img = new TouchImageView(container.getContext());
        img.setBackgroundColor(Color.parseColor("#FFFFFF"));
        //img.setScaleType(ImageView.ScaleType.FIT_XY);
        //img.setImageResource(images[position]);

        Picasso.with(mContext)
                .load(IMAGELINK+imageList.get(position))
                .placeholder(R.drawable.img_loding)
                .fit()
                .into(img);

        container.addView(img, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void startUpdate(ViewGroup container) {
        // TODO Auto-generated method stub
        super.startUpdate(container);
    }


}