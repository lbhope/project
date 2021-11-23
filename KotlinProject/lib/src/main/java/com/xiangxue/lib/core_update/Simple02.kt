package com.xiangxue.lib.core_update

fun main() {

    // 以前 { 里面直接把事情干了 }
    t01() {
        ""
    }

    // ::  == 拿到 run01 函数  （run01 变成函数类型的对象）
    val r01 = ::run01
    val r02 = r01

    t01( r02 )

}

fun t01(mm: (Int) -> String) {
    println(mm(88))
}

fun run01(number:Int) : String = "OK $number"