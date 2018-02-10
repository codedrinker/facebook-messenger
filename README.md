# facebook-messenger
A most easily usable Facebook Messenger Platform API

## Usage
### Add the maven dependency
```xml
<dependency>
  <groupId>com.github.codedrinker</groupId>
  <artifactId>facebook-messenger</artifactId>
  <version>1.0.0</version>
</dependency>
```
### Start with FMClient instance, get FMClient instance in FMClicent.getInstance().

```java
 FMClient fmClient = FMClient.getInstance();
```
### Use with* methods set parameters to FMClient instance. 

```java
//new PostBack Handler to receive the postback message from facebook
public class FMPPostbackHandler extends FMMessagePostBackHandler {
    @Override
    public void handle(FMReceiveMessage.Messaging message) {
        log.debug("FMPPostbackHandler handlePostBack, sender -> {}, postback -> {}", message.getSender(), message);
    }
}
```
```java
//get FMClient instance, and set token, secret, handler paramter.
FMClient fmClient = FMClient.getInstance();
FMPPostbackHandler fmpPostbackHandler = new FMPPostbackHandler();
fmClient.withAccessToken("token")
        .withAccessSecret("secret")
        .withFmMessagePostBackHandler(fmpPostbackHandler);
```
### Use signature method to valid the payload.

```java

String xHubSignature = request.getHeader("X-Hub-Signature");
StringBuilder buffer = new StringBuilder();
BufferedReader reader = request.getReader();
String line;
while ((line = reader.readLine()) != null) {
    buffer.append(line);
}
String payload = buffer.toString();
boolean signature = fmClient.signature(payload, xHubSignature);
if(signature){
  //do
} else {
  //forbid
}
```
### Use dispatch method to dispatch payload message.

```java
fmClient.dispatch(payload);
```

## Road Map
1.0.0

## Apache License
Copyright 2017 Chunlei Wang

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Example
https://github.com/codedrinker/facebook-messenger-example/tree/master
