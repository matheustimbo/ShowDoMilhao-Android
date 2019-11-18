package br.lucas.appshowdomilho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import android.content.DialogInterface
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Login : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loadingIndicator.visibility = View.INVISIBLE
        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        loginButton.setOnClickListener{

            loadingIndicator.visibility = View.VISIBLE

            val email = emailTextInput.text.toString()
            val senha = senhaTextInput.text.toString()




            mAuth!!.signInWithEmailAndPassword(email, senha).addOnCompleteListener{task->
                if(task.isSuccessful){
                    loadingIndicator.visibility = View.INVISIBLE
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
                    loadingIndicator.visibility = View.INVISIBLE
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
                    Toast.makeText(this,
                        "Falha no reenvio da verificação de email",
                        Toast.LENGTH_SHORT).show()
                    mAuth!!.signOut()
                }
            }
    }
}
