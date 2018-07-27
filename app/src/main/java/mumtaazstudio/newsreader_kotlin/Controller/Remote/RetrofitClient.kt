package mumtaazstudio.newsreader_kotlin.Controller.Remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitClient:Retrofit?=null

    fun getClient(baseUrl: String?):Retrofit{
        if (retrofitClient == null)
        {
            retrofitClient = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofitClient!!

    }
}