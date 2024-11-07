package chapter19

class Total {

    static void main(String[] args) {
        def testObj = new Total()
        testObj.clear
        testObj.add 2
        testObj.add 5
        testObj.add 7
        testObj.total
        testObj.clear()
        testObj.total()
    }

    def value = 0

    def getClear() {
        value = 0
    }

    def getTotal() {//定义方法，才能以属性的方式使用这个方法
        println("Total is $value")
    }

    def clear() {
        value = 0
    }

    def add(number) {
        value += number
    }

    def total() {
        println("Total is $value")
    }
}
