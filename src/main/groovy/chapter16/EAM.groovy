package chapter16

import org.codehaus.groovy.transform.GroovyASTTransformationClass

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

//创建一个注解，用于将变换控制在类上，避免全局变换作用范围过大的缺点
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
//注解只在类上起作用
@GroovyASTTransformationClass("chapter16.EAMTransformation")
//对注解的类，执行变换时，变换逻辑的类的路径
@interface EAM {

}