package tm.mr.relaxingsounds.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tm.mr.relaxingsounds.data.local.SoundDatabase
import tm.mr.relaxingsounds.data.remote.SoundsApi
import tm.mr.relaxingsounds.data.repository.Repository
import tm.mr.relaxingsounds.data.repository.SoundRemoteMediator
import tm.mr.relaxingsounds.data.repository.SoundRepository
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideHttpLoginInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://us-central1-awesome0-86d59.cloudfunctions.net/")
            .client(client)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


    @Provides
    fun provideSoundsApi(retrofit: Retrofit): SoundsApi = retrofit.create(SoundsApi::class.java)

    @Singleton
    @Provides
    fun provideSoundDatabase(@ApplicationContext applicationContext: Context): SoundDatabase =
        Room.databaseBuilder(
            applicationContext,
            SoundDatabase::class.java, "RelaxingSounds.db"
        ).build()

    @Singleton
    @Provides
    fun provideRemoteMediator(db: SoundDatabase, api: SoundsApi): SoundRemoteMediator =
        SoundRemoteMediator(db, api)

    @Singleton
    @Provides
    fun provideRepository(api: SoundsApi, db: SoundDatabase, remoteMediator: SoundRemoteMediator): Repository =
        SoundRepository(api, db, remoteMediator)

}