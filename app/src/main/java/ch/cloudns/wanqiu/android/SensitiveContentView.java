package ch.cloudns.wanqiu.android;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

public class SensitiveContentView extends FrameLayout {

  private ImageView realImage; // 真图
  private View overlay; // 遮罩层（包含占位图+文案+按钮）
  private ImageView placeholderImg; // 占位图
  private TextView tipText; // 提示文案
  private TextView toggleBtn; // 显示/恢复按钮

  private boolean revealed = false;

  private String tip;
  private String showText;
  private String hideText;

  public SensitiveContentView(Context context) {
    this(context, null);
  }

  public SensitiveContentView(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SensitiveContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initAttrs(context, attrs, defStyleAttr);
    init();
  }

  private void initAttrs(Context ctx, AttributeSet attrs, int defStyleAttr) {
    TypedArray a =
        ctx.obtainStyledAttributes(attrs, R.styleable.SensitiveContentView, defStyleAttr, 0);
    tip = a.getString(R.styleable.SensitiveContentView_scv_tipText);
    showText = a.getString(R.styleable.SensitiveContentView_scv_showText);
    hideText = a.getString(R.styleable.SensitiveContentView_scv_hideText);
    a.recycle();

    if (tip == null) tip = "该内容被标记为敏感，是否继续查看？";
    if (showText == null) showText = "显示";
    if (hideText == null) hideText = "隐藏";
  }

  private void init() {
    LayoutInflater.from(getContext()).inflate(R.layout.view_sensitive_content, this, true);
    realImage = findViewById(R.id.scv_real_image);
    overlay = findViewById(R.id.scv_overlay);
    placeholderImg = findViewById(R.id.scv_placeholder);
    tipText = findViewById(R.id.scv_tip);
    toggleBtn = findViewById(R.id.scv_toggle);
    toggleBtn.setBackgroundResource(R.drawable.btn_black_selector);

    tipText.setText(tip);
    toggleBtn.setText(showText);

    toggleBtn.setOnClickListener(v -> {toggle() ;});

    // 初始为未显示真实内容
    applyState(false, false);
  }

  /** 切换显示/隐藏真实内容 */
  public void toggle() {
    setRevealed(!revealed);
  }

  public boolean isRevealed() {
    return revealed;
  }

  /** 外部可主动控制显隐 */
  public void setRevealed(boolean reveal) {
    if (this.revealed == reveal) return;
    applyState(reveal, true);
  }

  private void applyState(boolean reveal, boolean animate) {
    this.revealed = reveal;

    if (reveal) {
      if (animate) {
        overlay
            .animate()
            .alpha(0f)
            .setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator())
            .setDuration(300)
            .withEndAction(() -> overlay.setVisibility(View.GONE))
            .start();

        realImage.setAlpha(0f);
        realImage.setVisibility(View.VISIBLE);
        realImage
            .animate()
            .alpha(1f)
            .setDuration(300)
            .setInterpolator(new android.view.animation.DecelerateInterpolator())
            .start();
      } else {
        overlay.setVisibility(View.GONE);
        realImage.setVisibility(View.VISIBLE);
      }
      toggleBtn.setText(hideText);
    } else {
      if (animate) {
        overlay.setAlpha(0f);
        overlay.setVisibility(View.VISIBLE);
        overlay
            .animate()
            .alpha(1f)
            .setDuration(300)
            .setInterpolator(new android.view.animation.DecelerateInterpolator())
            .start();

        realImage
            .animate()
            .alpha(0f)
            .setDuration(300)
            .setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator())
            .withEndAction(() -> realImage.setVisibility(View.INVISIBLE))
            .start();
      } else {
        overlay.setVisibility(View.VISIBLE);
        realImage.setVisibility(View.INVISIBLE);
      }
      toggleBtn.setText(showText);
    }
  }

  /** 设置提示文案（可运行时替换） */
  public void setTipText(CharSequence text) {
    tip = text != null ? text.toString() : "";
    tipText.setText(tip);
  }

  /** 设置按钮文案（显示/隐藏） */
  public void setButtonTexts(CharSequence show, CharSequence hide) {
    if (show != null) showText = show.toString();
    if (hide != null) hideText = hide.toString();
    toggleBtn.setText(revealed ? hideText : showText);
  }

  /** 给真实内容图像设置 bitmap（可以来自 assets/url/glide 等） */
  public void setRealBitmap(Bitmap bitmap) {
    realImage.setImageBitmap(bitmap);
    realImage.setContentDescription("真实内容图像");
  }

  /** 给占位图设置 bitmap（可选） */
  public void setPlaceholderBitmap(Bitmap bitmap) {
    placeholderImg.setImageBitmap(bitmap);
    placeholderImg.setContentDescription("敏感内容占位图像");
  }

  /** 如果你使用 Glide/Picasso 等，直接暴露内部 ImageView */
  public ImageView getRealImageView() {
    return realImage;
  }

  public ImageView getPlaceholderImageView() {
    return placeholderImg;
  }

  // ---------- 状态保存（横竖屏切换等不会丢失 revealed） ----------
  @Nullable
  @Override
  protected Parcelable onSaveInstanceState() {
    Parcelable superState = super.onSaveInstanceState();
    SavedState ss = new SavedState(superState);
    ss.revealed = this.revealed;
    return ss;
  }

  @Override
  protected void onRestoreInstanceState(Parcelable state) {
    if (!(state instanceof SavedState)) {
      super.onRestoreInstanceState(state);
      return;
    }
    SavedState ss = (SavedState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    applyState(ss.revealed, false);
  }

  static class SavedState extends BaseSavedState {
    public static final Creator<SavedState> CREATOR =
        new Creator<SavedState>() {
          @Override
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          @Override
          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
    boolean revealed;

    SavedState(Parcelable superState) {
      super(superState);
    }

    private SavedState(Parcel in) {
      super(in);
      revealed = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(@Nullable Parcel out, int flags) {
      super.writeToParcel(out, flags);
      if (out != null) out.writeInt(revealed ? 1 : 0);
    }
  }
}
