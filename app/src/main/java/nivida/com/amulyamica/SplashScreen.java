package nivida.com.amulyamica;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {
    AppPrefs prefs;

    boolean fromNotification=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        prefs=new AppPrefs(getApplicationContext());

        final Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        if(bundle!=null){
            fromNotification=bundle.getBoolean("fromNotification");
        }



        new CountDownTimer(2500, 1300) {

            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                prefs = new AppPrefs(SplashScreen.this);
                if (prefs.getis_verify().toString().equalsIgnoreCase("") || prefs.isLoggedin()==false) {
                    Intent iGo = new Intent(SplashScreen.this,
                            Login.class);
                    iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(iGo);
                    SplashScreen.this.finish();
                } else if (prefs.getis_verify().toString().equalsIgnoreCase("1") || prefs.isLoggedin()==true) {
                    Intent iGo = new Intent(SplashScreen.this,
                            MainActivity.class);
                    iGo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    if(fromNotification){
                        iGo.putExtra("fromNotification",true);
                        iGo.putExtra("title",bundle.getString("title"));
                        iGo.putExtra("message",bundle.getString("message"));
                    }


                    startActivity(iGo);
                    SplashScreen.this.finish();
                }

            }

        }.start();
    }
}
