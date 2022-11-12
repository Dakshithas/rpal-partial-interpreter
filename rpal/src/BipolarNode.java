

public abstract class BipolarNode extends InternalNode {
    public BipolarNode(Node parent) {
        super(parent);
    }

    public Node getLeftChild(){
        return children.get(0);
    }

    public Node getRightChild(){
        return children.get(1);
    }
}

class Gamma extends BipolarNode {
    public Gamma(Node parent) {
        super(parent);
        type = "gamma";
    }
}
class Lambda extends BipolarNode {
    public Lambda(Node parent) {
        super(parent);
        type = "lambda";
    }
}
class Equal extends BipolarNode{
    public Equal(Node parent) {
        super(parent);
        type = "=";
    }
}
class Plus extends  BipolarNode implements Operator{
    public Plus(Node parent) {
        super(parent);
        type = "+";

    }
}
class Minus extends BipolarNode implements Operator{
    public Minus(Node parent) {
        super(parent);
        type = "-";
    }

}
class Slash extends  BipolarNode implements Operator {
    public Slash(Node parent) {
        super(parent);
        type = "/";
    }
}
class Star extends  BipolarNode implements Operator{
    public Star(Node parent) {
        super(parent);
        type = "*";
    }
}
class DoubleStar extends  BipolarNode implements Operator{
    public DoubleStar(Node parent) {
        super(parent);
        type = "**";
    }
}
class Greater extends BinaryOpNode implements Operator{
    public Greater(Node parent) {
        super(parent);
        type = "gr";
    }
}
class bAnd extends BinaryOpNode implements Operator{
    public bAnd(Node parent) {
        super(parent);
        type = "&";
    }
}
class bEqual extends BinaryOpNode implements Operator{
    public bEqual(Node parent) {
        super(parent);
        type = "eq";
    }

}
class GreatEqual extends BinaryOpNode implements Operator{
    public GreatEqual(Node parent) {
        super(parent);
        type = "ge";
    }
}
class LessEqual extends BinaryOpNode implements Operator{
    public LessEqual(Node parent) {
        super(parent);
        type = "le";
    }
}
class Lesser extends  BinaryOpNode implements Operator{
    public Lesser(Node parent) {
        super(parent);
        type = "ls";
    }
}
class NotEqual extends BinaryOpNode implements Operator{
    public NotEqual(Node parent) {
        super(parent);
        type = "ne";
    }
}

class Or extends BinaryOpNode implements Operator{
    public Or(Node parent) {
        super(parent);
        type = "or";
    }
}
class Aug extends BinaryOpNode implements Operator{
    public Aug(Node parent) {
        super(parent);
        type = "aug";
    }
}
