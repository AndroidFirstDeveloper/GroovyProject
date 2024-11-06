package chapter18

import groovy.mock.interceptor.MockFor
//编译被测试类的命令
//C:\Users\PC>groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\MultiCallWriter.groovy
//运行命令
//C:\Users\PC>groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingMockFor3.groovy
class TestUsingMockFor3 extends GroovyTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingMockFor3 setUp")
    }

    void testSomeWriter() {
        println("TestUsingMockFor3 testSomeWriter")
        def testObj = new MultiCallWriter()
        def fileMock = new MockFor(FileWriter)
        //第一种方式，只调用，不使用断言
//        fileMock.demand.write(3..3) {}//这里设置的方法调用次数，如果少于被测试类中调用次数，那么会报错
        //第二种方式，调用，通过参数使用断言
        def params = ['one', 'two', 3]
        def index = 0
        fileMock.demand.write(3..3) { assertEquals(params[index++], it) }//这里不要给assertEquals方法内的参数加双引号使用$符号，因为这样得到的都是字符串，但是最后一个参数是3
        fileMock.demand.flush {}
        fileMock.demand.getEncoding { return 'whatever' }
        fileMock.demand.write { assertEquals('whatever', it.toString()) }
        fileMock.demand.close {}
        fileMock.use {
            testObj.someWriter()
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("TestUsingMockFor3 tearDown")
        new File("E:\\gworkspace\\GroovyProject\\src\\main\\resources\\output1.txt").delete()
    }
}


