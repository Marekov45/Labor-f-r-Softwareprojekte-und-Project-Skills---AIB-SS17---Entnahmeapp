package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.os.Bundle;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Marvin on 30.04.2017.
 */
public class PrimerList extends AppCompatActivity {

    private Spinner spinner;
    private TextView txtResult;
    private Button scanButton;
    private String[] items = {"Entnommen","Nicht Entnommen"};
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primerlist);

        scanButton = (Button) findViewById(R.id.scan);
        txtResult = (TextView) findViewById(R.id.txtResult);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimerList.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PrimerList.this,
                android.R.layout.simple_spinner_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
                if(data != null){
                    final Barcode barcode = data.getParcelableExtra("barcode");
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            txtResult.setText(barcode.displayValue);
                        }
                    });
                }
            }
        }






    }






