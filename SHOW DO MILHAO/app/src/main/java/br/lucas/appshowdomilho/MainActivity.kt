package br.lucas.appshowdomilho

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        var mediaPlayer: MediaPlayer?=null

        //Hide Status Bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sound audio music

        mediaPlayer = MediaPlayer.create(this,R.raw.musica_show_milhao)
        mediaPlayer?.setOnPreparedListener{
            mediaPlayer?.start()

        }
    }
}
