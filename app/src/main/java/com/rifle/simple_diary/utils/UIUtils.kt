package com.rifle.simple_diary.utils

import android.content.Context
import android.widget.Toast

class UIUtils {
    companion object {
        fun showToast(context: Context, desc: String) {
            Toast.makeText(context, desc, Toast.LENGTH_SHORT).show()
        }

        fun showNetError(context: Context) {
            Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
        }
    }
}