package ch.cloudns.wanqiu.android;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder> {

  private final List<Uri> imageUris;
  private final OnImageClickListener listener;
  public ImageGridAdapter(List<Uri> imageUris, OnImageClickListener listener) {
    this.imageUris = imageUris;
    this.listener = listener;
  }

  @NonNull
  @Override
  public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_image_thumbnail, parent, false);
    return new ImageViewHolder(view, listener);
  }

  @Override
  public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
    Uri imageUri = imageUris.get(position);
    Glide.with(holder.imageViewThumbnail.getContext())
            .load(imageUri)
            .centerCrop()
            .into(holder.imageViewThumbnail);

    // 这里要绑定 Uri 到 itemView

    holder.imageViewThumbnail.setClickable(true); // 确保可点击

    holder.imageViewThumbnail.setOnTouchListener((v, event) -> {
      v.animate().cancel(); // ✅ 取消旧动画

      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          v.animate()
                  .scaleX(0.95f)
                  .scaleY(0.95f)
                  .setDuration(30)
                  .start();
          break;

        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL: // ✅ 处理取消
          v.animate()
                  .scaleX(1.0f)
                  .scaleY(1.0f)
                  .setDuration(100)
                  .start();
          v.performClick(); // 触发 OnClickListener
          break;
      }
      return false;
    });
    holder.bind(imageUri);
  }


  @Override
  public int getItemCount() {
    return imageUris.size();
  }

  public interface OnImageClickListener {
    void onImageClick(Uri imageUri);
  }

  static class ImageViewHolder extends RecyclerView.ViewHolder {
    ImageView imageViewThumbnail;

    public ImageViewHolder(@NonNull View itemView, OnImageClickListener listener) {
      super(itemView);
      imageViewThumbnail = itemView.findViewById(R.id.image_view_thumbnail);

      itemView.setOnClickListener(
          v -> {
            if (listener != null) {
              int position = getAdapterPosition();
              if (position != RecyclerView.NO_POSITION) {
                listener.onImageClick((Uri) v.getTag());
              }
            }
          });
    }

    public void bind(Uri uri) {
      itemView.setTag(uri);
    }
  }
}
