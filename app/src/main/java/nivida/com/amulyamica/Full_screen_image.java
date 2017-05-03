package nivida.com.amulyamica;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class Full_screen_image extends AppCompatActivity {


    ImageView im_menu,im_back,im_logout,im_history,im_pending_meeting;
    AppPrefs pref;
    TouchImageView img_fullZoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
       // setToolbar();
        Intent intent = getIntent();
        String image = intent.getExtras().getString("imagePaths");
        pref=new AppPrefs(getApplicationContext());

        Log.e("lonng",""+image);




        img_fullZoom=(TouchImageView)findViewById(R.id.img_fullZoom);

        Picasso.with(this)
                .load(image)
                .placeholder(R.drawable.img_loding)
                .error(R.drawable.img_loding)
                .into(img_fullZoom);


    }

    public void onBackPressed() {

        finish();
    }

  /*  private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }*/
}
