package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.constant.Resource
import com.tailorapp.stitchup.data.remote.dto.customerDto.customerList.CustomerDataDto

interface CustomersListRepo {
    suspend fun customerList() : Resource<List<CustomerDataDto>>
}