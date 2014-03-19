fabric8-osgi-aspectj-demo
======================

Fabric8 + OSGi + AspectJ demonstration code.

# Pre-requisites

* JDK 7 (don't try JDK 8 as aspects won't compile!!)
* Maven 3.1.0 or newer

# Build and install

```
mvn clean install
```

# Provisioning

## Installation and initial configuration

* Download [latest build](https://repository.jboss.org/nexus/content/repositories/ea/io/fabric8/fabric8-karaf/) for ```fabric-karaf``` and extract it.
*(tested on fabric8-karaf-1.0.0.redhat-368)*
* Extract it
* ```cd``` to the newly extracted folder
* Define default administrative user (login: **admin**, password:**admin**) by uncommenting the last line of ```etc/user.properties```
* Start Fabric
```no-highlight
bin/fusefabric
```

If everything goes well, you should get a Fabric shell that looks like this:

```
Please wait while Fabric8 is loading...
100% [========================================================================]

______    _          _      _____
|  ___|  | |        (_)    |  _  |
| |_ __ _| |__  _ __ _  ___ \ V /
|  _/ _` | '_ \| '__| |/ __|/ _ \
| || (_| | |_) | |  | | (__| |_| |
\_| \__,_|_.__/|_|  |_|\___\_____/
  Fabric8 Container (1.0.0.redhat-368)
  http://fabric8.io/

Type 'help' to get started
and 'help [cmd]' for help on a specific command.
Hit '<ctrl-d>' or 'osgi:shutdown' to shutdown this container.

Open a browser to http://localhost:8181 to access the management console

Create a new Fabric via 'fabric:create'
or join an existing Fabric via 'fabric:join [someUrls]'

Fabric8:karaf@root>
```

## Start Fabric Ensemble
```
fabric:create --clean --wait-for-provisioning
```

## Define our own profile
```
profile-create --parents feature-dosgi osgi-aspect-example
profile-edit --features fabric-cxf --features fabric-cxf-registry --features swagger osgi-aspect-example
profile-edit --bundles mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.aspectj/1.7.4_1 osgi-aspect-example
profile-edit --bundles mvn:com.github.pires.example/aspect-aspects/0.1-SNAPSHOT osgi-aspect-example
profile-edit --bundles mvn:com.github.pires.example/aspect-service/0.1-SNAPSHOT osgi-aspect-example
profile-edit --bundles mvn:com.github.pires.example/aspect-rest/0.1-SNAPSHOT osgi-aspect-example
```

## Create and run new container with newly created profile

```
container-create-child --profile osgi-aspect-example root test1
```

# Testing

In Hawt.io UI, go to ```API``` tab (in the parent container), check the host and port where ```MyServiceEndpoint``` is available and point it down. Test the REST endpoint as you wish!

## REST API

Simple test
```
GET /demo/
```

Check the container logs and you shall see the aspect output :-)

```
2014-03-19 19:21:21,706 | INFO  | p1714525731-6470 | LoggingInInterceptor             | eptor.AbstractLoggingInterceptor  234 | 106 - org.apache.cxf.cxf-api - 2.7.0.redhat-610368 | Inbound Message
----------------------------
ID: 5
Address: http://localhost:8182/cxf/demo/
Http-Method: GET
Content-Type: application/json
Headers: {Accept=[application/json], accept-encoding=[gzip,deflate,sdch], Accept-Language=[en-US,en;q=0.8,pt-PT;q=0.6], be-token=[b30a3c54-83b8-4d6c-9a23-9fb75daec5f9], Cache-Control=[no-cache], connection=[keep-alive], content-type=[application/json], Host=[localhost:8182], User-Agent=[Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36]}
--------------------------------------
2014-03-19 19:21:21,708 | INFO  | p1714525731-6470 | MyAspect                         | ub.pires.example.aspect.MyAspect   20 | 89 - com.github.pires.example.aspect-aspects - 0.1.0.SNAPSHOT | logBefore() is running for
2014-03-19 19:21:21,708 | INFO  | p1714525731-6470 | MyService                        | ample.service.impl.MyServiceImpl   15 | 91 - com.github.pires.example.aspect-service - 0.1.0.SNAPSHOT | doing something..
2014-03-19 19:21:21,708 | INFO  | p1714525731-6470 | MyAspect                         | ub.pires.example.aspect.MyAspect   25 | 89 - com.github.pires.example.aspect-aspects - 0.1.0.SNAPSHOT | logAfter() is running for doSomething
2014-03-19 19:21:21,709 | INFO  | p1714525731-6470 | LoggingOutInterceptor            | eptor.AbstractLoggingInterceptor  234 | 106 - org.apache.cxf.cxf-api - 2.7.0.redhat-610368 | Outbound Message
---------------------------
ID: 5
Response-Code: 204
Content-Type:
Headers: {Date=[Wed, 19 Mar 2014 19:21:21 GMT], Content-Length=[0]}
--------------------------------------
```
