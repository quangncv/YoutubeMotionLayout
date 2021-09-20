package com.example.youtubemotionlayout.widgets

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.youtubemotionlayout.R

class LoadingDialog: DialogFragment(R.layout.dialog_loading) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }
}