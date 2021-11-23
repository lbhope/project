package com.xiangxue.kotlinproject.test
//单例
object Factory {
    fun test(){
        println(" test")
    }
}

class NetManager {
    // 只有一个实例
    object Holder {
        val instance = NetManager()
    }

    // 看不到 static  可以 派生操作
    companion object {
        // 全部都是  相当于 Java static
        fun getInstance() : NetManager = Holder.instance
    }

    fun show(name: String) {
        println("show:$name");
    }
}

fun main(){
    Factory.test()

    val net  = NetManager.getInstance()
    val net2  = NetManager.getInstance()
    net.show("kotlin welcome !!!")
    println(net == net2)
    println(net === net2)
}