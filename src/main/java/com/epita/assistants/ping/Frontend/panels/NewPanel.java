package com.epita.assistants.ping.Frontend.panels;

import com.epita.assistants.ping.Class.NodeClass;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.service.FrontendService;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewPanel extends JFrame {
    JButton create;
    JTextPane textarea;
    boolean isNewFile = false;
    String newFilePath;

    public NewPanel(TextPanel jTextPane, JTree tree){
        super("New file");
        create = new JButton("create");
        textarea = new JTextPane();
        this.add(create);
        this.add(textarea);

        this.getContentPane().add(BorderLayout.SOUTH, create);
        this.getContentPane().add(BorderLayout.NORTH, textarea);
        ActionListener actionListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String newFile = textarea.getText();
                //Path path = ProjectService.project.getRootNode().getPath();
                //String newPath = path.toAbsolutePath().toString();
                File file = null;

                //GETTING SELECTED PATH FROM THE TREE
                String selectedPathNode = "";
                if (!tree.isSelectionEmpty()) {
                    Object[] paths = tree.getSelectionPath().getPath();
                    for (int i = 0; i < paths.length; i++) {
                        selectedPathNode += paths[i];
                        if (i + 1 < paths.length) {
                            selectedPathNode += File.separator;
                        }
                    }
                    //file = new File(selectedPathNode);
                }
                else {
                    //String filepathfromroot = newPath + "/" + newFile;
                    //file = new File(filepathfromroot);

                    //Getting directory of the file
                    //String direcortyPath = "";
                    /*String token = "";
                    for (int i = 0; i < filepathfromroot.length(); i++){
                        if (filepathfromroot.charAt(i) == '/') {
                            direcortyPath += token + filepathfromroot.charAt(i);
                            token = "";
                        }
                        else
                            token+=filepathfromroot.charAt(i);
                    }*/
                    ///////
                    //var parentNode = NodeService.findNodeFromPath(Path.of(direcortyPath));

                    try {
                        Node node = NodeService.create(ProjectService.project.getRootNode(), newFile, Node.Types.FILE);
                        jTextPane.readFile(node);
                        FrontendService.updateTree();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
/*
                    if (file.createNewFile()) {
                        isNewFile = true;
                        newFilePath = newPath + "/" + newFile;

                        var nodeOptional = NodeService.findNodeFromPath(Path.of(newFilePath));
                        if (nodeOptional.isPresent()) {
                            try {
                                System.out.println("find node and opening it");
                                jTextPane.readFile(nodeOptional.get());
                            } catch (IOException ignored) {
                            }
                        } else {
                            return; // TODO send error message to user
                        }
                        System.out.println("New file path been added and boolean modified");
                    } else {
                        System.out.println("file not created");
                    }*/
                }
            }
        };
        create.addActionListener(actionListener);
    }

    String getNewfilePath(){
        if (isNewFile)
            return newFilePath;
        return null;
    }

    boolean getIsNewFile(){
        return isNewFile;
    }
}
