package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AcmeConsoleActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private AlphabetIndexView alphabetIndexView;
  private LinearLayoutManager layoutManager;
  private AcmeSectionAdapter adapter;
  private List<String> data;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_acme_console);

    recyclerView = findViewById(R.id.recyclerView);
    alphabetIndexView = findViewById(R.id.alphabetIndex);

    // 初始化数据
    data = getData();
    adapter = new AcmeSectionAdapter(this, data);
    layoutManager = new LinearLayoutManager(this);

    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);

    alphabetIndexView.setOnLetterChangedListener(
        letter -> {
          Toast.makeText(this, "快速定位到: " + letter, Toast.LENGTH_SHORT).show();
          scrollToLetter(letter);
        });
  }

  private List<String> getData() {
    List<String> list = new ArrayList<>();
    // 企业级模块示例
    String[] modules = {
      "Acme Dashboard",
      "Alarm Manager",
      "Alert Service",
      "Configuration Center",
      "Control Panel",
      "Monitoring",
      "Metrics",
      "User Management",
      "Permission Manager",
      "Zookeeper Monitor",
      "Acme Dashboard",
      "Alarm Manager",
      "Alert Service",
      "Configuration Center",
      "Control Panel",
      "Monitoring",
      "Metrics",
      "User Management",
      "Permission Manager",
      "Zookeeper Monitor"
    };
    Collections.addAll(list, modules);
    Collections.sort(list); // 按字母排序
    return list;
  }

  private void scrollToLetter(String letter) {
    for (int i = 0; i < data.size(); i++) {
      if (data.get(i).toUpperCase().startsWith(letter)) {
        recyclerView.scrollToPosition(i);
        break;
      }
    }
  }
}
