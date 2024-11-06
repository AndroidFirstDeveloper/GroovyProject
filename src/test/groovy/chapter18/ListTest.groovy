package chapter18

class ListTest extends GroovyTestCase {

    void testListSize() {//JUint希望测试方法返回类型为void，虽然groovy是动态语言，平时写def就可以，但是对于JUint最好写void
        def lst = [1, 2]
        assertEquals 'arraylist size must be 2', 2, lst.size()
    }
}
