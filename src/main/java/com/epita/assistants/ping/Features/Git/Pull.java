package com.epita.assistants.ping.Features.Git;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.errors.GitAPIException;

public class Pull extends FeatureClass {
    org.eclipse.jgit.api.Git gitObject;

    public Pull(org.eclipse.jgit.api.Git gitObject)
    {
        super(Mandatory.Features.Git.PULL);
        this.gitObject = gitObject;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        var initialRes = gitObject.pull();
        try {
            initialRes.call();
        } catch (GitAPIException e) {
            return new ExecutionReportClass(false);
        }
        return new ExecutionReportClass(true);
    }
}
