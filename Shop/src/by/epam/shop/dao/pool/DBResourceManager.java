package by.epam.shop.dao.pool;

import java.util.ResourceBundle;

public class DBResourceManager {
	 private DBResourceManager() {
	    }
	
	private final static String PROPERTY_FILE = "resources.db";
	
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(PROPERTY_FILE);


	public static String getValue(String key) {
		return BUNDLE.getString(key);
	}
}
