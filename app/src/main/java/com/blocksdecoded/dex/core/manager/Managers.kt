package com.blocksdecoded.dex.core.manager

import com.blocksdecoded.dex.core.adapter.IAdapter
import com.blocksdecoded.dex.core.model.AuthData
import com.blocksdecoded.dex.core.model.Coin
import io.horizontalsystems.ethereumkit.core.EthereumKit
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.web3j.tx.gas.ContractGasProvider

interface ICoinManager {
    val coins: List<Coin>
    fun cleanCoinCode(coinCode: String): String
    fun getCoin(code: String): Coin
    fun getErcCoinForAddress(address: String): Coin?
}

interface IEthereumKitManager {
    val gasProvider: ContractGasProvider
    val kit: EthereumKit?

    fun ethereumKit(authData: AuthData): EthereumKit
    fun refresh()
    fun unlink()
}

interface IAdapterManager {
    val adapters: List<IAdapter>
    val adaptersUpdatedSignal: BehaviorSubject<Unit>

    fun refresh()
    fun initAdapters()
    fun stopKits()
}

interface IWordsManager {
    var isBackedUp: Boolean
    var backedUpSignal: PublishSubject<Unit>
    
    fun validate(words: List<String>)
    fun generateWords(): List<String>
}

interface IPinManager {
    val isPinSet: Boolean

    fun store(pin: String)
    fun validate(pin: String): Boolean
    fun clear()
}