# Zendesk Melbourne - Coding Challenge



## Usage Instructions 
```
$ git clone https://github.com/shubham-pathak22/zendesk-coding-challenge.git
$ cd zendesk-coding-challenge
$ mvn package
$ java -jar target/zendesk-coding-challenge-1.0-SNAPSHOT.jar 

```
## Data

By default the application uses the files present in the resources as input files.
```
$ ls src/main/resources/*.json
users.json tickets.json organizations.json
```
To use other files as data, please edit the config.properties to provide complete path for the files. 
```
$ cat src/main/resources/config.properties 
user.json.path=
ticket.json.path=
organization.json.path=
```

## Design

The input file is scanned and each record is converted into a JSON Object.
All JSON Objects are loaded into a data structure called `InvertedIndex`.

`InvertedIndex` consists of 
* A `List` which contains actual JSON objects
* A `Map<String<Multimap<String,Integer>` which contains mapping of terms to a multimap containing values and list of indices of the actual objects containing those values


![alt text](https://github.com/shubham-pathak22/zendesk-coding-challenge/blob/master/figure.png"")

## Matching Instructions 
* Elements belonging to the array can be searched individually.
* Keys for the date values are stored  in "YYYY-MM-dd" format.So to search for records that were created on 21st April 2015, enter 2015-04-21 as the value
* Empty fields can be searched as well. When prompted for the value just press enter.

## Assumptions

## Limitations

