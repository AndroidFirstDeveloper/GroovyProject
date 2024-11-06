package chapter18

//先编译代码
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\CodeWithHeavierDependencies.groovy
//再运行单元测试类（继承于GroovyTestCase的子类）。classpath 指定编译好的代码。
//groovy -classpath  E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestCodeWithHeavierDependenciesUsingOverriding.groovy
//不能直接在ide里点击绿色三角按钮（在测试方法左侧），这样会报错：Test pattern chapter18.TestCodeWithHeavierDependenciesUsingOverriding in task :test。需要在命令窗口运行groovy命令。
class TestCodeWithHeavierDependenciesUsingOverriding extends GroovyTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp()
    }

    void testMyMethod() {
        def testObj = new CodeWithHeavierDependenciesExt()
        testObj.myMethod()
        assertEquals(35, testObj.result)
    }

    void testMyMethod2(){

    }

    void testMyMethod3(){

    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
    }
}

class CodeWithHeavierDependenciesExt extends CodeWithHeavierDependencies {
    def result

    @Override
    int someAction() {
        25
    }

    def println(text) {
        result = text
    }
}
