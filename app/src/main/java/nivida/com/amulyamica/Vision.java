package nivida.com.amulyamica;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Vision extends AppCompatActivity {
    TextView textView,txtTitle;
    ImageView image;
    WebView webView;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);
        setActionBar();
        fetchId();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void fetchId() {
        textView=(TextView)findViewById(R.id.text);
        txtTitle=(TextView)findViewById(R.id.txtTitle);
        image=(ImageView)findViewById(R.id.image);
        webView=(WebView) findViewById(R.id.webView);

        webView.setWebViewClient(new MyBrowser());

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);

        new GetCMSInformation("12").execute();
    }

    private class GetCMSInformation extends AsyncTask<Void, Void, String> {

        String pageID="";

        public GetCMSInformation(String pageID) {
            this.pageID = pageID;
        }

        @Override
        protected String doInBackground(Void... params) {
            List<NameValuePair> pairList=new ArrayList<>();
            pairList.add(new BasicNameValuePair("id",pageID));

            String json=new ServiceHandler().makeServiceCall(Globals.LINK+"InformationPage/App_GetInformationPageContent",ServiceHandler.POST,pairList);

            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("JSON DATA","--"+s);

            try {
                JSONObject object=new JSONObject(s);

                if(!object.getBoolean("status")){
                    Globals.Toast(getApplicationContext(),object.getString("message"));
                }
                else {
                    JSONObject data=object.getJSONObject("data");

                    String title=data.getString("title");
                    String description=data.getString("description");

                    String titles[]= title.split(" ");

                    String newTitle="";

                    for(int i=0; i<titles.length; i++){
                        if(i==0)
                            newTitle += "<font color=#666666>"+titles[i]+"</font> ";
                        else
                            newTitle += "<font color=#e77716>"+titles[i]+"</font> ";
                    }

                    txtTitle.setText(Html.fromHtml(newTitle));

                    webView.loadData(description,"text/html","UTF-8");
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Exception",e.getMessage());
            }
        }
    }

    public class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("MyURL",url);

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

            //webView.loadUrl(url);

            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Log.e(" page url",url);
        }

        @Override
        public void onPageFinished(WebView view, final String url) {
            Log.e("page url",url);

        }
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
