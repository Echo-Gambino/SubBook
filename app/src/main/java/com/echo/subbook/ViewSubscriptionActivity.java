/*
 * CMPUT 301
 *
 * v0.9 - Workable prototype that doesn't crash under normal use.
 *
 * 2018-02-05
 *
 * Copyright 2018 Hing Yue (Henry) Lam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * includedin allcopies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Henry on 2018-02-01.
 */

public class ViewSubscriptionActivity extends AppCompatActivity {

    public static final String EDIT_NAME = "com.echo.subbook.EDIT_NAME";
    public static final String EDIT_DATE = "com.echo.subbook.EDIT_DATE";
    public static final String EDIT_CHARGE = "com.echo.subbook.EDIT_CHARGE";
    public static final String EDIT_COMMENT = "com.echo.subbook.EDIT_COMMENT";
    public static final String EDIT_TITLE = "com.echo.subbook.EDIT_TITLE";
    public static final String EDIT_INDEX = "com.echo.subbook.EDIT_INDEX";
    public static final int EDIT_CODE = 3;

    private String name;
    private String date;
    private double charge;
    private String comment;
    private int index;

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
        charge = intent.getDoubleExtra(MainActivity.SUBSCRIPTION_CHARGE, 0);
        comment = intent.getStringExtra(MainActivity.SUBSCRIPTION_COMMENT);
        index = intent.getIntExtra(MainActivity.SUBSCRIPTION_INDEX, 0);

        textView_name.setText(name);
        textView_date.setText(date);
        textView_charge.setText("$" + Double.toString(charge));
        String comment_text = "Comment: \n" + comment;
        textView_comment.setText(comment_text);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if ((requestCode == EDIT_CODE) && (intent != null)) {
            intent.putExtra(EDIT_INDEX, index);
            setResult(resultCode, intent);
            finish();
        }
    }
}
