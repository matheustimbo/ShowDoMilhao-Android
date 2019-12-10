package br.lucas.appshowdomilho.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.lucas.appshowdomilho.R
import br.lucas.appshowdomilho.adapter.RankingAdapter
import br.lucas.appshowdomilho.database.Firebase
import br.lucas.appshowdomilho.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_ranking.*

class RankingActivity : AppCompatActivity() {

    private val adapter = RankingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)

        rankingList.adapter = adapter

        setUsers()
    }


    private fun setUsers() {
        Firebase.database.getReference("users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val updatedUsers = mutableListOf<User>()

                p0.children.forEach {
                    it.getValue(User::class.java)?.let { user ->

                        updatedUsers.add(user)
                    }
                }

                adapter.updateUsers(updatedUsers)
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }
}
