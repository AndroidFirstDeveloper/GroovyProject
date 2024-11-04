package chapter16


//编译groovy文件，生成class文件，并将class文件放到classes目录下
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes  E:\gworkspace\GroovyProject\src\main\groovy\chapter16\InjectAudit.groovy
//利用生成的class文件和清单配置文件，生成一个jar文件。这里没有指定jar文件的生成目录，默认是在命令行的根目录下，比如C:\User>
//jar -cf E:\gworkspace\GroovyProject\src\main\groovy\classes\injectAudit.jar -C E:\gworkspace\GroovyProject\src\main\groovy\classes chapter16 -C E:\gworkspace\GroovyProject\src\main\groovy\manifest .
//使用jar文件运行编写的groovy类
//groovy -classpath E:\gworkspace\GroovyProject\src\main\groovy\classes\injectAudit.jar E:\gworkspace\GroovyProject\src\main\groovy\chapter16\CheckingAccount.groovy
class CheckingAccount {

    public static void main(String[] args) {
        def account = new CheckingAccount()
        account.deposit(10000)
        account.deposit(12000)
        account.withDraw(11000)
    }

    def audit(account) {
        if (account > 10000) {
            print("auditing...")
        }
    }

    def deposit(account) {
        println("depositing ${account}...")
    }

    def withDraw(account) {
        println("withDrawing ${account}...")
    }
}
