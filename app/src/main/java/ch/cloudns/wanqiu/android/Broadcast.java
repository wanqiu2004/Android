package ch.cloudns.wanqiu.android;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Broadcast extends AppCompatActivity {

  private final String[] broadcastMessages = {
    "欢迎使用飞行助手APP，祝您旅途愉快！",
    "请及时查看登机口变动信息。",
    "国际航班请提前2小时到达机场办理手续。",
    "您的健康码、登机证请准备好接受查验。",
    "本次活动限时促销，快来领取您的机票优惠券！"
  };

  private int currentIndex = 0;
  private Handler handler = new Handler();
  private Runnable switchTextRunnable;
  private TextSwitcher textSwitcher;
  private LinearLayout broadcastContainer;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_broadcast_demo);

    textSwitcher = findViewById(R.id.textSwitcher);
    broadcastContainer = findViewById(R.id.broadcastContainer);

    // TextSwitcher 工厂和动画
    textSwitcher.setFactory(
        () -> {
          TextView tv = new TextView(this);
          tv.setTextSize(16);
          tv.setTextColor(0xFF000000);
          tv.setGravity(Gravity.CENTER_VERTICAL);
          return tv;
        });
    textSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
    textSwitcher.setText(broadcastMessages[currentIndex]);

    // 轮播逻辑
    switchTextRunnable =
        new Runnable() {
          @Override
          public void run() {
            currentIndex = (currentIndex + 1) % broadcastMessages.length;
            textSwitcher.setText(broadcastMessages[currentIndex]);
            handler.postDelayed(this, 4000);
          }
        };
    handler.postDelayed(switchTextRunnable, 4000);

    // 点击弹出Dialog
    broadcastContainer.setOnClickListener(v -> showBroadcastDialog());
  }

  private void showBroadcastDialog() {
    Dialog dialog = new Dialog(this);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

    LinearLayout dialogLayout = new LinearLayout(this);
    dialogLayout.setOrientation(LinearLayout.VERTICAL);
    dialogLayout.setPadding(50, 50, 50, 50);
    dialogLayout.setBackgroundColor(0xFFFFFFFF);

    TextView title = new TextView(this);
    title.setText("全部广播消息");
    title.setTextSize(20);
    title.setTextColor(0xFF1976D2);
    title.setGravity(Gravity.CENTER);
    title.setPadding(0, 0, 0, 20);
    dialogLayout.addView(title);

    ListView listView = new ListView(this);
    ArrayAdapter<String> adapter =
        new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, broadcastMessages);
    listView.setAdapter(adapter);
    dialogLayout.addView(
        listView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 600));

    Button closeBtn = new Button(this);
    closeBtn.setText("关闭");
    closeBtn.setTextColor(0xFFFFFFFF);
    closeBtn.setBackgroundColor(0xFF1976D2);
    closeBtn.setPadding(0, 20, 0, 20);
    LinearLayout.LayoutParams btnParams =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    btnParams.topMargin = 30;
    dialogLayout.addView(closeBtn, btnParams);

    closeBtn.setOnClickListener(v -> dialog.dismiss());

    dialog.setContentView(dialogLayout);

    if (dialog.getWindow() != null) {
      dialog
          .getWindow()
          .setLayout(
              (int) (getResources().getDisplayMetrics().widthPixels * 0.85),
              LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    dialog.show();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    handler.removeCallbacks(switchTextRunnable);
  }
}
