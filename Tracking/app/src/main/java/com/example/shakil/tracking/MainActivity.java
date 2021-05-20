package com.example.shakil.tracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView txtCurrentLatitude;
    private TextView txtCurrentLongitude;
    private Button btnCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GPSTracker gpsTracker = new GPSTracker(this);

        txtCurrentLatitude = findViewById(R.id.txt_current_latitude);
        txtCurrentLongitude = findViewById(R.id.txt_current_longitude);
        btnCurrentLocation = findViewById(R.id.btn_current_location);

        if(gpsTracker.getIsGPSTrackingEnabled()){


            btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String latitude = String.valueOf(gpsTracker.latitude);
                    String longitude = String.valueOf(gpsTracker.longitude);
                    txtCurrentLatitude.setText(latitude);
                    txtCurrentLongitude.setText(longitude);

                }
            });

        }
    }
}

