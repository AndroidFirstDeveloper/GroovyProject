package chapter18
//先编译java文件为class，如果groovy使用的jdk版本跟环境变量里设置的jdk版本不一致，需要在指定jdk目录下进行编译
//C:\Program Files\Java\jdk-11\bin>javac -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\java\chapter18\JavaCodeWithHeavierDependencies.java
//再运行groovy文件，需要指定上一步编译的java文件的路径
//C:\Program Files\Java\jdk-11\bin>groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestJavaCodeDependencies.groovy
class TestJavaCodeDependencies extends GroovyTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp()
    }

    void testMethod() {
        def testObj = new ExtendedJavaCode()
        def originalPrintStream = System.out
        def printMock = new PrintMock()
        System.out = printMock
        try {
            testObj.myMethod()
        } finally {
            System.out = originalPrintStream
        }
        assertEquals(35, printMock.result)
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
    }
}

class ExtendedJavaCode extends JavaCodeWithHeavierDependencies {
    @Override
    int someAction() { 25 }
}

class PrintMock extends PrintStream {
    PrintMock() {
        super(System.out)
    }

    def result

    @Override
    void println(int text) {
        result = text
    }
}
