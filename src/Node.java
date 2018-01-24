public class Node {
    public Node left ;
    public Node right ;
    public String node;
    Node(String no,Node le,Node ri)
    {
        node = no;
        left = le;
        right = ri;
    }
    public String toString(){
        return node;
    }

}