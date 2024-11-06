package chapter18

//先编译代码类
//C:\Program>groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\CodeWithHeavierDependencies.groovy
//运行命令
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingCategories.groovy
class TestUsingCategories extends GroovyTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingCategories setUp")
    }

    void testMyMethod() {
        def testObj = new CodeWithHeavierDependencies()
        use(MockHelper) {
            testObj.myMethod()
            assertEquals(35, MockHelper.result)
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("TestUsingCategories tearDown")
    }
}

class MockHelper {
    def static result

    def static println(self, text) { result = text }

    def static someAction(CodeWithHeavierDependencies self) {
        25
    }
}
