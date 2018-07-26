package mumtaazstudio.newsreader_kotlin

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_news_details.*
import retrofit2.http.Url

class NewsDetails : AppCompatActivity() {

    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        alertDialog = SpotsDialog(this)
        alertDialog.show()

        //WebView
        webView.settings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object :WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                alertDialog.dismiss()
            }
        }

        if (intent != null)
            if (!intent.getStringExtra("webURL").isEmpty())
                webView.loadUrl(intent.getStringExtra("webURL"))
    }
}
