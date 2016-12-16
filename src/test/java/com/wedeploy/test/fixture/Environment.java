package com.wedeploy.test.fixture;

/**
 * @author cirocosta
 */
public class Environment {

	public static boolean isRunningOnTravis() {
		String travisEnv = System.getenv("TRAVIS");

		if (travisEnv != null && !travisEnv.isEmpty()) {
			return true;
		}

		return false;
	}

}