package cz.lamorak.wavyuser.util

import android.app.Activity
import android.support.annotation.StringRes
import android.widget.Toast

fun Activity.showToast(@StringRes message: Int) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()