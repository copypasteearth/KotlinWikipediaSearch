'''kotlin
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
'''