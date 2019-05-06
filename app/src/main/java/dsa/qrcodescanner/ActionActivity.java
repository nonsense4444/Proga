package dsa.qrcodescanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;

public class ActionActivity extends AppCompatActivity {
    private TextView qrText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        final String qrtext = getIntent().getStringExtra("qrtext");

        qrText = (TextView)findViewById(R.id.qrText);
        qrText.setText(qrtext);

        Button copyButton = (Button)findViewById(R.id.copyButton);
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", qrtext);
                clipboard.setPrimaryClip(clip);
            }
        });

        Button shareButton = (Button)findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, qrtext);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        Button visitButton = (Button)findViewById(R.id.visitButton);
        if(URLUtil.isValidUrl(qrtext)) {
            visitButton.setEnabled(true);
        } else {
            visitButton.setEnabled(false);
        }
        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(qrtext));
                startActivity(browserIntent);
            }
        });

        Button closeButton = (Button)findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
