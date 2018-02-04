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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents SubscriptionList
 *
 * @author hingyue
 * @version 1.0
 * @see Subscription
 */
public class SubscriptionList {

    private ArrayList<Subscription> sub_list;    // ArrayList to keep track of all its Subscriptions
    private double total_charge;                 // totalCharge stores the total charge of all Subscriptions within ArrayList

    /**
     * Consctructs a SubscriptionList object
     */
    public SubscriptionList() {
        this.sub_list = new ArrayList<Subscription>();   // initializes subList with an ArrayList<Subscription>
        this.total_charge = 0.00;                        // initialize totalCharge with 0.00
    }

    /**
     * Consctructs a SubscriptionList object
     *
     * @param subList
     */
    public SubscriptionList(ArrayList<Subscription> subList) {
        this();                     // Cascades constructor to SubscriptionList()
        this.sub_list = subList;     // set subList to the given ArrayList<Subscription>
        this.refreshSum();          // calculates the sum from the given arraylist stored within the object.
    }

    /**
     * Adds a subscription onto its subscription list and updates its total monetary charge.
     *
     * @param subscription
     */
    public void addSubscription(Subscription subscription) {
        total_charge = total_charge + subscription.getCharge();   // updates the totalCharge with the new subscription
        sub_list.add(subscription);                             // adds subscription onto object's ArrayList
    }

    /**
     * Deletes (or removes) a subscription from the subscription list by the given index
     * and updates its total monetary charge.
     *
     * @param index
     */
    public void delSubscription(int index) {
        total_charge = total_charge - sub_list.get(index).getCharge(); // updates the totalCharge with removal of subscription
        sub_list.remove(index);                                      // removes subscription from object's ArrayList
    }

    /**
     * Returns the subscription from the subscription list by the given index.
     *
     * @param index
     * @return Subscription
     */
    public Subscription getSubscription(int index) {
        return this.sub_list.get(index);
    }

    /**
     * Sets the subscription within the subscription list by updating the old_subscription
     * linked to SubscriptionList with data from an unrelated new_subscription.
     *
     * @param old_sub
     * @param new_sub
     */
    public void setSubscription(Subscription old_sub, Subscription new_sub) {
        String new_name = new_sub.getName();       // get new_sub's name
        Date new_date = new_sub.getDate();         // get new_sub's date
        double new_charge = new_sub.getCharge();   // get new_sub's charge
        String new_comment = new_sub.getComment(); // get new_sub's comment

        try {
            old_sub.setName(new_name); // Try to set new_sub's name onto old_sub
        } catch(Exception e) {}                 // Do nothing if new_sub's name exceeds char count

        old_sub.setDate(new_date);     // Sets the new_date onto old_sub.

        try {
            old_sub.setCharge(new_charge); // Try to set new_sub's charge onto old_sub
        } catch(Exception e) {}                     // Do nothing if new_sub's charge is negative

        try {
            new_sub.setComment(new_comment);   // Try to set new_sub's comment onto old_sub
        } catch(Exception e) {}                         // Do nothing if new_comment's comment exceeds char count.
    }

    /**
     * Returns the SubscriptionList's whole subscription list (ArrayList<Subscription>)
     *
     * @return subList
     */
    public ArrayList<Subscription> getSubscriptionList() {
        return this.sub_list;
    }

    /**
     * Sets the subscription list with a given subList and refreshes the sum of
     * monetary charge from the subList's given Subscriptions.
     *
     * @param sub_list
     */
    public void setSubscriptionList(ArrayList<Subscription> sub_list) {
        this.sub_list = sub_list;     // Let SubscriptionList's ArrayList be given ArrayList argument
        this.refreshSum();          // Recalculate the sum of SubscriptionList
    }

    /**
     * Returns the sum of monetary charge of all Subscriptions within the SubscriptionList
     *
     * @retun totalCharge
     */
    public double getSum() {
        return this.total_charge;
    }

    /**
     * Recalculates the sum of monetary charge of all Subscriptions within the SubscriptionList
     * */
    public void refreshSum() {
        double sum = 0.00;      // Let the sum to accumulate start at 0.00
        for(int i = 0; i < this.sub_list.size(); i++) {
            sum = sum + this.sub_list.get(i).getCharge();    // For every subscription in arraylist, add its subscription onto the sum
        }
        this.total_charge = sum; // Let totalCharge be sum
    }

}
