package com.epita.assistants.ping.Class;

import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.util.ArrayList;
import java.util.List;

public class AspectClass implements Aspect {

    protected Mandatory.Aspects aspect;
    protected ArrayList<Feature> aspectFeatures;

    //Constructor
    public AspectClass(Mandatory.Aspects aspect) {
        this.aspect = aspect;
        this.aspectFeatures = new ArrayList<>();
        //TODO
    }

    //Methods
    @Override
    public Type getType() {
        return aspect;
    }

    @Override
    public List<Feature> getFeatureList() {
        return aspectFeatures;
    }
}
