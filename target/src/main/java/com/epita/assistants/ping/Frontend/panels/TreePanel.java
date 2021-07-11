package com.epita.assistants.ping.Frontend.panels;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.FrontendService;
import fr.epita.assistants.myide.domain.service.ProjectService;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.tree.TreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TreePanel extends JScrollPane {

    JTree tree;
    boolean isTree = false;
    public TreePanel(Project project, TextPanel textPanel) {
        tree = new JTree((TreeNode) project.getRootNode());
        isTree = true;
        getViewport().add(tree);
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2){
                    try {
                        textPanel.readFile((Node) tree.getLastSelectedPathComponent());
                    } catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    public void update() {
        if (isTree){
            tree = new JTree((TreeNode) ProjectService.project.getRootNode());
            getViewport().remove(0);
            getViewport().add(tree);
            tree.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getClickCount() == 2){
                        try {
                            FrontendService.mainWindow.textPanel.readFile((Node) tree.getLastSelectedPathComponent());
                        } catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public JTree getTree() {
        return tree;
    }
}
