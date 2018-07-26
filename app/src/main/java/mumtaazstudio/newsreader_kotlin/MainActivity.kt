package mumtaazstudio.newsreader_kotlin

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.widget.Adapter
import android.widget.Toast
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import mumtaazstudio.newsreader_kotlin.Adapter.SourceAdapter
import mumtaazstudio.newsreader_kotlin.Common.Common
import mumtaazstudio.newsreader_kotlin.Interface.NewsServices
import mumtaazstudio.newsreader_kotlin.Model.Source
import mumtaazstudio.newsreader_kotlin.Model.Website
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var mServices: NewsServices
    lateinit var adapter: SourceAdapter
    lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Paper.init(this)

        mServices = Common.newsService

        swipeRefresh_source.setOnRefreshListener {
            getWebsiteSources(true)
        }

        recyclerview_source.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerview_source.layoutManager = layoutManager

        dialog = ProgressDialog(this)
        dialog.setMessage("Loading...")

        getWebsiteSources(false)
    }

    private fun getWebsiteSources(isRefresh: Boolean) {

        if (!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if (cache != null && !cache.isEmpty() && cache != "null")
            {
                val webSite =  Gson().fromJson<Website>(cache, Website::class.java)
                adapter = SourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recyclerview_source.adapter = adapter
            }
            else
            {
                dialog.show()
                mServices.sources.enqueue(object :retrofit2.Callback<Website>{
                    override fun onFailure(call: Call<Website>?, t: Throwable?) {
                        Toast.makeText(baseContext, "Failed",Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Website>?, response: Response<Website>?) {
                        adapter = SourceAdapter(baseContext, response!!.body()!!)
                        adapter.notifyDataSetChanged()
                        recyclerview_source.adapter = adapter


                        Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                        dialog.dismiss()
                    }
                })
            }
        }
        else
        {
            swipeRefresh_source.isRefreshing = true

            mServices.sources.enqueue(object :retrofit2.Callback<Website>{
                override fun onFailure(call: Call<Website>?, t: Throwable?) {
                    Toast.makeText(baseContext, "Failed",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Website>?, response: Response<Website>?) {
                    adapter = SourceAdapter(baseContext, response!!.body()!!)
                    adapter.notifyDataSetChanged()
                    recyclerview_source.adapter = adapter


                    Paper.book().write("cache", Gson().toJson(response!!.body()!!))

                    swipeRefresh_source.isRefreshing=false

                }
            })

        }

    }
}
