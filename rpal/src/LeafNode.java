
public abstract class LeafNode extends Node {
    public LeafNode(Node parent) {
        super(parent);
    }
    protected void print(StringBuilder buffer, String prefix, String childrenPrefix) {
        buffer.append(prefix);
        buffer.append(this.getType());
        buffer.append('\n');
    }
    public String print() {
        StringBuilder buffer = new StringBuilder(50);
        print(buffer, "", "");
        return buffer.toString();
    }
}

abstract class ValueNode extends LeafNode {
    private String value;
    public ValueNode(Node parent, String value) {
        super(parent);
        this.value = value;
    }
    public String getType(){
        return this.value;
    }
    public String getLeafValue(){
        return value;
    }
    public String toString(){
        return this.value;
    }

}
class NilNode extends LeafNode {
    public NilNode(Node parent) {
        super(parent);
        type = "<nil>";
    }
}
class DummyNode extends LeafNode {
    public DummyNode(Node parent) {
        super(parent);
        type = "dummy";
    }
}
class ParenthesisNode extends  LeafNode {
    public ParenthesisNode(Node parent) {
        super(parent);
        type = "()";
    }
}
class YStarNode extends LeafNode {
    public YStarNode(Node parent) {
        super(parent);
        type = "Y*";
    }
}

class INTnode extends ValueNode {
    private int val;
    public INTnode(Node parent, int value) {
        super(parent,String.valueOf(value));
        this.val = value;
        type = "<INT:>";
    }

    public int getInt() {
        return val;
    }

}
class BooleanNode extends  ValueNode {
    boolean val;
    public BooleanNode(Node parent,boolean b) {
        super(parent,String.valueOf(b));
        type = "boolean";
        val = b;
    }

    public boolean getBooleanValue() {
        return val;
    }
}
class IDnode extends ValueNode {
    private String Id;

    public IDnode(Node parent, String id) {
        super(parent,id);
        this.Id = id;
        type = "<ID:>";

    }

    public String getId() {
        return Id;
    }
}
class OPnode extends ValueNode {
    private String operater;
    public OPnode(Node parent,String op) {
        super(parent,op);
        type = "<OP:>";
        operater = op;
    }

    public String getOperater() {
        return operater;
    }
}
class STRnode extends ValueNode {
    private String value;
    public STRnode(Node parent, String value) {
        super(parent,value);
        this.value = value;
        type = "<STR:>";
    }

    public String getString() {
        return value;
    }

}