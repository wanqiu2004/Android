package ch.cloudns.wanqiu.android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ProgressDialogActivity extends AppCompatActivity {

  private ProgressBar progressBar;
  private TextView textProgressPercentage;
  private Handler uiHandler;

  private int currentProgress = 0;
  private static final int MAX_PROGRESS = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    uiHandler = new Handler(Looper.getMainLooper());

    showProgressDialog();

    // 模拟后台线程任务
    new Thread(this::simulateBackgroundTask).start();
  }

  private void showProgressDialog() {
    // 使用 Material Design 的 AlertDialog
    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
    builder.setCancelable(false);

    // 加载自定义布局
    final android.view.View dialogView =
        getLayoutInflater().inflate(R.layout.dialog_progress, null);
    builder.setView(dialogView);

    // 引用布局中的组件
    progressBar = dialogView.findViewById(R.id.progress_bar);
    textProgressPercentage = dialogView.findViewById(R.id.text_progress_percentage);

    // 显示对话框
    builder.show();
  }

  private void simulateBackgroundTask() {
    while (currentProgress < MAX_PROGRESS) {
      try {
        Thread.sleep(100); // 模拟耗时操作
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      currentProgress += 2;

      // 更新 UI 必须切到主线程
      uiHandler.post(() -> updateProgress(currentProgress));
    }
  }

  private void updateProgress(int progress) {
    if (progressBar != null) {
      progressBar.setProgress(progress);
    }
    if (textProgressPercentage != null) {
      textProgressPercentage.setText(progress + "%");
    }

    if (progress >= MAX_PROGRESS) {
      // 进度完成后关闭对话框
//      dismissProgressDialog();
      startActivity(new Intent(this, MainActivity.class));
    }
  }

  private void dismissProgressDialog() {
    if (progressBar != null) {
      // 通过父视图拿到 AlertDialog 并关闭
      android.app.Dialog dialog = (android.app.Dialog) progressBar.getRootView().getParent();
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
      }
    }
  }
}
