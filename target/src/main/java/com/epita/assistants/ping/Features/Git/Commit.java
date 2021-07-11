package com.epita.assistants.ping.Features.Git;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.Arrays;

public class Commit extends FeatureClass {
    org.eclipse.jgit.api.Git gitObject;

    public Commit(org.eclipse.jgit.api.Git gitObject)
    {
        super(Mandatory.Features.Git.COMMIT);
        this.gitObject = gitObject;
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {

        String[] paramsStringArray = Arrays.copyOf(params, params.length, String[].class);
        StringBuilder paramsStringBuilder = new StringBuilder();
        for (var s : paramsStringArray) {
            paramsStringBuilder.append(s);
            paramsStringBuilder.append(' ');
        }
        String paramsString = new String(paramsStringBuilder);

        var initialRes = gitObject.commit().setMessage(paramsString);
        try {
            initialRes.call();
        } catch (GitAPIException e) {
            return new ExecutionReportClass(false);
        }
        return new ExecutionReportClass(true);
    }
}
