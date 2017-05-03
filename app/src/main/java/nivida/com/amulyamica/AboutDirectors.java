package nivida.com.amulyamica;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutDirectors extends AppCompatActivity {
    TextView textView,textViewdetails,textView2,textViewdetails2,textView3,textViewdetails3,textView4,textViewdetails4;
    ImageView image,image2,image3,image4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_directors);
        setActionBar();
        fetchId();
    }
    private void fetchId() {
        textView=(TextView)findViewById(R.id.text);
        image=(ImageView)findViewById(R.id.image);
        textViewdetails=(TextView)findViewById(R.id.text_details);
        textView2=(TextView)findViewById(R.id.text2);
        image2=(ImageView)findViewById(R.id.image2);
        textViewdetails2=(TextView)findViewById(R.id.text_details2);
        textView3=(TextView)findViewById(R.id.text3);
        image3=(ImageView)findViewById(R.id.image3);
        textViewdetails3=(TextView)findViewById(R.id.text_details3);
        textView4=(TextView)findViewById(R.id.text4);
        image4=(ImageView)findViewById(R.id.image4);
        textViewdetails4=(TextView)findViewById(R.id.text_details4);

        image.setImageResource(R.drawable.img_1);
        textView.setText("SRI OMPRAKASH AGARWAL, Chairman");
        textViewdetails.setText("Aged 64 years and having enormous experience in business for more than 40 years in Assam in the trade of Hardware and Electricals. He is at present looking after distribution of the products of the company at an exclusive Show Room located at Gandhidham in Kutch District of Gujarat");
        image2.setImageResource(R.drawable.img_2);
        textView2.setText("SRI GIRDHARILAL DOKANIA, C.A., Director");
        textViewdetails2.setText("Aged 56 years having vast experience of more than 20 years in Business Management and different Accounts Management Systems. He also adopts various Management Policies for better management and developmental activities");
        image3.setImageResource(R.drawable.img_3);
        textView3.setText("SRI RAKESH AGARWAL, B. Com (Hons), Managing Director.");
        textViewdetails3.setText("Aged 39 years is looking after the commercial affairs and adopts the marketing strategies of the Company. He is an energetic young man and has set up 5 Companies under his dynamic leadership. He is also a recipient of coveted award of Bharat Udyog Ratan from the Indian Development and Research Association, New Delhi as well as Bharatiya Udyog Ratan Award from Achievers Forum, New Delhi. He has also been adjudged as Brand Ambassador of Corporation Bank, Gandhidham.");
        image4.setImageResource(R.drawable.img_4);
        textView4.setText("SRI MUKESH AGARWAL, B.Com, Director.");
        textViewdetails4.setText("Aged 37 years looking after day to day management of productivity since he is having good experience in this department from North-east for more than a decade.");

    }

    private void setActionBar() {

        // TODO Auto-generated method stub
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.actionbar_design);


        View mCustomView = mActionBar.getCustomView();
        ImageView image_drawer = (ImageView) mCustomView.findViewById(R.id.image_drawer);
        ImageView img_btllogo = (ImageView) mCustomView.findViewById(R.id.img_btllogo);
        ImageView img_home = (ImageView) mCustomView.findViewById(R.id.img_home);

        image_drawer.setImageResource(R.drawable.back);
        image_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // app =new AppPrefs(E_GalleryScreen.this);
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
    @Override
    public void onBackPressed() {

        finish();
    }
}
