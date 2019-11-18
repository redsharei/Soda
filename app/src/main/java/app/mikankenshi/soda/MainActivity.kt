package app.mikankenshi.soda

import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShakeDetector.Listener {
   // val handler = Handler()
    var num = 0
    //var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)

     //   bar.setMax(100)
     //   bar.setProgress(80)


        Restert.setOnClickListener {
            num = 0
            progress.setImageLevel(num)
        }

        progress.setImageResource(R.drawable.progress_background)
        progress.setImageLevel(0)
       // progress.getDrawable().setLevel(500)

    }



    override fun hearShake() {
        // やりたい処理を書く
        /*
        val runnable = object : Runnable {
            // メッセージ受信が有った時かな?
            override fun run() {
                time++                      // 秒カウンタ+1
                timeToText(time)?.let {        // timeToText()で表示データを作り
                    timeView.text = it            // timeText.textへ代入(表示)
                }
                handler.postDelayed(this, 1000)  // 1000ｍｓ後に自分にpost
            }
        }
        handler.post(runnable)
        if(num==10000)  handler.removeCallbacks(runnable)
        */


        num += 1000
        progress.setImageLevel(num)
        Toast.makeText(this@MainActivity, Integer.toString(num), Toast.LENGTH_SHORT).show()

    }

    /*
    private fun timeToText(time: Int = 0): String? {
        return if (time < 0) {
            null                                    // 時刻が0未満の場合 null
        } else if (time == 0) {
            "00:00:00"                            // ０なら
        } else {
            val h = time / 3600
            val m = time % 3600 / 60
            val s = time % 60
            "%1$02d:%2$02d:%3$02d".format(h, m, s)  // 表示に整形
        }
    }
    */

}
