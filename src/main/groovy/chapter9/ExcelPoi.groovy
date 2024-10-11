package chapter9

import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFFont
import org.apache.poi.hssf.usermodel.HSSFRow
import org.apache.poi.hssf.usermodel.HSSFSheet
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.IndexedColors

/**
 * 此代码为Groovy代码
 * 利用apache poi生成excel文件的代码，使用该代码，需要下载apache的jar文件，复制文件到lib目录下（右键点击add external library）
 * 最开始下载的poi-4.0.0.jar，但是代码太老了，类找不到，所以下载了poi-3.12.jar，代码变成正常。
 * apache jar下载地址：https://repo1.maven.org/maven2/org/apache/poi/poi/
 * */
class ExcelPoi {

    /** Config your folder path here*/
    public static final targetFolderPath = "E:\\gworkspace\\"

    public static void main(String[] args) {
        //准备测试数据
        Map<String, String> excelContentMap = ["key1": "value1", "key2": "value2"]
        //创建Excel表格
        createExcel(excelContentMap)
    }
    /**
     * 创建Excel
     * @param excelContentMapList
     * @return
     */
    private static void createExcel(Map excelContentMap) {
//        def excelFile = new File(targetFolderPath + "ExcelTest_" + new Date().getTime() + ".xls")
        def excelFile = new File(targetFolderPath + "CodeGenerate.xls")//这里创建固定名称的文件，当文件打开的时候执行程序会停止，所以运行程序时不要打开文件
        if (excelFile.exists()) {
            def result = excelFile.delete()
            println(result)
        }
        def result = excelFile.createNewFile()
        println("$result xls file location:${excelFile.absolutePath}")

        String sheetName = "ExcelContentSheet"

        //创建Excel工作簿
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook()
        HSSFSheet excelSheet = hssfWorkbook.createSheet(sheetName)
        //设置默认列宽
        excelSheet.setDefaultColumnWidth(50)
        //行
        HSSFRow hssfRow = null
        //列
        HSSFCell hssfCell = null
        //创建表头 ， 此处以测试数据中的key作为表头
        if (excelContentMap?.size() > 0) {
            def excelHeader = excelContentMap?.keySet()
            hssfRow = excelSheet?.createRow(0)//创建行，参数是行的索引，默认从0开始
            excelHeader?.eachWithIndex { currentKey, currentIndex ->
                hssfCell = hssfRow?.createCell(currentIndex)
                hssfCell.setCellValue((String) currentKey)
                setHeaderCellStyle(hssfWorkbook, hssfCell)
            }
        }
        //创建表格内容，此处以测试数据的Value作为表格内容
        /**hssfRow = excelSheet?.createRow(1)
         excelContentMap?.eachWithIndex { currentCell, currentCellIndex ->
         hssfCell = hssfRow.createCell(currentCellIndex)
         hssfCell.setCellValue((String) currentCell.value)
         }*/
        for (i in 1..6) {
            hssfRow = excelSheet?.createRow(i)//创建行，参数是行的索引，从0开始计数，
            for (j in 0..3) {
                hssfCell = hssfRow.createCell(j)//创建单元格，参数是单元格所属列的索引，从0开始计数
                hssfCell.setCellValue(String.format("%s-%s", i, j))//设置单元格的内容，还可以设置单元格的样式下面（字体、背景、居中等）
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(excelFile)
        hssfWorkbook.write(fileOutputStream)
    }

    /**
     * 设置表头单元格样式
     * @param hssfWorkbook
     * @param hssfCell
     */
    private static void setHeaderCellStyle(HSSFWorkbook hssfWorkbook, HSSFCell hssfCell) {
        HSSFCellStyle hssfCellStyle = hssfWorkbook.createCellStyle()
        hssfCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND)
        hssfCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex())
        hssfCellStyle.setWrapText(true)
        HSSFFont hssfFont = hssfWorkbook.createFont()
        hssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)
        hssfCellStyle.setFont(hssfFont)
        hssfCell.setCellStyle(hssfCellStyle)
    }
}
