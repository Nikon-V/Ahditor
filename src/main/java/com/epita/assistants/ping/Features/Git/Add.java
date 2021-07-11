package com.epita.assistants.ping.Features.Git;

import com.epita.assistants.ping.Class.FeatureClass;
import com.epita.assistants.ping.Features.ExecutionReportClass;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.util.Arrays;

public class Add extends FeatureClass {
    org.eclipse.jgit.api.Git gitObject;

    public Add(Git gitObject) {
        super(Mandatory.Features.Git.ADD);
        this.gitObject = gitObject;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Object... params) {
        String[] paramsStringArray = Arrays.copyOf(params, params.length, String[].class);
        StringBuilder paramsStringBuilder = new StringBuilder();
        for (var s : paramsStringArray) {
            paramsStringBuilder.append(s);
            //paramsStringBuilder.append(' ');
        }

        String paramsString = new String(paramsStringBuilder);
        System.out.println(paramsString);
        var initialRes = gitObject.add().addFilepattern(paramsString);

        try {
            initialRes.call();
        } catch (GitAPIException e) {
            return new ExecutionReportClass(false);
        }
        return new ExecutionReportClass(true);
    }
}
