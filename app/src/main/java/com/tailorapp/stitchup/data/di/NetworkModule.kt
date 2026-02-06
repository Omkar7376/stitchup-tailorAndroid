package com.tailorapp.stitchup.data.di

import com.tailorapp.stitchup.constant.ApiConstant.API_KEY
import com.tailorapp.stitchup.constant.ApiConstant.BASE_URL
import com.tailorapp.stitchup.data.remote.api.authApi.AuthApiService
import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.data.repo.authRepo.LoginRepoImp
import com.tailorapp.stitchup.data.repo.authRepo.RegisterRepoImp
import com.tailorapp.stitchup.data.repo.customerRepo.AddCustomerRepoImp
import com.tailorapp.stitchup.data.repo.customerRepo.CustomersListRepoImp
import com.tailorapp.stitchup.data.repo.customerRepo.GetCustomerDetailsRepoImp
import com.tailorapp.stitchup.domain.repo.authRepo.LoginRepo
import com.tailorapp.stitchup.domain.repo.authRepo.RegisterRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.AddCustomerRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.CustomersListRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.GetCustomerDetailsRepo
import com.tailorapp.stitchup.retrofit.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor =
        ApiKeyInterceptor(API_KEY)

    @Provides
    @Singleton
    fun provideRetrofit(apiKeyInterceptor : ApiKeyInterceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(apiKeyInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApiService = retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthApiService): LoginRepo =
        LoginRepoImp(api)

    @Provides
    @Singleton
    fun provideRegisterRepo(api: AuthApiService): RegisterRepo =
        RegisterRepoImp(api)

    @Provides
    @Singleton
    fun provideCustomerApi(retrofit: Retrofit): CustomerApiService = retrofit.create(CustomerApiService::class.java)

    @Provides
    @Singleton
    fun provideCustomerListRepo(api: CustomerApiService): CustomersListRepo =
        CustomersListRepoImp(api)

    @Provides
    @Singleton
    fun provideAddCustomerRepo(api: CustomerApiService): AddCustomerRepo =
        AddCustomerRepoImp(api)

    @Provides
    @Singleton
    fun provideGetCustomerDetailsRepo(api: CustomerApiService): GetCustomerDetailsRepo =
        GetCustomerDetailsRepoImp(api)

}