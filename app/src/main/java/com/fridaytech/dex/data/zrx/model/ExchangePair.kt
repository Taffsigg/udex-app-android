package com.fridaytech.dex.data.zrx.model

import com.fridaytech.zrxkit.model.AssetItem

data class ExchangePair(
    val baseCoinCode: String,
    val quoteCoinCode: String,
    val baseAsset: AssetItem,
    val quoteAsset: AssetItem
)
