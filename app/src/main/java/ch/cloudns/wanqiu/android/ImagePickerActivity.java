package ch.cloudns.wanqiu.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ImagePickerActivity extends AppCompatActivity
    implements ImageGridAdapter.OnImageClickListener {

  private final List<Uri> selectedImageUris = new ArrayList<>();
  private RecyclerView recyclerViewImageGrid;
  private ImageGridAdapter imageGridAdapter;

  private final ActivityResultLauncher<Intent> imagePickerLauncher =
      registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),
          result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
              if (result.getData().getClipData() != null) { // 多选
                int count = result.getData().getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                  Uri uri = result.getData().getClipData().getItemAt(i).getUri();
                  selectedImageUris.add(uri);
                }
              } else if (result.getData().getData() != null) { // 单选
                Uri uri = result.getData().getData();
                selectedImageUris.add(uri);
              }
              imageGridAdapter.notifyDataSetChanged();
            } else {
              Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
            }
          });

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_image_picker);

    // 1. 启用 Edge-to-Edge
    EdgeToEdge.enable(this);

    // 2. 设置根布局 WindowInsets 监听器，处理安全区
    final View rootLayout = findViewById(R.id.layout_root);
    ViewCompat.setOnApplyWindowInsetsListener(
        rootLayout,
        (v, insets) -> {
          int topInset = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top;
          int bottomInset = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
          v.setPadding(v.getPaddingLeft(), topInset, v.getPaddingRight(), bottomInset);
          return WindowInsetsCompat.CONSUMED;
        });
    ViewCompat.requestApplyInsets(rootLayout);

    // 3. 初始化 RecyclerView
    recyclerViewImageGrid = findViewById(R.id.recycler_view_image_grid);
    recyclerViewImageGrid.setLayoutManager(new GridLayoutManager(this, 3));
    imageGridAdapter = new ImageGridAdapter(selectedImageUris, this);
    recyclerViewImageGrid.setAdapter(imageGridAdapter);

    // 4. 按钮选择图片
    findViewById(R.id.button_select_images).setOnClickListener(v -> openImagePicker());
  }

  private void openImagePicker() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
    intent.addCategory(Intent.CATEGORY_OPENABLE);
    intent.setType("image/*");
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
    imagePickerLauncher.launch(intent);
  }

  @Override
  public void onImageClick(Uri imageUri) {
    if (imageUri == null) return;
    Intent intent = new Intent(this, ImagePreviewActivity.class);
    intent.putExtra(ImagePreviewActivity.EXTRA_IMAGE_URI, imageUri.toString());
    startActivity(intent);
  }
}
