package mumtaazstudio.newsreader_kotlin

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.picasso.Picasso
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.activity_list_news.*
import kotlinx.android.synthetic.main.activity_main.*
import mumtaazstudio.newsreader_kotlin.Adapter.NewsAdapter
import mumtaazstudio.newsreader_kotlin.Common.Common
import mumtaazstudio.newsreader_kotlin.Interface.NewsServices
import mumtaazstudio.newsreader_kotlin.Model.News
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ListNews : AppCompatActivity() {

    lateinit var dialog:ProgressDialog
    lateinit var mService:NewsServices
    lateinit var adapter: NewsAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var swipeRefresh_source: SwipeRefreshLayout
    lateinit var avi:AVLoadingIndicatorView

    var webHotUrl:String? = ""
    var source=""
    var indicator:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        mService = Common.newsService

        //Progress Dialog object Instance
        dialog = ProgressDialog(this)


        //AVloadingIndicator object instance
        indicator = intent.getStringExtra("indicator")
        avi = AVLoadingIndicatorView(this)
        avi.setIndicator(indicator)


        swipeRefresh_source = SwipeRefreshLayout(this)

        swipeRefresh_source.setOnRefreshListener { getNews(source, true) }

        linear_layout_news.setOnClickListener {
            val details = Intent(baseContext, NewsDetails::class.java)
            details.putExtra("webURL",webHotUrl)
            baseContext.startActivity(details)

        }

        recyclerview_news.setHasFixedSize(true)
        recyclerview_news.layoutManager = LinearLayoutManager(this)

        if (intent != null)
        {
            source = intent.getStringExtra("source")
            if (!source.isEmpty())
                getNews(source, false)
        }
    }

    private fun getNews(source: String?, isRefresh: Boolean) {

        if (isRefresh)
        {
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : retrofit2.Callback<News>{
                        override fun onFailure(call: Call<News>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            dialog.dismiss()


                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(image_top_news)
                            tv_title_top.text = response!!.body()!!.articles!![0].title
                            tv_author_top.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url

                            val removeFirstItem = response!!.body()!!.articles

                            removeFirstItem!!.removeAt(0)

                            adapter = NewsAdapter(removeFirstItem!!, baseContext)
                            adapter.notifyDataSetChanged()
                            recyclerview_news.adapter = adapter
                        }

                    })

        }
        else
        {
            swipeRefresh_news.isRefreshing = true
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                    .enqueue(object : retrofit2.Callback<News>{
                        override fun onFailure(call: Call<News>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<News>?, response: Response<News>?) {
                            swipeRefresh_news.isRefreshing = false

                            Picasso.with(baseContext)
                                    .load(response!!.body()!!.articles!![0].urlToImage)
                                    .into(image_top_news)
                            tv_title_top.text = response!!.body()!!.articles!![0].title
                            tv_author_top.text = response!!.body()!!.articles!![0].author

                            webHotUrl = response!!.body()!!.articles!![0].url

                            val removeFirstItem = response!!.body()!!.articles

                            removeFirstItem!!.removeAt(0)

                            adapter = NewsAdapter(removeFirstItem!!, baseContext)
                            adapter.notifyDataSetChanged()
                            recyclerview_news.adapter = adapter
                        }

                    })
        }

    }
}
