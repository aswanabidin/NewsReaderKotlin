package mumtaazstudio.newsreader_kotlin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.card_news_layout.view.*
import mumtaazstudio.newsreader_kotlin.Interface.ItemClickListener

class NewsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var itemClickListener: ItemClickListener

    var news_title = itemView.tv_title_news
    var news_time_publish = itemView.tv_time_publish
    var news_image = itemView.news_image

    init {
        itemView.setOnClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition)
    }
}