package mumtaazstudio.newsreader_kotlin.Adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.squareup.picasso.Picasso
import mumtaazstudio.newsreader_kotlin.Common.ISO8601DateParser
import mumtaazstudio.newsreader_kotlin.Interface.ItemClickListener
import mumtaazstudio.newsreader_kotlin.Model.Article
import mumtaazstudio.newsreader_kotlin.Model.News
import mumtaazstudio.newsreader_kotlin.NewsDetails
import mumtaazstudio.newsreader_kotlin.R
import java.security.AlgorithmConstraints
import java.text.ParseException
import java.util.*

class NewsAdapter() : RecyclerView.Adapter<NewsViewHolder>(), Filterable {

    private lateinit var list: MutableList<Article>
    private lateinit var context: Context
    private lateinit var listFiltered: MutableList<Article>

    constructor(list: MutableList<Article>, context: Context) : this() {
        this.list = list
        this.listFiltered = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.card_news_layout, parent, false)
        return NewsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listFiltered.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        //Load image using picasso
        Picasso.with(context)
                .load(list[position].urlToImage)
                .into(holder.news_image)

        if (list[position].title!!.length > 70) {
            holder.news_title.text = list[position].title!!.substring(0, 70) + "..."
        } else {
            holder.news_title.text = list[position].title!!
        }

        if (list[position].publishedAt != null) {
            var date: Date? = null
            try {
                date = ISO8601DateParser.parse(list[position].publishedAt!!)

            } catch (e: ParseException) {
                e.printStackTrace()
            }
            holder.news_time_publish.setReferenceTime(date!!.time)
        }

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val details = Intent(context, NewsDetails::class.java)
                details.putExtra("webURL", list[position].url)
                context.startActivity(details)
            }

        })
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraints: CharSequence?): FilterResults {

                var chatString: String = constraints.toString()
                if (chatString.isEmpty()) {
                    listFiltered = list
                } else {
                    var filteredList: MutableList<Article> = mutableListOf()
                    for (a: Article in list) {
                        if (a.title!!.toLowerCase().contains(chatString.toLowerCase())) {
                            filteredList.add(a)
                        }
                    }
                    listFiltered = filteredList
                }
                var filterResult: FilterResults = FilterResults()
                filterResult.values = listFiltered
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                listFiltered = results!!.values as MutableList<Article>
                notifyDataSetChanged()

            }
        }
    }


}