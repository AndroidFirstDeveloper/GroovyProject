package chapter16

//编译groovy文件，生成class文件，并将class文件放到classes目录下
//groovyc -d E:\gworkspace\GroovyProject\src\main\groovy\classes  E:\gworkspace\GroovyProject\src\main\groovy\chapter16\CodeCheck.groovy
//利用生成的class文件和清单配置文件，生成一个jar文件。这里没有指定jar文件的生成目录，默认是在命令行的根目录下，比如C:\User>
//jar -cf checkcode.jar -C E:\gworkspace\GroovyProject\src\main\groovy\classes chapter16 -C E:\gworkspace\GroovyProject\src\main\groovy\manifest .
def canVote(a) {
    a > 17 ? "you can vote" : "you can`t vote"
}
