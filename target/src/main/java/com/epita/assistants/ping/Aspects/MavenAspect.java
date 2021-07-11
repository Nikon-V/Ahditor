package com.epita.assistants.ping.Aspects;

import com.epita.assistants.ping.Class.AspectClass;
import com.epita.assistants.ping.Features.Maven.*;
import com.epita.assistants.ping.Features.Maven.Package;
import fr.epita.assistants.myide.domain.entity.Mandatory;

import java.io.IOException;

public class MavenAspect extends AspectClass {
    public MavenAspect(){
        super(Mandatory.Aspects.MAVEN);
        aspectFeatures.add(new Clean());
        aspectFeatures.add(new Compile());
        aspectFeatures.add(new Tree());
        aspectFeatures.add(new Test());
        aspectFeatures.add(new Package());
        aspectFeatures.add(new Install());
        aspectFeatures.add(new Exec());
    }
}
