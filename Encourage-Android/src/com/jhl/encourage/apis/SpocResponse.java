package com.jhl.encourage.apis;

import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by nidhin on 28/5/14.
 */
@Root(name = "SPOCResponse")
public class SpocResponse {

    @ElementList(inline = true)
    private ArrayList<SpocObject> spocObjects;

	public ArrayList<SpocObject> getSpocObjects() {
		return spocObjects;
	}

}
