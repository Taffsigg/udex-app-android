package com.blocksdecoded.dex.presentation.restore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.blocksdecoded.dex.R
import com.blocksdecoded.dex.core.ui.CoreActivity
import com.blocksdecoded.dex.presentation.widgets.words.WordInputViewHolder
import com.blocksdecoded.dex.presentation.widgets.words.WordsInputAdapter
import kotlinx.android.synthetic.main.activity_restore_wallet.*

class RestoreWalletActivity: CoreActivity(), WordInputViewHolder.OnWordChangeListener {

    private lateinit var viewModel: RestoreWalletViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restore_wallet)
        
        viewModel = ViewModelProviders.of(this).get(RestoreWalletViewModel::class.java)
    
        restore_recycler.isNestedScrollingEnabled = false
        restore_recycler.layoutManager = GridLayoutManager(this, 2)
        restore_recycler.adapter = WordsInputAdapter(this)
        
        restore_confirm.setOnClickListener {
            viewModel.onRestoreClick()
        }
    }

    override fun onChange(position: Int, value: String) {
        viewModel.onWordChanged(position, value)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RestoreWalletActivity::class.java))
        }
    }
}