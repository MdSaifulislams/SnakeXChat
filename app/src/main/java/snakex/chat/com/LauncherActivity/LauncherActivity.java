package snakex.chat.com.LauncherActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import snakex.chat.com.Home;
import snakex.chat.com.LoginActivity.Login;
import snakex.chat.com.R;

public class LauncherActivity extends AppCompatActivity {




int launcherTime = 1000;

@Override
protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.launcher_activity);




	 new CountDownTimer(launcherTime, 100) {
			@Override
			public void onTick(long l) {

			}

			@Override
			public void onFinish() {
//			if (sure_login) {
				 startActivity(new Intent(LauncherActivity.this, Login.class));
				 finish();
//			}else {
//				startActivity(new Intent(app_logo_show.this,login_page.class));
//				finish();
//			}
			}
	 }.start();
}
}