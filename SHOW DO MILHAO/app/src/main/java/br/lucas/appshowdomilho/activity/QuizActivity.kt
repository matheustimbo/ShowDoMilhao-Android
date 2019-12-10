package br.lucas.appshowdomilho.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.lucas.appshowdomilho.database.Firebase
import br.lucas.appshowdomilho.model.Pergunta
import br.lucas.appshowdomilho.R
import br.lucas.appshowdomilho.model.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private val perguntas = mutableListOf<Pergunta>()
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        requestPerguntas()

        respostaQuiz1.setOnClickListener {
            handleResposta(0)
        }
        respostaQuiz2.setOnClickListener {
            handleResposta(1)
        }
        respostaQuiz3.setOnClickListener {
            handleResposta(2)
        }
        respostaQuiz4.setOnClickListener {
            handleResposta(3)
        }
    }

    private fun requestPerguntas() {
        Firebase.database.getReference("PERGUNTAS").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val perguntasChild = p0.children

                perguntasChild.forEach {
                    it.getValue(Pergunta::class.java)?.let { pergunta ->
                        perguntas.add(pergunta)
                    }
                }

                perguntas.shuffle()
                preencherPergunta()
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    private fun handleResposta(indexResposta: Int) {
        if (perguntas[index].Resposta == indexResposta && index < 15) {
            index++
            preencherPergunta()
        } else {

            Firebase.database.getReference("users").child(Firebase.auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    p0.getValue(User::class.java)?.let {

                        val max = if (it.bestShot < index) index else it.bestShot

                        with(Firebase.database.getReference("users").child(Firebase.auth.currentUser!!.uid)) {

                            child("lastShot").setValue(index)
                            child("bestShot").setValue(max)
                        }
                    }
                    startActivity(Intent(this@QuizActivity, RankingActivity::class.java))
                    finish()
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
        }
    }

    private fun preencherPergunta() {
        perguntaQuiz.text = perguntas[index].pergunta
        respostaQuiz1.text = perguntas[index].Alternativas[0]
        respostaQuiz2.text = perguntas[index].Alternativas[1]
        respostaQuiz3.text = perguntas[index].Alternativas[2]
        respostaQuiz4.text = perguntas[index].Alternativas[3]
    }
}
