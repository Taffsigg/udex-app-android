package com.fridaytech.dex.data.manager.clipboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.fridaytech.dex.App

object ClipboardManager : IClipboardManager {
    private val clipboard: ClipboardManager?
        get() = App.instance.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager

    override val hasPrimaryClip: Boolean
        get() = clipboard?.hasPrimaryClip() ?: false

    private fun copyTextToClipboard(context: Context, text: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
        val clip = ClipData.newPlainText("text", text)
        clipboard?.setPrimaryClip(clip)
    }

    override fun copyText(text: String) {
        copyTextToClipboard(
            App.instance,
            text
        )
    }

    override fun getCopiedText(): String = clipboard?.primaryClip?.itemCount?.let { count ->
        if (count > 0) {
            clipboard?.primaryClip?.getItemAt(0)?.text?.toString()
        } else {
            null
        }
    } ?: ""
}
