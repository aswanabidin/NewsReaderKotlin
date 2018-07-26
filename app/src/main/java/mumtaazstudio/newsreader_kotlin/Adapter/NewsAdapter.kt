package mumtaazstudio.newsreader_kotlin.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.internal.bind.util.ISO8601Utils
import com.squareup.picasso.Picasso
import mumtaazstudio.newsreader_kotlin.Common.ISO8601DateParser
import mumtaazstudio.newsreader_kotlin.Interface.ItemClickListener
import mumtaazstudio.newsreader_kotlin.Model.Article
import mumtaazstudio.newsreader_kotlin.R
import java.text.ParseException
import java.util.*

class NewsAdapter(val newsList: MutableList<Article>, private val context: Context) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.card_news_layout, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        //Load image using picasso
        Picasso.with(context).load(newsList[position].urlToImage).to(holder.news_image)

        if (newsList[position].title!!.length > 65)
        {
            holder.news_title.text = newsList[position].title!!.substring(0,65)+"..."
        }
        else
        {
            holder.news_title.text = newsList[position].title!!
        }

        if (newsList[position].publishedAt != null)
        {
            var date: Date? = null
            try {
                date = ISO8601DateParser.parse(newsList[position].publishedAt!!)

            } catch (e:ParseException) {
                e.printStackTrace()
            }
            holder.news_time_publish.setReferenceTime(date!!.time)
        }

        holder.setItemClickListener(object :ItemClickListener {
            override fun onClick(view: View, position: Int) {

            }

        })
    }


}