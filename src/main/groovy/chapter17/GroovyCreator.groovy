package chapter17

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.json.StreamingJsonBuilder
import groovy.swing.SwingBuilder
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import org.apache.groovy.json.internal.LazyMap

import javax.swing.WindowConstants
import java.awt.FlowLayout

//groovy生成器，主要是生成xml、json、html、
class GroovyCreator {
    public static void main(String[] args) {
        def builder = new MarkupBuilder()//生成器对于数据量较小时，性能还行，对于数据达几兆时性能不太好
        builder.languages {
            language(name: 'java') { author('DogLi') }
            language(name: 'C++') { author('Goling') }
            language(name: 'Lisp') { author('McCarthy') }
        }

        println(builder.toString())
        println()

        def langs = ['C++': 'Strostrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
        def writer = new StringWriter()
        def builder2 = new MarkupBuilder(writer)//可以给生成器设置输出目标，默认一般是输出到标准输出上，比如命令行窗口，可以设置输出到文件、对象中。
        builder2.languages {
            langs.each { key, value ->
                language(name: key) {
                    author(value)
                }
            }
        }
        println(writer)
        println()
        def builder3 = new StreamingMarkupBuilder().bind {//对于大数据，比如数据大小大于几兆的，使用这个生成器性能更好
            mkp.xmlDeclaration()
            mkp.declareNamespace(computer: "Computer")
            languages {
                comment << "Create using StreamingMarkupBuilder"
                langs.each { key, value ->
                    computer.language(name: key) {
                        author(value)
                    }
                }
            }
        }
        println(builder3)
        println()

        //json 1
        def john = new Person(first: 'John', last: 'Smith', sigs: ['Java', 'Groovy'], tools: ['script': 'Groovy', 'test': 'Spock'])
        def builder4 = new JsonBuilder(john)//json生成器，可以将对象转换为json格式。JsonBuilder生成器生成的数据默认保存在内存中
        def writer2 = new StringWriter()
        builder4.writeTo(writer2)//可以将内存的数据保存到输出设备中
        println(writer2)
        println()

        //json 2
        def builder5 = new JsonBuilder()//自定义json生成器数据的结构
        builder5 {
            firstName john.first
            lastName john.last
            'special interest groups' john.sigs
            'prefered tools' {
                numberOfTools john.tools.size()
                tools john.tools
            }
        }
        def writer3 = new StringWriter()
        builder5.writeTo(writer3)//将
        println(writer3)
        println()

        //json 3
        def writer4 = new StringWriter()
        def builder6 = new StreamingJsonBuilder(writer4, john)//直接将数据输出到文件流、标准输出上
        println(writer4)
        println()

        //json4
        def slurper = new JsonSlurper()//将json数据转换为一个对象，数据可以是字符串、文件流、服务器获取的数据流
        def jsonText = '{"first":"John","last":"Smith","tools":{"script":"Groovy","test":"Spock"},"sigs":["Java","Groovy"]}'
        def person2 = slurper.parseText(jsonText)//返回的对象是一个map，LazyMap
        println("class=${person2.getClass()}")
        println("${person2.first} ${person2.last} is interested in ${person2.sigs.join(',')}")
        //如果读取的属性不存在，那么返回null，但是不会报错
        def person3 = slurper.parse(new File('E:\\gworkspace\\GroovyProject\\src\\main\\resources\\person.json'))
        println("${person3.first} ${person3.last} is interested in ${person3.sigs.join(',')}")
        //如果读取的属性不存在，那么返回null，但是不会报错
        def person4 = slurper.parse(new FileReader('E:\\gworkspace\\GroovyProject\\src\\main\\resources\\person.json'))
        println("${person4.first} ${person4.last} is interested in ${person4.sigs.join(',')}")
        //如果读取的属性不存在，那么返回null，但是不会报错

        //swing
        def swing = new SwingBuilder()
        def frame = swing.frame(title: 'Swing', location: [500, 200], size: [50, 100],
                layout: new FlowLayout(),
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE) {
            lbl = label(text: 'test')//不要添加类型定义，否则报错
            btn = button(text: 'click me', actionPerformed: {//不要添加类型定义，否则报错
                btn.text = 'Click'
                lbl.text = 'Groovy'
            })
        }
        frame.show()
    }
}

class Person {
    String first
    String last
    def sigs
    def tools
}
