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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Henry on 2018-01-30.
 */

/**
 * Represents the edit/add subscription page of the app. The activitie's man purpose is to
 * take user input and ultimately send it down to the main activity to have the information
 * to be used to create or change a subscription in SubscriptionList.
 *
 * @author hingyue
 * @version 1.0
 * @see AppCompatActivity
 * @see ViewSubscriptionActivity
 * @see MainActivity
 * */
public class EditSubscriptionActivity extends AppCompatActivity {

    public static final String RET_NAME = "com.echo.subbook.RET_NAME";
    public static final String RET_DATE = "com.echo.subbook.RET_DATE";
    public static final String RET_CHARGE = "com.echo.subbook.RET_CHARGE";
    public static final String RET_COMMENT = "com.echo.subbook.RET_COMMENT";

    private TextView textView_title;
    private EditText editText_name;
    private EditText editText_date;
    private EditText editText_charge;
    private EditText editText_comment;

    private String title;
    private String name;
    private String date;
    private double charge;
    private String comment;

    /**
     * EditSubscriptionActivity sets up its layout, sets the texts of
     * textView, editTexts, and Buttons. Then it waits and takes user input.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);

        /* Initialize textView, editText, and Buttons from the activity_edit_subscription */
        textView_title = findViewById(R.id.editSub_textView_title);
        editText_name = findViewById(R.id.editSub_editText_name);
        editText_date = findViewById(R.id.editSub_editText_date);
        editText_charge = findViewById(R.id.editSub_editText_charge);
        editText_comment = findViewById(R.id.editSub_editText_comment);
        Button button_confirm = findViewById(R.id.editSub_button_confirm);
        Button button_cancel = findViewById(R.id.editSub_button_cancel);

        /* Initialize the variables needed to set the texts of objects initialized above */
        this.title = "Title";
        this.name = "Name";
        this.date = (new Date()).toString();
        this.charge = 0.00;
        this.comment = "Comment";
        String button_confirm_text = "Confirm";
        String button_cancel_text = "Back";

        Intent intent = getIntent();    // Extract intent that was called by an Activity

        if (intent.hasExtra(ViewSubscriptionActivity.EDIT_TITLE)) {
            /* If the sent intent has an Extra by ViewSubscriptionActivity.EDIT_TITLE,
             * assume that the other extras are also provided and the Activity and layout
             * to accommodate for editing the Subscription.
             */
            HandleEditing(intent);
            button_confirm_text = "Confirm Subscription";
            button_cancel_text = "Delete Subscription";
        } else if (intent.hasExtra(MainActivity.ADD_TITLE)) {
            /* If the sent intent has an Extra by MainActivity.ADD_TITLE
             * assume that the other extras are also provided and the Activity are layout
             * to accomadate for adding the Subscription.
             */
            HandleAdding(intent);
            button_confirm_text = "Add Subscription";
            button_cancel_text = "Cancel Subscription";
        }

        /* Sets the text of the buttons */
        button_confirm.setText(button_confirm_text);
        button_cancel.setText(button_cancel_text);

        button_confirm.setOnClickListener(new View.OnClickListener() {
            /**
             * button_confirm's onClick method tries to check for all its inputs to match
             * its constraints (e.g name's character length must not exceed 20), if all of
             * it passes, the method will send an intent with all of the extracted information
             * like name, date, charge, and comment back towards the caller Activity.
             * If any of the inputs fail to satisfy te constraints, a Toast message will appear
             * and send a message giving feedback on what happened whilst encouraging the user to
             * try again.
             *
             * @param view
             * */
            public void onClick(View view) {
                /* Extract information from the editTexts in the UI */
                String name_temp = editText_name.getText().toString();
                String date_temp = editText_date.getText().toString();
                double charge_temp = Double.valueOf(editText_charge.getText().toString());
                String comment_temp = editText_comment.getText().toString();
                String message = null;  // Sets up the error message in case user gives bad input

                if (name_temp.length() > 20) {
                    /* Occurs if name_temp exceeds 20 characters */
                    message = "The subscription name exceeded 20 characters, " +
                            "please try again.";
                    editText_name.setText(name);
                }  else if (isDateFormatCorrect(date_temp) == false) {
                    /* Occurs if date_temp cannot be formatted into a Date datatype */
                    message = "Date not formatted correctly, \n please try again";
                    editText_date.setText(date);
                } else if (charge_temp < 0) {
                    /* Occurs if charge_temp is negatives */
                    message = "The amount of money charged from a subscription should be positive, " +
                            "please try again.";
                    editText_charge.setText(Double.toString(charge));
                } else if (comment_temp.length() > 30) {
                    /* Occurs if comment_temp exceeds 30 characters */
                    message = "The subscription comment exceeded 30 characters, " +
                            "please try again.";
                    editText_comment.setText(comment);
                } else {
                    /* Occurs if all the data that has been extracted have satisfied
                     * the program's constraints t operate without errors
                     */
                    /* sets the Activity's date to be the data pulled from UI */
                    name = name_temp;
                    date = date_temp;
                    charge = charge_temp;
                    comment = comment_temp;

                    /* creates a new intent and ultimately puts the data from the UI into it */
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra(RET_NAME, name);
                    intent.putExtra(RET_DATE, date);
                    intent.putExtra(RET_CHARGE, charge);
                    intent.putExtra(RET_COMMENT, comment);

                    /* sets the result of the Activity as a success and sends
                     * the intent back to the caller.
                     */
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                if (message != null) {
                    /* This is the segment where the Activity sends feedback to the user
                     * on why the app cannot process the task fully. This is ignored if
                     * the message is null (app completed the process without problems)
                     */
                    Toast.makeText(getApplicationContext(), message,
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            /**
             * button cancel's onClick method handles inputs when the user clicks/taps
             * the button corresponding the button_cancel, which it responds by returning
             * an intent back to its caller Activity with the RESULT_CANCELED flag on.
             *
             * @param view
             */
            public void onClick(View view) {
                /* Create a new intent for MainActivity.class, mark the Activity's
                 * result as a failure (or cancelled) and send it back to the caller
                 */
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    /**
     * isDateFormatCorrect returns true if given String form of date is coherent with
     * the format that was set by date.toString(), and false if not.
     *
     * @param date
     */
    public boolean isDateFormatCorrect(String date) {
        Date date_dummy = null;
        try {
            /* The use of SimpleDateFormat for converting Date() to yyyy-MM-dd format
             * is found by Nadish Krishnan's answer to the StackOverFlow post
             * "how to convert date into yyyy-MM-dd format?", link is given below:
             * https://stackoverflow.com/questions/14039062/how-to-convert-date-in-to-yyyy-mm-dd-format
             */
            date_dummy = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {}
        /* returns true if try succeeded (String able to be formatted to date) and false
         * if it goes returns with an exceptions (String unable to be formatted to date) */
        return (date_dummy != null);
    }

    /**
     * Sets the class's private vars' buffer to have preset for Editing Subscription,
     * which takes and stores all of its information from the intent's extra's.
     *
     * @param intent
     */
    public void HandleEditing(Intent intent) {
        /* set the variables of EditSubscriptionActivity from the given
         * intent's data and enact the changes with setAllText
         */
        this.title = intent.getStringExtra(ViewSubscriptionActivity.EDIT_TITLE);
        this.name = intent.getStringExtra(ViewSubscriptionActivity.EDIT_NAME);
        this.date = intent.getStringExtra(ViewSubscriptionActivity.EDIT_DATE);
        this.charge = intent.getDoubleExtra(ViewSubscriptionActivity.EDIT_CHARGE, 0);
        this.comment = intent.getStringExtra(ViewSubscriptionActivity.EDIT_COMMENT);
        setAllText(title, name, date, charge, comment);
    }

    /**
     * Sets the class's private vars' buffer to have preset for Adding Subscription,
     * which mainly comprises of hardcoded defaults except for the title.
     *
     * @param intent
     */
    public void HandleAdding(Intent intent) {
        /* The use of SimpleDateFormat for converting Date() to yyyy-MM-dd format
         * is found by Nadish Krishnan's answer to the StackOverFlow post
         * "how to convert date into yyyy-MM-dd format?", link is given below:
         * https://stackoverflow.com/questions/14039062/how-to-convert-date-in-to-yyyy-mm-dd-format
         */
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        /* set the title variable of EditSubscriptionActivity with the given
         * intent and set the rest with the presets below, then enact
         * the changes with setAllText
         */
        this.title = intent.getStringExtra(MainActivity.ADD_TITLE);
        this.name = "Subscription";
        this.date = date_format.format(new Date());
        this.charge = 10.00;
        this.comment = "";
        setAllText(title, name, date, charge, comment);
    }

    /**
     * setAllText updates textViews and edtTexts' text with their respective data
     * (e.g textView_title set its text to be "Add Subscriptions" & editText_charge having "9")
     *
     * @param title
     * @param name
     * @param date
     * @param charge
     * @param comment
     */
    public void setAllText(String title, String name, String date, double charge, String comment) {
        /* The use of the method of rounding charge to two decimal places is
         * by Jonik's answer in a StackOverFlow post by the link shown:
         * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places#2808648
         */
        DecimalFormat df = new DecimalFormat("#0.00");
        textView_title.setText(title);
        editText_name.setText(name);
        editText_date.setText(date);
        editText_charge.setText(df.format(charge));
        editText_comment.setText(comment);
    }

}
