package br.lucas.appshowdomilho.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.lucas.appshowdomilho.database.Firebase
import br.lucas.appshowdomilho.R
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.loginButton


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadingIndicator.visibility = View.INVISIBLE

        loginButton.setOnClickListener {

            loadingIndicator.visibility = View.VISIBLE

            val email = emailTextInput.text.toString()
            val senha = senhaTextInput.text.toString()

            if (email == "") {
                emailTextInput.error = "Insira seu email"
            } else if (senha == "") {
                senhaTextInput.error = "Insira sua senha"
            } else {
                Firebase.auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        loadingIndicator.visibility = View.INVISIBLE
                        val user = Firebase.auth.currentUser
                        if (user!!.isEmailVerified) {
                            Toast.makeText(this, "Sucesso na tentativa de login", Toast.LENGTH_SHORT)
                                .show()

                            startActivity(Intent(this, HomeActivity::class.java))
                        } else {
                            verifyEmail()
                        }

                    } else {
                        loadingIndicator.visibility = View.INVISIBLE
                        Toast.makeText(
                            this,
                            "Erro na tentativa de login",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        cadastroButton.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }

    private fun verifyEmail() {
        val mUser = Firebase.auth.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Verifique seu email, email reenviado para " + mUser.email,
                        Toast.LENGTH_SHORT
                    ).show()
                    Firebase.auth.signOut()

                    AlertDialog.Builder(this)
                        .setTitle("Verificar email")
                        .setMessage("Você ainda não verificou seu email. Um novo email de verificação foi enviado. Caso não encontre, confira a caixa de spam")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(
                            android.R.string.yes
                        ) { dialog, which ->
                            // Continue with delete operation
                        }
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        "Falha no reenvio da verificação de email",
                        Toast.LENGTH_SHORT
                    ).show()
                    Firebase.auth.signOut()
                }
            }
    }
}
