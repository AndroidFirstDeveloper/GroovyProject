class PriceExtension {
    public static double getPrice2(String self) {
        /*def url = "http://ichart.finance.yahoo.com/table.csv?s=$self".toURL()
        println("url2=${url.toString()}")
        def data = url.readLines()[1].split(",")
        println("price2=${data[-1]}")
        Double.parseDouble(data[-1])*/
        try{
            self.toDouble()
        }catch (ex){
            println(ex)
            100.0f
        }
    }
}
