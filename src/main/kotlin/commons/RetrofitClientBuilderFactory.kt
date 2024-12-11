package commons

import okhttp3.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

class RetrofitClientBuilderFactory(private val converters: Iterable<Converter.Factory>,
                                   private val adapters: Iterable<CallAdapter.Factory>,
                                   private val okHttpClient: Call.Factory) {

    fun createInstance(): Retrofit.Builder {
        return Retrofit.Builder()
                .apply { converters.forEach { addConverterFactory(it) } }
                .apply { adapters.forEach { addCallAdapterFactory(it) } }
                .callFactory(okHttpClient)
    }
}
