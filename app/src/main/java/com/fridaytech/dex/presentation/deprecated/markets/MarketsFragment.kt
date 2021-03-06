package com.fridaytech.dex.presentation.deprecated.markets

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.fridaytech.dex.R
import com.fridaytech.dex.core.ui.CoreFragment
import com.fridaytech.dex.presentation.deprecated.markets.recycler.MarketViewHolder
import com.fridaytech.dex.presentation.deprecated.markets.recycler.MarketsAdapter
import com.fridaytech.dex.presentation.main.IFocusListener
import com.fridaytech.dex.presentation.statschart.ChartInfoDialog
import com.fridaytech.dex.utils.visible
import kotlinx.android.synthetic.main.fragment_markets.*

class MarketsFragment : CoreFragment(R.layout.fragment_markets), MarketViewHolder.Listener,
    IFocusListener {

    private lateinit var adapter: MarketsAdapter
    private lateinit var viewModel: MarketsViewModel

    //region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = MarketsAdapter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MarketsViewModel::class.java)

        viewModel.markets.observe(this, Observer { adapter.setMarkets(it) })

        viewModel.loading.observe(this, Observer { markets_refresh?.isRefreshing = it })

        viewModel.openMarketInfoEvent.observe(this, Observer { coinCode ->
            activity?.let {
                ChartInfoDialog.show(it.supportFragmentManager, coinCode)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        markets_recycler?.layoutManager = LinearLayoutManager(context)
        markets_recycler?.adapter = adapter

        markets_refresh?.setOnRefreshListener { viewModel.refresh() }
    }

    //endregion

    override fun onFocused() {
        coordinator?.visible = true
    }

    //region ViewHolder

    override fun onClick(position: Int) {
        viewModel.onMarketClick(position)
    }

    //endregion

    companion object {
        fun newInstance() = MarketsFragment()
    }
}
