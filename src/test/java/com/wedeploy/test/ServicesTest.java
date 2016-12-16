package com.wedeploy.test;

import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.EndpointResolutionMode;
import com.github.dockerjava.api.model.EndpointSpec;
import com.github.dockerjava.api.model.ServiceModeConfig;
import com.github.dockerjava.api.model.ServiceReplicatedModeOptions;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.TaskSpec;

import com.wedeploy.test.fixture.DockerSwarmModeHelper;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Ciro S. Costa
 */
public class ServicesTest extends DockerTest {

	@BeforeClass
	public static void setUpTestSuite() {
		docker = new DockerSwarmModeHelper();

		try {
			docker.initializeSwarmManager();
		} catch (Exception e) {
		}
	}

	@Test
	public void testService_assertCanBeCreated() {
		String serviceName = createID();

		docker.getClient()
			.createServiceCmd(new ServiceSpec()
				.withName(serviceName)
				.withTaskTemplate(new TaskSpec()
					.withContainerSpec(new ContainerSpec()
						.withImage("nginx")))
				.withMode(new ServiceModeConfig()
					.withReplicated(new ServiceReplicatedModeOptions()
						.withReplicas(10)))
				.withEndpointSpec(new EndpointSpec()
					.withMode(EndpointResolutionMode.DNSRR)))
			.exec();
	}

}