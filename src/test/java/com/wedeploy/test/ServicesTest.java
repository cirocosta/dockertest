package com.wedeploy.test;

import com.wedeploy.test.fixture.DockerClientHelper;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Ciro S. Costa
 */
public class ServicesTest extends DockerTest {

	@BeforeClass
	public static void setUpTestSuite() {
		docker = new DockerClientHelper();

		try {
			docker.initializeSwarmManager();
		} catch (Exception e) {
		}
	}

	@Test
	public static void testService_assertCanBeCreated() {
	}

}