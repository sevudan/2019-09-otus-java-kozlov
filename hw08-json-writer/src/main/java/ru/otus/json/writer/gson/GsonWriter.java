package ru.otus.json.writer.gson;

import java.util.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GsonWriter {

    /**Строим строку из массива.**/
    private StringBuilder arrayToGson(Object object) throws IllegalAccessException {
        var sb = new StringBuilder();
        for (int i = 0; i < Array.getLength(object); i++ ) {
            Object obj = Array.get(object, i);
            sb.append(buildJsonString(obj));
        }
        return GsonStringBuild.arrayWraper(sb);
    }

    /**Строим строку из коллекции.**/
    private StringBuilder collectionToGson(Object object) throws IllegalAccessException {
        Object[] collectionObj = ((Collection) object).toArray();
        return arrayToGson(collectionObj);
    }

    private StringBuilder buildJsonString(Object object) throws IllegalAccessException {

        var sb = new StringBuilder();

        if (object == null) {
            return GsonStringBuild.objectStringBuild("null");
        }

        if (ChekTypeHelper.isPrimitive(object.getClass())) {
            return  GsonStringBuild.objectStringBuild(object);

        } else if(ChekTypeHelper.isBoolean(object.getClass())) {
            StringBuilder resultTrue = GsonStringBuild.objectStringBuild("true");
            StringBuilder resultFalse =  GsonStringBuild.objectStringBuild("false");

            return (boolean) object ? resultTrue : resultFalse;

        } else if(ChekTypeHelper.isString(object.getClass())){
            return GsonStringBuild.objectStringWraper(sb, object);

        } else if(ChekTypeHelper.isChar(object.getClass())) {
            return GsonStringBuild.objectStringWraper(sb, object);

        } else if(ChekTypeHelper.isArray(object.getClass())){
            return arrayToGson(object);

        } else if(object instanceof Collection) {
            return collectionToGson(object);

        }else{
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);

                if (Modifier.isTransient(field.getModifiers())) continue;

                GsonStringBuild.stringFieldWraper(sb, field.getName());
                Object objectValue = field.get(object);
                sb.append(buildJsonString(objectValue));
            }
        }
        return sb;
    }

    public String toGson(Object object) {
        StringBuilder resultObject;

        if (object == null) return "null";
        try{
            StringBuilder jsonObject = buildJsonString(object);
            Object obj2 = GsonStringBuild.clearSymbols(new StringBuilder(jsonObject));

            if (object.toString().equals(obj2.toString()) || object.getClass().equals(Character.class) ) {
                return GsonStringBuild.deletekLastChar(jsonObject).toString();
            }
            resultObject = GsonStringBuild.resultStringWraper(jsonObject);
        } catch (IllegalAccessException e) { throw new IllegalArgumentException();}

        return resultObject.toString();
    }
}
