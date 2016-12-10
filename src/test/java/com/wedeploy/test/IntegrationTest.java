package com.wedeploy.test;

import com.wedeploy.test.fixture.DockerClientHelper;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Ciro S. Costa
 */
public class IntegrationTest {

	@BeforeClass
	public static void setUpTestSuite() {
		docker = new DockerClientHelper();
	}

	@Test
	public void testSwarm_assertDeinitAfterInit() {
		docker.initializeSwarmManager();
		docker.deinitializeSwarm();
	}

	@Test(expected = RuntimeException.class)
	public void testSwarm_assertFailsIfDoubleInit() {
		docker.initializeSwarmManager();
		docker.initializeSwarmManager();
	}

	@Test(expected = RuntimeException.class)
	public void testSwarm_assertFailsIfDoubleLeave() {
		docker.initializeSwarmManager();
		docker.deinitializeSwarm();
		docker.deinitializeSwarm();
	}

	@Test(expected = RuntimeException.class)
	public void testSwarm_assertFailsIfLeaveNonInitiated() {
		docker.deinitializeSwarm();
	}

	protected static DockerClientHelper docker;

}