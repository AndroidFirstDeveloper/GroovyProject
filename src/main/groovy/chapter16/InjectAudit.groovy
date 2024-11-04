package chapter16

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

/**第二个变换类。总结：寻找项目节点（即project），寻找项目下的类节点（即project下的所有类），再寻找类节点下的方法节点，最后向方法中加入一个语句*/
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
class InjectAudit implements ASTTransformation {
    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        println("node class=${astNodes[0].class}")
        println("node classes size=${astNodes[0].classes.size()}")//astNode[0]=org.codehaus.groovy.ast.ModuleNode
        //astNodes[0].classes[0]=ClassNode  name=chapter16.CheckingAccount
        println("node classes[0] class=${astNodes[0].classes[0].class}  name=${astNodes[0].classes[0].name}")
        def checkingAccountClassNode = astNodes[0].classes.find { it.name.endsWith('CheckingAccount') }
        injectAuditMethod(checkingAccountClassNode)
    }

    static def injectAuditMethod(ClassNode checkingAccountClassNode) {
        if (checkingAccountClassNode != null) {
            def nonAuditMethods = checkingAccountClassNode.methods.findAll { it.name != 'audit' && it.name != 'main' }
//            nonAuditMethods.each { injectMethodWithAudit(it) }
//            nonAuditMethods.each { injectMethodWithAudit2(it) }
//            nonAuditMethods.each { injectMethodWithAudit3(it) }
            nonAuditMethods.each { injectMethodWithAudit4(it) }
        }
    }

    //以最基本的方式构建调用语句
    static def injectMethodWithAudit(MethodNode methodNode) {
        println("method name=${methodNode.name}")//name=deposit withDraw
        //表达式中最后一个参数表示被调用方法接收的参数，参数的来源是调用方法自己的参数，我们可以根据需要获取所有的参数，或者其中的某个参数
        def callToAudit = new ExpressionStatement(new MethodCallExpression(new VariableExpression('this'), 'audit', new ArgumentListExpression(methodNode.parameters)))
        methodNode.code.statements.add(0, callToAudit)//node:org.codehaus.groovy.ast.stmt.BlockStatement
        println("statement class=${methodNode.code.class}")
    }

    //以buildSpec方式构建调用语句
    static def injectMethodWithAudit2(MethodNode methodNode) {
        println("injectMethodWithAudit2")
        List<Statement> list = new AstBuilder().buildFromSpec {
            expression {
                methodCall {
                    variable('this')
                    constant('audit')
                    argumentList {
                        methodNode.parameters.each { variable(it.name) }
                    }
                }
            }
        }
        def callToCheck = list[0]
        methodNode.code.statements.add(0, callToCheck)
    }

    //以buildString方式构建语句
    static def injectMethodWithAudit3(MethodNode methodNode) {
        println("injectMethodWithAudit3")
        def codeAsString = 'audit(account)'
        List<Statement> statements = new AstBuilder().buildFromString(codeAsString)
        def callToAudit = statements[0].statements[0].expression
        methodNode.code.statements.add(0, new ExpressionStatement(callToAudit))
    }

    //以buildCode方式构建语句
    static def injectMethodWithAudit4(MethodNode methodNode) {
        println("injectMethodWithAudit4")
        List<Statement> statements = new AstBuilder().buildFromCode { audit(account) }
        def callToAudit = statements[0].statements[0].expression
        methodNode.code.statements.add(0, new ExpressionStatement(callToAudit))
    }
}
