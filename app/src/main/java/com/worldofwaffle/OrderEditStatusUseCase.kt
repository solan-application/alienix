package com.worldofwaffle

import com.worldofwaffle.database.OrderDetailEntity

data class OrderEditStatusUseCase(val hasEdited: Boolean, val orderDetailEntityList: List<OrderDetailEntity>): UseCase