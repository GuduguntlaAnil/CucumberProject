package com.automation.workware.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader loads test configuration properties used by the framework.
 *
 * Current behavior:
 * - init_prop() reads the properties file from an absolute path (project-specific).
 * - The properties file contains keys like 'browser', 'excel.path' and 'base.url'.
 *
 * Notes / recommendations:
 * - You may want to change this to load properties from the classpath (src/test/resources)
 *   using ClassLoader.getResourceAsStream(...) to avoid hard-coded absolute paths.
 * - The method returns a java.util.Properties instance which callers can query for values.
 */
public class ConfigReader {
	
	private Properties prop;
	
	public Properties init_prop()  {
		prop=new Properties();
		try {
			FileInputStream ip=new FileInputStream("C:/Users/guduguntla anil/eclipse-workspace/CucumberProjectParallel/src/test/resources/configuration/config.properties");
			try {
				prop.load(ip);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	

}