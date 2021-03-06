package com.yj.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

public class MainActivity1 extends AppCompatActivity {

    private EditText edt_address;
    private EditText edt_lat;
    private EditText edt_lon;
    private Button btn_next;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        edt_address = (EditText)findViewById(R.id.edt_address);
        edt_lat = (EditText)findViewById(R.id.edt_lat);
        edt_lon = (EditText)findViewById(R.id.edt_lon);
        btn_next = (Button)findViewById(R.id.btn_next);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, MainActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch(requestCode) {
            case SEARCH_ADDRESS_ACTIVITY :
                if(resultCode == RESULT_OK){
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        edt_address.setText(data);

                        List<Address> list = null;
                        final Geocoder geocoder = new Geocoder(this);
                        String str = edt_address.getText().toString();
                        if(str != null) {
                            try {
                                //getFromLocationName(?????? ??????, ?????? ??????)
                                list = geocoder.getFromLocationName( str,1); // ?????? ??????
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("test","????????? ?????? - ???????????? ??????????????? ????????????");
                            }

                            if (list != null) {
                                if (list.size() == 0) {
                                    edt_lat.setText("???????????? ?????? ????????? ????????????");
                                } else {
                                    Log.v("?????? :::", String.valueOf(list.get(0).getLatitude()));
                                    Log.v("?????? :::", String.valueOf(list.get(0).getLongitude()));
                                    //list.get(0).getCountryName();  // ?????????
                                    String a = list.get(0).getLatitude()+" "; //??????
                                    String b = list.get(0).getLongitude()+" "; // ??????
                                    edt_lat.setText(a);
                                    edt_lon.setText(b);
                                }
                            }
                        }
                    }
                }
            break;
        }
    }
}