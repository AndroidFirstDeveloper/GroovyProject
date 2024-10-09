1、首先下载Intellij IDEA软件，并安装
2、下载groovy压缩包，下载完成后解压任意目录，将bin目录添加到环境变量，mac、window都需要添加
3、Intellij IDEA创建一个groovy工程，buildsystem选择gradle，配置使用的jdk、groovy版本
接下来就是写代码了：
4、在代码的最外层目录创建groovy类，定义好扩展方法，扩展方法必须是静态的，方法的第一个参数必须是要扩展的类的类型，其它参数根据个人需求添加，数量没有限制
5、在最外层目录下创建manifest/META-INF/services目录，目录名称不能修改，然后在创建一个文件org.codehaus.groovy.runtime.ExtensionModule，文件的名称不能修改，在文件内配置扩展方法相关
的类、版本、模块名称，如果有多个类用逗号隔开就行
6、在最外层目录下创建一个groovy文件（不创建类，只是创建一个文件），测试扩展方法的正确性，文件内容就是调用相应类的实例方法或静态方法
7、编译扩展类以生成class文件命令： groovyc -d classes PriceExtension.groovy PriceStaticExtension.groovy ThreadExt.groovy   //-d classes 表示将生成的class文件存储到classes目录下
8、打包class文件为jar包命令：jar -cf ExtensionTest.jar -C classes . -C manifest/ .    //-cf ExtensionTest.jar 表示生成的jar包名称，-C classes . -C manifest/ .表示将class文件
按照manifest目录下配置的规则打包到jar文件中
9、使用扩展方法命令：groovy -classpath ExtensionTest.jar ExtensionMethodTest.groovy  //-classpath ExtensionTest.jar 表示运行FindPrice2.groovy文件，需要使用jar文件中的方法

容易出错的点：
定义的扩展方法中的代码有问题
将代码放在多级目录下（未验证）
项目创建时选择android类型、或java类型等

代码推送到远程仓库步骤：
1、在本地创建了一个工程，并添加了若干代码，配置好忽略文件
2、先进行本地化保存（前提是安装了git软件，并将git安装路径添加到环境变量），命令如下：
git init   // 初始化版本库
git add .   // 添加文件到版本库（只是添加到缓存区），.代表添加文件夹下所有文件
git commit -m "first commit" // 把添加的文件提交到版本库，并填写提交备注
3、在github上创建一个新的仓库
4、将本地代码推送到远程仓库的命令如下：
git remote add origin 你的远程库地址  // 把本地库与远程库关联
git push -u origin master    // 第一次推送时
git push origin master  // 第一次推送后，直接使用该命令即可推送修改

