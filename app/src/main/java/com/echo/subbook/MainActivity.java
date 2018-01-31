package com.echo.subbook;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String ESA_TITLE = "com.echo.subbook.ESA_TITLE";

    private ListView subscriptionList;


    /* public static final int for startActivityForResult & knowledge of how to use startActivityForResult found in link:
    * https://stackoverflow.com/questions/37768604/how-to-use-startactivityforresult
    */
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
