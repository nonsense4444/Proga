package dsa.qrcodescanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class MainActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    ViewStub stub;
    QRCodeReaderView qrCodeReaderView;
    private static final int MY_CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    void openCamera() {
        stub = (ViewStub) findViewById(R.id.qr_stub);
        if(stub != null) {
            qrCodeReaderView = (QRCodeReaderView) stub.inflate();
        }
        if (qrCodeReaderView != null) {
            qrCodeReaderView.setOnQRCodeReadListener(this);
            qrCodeReaderView.setQRDecodingEnabled(true);
            qrCodeReaderView.setAutofocusInterval(2000L);
            qrCodeReaderView.startCamera();
        }
    }

    void closeCamera() {
        QRCodeReaderView qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qr_reader);
        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        Intent intent = new Intent(getBaseContext(), ActionActivity.class);
        intent.putExtra("qrtext", text);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeCamera();
    }
}
