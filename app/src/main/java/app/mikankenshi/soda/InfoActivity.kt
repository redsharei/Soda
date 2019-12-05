package app.mikankenshi.soda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

    }

    fun back(view: View){
        Sound.getInstance(this).playStop()
        Sound.getInstance(this).playSound(Sound.CLICK)
        //Intentのインスタンスを作成
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }
}
