package com.jhl.encourage.apis;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;

/**
 * Created by nidhin on 28/5/14.
 */

@Root(name ="SpocObject")
public class SpocObject {



    @Attribute
    private String resultTypeCode;

    public String getResultTypeCode() {
        return resultTypeCode;
    }

    public void setResultTypeCode(String resultTypeCode) {
        this.resultTypeCode = resultTypeCode;
    }

    @ElementMap(name = "map", attribute = true, inline = false, key = "key", entry = "entry", keyType = String.class, valueType = String.class, required = false)
    private HashMap<String , String> map;


    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}
