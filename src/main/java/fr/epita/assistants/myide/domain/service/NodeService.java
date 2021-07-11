package fr.epita.assistants.myide.domain.service;

import com.epita.assistants.ping.Class.NodeClass;
import fr.epita.assistants.myide.domain.entity.Node;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

public abstract class NodeService {

    /**
     * Update the content in the range [from, to[.
     *
     * @param node Node to update (must be a file).
     * @param from Beginning index of the text to update.
     * @param to Last index of the text to update (Not included).
     * @param insertedContent Content to insert.
     * @throws Exception upon update failure.
     * @return The node that has been updated.
     */
    public static Node update(final Node node, final int from, final int to, final byte[] insertedContent) throws Exception {

            if (!node.isFile())
                throw new Exception("Can't update into a directory kind person!");

            int length = Math.min(to - from, insertedContent.length);
            char[] copiedContent = new char[length];
            for (int i = 0; i < length; i++)
            {
                copiedContent[i] = (char) insertedContent[i];
            }

            File file = node.getPath().toFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));


            var fileContent = new StringBuilder();
            char[] cbuf = new char[4096];
            int ret;
            int left = from;
            while (left > 0 && (ret = reader.read(cbuf, 0, Math.min(left, 4095))) != -1) {
                left -= ret;
                cbuf[ret] = '\0';
                fileContent.append(cbuf, 0, ret);
            }


            fileContent.append(copiedContent);

            while ((ret = reader.read(cbuf, 0, 4095)) != -1) {
                cbuf[ret] = '\0';
                fileContent.append(cbuf, 0, ret);
            }
            reader.close();



            String endingString = fileContent.toString();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(endingString, 0, endingString.length());
            writer.close();

            return node;
    }

    /**
     * Delete the node given as parameter.
     *
     * @param node Node to remove.
     * @return True if the node has been deleted, false otherwise.
     */
    public static boolean delete(final Node node) {
        boolean ret;
        if (node.isFolder())
        {
            var children = node.getChildren();
            for (var c : children)
            {
                ret = delete(c);
                if (!ret)
                    return false;
            }
        }
        ret = node.getPath().toFile().delete();
        if (!ret)
            return false;
        var parent = ((NodeClass) node).getNodeParent();
        var siblings  = parent.getChildren();
        siblings.remove(node);
        return true;
    }

    /**
     * Create a new node.
     *
     * @param folder Parent node of the new node.
     * @param name Name of the new node.
     * @param type Type of the new node.
     * @throws Exception upon creation failure.
     * @return Node that has been created.
     */
    public static Node create(final Node folder, final String name, final Node.Type type) throws Exception {

            NodeClass newChildren = new NodeClass(folder, Path.of(folder.getPath().toString(), name) , (Node.Types) type);
            if (newChildren.isFile() && !newChildren.getPath().toFile().exists()){
                var ret = newChildren.getPath().toFile().createNewFile();
                if (!ret)
                    throw new RuntimeException("failed to create new file.");
            }
            else if (newChildren.isFolder() && !newChildren.getPath().toFile().exists()) {
                var ret = newChildren.getPath().toFile().mkdir();
                if (!ret)
                    throw new RuntimeException("failed to create new directory.");
            }
            folder.getChildren().add(newChildren);
            return newChildren;
    }

    /**
     * Move node from source to destination.
     *
     * @param nodeToMove Node to move.
     * @param destinationFolder Destination of the node.
     * @throws Exception upon move failure.
     * @return The node that has been moved.
     */
    public static Node move(final Node nodeToMove, final Node destinationFolder) throws Exception {
        NodeClass newNode = null;

            Path newPath = Path.of(destinationFolder.getPath().toString(), nodeToMove.getPath().getFileName().toString());
            if (!nodeToMove.getPath().toFile().renameTo(newPath.toFile())){
                throw new Exception("unable to move the node from the destination folder");
            }

            if (nodeToMove.isFolder()) {
                newNode = new NodeClass(destinationFolder, newPath, (ArrayList<Node>) nodeToMove.getChildren());
                destinationFolder.getChildren().add(newNode);
            }
            else if (nodeToMove.isFile()){
                newNode = new NodeClass(destinationFolder, newPath, (Node.Types) nodeToMove.getType());
                destinationFolder.getChildren().add(newNode);
            }
            delete(nodeToMove);
            return newNode;

    }

    public static Node override(Node fileNode, String data) throws Exception {
        if (fileNode.isFolder())
            return fileNode;
        File file = fileNode.getPath().toFile();
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(data, 0, data.length());
        writer.close();

        return fileNode;
    }

    public static Optional<Node> findNodeFromPath(Path path)
    {
        Node root = ProjectService.project.getRootNode();
        Path searchPath = path.toAbsolutePath();
        if (searchPath.startsWith(root.getPath().toAbsolutePath())) {
            while (!root.getPath().toAbsolutePath().toString().equals(path.toString())) {
                boolean found = false;
                for (var child : root.getChildren()) {
                    if (searchPath.startsWith(child.getPath().toAbsolutePath())) {
                        found = true;
                        root = child;
                        break;
                    }
                }
                if (!found)
                {
                    return Optional.empty();
                }
            }
            return Optional.of(root);
        }
        return Optional.empty();
    }

    public static Optional<Node> findClosestNodeFromPath(Path path)
    {
        Node root = ProjectService.project.getRootNode();
        Path searchPath = path.toAbsolutePath();
        if (searchPath.startsWith(root.getPath().toAbsolutePath())) {
            while (!root.getPath().toAbsolutePath().toString().equals(path.toString())) {
                boolean found = false;
                for (var child : root.getChildren()) {
                    if (searchPath.startsWith(child.getPath().toAbsolutePath())) {
                        found = true;
                        root = child;
                        break;
                    }
                }
                if (!found)
                {
                    return Optional.of(root);
                }
            }
            return Optional.of(root);
        }
        return Optional.empty();
    }


}
