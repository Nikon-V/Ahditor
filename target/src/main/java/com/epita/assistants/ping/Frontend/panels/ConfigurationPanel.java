package com.epita.assistants.ping.Frontend.panels;

import com.epita.assistants.ping.Class.MainConfig;
import com.epita.assistants.ping.Frontend.Utilities.Theme;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import fr.epita.assistants.myide.domain.service.FrontendService;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class ConfigurationPanel extends JFrame {

    public ConfigurationPanel(){
        super("Configuration Panel");
        JButton applyButton = new JButton("apply");
        applyButton.addActionListener(e -> MainConfig.updateConfig());
        applyButton.setAlignmentX(Component.LEFT_ALIGNMENT);


        String[] ThemeSettings = { "Dracula", "Light", "Dark", "IntelliJ"};
        JComboBox<String> scrollDownBarTheme = new JComboBox<>(ThemeSettings);
        switch (MainConfig.theme.name){
            case "Dracula" -> scrollDownBarTheme.setSelectedIndex(0);
            case "Light" -> scrollDownBarTheme.setSelectedIndex(1);
            case "Dark" -> scrollDownBarTheme.setSelectedIndex(2);
            case "IntelliJ" -> scrollDownBarTheme.setSelectedIndex(3);
        }
        scrollDownBarTheme.addActionListener(e -> {
            switch ((String) scrollDownBarTheme.getSelectedItem()) {
                case "Dracula" -> {
                    Theme theme = new Theme("Dracula", new FlatDarculaLaf());
                    if (FrontendService.changeTheme(theme))
                        MainConfig.theme = theme;
                }
                case "Light" -> {
                    Theme theme = new Theme("Light", new FlatLightLaf());
                    if (FrontendService.changeTheme(theme))
                        MainConfig.theme = theme;
                }
                case "Dark" -> {
                    Theme theme = new Theme("Dark", new FlatDarkLaf());
                    if (FrontendService.changeTheme(theme))
                        MainConfig.theme = theme;
                }
                case "IntelliJ" -> {
                    Theme theme = new Theme("IntelliJ", new FlatIntelliJLaf());
                    if (FrontendService.changeTheme(theme))
                        MainConfig.theme = theme;
                }
            }
        });
        scrollDownBarTheme.setMaximumSize(new Dimension(100, 30));
        scrollDownBarTheme.setAlignmentX(Component.LEFT_ALIGNMENT);


        JButton backgroundButton = new JButton("Change Background");
        backgroundButton.addActionListener(e -> {
            JFileChooser JFCBackgroundSetting = new JFileChooser("f:");
            int r = JFCBackgroundSetting.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                Path filepath = JFCBackgroundSetting.getSelectedFile().toPath();
                FrontendService.changeBackground(filepath.toFile());
                MainConfig.imagePath = filepath;
            }
        });
        backgroundButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton removeBackgroundButton = new JButton("Remove Background");
        removeBackgroundButton.addActionListener(e -> {
            MainConfig.imagePath = null;
            FrontendService.changeBackground(null);
        });
        removeBackgroundButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JColorChooser colorChooser = new JColorChooser();
        colorChooser.setColor(MainConfig.fading);
        colorChooser.setVisible(false);
        colorChooser.getSelectionModel().addChangeListener(e -> {
            MainConfig.fading = colorChooser.getColor();
            FrontendService.updateImage();
        });

        JButton colorChooserButton = new JButton("Hide/Show fading color chooser");
        colorChooserButton.addActionListener(e -> colorChooser.setVisible(!colorChooser.isVisible()));
        colorChooserButton.setAlignmentX(Component.LEFT_ALIGNMENT);



        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel padding = new JPanel();
        this.add(scrollDownBarTheme);
        this.add(backgroundButton);
        this.add(removeBackgroundButton);
        this.add(colorChooserButton);
        this.add(colorChooser);
        this.add(padding);
        this.add(applyButton);
        this.setResizable(false);

    }
}
