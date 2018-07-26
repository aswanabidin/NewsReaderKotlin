package mumtaazstudio.newsreader_kotlin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import mumtaazstudio.newsreader_kotlin.Interface.ItemClickListener
import mumtaazstudio.newsreader_kotlin.Model.Website
import mumtaazstudio.newsreader_kotlin.R

class SourceAdapter(private val context: Context, private val website: Website) :
        RecyclerView.Adapter<SourceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val inflater =  LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.card_source_layout, parent, false)
        return SourceViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return website.sources!!.size
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder!!.source_title.text = website.sources!![position].name
        holder.setItemClickListener(object : ItemClickListener
        {
            override fun onClick(view: View, position: Int) {
                Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
            }

        })


    }

}