import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.ToolTipManager;
import javax.swing.ImageIcon;
import javax.swing.Icon;

import java.net.URL;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Component;


public class TreeIconDemo2 extends JPanel 
                           implements TreeSelectionListener {
    public static Node eieizaa;
    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;

    public TreeIconDemo2() {
        super(new GridLayout(1,0));

        //Create the nodes.
        DefaultMutableTreeNode top = createNodes(eieizaa);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Set the icon for leaf nodes.
        ImageIcon tutorialIcon = createImageIcon("images/middle.gif");
        if (tutorialIcon != null) {
            DefaultTreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
            cellRenderer.setClosedIcon(tutorialIcon);
            cellRenderer.setOpenIcon(tutorialIcon);
            tree.setCellRenderer(cellRenderer);
        } else {
            System.err.println("Tutorial icon missing; using default.");
        }

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);

        //Create the HTML viewing pane.
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        JScrollPane htmlView = new JScrollPane(htmlPane);

        //Add the scroll panes to a split pane.
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setTopComponent(treeView);
        splitPane.setBottomComponent(htmlView);

        Dimension minimumSize = new Dimension(100, 50);
        htmlView.setMinimumSize(minimumSize);
        treeView.setMinimumSize(minimumSize);
        splitPane.setDividerLocation(100); //XXX: ignored in some releases
                                           //of Swing. bug 4101306
        //workaround for bug 4101306:
        //treeView.setPreferredSize(new Dimension(100, 100)); 

        splitPane.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(splitPane);
    }



    /** Required by TreeSelectionListener interface. */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                           tree.getLastSelectedPathComponent();

        Node info = (Node) node.getUserObject();
        String txt = Homework1.inorder(info);
        if (txt.length() != 1) txt = txt.substring(1,txt.length()-1) + "=" +Homework1.calculate(info);
        htmlPane.setText(txt);

    }

    private class BookInfo {
        public String bookName;
        public URL bookURL;

        public BookInfo(String book, String filename) {
            bookName = book;
            bookURL = TreeIconDemo2.class.getResource(filename);
            if (bookURL == null) {
                System.err.println("Couldn't find file: "
                                   + filename);
            }
        }

        public String toString() {
            return bookName;
        }
    }


    private void displayURL(URL url) {
        try {
            if (url != null) {
                htmlPane.setPage(url);
            } else { //null url
		htmlPane.setText("File Not Found");
                if (DEBUG) {
                    System.out.println("Attempted to display a null URL.");
                }
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }

    private DefaultMutableTreeNode createNodes(Node top)
    {
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(top);
                if (top.left != null)
                {
                    treeNode.add(createNodes(top.left));
                }
                 if (top.right != null)
                 {
                     treeNode.add(createNodes(top.right));
                 }
       return treeNode;

    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TreeIconDemo2.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Binary-3Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TreeIconDemo2 newContentPane = new TreeIconDemo2();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(Node eieigum) {
        eieizaa = eieigum;
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private class MyRenderer extends DefaultTreeCellRenderer {
        Icon tutorialIcon;

        public MyRenderer(Icon icon) {
            tutorialIcon = icon;
        }

        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);
            if (leaf && isTutorialBook(value)) {
                setIcon(tutorialIcon);
                setToolTipText("This book is in the Tutorial series.");
            } else {
                setToolTipText(null); //no tool tip
            }

            return this;
        }

        protected boolean isTutorialBook(Object value) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)value;
            BookInfo nodeInfo = 
                    (BookInfo)(node.getUserObject());
            String title = nodeInfo.bookName;
            if (title.indexOf("Tutorial") >= 0) {
                return true;
            } 

            return false;
        }
    }
}
