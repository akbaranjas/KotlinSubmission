package com.akbaranjas.footballmatchschedule.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.akbaranjas.footballmatchschedule.R
import com.akbaranjas.footballmatchschedule.models.Player
import com.akbaranjas.footballmatchschedule.util.getSubstringPlayerName
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.TEXT

class PlayerListAdapter(private val players: List<Player>, private val listener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayersHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersHolder {
        return PlayersHolder(PlayersUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayersHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount(): Int = players.size

}

class PlayersUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            relativeLayout {
                lparams(width = matchParent, height = wrapContent){
                    margin = dip(5)
                }

                padding = dip(16)
                imageView {
                    id = R.id.playerphoto
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                    centerVertically()
                    alignParentStart()
                    padding = dip(5)
                }

                textView {
                    id = R.id.player_name
                    textSize = 16f
                    setTypeface(typeface, Typeface.BOLD)
                }.lparams{
                    margin = dip(5)
                    centerVertically()
                    rightOf(R.id.playerphoto)
                }

                textView {
                    id = R.id.player_position
                    textSize = 14f
                    //textColor = android.R.color.darker_gray
                }.lparams{
                    margin = dip(5)
                    centerVertically()
                    alignParentEnd()
                    //rightOf(R.id.player_name)
                }

            }
        }
    }

}

class PlayersHolder(view: View) : RecyclerView.ViewHolder(view){

    private val playerCutout: ImageView = view.find(R.id.playerphoto)
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerPos: TextView = view.find(R.id.player_position)

    fun bindItem(player: Player, listener: (Player) -> Unit) {
        Picasso.get().load(player.playerCutout).fit().placeholder(R.drawable.progress_image).into(playerCutout)
        playerName.text = getSubstringPlayerName(player.playerName!!)
        playerPos.text = player.playerPos
        itemView.setOnClickListener { listener(player) }
    }
}