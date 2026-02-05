package com.tailorapp.stitchup.presentation.addCustomer

import com.tailorapp.stitchup.constant.Resource

data class AddCustomerUiState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val error: String? = null,

    var bookNo: String = "",

    val name: String = "",
    val age: String = "",
    val gender: String = "Male",
    val mobile: String = "",
    val address: String = "",

    val orderDate: String = "",
    val deliveryDate: String = "",
    val orderType: String = "Select",
    val discount: String = "",
    val advance: String = "",
    val note: String = "",

    val shirtQty: String = "",
    val shirtAmt: String = "",
    val shirtChest: String = "",
    val shirtLength: String = "",
    val shirtShoulder: String = "",
    val shirtSleeve: String = "",
    val shirtBicep: String = "",
    val shirtCuff: String = "",
    val shirtCollar: String = "",
    val shirtBack: String = "",
    val shirtFront1: String = "",
    val shirtFront2: String = "",
    val shirtFront3: String = "",

    val pantQty: String = "",
    val pantAmt: String = "",
    val pantOutsideLength: String = "",
    val pantInsideLength: String = "",
    val pantRise: String = "",
    val pantWaist: String = "",
    val pantSeat: String = "",
    val pantThigh: String = "",
    val pantKnee: String = "",
    val pantBottom: String = "",
)
