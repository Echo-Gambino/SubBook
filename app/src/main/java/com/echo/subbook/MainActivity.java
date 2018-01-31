package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String ESA_TITLE = "com.echo.subbook.ESA_TITLE";

    private ListView subscription_list_listView;
    private SubscriptionList subscription_list_obj;
    private ArrayList<Subscription> subscription_array;
    private ArrayAdapter<Subscription> adapter;

    /* public static final int for startActivityForResult & knowledge of how to use startActivityForResult found in link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.subscription_array = subscription_list_obj.getSubscriptionList();

        adapter = new ArrayAdapter<Subscription>(this,
                R.layout.sub_item, subscription_list_obj.getSubscriptionList());
        // this.subscription_list_obj.getSubscriptionList().setAdapter(adapter);
        this.subscription_array = this.subscription_list_obj.getSubscriptionList();
        //this.subscription_array.setAdapter(adapter);


        Date date1 = new Date();
        Subscription thing1 = new Subscription("Thing1", date1, 1);
        Subscription thing2 = new Subscription("Thing2", date1, 2);
        Subscription thing3 = new Subscription("Thing3", date1, 3);
        Subscription thing4 = new Subscription("Thing4", date1, 4);

        this.subscription_list_obj.addSubscription(thing1);
        this.subscription_list_obj.addSubscription(thing2);
        this.subscription_list_obj.addSubscription(thing3);
        this.subscription_list_obj.addSubscription(thing4);

        subscription_list_listView = (ListView) findViewById(R.id.listView_subscription);

        adapter.notifyDataSetChanged();

    }

    /* Code for the onActivityResult(...) {} to be able to respond to the intent of the
    * returned Activity that Main started was from the link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            TextView total_charge_text = findViewById(R.id.textView_total_charge);
            total_charge_text.setText("REQUEST_CODE");
//            if (requestCode == EditSubscriptionActivity.RESULT_OK ) {
//                total_charge_text.setText("RESULT_OK");
//            } else if (requestCode == EditSubscriptionActivity.RESULT_CANCELED) {
//                total_charge_text.setText("RESULT_CANCELED");
//            }
        }
    }

    public void AddSubscription(View view) {
        Intent intent = new Intent(this, EditSubscriptionActivity.class);

        intent.putExtra(ESA_TITLE ,"Add Subscription");
        //startActivity(intent);
        /* Code for startActivityForResult(...); and other snippets of code was found in the link:
        * https://stackoverflow.com/questions/15393899/how-to-close-activity-and-go-back-to-previous-activity-in-android
        */
        startActivityForResult(intent, REQUEST_CODE);

    }
}
