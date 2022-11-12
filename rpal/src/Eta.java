

public class Eta extends  Node {
    private Delta linkedDelta;
    public Eta(Node parent, Delta del) {
        super(parent);
        linkedDelta = del;
        type = "Eta";
    }

    public Delta getLinkedDelta() {
        return linkedDelta;
    }
    protected String print(){
        return "";
    }
    @Override
    protected void print(StringBuilder buffer, String prefix, String childrenPrefix){}
}
