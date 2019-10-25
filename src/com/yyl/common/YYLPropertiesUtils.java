package com.yyl.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class YYLPropertiesUtils {

	public static Properties getProperties() {

		Properties p = new Properties();

		try {
			InputStream inputStream = YYLPropertiesUtils.class.getClassLoader().getResourceAsStream(
					"SystemConfig.properties");

			p.load(inputStream);

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return p;
	}
}
