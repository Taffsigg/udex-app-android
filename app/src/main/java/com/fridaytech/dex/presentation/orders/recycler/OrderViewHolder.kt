package com.fridaytech.dex.presentation.orders.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.fridaytech.dex.R
import com.fridaytech.dex.data.zrx.model.SimpleOrder
import com.fridaytech.dex.presentation.orders.model.EOrderSide
import com.fridaytech.dex.utils.ui.getAttr
import com.fridaytech.dex.utils.ui.toDisplayFormat
import com.fridaytech.dex.utils.ui.toFiatDisplayFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_order.*

class OrderViewHolder(
    override val containerView: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        itemView.setOnClickListener { listener.onClick(adapterPosition) }
    }

    fun onBind(order: SimpleOrder) {
        itemView.setBackgroundColor(
            getAttr(if (adapterPosition % 2 == 0) {
                    R.attr.OddOrderBackground
                } else {
                    R.attr.EvenOrderBackground
                })
        )

        if (order.side == EOrderSide.BUY) {
            order_amount.text = order.takerAmount.toDisplayFormat()
            order_amount_coin.text = order.takerCoin.code
            order_amount_fiat.text = "~ $${order.takerFiatAmount.toFiatDisplayFormat()}"

            order_total.text = order.makerAmount.toDisplayFormat()
            order_total_coin.text = order.makerCoin.code
            order_total_fiat.text = "~ $${order.makerFiatAmount.toFiatDisplayFormat()}"
        } else {
            order_amount.text = order.makerAmount.toDisplayFormat()
            order_amount_coin.text = order.makerCoin.code
            order_amount_fiat.text = "~ $${order.makerFiatAmount.toFiatDisplayFormat()}"

            order_total.text = order.takerAmount.toDisplayFormat()
            order_total_coin.text = order.takerCoin.code
            order_total_fiat.text = "~ $${order.takerFiatAmount.toFiatDisplayFormat()}"
        }
    }

    interface Listener {
        fun onClick(position: Int)
    }
}
