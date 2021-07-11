package com.epita.assistants.ping.Class;

import com.epita.assistants.ping.Frontend.Utilities.Theme;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class MainConfig {
    public static Path mainConfigPath;
    public static Path imagePath;
    public static Theme theme;
    public static Color fading;

    public static void init(Path mainConfigPath){
        MainConfig.mainConfigPath = mainConfigPath;
        parseConfig();
    }

    public static void init() {
        System.setProperty("javax.xml.accessExternalDTD", "all");
        mainConfigPath = Path.of("~/.config/Ahditor/Ahditor.xml");
        if (mainConfigPath.startsWith("~" + File.separator)) {
            mainConfigPath = Path.of(System.getProperty("user.home") + mainConfigPath.toString().substring(1));
        }
        if (!mainConfigPath.toFile().exists())
        {
            System.err.println("Couldn't find IDE config File at: " + mainConfigPath.toString() + ", Using default values");
            defaultConfig();
            return;
        }
        parseConfig();
    }

    public static void defaultConfig(){
        imagePath = null;
        theme = new Theme("Dracula", new FlatDarculaLaf());
        fading = new Color(0, 0, 0, 0);
        try {
            theme.setTheme();
        } catch (Exception e){
            System.err.println("Couldn't set theme: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateConfig() {
        Document dom;
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();

            Element root = dom.createElement("configuration");

            // Image
            if (imagePath != null) {
                e = dom.createElement("image");
                e.appendChild(dom.createTextNode(imagePath.toString()));
                root.appendChild(e);
            }

            // Fading
            e = dom.createElement("fading");
            e.appendChild(dom.createTextNode(String.format("#%02X%02X%02X%02X", fading.getRed(), fading.getGreen(), fading.getBlue(), fading.getAlpha())));
            root.appendChild(e);

            // Theme
            e = dom.createElement("theme");
            e.appendChild(dom.createTextNode(theme.name));
            root.appendChild(e);

            dom.appendChild(root);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "Ahditor.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
                File mainConfigFile = mainConfigPath.toFile();
                if (!mainConfigFile.exists())
                    mainConfigFile.createNewFile();
                // send DOM to file
                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream(mainConfigFile)));

            } catch (TransformerException | IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }

    }

    public static void parseConfig(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(mainConfigPath.toFile());
            doc.getDocumentElement().normalize();

            // Image

            Element imageConfigElement = (Element) doc.getElementsByTagName("image").item(0);

            if (imageConfigElement == null) {
                imagePath = null;
            }
            else {
                Path imageConfigPath = Path.of(imageConfigElement.getTextContent());
                if (imageConfigPath.startsWith("~" + File.separator)) {
                    imageConfigPath = Path.of(System.getProperty("user.home") + imageConfigPath.toString().substring(1));
                }
                if (!imageConfigPath.toFile().exists()) {
                    imagePath = null;
                    System.err.println("Warning: Couldn't find file: " + imageConfigPath.toString());
                } else {
                    imagePath = imageConfigPath;
                }
            }

            // Fading
            // must be in format '#RRGGBBAA'
            fading = new Color(0, 0, 0, 0);
            Element fadingConfig = (Element) doc.getElementsByTagName("fading").item(0);
            try {
                if (fadingConfig != null) {
                    String fadingHex = fadingConfig.getTextContent();
                    int fadingInt = Integer.decode(fadingHex);
                    fading = new Color((fadingInt >> 24) & 0xFF, (fadingInt >> 16) & 0xFF, (fadingInt >> 8) & 0xFF, fadingInt & 0xFF);
                }
            } catch (NumberFormatException e){
                System.err.println("Malformed fading color config field, follow #RRGGBBAA");
                fading = new Color(0, 0, 0, 0);
            }


            // Theme
            // Can be:
            // Dracula
            // Light
            // Dark
            // IntelliJ
            Element themeConfig = (Element) doc.getElementsByTagName("theme").item(0);
            theme = new Theme("Dracula", new FlatDarculaLaf());
            if (themeConfig != null){
                switch (themeConfig.getTextContent()){
                    case "Dracula" -> {}
                    case "Light" -> theme = new Theme("Light", new FlatLightLaf());
                    case "Dark" -> theme = new Theme("Dark", new FlatDarkLaf());
                    case "IntelliJ" -> theme = new Theme("IntelliJ", new FlatIntelliJLaf());
                    default -> System.err.println("Theme can only be \"Dracula\",\"Light\",\"Dark\" or \"IntelliJ\"");
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.err.println("Error parsing config file, using default values");
            defaultConfig();
        }
        try {
            theme.setTheme();
        } catch (Exception e){
            System.err.println("Couldn't set theme: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
