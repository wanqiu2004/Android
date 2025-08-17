package ch.cloudns.wanqiu.android;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FloatingButtonActivity extends AppCompatActivity {

  private static final int REQUEST_OVERLAY_PERMISSION = 1234;
  private WindowManager windowManager;
  private Button floatingButton;
  private WindowManager.LayoutParams params;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // 不设置布局，纯悬浮窗显示
    checkOverlayPermission();
  }

  private void checkOverlayPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.canDrawOverlays(this)) {
        // 申请悬浮窗权限
        Intent intent =
            new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, REQUEST_OVERLAY_PERMISSION);
      } else {
        // 已有权限，直接显示悬浮窗
        showFloatingButton();
      }
    } else {
      // 6.0 以下无需动态权限，直接显示
      showFloatingButton();
    }
  }

  private void showFloatingButton() {
    windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

    floatingButton = new Button(this);
    floatingButton.setText("测试按钮");

    // 设置点击事件
    floatingButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            Toast.makeText(FloatingButtonActivity.this, "悬浮按钮被点击！", Toast.LENGTH_SHORT).show();

            // TODO: 在这里写自动点击测试逻辑
          }
        });

    int layoutFlag;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    } else {
      layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
    }

    params =
        new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT);

    params.gravity = Gravity.CENTER;

    windowManager.addView(floatingButton, params);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (windowManager != null && floatingButton != null) {
      windowManager.removeView(floatingButton);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_OVERLAY_PERMISSION) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (Settings.canDrawOverlays(this)) {
          showFloatingButton();
        } else {
          Toast.makeText(this, "悬浮窗权限未授予，功能受限！", Toast.LENGTH_LONG).show();
        }
      }
    }
  }
}
