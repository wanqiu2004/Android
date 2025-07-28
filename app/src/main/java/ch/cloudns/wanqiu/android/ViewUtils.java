package ch.cloudns.wanqiu.android;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewUtils {
  public static Button newPrimaryButton(
      Context ctx, CharSequence text, View.OnClickListener onClickListener) {
    ContextThemeWrapper themed = new ContextThemeWrapper(ctx, R.style.AppButton_Primary);
    Button btn = new Button(themed, null, 0);
    btn.setText(text);
    btn.setOnClickListener(onClickListener);
    return btn;
  }

  public static LinearLayout.LayoutParams wrapWithMargins(
      Context ctx, int horizontalDp, int verticalDp) {
    int h =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, horizontalDp, ctx.getResources().getDisplayMetrics());
    int v =
        (int)
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, verticalDp, ctx.getResources().getDisplayMetrics());
    LinearLayout.LayoutParams lp =
        new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    lp.setMargins(h, v, h, v);
    return lp;
  }
}
