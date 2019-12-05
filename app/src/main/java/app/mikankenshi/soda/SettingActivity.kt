package app.mikankenshi.soda

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ToggleButton

import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_setting.*




class SettingActivity : AppCompatActivity() {

    var isClickedS: Boolean = true
    var isClickedV: Boolean = true




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

    }

    fun sound(view: View){
        if(isClickedS){
            Sound.getInstance(this).playResume(Sound.OP)

            isClickedS=false
        }else{
            Sound.getInstance(this).playStop()


            isClickedS=true
        }

    }

    fun back(view: View){
        Sound.getInstance(this).playStop()
        Sound.getInstance(this).playSound(Sound.CLICK)
        //Intentのインスタンスを作成
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }


}
