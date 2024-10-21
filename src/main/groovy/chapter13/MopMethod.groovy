package chapter13

//测试动态添加方法、属性，Category(分类)添加、ExpandoMetaClass添加、mixin添加
class MopMethod {

    public static void main(args) {
        //MOP 元对象协议，动态给类注入新的方法技术，Category分类技术

        use(StringUtil) {
            println "123456789".toSSN()
            println new StringBuilder("987654321").toSSN()
        }
        try {
            println "123456789".toSSN()
        } catch (Exception ex) {
            println ex.message
        }


        use(StringUtilAnnotated) {
            println "123456789".toSSN()
        }

        //分类的方法接受的参数是一个对象+闭包
        use(FindUtil) {
            println "121254123".extractOnly { it == '4' || it == '5' }
        }
        //可以同时使用多个分类，逗号分隔类，或传入一个list。当多个类方法同名会有冲突，列表的最后一个分类优先级最高，优先调用后面的类的方法
        use(StringUtil, FindUtil) {
            def str = "123487651"
            println str.toSSN()
            println str.extractOnly() { it == '1' || it == '8' }
        }
        //还可以嵌套使用分类，就是在一个use中继续使用另一个use，里面的use优先级高
        use(Helper) {
            println 'hello'.toString()
        }

        //使用ExpandoMetaClass注入方法
        Integer.metaClass.daysFromNow = {
            Calendar today = Calendar.instance
            today.add(Calendar.DAY_OF_MONTH, delegate)
            today.time
        }
        println 5.daysFromNow()

        //使用ExpandoMetaClass定义属性
        Integer.metaClass.getCurrentDayNow = {
            Calendar today = Calendar.instance
            today.add(Calendar.DAY_OF_MONTH, delegate)
            today.time
        }
        println 10.currentDayNow

        //定义闭包变量，然后设置给ExpandoMetaClass
        def torrowDay = {
            Calendar today = Calendar.instance
            today.add(Calendar.DAY_OF_MONTH, (int) delegate)
            today.time
        }

        Integer.metaClass.torrowDay = torrowDay
        Long.metaClass.torrowDay = torrowDay

        println 1.torrowDay()
        println 1L.torrowDay()

        //给基类设置ExpandoMetaClass，子类可以随便使用扩展方法
        Number.metaClass.someMethod = { -> println "someMethod called"
        }
        2.someMethod()
        2L.someMethod()

        //通过ExpandoMetaClass添加静态方法
        Integer.metaClass.'static'.isEven = { val -> val % 2 == 0 }
        println "is 2 even ? " + Integer.isEven(2)
        println "is 3 even ? " + Integer.isEven(3)

        //通过ExpandoMetaClass添加构造方法，添加构造方法使用constructor关键字，以及<<符号（不是赋值=等号）。不能使用<<符号覆盖已有的构造、实例方法，否则groovy会报错
        Integer.metaClass.constructor << { Calendar calendar -> new Integer(calendar.get(Calendar.DAY_OF_YEAR))
        }
        println new Integer(Calendar.instance)
        //通过ExpadoMetaClass覆盖原来的构造方法,添加构造方法使用constructor关键字，以及=符号
        Integer.metaClass.constructor = { int val ->
            println "intercepting constructor call"
            def constructor = Integer.class.getConstructor(Integer.TYPE)
            constructor.newInstance(val)
        }

        println new Integer(4)
        println new Integer(Calendar.instance)
        //以分组的方式向类中注入方法（静态方法、实例方法、添加构造方法，覆盖构造方法）

        Integer.metaClass {
            daysFromNow1 = { ->
                Calendar today = Calendar.instance
                today.add(Calendar.DAY_OF_MONTH, delegate)
                today.time
            }
            getDaysFromNow2 = {
                Calendar today = Calendar.instance
                today.add(Calendar.DAY_OF_MONTH, delegate)
                today.time
            }
            'static' {
                isEven1 = { val -> val % 2 == 0 }
            }
            constructor = { Calendar calendar -> new Integer(calendar.get(Calendar.DAY_OF_YEAR))
            }
            constructor = { int val ->
                println "intercepting constructor call"
                def constructor = Integer.class.getConstructor(Integer.TYPE)
                constructor.newInstance(val)
            }
        }

        println 5.daysFromNow1()
        println 5.daysFromNow2
        println "Is 5 isEven ? " + Integer.isEven(5)
        println "Is 6 isEven ? " + Integer.isEven(6)
        println new Integer(Calendar.instance)
        println new Integer(5)
        //expandoMetaClass注入方法，非常的实用，但是也不是完美的。因为注入的方法不能在编译后java中调用，也不能通过反射调用。但是有一个变通方案，可以间接的调用，参考10.6章节

        //向具体的实例，注入新的方法（不是类），从而控制注入的方法的影响范围.注入方式可以选择metaClass,还可以选择创建一个ExpandoMetaClass，添加方法，初始化，然后赋值给实例的metaClass
        def emc = new ExpandoMetaClass(Person3)
        emc.sing = { -> 'oh baby baby'
        }
        emc.initialize()
        def jack = new Person3()
        def paul = new Person3()
        jack.metaClass = emc
        println jack.sing()

        try {
            paul.sing()
        } catch (Exception e) {
            println e
        }
        //可以通过metaClass=null，来取消注入的方法
        jack.metaClass = null
        try {
            jack.play()
            jack.sing()
        } catch (ex) {
            println ex
        }
        //可以直接通过向实例的metaClass注入方法，而不用创建ExpandoMetaClass对象
        jack.metaClass.dance = { -> "dance an tangle"
        }

        println jack.dance()
        try {
            println paul.dance()
        } catch (ex) {
            println ex
        }
        jack.metaClass = null
        try {
            jack.play()
            jack.dance()
        } catch (ex) {
            println ex
        }
        //使用分组的方式，直接通过实例的metaClass注入一组方法
        jack.metaClass {
            sing1 = { -> 'oh baby baby'
            }
            dance1 = { -> 'start the music ,dance an tangle'
            }
        }

        println jack.sing1()
        println jack.dance1()

        //Java允许实现多个接口，但是只允许继承一个类，同样Groovy也遵守这种约定。但是Groovy还支持灵活的将其它的类的实现方法，拉入到自己的类中。为了解决重复方法导致的问题，groovy将同名方法组合、解决冲突问题
        //混入的其它类的同名方法调用顺序优先级，越靠后优先级越高，比如A,B,C,D四个类，那么优先级D>C>B>A
        //使用Mixin实现方法的注入

        def person5 = new Person4(firstName: "John", lastName: "Smith")
        println person5.listen()
        //通过mixin方法注入类，这种方式不用修改源码，比注解的方式更强
        Dog.mixin Friend
        def buddy = new Dog(name: "Buddy")
        println buddy.listen()
        try {
            def rude = new Cat(name: "Rude")
            rude.listen()
        } catch (ex) {
            println ex
        }
        //还可以通过实例的metaClass，混入类
        def socks = new Cat(name: "Socks")
        socks.metaClass.mixin Friend
        println socks.listen()

        //使用mixin混入多个类，默认情况下多个重名方法（参数也一样）会相互冲突，先注入的方法被隐藏。即最后加入到mixin的方法会隐藏掉已经注入的方法
        //不过我们还是可以制定一个规则，让同名方法逐一调用，配合完成更好的处理
        writeStuff(created(StringWriter))

        //
        Object.metaClass.invokeOnPreviousMixin = { MetaClass currentMixinMetaClass, String method, Object[] args11 ->
            def previousMixin = delegate.getClass()
            for (mixin in mixedIn.mixinClasses) {
                if (mixin.mixinClass.theClass == currentMixinMetaClass.delegate.theClass) {
                    break
                }
                previousMixin = mixin.mixinClass.theClass
            }
            mixedIn[previousMixin]."$method"(*args11)
        }

        writeStuff(created(StringWriter, UppercaseFilter))
        writeStuff(created(StringWriter, ProfanityFilter))
        writeStuff(created(StringWriter, UppercaseFilter, ProfanityFilter))
        writeStuff(created(StringWriter, ProfanityFilter, UppercaseFilter))
    }

    def static writeStuff(writer) {//不要忘记写static，同时对于返回类型不用写出来，def就表示可以省略返回类型
        writer.write("This is stupid")
        println writer
    }

    def static created(theWriter, Object[] filters = []) {
        def instance = theWriter.newInstance()
        filters.each { filter -> instance.metaClass.mixin filter
        }
        instance
    }
}

class StringUtil {
    def static toSSN(self) {
        if (self.size() == 9) {
            "${self[0..2]}-${self[3..4]}-${self[5..8]}"
        }
    }
}

//@Category注解会根据我们传入的String参数将新定义的StringUtilAnnotated类的
//toSSN()转变为public static toSSN(String self) {...}。
@Category(String)
class StringUtilAnnotated {
    def toSSN() {
        if (size() == 9) {
            "${this[0..2]}-${this[3..4]}-${this[5..8]}"
        }
    }
}


class FindUtil {
    def static extractOnly(String self, closure) {
        def result = ''
        self.each {
            if (closure(it)) {
                result += it
            }
        }
        result
    }
}

//使用分类实现拦截方法。拦截String的toString方法,在字符串首位添加感叹号
class Helper {
    def static toString(String self) {
        def method = self.metaClass.methods.find { it.name == 'toString' }
        '!' + method.invoke(self, null) + "!"
    }
}


class Friend {
    def listen() {
        "$name is listening as a friend"
    }
}

@Mixin(Friend)
//通过注解的方式，混入其它类到当前类中，也就是将其它类的方法添加到当前类中，混入的类的方法优先级大于当前类
class Person4 {
    String firstName
    String lastName

    String getName() { "$firstName $lastName" }
}


class Dog {
    String name
}

class Cat {
    String name
}

abstract class Writer {
    abstract void write(String message)
}

class StringWriter extends Writer {
    def target = new StringBuilder()

    void write(String message) {
        target.append(message)
    }

    String toString() {
        target.toString()
    }
}

class UppercaseFilter {
    void write(String message) {
        def allUpper = message.toUpperCase()
        invokeOnPreviousMixin(metaClass, "write", allUpper)
    }
}

class ProfanityFilter {
    void write(String message) {
        def filtered = message.replaceAll("stupid", "s*****")
        invokeOnPreviousMixin(metaClass, "write", filtered)
    }
}

class Person3 {
    def play() { "playing..." }
}

