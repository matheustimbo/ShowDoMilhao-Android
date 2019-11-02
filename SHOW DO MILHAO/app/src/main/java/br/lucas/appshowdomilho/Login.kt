package br.lucas.appshowdomilho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        loginButton.setOnClickListener{
            val email = emailTextInput.text.toString()
            val senha = senhaTextInput.text.toString()




            mAuth!!.signInWithEmailAndPassword(email, senha).addOnCompleteListener{task->
                if(task.isSuccessful){
                    val user = mAuth!!.getCurrentUser()
                    if(user!!.isEmailVerified){
                        Toast.makeText(this,
                            "Sucesso na tentativa de login",
                            Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this, Home::class.java))
                    }else{
                       verifyEmail()
                    }

                }else{
                    Toast.makeText(this,
                        "Erro na tentativa de login",
                        Toast.LENGTH_SHORT).show()
                }
            }



        }

        cadastroButton.setOnClickListener{
            startActivity(Intent(this, Cadastro::class.java))
        }
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,
                        "Verifique seu email, email reenviado para " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                    mAuth!!.signOut()
                } else {
                    Toast.makeText(this,
                        "Falha no reenvio da verificação de email",
                        Toast.LENGTH_SHORT).show()
                    mAuth!!.signOut()
                }
            }
    }
}
