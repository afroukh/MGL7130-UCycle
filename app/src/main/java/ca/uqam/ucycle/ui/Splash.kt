package ca.uqam.ucycle.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import ca.uqam.ucycle.R


class Splash : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        val secondsDelayed = 1
        Handler().postDelayed({
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, (secondsDelayed * 2000).toLong())
    }
}