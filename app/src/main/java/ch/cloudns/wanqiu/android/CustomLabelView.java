package ch.cloudns.wanqiu.android;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomLabelView extends View {

    private Paint backgroundPaint;
    private Paint borderPaint;
    private Paint textPaint;
    private RectF rect;
    private String text = "📢 飞行广播";
    private float cornerRadius = 30f;
    private LinearGradient gradient;

    private float scale = 1.0f;

    public CustomLabelView(Context context) {
        super(context);
        init();
    }

    public CustomLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayerType(LAYER_TYPE_HARDWARE, null); // 为动画优化

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(Color.parseColor("#2196F3"));
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(44);
        textPaint.setTextAlign(Paint.Align.CENTER);

        rect = new RectF();

        // 开启动画（点击缩放）
        setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animate().scaleX(0.95f).scaleY(0.95f).alpha(0.8f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(100).start();
                    performClick(); // 触发点击事件
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean performClick() {
        return super.performClick(); // 可选：增加无障碍支持
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 创建渐变背景
        if (gradient == null) {
            gradient = new LinearGradient(
                    0, 0, getWidth(), getHeight(),
                    Color.parseColor("#E3F2FD"), // 浅蓝
                    Color.parseColor("#2196F3"), // 深蓝
                    Shader.TileMode.CLAMP
            );
            backgroundPaint.setShader(gradient);
        }

        rect.set(10, 10, getWidth() - 10, getHeight() - 10);

        // 背景圆角
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, backgroundPaint);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, borderPaint);

        // 居中绘制文字
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float x = getWidth() / 2f;
        float y = getHeight() / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText(text, x, y, textPaint);
    }

    public void setText(String text) {
        this.text = text;
        invalidate(); // 重绘
    }
}

