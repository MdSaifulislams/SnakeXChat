package snakex.chat.com.LauncherActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import snakex.chat.com.R
import android.os.CountDownTimer
import android.content.Intent
import snakex.chat.com.LoginActivity.Login

class LauncherActivity : AppCompatActivity() {
   var launcherTime = 1000
   override fun onCreate(savedInstanceState : Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.launcher_activity)



      object : CountDownTimer(launcherTime.toLong(), 100) {
         override fun onTick(l : Long) {}
         override fun onFinish() {
            startActivity(Intent(this@LauncherActivity, Login::class.java))
            finish()
         }
      }.start()
   }
}