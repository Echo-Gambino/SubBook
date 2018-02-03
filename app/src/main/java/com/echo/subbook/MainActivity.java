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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ADD_TITLE = "com.echo.subbook.ADD_TITLE";
    public static final String FILENAME = "subfile.sav";

    public static final String SUBSCRIPTION_NAME = "com.echo.subbook.SUBSCRIPTION_NAME";
    public static final String SUBSCRIPTION_DATE = "com.echo.subbook.SUBSCRIPTION_DATE";
    public static final String SUBSCRIPTION_CHARGE = "com.echo.subbook.SUBSCRIPTION_CHARGE";
    public static final String SUBSCRIPTION_COMMENT = "com.echo.subbook.SUBSCRIPTION_COMMENT";
    public static final String SUBSCRIPTION_INDEX = "com.echo.subbook.SUBSCRIPTION_INDEX";
    public static final int VIEW_CODE = 2;

    private TextView textView_sub_sum;
    private ListView listView_sub_list;

    private ArrayAdapter<Subscription> list_adapter;
    private ArrayList<Subscription> arraylist_subscription;
    private SubscriptionList obj_subscription;

    /* public static final int for startActivityForResult & knowledge of how to use startActivityForResult found in link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    public static final int ADD_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        textView_sub_sum = findViewById(R.id.textView_total_charge);
        listView_sub_list = findViewById(R.id.listView_subscription);
        Button addSubButton = findViewById(R.id.button_add_subscription);

        // Set up button triggers
        addSubButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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

        /**
         * Code from stack overflow, details how to generate an output from a listView, Link:
         * https://stackoverflow.com/questions/4709870/setonitemclicklistener-on-custom-listview
         */
        listView_sub_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object listItem = listView_sub_list.getItemAtPosition(position);

                Subscription sub = ((Subscription) listItem);

                Intent intent = new Intent(view.getContext(), ViewSubscriptionActivity.class);

                intent.putExtra(SUBSCRIPTION_NAME, sub.getName());
                intent.putExtra(SUBSCRIPTION_DATE, sub.getDate().toString());
                intent.putExtra(SUBSCRIPTION_CHARGE, sub.getCharge());
                intent.putExtra(SUBSCRIPTION_COMMENT, sub.getComment());
                intent.putExtra(SUBSCRIPTION_INDEX, position);

                startActivityForResult(intent, VIEW_CODE);
            }
        });
    }

    /* Code for the onActivityResult(...) {} to be able to respond to the intent of the
    * returned Activity that Main started was from the link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if ((resultCode == Activity.RESULT_OK) && (intent != null)) {
            Subscription sub = new Subscription();
            try {
                String ret_name = intent.getStringExtra(EditSubscriptionActivity.RET_NAME);
                String ret_date = intent.getStringExtra(EditSubscriptionActivity.RET_DATE);
                double ret_charge = intent.getDoubleExtra(EditSubscriptionActivity.RET_CHARGE, 0);
                String comment = intent.getStringExtra(EditSubscriptionActivity.RET_COMMENT);

                sub.setName(ret_name);
                sub.setDate(ret_date);
                sub.setCharge(ret_charge);
                sub.setComment(comment);

                String feedback_message = "";
                if (requestCode == ADD_CODE) {
                    obj_subscription.addSubscription(sub);
                    feedback_message = "Subscription added";
                    Toast.makeText(getApplicationContext(), feedback_message,
                            Toast.LENGTH_SHORT).show();
                } else if (requestCode == VIEW_CODE) {
                    int index = intent.getIntExtra(ViewSubscriptionActivity.EDIT_INDEX, 0);
                    Subscription old_sub = obj_subscription.getSubscription(index);
                    obj_subscription.setSubscription(old_sub, sub);
                    feedback_message = "Suscription edited";
                    Toast.makeText(getApplicationContext(), feedback_message,
                            Toast.LENGTH_SHORT).show();
                }

                list_adapter.notifyDataSetChanged();

                saveInFile();

            } catch (Exception e) {
                String error_message = "Error: Subscription operation failed";
                Toast.makeText(getApplicationContext(), error_message,
                        Toast.LENGTH_SHORT).show();
            }

        } else if ((resultCode == Activity.RESULT_CANCELED) && (intent != null)) {
            if (requestCode == VIEW_CODE) {
                if (intent.hasExtra(ViewSubscriptionActivity.EDIT_INDEX)) {
                    int index = intent.getIntExtra(ViewSubscriptionActivity.EDIT_INDEX, 0);
                    obj_subscription.delSubscription(index);
                    list_adapter.notifyDataSetChanged();
                    saveInFile();
                }
            } else if (requestCode == ADD_CODE) {
                String feedback_message = "Canceled add subscription";
                Toast.makeText(getApplicationContext(), feedback_message,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();

        list_adapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_1, obj_subscription.getSubscriptionList());

        listView_sub_list.setAdapter(list_adapter);

        refreshTotal();
    }

    private void refreshTotal() {
        double sum = obj_subscription.getSum();
        String sum_message = "Total Charge: " + Double.toString(sum);
        textView_sub_sum.setText(sum_message);
    }

    // loadFromFile() taken from my lonelyTwitter repo, which was forked from vingk/lonelyTwitter
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
            ArrayList<Subscription> subList = gson.fromJson(in, listType);
            obj_subscription = new SubscriptionList(subList);
        } catch (FileNotFoundException e) {
            ArrayList<Subscription> subList = new ArrayList<Subscription>();
            obj_subscription = new SubscriptionList(subList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // saveInFile() taken from my lonelyTwitter repo, which was forked from vingk/lonelyTwitter
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(obj_subscription.getSubscriptionList(), out);
            out.flush();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
