package com.yunzhou.yunzhoumeeting;


import android.app.Activity;
import android.os.Bundle;

import com.yunzhou.yunzhoumeeting.sdk.YunZhouMeetingAPI;
import com.yunzhou.yunzhoumeeting.sdk.base.Constant;
import com.yunzhou.yunzhoumeeting.sdk.bean.MeetingOptions;
import com.yunzhou.yunzhoumeeting.sdk.bean.User;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YunZhouMeetingAPI.getInstance().init(this);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.ll_create_meeting).setOnClickListener(view -> create());
        findViewById(R.id.ll_join_meeting).setOnClickListener(view -> join());
    }

    /**
     * 创建会议
     */
    private void create(){
        int userId = Utils.generateRandomUserId();
        String userName = "demo-user" + userId;
        MeetingOptions options = new MeetingOptions.Builder()
                .entryType(Constant.MEET_CONFIG_PAGE_ENTRY_VALUE_CREATE)
                // User信息包含userId, userName, userPhoto. UserPhoto为用户头像地址，如果不填为默认头像
                .userInfo(new User(String.valueOf(userId), userName, "https://your_user_info_url/photos/user_photo_5"))
                // 会议服务器地址请填入自己部署的会议服务器地址或者向技术支持获取
                .serverUrl("https://meeting.xxxxxxxxxx.com")
                .build();
        YunZhouMeetingAPI.getInstance().start(this, options);
    }

    /**
     * 加入会议
     */
    private void join(){
        int userId = Utils.generateRandomUserId();
        String userName = "demo-user" + userId;
        MeetingOptions options = new MeetingOptions.Builder()
                .entryType(Constant.MEET_CONFIG_PAGE_ENTRY_VALUE_JOIN)
                // User信息包含userId, userName, userPhoto. UserPhoto为用户头像地址，如果不填为默认头像
                .userInfo(new User(String.valueOf(userId), userName, "https://your_user_info_url/photos/user_photo_5"))
                .serverUrl("https://meeting.xxxxxxxxxx.com")
                .build();
        YunZhouMeetingAPI.getInstance().start(this, options);
    }

}