import java.util.Stack;

public class Homework1
{
	static Stack st = new Stack();

	public static void main (String[] args)
	{
		Node Lastnode = null;
		if (args.length == 0)
		{
			String input = "251-*32*+";

			for (int j = 0; j < input.length(); j++)
			{
				String value = input.substring(j,j+1);
				Node n = new Node(value,null,null);
				infix(n);

			}
			Lastnode = (Node)st.pop();
			String out = inorder(Lastnode);
			System.out.println(out.substring(1,out.length()-1) + "=" + calculate(Lastnode) );

		}

		TreeIconDemo2.main(Lastnode);

	}


	public static void infix(Node n)
	{
		if (n.node.matches("[0-9]"))
		{
			st.add(n);

		}
		else
			{
			n.right = (Node) st.pop();
			n.left = (Node) st.pop();
			st.add(n);
			}
	}

	public static String inorder(Node n)
	{
		String temp="";

		if(n.left != null)
		{
			temp+= "(" + inorder(n.left);
		}
		temp+=n.node;

		if(n.right != null)
		{
			temp+=inorder(n.right) + ")";
		}
		return temp ;
	}

	public static int calculate(Node n)
	{

		if (n.node.matches("[0-9]"))
		{
			return Integer.parseInt(n.node);
		}

		int value = 0;
		int right = calculate(n.right);
		int left = calculate(n.left);

		if(n.node.equals("+"))
		{
			value = left+right;
		}
		else if (n.node.equals("-"))
		{
			value = left-right;
		}
		else if (n.node.equals("*"))
		{
			value = left*right;
		}
		else if (n.node.equals("/"))
		{
			value = left/right;
		}
		return value;
	}
}