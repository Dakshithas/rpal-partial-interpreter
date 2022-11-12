
import java.util.ArrayList;

public class Root extends InternalNode{
    ArrayList<Node> children;
    public Root() {
        super(null);
        type = "Root";
    }
    @Override
    public int getLevel() {
        return -1;
    }
}