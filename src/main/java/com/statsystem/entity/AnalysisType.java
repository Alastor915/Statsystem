package com.statsystem.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Типы анализа
 */
public enum AnalysisType implements Serializable {
    NEWTON("Интерполяция полиномом Ньютона", "/fxml/interpolation_tab.fxml"),
    SPLINE("Интерполяция сплайном", "/fxml/interpolation_tab.fxml"),
    LSM("Апроксимация методом наименьших квадратов", ""),
    EXPECTATION("Математическое ожидание", ""),
    VARIANCE("Дисперсия", ""),
    DISTRIBUTION("Функция распределение", ""),
    CORRELATION("Автокорреляционная функция", "");

    private final String name;
    private final String path;

    AnalysisType(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}




