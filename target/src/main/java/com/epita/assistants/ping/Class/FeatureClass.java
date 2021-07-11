package com.epita.assistants.ping.Class;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;

public class FeatureClass implements Feature {

    protected Feature.Type featureType;

    public FeatureClass(Type feature_type) {
        this.featureType = feature_type;
    }

    //Methods
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        return null;
    }

    @Override
    public Type type() {
        return featureType;
    }
}
