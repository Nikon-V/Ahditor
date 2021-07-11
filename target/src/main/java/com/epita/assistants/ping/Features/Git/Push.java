package com.epita.assistants.ping.Features.Git;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.errors.GitAPIException;

public class Push extends FeatureClass {
    org.eclipse.jgit.api.Git gitObject;

    public Push(org.eclipse.jgit.api.Git gitObject)
    {
        super(Mandatory.Features.Git.PUSH);
        this.gitObject = gitObject;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Object... params) {
        var initialRes = gitObject.push();
        System.out.println("Pushing");
        try {
            initialRes.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return new ExecutionReportClass(false);
        }
        return new ExecutionReportClass(true);
    }
}
