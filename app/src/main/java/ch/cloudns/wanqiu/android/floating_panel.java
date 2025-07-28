package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class floating_panel extends AppCompatActivity {
  private LinearLayout floatingPanel;
  private View panelHeader;

  private float dX, dY;
  private int lastAction;

  // 用于限制拖拽边界
  private int rootWidth, rootHeight;
  private int panelWidth, panelHeight;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_floating_panel);

    floatingPanel = findViewById(R.id.floatingPanel);
    panelHeader = findViewById(R.id.panelHeader);

    // 在布局完成后拿到真正的宽高
    final View root = findViewById(R.id.root);
    root.getViewTreeObserver()
        .addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
              @Override
              public void onGlobalLayout() {
                rootWidth = root.getWidth();
                rootHeight = root.getHeight();
                panelWidth = floatingPanel.getWidth();
                panelHeight = floatingPanel.getHeight();

                // 初始位置（可自行调整）
                floatingPanel.setX(24f);
                floatingPanel.setY(100f);

                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
              }
            });

    panelHeader.setOnTouchListener(
        (view, event) -> {
          switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
              dX = floatingPanel.getX() - event.getRawX();
              dY = floatingPanel.getY() - event.getRawY();
              lastAction = MotionEvent.ACTION_DOWN;
              return true;

            case MotionEvent.ACTION_MOVE:
              lastAction = MotionEvent.ACTION_MOVE;
              float newX = event.getRawX() + dX;
              float newY = event.getRawY() + dY;

              // 限制不超出父布局范围
              newX = Math.max(0, Math.min(newX, rootWidth - panelWidth));
              newY = Math.max(0, Math.min(newY, rootHeight - panelHeight));

              floatingPanel.setX(newX);
              floatingPanel.setY(newY);
              return true;

            case MotionEvent.ACTION_UP:
              if (lastAction == MotionEvent.ACTION_DOWN) {
                // 点击事件（如果你想在点击标题栏时做点事）
                view.performClick();
              } else {
                // 可选：释放时吸附到左右边缘
                // snapToEdge();
              }
              lastAction = MotionEvent.ACTION_UP;
              return true;

            default:
              return false;
          }
        });
  }

  private void snapToEdge() {
    float middle = rootWidth / 2f;
    float targetX = (floatingPanel.getX() + panelWidth / 2f) >= middle ? rootWidth - panelWidth : 0;

    floatingPanel.animate().x(targetX).setDuration(90).start();
  }
}
