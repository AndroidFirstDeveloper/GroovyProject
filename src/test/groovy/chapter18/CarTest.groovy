package chapter18
//需要将该类放到test目录下，如果放到main目录下，无法找到Car类（Car类我们定义在main/java目录下）,有个奇怪的要求：使用java文件的地方需要跟java文件有相同的目录，比如都是chapter18目录
//编译Car类为class文件时，使用的jdk版本需要跟编译groovy代码的版本一致（可以进入到指定jdk的bin目录下运行编译java文件的命令：javac，可以用-d参数指定生成的class文件保存目录）
//编译groovy测试文件命令
//C:\Program Files\Java\jdk-11\bin>groovy -classpath  E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\test\groovy\chapter18\CarTest.groovy
class CarTest extends GroovyTestCase {

    def car

//    方法执行顺序：每执行一个测试方法，先执行setUp，在执行测试方法，最后执行tearDown方法，多个测试方法执行会重复上述步骤
    @Override
    protected void setUp() throws Exception {
        super.setUp()
        println("setUp")
        car = new Car()
    }

    void testInitialize() {
        println("testInitialize")
        assertEquals 0, car.miles
    }

    void testDrive() {
        println("testDrive")
        car.drive(10)
        assertEquals 10, car.miles
    }

    void testDriveNegativeInput() {
//        car.drive(-10)
        assertEquals 0, car.miles
    }

    void testException() {
        try {
            divide(2, 0)
            fail("Excepted ArithmeticException")
        } catch (ArithmeticException ex) {
            assertTrue(true)
        }
    }

    private void divide(int first, int second) {
//        if (second == 0) throw new ArithmeticException("second number must not be zero")
        println(first / second)
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown()
        println("tearDown")
//        car.setMiles(0)
    }
}
