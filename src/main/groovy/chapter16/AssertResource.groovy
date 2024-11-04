package chapter16

@EAM
class AssertResource {

    public static void main(String[] args) {
        AssertResource.myAsset {
            read()
            write()
        }
    }

    private def open() {
        println("open...")
    }

    private def close() {
        println("close...")
    }

    def read() {
        println("read...")
    }

    def write() {
        println("write")
    }
}
