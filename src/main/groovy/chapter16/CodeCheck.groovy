package chapter16

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.GroovyClassVisitor
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

//第一个变换类。检查方法名称、参数名称定义是否合规。
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
//SEMANTIC_ANALYSIS含义：在语义分析阶段之后应用ast变换
class CodeCheck implements ASTTransformation {

    @Override
    void visit(ASTNode[] astNodes, SourceUnit sourceUnit) {
        sourceUnit.ast.classes.each { classNode -> classNode.visitContents(new OurClassVisitor(sourceUnit)) }
    }
}

//可以hook类的所有节点，包括方法、属性、构造器，方便做各种拦截操作、判断操作
class OurClassVisitor implements GroovyClassVisitor {

    SourceUnit sourceUnit

    OurClassVisitor(theSourceUnit) {
        sourceUnit = theSourceUnit
    }

    private void reportError(message, lineNumber, columnNumber) {
        sourceUnit.addError(message, lineNumber, columnNumber)
    }

    @Override
    void visitMethod(MethodNode methodNode) {
        if (methodNode.name.size() == 1) {
            reportError "Make method name descriptive , avoid single letter names", methodNode.lineNumber, methodNode.columnNumber
        }
        methodNode.parameters.each { parameter ->
            if (parameter.name.size() == 1) {
                reportError "Single letter parameters are normally wrong!", parameter.lineNumber, parameter.columnNumber
            }
        }
    }

    @Override
    void visitClass(ClassNode classNode) {

    }

    @Override
    void visitConstructor(ConstructorNode constructorNode) {


    }

    @Override
    void visitField(FieldNode fieldNode) {

    }

    @Override
    void visitProperty(PropertyNode propertyNode) {

    }
}