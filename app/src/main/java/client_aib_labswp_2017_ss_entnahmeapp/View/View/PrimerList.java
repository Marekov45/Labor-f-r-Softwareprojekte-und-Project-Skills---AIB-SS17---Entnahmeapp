package client_aib_labswp_2017_ss_entnahmeapp.View.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.os.Bundle;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.CustomObserver;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.ServerAPI.ListImpl;
import client_aib_labswp_2017_ss_entnahmeapp.View.Controller.enumResponseCode.ResponseCode;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.User;
import client_aib_labswp_2017_ss_entnahmeapp.View.Model.model_List.PickList;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

/**
 * Created by Marvin on 30.04.2017.
 */
public class PrimerList extends AppCompatActivity implements CustomObserver {


    private TextView txtResult;
    private Button scanButton;
    private Button bListeAnzeigen;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    private ListImpl listImpl;
    private RadioGroup listGroup;
    private RadioButton radioRoboter;
    private RadioButton radioManual;
    private RadioButton radioExtra;
    private RadioButton radioAllLists;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primerlist);
        final User uobj = getIntent().getParcelableExtra("USER");

        listView = (ListView) findViewById(R.id.listv);
        ViewGroup headerView = (ViewGroup) getLayoutInflater().inflate(R.layout.header, listView, false);
        listView.addHeaderView(headerView);
//        String[] items = getResources().getStringArray(R.array.list_items);



//        ListAdapter adapter = new ListAdapter(this, R.layout.rowlayout, R.id.txtmodel, items);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(id!=-1){
                    startActivity(new Intent(PrimerList.this, Pop.class));
                    Toast.makeText(PrimerList.this, "List Item was clicked at "+position, Toast.LENGTH_SHORT).show();
                }

            }
        });

        listImpl = new ListImpl();
        listImpl.setCObserver(this);

        scanButton = (Button) findViewById(R.id.scan);
        bListeAnzeigen = (Button) findViewById(R.id.bListeAnzeigen);

        listGroup = (RadioGroup) findViewById(R.id.listGroup);

        radioRoboter = (RadioButton) findViewById(R.id.radioRoboter);
        radioManual = (RadioButton) findViewById(R.id.radioManuell);
        radioExtra = (RadioButton) findViewById(R.id.radioExtra);
        radioAllLists = (RadioButton) findViewById(R.id.radioAll);



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrimerList.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        bListeAnzeigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chooseList().equals("A")){
                    listImpl.requestAllLists(uobj.getUsername(), uobj.getPassword());
                }else{
                    listImpl.requestList(uobj.getUsername(), uobj.getPassword(), chooseList());

                }
            }
        });
    }

    private String chooseList() {

        int selectedID = listGroup.getCheckedRadioButtonId();

        switch (selectedID) {
            case R.id.radioRoboter:
                    return "S";
            case R.id.radioManuell:
                    return "M";
            case R.id.radioExtra:
                    return "E";
            case R.id.radioAll:
                return "A";
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
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

    @Override
    public void onResponseSuccess(Object o, ResponseCode code) {
        switch (code) {
            case LIST:
                receivePrimerList(o);
                break;
            case COMPLETELIST:
                receiveAllPrimerList(o);
                break;
            case TAKEPRIMER:
                takePrimer(o);
                break;
        }
    }

    private void takePrimer(Object o) {
    }

    private void receiveAllPrimerList(Object o) {
        System.out.println(o.toString());

        Toast.makeText(this, "SuccessALLPRIMERLOFALLLISTS", Toast.LENGTH_SHORT).show();
    }

    private void receivePrimerList(Object o) {
        System.out.println(o.toString());
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        List<PickList> pickLists = (List<PickList>) o;
        ListAdapter adapter = new ListAdapter(this, R.layout.rowlayout, R.id.txtmodel, pickLists);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResponseError() {
        Toast.makeText(this, "ResponseError", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailure() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }
}






