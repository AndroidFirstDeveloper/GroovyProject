package chapter18

import groovy.mock.interceptor.MockFor
//先编译代码类
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter18\TwoFileUser.groovy
//再运行测试类（需要classpath指定代码类的class文件）
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\TestUsingMockFor2.groovy
class TestUsingMockFor2 extends GroovyTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("TestUsingMockFor2 setUp")
    }

    void testMethod() {
        println("TestUsingMockFor2 testMethod")
        def testObj = new TwoFileUser()
        def testData = 'Multi Files'
        def fileMock = new MockFor(FileWriter)//这里的一个模拟对象，可以代表被测试类中的所有实例
        fileMock.demand.write { assertEquals(testData, it) }//但是需要为被测试类中每个实例指定调用的方法，即使多个实例调用的是相同的方法
        fileMock.demand.write { assertEquals(testData.size(), it) }
        fileMock.demand.close(2..2) {}//这里在模拟方法中指定调用的次数，可以减少书写的代码，前提是多个实例调用的方法是一样的。2..2表示最少两次，最多两次。0..3表示最少0次，最多3次。
        fileMock.use {
            testObj.useFiles(testData)
        }
    }

    @Override
    protected void tearDown() throws Exception {
//        super.tearDown()
        println("TestUsingMockFor2 tearDown")
        def file1 = new File("E:\\gworkspace\\GroovyProject\\src\\main\\output1.txt")
        if (file1.isFile() && file1.exists()) {
//            def security = System.getSecurityManager().checkDelete(file1.getAbsolutePath())
//            println("security=$security")
            def result1 = file1.delete()
            println("result1=$result1")
        }
        def file2 = new File("E:\\gworkspace\\GroovyProject\\src\\main\\output2.txt")
        if (file2.isFile() && file2.exists()) {
            def result2 = file2.delete()
            println("result2=$result2")
        }
    }
}
