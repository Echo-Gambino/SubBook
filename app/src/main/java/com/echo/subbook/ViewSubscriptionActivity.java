package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by Henry on 2018-02-01.
 */

public class ViewSubscriptionActivity extends AppCompatActivity {

    public static final String EDIT_NAME = "com.echo.subbook.EDIT_NAME";
    public static final String EDIT_DATE = "com.echo.subbook.EDIT_DATE";
    public static final String EDIT_CHARGE = "com.echo.subbook.EDIT_CHARGE";
    public static final String EDIT_COMMENT = "com.echo.subbook.EDIT_COMMENT";
    public static final String EDIT_TITLE = "com.echo.subbook.EDIT_TITLE";
    public static final int EDIT_CODE = 3;

    private String name;
    private String date;
    private int charge;
    private String comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subscription);

        TextView textView_name = findViewById(R.id.viewSub_textView_name);
        TextView textView_date = findViewById(R.id.viewSub_textView_date);
        TextView textView_charge = findViewById(R.id.viewSub_textView_charge);
        TextView textView_comment = findViewById(R.id.viewSub_textView_comment);
        Button button_edit = findViewById(R.id.viewSub_button_edit_subscription);

        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.SUBSCRIPTION_NAME);
        date = intent.getStringExtra(MainActivity.SUBSCRIPTION_DATE);
        charge = intent.getIntExtra(MainActivity.SUBSCRIPTION_CHARGE, 0);
        comment = intent.getStringExtra(MainActivity.SUBSCRIPTION_COMMENT);

        textView_name.setText(name);
        textView_date.setText(date);
        textView_charge.setText("$" + Integer.toString(charge));
        textView_comment.setText(comment);
        button_edit.setText("Edit Subscription");

        button_edit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditSubscriptionActivity.class);

                intent.putExtra(EDIT_NAME, name);
                intent.putExtra(EDIT_DATE, date);
                intent.putExtra(EDIT_CHARGE, charge);
                intent.putExtra(EDIT_COMMENT, comment);
                intent.putExtra(EDIT_TITLE, "Edit Subscription");

                startActivityForResult(intent, EDIT_CODE);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EDIT_CODE) {
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra(EDIT_NAME, name);
            intent.putExtra(EDIT_DATE, date);
            intent.putExtra(EDIT_CHARGE, charge);
            intent.putExtra(EDIT_COMMENT, comment);

            setResult(Activity.RESULT_OK, intent);

            finish();
        }

    }
}
