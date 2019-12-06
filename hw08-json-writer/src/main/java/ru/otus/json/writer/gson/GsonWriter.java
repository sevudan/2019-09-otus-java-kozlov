package ru.otus.json.writer.gson;

import java.util.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class GsonWriter {
   // private final Object object;
   // private final Class<?> clazz;

    public GsonWriter() {
      //  this.object = Objects.requireNonNull(object, "Object must not be NULL !");
        //this.clazz = object.getClass();
    }

    //Строим строку из массива.
    private StringBuilder arrayToGson(Object object) throws IllegalAccessException {
        var sb = new StringBuilder();
        for (int i = 0; i < Array.getLength(object); i++ ) {
            Object obj = Array.get(object, i);
            sb.append(gsonBuilder(obj));
        }
        return GsonStringBuild.arrayWraper(sb);
    }

    //Строим строку из коллекции.
    private StringBuilder collectionToGson(Object object) throws IllegalAccessException {
        Object[] collectionObj = ((Collection) object).toArray();
        return arrayToGson(collectionObj);
    }

    //Строим JSON строку.
    private StringBuilder gsonBuilder(Object object) throws IllegalAccessException {

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
                 sb.append(gsonBuilder(objectValue));
             }
        }
        return sb;
    }

    public String toGson(Object object) {
        object = Objects.requireNonNull(object, "Object must not be NULL !");
        StringBuilder jsonObject;
        try{
            jsonObject = GsonStringBuild.resultStringWraper(gsonBuilder(object));
        } catch (IllegalAccessException e) { throw new IllegalArgumentException();}
        return jsonObject.toString();
    }
}
