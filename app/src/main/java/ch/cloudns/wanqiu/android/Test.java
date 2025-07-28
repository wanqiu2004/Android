package ch.cloudns.wanqiu.android;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Rotation;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Test extends AppCompatActivity {

  private SensitiveContentView scv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_test);

    scv = findViewById(R.id.sensitiveView);

    // 1) 真实内容 in.jpg
    try (InputStream inputStream = getAssets().open("in.jpg")) {
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      scv.setRealBitmap(bitmap);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // 2) 占位图（如果 layout 里已经写死了 @drawable/preview，可以不传）
    try (InputStream inputStream = getAssets().open("preview.png")) {
      Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
      scv.setPlaceholderBitmap(bitmap);
    } catch (IOException e) {
      // ignore or log
    }

    // 3) 如果你想默认就显示真实内容（比如用户已经在设置里允许过），可以：
    // scv.setRevealed(true);



    @SuppressLint({"MissingInflatedId", "LocalSuppress"}) KonfettiView konfettiView = findViewById(R.id.konfettiView);
    Party party =
            new Party(
                    Angle.TOP, // angle（角度从哪边喷出）
                    360, // spread（喷发范围）
                    30f, // speed（起始速度）
                    40f, // maxSpeed（最大速度）
                    0.8f, // damping（阻力）
                    Arrays.asList(
                            Size.Companion.getSMALL(),
                            Size.Companion.getMEDIUM(),
                            Size.Companion.getLARGE()), // size（你可以添加多个）
                    Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def), // 颜色列表
                    Arrays.asList(Shape.Circle.INSTANCE, Shape.Square.INSTANCE), // 图形列表
                    200L, // timeToLive（生命周期）
                    true, // fadeOutEnabled（是否淡出）
                    new Position.Relative(0.5f, 0.5f), // 位置
                    0, // delay（延迟开始）
                    new Rotation(),
                    new Emitter(1, TimeUnit.SECONDS).perSecond(40) // emitter 配置
            );

    findViewById(R.id.toggle).setOnClickListener(v -> {scv.setRevealed(false);konfettiView.start(party);});
  }
}
