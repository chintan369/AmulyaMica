package nivida.com.amulyamica;

import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nivida.com.amulyamica.adapter.CertificateAdapter;
import nivida.com.amulyamica.models.CertificateItem;

import static nivida.com.amulyamica.Globals.LINK;
import static nivida.com.amulyamica.Globals.Toast;

public class CertificatesActivity extends AppCompatActivity {

    GridView grid_Certificates;
    List<CertificateItem> certificateItemList=new ArrayList<>();
    CertificateAdapter certificateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates);
        setActionBar();
        fetchIDs();
    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);

        image_drawer.setImageResource(R.drawable.back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void fetchIDs() {
        grid_Certificates=(GridView) findViewById(R.id.grid_Certificates);
        certificateAdapter=new CertificateAdapter(certificateItemList,getApplicationContext());
        grid_Certificates.setAdapter(certificateAdapter);

        new GetCertificates().execute();
    }

    private class GetCertificates extends AsyncTask<Void, Void, String>{

        Custom_ProgressDialog dialog;

        public GetCertificates() {
            dialog=new Custom_ProgressDialog(CertificatesActivity.this,"");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String json=new ServiceHandler().makeServiceCall(LINK+"Certificate/App_get_Certificate",ServiceHandler.POST);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            certificateItemList.clear();


            if(s!=null && !s.isEmpty()){
                try{
                    JSONObject object=new JSONObject(s);

                    if(object.getBoolean("status")){
                        JSONArray data=object.getJSONArray("data");

                        for(int i=0; i<data.length(); i++){
                            JSONObject main=data.getJSONObject(i);

                            CertificateItem item=new CertificateItem();
                            item.setId(main.getString("id"));
                            item.setTitle(main.getString("title"));
                            item.setImage(main.getString("image"));

                            certificateItemList.add(item);
                        }
                    }
                    else {
                        Toast(getApplicationContext(),object.getString("message"));
                    }
                }catch (JSONException j){
                    Log.e("Exception",j.getMessage());
                }
            }

            certificateAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
