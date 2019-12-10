package br.lucas.appshowdomilho.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.lucas.appshowdomilho.R
import br.lucas.appshowdomilho.model.User
import kotlinx.android.synthetic.main.holder_ranking.view.*

class RankingAdapter : RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.holder_ranking, parent, false)

        return RankingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        holder.bind(users[position], position + 1)
    }


    class RankingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: User, place: Int) {
            with(itemView) {
                placeUser.text = "#${place}"
                username.text = user.username
                bestShot.text = user.bestShot.toString()
            }
        }
    }

    fun updateUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users.sortedByDescending { it.bestShot }.filter { it.bestShot != 0 })
        notifyDataSetChanged()
    }
}