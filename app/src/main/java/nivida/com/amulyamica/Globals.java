package nivida.com.amulyamica;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import java.util.regex.Pattern;

/**
 * Created by chaitalee on 3/14/2016.
 */
public class Globals {

    public static String share ="https://play.google.com/store/apps/details?id=nivida.com.amulyamica&hl=en";

    public static String server_link ="http://app.nivida.in/";
    public static final String LINK="http://app.nivida.in/amulya/";
    public static String IMAGELINK= LINK+"files/";
    public static String SENDINQUIRY= "Contact/App_Send_Inquiry";
    public static final int POST= Request.Method.POST;
    //public static String server_link ="http://192.168.1.221/";



    static boolean connect = true;
    public static boolean isConnectingToInternet1(Context con){

        BroadcastReceiver mConnReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {


                boolean noConnectivity = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
	        /*String reason = intent
	                .getStringExtra(ConnectivityManager.EXTRA_REASON);*/
                boolean isFailover = intent.getBooleanExtra(
                        ConnectivityManager.EXTRA_IS_FAILOVER, false);

            //    ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);



                @SuppressWarnings("deprecation")
                NetworkInfo currentNetworkInfo = (NetworkInfo) intent
                        .getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                // NetworkInfo otherNetworkInfo = (NetworkInfo)
                // intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

                if (noConnectivity){
                    connect = false;

                }
                else if (currentNetworkInfo.isConnected()) {
                    connect = true;


                } else if (isFailover) {
                    connect = false;
                }  else {
                    connect = true;
                }
            }
        };
        return connect;
    }
    public static boolean isConnectingToInternet(Context con){
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }

    public static void CustomToast(Context context, String st,
                                   LayoutInflater inflater) {

        View view = inflater.inflate(R.layout.toast_custom, null);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(st);
        text.setTypeface(Globals.railway(context));
        text.setTextSize(14);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

    }

    public static boolean validEmail(String email) {
        return !TextUtils.isEmpty(email) && EMAIL_ADDRESS.matcher(email).matches();
    }

    public static final Pattern EMAIL_ADDRESS
            = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,64}" + "(" +
            "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,5}" + ")+"
    );

    public static void Toast(Context context, String st) {

        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_custom, null);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(st);
        text.setTypeface(Globals.railway(context));
        text.setTextSize(14);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();

    }

    public static Typeface railway(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Medium.ttf");
    }

    public static boolean isOnline(Context con) {
        ConnectivityManager connectivity = (ConnectivityManager) con
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

}
