package com.example.countdowntimer

import android.icu.util.DateInterval
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.countdowntimer.R.drawable.ic_play_arrow_black_24dp
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    inner class MyCountDownTimer(millisInFuture: Long,
                                 countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            timerText.text = "%ld:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            timerText.text = "0:00"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText.text = "3:00"
        val timer = MyCountDownTimer(3 * 60 * 1000, 100)
        playStop.setOnClickListener {
            timer.isRunning = when (timer.isRunning) {
                true -> {
                    timer.cancel()
                    playStop.setImageResource(ic_play_arrow_black_24dp)
                    false
                }
                false -> {
                    timer.start()
                    playStop.setImageResource(R.drawable.ic_stop_black_24dp)
                    true
                }
            }
        }
    }
}
