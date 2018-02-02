package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Henry on 2018-01-30.
 */

public class EditSubscriptionActivity extends AppCompatActivity {
    public static final String TEST_STRING = "com.echo.subbook.TEST_STRING";

    public static final String RET_NAME = "com.echo.subbook.RET_NAME";
    public static final String RET_DATE = "com.echo.subbook.RET_DATE";
    public static final String RET_CHARGE = "com.echo.subbook.RET_CHARGE";
    public static final String RET_COMMENT = "com.echo.subbook.RET_COMMENT";
    public static final String RET_IS_DELETE = "com.echo.subbook.RET_IS_DELETE";


    private TextView textView_title;
    private EditText editText_name;
    private EditText editText_date;
    private EditText editText_charge;
    private EditText editText_comment;
//    private Button button_confirm;

    private String title;
    private String name;
    private String date;
    private int charge;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        textView_title = findViewById(R.id.editSub_textView_title);
        editText_name = findViewById(R.id.editSub_editText_name);
        editText_date = findViewById(R.id.editSub_editText_date);
        editText_charge = findViewById(R.id.editSub_editText_charge);
        editText_comment = findViewById(R.id.editSub_editText_comment);
        Button button_confirm = findViewById(R.id.editSub_button_confirm);
        Button button_cancel = findViewById(R.id.editSub_button_cancel);

        Intent intent = getIntent();

        this.title = "Title";
        this.name = "Name";
        this.date = (new Date()).toString();
        this.charge = 0;
        this.comment = "Comment";
        String button_confirm_text = "Confirm";
        String button_cancel_text = "Back";


        if (intent.hasExtra(ViewSubscriptionActivity.EDIT_TITLE)) {
            HandleEditing(intent);
            button_confirm_text = "Confirm Subscription";
            button_cancel_text = "Delete Subscription";
        } else if (intent.hasExtra(MainActivity.ADD_TITLE)) {
            HandleAdding(intent);
            button_confirm_text = "Add Subscription";
            button_cancel_text = "Cancel Subscription";
        }

        button_confirm.setText(button_confirm_text);
        button_cancel.setText(button_cancel_text);


        button_confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                String name_temp = editText_name.getText().toString();
                String date_temp = editText_date.getText().toString();
                int charge_temp = Integer.valueOf(editText_charge.getText().toString());
                String comment_temp = editText_comment.getText().toString();
                String message = "";

                if (name_temp.length() > 20) {
                    message = "The subscription name exceeded 20 characters, " +
                            "please try again.";
                } else if (charge_temp < 0) {
                    message = "The amount of money charged from a subscription should be positive, " +
                            "please try again.";
                } else if (comment_temp.length() > 30) {
                    message = "The subscription comment exceeded 30 characters, " +
                            "please try again.";
                } else {
                    message = "Success!";
                    try {
                        Date d = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(date_temp);
                        name = name_temp;
                        date = date_temp;
                        charge = charge_temp;
                        comment = comment_temp;

                        intent.putExtra(RET_NAME, name);
                        intent.putExtra(RET_DATE, date);
                        intent.putExtra(RET_CHARGE, charge);
                        intent.putExtra(RET_COMMENT, comment);

                        setResult(Activity.RESULT_OK, intent);
                        finish();

                    } catch (Exception e) {
                        message = "Date not formatted correctly, \n please try again";
                        // editText_date.setText(date);
                    }
                }
//                Toast.makeText(getApplicationContext(), intent.toString(),
//                        Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), message,
//                       Toast.LENGTH_LONG).show();

            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });


//        Toast.makeText(getApplicationContext(), message,
//                Toast.LENGTH_LONG).show();

    }

    /*
    public void fillIntent(Intent intent) {
        intent.putExtra(RET_NAME, this.name);
        intent.putExtra(RET_DATE, this.date);
        intent.putExtra(RET_CHARGE, this.charge);
        intent.putExtra(RET_COMMENT, this.comment);
    }
    */

    public void HandleEditing(Intent intent) {
        this.title = intent.getStringExtra(ViewSubscriptionActivity.EDIT_TITLE);
        this.name = intent.getStringExtra(ViewSubscriptionActivity.EDIT_NAME);
        this.date = intent.getStringExtra(ViewSubscriptionActivity.EDIT_DATE);
        this.charge = intent.getIntExtra(ViewSubscriptionActivity.EDIT_CHARGE, 0);
        this.comment = intent.getStringExtra(ViewSubscriptionActivity.EDIT_COMMENT);
        setAllText(title, name, date, charge, comment);
    }

    public void HandleAdding(Intent intent) {
        this.title = intent.getStringExtra(MainActivity.ADD_TITLE);
        this.name = "Subscription";
        this.date = (new Date()).toString();
        this.charge = 10;
        this.comment = "";
        setAllText(title, this.name, this.date, this.charge, this.comment);
    }

    public void setAllText(String title, String name, String date, int charge, String comment) {
        textView_title.setText(title);
        editText_name.setText(name);
        editText_date.setText(date);
        editText_charge.setText(Integer.toString(charge));
        editText_comment.setText(comment);
    }


//    public void AbortSubscription(View view) {
//        Intent MyIntentToReturn = new Intent(this, MainActivity.class);
//
//        MyIntentToReturn.putExtra(TEST_STRING, "edit subscription returns a value!");
//
//        /* Code for setResult(...); and other snippets of code was found in the link:
//        https://stackoverflow.com/questions/15393899/how-to-close-activity-and-go-back-to-previous-activity-in-android
//        */
//        setResult(Activity.RESULT_OK, MyIntentToReturn);
//        /* finish(); the function that retracts the activity stack by one was acquired by the link:
//        https://stackoverflow.com/questions/4038479/android-go-back-to-previous-activity
//        */
//        finish();
//    }


}
