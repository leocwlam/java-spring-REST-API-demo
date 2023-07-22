# java-spring-REST-API-demo

> Provide simple the Java Sprint demo REST API

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/leocwlam/java-spring-REST-API-demo/blob/master/LICENSE)
[![Build Status](https://api.travis-ci.com/leocwlam/java-spring-REST-API-demo.svg?branch=master)](https://app.travis-ci.com/leocwlam/java-spring-REST-API-demo)
[![Coverage Status](https://coveralls.io/repos/github/leocwlam/java-spring-REST-API-demo/badge.svg?branch=master)](https://coveralls.io/github/leocwlam/java-spring-REST-API-demo?branch=master)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)
[![Known Vulnerabilities](https://snyk.io/test/github/leocwlam/java-spring-REST-API-demo/badge.svg)](https://snyk.io/test/github/leocwlam/java-spring-REST-API-demo)

# Contents

---

<p align="center">
    <a href="#quick-start">Quick Start</a> &bull;
    <a href="#test">Run Test</a> &bull;
    <a href="#docker-image-container">Build Local Docker Image And Create Container</a> &bull;
    <a href="#example">Example</a> &bull;
    <a href="#license">License</a>
</p>

---

# <a name="quick-start"></a>Quick Start

```bash
$ git clone https://github.com/leocwlam/java-spring-REST-API-demo.git java-spring-REST-API-demo
$ cd java-spring-REST-API-demo
```

# <a name="test"></a>Run Test

```bash
$ mvn test
```

![Test Output](https://raw.githubusercontent.com/leocwlam/java-spring-REST-API-demo/master/docs/unittests.png)

# <a name="docker-image-container"></a>Build Local Docker Image And Create Container

- Make sure mvn install before build docker image

```bash
$ mvn install
$ docker build -t java-spring-REST-API-demo:1.0 .
$ docker network create demo-network
$ docker run -d \
-p 3000:8080 \
--name java-spring-REST-API-demo \
--network demo-network \
java-spring-REST-API-demo:1.0
$ open -a "Google Chrome" http://localhost:3000/v1/assets
```

- Note: We can skip create network `demo-network`, if we already created network before.

# <a name="docker-stop"></a>

- Stop java-spring-REST-API-demo container

```bash
$ docker stop gateway
```

# <a name="docker-start"></a>

- Start java-spring-REST-API-demo container

```bash
$ docker start gateway
```

# <a name="example"></a>Example

## <a name="get-all-assets"></a>Get all assets

```
GET: localhost:8080/v1/assets
```

![List Output](https://raw.githubusercontent.com/leocwlam/java-spring-REST-API-demo/master/docs/getall.png)

- Note: GET: localhost:3000/v1/assets for docker testing

## <a name="get-asset-id"></a>Get asset id

```
GET: localhost:8080/v1/assets/e675064e-6c9b-40ac-9c07-08bb2e1e5065
```

![Get Output](https://raw.githubusercontent.com/leocwlam/java-spring-REST-API-demo/master/docs/get.png)

- Note: GET: localhost:3000/v1/assets/e675064e-6c9b-40ac-9c07-08bb2e1e5065 for docker testing

## <a name="Add-new-asset"></a>Add new asset

```
POST: localhost:8080/v1/assets

Request Body:
      {
          "name": "test1",
          "type": 1,
          "description": "Desc 1"
      }
```

![Create Output](https://raw.githubusercontent.com/leocwlam/java-spring-REST-API-demo/master/docs/create.png)

- Note: GET: localhost:3000/v1/assets for docker testing

## <a name="Update-asset-id"></a>Update asset

```
PUT: localhost:8080/v1/assets/e675064e-6c9b-40ac-9c07-08bb2e1e5065
Request Body:
      {
          "name": "testChange",
          "type": 13,
          "description": "Desc 3"
      }
```

![Update Output](https://raw.githubusercontent.com/leocwlam/java-spring-REST-API-demo/master/docs/update.png)

- Note: GET: localhost:3000/v1/assets/e675064e-6c9b-40ac-9c07-08bb2e1e5065 for docker testing

## <a name="Delete-asset-id"></a>delete asset id

```
DELETE: localhost:8080/v1/assets/e675064e-6c9b-40ac-9c07-08bb2e1e5065
```

![Delete Output](https://raw.githubusercontent.com/leocwlam/java-spring-REST-API-demo/master/docs/delete.png)

- Note: GET: localhost:3000/v1/assets/e675064e-6c9b-40ac-9c07-08bb2e1e5065 for docker testing

# <a name="license"></a>License

MIT
