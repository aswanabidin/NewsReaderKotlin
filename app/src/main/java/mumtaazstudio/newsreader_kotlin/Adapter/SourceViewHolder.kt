package mumtaazstudio.newsreader_kotlin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.card_source_layout.view.*
import mumtaazstudio.newsreader_kotlin.Interface.ItemClickListener

class SourceViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

    private lateinit var itemClickListener:ItemClickListener
    var source_title = itemView.source_title

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition)
    }




}