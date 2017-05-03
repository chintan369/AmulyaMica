package nivida.com.amulyamica;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nivida new on 23-Aug-16.
 */
public class WebServiceCaller {


    String jsonData="";



    public static String CheckUniqueMobile(final String phone, final String user_id){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("phone_no", "" + phone));

                    if(!user_id.equalsIgnoreCase("0"))
                        parameters.add(new BasicNameValuePair("user_id",user_id));

                    //Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                    Log.e("params",""+parameters.toString());

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_CheckUniqueMobile",ServiceHandler.POST,parameters);
                    //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();

        while (notDone[0]){

        }

        return json[0];
    }

    public static String CheckUniqueEmail(final String emailid){

        final String[] json = new String[1];
        final boolean[] notDone = {true};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    List<NameValuePair> parameters = new ArrayList<NameValuePair>();
                    parameters.add(new BasicNameValuePair("email_id", "" + emailid));

                    //Log.e("", "" + firstname + "- " + lastname + "- " + gender + "- " + mobile);
                    Log.e("params",""+parameters.toString());

                    json[0] = new ServiceHandler().makeServiceCall(Globals.server_link+"amulya/User/App_CheckUniqueEmail",ServiceHandler.POST,parameters);
                    //String json = new ServiceHandler.makeServiceCall(GlobalVariable.link+"App_Registration",ServiceHandler.POST,params);
                    System.out.println("array: " + json[0]);
                    notDone[0] =false;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error1: " + e.toString());
                    notDone[0]=false;

                }
            }
        });
        thread.start();

        while (notDone[0]){

        }

        return json[0];
    }
}
