package com.example.administrator.timenote.Ring;

        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.widget.RemoteViews;
        import android.widget.RemoteViewsService;

        import com.example.administrator.timenote.Manager.TaskManager.LoadAllEvent;
        import com.example.administrator.timenote.Manager.UserManager.UserLogin;
        import com.example.administrator.timenote.Manager.UserManager.loginServe;
        import com.example.administrator.timenote.Model.BeanEventInformation;
        import com.example.administrator.timenote.Model.BeanUserInformation;
        import com.example.administrator.timenote.R;
        import com.example.administrator.timenote.Ui.MainActivity;
        import com.example.administrator.timenote.Ui.taskAdapter;

        import java.util.ArrayList;
        import java.util.Date;
        import java.util.List;
        import java.util.Map;

        import static com.example.administrator.timenote.Model.BeanEventInformation.allEventList;

public class ListViewService extends RemoteViewsService {
    public static final String INITENT_DATA = "extra_data";
    List<String> todayEvent = new ArrayList<>();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsFactory {

        private Context mContext;

        private List<String> mList = new ArrayList<>();

        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
//            mList.add("一");
//            mList.add("二");
//            mList.add("三");
//            mList.add("四");
//            mList.add("五");
//            mList.add("六");
            final Map<String, String> map = new loginServe().getSaveUserInfo(ListViewService.this);
            if (map != null){
                Thread r = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        UserLogin userLogin = new UserLogin();
                        userLogin.getRemoteInfo(map.get("useremail"));
                    }
                });
                r.start();
                try {
                    r.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        LoadAllEvent loadAllEvent = new LoadAllEvent();
                        loadAllEvent.getRemoteInfo(BeanUserInformation.currentLoginUser.getUserid());
                    }
                });
                t.start();
                try {
                    t.join(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long millis;
                for (int i = 0; i < allEventList.size(); i++){
                    millis = allEventList.get(i).getEventdate().getTime()-new Date(System.currentTimeMillis()).getTime();
                    if ((millis >= 0) && (millis <= (1000 * 60 * 60 * 24)) && (!allEventList.get(i).getEventdate().toString().equals("Sat Jan 01 00:00:00 GMT+08:00 1")))
                        todayEvent.add(allEventList.get(i).getEventname());
                }
                //  System.out.print(1);
            }
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            todayEvent.clear();
        }

        @Override
        public int getCount() {
            return  todayEvent.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.my_widget_layout_item);
            if (todayEvent == null){
                views.setTextViewText(R.id.task_1, "今日无任务");
            }
            else {
                views.setTextViewText(R.id.task_1, todayEvent.get(position));
            }
            Bundle extras = new Bundle();
            extras.putInt(ListViewService.INITENT_DATA, position);
            Intent changeIntent = new Intent();
            changeIntent.setAction(MulAppWidgetProvider.CHANGE_IMAGE);
            changeIntent.putExtras(extras);
            /* android.R.layout.simple_list_item_1 --- id --- text1
             * listview的item click：将 changeIntent 发送，
             * changeIntent 它默认的就有action 是provider中使用 setPendingIntentTemplate 设置的action*/

            views.setOnClickFillInIntent(R.id.task_1, changeIntent);
            return views;
        }

        /* 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
         * 如果返回null 显示默认界面
         * 否则 加载自定义的，返回RemoteViews
         */
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }

}