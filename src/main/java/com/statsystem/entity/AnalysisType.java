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
    LSM("Апроксимация методом наименьших квадратов", "/fxml/lsm_tab.fxml"),
    EXPECTATION("Математическое ожидание", "/fxml/expected_value_tab.fxml"),
    VARIANCE("Дисперсия", "/fxml/dispersion_tab.fxml"),
    DISTRIBUTION("Функция распределения", "/fxml/func_distribution_tab.fxml"),
    CORRELATION("Автокорреляционная функция", "/fxml/correlation_tab.fxml");

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




