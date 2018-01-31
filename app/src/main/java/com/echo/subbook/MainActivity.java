package com.echo.subbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String ESA_TITLE = "com.echo.subbook.ESA_TITLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AddSubscription(View view) {
        Intent intent = new Intent(this, EditSubscriptionActivity.class);

        intent.putExtra(ESA_TITLE ,"Add Subscription");
        startActivity(intent);
    }
}
