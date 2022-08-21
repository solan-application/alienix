package com.worldofwaffle

import com.worldofwaffle.database.OrderDetailEntity

data class OrderDetailUseCase(val orderDetailEntityList: List<OrderDetailEntity>): UseCase