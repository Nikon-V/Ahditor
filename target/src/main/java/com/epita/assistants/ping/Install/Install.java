package com.epita.assistants.ping.Install;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Install {
    public static boolean checkInstall(){
        Path configDir = Path.of(System.getProperty("user.home") + "/.config/Ahditor/");
        if (!configDir.toFile().exists())
            return false;
        Path configDtd = Path.of(configDir.toString() + "/Ahditor.dtd");
        if (!configDtd.toFile().exists())
            return false;
        Path projectConfigDtd = Path.of(configDir.toString() + "/Ahditor_projectconfig.dtd");
        if (!projectConfigDtd.toFile().exists())
            return false;
        Path config = Path.of(configDir.toString() + "/Ahditor.xml");
        return config.toFile().exists();
    }

    public static void correctInstall(){
        System.out.println("Writing Configuration Files");
        Path configDir = Path.of(System.getProperty("user.home") + "/.config/Ahditor/");
        configDir.toFile().mkdirs();
        String stringConfigDtd = """
                <?xml encoding="UTF-8"?>

                        <!ELEMENT configuration (image?,fading,theme)>
                        <!ATTLIST configuration
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT image (#PCDATA)>
                        <!ATTLIST image
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT fading (#PCDATA)>
                        <!ATTLIST fading
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT theme (#PCDATA)>
                        <!ATTLIST theme
                                xmlns CDATA #FIXED ''>""";
        Path configDtd = Path.of(configDir.toString() + "/Ahditor.dtd");
        writeString(stringConfigDtd, configDtd);

        String stringProjectConfigDtd = """
                <?xml encoding="UTF-8"?>

                        <!ELEMENT configuration (compilation,execution)>
                        <!ATTLIST configuration
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT compilation (argument*)>
                        <!ATTLIST compilation
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT execution (argument*,mainClass,path)>
                        <!ATTLIST execution
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT mainClass (#PCDATA)>
                        <!ATTLIST mainClass
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT path (#PCDATA)>
                        <!ATTLIST path
                                xmlns CDATA #FIXED ''>

                        <!ELEMENT argument (#PCDATA)>
                        <!ATTLIST argument
                                xmlns CDATA #FIXED ''>""";

        Path projectConfigDtd = Path.of(configDir.toString() + "/Ahditor_projectconfig.dtd");
        writeString(stringProjectConfigDtd, projectConfigDtd);

        String stringConfig = """
                <configuration>
                    <fading>#0000007F</fading>
                    <theme>Dracula</theme>
                </configuration>""";
        Path config = Path.of(configDir.toString() + "/Ahditor.xml");
        writeString(stringConfig, config);

    }

    private static void writeString(String ahditorConfigDtd, Path projectConfigDtd){
        File projectConfigDtdFile = projectConfigDtd.toFile();
        if (!projectConfigDtdFile.exists())
        {
            try {
                projectConfigDtdFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Couldn't create file " + projectConfigDtd.toString());
            }
        }
        try {
            FileWriter projectConfigDtdFileW = new FileWriter(projectConfigDtdFile);
            projectConfigDtdFileW.write(ahditorConfigDtd);
            projectConfigDtdFileW.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldn't write into " + projectConfigDtd.toString());
        }
    }
}
