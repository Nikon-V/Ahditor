package com.epita.assistants.ping.Features;

import fr.epita.assistants.myide.domain.entity.Feature;

public class ExecutionReportClass implements Feature.ExecutionReport {

    private final boolean _isSuccess;

    public ExecutionReportClass(boolean isSuccess) {
        this._isSuccess = isSuccess;
    }

    @Override
    public boolean isSuccess() {
        return _isSuccess;
    }
}
