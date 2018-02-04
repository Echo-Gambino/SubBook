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

package com.echo.hingyue_subbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Henry on 2018-02-01.
 */

/**
 * Represents the page where you view the information of a subscription that
 * was passed from MainActivity, this gives the user an opportunity to pass
 * that information a second time towards EditSubscriptionActivity to pass
 * back towards MainActivity to modify the contents of subscription.
 *
 * @author hingyue
 * @version 1.0
 * @see AppCompatActivity
 * @see ViewSubscriptionActivity
 * @see EditSubscriptionActivity
 */
public class ViewSubscriptionActivity extends AppCompatActivity {

    public static final String EDIT_TITLE = "com.echo.subbook.EDIT_TITLE";
    public static final String EDIT_NAME = "com.echo.subbook.EDIT_NAME";
    public static final String EDIT_DATE = "com.echo.subbook.EDIT_DATE";
    public static final String EDIT_CHARGE = "com.echo.subbook.EDIT_CHARGE";
    public static final String EDIT_COMMENT = "com.echo.subbook.EDIT_COMMENT";
    public static final String EDIT_INDEX = "com.echo.subbook.EDIT_INDEX";

    public static final int EDIT_CODE = 3;

    private String name;    // Name of subscription
    private String date;    // Date of Subscription
    private double charge;  // Charge of Subscription
    private String comment; // Comment of Subscription
    private int index;      // Where the subscription is within the ArrayList of SubscriptionList

    /**
     * ViewSubscriptionActivity sets up the layout, extracts the information from the intent
     * of MainActivity and puts up the information onto textView. Not only that, the
     * ViewSubscriptionActivity handles user interaction for button_edit, which is associated
     * with viewSub_edit_subscription; that sends information to EditSubscriptionActivity.class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subscription);

        /* Initialize the textViews and button from activity_view_subscription.xml */
        TextView textView_name = findViewById(R.id.viewSub_textView_name);
        TextView textView_date = findViewById(R.id.viewSub_textView_date);
        TextView textView_charge = findViewById(R.id.viewSub_textView_charge);
        TextView textView_comment = findViewById(R.id.viewSub_textView_comment);
        Button button_edit = findViewById(R.id.viewSub_button_edit_subscription);

        /* Extract the intent, then the information from that intent onto
         * ViewSubscriptionActivity's variables
         */
        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.SUB_NAME);
        date = intent.getStringExtra(MainActivity.SUB_DATE);
        charge = intent.getDoubleExtra(MainActivity.SUB_CHARGE, 0);
        comment = intent.getStringExtra(MainActivity.SUB_COMMENT);
        index = intent.getIntExtra(MainActivity.SUB_INDEX, 0);

        /* Sets & formats the text of the textView and button to the stored variables of
         * ViewSubscriptionActivity; which was set with info of the given intent above.
         */
        textView_name.setText(name);
        textView_date.setText(date);
                /* The use of the method of rounding charge to two decimal places is
         * by Jonik's answer in a StackOverFlow post by the link shown:
         * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places#2808648
         */
        DecimalFormat df = new DecimalFormat("#0.00");
        textView_charge.setText("$" + df.format(charge));
        String comment_text = "Comment: \n" + comment;
        textView_comment.setText(comment_text);
        button_edit.setText("Edit Subscription");

        button_edit.setOnClickListener(new View.OnClickListener() {
            /**
             * button_edit's onClick method and sends an intent with information from its
             * recieved intent from MainActivity.class towards EditSubscriptionActivity.class
             *
             * @param view
             */
            public void onClick(View view) {
                /* Create new intent for EditSubscriptionActivity.class */
                Intent intent = new Intent(view.getContext(), EditSubscriptionActivity.class);
                /* Fill intent with the name, date, charge, and comment of the intent received
                 * when ViewActivity was created and sends it out to EditSubscriptionActivity.
                 */
                intent.putExtra(EDIT_NAME, name);
                intent.putExtra(EDIT_DATE, date);
                intent.putExtra(EDIT_CHARGE, charge);
                intent.putExtra(EDIT_COMMENT, comment);
                intent.putExtra(EDIT_TITLE, "Edit Subscription");
                startActivityForResult(intent, EDIT_CODE);
            }
        });
    }

    /**
     * onActivityResultMethod takes in the result and intent from EditSubscriptionActivity,
     * and acts as a mediator to add the index number into the recieved intent so that
     * MainActivity.class can know which item of the subscription to delete or edit.
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if ((requestCode == EDIT_CODE) && (intent != null)) {
            /* If the requestCode is EditSubscriptionActivity and the intent is not
             * null this signifies that it returned by either pressing confirm edit
             * or delete subscription, which it relays all the information to MainActivity
             * and adds the index of the specified subscription in the ArrayList*/
            intent.putExtra(EDIT_INDEX, index);
            setResult(resultCode, intent);
            finish();
        }
        /* If the intent is null (Signify that the 'back' button was pressed), then
         * the ViewSubscriptionActivity does nothing, as it assumes that the user
         * canceled the change for editing the Subscription
         */
    }
}
