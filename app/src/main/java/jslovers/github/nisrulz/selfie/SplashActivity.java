package jslovers.github.nisrulz.selfie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Make it fullscreen
    // IMPORTANT : Call this before the call to setContentView
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_splash);

    // Use a handler to execute code with a delay
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        // Start the MainActivity using intent
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
      }
    }, 1500);
  }
}
