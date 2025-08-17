package ch.cloudns.wanqiu.android;

import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class ProgressIndicators extends AppCompatActivity {

  private LinearProgressIndicator linearProgress;
  private CircularProgressIndicator circularProgress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_progress_indicators);

    linearProgress = findViewById(R.id.linearProgress);
    circularProgress = findViewById(R.id.circularProgress);

    // 创建 ValueAnimator：0 -> 100 -> 0，循环
    ValueAnimator animator = ValueAnimator.ofInt(0, 100);
    animator.setDuration(2000); // 2秒从0到100
    animator.setRepeatMode(ValueAnimator.REVERSE); // 回退到0
    animator.setRepeatCount(ValueAnimator.INFINITE); // 无限循环
    animator.addUpdateListener(
        animation -> {
          int progress = (int) animation.getAnimatedValue();
          linearProgress.setProgress(progress);
          circularProgress.setProgress(progress);
        });
    animator.start();
  }
}
