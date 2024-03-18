package com.yunzhou.yunzhoumeeting;


import android.app.Activity;
import android.os.Bundle;

import com.yunzhou.yunzhoumeeting.sdk.YunZhouMeetingAPI;
import com.yunzhou.yunzhoumeeting.sdk.base.Constant;
import com.yunzhou.yunzhoumeeting.sdk.bean.MeetingOptions;
import com.yunzhou.yunzhoumeeting.sdk.bean.User;


public class MainActivity extends Activity {

    private static final String MEETING_SERVER_URL = "https://meeting.xxxxxxxxxx.com";
    private static final String MEETING_AVATAR_URL = "https://your_user_info_url/photos/user_photo_5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YunZhouMeetingAPI.getInstance().init(this);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.ll_create_meeting).setOnClickListener(view -> startMeeting(Constant.MEET_CONFIG_PAGE_ENTRY_VALUE_CREATE));
        findViewById(R.id.ll_join_meeting).setOnClickListener(view -> startMeeting(Constant.MEET_CONFIG_PAGE_ENTRY_VALUE_JOIN));
    }

    private void startMeeting(String entryType){
        int userId = Utils.generateRandomUserId();
        String userName = "demo-user" + userId;
        MeetingOptions options = new MeetingOptions.Builder()
                .entryType(entryType)
                // User信息包含userId, userName, userPhoto. UserPhoto为用户头像地址，如果不填为默认头像
                .userInfo(new User(String.valueOf(userId), userName, MEETING_AVATAR_URL))
                // 会议服务器地址请填入自己部署的会议服务器地址或者向技术支持获取
                .serverUrl(MEETING_SERVER_URL)
                .build();
        YunZhouMeetingAPI.getInstance().start(this, options);
    }

}