package br.lucas.appshowdomilho.activity

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import br.lucas.appshowdomilho.database.Firebase
import br.lucas.appshowdomilho.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        //Hide Status Bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //sound audio music

        val mediaPlayer = MediaPlayer.create(this, R.raw.musica_show_milhao)
        mediaPlayer?.setOnPreparedListener {
            mediaPlayer.start()
        }

        val letsStartButton: Button = findViewById(R.id.angry_btn)

        letsStartButton.setOnClickListener {
            if (Firebase.auth?.currentUser != null) {
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                startActivity(Intent(this, CadastroActivity::class.java))
            }
        }
    }
}
