package ch.cloudns.wanqiu.android;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class ImagePreviewActivity extends AppCompatActivity {

  public static final String EXTRA_IMAGE_URI = "extra_image_uri";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_preview);

    ImageView imageViewPreview = findViewById(R.id.image_view_preview);

    String imageUriString = getIntent().getStringExtra(EXTRA_IMAGE_URI);
    if (imageUriString != null) {
      Uri imageUri = Uri.parse(imageUriString);
      Glide.with(this).load(imageUri).fitCenter().into(imageViewPreview);
    }
  }
}
