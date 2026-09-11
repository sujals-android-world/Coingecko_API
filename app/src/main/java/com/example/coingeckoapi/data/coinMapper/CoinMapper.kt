package com.example.coingeckoapi.data.coinMapper

import com.example.coingeckoapi.data.remote.response.CoinGeckoResponseItem
import com.example.coingeckoapi.data.roomdb.CoinEntity
import com.example.coingeckoapi.domain.model.CoinModel

fun CoinGeckoResponseItem.toModel() : CoinModel {
    return CoinModel(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap_rank = market_cap_rank,
        price_change_percentage_24h = price_change_percentage_24h,
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        max_supply = max_supply,
        price_change_24h = price_change_24h,
        roi = roi,
        total_supply = total_supply,
        total_volume = total_volume
    )
}
fun List<CoinGeckoResponseItem>.toModelList(): List<CoinModel> {
    return map { it.toModel() }
}

fun List<CoinGeckoResponseItem>.toCoinEntityListFromAPI(): List<CoinEntity> {
    return map { it.toCoinEntity() }
}

fun CoinModel.toCoin(): CoinGeckoResponseItem {
    return CoinGeckoResponseItem(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap_rank = market_cap_rank,
        price_change_percentage_24h = price_change_percentage_24h,
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        max_supply = max_supply,
        price_change_24h = price_change_24h,
        roi = roi,
        total_supply = total_supply,
        total_volume = total_volume
    )
}

fun List<CoinModel>.toCoinList(): List<CoinGeckoResponseItem> {
    return map { it.toCoin() }
}
fun List<CoinModel>.toCoinEntityList(): List<CoinEntity> {
    return map { it.toCoinEntity() }
}
fun List<CoinEntity>.toCoinModelList(): List<CoinModel> {
    return map { it.toCoinModel() }
}

fun CoinModel.toCoinEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap_rank = market_cap_rank,
        price_change_percentage_24h = price_change_percentage_24h,
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        max_supply = max_supply,
        price_change_24h = price_change_24h,
        total_supply = total_supply,
        total_volume = total_volume
    )
}

fun CoinEntity.toCoinModel(): CoinModel {
    return CoinModel(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap_rank = market_cap_rank,
        price_change_percentage_24h = price_change_percentage_24h,
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        max_supply = max_supply,
        price_change_24h = price_change_24h,
        total_supply = total_supply,
        total_volume = total_volume
    )
}

fun CoinGeckoResponseItem.toCoinEntity(): CoinEntity {
    return CoinEntity(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap_rank = market_cap_rank,
        price_change_percentage_24h = price_change_percentage_24h,
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        max_supply = max_supply,
        price_change_24h = price_change_24h,
        total_supply = total_supply,
        total_volume = total_volume
    )
}

fun CoinEntity.toCoinGeckoResponseItem(): CoinGeckoResponseItem {
    return CoinGeckoResponseItem(
        id = id,
        symbol = symbol,
        name = name,
        image = image,
        current_price = current_price,
        market_cap_rank = market_cap_rank,
        price_change_percentage_24h = price_change_percentage_24h,
        ath = ath,
        ath_change_percentage = ath_change_percentage,
        ath_date = ath_date,
        atl = atl,
        atl_change_percentage = atl_change_percentage,
        atl_date = atl_date,
        circulating_supply = circulating_supply,
        fully_diluted_valuation = fully_diluted_valuation,
        high_24h = high_24h,
        last_updated = last_updated,
        low_24h = low_24h,
        market_cap = market_cap,
        market_cap_change_24h = market_cap_change_24h,
        market_cap_change_percentage_24h = market_cap_change_percentage_24h,
        max_supply = max_supply,
        price_change_24h = price_change_24h,
        roi = null,
        total_supply = total_supply,
        total_volume = total_volume
    )
}



