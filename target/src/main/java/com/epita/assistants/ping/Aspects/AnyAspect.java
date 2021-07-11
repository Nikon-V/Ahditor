package com.epita.assistants.ping.Aspects;

import com.epita.assistants.ping.Class.AspectClass;
import com.epita.assistants.ping.Features.Any.Cleanup;
import com.epita.assistants.ping.Features.Any.Dist;
import com.epita.assistants.ping.Features.Any.Search;
import fr.epita.assistants.myide.domain.entity.Mandatory;

public class AnyAspect extends AspectClass {

    public AnyAspect() {
        super(Mandatory.Aspects.ANY);
        aspectFeatures.add(new Cleanup());
        aspectFeatures.add(new Dist());
        aspectFeatures.add(new Cleanup());
        aspectFeatures.add(new Search());
    }
}
