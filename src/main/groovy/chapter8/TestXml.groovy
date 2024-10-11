package chapter8

class TestXml {

    public static void main(args) {
        useDom()
        useXmlParser()
        useXmlSlurper()
        useXmlSlurperNs()
        createXml()
        createXml2()
    }

    def static createXml2() {
        println("")
        def langs = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
        def builder = new groovy.xml.StreamingMarkupBuilder()
        builder.encoding = "UTF-8"
        def xmlDocument = builder.bind {
            mkp.xmlDeclaration()//这两行代码，虽然点不动，但是不影响程序，而且还是必须的，去掉就不行了
            mkp.declareNamespace(computer: 'Computer')
            languages {
                comment << "Created using StringingMarkupBuilder"//出错点，comment不要写错了，写错会提示没有这个东西，commnent
                langs.each {
                    key, value ->
                        computer.language(name: key) {
                            author(value)
                        }
                }
            }
        }
        println(xmlDocument)
    }

    def static createXml() {
        def languages = ['C++': 'Stroustrup', 'Java': 'Gosling', 'Lisp': 'McCarthy']
        def content = ''
        languages.each { language, author ->
            def fragment = """
            <language name="${language}">
                <author>${author}</author>
            </language>
            """
            content += fragment
        }
        def xml = "<languages>$content</languages>"
        println(xml)
    }

    def static useXmlSlurperNs() {
        println("")
        def languages = new groovy.xml.XmlSlurper().parse("E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter8\\computerAndNaturalLanguages.xml").declareNamespace(human: 'Natural')
        println("Languages:")
        println("${languages.language.collect { it.@name }.join(', ')}")
        println("Natural Languages")
        println languages.'human:language'.collect { it.@name }.join(', ')
    }

    def static useXmlSlurper() {
        println("")
        def languages = new groovy.xml.XmlSlurper().parse("E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter8\\languages.xml")
        println("Languages and authors")
        languages.language.each {
            println("${it.@name} authored by ${it.author[0].text()}")
        }

        def languagesByAuthor = { authorName ->
            languages.language.findAll { it ->//易错点：languages.language，不要忘了写language，这个跟XmlParser解析不同
                it.author[0].text() == authorName
            }.collect {
                it.@name
            }.join(", ")
        }
        println("Languages by Wirth:" + languagesByAuthor("Wirth"))
    }

    def static useXmlParser() {
        println("")
        def languages = new groovy.xml.XmlParser().parse("E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter8\\languages.xml")
        println("Languages and authors")
        languages.each {
            println("${it.@name} authored by ${it.author[0].text()}")
        }
        def languagesByAuthor = { authorName ->
            languages.findAll {
                it.author[0].text() == authorName
            }.collect {
                it.@name
            }.join(', ')
        }
        println("Languages by Wirth:" + languagesByAuthor('Wirth'))
    }

    def static useDom() {
        def document = groovy.xml.DOMBuilder.parse(new FileReader("E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter8\\languages.xml"))
        def rootElement = document.documentElement
        use(groovy.xml.dom.DOMCategory) {
            println("Languages and authors")
            def languages = rootElement.language
            languages.each { language -> println("${language.'@name'} authored by ${language.author[0].text()}")
            }

            def languagesByAuthor = { authorName ->
                languages.findAll {
                    it.author[0].text() == authorName
                }.collect {
                    it.'@name'
                }.join(', ')
            }
            println("Languages by wirth:" + languagesByAuthor("Wirth"))
        }
    }
}
