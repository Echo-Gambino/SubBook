package com.echo.subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Henry on 2018-01-30.
 */

public class EditSubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.ESA_TITLE);

        TextView ESA_title = findViewById(R.id.textView_esa_title);
        ESA_title.setText(title);

    }

}
