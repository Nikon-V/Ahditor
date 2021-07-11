package com.epita.assistants.ping.Class;

import fr.epita.assistants.myide.domain.entity.Node;

import javax.swing.tree.TreeNode;
import javax.validation.constraints.NotNull;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class NodeClass implements Node, TreeNode {

    protected Path nodePath;
    protected Types nodeType;
    protected ArrayList<Node> nodeChildren;
    protected Node nodeParent;

    //Constructors
    public NodeClass(Node nodeParent, Path node_path, Types node_type) {
        this.nodePath = node_path;
        this.nodeType = node_type;
        this.nodeChildren = new ArrayList<>();
        this.nodeParent = nodeParent;
    }

    public NodeClass(Node nodeParent, Path node_path, ArrayList<Node> node_children) {
        this.nodePath = node_path;
        this.nodeType = Types.FOLDER;
        this.nodeChildren = node_children;
        this.nodeParent = nodeParent;
    }

    //Methods
    @Override
    public Path getPath() {
        return nodePath;
    }

    @Override
    public Type getType() {
        return nodeType;
    }

    @Override
    public List<@NotNull Node> getChildren() {
        return nodeChildren;
    }

    @Override
    public boolean isFile() {
        return Node.super.isFile();
    }

    @Override
    public boolean isFolder() {
        return Node.super.isFolder();
    }

    public Node getNodeParent() {
        return nodeParent;
    }

    public void initiateDiscovery()
    {
        if (isFile())
            return;

        var files = nodePath.toFile().list();
        ArrayList<Path> filePaths = new ArrayList<>();

        if (files != null) {
            for (String fileName : files)
                filePaths.add(Path.of(nodePath.toString(), fileName));
        }

        for (var filePath : filePaths) {

            Node.Types childType = Types.FOLDER;
            if (filePath.toFile().isFile())
                childType = Types.FILE;

            nodeChildren.add(new NodeClass(this, filePath, childType));
            ((NodeClass) nodeChildren.get(nodeChildren.size()-1)).initiateDiscovery();
        }
    }

    @Override
    public TreeNode getChildAt(int childIndex) {
        return (TreeNode) nodeChildren.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return nodeChildren.size();
    }

    @Override
    public TreeNode getParent() {
        return (TreeNode) nodeParent;
    }

    @Override
    public int getIndex(TreeNode node) {
        int index;
        boolean found = false;
        for (index = 0; index < getChildCount(); index++) {
            if (getChildAt(index).equals(node)) {
                found = true;
                break;
            }
        }
        if (!found)
            return -1;
        return index;
    }

    @Override
    public boolean getAllowsChildren() {
        return isFolder();
    }

    @Override
    public boolean isLeaf() {
        return isFile();
    }

    @Override
    public Enumeration<? extends TreeNode> children() {
        Iterator<Node> iterator = nodeChildren.iterator();
        Enumeration<? extends TreeNode> enumeration = new Enumeration<TreeNode>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public TreeNode nextElement() {
                return (TreeNode) iterator.next();
            }
        };
        return enumeration;
    }

    @Override
    public String toString() {
        return nodePath.getFileName().toString();
    }
}
