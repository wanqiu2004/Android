package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public abstract class TopAppBarActivity extends AppCompatActivity {
  protected MaterialToolbar topAppBar;

  @Override
  public void onCreate(
      @Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  protected void setupTopAppBar() {
    topAppBar = findViewById(R.id.topAppBar);
    if (topAppBar != null) {
      setSupportActionBar(topAppBar);
      topAppBar.setNavigationOnClickListener(v -> onBackPressed());
    }
  }
}
