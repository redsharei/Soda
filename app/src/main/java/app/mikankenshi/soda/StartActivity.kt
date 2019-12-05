package app.mikankenshi.soda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sound.getInstance(this).playSoundLoop(Sound.OP)
        setContentView(R.layout.activity_start)


    }



    fun start(view: View){
        Sound.getInstance(this).playStop()
        Sound.getInstance(this).playSound(Sound.CLICK)
        //Intentのインスタンスを作成
        val intent = Intent(this, MainActivity::class.java)
        //textは受け渡す変数
        val text = "Hello!"
        //intent変数をつなげる(第一引数はキー，第二引数は渡したい変数)
        intent.putExtra("TEXT_KEY",text)
        //画面遷移を開始
        startActivity(intent)

    }

    fun setting(view: View){
        Sound.getInstance(this).playStop()
        Sound.getInstance(this).playSound(Sound.CLICK)
        //Intentのインスタンスを作成
        val intent = Intent(this, SettingActivity::class.java)
        startActivity(intent)

    }

    fun info(view: View){
        Sound.getInstance(this).playStop()
        Sound.getInstance(this).playSound(Sound.PAPER)
        //Intentのインスタンスを作成
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }

    override fun onStop() {
        super.onStop()
        Sound.getInstance(this).playStop()

        // TODO: ユーザーデータなど消えては困るものはonPauseで処理を行う
    }


    override fun onStart() {
        super.onStart()
        Sound.getInstance(this).playResume(Sound.OP)
    }

    /*  override fun onDestroy() {
          super.onDestroy()
          Sound.getInstance(this).playStop()

          // TODO: アプリ終了処理
      }
  */

}
