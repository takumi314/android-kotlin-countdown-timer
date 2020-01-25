package com.example.countdowntimer

import android.media.AudioManager
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.countdowntimer.R.drawable.ic_play_arrow_black_24dp
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private var soundResId = 0

    inner class MyCountDownTimer(millisInFuture: Long,
                                 countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false

        // コンストラクタで指定した間隔でコールされる
        // - millisUntilFinished: タイマーの残り時間 [ms] で渡される
        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000L % 60L
            timerText.text = "%1d:%2$02d".format(minute, second)
        }
        // カウントが 0 になるとコールされる
        override fun onFinish() {
            timerText.text = "0:00"
            // サウンドを再生する
            // - soundID: サウンドID
            // - leftVolume: 左ボリューム (0.0 - 1.0)
            // - rightVolume: 右ボリューム (0.0 - 1.0)
            // - priority: ストリームの優先順位 (最低0)
            // - loop: ループモード (0: 繰り返さない, 1: 繰り返す)
            // - rate: 再生速度 (1.0: 通常再生, 0.5〜2.0で指定可能)
            soundPool.play(soundResId, 1.0f, 100f, 0, 0, 1.0f)
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

    override fun onResume() {
        super.onResume()
        // SoundPoolクラスのインスタンスを作成する
        // - mexStream: 同時に再生することができる音源数
        // - streamType: アラーム音のためのストリーム
        // - srcQuality: 品質を指定する
        soundPool = SoundPool(2, AudioManager.STREAM_ALARM, 0)
        // リソースからサウンドファイルを読み込む
        // - context: アクティビティを指定する
        // - redId: サウンドファイルのリソースID
        // - priority: 音の優先順位(現在は効果がないため, 互換性のために 1 を指定している)
        soundResId = soundPool.load(this, R.raw.bellsound, 1)

    }


    override fun onPause() {
        super.onPause()
        soundPool.release() // メモリ開放
    }

}
