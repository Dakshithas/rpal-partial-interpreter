

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Delta extends Node {
    private Environment linkedEnv;
    private Stack<Node> valuestack;
    private ArrayList<Node> bindings;

    public Delta(Node parent) {
        super(parent);
        valuestack = new Stack<>();
        type = "delta";
        bindings = new ArrayList<>();
    }

    public void pushValue(Node n){
        valuestack.push(n);
    }

    public Node popValue(){
        return valuestack.pop();
    }

    public Node peekValue(){
        return valuestack.peek();
    }

    public Stack<Node> getValuestack() {
        return valuestack;
    }

    public Node getBindingAt(int index){
        return bindings.get(index);
    }

    public void setBindings(Node newBinding) {
        bindings.add(newBinding);
    }

    public int getBindingcount(){
        return bindings.size();
    }

    public Environment getLinkedEnv() {
        return linkedEnv;
    }

    public void setLinkedEnv(Environment linkedEnv) {
        this.linkedEnv = linkedEnv;
    }
    protected String print(){
        return "";
    }
    protected void print(StringBuilder buffer, String prefix, String childrenPrefix){};
    
    public String toString(){
        List<String> str=new ArrayList<>();
        for(Node node:valuestack){
            str.add(node.getType());
        }
        return String.join("|", str);
    }
}
