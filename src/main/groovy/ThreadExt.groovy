//定义扩展方法的类
class ThreadExt {
    public static Thread hello(Thread self, Closure closure) {
        closure()
        return self
    }
}
