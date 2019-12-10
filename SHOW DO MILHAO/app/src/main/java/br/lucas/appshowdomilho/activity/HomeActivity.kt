package br.lucas.appshowdomilho.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.lucas.appshowdomilho.database.Firebase
import br.lucas.appshowdomilho.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val usernameReference =
            Firebase.database.reference.child("users").child(Firebase.auth.currentUser!!.uid)
                .child("username")

        val usernameListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                helloUserLabel.text = "Olá, " + dataSnapshot.getValue().toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        usernameReference.addListenerForSingleValueEvent(usernameListener)


        logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        rankingBtn.setOnClickListener {
            startActivity(Intent(this, RankingActivity::class.java))
        }

        quizBtn.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
        }
    }
}
