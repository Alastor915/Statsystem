package com.statsystem.entity;

import java.io.Serializable;

/**
 * Типы анализа
 */
public enum AnalysisType implements Serializable {
    NEWTON, SPLINE, LSM, EXPECTATION, VARIANCE, DISTRIBUTION
}