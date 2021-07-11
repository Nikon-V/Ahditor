package com.epita.assistants.ping.Frontend.panels;

import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.ProjectService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CommitPanel extends JFrame{
    JButton commitButton;;
    Project project;
    JTextPane textPane;

    public CommitPanel(Project project) {
        super("Commit Panel");
        this.project = project;
        commitButton  = new JButton("Commit");
        this.add(commitButton);
        textPane = new JTextPane();
        this.add(textPane);

        this.getContentPane().add(BorderLayout.NORTH, commitButton);
        this.getContentPane().add(BorderLayout.CENTER, textPane);
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                switch (command){
                    case "Commit" -> {
                        System.out.println("Commit button clicked");
                        var res = project.getFeature(Mandatory.Features.Git.COMMIT);
                        String commitMessage = textPane.getText();
                        if (res.isPresent() && !(commitMessage.isEmpty()))
                        {
                            res.get().execute(project, commitMessage);
                        }
                        else
                        {
                            System.err.println("ERR");
                        }
                    }
                }

            }
        };
        commitButton.addActionListener(actionListener);
    }

}
