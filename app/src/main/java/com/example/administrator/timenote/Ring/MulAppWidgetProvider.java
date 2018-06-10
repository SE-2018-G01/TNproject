package com.example.administrator.timenote.Ring;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.example.administrator.timenote.R;
import com.example.administrator.timenote.Ui.MainActivity;

public class MulAppWidgetProvider extends AppWidgetProvider {

    public static final String CHANGE_IMAGE = "com.example.joy.action.CHANGE_IMAGE";

    private RemoteViews mRemoteViews;
    private ComponentName mComponentName;




    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.mul_app_widget_provider);

        mRemoteViews.setTextViewText(R.id.refresh, "点击跳转到Activity");
        Intent skipIntent = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 200, skipIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.refresh, pi);
        // 设置 ListView 的adapter。
        // (01) intent: 对应启动 ListViewService(RemoteViewsService) 的intent
        // (02) setRemoteAdapter: 设置 ListView 的适配器
        // 通过setRemoteAdapter将 ListView 和ListViewService关联起来，
        // 以达到通过 GridWidgetService 更新 gridview 的目的
        Intent lvIntent = new Intent(context, ListViewService.class);
        mRemoteViews.setRemoteAdapter(R.id.lv_test, lvIntent);
        mRemoteViews.setEmptyView(R.id.lv_test,android.R.id.empty);

        // 设置响应 ListView 的intent模板
        // 说明：“集合控件(如GridView、ListView、StackView等)”中包含很多子元素，如GridView包含很多格子。
        // 它们不能像普通的按钮一样通过 setOnClickPendingIntent 设置点击事件，必须先通过两步。
        // (01) 通过 setPendingIntentTemplate 设置 “intent模板”，这是比不可少的！
        // (02) 然后在处理该“集合控件”的RemoteViewsFactory类的getViewAt()接口中 通过 setOnClickFillInIntent 设置“集合控件的某一项的数据”

        /*
         * setPendingIntentTemplate 设置pendingIntent 模板
         * setOnClickFillInIntent   可以将fillInIntent 添加到pendingIntent中
         */
        Intent toIntent = new Intent(CHANGE_IMAGE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 200, toIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);



        mComponentName = new ComponentName(context, MulAppWidgetProvider.class);
        appWidgetManager.updateAppWidget(mComponentName, mRemoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(TextUtils.equals(CHANGE_IMAGE,intent.getAction())){
            Bundle extras = intent.getExtras();
            int position = extras.getInt(ListViewService.INITENT_DATA);
            Intent intent1 =new Intent();
            intent1.setComponent(new ComponentName("com.example.administrator.timenote","com.example.administrator.timenote.Ui.Task_Update"));
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.mul_app_widget_provider);
            mComponentName = new ComponentName(context, MulAppWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(mComponentName, mRemoteViews);
        }
    }
}