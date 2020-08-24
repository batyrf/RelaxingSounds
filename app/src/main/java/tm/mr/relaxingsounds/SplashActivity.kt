package tm.mr.relaxingsounds

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import tm.mr.relaxingsounds.data.extension.launchActivity

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            launchActivity<MainActivity>()
        }, 2000)
    }

}