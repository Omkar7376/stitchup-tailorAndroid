package com.tailorapp.stitchup.domain.di

import com.tailorapp.stitchup.data.repo.customerRepo.UpdateCustomerRepoImp
import com.tailorapp.stitchup.domain.repo.authRepo.LoginRepo
import com.tailorapp.stitchup.domain.repo.authRepo.RegisterRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.AddCustomerRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.CustomersListRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.GetCustomerDetailsRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdateCustomerRepo
import com.tailorapp.stitchup.domain.repo.customerRepo.UpdateShirtRepo
import com.tailorapp.stitchup.domain.usecase.auth.LoginUseCase
import com.tailorapp.stitchup.domain.usecase.auth.RegisterUseCase
import com.tailorapp.stitchup.domain.usecase.customer.AddCustomerUseCase
import com.tailorapp.stitchup.domain.usecase.customer.CustomersListUseCase
import com.tailorapp.stitchup.domain.usecase.customer.GetCustomerDetailsUseCase
import com.tailorapp.stitchup.domain.usecase.customer.UpdateCustomerUseCase
import com.tailorapp.stitchup.domain.usecase.customer.UpdateShirtUseCase
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

    @Provides
    @Singleton
    fun provideUpdateCustomerUseCase(repoImp: UpdateCustomerRepo) =
        UpdateCustomerUseCase(repoImp)

    @Provides
    @Singleton
    fun provideUpdateShirtUseCase(repo: UpdateShirtRepo) =
        UpdateShirtUseCase(repo)
}