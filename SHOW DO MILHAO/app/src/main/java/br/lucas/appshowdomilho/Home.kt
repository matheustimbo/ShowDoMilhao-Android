package br.lucas.appshowdomilho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*

data class Questao(
    var answerIndex: Int?,
    var question: String?
)

class Home : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mAuth = FirebaseAuth.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference
        val usernameReference = mDatabaseReference!!.child("users").child(mAuth!!.currentUser!!.uid).child("username")
        val usernameListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                helloUserLabel.text = "Olá, " + dataSnapshot.getValue().toString()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        usernameReference.addListenerForSingleValueEvent(usernameListener)


        logoutBtn.setOnClickListener{
            mAuth!!.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }

        rankingBtn.setOnClickListener{
            startActivity(Intent(this, RankingActivity::class.java))
        }

        quizBtn.setOnClickListener{
            startActivity(Intent(this, QuizActivity::class.java))
        }

        teste.setOnClickListener{
            val questaoIndex = "2"
            val respostaIndex = 3
            val questao = "Pergunta"
            val opcao1 = "aaa"
            val opcao2 = "bbb"
            val opcao3 = "ccc"
            val opcao4 = "ddd"

            mDatabaseReference!!.child("questions").child(questaoIndex).setValue(Questao(respostaIndex, questao))
            mDatabaseReference!!.child("questions").child(questaoIndex).child("options").child("0").setValue(opcao1)
            mDatabaseReference!!.child("questions").child(questaoIndex).child("options").child("1").setValue(opcao2)
            mDatabaseReference!!.child("questions").child(questaoIndex).child("options").child("2").setValue(opcao3)
            mDatabaseReference!!.child("questions").child(questaoIndex).child("options").child("3").setValue(opcao4)
        }

    }
}
