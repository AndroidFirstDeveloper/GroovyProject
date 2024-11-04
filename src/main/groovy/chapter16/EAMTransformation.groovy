package chapter16

import groovyjarjarasm.asm.Opcodes
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

//第三个变换类。局部变换不用配置清单文件，这比全局变换显得简单一点。
//编译ast类的命令
//C:\Users\PC>groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes E:\gworkspace\GroovyProject\src\main\groovy\chapter16\EAM.groovy E:\gworkspace\GroovyProject\src\main\groovy\chapter16\EAMTransformation.groovy
//生成jar文件命令
//C:\Users\PC>jar -cf E:\gworkspace\GroovyProject\src\main\groovy\classes\eam.jar -C E:\gworkspace\GroovyProject\src\main\groovy\classes chapter16
//运行groovy文件，指定jar包路径
//C:\Users\PC>groovy -classpath  E:\gworkspace\GroovyProject\src\main\groovy\classes\eam.jar E:\gworkspace\GroovyProject\src\main\groovy\chapter16\AssertResource.groovy
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class EAMTransformation implements ASTTransformation {
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        astNodes.findAll { node -> node instanceof ClassNode }.each { classNode ->
            def useMethodBody = new AstBuilder().buildFromCode {
                def instance = newInstance()
                try {
                    instance.open()
                    instance.with block
                } finally {
                    instance.close()
                }
            }
            def useMethod = new MethodNode('myAsset',//第一个参数表示方法的名字
                    Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC,//第二个参数表示方法的修饰符 public static. 易出错的地方，这里使用的静态常量在Opcodes类下，只写后面的修饰符会报错
                    ClassHelper.OBJECT_TYPE,//第三个参数表示方法的返回类型，object类型
                    [new Parameter(ClassHelper.OBJECT_TYPE, 'block')] as Parameter[],//第四个参数表示方法的参数列表，每一个参数对象需要写明类型，参数名称，这里是一个闭包参数
                    [] as ClassNode[],//第五个参数表示方法的可能会抛出的异常.易出错的地方，这里不要忘记最后的中括号
                    useMethodBody[0] as Statement)//第六个参数表示方法体，也就是方法中的逻辑代码
            classNode.addMethod(useMethod)
        }
    }
}
