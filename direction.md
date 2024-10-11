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

mysql数据库命令：
net start mysql
net stop mysql
bin>mysql -uroot -p //启动mysql后，输入命令，命令行会提示用户输入密码
mysql>update user set authentication_string=password(“123456”) where user=“root”; //修改数据库密码
mysql>use mysql //进入数据库管理系统
mysql>exit  //退出登录
mysql>quit; //退出登录
mysql> create database db_test default character set utf8 collate utf8_general_ci; //新建一个数据库，名字是db_test，并设置编码格式，防止乱码
mysql>show databases; //显示所有数据库
//------------------------------------------使用数据库
mysql> use db_test;
Database changed
mysql>
mysql> source D:\Dev_Env\Mysql 5.7.35\run_sql\ry.sql    // source命令，后面接sql文件路径
//-------------------------------------------使用数据库
假如忘记了密码，重置密码步骤:
1、以管理员身份打开cmd窗口。
2、在上述窗口停止MySql服务运行：net stop mysql
3、重置密码，执行一下命令：mysqld --defaults-file="D:\Dev_Env\Mysql 5.7.35\mysql-5.7.35-winx64\mysql-5.7.35-winx64\my.ini" --skip-grant-tables
或者打开my.ini文件，在任意位置添加：skip-grant-tables
4、以管理员身份，新开一个cmd窗口， 执行登录命令(在新的一行提示输入密码，直接回车就行)：mysql -uroot -p
5、重新设置密码：mysql> update mysql.user set authentication_string=password("123456") where user='root';


groovy gradle项目添加jar文件步骤：
创建一个lib文件夹（跟src同级目录，已有的话不创建），将jar文件复制到lib文件夹下，
右键jar文件选择add external library，
在项目根目录下的build.gradle文件中添加：implementation fileTree(dir:'lib',includes:['*jar']) //不添加的话，还是用不了jar文件里的类,比如会报错：Caught: java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver
有时还会报错，需要把代码文件移动到代码根目录（比如src/main/groovy），还要去掉class类名的包裹（比如class A{static void main(args){xxxxxx}}），必须要移动位置+去掉外部class包裹，少一个都不行（亲自试验过） //不这样做有时报错：Exception in thread "main" groovy.lang.GroovyRuntimeException: DataSet unable to evaluate expression. AST not available for closure: chapter9.MysqlTest$_main_closure5. Is the source code on the classpath?

mysql-connector-java.jar下载地址：https://groovy.apache.org/download.html#distro ，打开网页，点击Groovy's artifactory instance (includes pre-ASF versions)
poi下载地址：https://repo1.maven.org/maven2/org/apache/poi/ ，打开网页，点击poi