package ge.homework.app.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ge.homework.app.retrofit.Auth
import ge.homework.app.retrofit.OrderRepo
import ge.homework.app.retrofit.UserRepo
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return builder.client(client).build()
    }
    
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val new = chain.request().newBuilder()
                    .addHeader("Auth", Auth.token)
                    .build()

                chain.proceed(new)
            })
            .build()
    }
    
    @Provides
    @Singleton
    fun provideUserRepo(retrofit: Retrofit): UserRepo {
        return retrofit.create(UserRepo::class.java)
    }
    
    @Provides
    @Singleton
    fun provideOrderRepo(retrofit: Retrofit): OrderRepo {
        return retrofit.create(OrderRepo::class.java)
    }
}