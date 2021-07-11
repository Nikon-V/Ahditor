package com.epita.assistants.ping.Features.Maven;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.IOException;

public class Test extends FeatureClass {
    public Test() {
        super(Mandatory.Features.Maven.TEST);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        var mvn = new ProcessBuilder();
        mvn.directory(project.getRootNode().getPath().toFile());
        mvn.command("mvn", "test");

        try {
            var process = mvn.start();
            try{
                process.waitFor();
            } catch (InterruptedException e) {
                return new ExecutionReportClass(false);
            }
        } catch (IOException e){
            return new ExecutionReportClass(false);
        }

        return new ExecutionReportClass(true);
    }
}
