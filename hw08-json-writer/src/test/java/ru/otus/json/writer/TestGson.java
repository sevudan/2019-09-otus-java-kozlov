package ru.otus.json.writer;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.otus.json.writer.gson.GsonWriter;
import ru.otus.json.writer.testobjects.TestPrimitive;
import ru.otus.json.writer.testobjects.TestWithArrays;

public class TestGson {

    @Test
    public void testObjectWithPrimitive(){
        Gson gson = new Gson();
        var gsonBuilder = new GsonWriter();
        var testInstance = new TestPrimitive();

        String myGson = gsonBuilder.toGson(testInstance);
        String targetJson = gson.toJson(testInstance);

        assertTrue(targetJson.equals(myGson));
    }

    @Test
    public void testObjectWithArrayAndCollections(){
        Gson gson = new Gson();
        var gsonBuilder = new GsonWriter();
        var testInstance = new TestWithArrays();
        testInstance.addStringCollection("Hello ");
        testInstance.addStringCollection("everybody");
        testInstance.addStringCollection("!");
        testInstance.addObjettsCollection(null);
        testInstance.addObjettsCollection(12389);
        testInstance.addObjettsCollection(3031);
        testInstance.addObjettsCollection(1918);

        String myGson = gsonBuilder.toGson(testInstance);
        String targetJson = gson.toJson(testInstance);

        assertTrue(targetJson.equals(myGson));
    }

    @Test()
    public void testNullObject(){
        var gsonBuilder = new GsonWriter();
        gsonBuilder.toGson(null);
    }
}
