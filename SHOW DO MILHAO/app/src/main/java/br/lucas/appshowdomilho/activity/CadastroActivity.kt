package br.lucas.appshowdomilho.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.lucas.appshowdomilho.database.Firebase
import br.lucas.appshowdomilho.R
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnCadastrar.setOnClickListener {
            val nome = textInputNome.text.toString()
            val email = textInputEmail.text.toString()
            val senha = textInputSenha.text.toString()

            if (nome == "") {
                textInputNome.error = "Insira seu nome"
            } else if (email == "") {
                textInputEmail.error = "Insira seu email"
            } else if (senha == "") {
                textInputSenha.error = "Insira sua senha"
            } else {
                Firebase.auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) { task ->
                        Log.w("WARN", "a")
                        if (task.isSuccessful) {
                            Log.w("WARN", "b")
                            val userId = Firebase.auth.currentUser!!.uid

                            //Verify Email
                            verifyEmail()

                            //update user profile information
                            val currentUserDb = Firebase.database.reference.child("users").child(userId)

                            currentUserDb.child("username").setValue(nome)
                            currentUserDb.child("bestShot").setValue(0)
                            currentUserDb.child("lastShot").setValue(0)

                            Firebase.auth.signOut()
                        } else {
                            Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun verifyEmail() {
        val mUser =  Firebase.auth.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Email de verificação enviado para " + mUser.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Falha no envio do email de verificação.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}
