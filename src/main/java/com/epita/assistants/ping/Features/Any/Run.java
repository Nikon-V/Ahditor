package com.epita.assistants.ping.Features.Any;

import com.epita.assistants.ping.Class.FeatureClass;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class Run extends FeatureClass {

    public Run() {
        super(Mandatory.Features.Any.RUN);
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        return super.execute(project, params);
    }
}
