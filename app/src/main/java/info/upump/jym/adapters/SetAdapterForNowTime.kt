package info.upump.jym.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.upump.jym.R
import info.upump.jym.activity.constant.Constants
import info.upump.jym.adapters.holders.AbstractSetViewHolder
import info.upump.jym.adapters.holders.SetsViewHolder
import info.upump.jym.adapters.holders.SetsViewHolderDefault
import info.upump.jym.adapters.holders.SetsViewHolderOnlyTitle
import info.upump.jym.entity.Exercise
import info.upump.jym.entity.Sets
import info.upump.jym.fragments.cycle.CRUD

class SetAdapterForNowTime(val list: List<Sets>, val type: Int, val crud: CRUD<Sets>?) :
    RecyclerView.Adapter<AbstractSetViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (list[position].id == -1L) {
            Constants.NOW_TIME_SET
        } else {
            type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractSetViewHolder {
        var inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.sets_card_layout_v2, parent, false)
        var setsViewHolder: AbstractSetViewHolder? = null
        Log.d("TAG", "${type}")
        when (viewType) {
            Constants.DEFAULT_TYPE -> setsViewHolder = SetsViewHolderDefault(inflate)
            Constants.USER_TYPE -> setsViewHolder = SetsViewHolder(inflate, crud)
            Constants.NOW_TIME_SET -> {
                inflate = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sets_card_layout_for_now_time, parent, false)
                setsViewHolder = SetsViewHolderOnlyTitle(inflate)
            }
        }

        return setsViewHolder!!
    }

    override fun onBindViewHolder(holder: AbstractSetViewHolder, position: Int) {
        if (list[position].id == -1L) {
            val holderTitle = holder as SetsViewHolderOnlyTitle
            holderTitle.bind(list[position])
            return
        }

        holder.bind(list[position])

    }

    override fun getItemCount(): Int {
        return list.size
    }
}