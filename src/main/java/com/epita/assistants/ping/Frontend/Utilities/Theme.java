package com.epita.assistants.ping.Frontend.Utilities;

import javax.swing.*;

public class Theme {

    boolean isString;
    String strTheme;
    public String name;
    LookAndFeel theme;

    public Theme(String name, LookAndFeel theme)
    {
        this.name = name;
        this.theme = theme;
        isString = false;
    }

    public Theme(String name, String strTheme)
    {
        this.name = name;
        this.strTheme = strTheme;
        isString = true;
    }

    public void setTheme() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        if (isString)
            UIManager.setLookAndFeel(strTheme);
        else
            UIManager.setLookAndFeel(theme);
    }
}
