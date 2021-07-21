package com.rafao1991.myexperienceasandroidengineer.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Experience(val year: Int, val description: String) : Parcelable