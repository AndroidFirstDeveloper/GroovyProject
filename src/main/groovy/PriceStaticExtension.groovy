class PriceStaticExtension {
    public static double getPrice1(String self, String ticker) {
        /*def url = "http://ichart.finance.yahoo.com/table.csv?s=$ticker".toURL()
        println("url1=${url.toString()}")
        def data = url.readLines()[1].split(",")
        println("price1=${data[-1]}")
        Double.parseDouble(data[-1])*/
        try{
            ticker.toDouble()
        }catch (ex){
            println(ex)
            100.0f
        }
    }
}
