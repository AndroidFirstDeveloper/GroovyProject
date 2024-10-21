package chapter14

//合成方法，拦截、缓存、调用。拦截未定义的方法，创建方法并通过metaClass注入到类或实例中，然后调用创建的方法。
//通过定义methodMissing来实现合成方法，其实是在拦截方法以后，将创建的方法体（闭包）注入到metaClass
//通过实现GroovyInterceptable接口来实现合成方法，在methodMissing方法拦截方法后，将创建的方法注入到metaClass
// ExpandoMetaClass合成方法，对于无法编辑的类，无法实现其methodMissing方法，不能通过拦截方法的方式合成方法。而ExpandoMetaClass可以。
class HCMethod {

    public static void main(args) {
        def gaidou = new Person5()
        println gaidou.work()
        println gaidou.playTennis()
        println gaidou.playBasketBall()
        println gaidou.playVolleyBall()
        println gaidou.playTennis()
        // try{
        // 	gaidou.playPolitics()
        // }catch(ex){
        // 	println ex
        // }

        def person111 = new Person6()
        person111.speak()

        //expandometaclass
        //第一部分代码
        /*Person.metaClass.methodMissing = { String name, myargs ->
            def plays = ['Tennis', 'VolleyBall', 'BasketBall']
            System.out.println("methodMissing called for $name")
            def methodInList = plays.find { it == name.split('play')[1] }
            if (methodInList) {
                def impl = { Object[] vargs -> "playing ${name.split('play')[1]}..." }
                Person.metaClass."$name" = impl
                impl(myargs)
            } else {
                throw new MissingMethodException(name, Person.class, myargs)
            }
        }
        def jack = new Person()
        println jack.work()
        println jack.playTennis()
        println jack.playTennis()
        try {
            jack.playPolitics()
        } catch (ex) {
            println(ex)
        }*/
        //第二部分代码，测试第二部分代码需要把第一部分注释掉.所有方法会先进入invokeMethod，如果方法存在直接调用方法，如果方法不存在调用methodMissing方法，在methodMissing方法内
        //通过metaClass注入未定义的方法.调用在methodMissing方法中无法动态生成的方法，会报错MissingMethodException，不会堆栈溢出.这里的methodMissing方法只能够创建三个未定义方法：
        //playTennis、playVolleyball、playBasketBall。
        Person.metaClass.invokeMethod = { String name, myargs ->
            System.out.println("intercepting call for $name")
            def method = Person.metaClass.getMetaMethod(name, myargs)
            if (method) {
                method.invoke(delegate, myargs)
            } else {
                Person.metaClass.invokeMissingMethod(delegate, name, myargs)
            }
        }

        Person.metaClass.methodMissing = { String name, myargs ->
            def plays = ['Tennis', 'VolleyBall', 'BasketBall']
            System.out.println("methodMissing called for $name")
            def methodInList = plays.find { it == name.split('play')[1] }
            if (methodInList) {
                def impl = { Object[] vargs -> "playing ${name.split('play')[1]}..." }
                Person.metaClass."$name" = impl
                impl(myargs)
            } else {
                throw new MissingMethodException(name, Person.class, myargs)
            }
        }
        def chou = new Person()
        println chou.work()
        println chou.playTennis()
        println chou.playTennis()
        try {
            println chou.playPingpang()
        } catch (ex) {
            println(ex)
        }

        //为具体实例合成方法
        def emc = new ExpandoMetaClass(Motor)
        emc.methodMissing = { String name, myargs -> "i am motor of all trades ... I can $name"
        }
        emc.initialize()
        def bb = new Motor()
        def bta = new Motor()
        bb.metaClass = emc
        println bb.sing()
        println bb.dance()
        println bb.juggle()
        try {
            bta.sing()
        } catch (ex) {
            println(ex)
        }
    }
}

class Person {
    def work() { "working" }
}

class Person5 implements GroovyInterceptable {
    def work() { "working..." }

    def plays = ["Tennis", "VolleyBall", "BasketBall"]

    def invokeMethod(String name, args) {//这里的args不能写错，写成Object[] args就错了
        System.out.println "intercepting call for $name"
        //这里的打印方法，不能写成println，需要写完整的System.out.println，否则会导致递归调用最终堆栈溢出错误
        def method = metaClass.getMetaMethod(name, args)
        if (method) {
            method.invoke(this, args)
        } else {
            metaClass.invokeMethod(this, name, args)
        }
    }

    def methodMissing(String name, args) {
        System.out.println "methodMissing called for $name"
        def methodInList = plays.find { it == name.split("play")[1] }
        if (methodInList) {
            def impl = { Object[] vargs -> "playing ${name.split('play')[1]}..."
            }
            Person5 instance = this
            instance.metaClass."$name" = impl
            impl(args)
        } else {
            throw new MissingMethodException(name, Person5.class, args)
        }
    }
}

class Person6 implements GroovyInterceptable {

    def invokeMethod(String name, args) {
        System.out.println "person6 intercepting all method"
        def method = metaClass.getMetaMethod(name, args)
        if (method) {
            method.invoke(this, args)
        } else {
            throw new MissingMethodException(name, Person6.class, args)
        }
    }

    def speak() {
        System.out.println "i speak english"
    }
}

class Motor {
    def running() { "running in speed 78 kilimiter" }
}
