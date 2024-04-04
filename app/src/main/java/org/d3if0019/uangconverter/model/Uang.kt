package org.d3if0019.uangconverter.model

import androidx.annotation.DrawableRes
import org.d3if0019.uangconverter.R

data class Uang(val kode:String, val nama:String, val nilai:Double,@DrawableRes val imageResId: Int )

val mataUang = listOf(
    Uang("USD","United Stated Dollar",1.0, R.drawable.united_states),
    Uang("EUR","Euro",0.93, R.drawable.euro),
    Uang("GBP","British Pound Sterling",0.79, R.drawable.british),
    Uang("JPY","Japanese Yen",151.37, R.drawable.japanese),
    Uang("IDR","Indonesian Rupiah",15899.30, R.drawable.indonesia)
)