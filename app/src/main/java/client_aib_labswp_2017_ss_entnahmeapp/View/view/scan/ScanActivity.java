package client_aib_labswp_2017_ss_entnahmeapp.View.view.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import client.aib_labswp_2017_ss_entnahmeapp.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

/**
 * This class is responsible for the detection of barcodes using the camera of the specific device.
 * Created by Marvin on 04.05.2017.
 */
public class ScanActivity extends AppCompatActivity {
    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously
     *                           being shut down then this Bundle contains the data it most recently supplied
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        cameraView = (SurfaceView) findViewById(R.id.cameraView);
        // Control whether the surface view's surface is placed on top of another regular surface view in the window (but still behind the window itself)
        cameraView.setZOrderMediaOverlay(true);
        // Return the SurfaceHolder providing access and control over this SurfaceView's underlying surface
        holder = cameraView.getHolder();
        // Searches for barcodes in the specified format ( right now it is CODE_128)
        barcode = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.CODE_128)
                .build();

        // Indicates whether the detector has all of the required dependencies available locally in order to do detection
        if (!barcode.isOperational()) {
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        cameraSource = new CameraSource.Builder(this, barcode)
                .setFacing(CameraSource.CAMERA_FACING_BACK) // sets the camera to use either back or front camera
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1024)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {

            /**
             * Opens the camera and starts sending preview frames to the underlying detector.
             *
             * @param holder The SurfaceHolder whose surface is being created. It must not be {@code null}.
             */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    // Checks whether you have been granted a particular permission
                    if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            /**
             * Called by the detector to deliver detection results to the processor.
             *
             * @param detections the detected items that were identified. It must not be {@code null}.
             */
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // Returns a collection of the detected items that were identified in the frame
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    Intent intent = new Intent();
                    // adds extended data to the intent
                    intent.putExtra("barcode", barcodes.valueAt(0));
                    //  takes an int result value and an Intent that is passed back to {@link PrimerList#onActivityResult(int,int,Intent)}
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
