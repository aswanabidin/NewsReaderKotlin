package mumtaazstudio.newsreader_kotlin

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import com.squareup.picasso.Picasso
import com.wang.avi.AVLoadingIndicatorView
import kotlinx.android.synthetic.main.activity_list_news.*
import mumtaazstudio.newsreader_kotlin.Adapter.NewsAdapter
import mumtaazstudio.newsreader_kotlin.Common.Common
import mumtaazstudio.newsreader_kotlin.Interface.NewsServices
import mumtaazstudio.newsreader_kotlin.Model.Article
import mumtaazstudio.newsreader_kotlin.Model.News
import retrofit2.Call
import retrofit2.Response
import kotlin.jvm.java

class ListNews : AppCompatActivity() {

    lateinit var dialog:ProgressDialog
    lateinit var mService:NewsServices
    lateinit var adapter: NewsAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var swipeRefresh_source: SwipeRefreshLayout
    lateinit var avi:AVLoadingIndicatorView
    lateinit var searchView: SearchView
    lateinit var toolbar: Toolbar

    var list:MutableList<Article> = mutableListOf()
    var btnBack:ImageButton? = null

    var webHotUrl:String? = ""
    var source=""
    var indicator:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        btnBack = findViewById(R.id.btn_back) as ImageButton
        btnBack!!.setOnClickListener {
            finish()
        }


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
                            toolbar_top_news.text = response!!.body()!!.articles!![0].author

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
                            toolbar_top_news.text = response!!.body()!!.articles!![0].author

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        var item: MenuItem = menu!!.findItem(R.id.action_search)
        searchView = MenuItemCompat
                .getActionView(item) as SearchView

        MenuItemCompat.setOnActionExpandListener(item, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                toolbar.setBackgroundColor(Color.WHITE)
                (searchView.findViewById(R.id.search_src_text) as EditText)
                        .setHintTextColor(Color.BLACK)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                searchView.setQuery("Search...",true)
                return true
            }

        })
        searchView.maxWidth = Int.MAX_VALUE
        searchNews(searchView)
        return true
    }

    private fun searchNews(searchView: SearchView) {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }

        })

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.action_search)
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (!searchView.isIconified)
        {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }
}
