package commons



class ServiceUrlStub(private val stubsPorts: Map<String, Int>) {


    companion object {
        private const val HOST = "http://localhost"
    }

    fun stubService(name: String) = stubsPorts[name]?.let { "$HOST:$it" }

    fun hasService(name: String) = stubsPorts.containsKey(name)

}
