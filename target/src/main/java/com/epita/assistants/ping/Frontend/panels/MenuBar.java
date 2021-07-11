package com.epita.assistants.ping.Frontend.panels;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar {

    public MenuBar(ActionListener actionListener)
    {
        super();

        // Create amenu for menu
        JMenu m1 = new JMenu("File");
//        m1.setMnemonic(KeyEvent.VK_F);

        // Create menu items
        JMenuItem mi1 = new JMenuItem("New");
        KeyStroke keyStrokeToNew
                = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        mi1.setAccelerator(keyStrokeToNew);
        mi1.setMnemonic(KeyEvent.VK_N);

        JMenuItem mi5 = new JMenuItem("Configuration");
        KeyStroke keyStrokeToSettings
                = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);
        mi5.setAccelerator(keyStrokeToSettings);
        mi5.setMnemonic(KeyEvent.VK_L);

        JMenuItem mi2 = new JMenuItem("Open");
        KeyStroke keyStrokeToOpen
                = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        mi2.setAccelerator(keyStrokeToOpen);
        mi2.setMnemonic(KeyEvent.VK_O);

        JMenuItem mi3 = new JMenuItem("Save");
        KeyStroke keyStrokeToSave
                = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        mi3.setAccelerator(keyStrokeToSave);
        mi3.setMnemonic(KeyEvent.VK_S);

        mi1.addActionListener(actionListener);
        mi2.addActionListener(actionListener);
        mi3.addActionListener(actionListener);
        mi5.addActionListener(actionListener);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi5);
        this.add(m1);

        JMenu m2 = new JMenu("Edit");
        JMenuItem search = new JMenuItem("Search");
        KeyStroke searchKey
                = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        search.setAccelerator(searchKey);
        search.setMnemonic(KeyEvent.VK_F);
        search.addActionListener(actionListener);
        m2.add(search);





        JMenu m8 = new JMenu("Build");
        JMenuItem buildProject = new JMenuItem("Build");
        KeyStroke buildStroke
                = KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK);
        buildProject.setAccelerator(buildStroke);
        buildProject.setMnemonic(KeyEvent.VK_B);
        buildProject.addActionListener(actionListener);
        m8.add(buildProject);

        JMenu m9 = new JMenu("Run");
        JMenuItem runMain = new JMenuItem("Run");
        KeyStroke rmStroke
                = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
        runMain.setAccelerator(rmStroke);
        runMain.setMnemonic(KeyEvent.VK_R);
        runMain.addActionListener(actionListener);
        m9.add(runMain);


        JMenu m11 = new JMenu("Git");
        JMenuItem add = new JMenuItem("Add");
        KeyStroke addStroke
                = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK);
        add.setAccelerator(addStroke);
        add.setMnemonic(KeyEvent.VK_A);


        JMenuItem commit = new JMenuItem("Commit");
        KeyStroke commitStroke
                = KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK);
        commit.setAccelerator(commitStroke);
        commit.setMnemonic(KeyEvent.VK_M);


        JMenuItem push = new JMenuItem("Push");
        KeyStroke pushStroke
                = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
        push.setAccelerator(pushStroke);
        push.setMnemonic(KeyEvent.VK_P);

        add.addActionListener(actionListener);
        commit.addActionListener(actionListener);
        push.addActionListener(actionListener);
        m11.add(add);
        m11.add(commit);
        m11.add(push);



        this.add(m2);
        this.add(m8);
        this.add(m9);
        this.add(m11);
    }
}
