/**
 必须要将该文件放到代码根目录下，并且不能有class类名包裹，否则会报错：AST not available for closure: chapter9.MysqlTest$_main_closure5
 */
static void main(args) {
//        try {
    //连接到数据库
    def driverName = "com.mysql.jdbc.Driver"
//    def driverName = "com.mysql.cj.jdbc.Driver"
    def sql = groovy.sql.Sql.newInstance("jdbc:mysql://localhost:3306/weatherinfo", "root", "123456", driverName)
    println(sql.connection.catalog)
    //查询数据库
    sql.eachRow("select * from weather") {
        printf "%-20s%s\n", it.city, it[1]
    }
    //
    println("")
    def processMeta = { metaData ->
        metaData.columnCount.times { i -> printf "%-21s", metaData.getColumnLabel(i + 1)
        }
        println("")
    }
    sql.eachRow("select * from weather", processMeta) {
        printf "%-20s %s\n", it.city, it[1]
    }
    //查询行数
    println("")
    def rows = sql.rows("select * from weather")
    println("weather info availiable for ${rows.size()} cities")
    println("")
    //将数据转换为xml表示
    def bldr = new groovy.xml.MarkupBuilder()
    bldr.weather {
        sql.eachRow("select * from weather") {
            city(name: it.city, temperature: it.temperature)
        }
    }
    //dataset 过滤查询数据
    println("")
    def dataSet = sql.dataSet('weather')
    def citiesBelowFreezing = dataSet.findAll { it -> it.temperature < 32 }
    println(citiesBelowFreezing)
    println("Cities below freezing")
    citiesBelowFreezing.each {
        it -> println it.city
    }
    //dataset添加数据
    println("")
    println("Number of cities:" + sql.rows("select * from weather").size())
    dataSet.add(city: 'Denver', temperature: 19)
    println("Number of cities:" + sql.rows("select * from weather").size())
    //execute executeInsert添加数据
    println("")
    def temperature = 50
    sql.executeInsert("""insert into weather(city,temperature)
                            values('Oklahoma City',${temperature})
                        """)
//    println sql.firstRow("select temperature from weather where city='Oklahoma City'")

    //操作excel文件。读取excel的代码无法运行，jdbc:odbc使用的jdk版本不能大于1.7，
    /*println("")
    def excelSql = groovy.sql.Sql.newInstance("""jdbc:odbc:Driver=
        {Microsoft Excel Driver (*.xls,*.xlsx,*.xlsm,*.xlsb)};
        DBQ=E:\\gworkspace\\GroovyProject\\src\\main\\groovy\\chapter9\\weather.xlsx;READONLY=false""", "", "")
    println("City\t\tTemperature")
    excelSql.eachRow('select * from [temperatures$]') {//注意这里用单引号包裹，因为$是特殊符号，如果用用双引号包裹需要使用转义符\
        println("${it.city}\t\t${it.temperature}")
    }*/

//        }catch (ex){
//            println(ex)
//        }
}
