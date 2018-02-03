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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a subscription
 *
 * @author hingyue
 * @version 1.0
 * */
public class Subscription {
    private String name;        // Have a maximum of 20 characters in name.
    private Date date;
    private double charge;
    private String comment;     // Have a maximum of 30 characters in comments.

    /**
     * Consctructs a Subscription object
     */
    public Subscription() {
        this.name = "";
        this.date = new Date();
        this.charge = 0.00;
        this.comment = "";
    }

    /**
     * Constructs a Subscription object
     *
     * @param name
     */
    public Subscription(String name) {
        this();
        this.name = name;
    }

    /**
     * Consctructs a Subscription object
     *
     * @param name
     * @param date
     */
    public Subscription(String name, Date date) {
        this(name);
        this.date = date;
    }

    /**
     * Constructs a Subscription object
     *
     * @param name
     * @param date
     * @param charge
     */
    public Subscription(String name, Date date, double charge) {
        this(name, date);
        this.charge = charge;
    }

    /**
     * Constructs a Subscription object
     *
     * @param name
     * @param date
     * @param charge
     * @param comment
     */
    public Subscription(String name, Date date, double charge, String comment) {
        this(name, date, charge);
        this.comment = comment;
    }

    /**
     * Returns the name from the Subscription object
     *
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name from the Subscription object
     *
     * @param name
     * @throws StringTooLongException
     */
    public void setName(String name) throws StringTooLongException {
        /* if/else statements taken from Tweet.java in Echo-Gambino/lonelyTwitter,
        which was forked from vingk/lonelyTwitter.
        */
        if (name.length() > 20) {
            throw new StringTooLongException();
        } else {
            this.name = name;
        }
    }

    /**
     * Returns the date of Subscription object
     *
     * @return date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Sets the date of Subscription object with an argument of a Date datatype.
     *
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Sets the date of Subscription object with an argument of a String datatype.
     *
     * @param date
     */
    public void setDate(String date) {
        String date_format = "EEE MMM dd HH:mm:ss zzz yyyy";
        try {
            this.date = new SimpleDateFormat(date_format).parse(date);
        } catch (Exception e) {}
    }

    /**
     * Returns the charge of the Subscription object.
     *
     * @return charge
     */
    public double getCharge() {
        return this.charge;
    }

    /**
     * Sets the charge of the Subscription object with an argument that has a double datatype.
     *
     * @param charge
     * @throws NegativeIntegerException
     */
    public void setCharge(double charge) throws NegativeIntegerException {
        if (charge < 0.0) {
            throw new NegativeIntegerException();
        } else {
            this.charge = charge;
        }
    }

    /**
     * Returns the comment of the Subscription object.
     *
     * @return comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Sets the comment of the Subscription object with an argument that has a String datatype.
     *
     * @param comment
     * @throws StringTooLongException
     */
    public void setComment(String comment) throws StringTooLongException {
        /* if/else statements taken from Tweet.java in Echo-Gambino/lonelyTwitter,
        which was forked from vingk/lonelyTwitter.
        */
        if (comment.length() > 30) {
            throw new StringTooLongException();
        } else {
            this.comment = comment;
        }
    }

    /**
     * Replaces Subscription's .toString() method to return all its data (except for comment)
     * in a visually servicable format.
     *
     * @return message
     * */
    @Override
    public String toString() {
        String message = name + ": \t $" + Double.toString(charge) + "\n"
                + date.toString();
        return message;
    }

}




