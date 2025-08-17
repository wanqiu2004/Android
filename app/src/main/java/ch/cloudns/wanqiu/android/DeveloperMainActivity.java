package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class DeveloperMainActivity extends AppCompatActivity {

  private MaterialToolbar topAppBar;
  private Button btnStartTutorial;
  private Button btnApiDocs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_developer_main);

    bindViews();
    setupToolbar();
    setupListeners();
  }

  private void bindViews() {
    topAppBar = findViewById(R.id.topAppBar);
    btnStartTutorial = findViewById(R.id.btnStartTutorial);
    btnApiDocs = findViewById(R.id.btnApiDocs);
  }

  private void setupToolbar() {
    setSupportActionBar(topAppBar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      // 显示左上角返回按钮（Up 按钮）
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  private void setupListeners() {
    btnStartTutorial.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            // TODO: 启动新手教程界面示例
            // Intent intent = new Intent(DeveloperMainActivity.this, TutorialActivity.class);
            // startActivity(intent);
          }
        });

    btnApiDocs.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            // TODO: 打开API文档链接或界面示例
            // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yourapi.docs"));
            // startActivity(intent);
          }
        });
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      // 点击返回箭头，关闭页面
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
