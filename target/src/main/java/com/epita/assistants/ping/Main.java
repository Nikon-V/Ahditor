package com.epita.assistants.ping;

import com.epita.assistants.ping.Class.MainConfig;
import com.epita.assistants.ping.Install.Install;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.service.FrontendService;
import fr.epita.assistants.myide.domain.service.ProjectService;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args){
        if (!Install.checkInstall())
            Install.correctInstall();

        MainConfig.init();
        if (args.length == 1)
            ProjectService.load(Path.of(args[0]));
        else if (args.length == 2)
            ProjectService.load(Path.of(args[0]), Path.of(args[1]));
        try{
            FrontendService.init();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
