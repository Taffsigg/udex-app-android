package com.blocksdecoded.dex.presentation.widgets.balance

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.blocksdecoded.dex.R
import com.blocksdecoded.dex.utils.ui.toDisplayFormat
import com.blocksdecoded.dex.utils.ui.toFiatDisplayFormat
import com.blocksdecoded.dex.utils.visible
import kotlinx.android.synthetic.main.view_total_balance.view.*

class TotalBalanceView : LinearLayout {
    
    init {
    	View.inflate(context, R.layout.view_total_balance, this)
    }
    
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun update(balanceInfo: TotalBalanceInfo, iconVisible: Boolean = true, isEstimated: Boolean = false) {
        total_balance?.text = "${balanceInfo.balance.toDisplayFormat()} ${balanceInfo.coin.code}"
        total_fiat_balance?.text = "$${balanceInfo.fiatBalance.toFiatDisplayFormat()}"

        total_amount_coin_icon?.visible = iconVisible
        total_amount_coin_icon?.bind(balanceInfo.coin.code)

        total_balance_hint?.text = (if (isEstimated)
            context.getString(R.string.hint_equity_value, balanceInfo.coin.code)
        else
            context.getString(R.string.hint_available_balance))
    }

}