package app.mikankenshi.soda

import android.annotation.TargetApi
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build

class Sound constructor(context: Context) {


    private var soundPool: SoundPool? = null


    companion object {

        var SOUND_END = 0
        var OP = 0
        var CLICK = 0
        var PAPER = 0

        var INSTANCE:Sound? = null
        fun getInstance(context: Context) =
            INSTANCE ?: Sound(context).also {
                INSTANCE = it
            }

    }

    init {
        createSoundPool()
        loadSoundIDs(context)
    }

    private fun createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool()
        } else {
            createOldSoundPool()
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNewSoundPool() {
        val attributes = AudioAttributes.Builder().apply {
            setUsage(AudioAttributes.USAGE_GAME)
            setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)

        }.build()

        soundPool = SoundPool.Builder().apply {
            setMaxStreams(2)
            setAudioAttributes(attributes)
        }.build()
    }


    private fun createOldSoundPool() {
        soundPool = SoundPool(2, AudioManager.STREAM_MUSIC, 0)
    }

    private fun loadSoundIDs(context:Context) {
        soundPool?.let {
            println("サウンドファイルロード")
            SOUND_END = it.load(context, R.raw.kan, 1)
            OP = it.load(context, R.raw.op, 1)
            CLICK = it.load(context, R.raw.click, 1)
            PAPER = it.load(context, R.raw.paper, 1)


            //  SAD_TROMBONE = it.load(context, R.raw.sad_trombone, 1)
        }
    }


    fun playSound(soundID:Int) {
        soundPool?.let{
            it.play(soundID, 1.0f, 1.0f, 1, 0, 1.0f)
            println("サウンド再生")
        }
    }

    fun unload(soundID:Int) {
        soundPool?.let{
            it.unload(soundID)
        }
    }


    fun playSoundLoop(soundID:Int) {
        soundPool?.let{
            //再生テスト
            var streamID = 0
            do {
                //少し待ち時間を入れる
                try {
                    Thread.sleep(10)
                } catch (e: InterruptedException) {
                }

                //ボリュームをゼロにして再生して戻り値をチェック
                streamID = it.play(soundID, 0.0f, 0.0f, 1, 0, 1.0f)
            } while (streamID == 0)

            it.play(soundID, 1.0f, 1.0f, 1, -1, 1.0f)
            println("サウンドループ")
        }
    }


    fun playResume(soundID:Int) {
        soundPool?.let{
            it.resume(soundID)
        }
    }

    fun playStop() {
        soundPool?.let{
            it.autoPause()
        }
    }
    /*
    fun playStop(soundID:Int) {
        soundPool?.let{
            it.pause(soundID)
        }
    }*/

    fun close() { // シングルトンの場合呼びようがない？
        soundPool?.release()
        soundPool = null
    }
}