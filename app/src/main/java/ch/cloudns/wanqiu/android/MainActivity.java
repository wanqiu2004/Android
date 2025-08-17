package ch.cloudns.wanqiu.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    LinearLayout container = findViewById(R.id.container);
    LinearLayout.LayoutParams lp = ViewUtils.wrapWithMargins(this, 18, 4);

    container.addView(
        ViewUtils.newPrimaryButton(
            this, "Floating Panel", v -> startActivity(new Intent(this, floating_panel.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this,
            "Image Carousel",
            v -> startActivity(new Intent(this, ImageCarouselActivity.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(this, "Test", v -> startActivity(new Intent(this, Test.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this, "FrameLayout", v -> startActivity(new Intent(this, FrameLayout.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this, "ACME", v -> startActivity(new Intent(this, AcmeConsoleActivity.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this, "Konfetti", v -> startActivity(new Intent(this, BiometricPromptTest.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this,
            "BiometricPromptTest",
            v -> startActivity(new Intent(this, BiometricPromptTest.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this, "Overlay", v -> startActivity(new Intent(this, FloatingButtonActivity.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this,
            "TextSwitcher_BroadcastActivity",
            v -> startActivity(new Intent(this, TextSwitcher_BroadcastActivity.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this,
            "BroadcastActivity",
            v -> startActivity(new Intent(this, BroadcastDemoActivity.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this, "Broadcast", v -> startActivity(new Intent(this, Broadcast.class))),
        lp);
    container.addView(
        ViewUtils.newPrimaryButton(
            this,
            "ProgressIndicators",
            v -> startActivity(new Intent(this, ProgressIndicators.class))),
        lp);

    container.addView(
        ViewUtils.newPrimaryButton(
            this,
            "ImagePickerActivity",
            v -> startActivity(new Intent(this, ImagePickerActivity.class))),
        lp);
  }
}
