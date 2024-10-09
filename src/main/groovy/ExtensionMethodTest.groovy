/**
 * 扩展方法测试入口类，只创建文件，不定义类
 *
 **/
def ticker1 = "119.6"
def ticker2 = "19.9"
println "Price  for $ticker1 using static method is ${String.getPrice1(ticker1)}"
println "Price for $ticker2 using instance method is ${ticker2.getPrice2()}"
Thread.hello {
    println("thread static extension method $delegate")
}
//println(ticker)