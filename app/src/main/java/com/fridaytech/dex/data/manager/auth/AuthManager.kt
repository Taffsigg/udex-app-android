package com.fridaytech.dex.data.manager.auth

import android.security.keystore.UserNotAuthenticatedException
import com.fridaytech.dex.App
import com.fridaytech.dex.core.model.AuthData
import com.fridaytech.dex.data.adapter.Erc20Adapter
import com.fridaytech.dex.data.adapter.EthereumAdapter
import com.fridaytech.dex.data.manager.IAdapterManager
import com.fridaytech.dex.data.manager.ICoinManager
import com.fridaytech.dex.data.security.ISecuredStorage
import com.fridaytech.dex.data.zrx.IRelayerAdapterManager
import com.fridaytech.dex.utils.Logger
import io.reactivex.subjects.PublishSubject

class AuthManager(
    private val securedStorage: ISecuredStorage,
    private val coinManager: ICoinManager
) : IAuthManager {
    override var adapterManager: IAdapterManager? = null
    override var relayerAdapterManager: IRelayerAdapterManager? = null

    override var authData: AuthData? = null
        get() = try {
            securedStorage.authData
        } catch (e: Exception) {
            Logger.e(e)
            null
        }// TODO: Load via safeLoad

    override var authDataSubject = PublishSubject.create<Unit>()

    override val isLoggedIn: Boolean
        get() = !securedStorage.noAuthData()

    @Throws(UserNotAuthenticatedException::class)
    override fun safeLoad() {
        authData = securedStorage.authData
        authDataSubject.onNext(Unit)
    }

    @Throws(UserNotAuthenticatedException::class)
    override fun login(words: List<String>) {
        AuthData(words).let {
            securedStorage.saveAuthData(it)
            authData = it
            coinManager.enableDefaultCoins()
            authDataSubject.onNext(Unit)
        }
    }

    override fun logout() {
        adapterManager?.stopKits()
        relayerAdapterManager?.clearRelayers()

        EthereumAdapter.clear(App.instance)
        Erc20Adapter.clear(App.instance)

        coinManager.clear()

        authData = null
    }
}
