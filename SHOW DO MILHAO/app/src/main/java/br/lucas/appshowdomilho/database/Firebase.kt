package br.lucas.appshowdomilho.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object Firebase {
    val auth by lazy { FirebaseAuth.getInstance() }
    val database by lazy { FirebaseDatabase.getInstance() }
}