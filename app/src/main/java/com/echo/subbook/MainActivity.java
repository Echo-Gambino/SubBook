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
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ESA_TITLE = "com.echo.subbook.ESA_TITLE";
    public static final String ADD_TITLE = "com.echo.subbook.ADD_TITLE";
    public static final String FILENAME = "subfile.sav";

    public static final String SUBSCRIPTION_NAME = "com.echo.subbook.SUBSCRIPTION_NAME";
    public static final String SUBSCRIPTION_DATE = "com.echo.subbook.SUBSCRIPTION_DATE";
    public static final String SUBSCRIPTION_CHARGE = "com.echo.subbook.SUBSCRIPTION_CHARGE";
    public static final String SUBSCRIPTION_COMMENT = "com.echo.subbook.SUBSCRIPTION_COMMENT";
    public static final int VIEW_CODE = 2;

    private TextView textView_sub_sum;
    private ListView listView_sub_list;

    private ArrayAdapter<Subscription> list_adapter;
    private ArrayList<Subscription> arraylist_subscription;
//    private ArrayList<Subscription> arraylist_subscription = new ArrayList<Subscription>();
    private SubscriptionList obj_subscription;

    /* public static final int for startActivityForResult & knowledge of how to use startActivityForResult found in link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements
        textView_sub_sum = findViewById(R.id.textView_total_charge);
        listView_sub_list = findViewById(R.id.listView_subscription);
        Button addSubButton = findViewById(R.id.button_add_subscription);

//        ArrayList<Subscription> this.arraylist_subscription = new ArrayList<Subscription>();


        // Set up button triggers
        addSubButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /* Code for Intent intent = new Intent(view.getContext(), ...) was found by this link:
                * https://stackoverflow.com/questions/19464100/starting-intent-from-onclicklistener#19464142
                * This was used because I didn't know how to send an intent in View.OnClickListener()
                */
                Intent intent = new Intent(view.getContext(), EditSubscriptionActivity.class);
                // intent.putExtra(ESA_TITLE ,"Add Subscription");
                intent.putExtra(ADD_TITLE ,"Add Subscription");
                /* Code for startActivityForResult(...); and other snippets of code was found in the link:
                * https://stackoverflow.com/questions/15393899/how-to-close-activity-and-go-back-to-previous-activity-in-android
                */
                startActivityForResult(intent, REQUEST_CODE);
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
//                String message = sub.toString() + "   " + Integer.toString(position) + "\n"
//                        + obj_subscription.getSubscription(position);
//                Toast.makeText(getApplicationContext(), message,
//                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(view.getContext(), ViewSubscriptionActivity.class);

                intent.putExtra(SUBSCRIPTION_NAME, sub.getName());
                intent.putExtra(SUBSCRIPTION_DATE, sub.getDate().toString());
                intent.putExtra(SUBSCRIPTION_CHARGE, sub.getCharge());
                intent.putExtra(SUBSCRIPTION_COMMENT, sub.getComment());

                startActivityForResult(intent, VIEW_CODE);
            }
        });

    }

    /* Code for the onActivityResult(...) {} to be able to respond to the intent of the
    * returned Activity that Main started was from the link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            textView_sub_sum.setText("REQUEST_CODE");

            Subscription sub = new Subscription("Netflix");
            Subscription sub1 = new Subscription("Netflix1");
            Subscription sub2 = new Subscription("Netflix2");
            Subscription sub3 = new Subscription("Netflix3");
            Subscription sub4 = new Subscription("Netflix4");
//            arraylist_subscription.add(sub);
            obj_subscription.addSubscription(sub);
            obj_subscription.addSubscription(sub1);
            obj_subscription.addSubscription(sub2);
            obj_subscription.addSubscription(sub3);
            obj_subscription.addSubscription(sub4);

//            String message = arraylist_subscription.toString();
//            Toast.makeText(getApplicationContext(), message,
//                    Toast.LENGTH_LONG).show();

            list_adapter.notifyDataSetChanged();
            saveInFile();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadFromFile();
//        list_adapter = new ArrayAdapter<Subscription>(this,
//                R.layout.sub_item, arraylist_subscription);
//        list_adapter = new ArrayAdapter<Subscription>(this,
//                android.R.layout.simple_list_item_1, arraylist_subscription);

//        list_adapter = new ArrayAdapter<Subscription>(this,
//                android.R.layout.simple_list_item_1, arraylist_subscription);

        list_adapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_1, obj_subscription.getSubscriptionList());

        listView_sub_list.setAdapter(list_adapter);

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>(){}.getType();
//            arraylist_subscription = gson.fromJson(in, listType);
            ArrayList<Subscription> subList = gson.fromJson(in, listType);
//            obj_subscription = new SubscriptionList(arraylist_subscription);
            obj_subscription = new SubscriptionList(subList);
        } catch (FileNotFoundException e) {
//            arraylist_subscription = new ArrayList<Subscription>();
            ArrayList<Subscription> subList = new ArrayList<Subscription>();
//            obj_subscription = new SubscriptionList(arraylist_subscription);
            obj_subscription = new SubscriptionList(subList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
//            gson.toJson(arraylist_subscription, out);
            gson.toJson(obj_subscription.getSubscriptionList(), out);
            out.flush();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
