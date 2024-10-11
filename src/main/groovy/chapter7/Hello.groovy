import java.io.*;


class Greetings {

    def static foo(str) {
        str?.reverse()
    }

    public static void main(String[] args) {
        try {
            // Process proc=Runtime.getRuntime().exec("svn help");
            Process proc = Runtime.getRuntime().exec("groovy -v");
            // Process proc=Runtime.getRuntime().exec("ls -l");
            BufferedReader result = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = result.readLine()) != null) {
                System.out.println(line);
            }
            println "${proc.getClass().name}";
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("ho ")
        }
        System.out.println("Merry Groovy!")

        for (i in 0..2) {
            print 'ho '
        }
        println "Merry Groovy!"

        0.upto(2) {
            print "$it "
        }
        println ""
        3.times {
            print "$it "
        }
        println ""
        0.step(10, 2) {
            print "$it "
        }
        println ""
        println foo("Levi")
        println foo(null)

        Car2 car2 = new Car2(2008)
        System.out.println("year=${car2.year}")
        System.out.println("miles=${car2.miles}")
        car2.miles = 20
        System.out.println("Setting miles")
        System.out.println("miles=${car2.miles}")

        Car1 car1 = new Car1(2001)
        System.out.println("year=" + car1.getYear())
        System.out.println("miles=" + car1.getMiles())
        System.out.println("setteting miles")
        car1.setMiles(100)
        System.out.println("miles=" + car1.getMiles())


        def robot = new Robot(type: 'arm', width: 20, height: 50)
        println("type=${robot.type} height=${robot.height} width=${robot.width}")

        //使用多赋值
        def (firstName, lastName) = splitName('James Bond')
        println "firstName=$firstName lastName=$lastName"

        //多赋值，交换变量的值
        def num1 = 100
        def num2 = 200
        println "num1=$num1 num2=$num2"
        (num1, num2) = [num2, num1]
        println "num1=$num1 num2=$num2"
        (num1, num2) = [num2, num1]

        //多赋值，当变量不够时，丢弃值；当值不够时，变量赋null，对于基本类型会抛异常
        def (cat, mouse) = ['jerry', 'tom', 'dog', 'fish']
        println "cat=$cat mouse=$mouse"
        def (card, vehicle, actor) = ['Shuihu', 'Motor']
        println "$card=$card vehicle=$vehicle actor=$actor"

        //定义变量甚至都不需要写def（不写具体的类型，写def根据值推断类型比较好理解，但是不写def，只写变量名称，不太符合逻辑）

        //以代码块的方式，表示接口
        // def listener={println "OnClickListener implemention"}
        // view.setOnClickListener(listener as OnClickListener)
        // view.setOnClickListener({println "view id is ${it.id}"} as OnClickListener)


        //bool值判断，对于表达式当对象不为空时，表达式代表true（对于特殊类型的对象，还要判断对象其它属性，集合大小不为0，字符串长度不为0，数字不为0，数组长度不为0）
        String s1 = "nice"
        int num3 = 10
        if (s1) {
            println "s1=$s1"
        }
        if (num3) {
            println "num3=$num3"
        }

        def list1 = null
        println list1 ? 'list1 true' : 'list1 false'
        def list2 = [1, 2, 3]
        println list2 ? 'list2 true' : 'list2 false'
        def list3 = []
        println list3 ? 'list3 true' : 'list3 false'

        //操作符重载
        def c1 = new ComplexNumber(real: 1, imaginary: 2)
        def c2 = new ComplexNumber(real: 4, imaginary: 1)
        println c1 + c2

        //for-each循环遍历集合
        String[] array1 = ['hello', 'hi', 'hey']
        for (String element : array1) {
            print "$element "
        }
        println ""
        for (def element : array1) {
            print "$element "
        }
        println ""
        for (element in array1) {
            print "$element "
        }
        println ""

        //设置枚举值
        orderCoffee(CoffeeSize.SMALL)
        orderCoffee(CoffeeSize.LARGE)
        orderCoffee(CoffeeSize.MUG)
        println "Available size are:"
        for (size in CoffeeSize.values()) {
            print "$size "
        }
        println ""
        //为枚举定义构造方法、普通方法

        for (methodology in Methodologies.values()) {
            methodology.iterationDetails()
        }


        //变长参数
        receiveArgs(1, 2, 3, 4, 5)
        receiveArray(1, 2, 3, 4, 5)
        int[] array2 = [2, 3, 4, 5]
        receiveArray(1, array2)
        receiveArray(1, [2, 3, 4, 5] as int[])


        //注解
        def person = new Person(firstName: 'Sara', lastName: 'Walker', age: 49)
        println person

        def manager = new Manager()
        manager.analyze()
        manager.work()
        manager.writeReport()

        println new CreditCard("00001-899999-1100000", 1000)

        def asNeed = new AsNeed(value: 1000)
        println asNeed.heavy1.size
        println asNeed.heavy1.size
        println asNeed.heavy2.size

        fluentCreate()

        println "Accessing TheUnique"
        TheUnique.instance.hello()
        TheUnique.instance.hello()

        def str1 = "hello"
        def str2 = str1
        def str3 = new String("hello")
        def str4 = "Hello"

        println "str1==str2 ${str1 == str2}"
        println "str1==str3 ${str1 == str3}"
        println "str1==str4 ${str1 == str4}"

        println "str1.is(str2) ${str1.is(str2)}"
        println "str1.is(str3) ${str1.is(str3)}"
        println "str1.is(str4) ${str1.is(str4)}"

        new A() == new A()
        new B() == new B()

        //java运行groovyc生成的class文件，必须指定class文件的目录、groovy的jar包地址、代码里引用的其它类所属的jar包地址，通过-classpath参数指定（-classpath或者-class_path）
        // -cp "/Users/zhenlingcui/workspace/groovy_workspace:/Users/zhenlingcui/Groovy/groovy-2.1.0/lib/groovy-2.1.0.jar:/Users/zhenlingcui/Groovy/groovy-2.1.0/lib/asm-4.0.jar"
        //java运行javac生成的class文件，需要到class文件目录下执行命令，否则找不到文件
        // 注意：
        // 1、java运行xxxx.class文件，不要带.class后缀，只要xxxx类名。
        // 2、groovyc编译.groovy文件生成的.class文件默认在不在.groovy文件所在目录，可以通过-d xxx/xx/xx指定.class文件保存目录
        // 3、groovyc编译.groovy文件生成的.class文件的名称是.groovy文件中定义的类的名，不是.groovy文件的名称


        //构造函数中，闭包跟匿名内部类的冲突
        def calibrator1 = new Calibrator({ println "the calculation provided" })
        def calculation = { println "other calculation provided" }
        def calibrator2 = new Calibrator(calculation)

        //定义基本类型数组的方式
        int[] intArray1 = [1, 2, 3, 4, 5]//如果省略掉左侧的类型信息int[]，Groovy会假设我们在创建ArrayList的实例，注意：左侧有时候忘记[]，只写int 这样会报错
        println intArray1
        println "class is ${intArray1.getClass().name}" //[I，表示intArray1对象的类型是一个数组

        def intArray2 = [0, 1, 2, 3, 4, 5] as int[] //作为一种选择，还可以使用as操作符来创建数组
        println intArray2
        println "class is ${intArray2.getClass().name}"

        //方法的形参、变量、没有定义类型的话，默认是Object类型

        //方法不明确写明返回类型，只写def，表示方法返回Object类型，


        //静态类型检查，静态类型检查注解可以添加到方法、类上，添加到类上作用于类的所有方法、闭包语句。方法上还可以添加SKIP注解跳过静态类型检查
        try {
            shout("hello")
        } catch (ex) {
            println "failed ..."
        }

        def str = "hello"
        str.metaClass.shout = { -> toUpperCase() }
        shoutString(str)

        printInReverse "hello"

        use("hello")
        use(4)

        //注意区分函数调用时使用的闭包、函数定义时使用的闭包，函数调用时闭包可以写到最后，函数定义时闭包只是一个形参，是一个变量名称


        //方法委托、方法分派、this/owner/delegate（三个属性，用于确定由哪个对象处理该闭包内的方法调用） 元编程

        print "total of even numbers from 1 to 10 is "
        println totalSelectValues(10) { it % 2 == 0 }
        def isOdd = { it % 2 != 0 }
        print "Total odd numbers from 1 to 10 is "
        println totalSelectValues(10, isOdd)

        def equ1 = new Equipment({ println "Calculator 1" })
        def aCalculator = { println "Calculator 2" }
        def equ2 = new Equipment(aCalculator)
        def equ3 = new Equipment(aCalculator)

        equ1.simulate()
        equ2.simulate()
        equ3.simulate()

        tellFoutune() { date, foutune -> println "Fortune for ${date} is ${foutune}"
        }

        //使用闭包关闭资源
        Resource.use() { res ->
            res.read()
            res.write()
        }

        tellFoutunes { date, foutune -> println "Foutune for ${date} is ${foutune}"
        }

        tellFoutunes2 { date, foutune -> println "Foutune2 for ${date} is ${foutune}"
        }

        tellFoutunes3 { msg, date, num -> println "Foutune3 for ${msg} date:${date} values is ${num}"
        }

        doSomeThing()

        doSomeThing {
            println "using specified implementation"
        }

        completeOrder(100) { it * 0.0825 }
        completeOrder(100) { amount, rate -> amount * (rate / 100) }

        examine() {}
        examine() { it }
        examine() { -> }
        examine() { val1 -> }
        examine() { Date val1 -> }
        examine() { Date val1, val2 -> }
        examine() { Date val1, String val2 -> }


        examiningClosure {
            println "In First Closure:"
            println "class is " + getClass().name
            println "this is " + this + " ,super:" + this.getClass().superclass.name
            println "owner is " + owner + " ,super:" + owner.getClass().superclass.name
            println "delegate is " + delegate + " ,super:" + delegate.getClass().superclass.name

            examiningClosure {
                println "In Closure within the first closure:"
                println "class is " + getClass().name
                println "this is " + this + " ,super:" + this.getClass().superclass.name
                println "owner is " + owner + " ,super:" + owner.getClass().superclass.name
                println "delegate is " + delegate + " ,super:" + delegate.getClass().superclass.name
            }
        }


        new Example().foo {
            println "闭包委托，this、owner、delegate三个属性"
            println "this is " + this + " supper:" + this.getClass().superclass.name
            println "owner is " + owner + " super:" + owner.getClass().superclass.name
            println "delegate is " + delegate + " super:" + delegate.getClass().superclass.name
            f1()
            f2()
        }

        //Groovy字符串的使用
        println 'groovy string using'
        def str5 = "groovy string using 2"
        println str5
        println str5.getClass().name

        def value3 = 1000
        println "groovy using expression \$$value3"//双引号包裹的字符串，特殊字符需要加转义字符
        println 'groovy using expression $value3'
        def str6 = /groovy using expression $$value3/ //正斜杠包裹的字符串，特殊字符不用加转义符号\
        println str6

        def str7 = "hello"
        println str7[2]
        try {
            str7[2] = 'A'
        } catch (ex) {
            println ex
        }

        def what = new StringBuilder("fence")
        println "what class is ${what.getClass().name}"
        def text = "the cow jumped over the $what"
        println text
        what.replace(0, 5, "moon")
        println text


        def value4 = 125
        printClassInfo("this stock close at $value4")
        printClassInfo(/the stock close at $value4/)
        printClassInfo("the stock close at 100")

        //GString的惰性求值
        def prices = 684.71
        def company = 'Google'
        def quote = "Today $company stocks close at $prices"
        println quote

        def stocks = [Apple: 663.01, MicroSoft: 30.95]
        stocks.each { key, value ->
            company = key
            prices = value
            // println "company $company  prices $prices"
            println quote
        }


        def companyClosure = { it.write(company) }
        def pricesClosure = { it.write("$prices") }
        def quote2 = "Today ${companyClosure} stocks close at ${pricesClosure}"

        stocks.each { key, value ->
            company = key
            prices = value
            println quote2
        }

        def companyClosure2 = { -> company }
        def pricesClosure2 = { -> prices }
        def quote3 = "Today ${companyClosure2} stocks close at ${pricesClosure2}"
        stocks.each { key, value ->
            company = key
            prices = value
            println quote3
        }

        def quote4 = "Today ${-> company} stocks close at ${-> prices}"
        stocks.each { key, value ->
            company = key
            prices = value
            println quote4
        }

        //多行字符串
        def multiStr = '''
		this is bad day, i am so tired.
		the rest time is end,need you work
		go back company.'''
        println multiStr

        def value5 = 100.99
        def multiStr2 = """
		invoked my friends to eat food
		,orderd four menu and three drink
		spend one hundard twenty one"""
        println multiStr2

        def langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCartly']
        def content = ''
        langs.each { language, author ->
            def fragment = """
			<language name="${language}">
				<autor>${author}</author>
			</language>
			"""
            content += fragment
        }
        def xml = "<languages>${content}</languages>"
        println xml

        //字符串操作 -
        def str8 = 'It`s a rain day in seattle'
        println str8
        str8 -= 'rain'
        println str8

        //字符串操作+
        for (str9 in 'held'..'helm') {
            println "$str9"
        }

        //正则表达式
        def obj = ~"hello"//注意这里~跟=号不要挨着，否则编译报错，=表示赋值的意思，～号表示创建一个正则表达式
        println obj.getClass().name

        def pattern = ~"(G|g)roovy"
        def text2 = "Groovy is hip"
        if (text2 =~ pattern) {//=～这里两个符号是挨着的，表示匹配正则表达式，并且是部分匹配
            println "match"
        } else {
            println "no match"
        }

        if (text2 ==~ pattern) {//==～这里三个符号是挨着的，表示匹配正则表达式，并且是精确匹配
            println "match"
        } else {
            println "no match"
        }

        def matcher = "Groovy is groovy" =~ /(G|g)roovy/
        println "Size of matcher is ${matcher.size()}"
        println "with elements ${matcher[0]} and ${matcher[1]}"
        println matcher

        //
        def str10 = "Groovy is groovy ,really groovy"
        println str10
        def result = (str10 =~ /groovy/).replaceAll('hip')//matcher.replace 表示将源字符串中符合匹配条件的内容替换成目标内容
        println result

        //第六章集合的使用
        def list = [1, 2, 3, 4, 5, 6, 7]
        println list
        println list.getClass().name
        println list[0]
        println list[list.size() - 1]
        println list[-1]
        println list[-2]
        println list[2..5]
        println list[-3..-1]

        def subList = list[2..5]
        println subList.dump()
        subList[0] = 55
        println list

        list.each { it -> println it
        }
        list.reverseEach { it -> println it
        }

        list.eachWithIndex { value, index -> println "[$index]=$value"
        }

        println list.collect { it * 2 }

        println list.find { it == 2 }
        println list.find { it > 10 }
        println list.findAll { it == 2 }
        println list.findAll { it > 10 }

        //计算集合中字符串的字符个数
        def list4 = ["place", "programmer", "In", "Groovy"]
        def count = 0
        list4.each { it -> count += it.size()
        }
        println "${list4} charaters $count"

        println "${list4} charaters ${list4.collect { it.size() }.sum()}"

        println "${list4} charaters ${list4.inject(0) { coverCount, element -> coverCount += element.size() }}"

        println list4.join(" ")

        list4[0] = ['Be', "Successful"]
        println list4
        list4 = list4.flatten()
        println list4

        list4 = list4 - ['Be', "Successful"]
        println list4
        println list4.size()
        println list4*.size()
        words(*list4)//传递一个集合的时候，需要带上*号，否则报错

        //创建一个map对象，这也是一个集合，但是属于关联性集合，键值对形式
        def map1 = ['Java': 'Celins', 'C++': 'DogLi', 'Groovy': 'DaviLench']
        println map1
        println map1.getClass().name
//这里为什么不直接用class属性，非要写getClass方法，是因为跟map访问键值对的方式有冲突，map可以用.的形式写键，如果写class会被误认为是键而不是属性
        println map1['Java']
        println map1.Java
        // println map1.C++//这样写会报错，虽然有键C++，但是++是操作符，程序会误认为C、++这是两部分内容，对于这种情况最好用[]包裹键

        //遍历map each、collect、find、findAll
        map1.each { entry -> println "${entry.getClass().name} Language ${entry.key} authored by ${entry.value}"
        }

        map1.each { language, author -> println "Language $language authored by $author"
        }

        def list5 = map1.collect { entry -> entry.key.replaceAll("[+]", "P")
        }
        println "$list5"
        def list6 = map1.collect { language, autor -> language.replaceAll('J', 'j')
        }
        println "$list6"

        def value12 = map1.find { entry -> entry.key.length() > 5
        }
        println "length>5 ${value12.key}"

        def map2 = map1.findAll { language, author -> language.size() > 3
        }
        map2.each { key, value -> println "Language $key autored by $value"
        }

        def map3 = ['briang': 'Brian Goetz', 'brians': 'Brian Sletten', 'davidb': 'David Bock', 'davidg': 'David Geary', 'scottd': 'Scott Davis', 'scottl': 'Scott Leberknight', 'stuarth': 'Stuart Halloway']
        def groupByFirstName = map3.groupBy { it.value.split(' ')[0] }
        println "class is ${groupByFirstName.getClass().name}"
        println groupByFirstName
//[Brian:[briang:Brian Goetz, brians:Brian Sletten], David:[davidb:David Bock, davidg:David Geary], Scott:[scottd:Scott Davis, scottl:Scott Leberknight], Stuart:[stuarth:Stuart Halloway]]

        groupByFirstName.each { fName, buddies -> println "$fName:${buddies.collect { key, fullName -> fullName }.join(', ')}"
        }

        //使用GDK的扩展方法 dump inspect
        def str11 = "helloworld"
        println str11
        println str11.dump()
        println str11.inspect()

        def person2 = new Person(firstName: 'sanfeng', lastName: 'zhang', age: 10)
        println person2.dump()
        println person2.inspect()

        //使用GDK的扩展方法 with
        def list7 = [1, 2, 3, 4]
        list7.with {
            add(5)
            add(6)
            println contains(2)

            println "this $this"
            println "owner $owner"
            println "delegate $delegate"
        }
        //使用GDK扩展的方法 sleep
        def thread = Thread.start {
            println "thread start runing"
            def startTime = System.nanoTime()
            new Object().sleep(2000)
            def endTime = System.nanoTime()
            println "Thread done in ${(endTime - startTime) / 10**9} seconds"
        }
        new Object().sleep(100)
        println "Let`s interrupt the thread"
        thread.interrupt()
        thread.join()

        println ""
        playWithSleep(true)
        println ""
        new Object().sleep(2000)
        playWithSleep(false)


        //间接调用方法，类似java的反射调用
        def person3 = new Person2()
        person3.invokeMethod("walk", null)
        person3.invokeMethod("walk", 100)
        person3.invokeMethod("walk", [100, "Beijing"] as Object[])

        def process = "wc".execute()
        println "process:$process"
        process.out.withWriter {
            it << "this is process communication \n"
            it << "multi line content input \n"
        }
        println process.in.text


        String[] strArray1 = ['groovy', '-e', '"print \'Groovy\'"']
        println "conduct command line :${strArray1.join(' ')}"
        println strArray1.execute().text

        //普通进程、守护进程
        printThreadInfo("Main")

        Thread.start {
            printThreadInfo 'Started'
            sleep(3000) {
                println "interrupt started"
            }
            println "finish started"
        }

        sleep(1000)

        Thread.startDaemon {
            printThreadInfo 'Daemon'
            sleep(1000) {
                println "interrupt daemon"
            }
            println "finish daemon"//不会执行到这里，非守护进程结束后，守护进程会立即结束。（那非守护进程后结束，守护进程什么时候结束呢？:守护进程正常结束，不会等待非守护进程）
        }

        //groovy io读写扩展使用
        println ""
        println new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').text

        println ""
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').eachLine { line -> println line
        }

        println ""
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').eachLine { line, lineNo -> println "$lineNo $line"
        }

        println ""
        def lineNoRange = 2..3
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').eachLine { line, lineNo ->
            if (lineNoRange.contains(lineNo)) {
                println "$lineNo $line"
            }
        }

        println ""
        println new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').filterLine {
            it =~ /language/
        }

        println ""
        byte[] data = []
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').withInputStream { it ->
            println it
            data = it.getBytes()
            println data
        }

        println ""
        byte[] outBytes = [44, 22, 88]
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').withOutputStream { it ->
            println it
            it.write(outBytes)
        }

        println ""
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').withWriter {
            println it
            it << "practice groovy withWriter using \n"
            it << "practice groovy io using \n"
        }
        println ""

        def line = ""
        new File('/Users/zhenlingcui/workspace/groovy_workspace/GroovyProgram.md').withReader {
            println it
            while (line = it.readLine()) {
                println line
            }
        }

        // http://groovy.codehaus.org/groovy-jdk (GDK，主要是groovy对jdk的扩展)

    }


    def static printThreadInfo(msg) {
        def currentThread = Thread.currentThread()
        println "$msg Thread is ${currentThread}. Daemon?${currentThread.isDaemon()}"
    }

    def static playWithSleep(flag) {
        def thread = Thread.start {
            println "thread start runing"
            def startTime = System.nanoTime()
            new Object().sleep(2000) {
                println "interrupt the thread"
                flag
            }
            def endTime = System.nanoTime()
            println "thread done in ${(endTime - startTime) / 10**9} seconds"
        }

        println "let`s interrupt the thread"
        new Object().sleep(200)
        thread.interrupt()
        thread.join()
    }

    def static words(a, b, c) {
        println "$a $b $c"
    }


    def static printClassInfo(obj) {
        println "class: ${obj.getClass().name}"
        println "superclass:${obj.getClass().superclass.name}"
    }

    // def static f2(){
    // 	println "f2 of main called..."
    // }

    def static f1() {
        println "f1 of main called..."
    }

    def static examiningClosure(closure) {
        closure()
    }


    def static examine(closure) {//动态闭包，检查参数个数，参数类型
        println "${closure.maximumNumberOfParameters} parameter given :"
        for (aParamter in closure.parameterTypes) {
            println aParamter.name
        }
        println "---------------"
    }

    def static completeOrder(amount, taxComputer) {//动态闭包，动态判断闭包的参数个数
        def tax = 0
        if (taxComputer.maximumNumberOfParameters == 2) {
            tax = taxComputer(amount, 6.05)
        } else {
            tax = taxComputer(amount)
        }
        println "Sales tax is ${tax}"
    }

    def static doSomeThing(closure) {//动态闭包，可以判断闭包是否为空
        if (closure) {
            closure()
        } else {
            println "using default implementation"
        }
    }

    def static tellFoutunes3(closure) {//科里化，指定多个参数时，参数用逗号隔开
        def date1 = new Date("10/06/2024")
        def postFoutune = closure.ncurry(1, date1)
        postFoutune "hello curry", 100
        postFoutune "hello groovy", 109
    }

    def static tellFoutunes2(closure) {//科里化
        def date1 = new Date("10/06/2024")
        def date2 = new Date("10/07/2024")
        def postFoutune = closure.rcurry("your day filled with ceremony")
        postFoutune date1
        postFoutune date2

    }

    def static tellFoutunes(closure) {
        def date = new Date("10/05/2024")
        def postFoutune = closure.curry(date)
        postFoutune "your day is filled with ceremony"
        postFoutune "They^re features,not bugs"
    }

    def static tellFoutune(closure) {//向闭包传递参数
        closure new Date("09/10/2024"), "Your day is filled width ceremony"
    }

    def static totalSelectValues(n, closure) {
        def total = 0
        for (i in 1..n) {
            if (closure(i)) {
                total += i
            }
        }
        total
    }

    @groovy.transform.TypeChecked
    def static use(Object instance) {
        if (instance instanceof String) {
            println instance.length()//不必强制类型转换
        } else {
            println instance
        }
    }

    @groovy.transform.TypeChecked
    def static printInReverse(String str) {//静态类型注释的方法，形参必须写明类型，不能省略
        println str.reverse()//没问题
    }


    // @groovy.transform.TypeChecked//静态类型检查，对于动态类型注入的方法，静态检测报错
    def static shoutString(String str) {
        println str.shout()
    }

    // @groovy.transform.TypeChecked//静态类型检查，对于类型错误，在编译阶段就显示出来
    def static shout(String str) {
        println "Printing in uppercase"
        println str.toUpperCase()
        println "Printing again in uppercase"
        println str.toUppercase()
    }


    def static splitName(fullName) {//方法参数都不用定义类型，方法返回类型也不用定义，return关键字都不用写，简洁的有点过分
        fullName.split(' ')
    }

    def static orderCoffee(size) {
        print "Coffee order received for size: $size "
        switch (size) {
            case [CoffeeSize.SHORT, CoffeeSize.SMALL]:
                println "you're health conscious"
                break
            case CoffeeSize.MEDIUM..CoffeeSize.LARGE:
                println "you gotta be programmer"
                break
            case CoffeeSize.MUG:
                println "you should try coffeine IV"
                break
        }
    }

    def static receiveArgs(int a, int ... b) {
        println "you passed $a and $b"
    }

    def static receiveArray(int a, int[] b) {
        println "you passed $a and $b"
    }

    // 在使用对象时，Newify注解可以去掉new创建对象，类似kotlin的语法
    @Newify([Person, CreditCard])
    def static fluentCreate() {
        println Person.new(firstName: 'John', lastName: 'Done', age: 20)
        println Person(firstName: 'John', lastName: 'Done', age: 20)
        println CreditCard("1234-5678-90933", 2000)
    }
}

class Car1 {
    private final int year;
    private int miles = 0;

    public Car1(int thisYear) {
        this.year = thisYear;
    }

    public int getYear() {
        return year;
    }

    public void setMiles(int m) {
        this.miles = m;
    }

    public int getMiles() {
        return this.miles;
    }
}

class Car2 {
    final year
    def miles = 0

    Car2(thisYear) {
        year = thisYear
    }
}

class Robot {
    def type, height, width
}

class ComplexNumber {
    def real, imaginary

    def plus(other) {
        new ComplexNumber(real: real + other.real,
                imaginary: imaginary + other.imaginary)
    }

    String toString() {
        "$real ${imaginary > 0 ? '+' : ''} ${imaginary}i"
    }
}


enum CoffeeSize {
    SHORT, SMALL, MEDIUM, LARGE, MUG
}

enum Methodologies {
    EVO(5),
    XP(21),
    SCRUM(30);

    final int daysInInteration

    Methodologies(days) {
        daysInInteration = days
    }

    def iterationDetails() {
        println "${this} recommond $daysInInteration days for iteration"
    }
}


//Canonical注解可以将所有的字段拼接到toString方法中以逗号隔开，不用我们自己再写toString方法了，还支持不显示某些字段
import groovy.transform.*

@Canonical(excludes = "lastName,age")
class Person {
    String firstName
    String lastName
    int age
}


//
class Worker {
    def work() {
        println "get work done"
    }

    def analyze() {
        println "analyze ..."
    }

    def writeReport() {
        println "get report written"
    }
}

class Expert {
    def analyze() {
        println "expert analysis..."
    }
}

class Manager {
    @Delegate
    Expert expert = new Expert()//Delegate注解可以访问类的方法，而不必在声明类中实现类的方法，当类中的方法增加时也不必修改声明类
    @Delegate
    Worker worker = new Worker()
}

// 该注解将类的字段标记为final，还隐式的添加了构造函数、toString、hashCode、equals方法
@Immutable
class CreditCard {
    String cardNumber
    int creditLimit
}

//懒加载，在使用Lazy标记的对象时才去创建对象
class Heavy {
    def size = 10

    Heavy() {
        println "Creating heavy size $size"
    }
}

class AsNeed {
    def value
    @Lazy
    Heavy heavy1 = new Heavy()
    @Lazy
    Heavy heavy2 = { new Heavy(size: value) }()

    AsNeed() { println "Created AsNeed" }
}

//单例注解，线程安全，同时也懒加载
//在 Groovy 2.6 中，如果要包含显式构造函数，则必须将 strict 设置为 false
@Singleton(lazy = true ,strict = false)
class TheUnique {
    private TheUnique() {
        println "Instance created"
    }

    def hello() {
        println "hello"
    }
}

class A {
    boolean equals(other) {
        println "A equals Called"
        false
    }
}

class B implements Comparable {
    boolean equals(other) {
        println "B equals Called"
        false
    }

    int compareTo(other) {
        println "B compareTo Called"
        0
    }
}

class Calibrator {

    Calibrator(calculationBlock) {
        print "using... "
        calculationBlock()
    }
}

@groovy.transform.TypeChecked
class Sample {
    def method1() {

    }

    @groovy.transform.TypeChecked(groovy.transform.TypeCheckingMode.SKIP)
    def method2(String str) {//针对这个方法，跳过静态类型检查
        str.shout()
    }
}


class NoStaticCompile {//非静态编译

    def shout1(String str) {
        println str.toUpperCase()
    }
}

class StaticCompile {//静态编译，使用注解CompileStatic就可以声明用静态编译的方式编译字节码
    @groovy.transform.CompileStatic
    def shout1(String str) {
        println str.toUpperCase()
    }
}

class Equipment {
    def calculator

    Equipment(closure) {
        calculator = closure
    }

    def simulate() {
        println "runing simulate"
        calculator()
    }
}

//闭包实现资源自动清理

class Resource {

    def open() {
        println "open ..."
    }

    def read() {
        println "read ..."
    }

    def write() {
        println "write ..."
    }

    def close() {
        println "close ..."
    }

    def static use(closure) {
        def resource = new Resource()
        try {
            resource.open()
            closure(resource)
        } catch (ex) {
            println ex
        } finally {
            resource.close()
        }
    }
}


class Handler {
    def f1() { println "f1 of Handler called..." }

    def f2() { println "f2 of handler called..." }
}

class Example {
    def f1() { println "f1 of Example called..." }

    def f2() { println "f2 of Example called..." }

    def foo(closure) {
        closure.delegate = new Handler()
        closure()
    }
}

class Person2 {
    def walk() { println "walk ...." }

    def walk(int miles) { println "walk $miles miles" }

    def walk(int miles, String where) { println "walk $miles miles $where ..." }
}




