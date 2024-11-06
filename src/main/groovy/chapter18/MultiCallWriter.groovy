package chapter18

class MultiCallWriter {

    def someWriter() {
        def file = new FileWriter('E:\\gworkspace\\GroovyProject\\src\\main\\resources\\output1.txt')
        file.write('one')
        file.write('two')
        file.write(3)
        file.flush()
        file.write(file.getEncoding())
        file.close()
    }
}
