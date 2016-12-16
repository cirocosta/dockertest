package com.wedeploy.test.fixture;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.RemoteApiVersion;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
//DOCKER_TLS_VERIFY=1
//DOCKER_HOST=tcp://192.168.99.101:2376
//DOCKER_CERT_PATH=/Users/cirocosta/.docker/machine/machines/swarm-master
//DOCKER_MACHINE_NAME=swarm-master

/**
 * @author Ciro S. Costa
 */
public class DockerSwarmHelper {

	public DockerSwarmHelper() {
		DockerClientConfig dockerConfig = DefaultDockerClientConfig
			.createDefaultConfigBuilder()
			.withApiVersion(RemoteApiVersion.VERSION_1_24)
			.withDockerHost("tcp://192.168.99.101:3376")
			.withDockerCertPath("/Users/cirocosta/.docker/machine/machines/swarm-master")
			.withDockerTlsVerify(true)
			.build();

		dockerClient = DockerClientBuilder
			.getInstance(dockerConfig)
			.withDockerCmdExecFactory(new NettyDockerCmdExecFactory())
			.build();
	}

	public DockerClient getDockerClient() {
		return dockerClient;
	}

	private static DockerClient dockerClient;

}