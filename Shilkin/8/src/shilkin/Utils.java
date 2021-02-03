package shilkin;

import shilkin.annotations.XmlIgnore;
import shilkin.annotations.XmlName;
import shilkin.annotations.XmlType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public String toSerializeObject(Object object) throws IllegalAccessException {
        StringBuilder output = new StringBuilder();
        final Class<?> myClass = object.getClass();
        String typeName = getTypeName(myClass);
        output
                .append("<")
                .append(typeName)
                .append(">\n");
        for (Field declaredField : myClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(XmlIgnore.class)) {
                continue;
            }
            declaredField.setAccessible(true);
            String fieldName = getFieldName(declaredField);
            output
                    .append("<")
                    .append(fieldName)
                    .append(">")
                    .append(declaredField.get(object).toString())
                    .append("</")
                    .append(fieldName).append(">\n");
        }
        output
                .append("</")
                .append(typeName)
                .append(">");
        return output.toString();
    }

    private String getTypeName(Class<?> myClass) {
        final XmlType annotation = myClass.getAnnotation(XmlType.class);
        if (annotation != null) {
            return annotation.value();
        }
        return myClass.getSimpleName();
    }

    private String getFieldName(Field declaredField) {
        final XmlName annotation = declaredField.getAnnotation(XmlName.class);
        if (annotation != null) {
            return annotation.value();
        }
        return declaredField.getName();
    }

    public <T> T toDeserializeObject(String xmlObject, Class<T> myClass) throws IllegalAccessException, InstantiationException {
        xmlObject = xmlObject.replaceAll("[\n]", "");
        List<String> names = new ArrayList<>();
        StringBuilder builder = new StringBuilder(xmlObject);
        int startIndex = 0;
        int endIndex = 0;
        String name;
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '<' && builder.charAt(i + 1) != '/') {
                startIndex = i + 1;
            }
            if (builder.charAt(i) == '>') {
                endIndex = i;
            }
            if (startIndex != 0 && endIndex != 0 && startIndex < endIndex) {
                name = xmlObject.substring(startIndex, endIndex);
                names.add(name);
                startIndex = 0;
                endIndex = 0;
            }
        }
        names.remove(0);
        startIndex = 0;
        endIndex = 0;
        List<String> xmlFields = new ArrayList<>();
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '>') {
                startIndex = i + 1;
            }
            if (builder.charAt(i) == '<' && builder.charAt(i + 1) == '/') {
                endIndex = i;
            }
            if (endIndex != 0 && startIndex != 0 && startIndex < endIndex) {
                name = xmlObject.substring(startIndex, endIndex);
                xmlFields.add(name);
                startIndex = 0;
                endIndex = 0;
            }
        }
        Map<String, String> xmlLines = new HashMap<>();
        for (int i = 0; i < xmlFields.size(); i++) {
            xmlLines.put(names.get(i), xmlFields.get(i));
        }
        T object = myClass.newInstance();
        for (int i = 0; i < myClass.getDeclaredFields().length; i++) {
            Field field = myClass.getDeclaredFields()[i];
            field.setAccessible(true);
            if (field.isAnnotationPresent(XmlIgnore.class)) {
                continue;
            }
            if (field.getType() == String.class) {
                field.set(object, xmlLines.get(field.getAnnotation(XmlName.class).value()));
            } else {
                field.set(object, Integer.parseInt(xmlLines.get(field.getAnnotation(XmlName.class).value())));
            }
        }
        return object;
    }
}
