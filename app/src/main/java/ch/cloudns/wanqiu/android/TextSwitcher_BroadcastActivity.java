package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import androidx.appcompat.app.AppCompatActivity;

public class TextSwitcher_BroadcastActivity extends AppCompatActivity {

  private final String[] broadcastMessages = {
    "欢迎使用飞行助手APP，祝您旅途愉快！",
    "请及时查看登机口变动信息。",
    "国际航班请提前2小时到达机场办理手续。",
    "您的健康码、登机证请准备好接受查验。",
    "本次活动限时促销，快来领取您的机票优惠券！"
  };

  private int currentIndex = 0;
  private Handler handler;
  private Runnable switchRunnable;
  private TextSwitcher textSwitcher;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_broadcast);

    // 初始化 TextSwitcher
    textSwitcher = findViewById(R.id.textSwitcher);
    textSwitcher.setFactory(
        new ViewSwitcher.ViewFactory() {
          @Override
          public View makeView() {
            TextView tv = new TextView(TextSwitcher_BroadcastActivity.this);
            tv.setTextSize(12);
            tv.setTextColor(0xFF000000);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            return tv;
          }
        });

    // 设置淡入淡出动画（也可以 slide）
    textSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

    // 设置第一条
    textSwitcher.setText(broadcastMessages[0]);

    // 定时切换
    handler = new Handler();
    switchRunnable =
        new Runnable() {
          @Override
          public void run() {
            currentIndex = (currentIndex + 1) % broadcastMessages.length;
            textSwitcher.setText(broadcastMessages[currentIndex]);
            handler.postDelayed(this, 5000);
          }
        };
    handler.postDelayed(switchRunnable, 5000);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (handler != null && switchRunnable != null) {
      handler.removeCallbacks(switchRunnable);
    }
  }
}
