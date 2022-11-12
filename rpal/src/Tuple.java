
import java.util.ArrayList;

public class Tuple extends Node {
    ArrayList<Node> tupleValues;
    public Tuple(Node parent) {
        super(parent);
        type = "tuple";
        tupleValues = new ArrayList<>();
    }

    public Node getValueAt(int m){
        // System.out.println(tupleValues);
        return tupleValues.get(m-1);
    }

    public void addValue(Node n){
        tupleValues.add(n);
    }

    public int getCount(){
        return tupleValues.size();
    }
    protected String print(){
        return "";
    }
    protected void print(StringBuilder buffer, String prefix, String childrenPrefix){}
}
