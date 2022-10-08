package com.worldofwaffle

data class DetailedOrderHistory(val userOrderId: String,
                                val totalItemTotalPrice: Int,
                                val orderHistoryDetail: List<OrderedHistoryDetail>)