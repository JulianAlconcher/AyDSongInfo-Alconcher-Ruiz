package ayds.artist.external.wikipedia.data

const val WIKIPEDIA_LOGO = "https://lh4.googleusercontent.com/proxy/8-h64K947CgDJrGTIM3i3mY7SZpLS6QPO7VsF6m501_uAxIsLxyBHWo6WYDwz8jvzeS7gVX6yhFoAOGW7koLMPXcAYH4U9R-RMJdJJQXs_IVVa3Q7VSP093VzvI"
sealed class WikipediaArticle{
    data class WikipediaArticleWithData(
        var name: String,
        var description: String,
        var wikipediaURL: String,
        var wikipediaLogoURL: String,
        val sourceLogo: String = WIKIPEDIA_LOGO
    ): WikipediaArticle()
}


