package com.wedeploy.test;

import com.wedeploy.test.fixture.DockerClientHelper;

import org.junit.BeforeClass;

/**
 * @author Ciro S. Costa
 */
public abstract class DockerTest {

	@BeforeClass
	public static void setUpTestSuite() {
		docker = new DockerClientHelper();
	}

	protected static DockerClientHelper docker;

}