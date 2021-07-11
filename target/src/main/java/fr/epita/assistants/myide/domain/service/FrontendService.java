package fr.epita.assistants.myide.domain.service;

import com.epita.assistants.ping.Frontend.Utilities.Theme;
import com.epita.assistants.ping.Frontend.panels.MainWindow;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public abstract class FrontendService {
    public static MainWindow mainWindow = null;

    public static void init() throws IOException {
        mainWindow = new MainWindow(ProjectService.project);
    }

    public static boolean changeTheme(Theme theme)
    {
        try {
            theme.setTheme();
            SwingUtilities.updateComponentTreeUI(mainWindow.frame);
            mainWindow.frame.pack();
        } catch (Exception e)
        {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    };

    public static void updateTree() { mainWindow.treePanel.update();}

    public static void updateImage(){
        mainWindow.imagePanel.repaint();
    }

    public static void changeBackground(File ImageFile){
        mainWindow.imagePanel.setImageToImagePanel(ImageFile);
    }
}
