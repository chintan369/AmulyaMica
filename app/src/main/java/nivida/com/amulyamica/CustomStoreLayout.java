package nivida.com.amulyamica;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;

/**
 * Created by SEO on 12/7/2016.
 */

public class CustomStoreLayout extends BaseAdapter {
    List<Bean_Stores> bean_storesList;
    //Bean_Categories bean_categories;
    Context context;
    Activity activity;
    //DisSalesCallback disSalesCallback;
    AppPrefs prefs;
    String userID;
    //ArrayList<String> array_Listimage;

    public CustomStoreLayout(Context context, List<Bean_Stores> bean_storesList, Activity activity) {
        this.context = context;
        this.bean_storesList = bean_storesList;
        this.activity = activity;
    }


    @Override
    public int getCount() {
        return bean_storesList.size();
    }

    @Override
    public Object getItem(int position) {
        return bean_storesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view;


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_store_address, parent, false);


        final Bean_Stores addressList = bean_storesList.get(position);
        TextView contactPerson = (TextView) view.findViewById(R.id.txtName);
        TextView txt_viewMap = (TextView) view.findViewById(R.id.txt_viewMap);
        TextView shopName = (TextView) view.findViewById(R.id.txtShop);
        TextView storeAddress = (TextView) view.findViewById(R.id.txtAddress);
        LinearLayout layout_name = (LinearLayout) view.findViewById(R.id.layout_name);
        LinearLayout layout_mobile = (LinearLayout) view.findViewById(R.id.layout_mobile);
        LinearLayout layout_email = (LinearLayout) view.findViewById(R.id.layout_email);

        TextView txtMobile = (TextView) view.findViewById(R.id.txtMobile);
        TextView txtEmail = (TextView) view.findViewById(R.id.txtEmail);

        txt_viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUri = bean_storesList.get(position).getMapLink();
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent);
            }
        });

        txtEmail.setText(addressList.getEmail_id());

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, addressList.getEmail_id());
                context.startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        txtMobile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+addressList.getMobile().trim()));
                if (ActivityCompat.checkSelfPermission(context, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(intent);
                }
                else {
                    activity.requestPermissions(new String[]{CALL_PHONE},1);
                }

            }
        });

        if(addressList.getEmail_id().isEmpty() || addressList.getEmail_id().equalsIgnoreCase("null")){
            layout_email.setVisibility(View.GONE);
        }

        contactPerson.setText(addressList.getContact_person());
        shopName.setText(addressList.getBranch_name());
        storeAddress.setText(addressList.getAddress()+" "+"-("+addressList.getPincode()+"), "+addressList.getState());
        txtMobile.setText(addressList.getMobile());

        if(addressList.getContact_person().isEmpty())
            layout_name.setVisibility(View.GONE);

        if(addressList.getMobile().isEmpty() || addressList.getMobile().equalsIgnoreCase("null"))
            layout_mobile.setVisibility(View.GONE);

        if(addressList.getMapLink().isEmpty())
            txt_viewMap.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.d("Size in Adapter",bean_storesList.size()+"");
    }

    public void updateData(List<Bean_Stores> bean_storesList)
    {
        this.bean_storesList=bean_storesList;
        notifyDataSetChanged();
    }

}

