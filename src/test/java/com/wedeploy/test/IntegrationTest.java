package com.wedeploy.test;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.SwarmSpec;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.RemoteApiVersion;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
import com.wedeploy.test.fixture.Network;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Ciro S. Costa
 */
public class IntegrationTest {

	@BeforeClass
	public void setUpTestSuite() {
		DockerClientConfig config = DefaultDockerClientConfig
			.createDefaultConfigBuilder()
			.withApiVersion(RemoteApiVersion.VERSION_1_24)
			.build();

		dockerClient = DockerClientBuilder
			.getInstance(config)
			.withDockerCmdExecFactory(new NettyDockerCmdExecFactory())
			.build();

		hostIp = Network.getHostIp();
	}

	@Test
	public void testSwarm_assertInitializationWorks () {
		initializeSwarmManager();
		deinitializeSwarm();
	}

	public void initializeSwarmManager () {
		dockerClient
			.initializeSwarmCmd(new SwarmSpec())
			.withAdvertiseAddr(hostIp)
			.withForceNewCluster(true)
			.exec();
	}

	public void deinitializeSwarm () {
		dockerClient
			.leaveSwarmCmd()
			.withForceEnabled(true)
			.exec();
	}

	private DockerClient dockerClient;
	private static String hostIp;
}
