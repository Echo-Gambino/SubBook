package com.echo.subbook;

/**
 * Created by Henry on 2018-01-29.
 */

import java.util.ArrayList;
import java.util.List;

public class SubscriptionList {
    // private ArrayList<Subscription> subList;
    private List<Subscription> subList;
    private int totalCharge;

    public SubscriptionList() {
        this.subList = new ArrayList<Subscription>();
        this.totalCharge = 0;
    }

    public List<Subscription> getSubscriptionList() {
        return this.subList;
    }

    public void setSubscription() {
        // nothing
    }


}
