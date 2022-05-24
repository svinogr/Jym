package info.upump.jym.adapters.holders

import android.view.View
import android.widget.TextView
import info.upump.jym.R
import info.upump.jym.entity.Sets

class SetsViewHolderOnlyTitle(val itemView: View?) : AbstractSetViewHolder(itemView) {
    private val title = itemView.findViewById<TextView>(R.id.set_card_layout_for_now_title)
    override fun bind(sets: Sets?) {
        title.text = sets?.title
    }

    override fun startActivity() {
    }

}