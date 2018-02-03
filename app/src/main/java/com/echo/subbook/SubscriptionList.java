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

/**
 * Created by Henry on 2018-01-29.
 */

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubscriptionList {
    private ArrayList<Subscription> subList;
    private double totalCharge;

    public SubscriptionList() {
        this.subList = new ArrayList<Subscription>();
        this.totalCharge = 0.00;
    }

    public SubscriptionList(ArrayList<Subscription> subList) {
        this();
        this.subList = subList;
        this.refreshSum();
    }

    public void addSubscription(Subscription subscription) {
        this.totalCharge = this.totalCharge + subscription.getCharge();
        this.subList.add(subscription);
    }

    public void delSubscription(int index) {
        this.totalCharge = this.totalCharge - this.subList.get(index).getCharge();
        this.subList.remove(index);
    }

    public Subscription getSubscription(int index) {
        return this.subList.get(index);
    }

    public void setSubscription(Subscription old_subscription, Subscription new_subscription) {
        String new_name = new_subscription.getName();
        try {
            old_subscription.setName(new_name);
        } catch(Exception e) {}     // Do nothing if new_name exceeds char count.

        Date new_date = new_subscription.getDate();
        old_subscription.setDate(new_date);

        double new_charge = new_subscription.getCharge();
        try {
            old_subscription.setCharge(new_charge);
        } catch(Exception e) {}

        String new_comment = new_subscription.getComment();
        try {
            new_subscription.setComment(new_comment);
        } catch(Exception e) {}     // Do nothing if new_comment exceeds char count.
    }

    public ArrayList<Subscription> getSubscriptionList() {
        return this.subList;
    }

    public void setSubscriptionList(ArrayList<Subscription> subList) {
        this.subList = subList;
        this.refreshSum();
    }

    public double getSum() {
        return this.totalCharge;
    }

    public void refreshSum() {
        double sum = 0.0;
        for(int i = 0; i < this.subList.size(); i++) {
            sum = sum + this.subList.get(i).getCharge();
        }
        this.totalCharge = sum;
    }

}
