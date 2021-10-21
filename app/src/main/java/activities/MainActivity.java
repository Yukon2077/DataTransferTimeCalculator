package activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.yukon.activities.R;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    MaterialToolbar toolbar;
    TextInputLayout transferSpeedTextLayout, fileSizeTextLayout;
    SwitchMaterial transferSpeedSwitch, fileSizeSwitch;
    RadioGroup transferSpeedRadioGroup, fileSizeRadioGroup;
    RadioButton transferSpeedNone, transferSpeedKilo, transferSpeedMega,
            transferSpeedGiga, transferSpeedTera, fileSizeNone, fileSizeKilo,
            fileSizeMega, fileSizeGiga, fileSizeTera;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result = findViewById(R.id.result);

        transferSpeedTextLayout = findViewById(R.id.transferSpeed);
        transferSpeedSwitch = findViewById(R.id.transferSpeedByteOrBitSwitch);
        transferSpeedRadioGroup = findViewById(R.id.transferSpeedRadioGroup);
        transferSpeedNone = findViewById(R.id.transferSpeedNone);
        transferSpeedKilo = findViewById(R.id.transferSpeedKilo);
        transferSpeedMega = findViewById(R.id.transferSpeedMega);
        transferSpeedGiga = findViewById(R.id.transferSpeedGiga);
        transferSpeedTera = findViewById(R.id.transferSpeedTera);

        fileSizeTextLayout = findViewById(R.id.fileSize);
        fileSizeSwitch = findViewById(R.id.fileSizeByteOrBitSwitch);
        fileSizeRadioGroup = findViewById(R.id.fileSizeRadioGroup);
        fileSizeNone = findViewById(R.id.fileSizeNone);
        fileSizeKilo = findViewById(R.id.fileSizeKilo);
        fileSizeMega = findViewById(R.id.fileSizeMega);
        fileSizeGiga = findViewById(R.id.fileSizeGiga);
        fileSizeTera = findViewById(R.id.fileSizeTera);

        transferSpeedSwitch.setOnCheckedChangeListener(this);
        fileSizeSwitch.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.transferSpeedByteOrBitSwitch:
                if(isChecked){
                    transferSpeedNone.setText("bps");
                    transferSpeedKilo.setText("Kbps");
                    transferSpeedMega.setText("Mbps");
                    transferSpeedGiga.setText("Gbps");
                    transferSpeedTera.setText("Tbps");
                } else {
                    transferSpeedNone.setText("Bps");
                    transferSpeedKilo.setText("KBps");
                    transferSpeedMega.setText("MBps");
                    transferSpeedGiga.setText("GBps");
                    transferSpeedTera.setText("TBps");
                }
                break;
            case R.id.fileSizeByteOrBitSwitch:
                if(isChecked){
                    fileSizeNone.setText("b");
                    fileSizeKilo.setText("Kb");
                    fileSizeMega.setText("Mb");
                    fileSizeGiga.setText("Gb");
                    fileSizeTera.setText("Tb");
                } else {
                    fileSizeNone.setText("B");
                    fileSizeKilo.setText("KB");
                    fileSizeMega.setText("MB");
                    fileSizeGiga.setText("GB");
                    fileSizeTera.setText("TB");
                }
                break;
        }
    }

    public void calculate(View view) {
        Boolean isGood = true;
        if (transferSpeedTextLayout.getEditText().getText().toString().equals("")) {
            transferSpeedTextLayout.setError("Transfer Speed is Empty");
            isGood = false;
        } else {
            transferSpeedTextLayout.setError(null);
        }
        if (fileSizeTextLayout.getEditText().getText().toString().equals("")) {
            fileSizeTextLayout.setError("File Size is Empty");
            isGood = false;
        } else {
            fileSizeTextLayout.setError(null);
        }

        if (!isGood) {
            return;
        }

        float transferSpeed, fileSize;
        transferSpeed = Float.parseFloat(transferSpeedTextLayout.getEditText().getText().toString());
        fileSize = Float.parseFloat(fileSizeTextLayout.getEditText().getText().toString());
        if (transferSpeed==0) {
            result.setText("Infinte Time");
            return;
        }
        if(!fileSizeSwitch.isChecked()) {
            fileSize = fileSize * 8;
        }
        switch (fileSizeRadioGroup.getCheckedRadioButtonId()) {
            case R.id.fileSizeNone:
                break;
            case R.id.fileSizeKilo:
                fileSize = fileSize * 1024;
                break;
            case R.id.fileSizeMega:
                fileSize = fileSize * 1024 * 1024;
                break;
            case R.id.fileSizeGiga:
                fileSize = fileSize * 1024 * 1024 * 1024;
                break;
            case R.id.fileSizeTera:
                fileSize = fileSize * 1024 * 1024 * 1024 * 1024;
                break;
        }

        if(!transferSpeedSwitch.isChecked()) {
            transferSpeed = transferSpeed * 8;
        }
        switch (transferSpeedRadioGroup.getCheckedRadioButtonId()) {
            case R.id.transferSpeedNone:
                break;
            case R.id.transferSpeedKilo:
                transferSpeed = transferSpeed * 1024;
                break;
            case R.id.transferSpeedMega:
                transferSpeed = transferSpeed * 1024 * 1024;
                break;
            case R.id.transferSpeedGiga:
                transferSpeed = transferSpeed * 1024 * 1024 * 1024;
                break;
            case R.id.transferSpeedTera:
                transferSpeed = transferSpeed * 1024 * 1024 * 1024 * 1024;
                break;
        }

        float timeInSeconds = (fileSize/transferSpeed);
        long years, days, hours, minutes, seconds;
        seconds = (long) (timeInSeconds % 60);
        minutes = (long) ((timeInSeconds / 60) % 60);
        hours = (long) ((timeInSeconds / (60 * 60)) % 24);
        days = (long) ((timeInSeconds / (60 * 60 * 24)) % 365);
        years = (long) timeInSeconds / (60 * 60 * 24 * 365);
        String time = "";
        if(years>0) {
            time += years;
            if(years==1) {
                time += " year ";
            } else {
                time += " years ";
            }
        }
        if(days>0) {
            time += days;
            if(days==1) {
                time += " day ";
            } else {
                time += " days ";
            }
        }
        if(hours>0) {
            time += hours;
            if(hours==1) {
                time += " hour ";
            } else {
                time += " hours ";
            }
        }
        if(minutes>0) {
            time += minutes;
            if(minutes==1) {
                time += " minute ";
            } else {
                time += " minutes ";
            }
        }
        if(seconds>=0) {
            time += seconds;
            if(seconds==1) {
                time += " second ";
            } else {
                time += " seconds ";
            }
        }
        result.setText(time);

    }
}