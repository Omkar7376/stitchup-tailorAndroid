package com.tailorapp.stitchup.presentation.order

import androidx.lifecycle.ViewModel
import com.tailorapp.stitchup.domain.usecase.order.CreateOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(private val orderUseCase: CreateOrderUseCase) :
    ViewModel() {
}