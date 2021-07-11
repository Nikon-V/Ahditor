package com.epita.assistants.ping.Frontend.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuickActionPanel extends JPanel {

    JButton build;
    JButton run;


    public QuickActionPanel(ActionListener actionListener){
        build = new JButton("Build");
        run = new JButton("Run");
        setMaximumSize(new Dimension(20000, 20));
        setMinimumSize(new Dimension(-1, 20));
        build.addActionListener(actionListener);
        run.addActionListener(actionListener);
        add(build);
        add(run);
    }
}
