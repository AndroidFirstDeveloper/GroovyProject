package chapter17
//继承系统的基类，自定义生成器。必须要实现setParent、createNode方法，createNode方法一共四个，对应方法没有参数，只有一个值，只有一个map，值跟map都有的情况
//下面的代码对只有一个值、值跟map都有的情况，没有实现。
class TodoBuilderWithSupport extends BuilderSupport {
    static void main(String[] args) {
        def builder = new TodoBuilderWithSupport()
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

    private def result = new StringWriter()
    private def level = 0

    @Override
    protected void setParent(Object o, Object o1) {

    }

    //不提供值
    @Override
    protected Object createNode(Object o) {
        if (o == 'build') {
            result << "To Do:\n"
            'buildnode'
        } else {
            handle(o, [:])
        }
    }

    //提供一个值
    @Override
    protected Object createNode(Object o, Object o1) {
        throw new Exception("Invalid format")
    }

    //提供一个map
    @Override
    protected Object createNode(Object o, Map map) {
        return handle(o, map)
    }

    //提供一个map和一个值
    @Override
    protected Object createNode(Object o, Map map, Object o1) {
        throw new Exception("Invalid format")
    }

    def propertyMissing(String name) {
        handle(name, [:])
        level--
    }

    @Override
    protected void nodeCompleted(Object parent, Object node) {
//        super.nodeCompleted(parent, node)
        level--
        if (node == 'buildnode') {
            println(result)
        }
    }

    private def handle(String name, args) {
        level++
        level.times { result << " " }
        result << placeXifStatusDone(args)
        result << name.replace('_', " ")
        result << printParameters(args)
        result << "\n"
        name
    }

    private def placeXifStatusDone(args) {
        args.length > 0 && args[0] instanceof Map && args[0]['status'] == 'done' ? 'x ' : '- '
    }

    private def printParameters(args) {
        def values = ""
        if (args.size() > 0) {
            values += '['
            def count = 0
            args.each { key, value ->
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
