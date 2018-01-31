package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Henry on 2018-01-30.
 */

public class EditSubscriptionActivity extends AppCompatActivity {
    public static final String TEST_STRING = "com.echo.subbook.TEST_STRING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.ESA_TITLE);

        TextView ESA_title = findViewById(R.id.textView_esa_title);
        ESA_title.setText(title);

    }

    public void AbortSubscription(View view) {
        Intent MyIntentToReturn = new Intent(this, MainActivity.class);

        MyIntentToReturn.putExtra(TEST_STRING, "edit subscription returns a value!");

        /* Code for setResult(...); and other snippets of code was found in the link:
        https://stackoverflow.com/questions/15393899/how-to-close-activity-and-go-back-to-previous-activity-in-android
        */
        setResult(Activity.RESULT_OK, MyIntentToReturn);
        /* finish(); the function that retracts the activity stack by one was acquired by the link:
        https://stackoverflow.com/questions/4038479/android-go-back-to-previous-activity
        */
        finish();
    }

}
