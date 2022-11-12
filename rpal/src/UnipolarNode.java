
public abstract class UnipolarNode extends InternalNode {
    public UnipolarNode(Node parent) {
        super(parent);
    }
    public Node getChild() {
        return children.get(0);
    }
}
abstract class UnaryOpNode extends UnipolarNode {
    public UnaryOpNode(Node parent) {
        super(parent);
    }
}
abstract class BinaryOpNode extends BipolarNode {
    public BinaryOpNode(Node parent) {
        super(parent);
    }
}
class Negative extends UnaryOpNode  implements Operator{
    public Negative(Node parent) {
        super(parent);
        type = "neg";
    }
}
class Not extends UnaryOpNode implements Operator{
    public Not(Node parent) {
        super(parent);
        type = "not";
    }
}
