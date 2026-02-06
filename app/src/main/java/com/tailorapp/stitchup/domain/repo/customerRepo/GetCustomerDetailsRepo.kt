package com.tailorapp.stitchup.domain.repo.customerRepo

import com.tailorapp.stitchup.domain.model.customer.getCustomer.GetCustomerDetailsResponse

interface GetCustomerDetailsRepo {
    suspend fun getCustomerDetails(id: Int) : GetCustomerDetailsResponse
}
