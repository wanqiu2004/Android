package ch.cloudns.wanqiu.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AlphabetIndexView extends View {

  private static final String[] LETTERS = {
    "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
    "T", "U", "V", "W", "X", "Y", "Z"
  };

  private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
  private int selectedIndex = -1;
  private OnLetterChangedListener listener;

  public AlphabetIndexView(Context context) {
    super(context);
    init();
  }

  public AlphabetIndexView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    paint.setColor(Color.WHITE);
    paint.setTextSize(32f);
    paint.setTextAlign(Paint.Align.CENTER);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    float width = getWidth();
    float height = getHeight();
    float singleHeight = height / LETTERS.length;

    for (int i = 0; i < LETTERS.length; i++) {
      float x = width / 2;
      float y = singleHeight * i + singleHeight / 2 + paint.getTextSize() / 2;
      paint.setColor(i == selectedIndex ? Color.YELLOW : Color.WHITE);
      canvas.drawText(LETTERS[i], x, y, paint);
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float y = event.getY();
    int index = (int) (y / (getHeight() / LETTERS.length));

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_MOVE:
        if (index >= 0 && index < LETTERS.length) {
          selectedIndex = index;
          if (listener != null) listener.onLetterChanged(LETTERS[index]);
          invalidate();
        }
        break;
      case MotionEvent.ACTION_UP:
        selectedIndex = -1;
        invalidate();
        break;
    }
    return true;
  }

  public void setOnLetterChangedListener(OnLetterChangedListener l) {
    this.listener = l;
  }

  public interface OnLetterChangedListener {
    void onLetterChanged(String letter);
  }
}
