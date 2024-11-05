package chapter17

class MyBuilderFactory {

    public static void main(String[] args) {
        def builder = new RobotBuilder()
        def robot = builder.robot('iRobot') {
            forward(dist: 20)
            left(rotation: 90)
            forward(speed: 10, duration: 5)
        }
        robot.go()
    }
}

class RobotBuilder extends FactoryBuilderSupport {
    {
        registerFactory('robot', new RobotFactory())
        registerFactory('forward', new ForwardMoveFactory())
        registerFactory('left', new LeftTurnFactory())
    }
}

class Robot {
    private def name
    def movements = []

    def go() {
        println("robot $name operating...")
        movements.each {
            println(it)
        }
    }
}

class ForwardMove {
    def dist

    String toString() {
        "move distance...$dist"
    }
}

class LeftTurn {
    def rotation

    String toString() {
        "turn left ... $rotation degrees"
    }
}

class RobotFactory extends AbstractFactory {

    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new Robot(name: o1)
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        println("child name=${child.class}")
        parent.movements << child
    }
}

class ForwardMoveFactory extends AbstractFactory {

    @Override
    boolean isLeaf() {
        return true
    }

    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new ForwardMove()
    }

    @Override
    boolean onHandleNodeAttributes(FactoryBuilderSupport builder, Object node, Map attributes) {
        if (attributes.speed && attributes.duration) {
            node.dist = attributes.speed * attributes.duration
            attributes.remove('speed')
            attributes.remove('duration')
        }
        true
    }
}

class LeftTurnFactory extends AbstractFactory {
    @Override
    boolean isLeaf() {
        return true
    }

    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new LeftTurn()
    }
}

