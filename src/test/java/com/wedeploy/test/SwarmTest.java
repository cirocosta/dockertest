package com.wedeploy.test;

import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Info;

import com.wedeploy.test.fixture.DockerSwarmHelper;
import com.wedeploy.test.fixture.Environment;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Ciro S. Costa
 */
public class SwarmTest {

	@Before
	public void setUp() {
		Assume.assumeFalse(Environment.isRunningOnTravis());
		swarm = new DockerSwarmHelper();
	}

	@Test
	public void testSwarm_assertCanCreateContainer() {
		String containerId = swarm
			.getDockerClient()
			.createContainerCmd("nginx")
			.exec()
			.getId();

		InspectContainerResponse inspection = swarm
			.getDockerClient()
			.inspectContainerCmd(containerId)
			.exec();

		swarm
			.getDockerClient()
			.startContainerCmd(containerId)
			.exec();
	}

	@Ignore("can't get this yet")
	@Test
	public void testSwarm_assertCanGetInfo() {
		Info info = swarm
			.getDockerClient()
			.infoCmd()
			.exec();
	}

	private static DockerSwarmHelper swarm;

}