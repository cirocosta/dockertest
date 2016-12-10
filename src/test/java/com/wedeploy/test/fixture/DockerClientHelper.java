package com.wedeploy.test.fixture;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.SwarmSpec;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.RemoteApiVersion;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ciro S. Costa
 */
public class DockerClientHelper {

	public DockerClientHelper() {
		DockerClientConfig dockerConfig = DefaultDockerClientConfig
			.createDefaultConfigBuilder()
			.withApiVersion(RemoteApiVersion.VERSION_1_24)
			.build();

		dockerClient = DockerClientBuilder
			.getInstance(dockerConfig)
			.withDockerCmdExecFactory(new NettyDockerCmdExecFactory())
			.build();
	}

	public void deinitializeSwarm() {
		log.debug("deinitializing swarm manager");

		dockerClient
			.leaveSwarmCmd()
			.withForceEnabled(true)
			.exec();
	}

	public void initializeSwarmManager() {
		log.debug("initializing swarm manager");

		dockerClient
			.initializeSwarmCmd(new SwarmSpec())
			.exec();
	}

	private static DockerClient dockerClient;
	private static final Logger log = LoggerFactory.getLogger(
		DockerClientHelper.class);

}