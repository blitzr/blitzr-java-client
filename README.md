Blitzr API client for Java
==========================

[![](https://jitpack.io/v/blitzr/blitzr-java-client.svg)](https://jitpack.io/#blitzr/blitzr-java-client)


This client provides you an easy way to consume the Blitzr API in Java. The only dependency of this package is
Jackson Databind.

This bundle is really easy to use, you can find all the documentation [here](http://blitzr.github.io/blitzr-java-client/).


Installation
============

The Blitzr Java Client is hosted on Github and is available for Gradle, Maven, Sbt and Leiningen via the JitPack repository.

You will find the installation guide on the JitPack Blitzr Repository.

Example using Gradle:
---------------------

Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```
Add the dependency:
```
dependencies {
        compile 'com.github.blitzr:blitzr-java-client:1.0.1'
}
```

Example using Maven:
--------------------

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the dependency:
```xml
<dependency>
    <groupId>com.github.blitzr</groupId>
    <artifactId>blitzr-java-client</artifactId>
    <version>1.0.1</version>
</dependency>
```

Examples
========

Example classic:
----------------

```java
BlitzrClient blitzr = new BlitzrClient(yourApiKey);

Artist artist = blitzr.getArtist("the-beatles", null, null, null);

System.out.println(artist.getName());

List releases = blitzr.getArtistReleases(artist.getSlug(), null, 0, 10, ReleaseType.official, ReleaseFormat.album, false);

for (Release release : releases) {
    System.out.println(release.getName());
}

// out:
// The Beatles
// Abbey Road
// Let It Be
// The Beatles
// Sgt. Pepper's Lonely Hearts Club Band
// Revolver
// Rubber Soul
// Beatles VI
// Beatles '65
// Beatles For Sale
// The Beatles' Second Album
```

Same example with generator:
----------------------------

```java
BlitzrClient blitzr = new BlitzrClient(yourApiKey);

Artist artist = blitzr.getArtist("the-beatles", null, null, null);

System.out.println(artist.getName());

Generator releases = blitzr.getArtistReleasesGenerator(artist.getSlug(), null, 0, 10, ReleaseType.official, ReleaseFormat.album, false);

for (Release release : releases) {
    System.out.println(release.getName());
}

// The Beatles
// Abbey Road
// Let It Be
// The Beatles
// Sgt. Pepper's Lonely Hearts Club Band
// Revolver
// Rubber Soul
// Beatles VI
// Beatles '65
// Beatles For Sale
// The Beatles' Second Album  (The generator calls the api to continue)
// Twist And Shout
// Meet The Beatles!
// Something New
// Long Tall Sally
// With The Beatles
// Please Please Me
```