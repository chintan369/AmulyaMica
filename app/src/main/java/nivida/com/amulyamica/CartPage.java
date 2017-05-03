package nivida.com.amulyamica;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.Serializable;

public class CartPage extends AppCompatActivity implements Serializable {
    LinearLayout check;
    AppPrefs prefs;
    Bean_LaminateList bean_laminateList;
    Bean_Categories categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);
        setActionBar();
        Intent intent=getIntent();
        bean_laminateList=(Bean_LaminateList) intent.getSerializableExtra("laminate");
        categories=(Bean_Categories) intent.getSerializableExtra("category");
        prefs=new AppPrefs(getApplicationContext());
        check=(LinearLayout)findViewById(R.id.checkout);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartPage.this, ThankyouPage.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
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
                if(prefs.getPage().equalsIgnoreCase("list"))
                {
                    Intent intent=new Intent(CartPage.this,LaminateList.class);
                    intent.putExtra("category",categories);
                    startActivity(intent);
                    finish();
                }
                if(prefs.getPage().equalsIgnoreCase("descp"))
                {
                    Intent intent=new Intent(CartPage.this,DescriptionPage.class);
                    startActivity(intent);
                    finish();
                }

            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartPage.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if(prefs.getPage().equalsIgnoreCase("list"))
        {
            Intent intent=new Intent(CartPage.this,LaminateList.class);
            startActivity(intent);
            finish();
        }
        if(prefs.getPage().equalsIgnoreCase("descp"))
        {
            Intent intent=new Intent(CartPage.this,DescriptionPage.class);
            startActivity(intent);
            finish();
        }

    }
}
