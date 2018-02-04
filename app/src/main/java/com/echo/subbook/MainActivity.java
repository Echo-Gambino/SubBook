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
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * MainActivity represents the main page of the SubBook app, which is responsible for displaying,
 * editing, and updating the subscription list, and also provides methods to edit, add, and
 * remove subscriptions from that list as well.
 *
 * @author hingyue
 * @version 1.0
 * @see AppCompatActivity
 * @see EditSubscriptionActivity
 * @see ViewSubscriptionActivity
 * */
public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "subfile.sav";

    public static final String ADD_TITLE = "com.echo.subbook.ADD_TITLE";
    public static final String SUB_NAME = "com.echo.subbook.SUBSCRIPTION_NAME";
    public static final String SUB_DATE = "com.echo.subbook.SUBSCRIPTION_DATE";
    public static final String SUB_CHARGE = "com.echo.subbook.SUBSCRIPTION_CHARGE";
    public static final String SUB_COMMENT = "com.echo.subbook.SUBSCRIPTION_COMMENT";
    public static final String SUB_INDEX = "com.echo.subbook.SUBSCRIPTION_INDEX";

    /* public static final int for startActivityForResult & knowledge of how to use startActivityForResult found in link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    public static final int ADD_CODE = 1;
    public static final int VIEW_CODE = 2;

    private TextView textView_sub_sum;
    private ListView listView_sub_list;

    private ArrayAdapter<Subscription> list_adapter;
    private ArrayList<Subscription> arraylist_subscription;
    private SubscriptionList obj_subscription;

    /**
     * onCreate method for MainActivity, which sets up the screen/layout in view and
     * handles all the interactable's (buttons & listitems) code when interacted upoun.
     *
     * @param savedInstanceState
     * @see EditSubscriptionActivity
     * @see ViewSubscriptionActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize the textViews and Button from activity_main.xml */
        textView_sub_sum = findViewById(R.id.textView_total_charge);
        listView_sub_list = findViewById(R.id.listView_subscription);
        Button addSubButton = findViewById(R.id.button_add_subscription);

        addSubButton.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick deals with user click/tap interaction for addSubButton; when addSubButton
             * detects a click/tap, it creates a new intent with the destination of
             * EditSubscriptionActivity.class, alongside storing the string "Add Subscription"
             * within that Intent, whilst expecting a return from that activity.
             *
             * @param view
             * @see EditSubscriptionActivity
             */
            public void onClick(View view) {
                /* If addSubButton is pressed, create a new intent for EditSubscriptionActivity
                 * and add extra information of its requested title before sending it off.
                 */
                /* Code for Intent intent = new Intent(view.getContext(), ...) was found by this link:
                * https://stackoverflow.com/questions/19464100/starting-intent-from-onclicklistener#19464142
                * This was used because I didn't know how to send an intent in View.OnClickListener()
                */
                Intent intent = new Intent(view.getContext(), EditSubscriptionActivity.class);
                intent.putExtra(ADD_TITLE ,"Add Subscription");
                /* Code for startActivityForResult(...); and other snippets of code was found in the link:
                * https://stackoverflow.com/questions/15393899/how-to-close-activity-and-go-back-to-previous-activity-in-android
                */
                startActivityForResult(intent, ADD_CODE);
            }
        });

        /*
         * Code from stack overflow, details how to generate an output from a listView, Link:
         * https://stackoverflow.com/questions/4709870/setonitemclicklistener-on-custom-listview
         */
        listView_sub_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * onItemClick deals with inputs when the user clicks/taps the individual list items
             * within te listView. onItemClick takes the object listItem and convert it into
             * a Subscription object which it (most likely) once was, then it would extract and
             * send all its information (name, date, charge, comment) alongside its position in
             * the list to viewSubscriptionActivity.class.
             *
             * @param parent
             * @param view
             * @param position
             * @param id
             * @see ViewSubscriptionActivity
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Gets the listItem as an argument of the method, and cast it
                 * as a Subscription object (because its designed to only have
                 * Subscription objects as list items).*/
                Object listItem = listView_sub_list.getItemAtPosition(position);
                Subscription sub = ((Subscription) listItem);

                /* The use of SimpleDateFormat for converting Date() to yyyy-MM-dd format
                 * is found by Nadish Krishnan's answer to the StackOverFlow post
                 * "how to convert date into yyyy-MM-dd format?", link is given below:
                 * https://stackoverflow.com/questions/14039062/how-to-convert-date-in-to-yyyy-mm-dd-format
                 */
                SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

                /* Create a new intent for ViewSubscriptionActivity, and fill it with
                 * the Subscription object's name, date, charge, and comment before
                 * sending it out to ViewSubscriptionActivity.
                 */
                Intent intent = new Intent(view.getContext(), ViewSubscriptionActivity.class);
                intent.putExtra(SUB_NAME, sub.getName());
                intent.putExtra(SUB_DATE, date_format.format(sub.getDate()));
                intent.putExtra(SUB_CHARGE, sub.getCharge());
                intent.putExtra(SUB_COMMENT, sub.getComment());
                intent.putExtra(SUB_INDEX, position);
                startActivityForResult(intent, VIEW_CODE);
            }
        });
    }


    /**
     * onActivityResult deals with the returns from the Activities started by the ClickListeners
     * from addSubButton and listView_sub_list. This takes in the requestCode and resultCode
     * to determines the type of return from an activity (e.g AddSubscriptionActivity canceled its
     * addSubscription; thus it does not do anything with SubscriptionList). If intent is null,
     * then the method assumes that it signifies no changes should be enacted, which it follows suit.
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     * @see EditSubscriptionActivity
     * @see ViewSubscriptionActivity
     */
    /* Code for the onActivityResult(...) {} to be able to respond to the intent of the
    * returned Activity that Main started was from the link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        String feedback_message = "";   // set up the feed_back message to display results
        if ((resultCode == Activity.RESULT_OK) && (intent != null)) {
            /* Occurs only if the returning result from Activity is OK and not a 'back' button
            * press from the phone itself
            */
            try {
                /* try statement is to appease sub.setName(), sub.setDate, sub.setCharge, etc.
                * since they all can throw exceptions. The try statement should work every time
                * due to the information cleaning from EditSubscriptionActivity before it returns.
                */
                /* extract name, date, charge, and comment from the given intent */
                String ret_name = intent.getStringExtra(EditSubscriptionActivity.RET_NAME);
                String ret_date = intent.getStringExtra(EditSubscriptionActivity.RET_DATE);
                double ret_charge = intent.getDoubleExtra(EditSubscriptionActivity.RET_CHARGE, 0);
                String ret_comment = intent.getStringExtra(EditSubscriptionActivity.RET_COMMENT);

                /* create a new Subscription and set the information stored in ret_name,
                 * ret_date, ret_charge, and ret_comment into the new Subscription.
                 */
                Subscription sub = new Subscription();
                sub.setName(ret_name);
                sub.setDate(ret_date);
                sub.setCharge(ret_charge);
                sub.setComment(ret_comment);


                if (requestCode == ADD_CODE) {
                    /* Occurs if Activity returned is from a request to add a Subscription */
                    obj_subscription.addSubscription(sub);
                    feedback_message = "Subscription added";
                } else if (requestCode == VIEW_CODE) {
                    /* Occurs if Activity returned is from a request to edit a Subscription */
                    int index = intent.getIntExtra(ViewSubscriptionActivity.EDIT_INDEX, 0);
                    Subscription old_sub = obj_subscription.getSubscription(index);
                    obj_subscription.setSubscription(old_sub, sub);
                    feedback_message = "Suscription edited";
                }

                /* After the code has been executed, it is now assumed that the ArrayList
                * is now different from before, so we notify the adapter that the data set
                * changed and save the ArrayList.
                */
                list_adapter.notifyDataSetChanged();
                saveInFile();

            } catch (Exception e) {
                /* Occurs in a fatal event which editing or adding the subscription onto the
                 * ArrayList is not possible without an error.
                 */
                feedback_message = "Error: Subscription operation failed";
            }
        } else if ((resultCode == Activity.RESULT_CANCELED) && (intent != null)) {
            /* Occurs when user pressed the cancel/delete button of EditSubscriptionActivity */
            if (requestCode == VIEW_CODE) {
                /* Occurs when EditSubscriptionActivity sends information to
                 * delete subscription due to the user pressing the delete button.
                 */
                if (intent.hasExtra(ViewSubscriptionActivity.EDIT_INDEX)) {
                    /* Occurs if the intent has the index number
                     * necessary to facilitate the deletion
                     */
                    int index = intent.getIntExtra(ViewSubscriptionActivity.EDIT_INDEX, 0);
                    obj_subscription.delSubscription(index);
                    list_adapter.notifyDataSetChanged();
                    saveInFile();
                    feedback_message = "Deleted subscription";
                }
            } else if (requestCode == ADD_CODE) {
                /* Occurs when EditsubscriptionActivity sends information signifying
                * that it canceled adding a Subscription to SubscriptionList.
                */
                feedback_message = "Canceled add subscription";
            }
        }
        Toast.makeText(getApplicationContext(), feedback_message,
                Toast.LENGTH_SHORT).show();
    }


    /**
     * onStart initializes this whenever the Activity is started, which loads in
     * the required data from loadFromFile(), creates the list_adapter, and
     * sets up list_adapter as te adapter for listView_sub_list. Finally, it
     * takes the sum of all subscriptions generated by the .getSum() method of
     * obj_subscription and uses the double as part of the display for
     * textView_sub_sum.
     */
    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();     /* Initializes SubscriptionList */

        /* list_adapter is initialized with ArrayAdapter using simple_list_item_1 as its list
         * item layout and obj_subscription's SubscriptionList as the list to reference,
         * which, it uses the listView_sub_list as the adapter acting as a mediator between
         * the SubscriptionList and listView.
         */
        list_adapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_1, obj_subscription.getSubscriptionList());
        listView_sub_list.setAdapter(list_adapter);

        /* Initializes the sum charge from obj_subscription.getSum(), then it formats
         * the double given into a string and puts it onto textView_sub_sum.
         */
        double sum = obj_subscription.getSum();
        /* The use of the method of rounding charge to two decimal places is
         * by Jonik's answer in a StackOverFlow post by the link shown:
         * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places#2808648
         */
        DecimalFormat df = new DecimalFormat("#0.00");
        String sum_message = "Total Charge: $" + df.format(sum);
        textView_sub_sum.setText(sum_message);
    }

    /**
     * loadFromFile loads the data onto obj_subscription that was previously stored by the
     * app itself, if loadFromFile() fails to find the app, it simply initializes
     * obj_subscription with a new SubscriptionList object.
     */
    /* loadFromFile() taken from my lonelyTwitter repo, which was forked from vingk/lonelyTwitter */
    private void loadFromFile() {
        try {
            /* Attempts to extract the data to initialize
             * obj_subscription from a predetermined file.
             */
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<SubscriptionList>(){}.getType();
            obj_subscription = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            /* Tries to initialize obj_subscription with a
             * clean clate if it cannot find the designated file.
             */
            obj_subscription = new SubscriptionList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * saveInFile stores the data from obj_subscription into a file designated by the app.
     */
    /* saveInFile() taken from my lonelyTwitter repo, which was forked from vingk/lonelyTwitter */
    private void saveInFile() {
        try {
            /* Tries to save obj_subscription's data onto the designated file */
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(obj_subscription, out);
            out.flush();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
