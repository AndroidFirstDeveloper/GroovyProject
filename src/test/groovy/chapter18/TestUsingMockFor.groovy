package chapter18

import groovy.mock.interceptor.MockFor

//先编译代码类
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\ClassWithDependency.groovy
//再运行测试类（需要classpath指定代码类的class文件）
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingMockFor.groovy
class TestUsingMockFor extends GroovyTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingMockFor setUp")
    }

    void testMethod() {
        def testObj = new ClassWithDependency()
        def fileMock = new MockFor(FileWriter)
        def text
        fileMock.demand.write { text = it.toString() }
        fileMock.demand.close {}
        fileMock.use {
            testObj.methodC(1)
        }
        assertEquals("The value is 1", text)
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("TestUsingMockFor tearDown")
    }
}
