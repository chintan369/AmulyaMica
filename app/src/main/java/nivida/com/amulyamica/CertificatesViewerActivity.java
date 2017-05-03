package nivida.com.amulyamica;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nivida.com.amulyamica.adapter.ImageSliderAdapter;

public class CertificatesViewerActivity extends AppCompatActivity {

    ViewPager imageViewer;
    TextView txt_CertiName;

    ImageSliderAdapter sliderAdapter;

    int selectedPosition=0;
    ArrayList<String> certiNames=new ArrayList<>();
    ArrayList<String> certiImages=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificates_viewer);

        Intent intent=getIntent();
        selectedPosition=intent.getIntExtra("selectedPosition",0);
        certiNames=intent.getStringArrayListExtra("certiNames");
        certiImages=intent.getStringArrayListExtra("certiImages");

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

        img_home.setVisibility(View.GONE);

        image_drawer.setImageResource(R.drawable.back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    private void fetchIDs() {
        imageViewer=(ViewPager) findViewById(R.id.imageViewer);
        txt_CertiName=(TextView) findViewById(R.id.txt_CertiName);

        txt_CertiName.setText(certiNames.get(selectedPosition));

        sliderAdapter=new ImageSliderAdapter(getApplicationContext(),certiImages,this);
        imageViewer.setAdapter(sliderAdapter);

        imageViewer.setCurrentItem(selectedPosition,true);

        imageViewer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txt_CertiName.setText(certiNames.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
