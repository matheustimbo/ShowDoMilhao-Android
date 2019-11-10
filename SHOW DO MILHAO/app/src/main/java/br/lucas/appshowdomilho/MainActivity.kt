package br.lucas.appshowdomilho

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

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

        val letsStartButton: Button = findViewById(R.id.angry_btn)
        mAuth = FirebaseAuth.getInstance()

        letsStartButton.setOnClickListener{
            if(mAuth?.currentUser != null){
                startActivity(Intent(this, Home::class.java))
            }else{
                startActivity(Intent(this, Cadastro::class.java))
            }

        }
    }
}
