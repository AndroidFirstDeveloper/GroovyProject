package chapter19
//这个文件是脚本文件，创建文件的时候选择Groovy script，写代码的时候不要写class xxxx，直接写代码即可，定义的orderInfo不要加具体类型或者def，否则会报错
def large = 'large'
def thin = 'thin'
def visa = 'visa'
def Olives = 'Olives'
def Onions = 'Onions'
def Bell_Pepper = 'Bell_Pepper'
orderInfo = [:]

def methodMissing(String name, args) {
    orderInfo[name] = args
}

def acceptOrder(Closure closure) {
    closure.delegate = this
    closure()
    println("Validation and processing performed here for order received:")
    orderInfo.each { key, value -> println("$key -> ${value.join(', ')}")
    }
}

