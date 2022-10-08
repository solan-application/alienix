package com.worldofwaffle

data class OrderedHistoryDetail(val userId: String,
                                val waffleName: String,
                                val addOns: String,
                                val hasTakeAway: Boolean,
                                val waffleCount: String)