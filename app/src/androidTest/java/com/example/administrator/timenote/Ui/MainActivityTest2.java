package com.example.administrator.timenote.Ui;


import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.administrator.timenote.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Test
    public void mainActivityTest1() {
        //通过id找到edittext，在里面输入2并关闭输入法
        Espresso.onView(withId(R.id.userid_1)).perform(typeText(""), closeSoftKeyboard());
        //通过id找到edittext，在里面输入5并关闭输入法
        Espresso.onView(withId(R.id.pwd_1)).perform(typeText(""), closeSoftKeyboard());
        //通过id找到button，执行点击事件
        Espresso.onView(withId(R.id.loading_1)).perform(click());

    }
    @Test
    public void mainActivityTest3() {
        //通过id找到edittext，在里面输入2并关闭输入法
        Espresso.onView(withId(R.id.userid_1)).perform(typeText("972357450@qq.com"), closeSoftKeyboard());
        //通过id找到edittext，在里面输入5并关闭输入法
        Espresso.onView(withId(R.id.pwd_1)).perform(typeText(""), closeSoftKeyboard());
        //通过id找到button，执行点击事件
        Espresso.onView(withId(R.id.loading_1)).perform(click());

    }
    @Test
    public void mainActivityTest2() {
        //通过id找到edittext，在里面输入2并关闭输入法
        Espresso.onView(withId(R.id.userid_1)).perform(typeText("972357450@qq.com"), closeSoftKeyboard());
        //通过id找到edittext，在里面输入5并关闭输入法
        Espresso.onView(withId(R.id.pwd_1)).perform(typeText("87866286"), closeSoftKeyboard());
        //通过id找到button，执行点击事件
        Espresso.onView(withId(R.id.loading_1)).perform(click());

    }



}
