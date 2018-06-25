package com.example.administrator.timenote.Ui;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.administrator.timenote.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class Sign_UpActivityTest {
    @Rule
    public ActivityTestRule<Sign_UpActivity> mActivityTestRule = new ActivityTestRule<>(Sign_UpActivity.class);
    @Test
    public void Sign_UpActivityTest1() {
        Espresso.onView(withId(R.id.e_mail_1)).perform(typeText("972357450@qq.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.name_1)).perform(typeText("lzc"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pwd_1)).perform(typeText("123456"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pwd_2)).perform(typeText("123"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sign_up_2)).perform(click());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void Sign_UpActivityTest2() {
        Espresso.onView(withId(R.id.e_mail_1)).perform(typeText("li_zhecong@qq.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.name_1)).perform(typeText("lzc"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pwd_1)).perform(typeText("12345678"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.pwd_2)).perform(typeText("12345678"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.sign_up_2)).perform(click());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}