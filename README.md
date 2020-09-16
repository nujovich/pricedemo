# BitCoin Price Challenge

This is an application developed with SpringBoot that containd an async task executor with a scheduled task that requests the bitcoin price at a fixed time and stores in a volatile hash map structure contained in memory

## Stack used

1. Java 1.8 with Streams API
2. Spring boot 2.2.3 to build RESTful APIs and @Scheduled annotation usage
3. JaCoCo Libs to unit testing and coverage (81% according reports)
4. Jackson 2.10 Lib to decode json REST API response to plain string
4. Awaitility 3.0 to test scheduled task

## Run the project locally

Proceed to clone [GIT](https://github.com/nujovich/pricedemo.git) repository

```bash
git clone https://github.com/nujovich/pricedemo.git
```

Generate target folder

```bash
mvn clean install
```

To see the JaCoCo reports

```bash
mvn test
mvn jacoco:report
```
This will generate a site\jacoco folder under target. To see generate reports go to index.html file, right click on it, copy path and paste it in your browser. Coverage %81

Run the spring boot app using the mvn wrapper command
```bash
mvnw spring-boot:run
```
Or Run the app using the conventional java way
```bash
java -jar price-0.0.1-SNAPSHOT
```

## Usage

### Schedule task running in the background
Every 10 seconds a request is being made to a remote server to obtain latest bitcoin price. The execution is being performed by an async task executor with a pool of 10 thread (fixed number)

```bash
####################PRICES STORAGE################### Thread: taskExecutor-1
2020-09-15T22:58:07=10770.1
####################PRICES STORAGE################### Thread: taskExecutor-2
2020-09-15T22:58:07=10770.1
2020-09-15T22:58:15=10770.1
####################PRICES STORAGE################### Thread: taskExecutor-3
2020-09-15T22:58:07=10770.1
2020-09-15T22:58:15=10770.1
2020-09-15T22:58:25=10770.1
####################PRICES STORAGE################### Thread: taskExecutor-4
2020-09-15T22:58:07=10770.1
2020-09-15T22:58:35=10776.2
2020-09-15T22:58:15=10770.1
2020-09-15T22:58:25=10770.1
####################PRICES STORAGE################### Thread: taskExecutor-5
2020-09-15T22:58:45=10776.2
2020-09-15T22:58:07=10770.1
2020-09-15T22:58:35=10776.2
2020-09-15T22:58:15=10770.1
2020-09-15T22:58:25=10770.1
```
This information is saved in a volatile hash map object to make sure every thread reads the latest from this structure

### Rest Services Exposed

#### Get the bitcoin price for a given date and time

```bash
curl -v GET http://localhost:8080/pricedemo/v1/price/2020-09-15T23:00:15 -H 'Content-Type: application/json'

< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 16 Sep 2020 02:04:15 GMT
<
10777.8
```
#### Get stats: mean, max price for the whole time series and difference percentage for two given timestamps

```bash
curl -v GET http://localhost:8080/pricedemo/v1/price/2020-09-15T23:00:15/2020-09-15T22:58:35/stats -H 'Content-Type: application/json'

< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 16 Sep 2020 02:07:50 GMT
<
{"mean":10777.0,"maxPrice":10777.8,"percentageDiff":0.01}
```

#### If there's no price store for the selected date and time

```
curl -v GET http://localhost:8080/pricedemo/v1/price/2020-09-13T01:22:37  -H 'Content-Type: application/json'

< HTTP/1.1 404
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 16 Sep 2020 02:10:45 GMT
<
{"errorMessage":"There's no price for the selected datetime.","errorCode":404}*
```