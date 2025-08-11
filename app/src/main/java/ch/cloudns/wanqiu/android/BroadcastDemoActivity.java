package ch.cloudns.wanqiu.android;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.*;

public class BroadcastDemoActivity extends Activity {

    private final String[] broadcastMessages = {
            "欢迎使用飞行助手APP，祝您旅途愉快！",
            "请及时查看登机口变动信息。",
            "国际航班请提前2小时到达机场办理手续。",
            "您的健康码、登机证请准备好接受查验。",
            "本次活动限时促销，快来领取您的机票优惠券！"
    };

    private int currentIndex = 0;
    private Handler handler;
    private Runnable switchTextRunnable;
    private TextSwitcher textSwitcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 根布局
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.WHITE);
        rootLayout.setPadding(40, 100, 40, 40);
        rootLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        setContentView(rootLayout);

        // 广播栏容器，圆角淡蓝色背景
        LinearLayout broadcastContainer = new LinearLayout(this);
        broadcastContainer.setOrientation(LinearLayout.HORIZONTAL);
        broadcastContainer.setPadding(30, 20, 30, 20);
        broadcastContainer.setGravity(Gravity.CENTER_VERTICAL);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.parseColor("#E3F2FD")); // 浅蓝色背景
        bg.setCornerRadius(38f);
        broadcastContainer.setBackground(bg);

        // 添加一个广播图标
        ImageView icon = new ImageView(this);
        icon.setImageResource(android.R.drawable.ic_dialog_info);
        icon.setColorFilter(Color.parseColor("#1976D2"));
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        iconParams.setMarginEnd(20);
        broadcastContainer.addView(icon, iconParams);

        // TextSwitcher 作为广播消息显示
        textSwitcher = new TextSwitcher(this);
        textSwitcher.setFactory(() -> {
            TextView tv = new TextView(BroadcastDemoActivity.this);
            tv.setTextSize(14);
            //tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            return tv;
        });

        // 使用淡入淡出动画，更温柔
        textSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));

        LinearLayout.LayoutParams switcherParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f);
        broadcastContainer.addView(textSwitcher, switcherParams);

        // 添加广播容器到根布局
        rootLayout.addView(broadcastContainer, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // 设置初始广播消息
        textSwitcher.setText(broadcastMessages[currentIndex]);

        // 点击弹出Dialog显示全部消息
        broadcastContainer.setOnClickListener(v -> showBroadcastDialog());

        // 轮播逻辑
        handler = new Handler();
        switchTextRunnable = new Runnable() {
            @Override
            public void run() {
                currentIndex = (currentIndex + 1) % broadcastMessages.length;
                textSwitcher.setText(broadcastMessages[currentIndex]);
                handler.postDelayed(this, 4000); // 4秒切换一次，更从容
            }
        };
        handler.postDelayed(switchTextRunnable, 4000);
    }

    private void showBroadcastDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 容器布局
        LinearLayout dialogLayout = new LinearLayout(this);
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setPadding(40, 40, 40, 40);
        dialogLayout.setBackgroundColor(Color.WHITE);

        // 标题
        TextView dialogTitle = new TextView(this);
        dialogTitle.setText("全部广播消息");
        dialogTitle.setTextSize(20);
        dialogTitle.setTextColor(Color.parseColor("#1976D2"));
        dialogTitle.setGravity(Gravity.CENTER);
        dialogTitle.setPadding(0, 0, 0, 20);
        dialogLayout.addView(dialogTitle);

        // ListView 显示所有广播
        ListView listView = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                broadcastMessages);
        listView.setAdapter(adapter);
        dialogLayout.addView(listView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                600 // 限制高度，方便滚动
        ));

        // 关闭按钮
        Button closeBtn = new Button(this);
        closeBtn.setText("关闭");
        closeBtn.setTextColor(Color.WHITE);
        closeBtn.setBackgroundColor(Color.parseColor("#1976D2"));
        closeBtn.setPadding(0, 20, 0, 20);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.topMargin = 30;
        dialogLayout.addView(closeBtn, btnParams);

        closeBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.setContentView(dialogLayout);

        // 设置dialog宽度
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.85),
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && switchTextRunnable != null) {
            handler.removeCallbacks(switchTextRunnable);
        }
    }
}
