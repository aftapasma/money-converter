package org.d3if0019.uangconverter.model

data class Uang(val kode:String, val nama:String, val nilai:Double)

val mataUang = listOf(
    Uang("USD","United Stated Dollar",1.0),
    Uang("EUR","Euro",0.93),
    Uang("GBP","British Pound Sterling",0.79),
    Uang("JPY","Japanase Yen",151.37),
    Uang("IDR","Indonesian Rupiah",15899.30)
)