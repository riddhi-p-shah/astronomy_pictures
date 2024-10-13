package com.adyen.android.assignment.utils

import android.content.Context
import android.widget.Toast

object Utils {
    fun showUpcomingFeatureToast(context: Context) {
        Toast.makeText(
            context,
            "Feature coming soon...",
            Toast.LENGTH_SHORT
        ).show()
    }
}
