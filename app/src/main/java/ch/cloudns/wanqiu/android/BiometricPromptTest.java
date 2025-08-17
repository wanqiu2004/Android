package ch.cloudns.wanqiu.android;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;

public class BiometricPromptTest extends AppCompatActivity {

  private BiometricPrompt biometricPrompt;
  private BiometricPrompt.PromptInfo promptInfo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_biometric_prompt_test);
    setupBiometricAuthentication();

    TextView tvAuth = findViewById(R.id.tv_auth);
    TextView tvHelp = findViewById(R.id.tv_help);
    TextView tvExit = findViewById(R.id.tv_exit);

    tvAuth.setOnClickListener(v -> startAuthentication());
    tvHelp.setOnClickListener(
        v -> Toast.makeText(this, "请通过指纹、人脸或设备密码验证身份。", Toast.LENGTH_LONG).show());
    tvExit.setOnClickListener(v -> finish());
  }

  private void setupBiometricAuthentication() {
    Executor executor = ContextCompat.getMainExecutor(this);

    biometricPrompt =
        new BiometricPrompt(
            this,
            executor,
            new BiometricPrompt.AuthenticationCallback() {
              @Override
              public void onAuthenticationSucceeded(
                  @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "✅ 验证成功", Toast.LENGTH_SHORT).show();
                // 可在此执行敏感操作
              }

              @Override
              public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "⚠️ 验证错误: " + errString, Toast.LENGTH_SHORT)
                    .show();
              }

              @Override
              public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "❌ 验证失败", Toast.LENGTH_SHORT).show();
              }
            });

    promptInfo =
        new BiometricPrompt.PromptInfo.Builder()
            .setTitle("身份验证")
            .setSubtitle("请通过指纹、人脸或设备密码验证")
            .setAllowedAuthenticators(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                    | BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build();
  }

  private void startAuthentication() {
    BiometricManager biometricManager = BiometricManager.from(this);
    int canAuth =
        biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
                | BiometricManager.Authenticators.DEVICE_CREDENTIAL);

    switch (canAuth) {
      case BiometricManager.BIOMETRIC_SUCCESS:
        biometricPrompt.authenticate(promptInfo);
        break;
      case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
        Toast.makeText(this, "设备不支持生物识别", Toast.LENGTH_SHORT).show();
        break;
      case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
        Toast.makeText(this, "生物识别暂不可用", Toast.LENGTH_SHORT).show();
        break;
      case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
        Toast.makeText(this, "未录入指纹或人脸，请先在系统中设置", Toast.LENGTH_LONG).show();
        break;
      default:
        Toast.makeText(this, "无法启动身份验证", Toast.LENGTH_SHORT).show();
    }
  }
}
