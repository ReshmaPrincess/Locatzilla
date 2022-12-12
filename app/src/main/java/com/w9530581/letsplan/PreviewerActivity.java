package com.w9530581.letsplan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PreviewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previewer);

        Intent intent = getIntent();
        String str = intent.getStringExtra("ImageUrl");

        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView3.setImageBitmap(getBitmapFromEncodedString(str));

    }

    private Bitmap getBitmapFromEncodedString(String encodedString) {
        byte[] arr = Base64.decode(encodedString, Base64.URL_SAFE);
        Bitmap img = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        return img;
    }
}