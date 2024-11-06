package chapter18

//先编译groovy代码文件
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\ClassWithDependency.groovy
//再运行groovy测试文件，运行的时候需要classpath指定代码文件的class文件目录
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingMap.groovy
class TestUsingMap extends GroovyTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingMap setUp")
    }

    void testMethodA() {
        def text = ''
        def fileMock = [write: { text = it }]
        def testObj = new ClassWithDependency()
        testObj.methodA(1, fileMock)
        assertEquals("The value is 1", text)
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("TestUsingMap tearDown")
    }
}
