package com.epita.assistants.ping.Class;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class ProjectConfiguration {
    public Path configFile;
    public ArrayList<String> compilationArguments;
    public ArrayList<String> executionArguments;
    public String executionMainClass;
    public String executionPath;

    public ProjectConfiguration(Path configFile){
        this.configFile = configFile;
        parseConfig();
    }

    public ProjectConfiguration()
    {
        configFile = null;
        compilationArguments = new ArrayList<>();
        executionArguments = new ArrayList<>();
        executionMainClass = "";
        executionPath = "";
    }

    public void parseConfig(){
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(configFile.toFile());
            doc.getDocumentElement().normalize();

            // Compilation

            Element compilationSettings = (Element) doc.getElementsByTagName("compilation").item(0);
            //Arguments
            compilationArguments = new ArrayList<>();
            NodeList compilationSettingsArguments = compilationSettings.getElementsByTagName("argument");
            for (int i = 0; i < compilationSettingsArguments.getLength(); i++) {
                compilationArguments.add(compilationSettingsArguments.item(i).getTextContent());
            }

            // Execution

            Element executionSettings = (Element) doc.getElementsByTagName("execution").item(0);
            //Arguments
            executionArguments = new ArrayList<>();
            NodeList executionSettingsArguments = executionSettings.getElementsByTagName("argument");
            for (int i = 0; i < executionSettingsArguments.getLength(); i++){
                executionArguments.add(executionSettingsArguments.item(i).getTextContent());
            }
            //MainClass
            executionMainClass = executionSettings.getElementsByTagName("mainClass").item(0).getTextContent();
            //Path
            executionPath = executionSettings.getElementsByTagName("path").item(0).getTextContent();


        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
