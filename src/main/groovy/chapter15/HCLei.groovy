package chapter15

//使用Expando动态的合成类+委托实现添加方法
//委托的实现原理：创建委托对象，在调用未定义的方法时遍历委托对象，检查委托对象是否实现了要调用的方法，如果实现了则创建闭包注入到当前类的metaClass."$name" name是调用的方法的名称
//闭包内使用委托对象，调用其相关方法。做完上述处理，第一次调用未定义方法直接用委托对象调用同名方法，之后调用未定义方法则是走闭包里的代码(其实也是调用委托对象调用同名方法)
//委托类的优先级是越靠前优先级越高
class HCLei {
    public static void main(args) {
        //简单的定义类，并设置属性和方法
        def carA = new Expando()
        def carB = new Expando(year: 10, miles: 0, turn: { println("turning...") })
        carA.year = 15
        carA.miles = 10
        carB.drive = {
            miles += 10
            println("$miles miles was driven")
        }
        println("carA:" + carA)
        println("carB:" + carB)
        carB.turn()
        carB.drive()
        //读取文件内容，创建动态对象
        def data = new File("E:\\gworkspace\\GroovyProject\\src\\main\\resources\\cars.txt").readLines()
        def props = data[0].split(',')
        data -= data[0]
        def averageMilesDriverPerYear = { miles.toLong() / (2008 - year.toLong()) }
        def cars = data.collect {
            def car = new Expando()
            it.split(',').eachWithIndex { value, index -> car[props[index]] = value }
            car.ampy = averageMilesDriverPerYear
            car
        }
        props.each { name -> print("$name ") }
        println(" Avg. MPY")
        def ampyMethod = 'ampy'
        cars.each { car ->
            for (String property : props) {
                print("${car[property]} ")
            }
            println(car."$ampyMethod"())
        }

        //委托实现方法
        def peter = new Manager()
        peter.schedule()
        peter.simpleWork1('fast')
        peter.simpleWork1("quality")
        peter.simpleWork2()
        peter.simpleWork2()
        peter.advanceWork1('fast')
        peter.advanceWork1('quality')
        peter.advanceWork2('prototype', 'quality')
        peter.advanceWork2('product', 'quality')
        try {
            peter.simpleWork3()
        } catch (ex) {
            println(ex)
        }

        Object.metaClass.delegateCallsTo = { Class... klassOfDelegates ->
            def objectOfDelegates = klassOfDelegates.collect { it.newInstance() }
            delegate.metaClass.methodMissing = { String name, myargs ->
                println("intercepting call to $name....")
                def delegateTo = objectOfDelegates.find { it.metaClass.respondsTo(it, name, myargs) }
                if (delegateTo) {
                    delegate.metaClass."$name" = { Object[] varArgs -> delegateTo.invokeMethod(name, varArgs) }
                    delegateTo.invokeMethod(name, myargs)
                } else {
                    throw new MissingMethodException(name, delegate.getClass(), myargs)
                }
            }
        }

        def ceo = new Ceo()
        ceo.schedule()
        ceo.simpleWork1('fast')
        ceo.simpleWork1("quality")
        ceo.simpleWork2()
        ceo.simpleWork2()
        ceo.advanceWork1('fast')
        ceo.advanceWork1('quality')
        ceo.advanceWork2('prototype', 'quality')
        ceo.advanceWork2('product', 'quality')
        println("Is 2008 a leap year? " + ceo.isLeapYear(2008))
        try {
            ceo.simpleWork3()
        } catch (ex) {
            println(ex)
        }
        def coo = new Ceo()
        coo.simpleWork1("c+v")
    }
}


class Worker {
    def simpleWork1(spec) { println("worker does work1 width spec $spec") }

    def simpleWork2() { println("worker dose work2") }
}

class Expert {
    def advanceWork1(spec) { println("Expert does work1 width spec $spec") }

    def advanceWork2(scope, spec) {
        println("Expert does work2 with scope $scope spec $spec")
    }
}

class GregorianCalendar {
    def isLeapYear(year) {
        return year > 2006
    }
}
//委托实现方法,将方法的实现通过其他类实现， 如果其它类有调用的方法，将方法注入到metaClass，在闭包内调用委托类的方法
class Manager {
    def worker = new Worker()
    def expert = new Expert()

    def schedule() { println("scheduling...") }

    def methodMissing(String name, args) {
        println("intercepting call to $name...")
        def delegateTo = null
        if (name.startsWith('simple')) {
            delegateTo = worker
        }
        if (name.startsWith("advance")) {
            delegateTo = expert
        }
        if (delegateTo?.metaClass?.respondsTo(delegateTo, name, args)) {
            Manager instance = this
            instance.metaClass."$name" = { Object[] varArgs -> delegateTo.invokeMethod(name, varArgs) }
            delegateTo.invokeMethod(name, args)
        } else {
            throw new MissingMethodException(name, Manager.class, args)
        }
    }
}

class Ceo {
    {
        delegateCallsTo Worker, Expert, GregorianCalendar
    }

    def schedule() { println("scheduling....") }
}
