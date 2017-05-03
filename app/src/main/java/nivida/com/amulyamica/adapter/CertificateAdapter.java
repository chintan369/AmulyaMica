package nivida.com.amulyamica.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nivida.com.amulyamica.CertificatesViewerActivity;
import nivida.com.amulyamica.R;
import nivida.com.amulyamica.models.CertificateItem;

import static nivida.com.amulyamica.Globals.IMAGELINK;

/**
 * Created by Nivida new on 18-Feb-17.
 */

public class CertificateAdapter extends BaseAdapter {

    List<CertificateItem> certificateItemList=new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public CertificateAdapter(List<CertificateItem> certificateItemList, Context context) {
        this.certificateItemList = certificateItemList;
        this.context = context;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return certificateItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return certificateItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(R.layout.grid_certificate_item,parent,false);

        ImageView img_CertiImage=(ImageView) view.findViewById(R.id.img_CertiImage);
        TextView txt_CartiName=(TextView) view.findViewById(R.id.txt_CertiName);

        Picasso.with(context).load(IMAGELINK+certificateItemList.get(position).getImage())
                .into(img_CertiImage);

        txt_CartiName.setText(certificateItemList.get(position).getTitle());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> certiImages=new ArrayList<String>();
                ArrayList<String> certiNames=new ArrayList<String>();

                for(int i=0; i<certificateItemList.size(); i++){
                    certiNames.add(certificateItemList.get(i).getTitle());
                    certiImages.add(certificateItemList.get(i).getImage());
                }

                Intent intent=new Intent(context, CertificatesViewerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("selectedPosition",position);
                intent.putStringArrayListExtra("certiNames",certiNames);
                intent.putStringArrayListExtra("certiImages",certiImages);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
