package chapter18

import groovy.mock.interceptor.MockFor
import groovy.mock.interceptor.StubFor

//先编译groovy代码文件(当代码文件发生变化时，要重新编译文件)
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\ClassWithDependency.groovy
//再运行groovy测试文件，运行的时候需要classpath指定代码文件的class文件目录
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingStubFor.groovy
class TestUsingStubFor extends GroovyTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingStubFor setUp")
    }

    void testMethodB() {
        def testObj = new ClassWithDependency()
        def fileMock = new StubFor(FileWriter)
//        def fileMock = new MockFor(FileWriter)//这里如果使用MockFor，运行会报错，因为ClassWithDependency.methodB方法没有调用close，但是在fileMock上调用了close方法
        def text
        fileMock.demand.write { text = it.toString() }
        fileMock.demand.close {}
        fileMock.use {
            testObj.methodB(1)
        }
        assertEquals("The value is 1", text)
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("TestUsingStubFor tearDown")
    }
}
