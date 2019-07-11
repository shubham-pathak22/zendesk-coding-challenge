# Zendesk Melbourne - Coding Challenge


## Requirements
* Java 8
* Apache Maven 3.6.0 

## Usage 
```
$ git clone https://github.com/shubham-pathak22/zendesk-coding-challenge.git
$ cd zendesk-coding-challenge
$ mvn package
$ java -jar target/zendesk-coding-challenge-1.0-SNAPSHOT.jar 

```
Alternatively, open the project in an IDE and run `Main.java`

## Data

By default the application uses the files present in the `resources` folder as input files.
```
$ ls src/main/resources/*.json
users.json tickets.json organizations.json
```
To provide other files as input, please edit the `config.properties` to provide complete path for the files. 
```
$ cat src/main/resources/config.properties 
user.json.path=
ticket.json.path=
organization.json.path=
```

## Design

The input file is scanned and each record is converted into a JSON Object.
All JSON Objects are loaded into a data structure called `Dictionary`.

`Dictionary` consists of 
* A `List` which contains actual JSON objects
* An `Inverted Index (Map<String<Multimap<String,Integer>)` which contains mapping of terms to a multimap containing values and list of indices of the actual objects containing those values


![alt text](https://github.com/shubham-pathak22/zendesk-coding-challenge/blob/master/figure.png "Dictionary")



## Matching Instructions 
* Search is case-insensitve
* Values in the array can be searched independently. All records containing the particular array value will be matched
* Keys for the date values are stored  in "YYYY-MM-dd" format. To search for records that were created on 21st April 2015, enter 2015-04-21 as the value
* Empty fields can be searched as well. When prompted for the value just press enter.

## Assumptions
* Names of the member variables in the model classes match the attribute names of the json
* Model class has the id field annotated with `@Id`
* Attribute `_id` is unique and document has just one record per `_id`
* `_id` cannot be null
* Input files are valid `JSON` files.
* Json Records in the input file are in form of an `array` 
* All the date values are of the format `yyyy-MM-dd'T'HH:mm:ss ZZ`

## External Libraries
* [Google Guava](https://github.com/google/guava)
* [Google Gson](https://github.com/google/gson)
* [Joda-Time](https://www.joda.org/joda-time/)
* [Apache Commons](https://commons.apache.org/proper/commons-io/)
* [Apache Commons Lang](https://commons.apache.org/proper/commons-lang/)
* [Lombok](https://projectlombok.org)
* [MapStruct](http://mapstruct.org)
* [Junit4](https://junit.org/junit4/)






