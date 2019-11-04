package app.mikankenshi.soda

import android.content.Context
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.seismic.ShakeDetector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShakeDetector.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sd = ShakeDetector(this)
        sd.start(sensorManager)

        bar.setMax(100);
        bar.setProgress(80);

    }



    override fun hearShake() {
        // やりたい処理を書く
        Toast.makeText(this@MainActivity, "Everybody Shaked!", Toast.LENGTH_SHORT).show()
    }
}
