package com.epita.assistants.ping.Frontend.panels;

import com.epita.assistants.ping.Class.MainConfig;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.NodeService;
import fr.epita.assistants.myide.domain.service.ProjectService;


import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;


public class MainWindow implements ActionListener, ComponentListener {
    public JFrame frame;
    Project project;
    TextPanel textPanel;
    public ImagePanel imagePanel;
    QuickActionPanel quickActionPanel;
    public TreePanel treePanel;
    JSplitPane splitPaneLeft;
    public SearchPanel searchPanel;
    double firstDividerPosition = 1 / 3.0;
    Dimension oldSize;

    public MainWindow(Project project) throws IOException {
        this.project = project;



        frame = new JFrame("Ahditor");
        frame.addComponentListener(this);
        textPanel = new TextPanel();

        if (MainConfig.imagePath != null) {
            imagePanel = new ImagePanel(MainConfig.imagePath.toFile());
        }
        else {
            imagePanel = new ImagePanel(null);
        }
        imagePanel.add(textPanel);

        treePanel = new TreePanel(project, textPanel);
        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.PAGE_AXIS));
        searchPanel = new SearchPanel(textPanel);
        midPanel.add(searchPanel);
        midPanel.add(imagePanel);
        searchPanel.setVisible(false);
        splitPaneLeft = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePanel, midPanel);
        MenuBar menuBar = new MenuBar(this);
        frame.setJMenuBar(menuBar);

        quickActionPanel = new QuickActionPanel(this);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(quickActionPanel);
        mainPanel.add(splitPaneLeft);
        frame.setContentPane(mainPanel);

        frame.setSize(500,500);
        splitPaneLeft.setResizeWeight(0);
        splitPaneLeft.setDividerLocation((int) (frame.getSize().width * firstDividerPosition));
        oldSize = frame.getSize();
        frame.setVisible(true);

        frame.addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeactivated(WindowEvent e) {}
        });

    }

    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        switch (command) {
            case "New" -> {
                NewPanel newPanel = new NewPanel(textPanel, treePanel.getTree());
                newPanel.setSize(200,200);
                newPanel.setVisible(true);

            }
            case "Save" -> textPanel.saveFile();
            case "Open" -> {
                JFileChooser j = new JFileChooser(ProjectService.project.getRootNode().getPath().toString());
                int r = j.showOpenDialog(null);
                if (r == JFileChooser.APPROVE_OPTION) {
                    Path filepath = j.getSelectedFile().toPath();
                    var nodeOptional = NodeService.findNodeFromPath(filepath);
                    if (nodeOptional.isPresent()) {
                        try {
                            textPanel.readFile(nodeOptional.get());
                        } catch (IOException ignored) {}
                    } else {
                        return; // TODO send error message to user
                    }
                }
            }
            case "Search" -> {
                searchPanel.setVisible(!searchPanel.isVisible());
                searchPanel.textAreaRequestFocus();
            }
            case "Configuration" -> {
                ConfigurationPanel configurationPanel = new ConfigurationPanel();
                configurationPanel.setSize(700, 500);
                configurationPanel.setVisible(true);
            }
            case "Add" -> {
                var res = project.getFeature(Mandatory.Features.Git.ADD);
                if (res.isPresent()){
                    Path path = textPanel.nodeFile.getPath();
                    path = Path.of(path.toString().substring(project.getRootNode().getPath().toString().length() + 1));
                    res.get().execute(project, path.toString());
                    System.out.println("project added");
                }
                else
                    System.err.println("Feature PUSH Not Found");
            }
            case "Commit" -> {
                System.out.println("Commit activated!");
                CommitPanel commitPanel = new CommitPanel(project);
                commitPanel.setSize(400, 400);
                commitPanel.setVisible(true);
            }
            case "Push" -> {
                var res = project.getFeature(Mandatory.Features.Git.PUSH);
                if (res.isPresent()){
                    res.get().execute(project);
                    System.out.println("project pushed");
                }
                else
                    System.err.println("Feature PUSH Not Found");
            }
            case "Build" -> {
                Optional<Feature> feature = ProjectService.project.getFeature(Mandatory.Features.Maven.COMPILE);
                if (feature.isPresent()) {
                    Feature.ExecutionReport report = feature.get().execute(project);
                    if (!report.isSuccess())
                    {
                        System.out.println("Couldn't build");
                    }
                    else
                    {
                        System.out.println("Success");
                    }
                }
            }
            case "Run" -> {
                Optional<Feature> feature = ProjectService.project.getFeature(Mandatory.Features.Maven.EXEC);
                if (feature.isPresent()) {

                    Feature.ExecutionReport report = feature.get().execute(project);

                    if (!report.isSuccess())
                    {
                        System.out.println("Couldn't execute");
                    }
                    else
                    {
                        System.out.println("Success");
                    }
                }
            }

        }
    }



    @Override
    public void componentResized(ComponentEvent e) {
        imagePanel.updateImageFromMain(frame.getSize());

        splitPaneLeft.setDividerLocation((int) (frame.getSize().width * firstDividerPosition));

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
