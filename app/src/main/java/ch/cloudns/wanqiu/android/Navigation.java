package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class Navigation extends AppCompatActivity {

  private RecyclerView recyclerViewTweets;
  private BottomNavigationView bottomNavigationMain;
  private TweetAdapter tweetAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initViews();
    setupRecyclerView();
    setupBottomNavigation();
  }

  /** 初始化视图组件 */
  private void initViews() {
    recyclerViewTweets = findViewById(R.id.recyclerViewTweets);
    bottomNavigationMain = findViewById(R.id.bottomNavigationMain);
  }

  /** 配置 RecyclerView */
  private void setupRecyclerView() {
    recyclerViewTweets.setLayoutManager(new LinearLayoutManager(this));
    tweetAdapter = new TweetAdapter(generateMockTweets());
    recyclerViewTweets.setAdapter(tweetAdapter);
  }

  /** 配置底部导航栏事件 */
  private void setupBottomNavigation() {
    bottomNavigationMain.setOnItemSelectedListener(
        item -> {
          int itemId = item.getItemId();
          if (itemId == R.id.menu_home) {
            // TODO: 切换到首页
            return true;
          } else if (itemId == R.id.menu_search) {
            // TODO: 切换到搜索
            return true;
          } else // TODO: 切换到个人资料
          if (itemId == R.id.menu_notifications) {
            // TODO: 切换到通知
            return true;
          } else return itemId == R.id.menu_profile;
        });
  }

  /** 模拟推文数据 */
  @NonNull
  private List<String> generateMockTweets() {
    List<String> tweets = new ArrayList<>();
    for (int i = 1; i <= 100; i++) {
      tweets.add("Tweet 内容 " + i);
    }
    return tweets;
  }
}
