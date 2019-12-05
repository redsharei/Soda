package app.mikankenshi.soda

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.os.VibrationEffect.DEFAULT_AMPLITUDE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


import android.widget.Toast
import androidx.lifecycle.Observer
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity(), ShakeDetector.Listener {
    val handler = Handler()
    var num = 0
    var time = 0
    var judge = false
    var onetime = true

    private lateinit var sound:Sound


    lateinit var vb0: Vibrator // Vibratorを宣言
    //val vbpt0= longArrayOf(0,150,50,150,50,150,50,350,50,350,50,350,50) // Vivratorのパターンを作成

    val vbpt0 = longArrayOf(0, 100)

    val runnable = object : Runnable {
        // メッセージ受信が有った時かな?
        override fun run() {
            time++                      // 秒カウンタ+1
            timeToText(time)?.let {
                // timeToText()で表示データを作り
               // timeView.text = it            // timeText.textへ代入(表示)
            }
            handler.postDelayed(this, 100)  // 10ｍｓ後に自分にpost
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = intent.getStringExtra("TEXT_KEY") //intent

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)
        vb0 = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator // Vibratorを設定

        OrientationLiveData(this).observe(this, Observer { orientation ->

            if (orientation == null) return@Observer

            //     orientation.azimuth
            //   textView.text= orientation.pitch.toString()
            //   orientation.roll
            // で処理を行う
        })

        Sound.getInstance(this)//sound 初期化


        restert.setOnClickListener {


            handler.post(runnable)
        }
/*
        stop.setOnClickListener {
            handler.removeCallbacks(runnable)
        }
*/

        back.setOnClickListener {
            Sound.getInstance(this).playResume(Sound.OP)
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }

        restert.setOnClickListener {
            num = 0
            progress.setImageLevel(num)
            handler.removeCallbacks(runnable)      // キューキャンセル
            time = 0                          // 秒カウンタークリア
            timeToText()?.let {
                // timeToText()で表示データを作り
                //timeView.text = it                // timeText.textに表示
            }
            onetime = true
        }
/*
        stop.setOnClickListener {
            num = num
            progress.setImageLevel(num)
            when (judge) {
                false -> {
                    stop.text = "START"
                    judge = true
                }
                else -> {
                    stop.text = "STOP"
                    judge = false
                }
            }
            vb0.cancel() // Vibratorを止める
        }
*/
        progress.setImageResource(R.drawable.progress_background)
        progress.setImageLevel(0)
    }


    override fun hearShake() {
        // やりたい処理を書く

        startView.text=("")

        if (num<10000&&vb0.hasVibrator()) {
            vb0run()       //vibrate start
        }


        if (onetime) {


            handler.post(runnable)
            onetime = false
        }

        if (num == 10000) {
            handler.removeCallbacks(runnable)
            Sound.getInstance(this).playSound(Sound.SOUND_END)
        }


        num += 500
        progress.setImageLevel(num)
       // textView.text = num.toString()
        //       Toast.makeText(this@MainActivity, Integer.toString(num), Toast.LENGTH_SHORT).show()
    }


    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null                                    // 時刻が0未満の場合 null
        } else if (time == 0) {
            "00:00:00"                            // ０なら
        } else {
            /*
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            */
            val m = time / 60 / 10
            val s = time / 10 % 60
            val ms = time % 10

            "%1$02d:%2$02d:%3$1d0".format(m, s, ms)  // 表示に整形
        }
    }

    fun vb0run() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Android8.0以降の場合
            // vb0.vibrate(VibrationEffect.createWaveform(vbpt0,0))
            vb0.vibrate(VibrationEffect.createOneShot(300, DEFAULT_AMPLITUDE))
        } else { // Android8.0未満の場合
            // vb0.vibrate(vbpt0,0)
            vb0.vibrate(100)
        }
    }


}

//0.1秒  ok
//stop  ok
//ランキング
//level（ゲージ量）
//バイブon off
//振る速度に合わせる（加速度せんさー実装したい中）
// 多重継承
//インターフェスにしたんだけど、呼び出せない。
//スタート画面
//満杯になったら、振っても反応しないように
//音
//UI
//タイマーをなくそう
//画像変えられるとか。
//bgm