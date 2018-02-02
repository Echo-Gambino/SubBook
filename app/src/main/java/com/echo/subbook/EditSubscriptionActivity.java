package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Henry on 2018-01-30.
 */

public class EditSubscriptionActivity extends AppCompatActivity {
    public static final String TEST_STRING = "com.echo.subbook.TEST_STRING";

    private TextView textView_title;
    private EditText editText_name;
    private EditText editText_date;
    private EditText editText_charge;
    private EditText editText_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        textView_title = findViewById(R.id.editSub_textView_title);
        editText_name = findViewById(R.id.editSub_editText_name);
        editText_date = findViewById(R.id.editSub_editText_date);
        editText_charge = findViewById(R.id.editSub_editText_charge);
        editText_comment = findViewById(R.id.editSub_editText_comment);

        Intent intent = getIntent();

        String message = Boolean.toString(intent.hasExtra(MainActivity.ESA_TITLE));
        if (intent.hasExtra(ViewSubscriptionActivity.EDIT_TITLE)) {
            HandleEditing(intent);
        } else if (intent.hasExtra(MainActivity.ADD_TITLE)) {
            HandleAdding(intent);
        }


//        String message = editText_name.toString();
        //Editable test = editText_name.getText();
        editText_name.setText("Test");
//        String message = test.toString();
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();

//        String title = intent.getStringExtra(MainActivity.ESA_TITLE);
//        TextView ESA_title = findViewById(R.id.editSub_textView_title);
//        ESA_title.setText(title);

    }

    public void HandleEditing(Intent intent) {
        String title = intent.getStringExtra(ViewSubscriptionActivity.EDIT_TITLE);
        String name = intent.getStringExtra(ViewSubscriptionActivity.EDIT_NAME);
        String date = intent.getStringExtra(ViewSubscriptionActivity.EDIT_DATE);
        int charge = intent.getIntExtra(ViewSubscriptionActivity.EDIT_CHARGE, 0);
        String comment = intent.getStringExtra(ViewSubscriptionActivity.EDIT_COMMENT);
        setAllText(title, name, date, charge, comment);
    }

    public void HandleAdding(Intent intent) {
        String title = intent.getStringExtra(MainActivity.ADD_TITLE);
    }

    public void setAllText(String title, String name, String date, int charge, String comment) {
        textView_title.setText(title);
        editText_name.setText(name);
        editText_date.setText(date);
        editText_charge.setText(Integer.toString(charge));
        editText_comment.setText(comment);
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
