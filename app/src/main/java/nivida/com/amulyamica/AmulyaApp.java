package nivida.com.amulyamica;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by SEO on 10/26/2016.
 */

public class AmulyaApp extends Application {
    private static final String TAG = "DEFAULT";
    private static AmulyaApp mInstance;
    private static RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        Fresco.initialize(this);
    }

    public static synchronized AmulyaApp getInstance(){
        return mInstance;
    }

    public RequestQueue getRequesetQueue(){
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(this.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        request.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequesetQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequesetQueue().add(request);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }
}
