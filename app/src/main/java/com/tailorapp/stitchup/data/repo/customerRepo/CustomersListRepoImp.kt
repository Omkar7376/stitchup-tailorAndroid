package com.tailorapp.stitchup.data.repo.customerRepo

import android.util.Log
import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.api.customerApi.CustomerApiService
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto
import com.tailorapp.stitchup.domain.repo.customerRepo.CustomersListRepo

class CustomersListRepoImp(private val apiService: CustomerApiService) : CustomersListRepo {
    override suspend fun customerList() : Resource<List<CustomerDataDto>> {
        return try {
            val response = apiService.getCustomerList()
            Log.d("###", "customerList: ${response.body().toString()}")
            Resource.Success(response.body()!!.customer)
            
        } catch (e: java.net.SocketTimeoutException) {
            Log.e("###", "customerList: Timeout - Server unreachable", e)
            Resource.Error("Server Timeout. Check IP Address or Server Status.")
        } catch (e: java.io.IOException) {
            Log.e("###", "customerList: Network Error", e)
            Resource.Error("Network Error. Check Internet Connection.")
        } catch (e: Exception) {
            Log.e("###", "customerList: General Error", e)
            Resource.Error(e.message ?: "Unknown Error")
        }
    }
}