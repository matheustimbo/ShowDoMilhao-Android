package br.lucas.appshowdomilho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.w3c.dom.Text
import java.util.logging.Logger

class Cadastro : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = FirebaseDatabase.getInstance().reference

        btnCadastrar.setOnClickListener{
            val nome = textInputNome.text.toString()
            val email = textInputEmail.text.toString()
            val senha = textInputSenha.text.toString()

            if(nome == ""){
                textInputNome.error = "Insira seu nome"
            } else
            if(email == ""){
                textInputEmail.error = "Insira seu email"
            } else
            if(senha == ""){
                textInputSenha.error = "Insira sua senha"
            }else{
                mAuth!!.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this){task->
                        Log.w("WARN","a")
                        if(task.isSuccessful){
                            Log.w("WARN","b")
                            val userId = mAuth!!.currentUser!!.uid

                            //Verify Email
                            verifyEmail();

                            //update user profile information
                            val currentUserDb = mDatabaseReference!!.child("users").child(userId)
                            currentUserDb.child("username").setValue(nome)

                            mAuth!!.signOut()


                        }else{

                            Toast.makeText(this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                        }

                    }


            }


        }

        loginButton.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,
                        "Email de verificação enviado para " + mUser.getEmail(),
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,
                        "Falha no envio do email de verificação.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
