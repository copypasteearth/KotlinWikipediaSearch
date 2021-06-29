package jacs.apps.kotlinwikipediasearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import jacs.apps.kotlinwiki.network.WikiAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import jacs.apps.kotlinwiki.KotlinWiki
import jacs.apps.kotlinwiki.datamodel.WikiResponse

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        GlobalScope.launch{
            withContext(Dispatchers.IO){
                val kotlinWiki = KotlinWiki()
                var search = kotlinWiki.searchResponse("hello world");
                for(page in search.query?.pages!!){
                    val pageData = page.title?.let { kotlinWiki.getPage(it) }
                    if (pageData != null) {
                        Log.d("page", kotlinWiki.getSimplyParsedWikiText())
                        GlobalScope.launch(Dispatchers.Main){
                            textView.append(kotlinWiki.getSimplyParsedWikiText())
                        }
                    }
                }
            }
        }

    }
}