package com.epita.assistants.ping.Features.Maven;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.IOException;

public class Clean extends FeatureClass {

    public Clean() {
        super(Mandatory.Features.Maven.CLEAN);
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Object... params) {
        var mvn = new ProcessBuilder();
        mvn.command("mvn","clean");

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
