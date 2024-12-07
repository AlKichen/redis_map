# About the Application

This application demonstrates how a Map implementation works, where data is stored in Redis under the hood.  
The application supports working with different Redis modes:
- **standalone**
- **sentinel**
- **redis-cluster**

## Running in dev mode
For local usage in dev mode:
1. Configure the environment
    - If you want to use **standalone** mode, you need to start Docker with [docker-compose](redis-db-dev/redis-standalone/docker-compose.yaml)
    - If you want to use **redis-cluster** mode, it can only be set up locally (without using Docker or k8s).
      Instructions for running [redis-cluster-locally](redis-db-dev/redis-cluster-locally/ReadMe(EN).md).
    - If you want to use **sentinel** mode, it also requires local deployment (without using Docker or k8s).
      Instructions for running [redis-sentinel](redis-db-dev/redis-sentinel/ReadMe(EN).md).
      Or, if you already have Redis, just correctly set up the configuration in the `application-dev.yml` file according to your setup.
2. Set up the configuration in the `application-dev.yml` file - create it by removing the `.sample` postfix from the example below.  
   You can find a configuration example in the [application-dev.yml](src/main/resources/application-dev.yml.sample) file.
   The example configuration should help you understand where and what to set.
3. Run the application with the **`dev`** profile.
4. For testing the application's functionality, a test controller has been created that provides two endpoints:
    - **POST** `/test/save?key=TestKey&value=123`
      Where `key` is the key to be stored (a string), and `value` is the value to be stored under that key (an integer).
    - **GET** `/test/check-direct?key=TestKey`
      Where `key` is the key to check the value.
      These endpoints can be used to verify if the values are indeed stored in Redis.  
      The `/test/check-direct` method checks the value directly in Redis, bypassing the **RedisMap** implementation.

## Running Tests and Building
The build process is initiated by running `./mvnw clean install` from the root of the project. Since a successful build
requires passing all tests, you need to either set up the test environment or run the build without tests using
the command `./mvnw clean install -DskipTests`.

### Setting Up the Test Environment
Use the [application.yml.sample](src/test/resources/application.yml.sample) file to create an `application.yml` file that will be used for configuring the application in test mode.
Run the tests [RedisMapTests](src/test/java/com/task/test_task/tests/RedisMapTests.java).

## Issues with Redis-Cluster Configuration
Unfortunately, I was unable to assemble a working configuration for Redis in **redis-cluster** mode using Kubernetes or Docker.  
The main difficulty was that the locally launched application (outside the Kubernetes or Docker container) could not see all the cluster nodes.

### Possible Cause
The problem is most likely related to network accessibility.  
Solutions found online did not work on my PC. Examples of non-working configurations:
- [Redis Cluster Using Docker](https://medium.com/@ahmettuncertr/redis-cluster-using-docker-1c8458a93d4b)
- [Setup a Redis in a Local Machine - Redis Clustering](https://medium.com/@ishara11rathnayake/setup-a-redis-in-a-local-machine-redis-clustering-120289f71df5)
- [External Access to a Kubernetes Redis Cluster](https://dev.to/kermodebear/external-access-to-a-kuberenetes-redis-cluster-46n6)
- [How to Deploy a Redis Cluster in Kubernetes](https://medium.com/@dmitry.romanoff/how-to-deploy-a-redis-cluster-in-kubernetes-65c967887f6f)

### Alternative
To resolve the issue, I wrote detailed instructions on running Redis in **redis-cluster** mode by installing it locally on your PC.  
Instructions can be found in [redis-cluster-locally](redis-db-dev/redis-cluster-locally/ReadMe(EN).md).
Also, for **redis-sentinel** mode, see [redis-sentinel](redis-db-dev/redis-sentinel/ReadMe(EN).md).
