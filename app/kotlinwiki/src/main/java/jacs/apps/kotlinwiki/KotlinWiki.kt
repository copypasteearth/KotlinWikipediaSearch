package jacs.apps.kotlinwiki

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jacs.apps.kotlinwiki.datamodel.PageData
import jacs.apps.kotlinwiki.datamodel.WikiResponse
import jacs.apps.kotlinwiki.network.WikiAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class KotlinWiki {
    private lateinit var apiService: Retrofit
    private var wikitext: String = ""
    init {
        apiService = Retrofit.Builder().baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()).asLenient())
            .client(
                OkHttpClient().newBuilder()
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS).build())
            .build()
    }
    suspend fun searchResponse(string: String) : WikiResponse{
        val response = apiService.create(WikiAPI::class.java).getSearchRespone(string,
            "query",
            "2",
            "extracts|pageimages|pageterms",
            "100",
            "true",
            "thumbnail",
            "json",
            "prefixsearch",
            "300",
            "true")
        return response
    }
    suspend fun getPage(string: String) : PageData{
        val nextResponse = apiService.create(WikiAPI::class.java).getPageData("parse", string, "wikitext","json","2")
        wikitext = nextResponse.parse?.wikitext.toString()
        return nextResponse
    }
    fun getSimplyParsedWikiText() : String{
        wikitext = wikitext.replace("(?s)<ref.*?</ref>".toRegex(), "");
        wikitext = wikitext.replace("(?s)<!--.*?-->".toRegex(), "");
        wikitext = wikitext.replace("(?s)<small>.*?</small>".toRegex(), "");
        wikitext = wikitext.replace("[[", "")
        wikitext = wikitext.replace("]]","")
        wikitext = wikitext.replace("|", " ")
        wikitext = wikitext.replace("(?s)\\{\\{.*?\\}\\}".toRegex(), "")
        wikitext = wikitext.replace("(?s)\\{.*?\\}".toRegex(), "")
        wikitext = wikitext.replace("}}","")
        //wikitext = wikitext.replace("\\{(?<re>\\{\\{(?:(?> [^\\{\\}]+ )|<re>)*\\}\\})\\}".toRegex(), "")
        return wikitext
    }
}