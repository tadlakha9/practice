package commons


object RetrofitUrlFactory {

    private const val PROTOCOL = "http://"

    fun createUrl(name: String, url: String, serviceUrlStub: ServiceUrlStub) = when {
        serviceUrlStub.hasService(name) -> serviceUrlStub.stubService(name)
        url.isNotBlank() -> url.addMissingTrailingSlash()
        else -> "$PROTOCOL$name"
    }

    private fun String.addMissingTrailingSlash() = if (!endsWith('/')) "$this/" else this
}
