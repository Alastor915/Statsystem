package com.statsystem.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Типы анализа
 */
public enum AnalysisType implements Serializable {
    NEWTON(0,"Интерполяция полиномом Ньютона"),
    SPLINE(1,"Интерполяция сплайном"),
    LSM(2,"Апроксимация методом наименьших квадратов"),
    EXPECTATION(3,"Математическое ожидание"),
    VARIANCE(4,"Дисперсия"),
    DISTRIBUTION(5,"Функция распределение"),
    CORRELATION(6,"Автокорреляционная функция");

        private final long value;
        private final String name;
        private static Map<Long,String> pathes = new HashMap<>();

        private AnalysisType(long value, String name) {
            this.value = value; this.name = name;
        }

        static {
            pathes.put(0L,"/fxml/interpolation_tab.fxml");
            pathes.put(1L,"/fxml/interpolation_tab.fxml");
            pathes.put(2L,"");
            pathes.put(3L,"");
            pathes.put(4L,"");
            pathes.put(5L,"");
            pathes.put(6L,"");
        }

        public long getValue() {
            return value;
        }
        public String getName() {
        return name;
    }

        public static String getPath(long index){
            return pathes.get(index);
        }
}




