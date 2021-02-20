package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yukon.downloadtimecalculator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void calculate(View view) {
        TextView Txv = (TextView) findViewById(R.id.textView);
        EditText edsz = (EditText) findViewById(R.id.editSize);
        EditText edsp = (EditText) findViewById(R.id.editSpeed);
        Context context = getApplicationContext();
        int dur = Toast.LENGTH_SHORT;
        Toast toast2 = Toast.makeText(context,"Please enter Network Speed",dur);
        if (edsp.getText().length() == 0){
            toast2.show();
            return;
        }
        Toast toast1 = Toast.makeText(context,"Please enter File Size",dur);
        if (edsz.getText().length() == 0) {
            toast1.show();
            return;
        }
        double szi =  Double.parseDouble(edsz.getText().toString());
        double spi = Double.parseDouble(edsp.getText().toString());
        if (spi==0){
            Txv.setText("Infinity");
            return;
        }
        Spinner s1 = (Spinner) findViewById(R.id.spinner1);
        Spinner s2 = (Spinner) findViewById(R.id.spinner2);
        String ss1 = s1.getItemAtPosition(s1.getSelectedItemPosition()).toString();
        spi = ConverttoB(spi,ss1);
        String ss2 = s2.getItemAtPosition(s2.getSelectedItemPosition()).toString();
        szi = ConverttoB(szi,ss2);
        if( (ss1.equals("bps")||ss1.equals("Kbps")||ss1.equals("Mbps")||ss1.equals("Gbps")||ss1.equals("Tbps")) ^ (ss2.equals("b")||ss2.equals("Kb")||ss2.equals("Mb")||ss2.equals("Gb")||ss2.equals("Tb") )) {
            if (ss1.equals("bps")||ss1.equals("Kbps")||ss1.equals("Mbps")||ss1.equals("Gbps")||ss1.equals("Tbps")){
                spi = spi/8;
            }
            if (ss2.equals("b")||ss2.equals("Kb")||ss2.equals("Mb")||ss2.equals("Gb")||ss2.equals("Tb") ){
                szi = szi/8;
            }
        }

        double result = szi / spi ;
        String time = caltime(result);
        Txv.setText(time);
    }

    public double ConverttoB(double a, String s){

        if(s.equals("KBps") || s.equals("Kbps") || s.equals("KB") || s.equals("Kb"))
            return a*1024;
        else if(s.equals("MBps") || s.equals("Mbps") || s.equals("MB") || s.equals("Mb"))
            return a*1024*1024;
        else if(s.equals("GBps") || s.equals("Gbps") || s.equals("GB") || s.equals("Gb"))
            return a*1024*1024*1024;
        else if(s.equals("TBps") || s.equals("Tbps") || s.equals("TB") || s.equals("Tb"))
            return a*1024*1024*1024*1024;
        else
            return a;
    }
    public String caltime( double x){
        String t="";
        double t1=x,t2=x,t3=x,t4=x,t5=x,t6=x,t7=x,t8=x;
        if (x>31536000){
            t1=x/31536000;
            t2=x%31536000;
            int tn1 = (int) t1;
            if  (tn1==1){
                t += String.valueOf(tn1) + " year, ";
            }
            else {
                t += String.valueOf(tn1) + " years, ";
            }
        }

        if (x>86400){
            t3=t2/86400;
            t4=t2%86400;
            int tn3 = (int) t3;
            if (tn3==1){
                t += String.valueOf(tn3) + " day, ";
            }
            else {
                t += String.valueOf(tn3) + " days, ";
            }
        }
        if (x>3600){
            t5=t4/3600;
            t6=t4%3600;
            int tn5 = (int) t5;
            if (tn5==1){
                t += String.valueOf(tn5) + " hour, ";
            }
            else {
                t += String.valueOf(tn5) + " hours, ";
            }
        }
        if (x>60){
            t7=t6/60;
            t8=t6%60;
            int tn7 = (int) t7;
            if (tn7==1){
                t += String.valueOf(tn7) + " minute, ";
            }
            else {
                t += String.valueOf(tn7) + " minutes, ";
            }
        }
        int tn8 = (int) t8;
        if (tn8==1){
            t += String.valueOf(tn8) + " second";
        }
        else {
            t += String.valueOf(tn8) + " seconds";
        }
        return t;
    }

}