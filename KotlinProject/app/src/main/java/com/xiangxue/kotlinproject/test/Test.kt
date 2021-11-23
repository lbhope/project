package com.xiangxue.kotlinproject.test

class Test constructor(firstName: String) { //主构造函数
    var secondName: String = ""

    constructor(firstName: String, secondName: String = "默认值") : this(firstName) {
        this.secondName = secondName
    }

    //定义类的属性
    var name: String = "可读写"
    var url: String = "HTTP://WWW.XXX.COM" //修改get 和 set 方法
        get() = field.toLowerCase()
        set

    val info: String = "只可读"
    var fName = firstName


    fun printName():Unit{
        println("firstName：$fName  secondName：$secondName")
    }


    init {
        println("初始化 主构造函数 $firstName")
        println("初始化代码执行 $url")
    }


}

fun main() {
    val obj = Test("第一个名字") // 创建对象，Kotlin中没有 new 关键字
    println(obj.name + " " + obj.fName)

    val obj2 = Test("第一个名字","第二个名字")
    println(obj.fName + " " + obj.secondName)

    obj2.printName()

}