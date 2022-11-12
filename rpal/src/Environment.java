
import java.util.HashMap;

public class Environment extends Node {
    private HashMap<String,Node> values;
    private int id;
    public Environment(Environment environment,int id){
        super(environment);
        values = new HashMap<>();
        type = "envi";
        this.id =id;
    }

    public void addValue(String s, Node n){
        values.put(s,n);
    }

    public Node getValue(String s){
        return values.get(s);
    }

    public Node getdelta() {
        return super.getParent();
    }

    public boolean hasValue(String s){return values.containsKey(s);}

    public int getValueCount(){return values.size();}

    public int getId() {
        return id;
    }
    protected String print(){
        return "";
    }
    protected void print(StringBuilder buffer, String prefix, String childrenPrefix){}
}
