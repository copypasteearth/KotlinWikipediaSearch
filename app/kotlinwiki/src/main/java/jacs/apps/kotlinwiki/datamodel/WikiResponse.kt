package jacs.apps.kotlinwiki.datamodel

data class WikiResponse(
    val batchcomplete: Boolean?,
    val `continue`: Continue?,
    val query: Query?
)