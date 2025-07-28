package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Rotation;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class KonfettiActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_konfetti);

    KonfettiView konfettiView = findViewById(R.id.konfettiView);
    Party party =
        new Party(
            Angle.TOP, // angle（角度从哪边喷出）
            90, // spread（喷发范围）
            30f, // speed（起始速度）
            40f, // maxSpeed（最大速度）
            0.8f, // damping（阻力）
            Arrays.asList(
                Size.Companion.getSMALL(),
                Size.Companion.getMEDIUM(),
                Size.Companion.getLARGE()), // size（你可以添加多个）
            Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def), // 颜色列表
            Arrays.asList(Shape.Circle.INSTANCE, Shape.Square.INSTANCE), // 图形列表
            1000L, // timeToLive（生命周期）
            true, // fadeOutEnabled（是否淡出）
            new Position.Relative(0.5f, 1f), // 位置
            0, // delay（延迟开始）
            new Rotation(),
            new Emitter(2, TimeUnit.SECONDS).perSecond(30) // emitter 配置
            );

    findViewById(R.id.start).setOnClickListener(
            v -> { konfettiView.start(party); }
    );
  }
}
