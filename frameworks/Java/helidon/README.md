# [Helidon](http://helidon.io) Benchmarking Test (2.0.2)

This is the Helidon portion of a [benchmarking test suite](../) comparing a variety of web development platforms.
We have the following three tests:

- Helidon SE - reactive framework with Helidon Db Client database access
- Helidon MP - MicroProfile with raw database access
- Helidon MP JPA - MicroProfile with JPA database access

## Versions

* [Java OpenJDK 11](http://openjdk.java.net/) - build
* [Java OpenJDK 15](http://openjdk.java.net/) - runtime
* [Helidon 2.0.2](http://helidon.io/)

## Helidon SE Implementation

* [json](reactive/src/main/java/io/helidon/techempower/se/JsonHandler.java)
* [plaintext](reactive/src/main/java/io/helidon/techempower/se/PlainTextHandler.java)
* [db](reactive/src/main/java/io/helidon/techempower/se/DbService.java)
* [query](reactive/src/main/java/io/helidon/techempower/se/DbService.java)
* [update](reactive/src/main/java/io/helidon/techempower/se/DbService.java)
* [fortunes](reactive/src/main/java/io/helidon/techempower/se/DbService.java)

### Test URLs

* json: http://localhost:8080/json
* plaintext: http://localhost:8080/plaintext
* db: http://localhost:8080/db
* query: http://localhost:8080/queries?queries=
* update: http://localhost:8080/updates?updates=
* fortunes: http://localhost:8080/fortunes

## Helidon MP Implementation

* [json](microprofile/src/main/java/io/helidon/techempower/mp/JsonService.java)
* [plaintext](microprofile/src/main/java/io/helidon/techempower/mp/PlainTextService.java)
* [db](microprofile/src/main/java/io/helidon/techempower/mp/DatabaseServiceRaw.java)
* [query](microprofile/src/main/java/io/helidon/techempower/mp/DatabaseServiceRaw.java)
* [update](microprofile/src/main/java/io/helidon/techempower/mp/DatabaseServiceRaw.java)
* [fortunes](microprofile/src/main/java/io/helidon/techempower/mp/DatabaseServiceRaw.java)

An alternative test exists utilizing JPA, sources in:
[JPA database tests](microprofile/src/main/java/io/helidon/techempower/mp/DatabaseServiceJpa.java)

These tests cannot be used for queries and updates, as there is a problem with
data caching - JPA caches aggressively which is not allowed by the test.

### Test URLs

* json:  http://localhost:8080/json
* plaintext: http://localhost:8080/plaintext
* db: http://localhost:8080/db/raw/single
* db (JPA): http://localhost:8080/db/jpa/single
* query: http://localhost:8080/db/raw/query?queries=
* query (JPA): http://localhost:8080/db/jpa/query?queries= (unused)
* update: http://localhost:8080/db/raw/updates?updates=
* update (JPA): http://localhost:8080/db/raw/updates?updates= (unused)
* fortunes: http://localhost:8080/db/raw/fortunes
* fortunes (JPA): http://localhost:8080/db/jpa/fortunes

