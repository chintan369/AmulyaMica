package nivida.com.amulyamica;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SampleUrvi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_urvi);
        init();
    }
    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.GREEN);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Product ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
//        TextView tv2 = new TextView(this);
//        tv2.setText(" Unit Price ");
//        tv2.setTextColor(Color.WHITE);
//        tbrow0.addView(tv2);
//        TextView tv3 = new TextView(this);
//        tv3.setText(" Stock Remaining ");
//        tv3.setTextColor(Color.WHITE);
//        tbrow0.addView(tv3);
//        stk.addView(tbrow0);
        for (int i = 0; i < 4; i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.LEFT);
            t1v.setGravity(Gravity.START);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText("Product " + i);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.RIGHT);
            tbrow.addView(t2v);
//            TextView t3v = new TextView(this);
//            t3v.setText("Rs." + i);
//            t3v.setTextColor(Color.WHITE);
//            t3v.setGravity(Gravity.CENTER);
//            tbrow.addView(t3v);
//            TextView t4v = new TextView(this);
//            t4v.setText("" + i * 15 / 32 * 10);
//            t4v.setTextColor(Color.WHITE);
//            t4v.setGravity(Gravity.CENTER);
//            tbrow.addView(t4v);
            t1v.setPadding(20, 3, 20, 3);
            t2v.setPadding(20, 3, 20, 3);
            stk.addView(tbrow);
        }

    }
}
