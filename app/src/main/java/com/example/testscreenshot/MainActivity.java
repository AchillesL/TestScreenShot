package com.example.testscreenshot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static int REQUEST_MEDIA_PROJECTION = 0;

    private Button mBtn;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = findViewById(R.id.btn);
        mImageView = findViewById(R.id.iv);

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try2StartScreenShot();
            }
        });
    }

    private void try2StartScreenShot() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEDIA_PROJECTION) {
            if (resultCode == RESULT_OK && data != null) {
                ScreenShotHelper screenShotHelper = new ScreenShotHelper(MainActivity.this, resultCode, data, new ScreenShotHelper.OnScreenShotListener() {
                    @Override
                    public void onFinish(Bitmap bitmap) {
                        mImageView.setImageBitmap(bitmap);
                    }
                });
                screenShotHelper.startScreenShot();
            }
        }
    }
}
