package nivida.com.amulyamica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class VirtualRoomActivity extends AppCompatActivity {

    WebView web_eGallery;
    ImageView img_closeWeb;

    Custom_ProgressDialog dialog;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virtual_room);

        web_eGallery=(WebView) findViewById(R.id.web_eGallery);
        img_closeWeb=(ImageView) findViewById(R.id.img_closeWeb);
        dialog=new Custom_ProgressDialog(this,"Wait while Loading...");
        dialog.setCancelable(true);
        dialog.setIndeterminate(false);

        setActionBar();

        img_closeWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        web_eGallery.setWebViewClient(new MyBrowser());

        web_eGallery.getSettings().setLoadsImagesAutomatically(true);
        web_eGallery.getSettings().setJavaScriptEnabled(true);
        web_eGallery.setWillNotCacheDrawing(true);
        web_eGallery.destroyDrawingCache();
        web_eGallery.getSettings().setAppCacheEnabled(false);
        web_eGallery.getSettings().setBuiltInZoomControls(true);
        web_eGallery.getSettings().setAllowContentAccess(true);
        web_eGallery.getSettings().setAllowFileAccess(true);
        web_eGallery.getSettings().setAllowFileAccessFromFileURLs(true);
        web_eGallery.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web_eGallery.getSettings().setLoadWithOverviewMode(true);
        web_eGallery.getSettings().setDomStorageEnabled(true);
        web_eGallery.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        web_eGallery.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);

        web_eGallery.setWebChromeClient(new WebChromeClient(){

        });

        //web_eGallery.loadUrl("http://app.nivida.in/amulya_visualizer/");
        web_eGallery.loadDataWithBaseURL("http://app.nivida.in/amulya_visualizer/",null,"text/html","UTF-8",null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_visualizer,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_refresh){
            //web_eGallery.loadUrl("http://app.nivida.in/amulya_visualizer/");
            web_eGallery.loadDataWithBaseURL("http://app.nivida.in/amulya_visualizer/",null,"text/html","UTF-8",null);
        }

        return true;
    }

    public class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("MyURL",url);
            web_eGallery.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            dialog.show();
            Log.e(" page url",url);
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            Log.e("page url",url);
            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if(web_eGallery.canGoBack()){
            web_eGallery.goBack();
        }
        else {
            finish();
        }
    }
}
