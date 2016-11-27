package com.statsystem.entity;

public class Type {
    AnalysisType type;

    public Type(AnalysisType type) {
        this.type = type;
    }

    public String getTypeName() {
        String name;
        switch (type) {
            case NEWTON:
                name = "";
                break;

            case SPLINE:
                name = "";
                break;

            case LSM:
                name = "";
                break;

            case EXPECTATION:
                name = "";
                break;

            case VARIANCE:
                name = "";
                break;

            case DISTRIBUTION:
                name = "";
                break;

            default:
                name = "";
                break;
        }
        return name;
    }
}
