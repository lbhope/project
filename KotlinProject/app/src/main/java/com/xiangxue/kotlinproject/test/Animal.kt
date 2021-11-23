package com.xiangxue.kotlinproject.test

//一个类要被继承，可以使用 open 关键字进行修饰
open class Animal(var name: String) {

    //允许子类重写
    open fun printName(){
        println("name:$name")
    }
}

