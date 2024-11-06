package chapter18
//编译命令
//C:\Program>groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\CodeWithHeavierDependencies.groovy
//运行命令
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingExpandoMetaClass.groovy

class TestUsingExpandoMetaClass extends GroovyTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingExpandoMetaClass setUp")
    }

    void testMethod() {
        def result
        def emc = new ExpandoMetaClass(CodeWithHeavierDependencies, true)
        emc.println = { text -> result = text }
        emc.someAction = { -> 25 }
        emc.initialize()

        def testObj = new CodeWithHeavierDependencies()
        testObj.metaClass = emc
        testObj.myMethod()
        assertEquals(35, result)
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("TestUsingExpandoMetaClass tearDown")
    }
}
