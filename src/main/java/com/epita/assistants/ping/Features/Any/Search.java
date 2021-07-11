package com.epita.assistants.ping.Features.Any;

import com.epita.assistants.ping.Class.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class Search extends FeatureClass {
    public Search() {
        super(Mandatory.Features.Any.SEARCH);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        // TODO
        return super.execute(project, params);
    }
}
