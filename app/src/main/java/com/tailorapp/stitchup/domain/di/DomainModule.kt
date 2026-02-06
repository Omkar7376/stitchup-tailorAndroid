package com.tailorapp.stitchup.domain.di

import com.tailorapp.stitchup.domain.repo.authRepo.LoginRepo
import com.tailorapp.stitchup.domain.repo.authRepo.RegisterRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.AddCustomerRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.CustomersListRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.GetCustomerDetailsRepo
import com.tailorapp.stitchup.domain.usecase.auth.LoginUseCase
import com.tailorapp.stitchup.domain.usecase.auth.RegisterUseCase
import com.tailorapp.stitchup.domain.usecase.customer.AddCustomerUseCase
import com.tailorapp.stitchup.domain.usecase.customer.CustomersListUseCase
import com.tailorapp.stitchup.domain.usecase.customer.GetCustomerDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repository: LoginRepo) =
        LoginUseCase(repository)

    @Provides
    @Singleton
    fun provideRegisterUseCase(repo: RegisterRepo) =
        RegisterUseCase(repo)

    @Provides
    @Singleton
    fun provideCustomerListUseCase(repo: CustomersListRepo) =
        CustomersListUseCase(repo)

    @Provides
    @Singleton
    fun provideCustomersAddCustomersUseCase(repo: AddCustomerRepo) =
        AddCustomerUseCase(repo)

    @Provides
    @Singleton
    fun provideCustomerDetailsUseCase(repo: GetCustomerDetailsRepo) =
        GetCustomerDetailsUseCase(repo)

}