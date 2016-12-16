package com.wedeploy.test;

import com.wedeploy.test.fixture.DockerSwarmModeHelper;

import java.util.UUID;

import org.junit.BeforeClass;

/**
 * @author Ciro S. Costa
 */
public abstract class DockerTest {

	@BeforeClass
	public static void setUpTestSuite() {
		docker = new DockerSwarmModeHelper();
	}

	protected String createID() {
		return UUID.randomUUID().toString();
	}

	protected static DockerSwarmModeHelper docker;

}