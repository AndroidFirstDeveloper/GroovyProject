package chapter18

class TwoFileUser {
    def useFiles(str) {
        def file1 = new FileWriter("E:\\gworkspace\\GroovyProject\\src\\main\\output1.txt")//这里会自动创建文件，不需要我们提前创建文件
        def file2 = new FileWriter("E:\\gworkspace\\GroovyProject\\src\\main\\output2.txt")//这里会自动创建文件，不需要我们提前创建文件
        file1.write(str)
        file2.write(str.size())
        file1.close()
        file2.close()
    }
}
