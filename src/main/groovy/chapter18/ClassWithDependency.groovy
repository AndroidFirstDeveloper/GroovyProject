package chapter18

class ClassWithDependency {
    def methodA(val, file) {
        file.write "The value is $val"
    }

    def methodB(val) {
        //由于存根(StubFor)测试，不会拦截调用构造方法，所以new对象会被调用，这里需要传一个实际的路径否则报错
        def file = new FileWriter("E:\\gworkspace\\GroovyProject\\src\\main\\resources\\output.txt")
        file.write("The value is $val")
    }

    def methodC(val) {
        def file = new FileWriter("E:\\gworkspace\\GroovyProject\\src\\main\\resources\\output.txt")
        file.write("The value is $val")
        file.close()
    }
}
