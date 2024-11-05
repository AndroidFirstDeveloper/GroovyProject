package chapter17

//自定义生成器，自己写代码，解析方法、属性
class TodoBuilder {
    static void main(String[] args) {
        def builder = new TodoBuilder()
        builder.build {
            Prepare_Vacation(start: '02/15', end: '02/22') {
                Reserve_Flight(on: '01/01', status: 'done')
                Reserve_Hotel(on: '01/02')
                Reserve_Car(on: '01/02')
            }
            Buy_New_Mac {
                Install_QuickSilver
                Install_TextMate
                Install_Groovy {
                    Run_all_tests
                }
            }
        }
    }

    private def level = 0
    private def result = new StringWriter()

    def build(Closure closure) {
        result << "To-Do\n"
        closure.delegate = this
        closure()
        println result
    }

    def methodMissing(String name, args) {
        handle(name, args)
    }

    def propertyMissing(String name) {
        Object[] emptyArray = []
        handle(name, emptyArray)
    }

    private def handle(String name, args) {
        level++
        level.times { result << " " }
        result << placeXifStatusDone(args)
        result << name.replace('_', " ")
        result << printParameters(args)
        result << "\n"
        if (args.length > 0 && args[-1] instanceof Closure) {
            def theClosure = args[-1]
            theClosure.delegate = this
            theClosure()
        }
        level--
    }

    private def placeXifStatusDone(args) {
        args.length > 0 && args[0] instanceof Map && args[0]['status'] == 'done' ? 'x ' : '- '
    }

    private def printParameters(args) {
        def values = ""
        if (args.length > 0 && args[0] instanceof Map) {
            values += '['
            def count = 0
            args[0].each { key, value ->
                if (key == 'status') return
                count++
                values += (count > 1 ? " " : "")
                values += "$key:$value"
            }
            values += ']'
        }
        values
    }
}
