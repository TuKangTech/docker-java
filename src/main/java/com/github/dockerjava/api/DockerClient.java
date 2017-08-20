package com.github.dockerjava.api;

import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.RemoteApiVersion;
import com.github.dockerjava.core.command.InspectTaskCmdImpl;

import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

// https://godoc.org/github.com/fsouza/go-dockerclient
public interface DockerClient extends Closeable {

    AuthConfig authConfig() throws DockerException;

    /**
     * Authenticate with the server, useful for checking authentication.
     */
    AuthCmd authCmd();

    InfoCmd infoCmd();

    PingCmd pingCmd();

    VersionCmd versionCmd();

    /**
     * * IMAGE API *
     */

    PullImageCmd pullImageCmd(@Nonnull String repository);

    PushImageCmd pushImageCmd(@Nonnull String name);

    PushImageCmd pushImageCmd(@Nonnull Identifier identifier);

    CreateImageCmd createImageCmd(@Nonnull String repository, @Nonnull InputStream imageStream);

    /**
     * Loads a tarball with a set of images and tags into a Docker repository.
     * <p>
     * Corresponds to POST /images/load API endpoint.
     *
     * @param imageStream stream of the tarball file
     * @return created command
     * @since {@link RemoteApiVersion#VERSION_1_7}
     */
    LoadImageCmd loadImageCmd(@Nonnull InputStream imageStream);

    SearchImagesCmd searchImagesCmd(@Nonnull String term);

    RemoveImageCmd removeImageCmd(@Nonnull String imageId);

    ListImagesCmd listImagesCmd();

    InspectImageCmd inspectImageCmd(@Nonnull String imageId);

    SaveImageCmd saveImageCmd(@Nonnull String name);

    /**
     * * CONTAINER API *
     */

    ListContainersCmd listContainersCmd();

    CreateContainerCmd createContainerCmd(@Nonnull String image);

    /**
     * Creates a new {@link StartContainerCmd} for the container with the given ID. The command can then be further customized by using
     * builder methods on it like {@link StartContainerCmd#withDns(String...)}.
     * <p>
     * <b>If you customize the command, any existing configuration of the target container will get reset to its default before applying the
     * new configuration. To preserve the existing configuration, use an unconfigured {@link StartContainerCmd}.</b>
     * <p>
     * This command corresponds to the <code>/containers/{id}/start</code> endpoint of the Docker Remote API.
     */
    StartContainerCmd startContainerCmd(@Nonnull String containerId);

    ExecCreateCmd execCreateCmd(@Nonnull String containerId);

    InspectContainerCmd inspectContainerCmd(@Nonnull String containerId);

    RemoveContainerCmd removeContainerCmd(@Nonnull String containerId);

    WaitContainerCmd waitContainerCmd(@Nonnull String containerId);

    AttachContainerCmd attachContainerCmd(@Nonnull String containerId);

    ExecStartCmd execStartCmd(@Nonnull String execId);

    InspectExecCmd inspectExecCmd(@Nonnull String execId);

    LogContainerCmd logContainerCmd(@Nonnull String containerId);

    /**
     * Copy resource from container to local machine.
     *
     * @param containerId id of the container
     * @param resource    path to container's resource
     * @return created command
     * @since {@link RemoteApiVersion#VERSION_1_20}
     */
    CopyArchiveFromContainerCmd copyArchiveFromContainerCmd(@Nonnull String containerId, @Nonnull String resource);

    /**
     * Copy resource from container to local machine.
     *
     * @param containerId id of the container
     * @param resource    path to container's resource
     * @return created command
     * @see #copyArchiveFromContainerCmd(String, String)
     * @deprecated since docker API version 1.20, replaced by {@link #copyArchiveFromContainerCmd(String, String)}
     * since 1.24 fails.
     */
    @Deprecated
    CopyFileFromContainerCmd copyFileFromContainerCmd(@Nonnull String containerId, @Nonnull String resource);

    /**
     * Copy archive from local machine to remote container
     *
     * @param containerId id of the container
     * @return created command
     * @since {@link RemoteApiVersion#VERSION_1_20}
     */
    CopyArchiveToContainerCmd copyArchiveToContainerCmd(@Nonnull String containerId);

    ContainerDiffCmd containerDiffCmd(@Nonnull String containerId);

    StopContainerCmd stopContainerCmd(@Nonnull String containerId);

    KillContainerCmd killContainerCmd(@Nonnull String containerId);

    /**
     * Update container settings
     *
     * @param containerId id of the container
     * @return command
     * @since {@link RemoteApiVersion#VERSION_1_22}
     */
    UpdateContainerCmd updateContainerCmd(@Nonnull String containerId);

    /**
     * Rename container.
     *
     * @param containerId id of the container
     * @return command
     * @since {@link RemoteApiVersion#VERSION_1_17}
     */
    RenameContainerCmd renameContainerCmd(@Nonnull String containerId);

    RestartContainerCmd restartContainerCmd(@Nonnull String containerId);

    CommitCmd commitCmd(@Nonnull String containerId);

    BuildImageCmd buildImageCmd();

    BuildImageCmd buildImageCmd(File dockerFileOrFolder);

    BuildImageCmd buildImageCmd(InputStream tarInputStream);

    TopContainerCmd topContainerCmd(String containerId);

    TagImageCmd tagImageCmd(String imageId, String repository, String tag);

    PauseContainerCmd pauseContainerCmd(String containerId);

    UnpauseContainerCmd unpauseContainerCmd(String containerId);

    EventsCmd eventsCmd();

    StatsCmd statsCmd(String containerId);

    CreateVolumeCmd createVolumeCmd();

    InspectVolumeCmd inspectVolumeCmd(String name);

    RemoveVolumeCmd removeVolumeCmd(String name);

    ListVolumesCmd listVolumesCmd();

    ListNetworksCmd listNetworksCmd();

    InspectNetworkCmd inspectNetworkCmd();

    CreateNetworkCmd createNetworkCmd();

    RemoveNetworkCmd removeNetworkCmd(@Nonnull String networkId);

    ConnectToNetworkCmd connectToNetworkCmd();

    DisconnectFromNetworkCmd disconnectFromNetworkCmd();

    /**
     * Enables swarm mode for the docker engine and creates a new swarm cluster
     *
     * @param swarmSpec the specification for the swarm
     * @return the command
     * @since 1.24
     */
    InitializeSwarmCmd initializeSwarmCmd(SwarmSpec swarmSpec);

    /**
     * Gets information about the swarm the docker engine is currently in
     *
     * @return the command
     * @since 1.24
     */
    InspectSwarmCmd inspectSwarmCmd();

    /**
     * Enables swarm mode for the docker engine and joins an existing swarm cluster
     *
     * @return the command
     * @since 1.24
     */
    JoinSwarmCmd joinSwarmCmd();

    /**
     * Disables swarm node for the docker engine and leaves the swarm cluster
     *
     * @return the command
     * @since 1.24
     */
    LeaveSwarmCmd leaveSwarmCmd();

    /**
     * Updates the swarm specification
     *
     * @param swarmSpec the specification for the swarm
     * @return the command
     * @since 1.24
     */
    UpdateSwarmCmd updateSwarmCmd(SwarmSpec swarmSpec);

    /**
     * Command to list all services in a docker swarm. Only applicable if docker runs in swarm mode.
     *
     * @return command
     * @since {@link RemoteApiVersion#VERSION_1_24}
     */
    ListServicesCmd listServicesCmd();

    /**
     * Command to create a service in a docker swarm. Only applicable if docker runs in swarm mode.
     *
     * @param serviceSpec the service specification
     * @return command
     * @since {@link RemoteApiVersion#VERSION_1_24}
     */
    CreateServiceCmd createServiceCmd(ServiceSpec serviceSpec);

    /**
     * Command to inspect a service
     *
     * @param serviceId service id or service name
     * @return command
     */
    InspectServiceCmd inspectServiceCmd(String serviceId);

    /**
     * Command to update a service specification
     *
     * @param serviceId   service id or service name
     * @param serviceSpec the new service specification
     * @return command
     */
    UpdateServiceCmd updateServiceCmd(String serviceId, ServiceSpec serviceSpec);

    /**
     * Command to remove a service
     *
     * @param serviceId service id or service name
     * @return command
     */
    RemoveServiceCmd removeServiceCmd(String serviceId);

    /**
     * Command to list all tasks in a docker swarm. Only applicable if docker runs in swarm mode.
     *
     * @return
     */
    ListTasksCmd listTasksCmd();

    /**
     * Command to inspect a task
     *
     * @param taskId task id
     * @return command
     */
    InspectTaskCmd inspectTaskCmd(String taskId);

    /**
     * Command to list all swarmNodes in a docker swarm. Only applicable if docker runs in swarm mode.
     *
     * @return
     */
    ListSwarmNodesCmd ListSwarmNodesCmd();

    /**
     * Command to inspect a swarmNode
     *
     * @param swarmNodeId swarmNode id
     * @return command
     */
    InspectSwarmNodeCmd InspectSwarmNodeCmd(String swarmNodeId);

    /**
     * Command to remove a swarmNode
     *
     * @param swarmNodeId swarmNode id
     * @return command
     */
    RemoveSwarmNodeCmd removeSwarmNodeCmd(String swarmNodeId);

    /**
     * Command to update a swarmNode specification
     *
     * @param swarmNodeId   swarmNode id
     * @param swarmNodeSpec the new swarmNode specification
     * @return command
     */
    UpdateSwarmNodeCmd updateSwarmNodeCmd(String swarmNodeId, SwarmNodeSpec swarmNodeSpec);

    @Override
    void close() throws IOException;
}
