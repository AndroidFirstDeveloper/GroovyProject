package chapter19
//这个文件是脚本文件，创建文件的时候选择Groovy script，直接写代码不要定义class xxxx。写完后，在命令行窗口运行该文件：groovy xxxx
def dslDef = new File("E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter19\\GroovyPizzaDSL.groovy").text
def dsl = new File("E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter19\\orderPizza.dsl").text

def script = """
${dslDef}
acceptOrder{
${dsl}
}
"""
new GroovyShell().evaluate(script)//运行脚本文件
