package com.worldofwaffle

import androidx.databinding.ObservableField

class OrderHistoryDetailItemViewModel(orderedHistoryDetail: OrderedHistoryDetail)
    : BaseOrderHistoryViewModel(orderedHistoryDetail.userId) {
    val waffleName = ObservableField<String>()
    val addOns = ObservableField<String>()
    val waffleCount = ObservableField<String>()

        init {
            waffleName.set(orderedHistoryDetail.waffleName)
            addOns.set(orderedHistoryDetail.addOns)
            waffleCount.set(orderedHistoryDetail.waffleCount)
        }
    }