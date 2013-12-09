SEPR
====

Air Traffic Controller Game

## Compiling the source
This project uses Maven to handle its dependencies.

* Install [Maven 2 or 3](http://maven.apache.org/download.html)  
* Checkout this repo and run: `mvn clean package`

## Developing with eclipse
Make sure you have the following eclipse plugins installed:
* [m2eclipse](http://eclipse.org/m2e/download/) (Eclipse maven integration)
* [lombok](http://projectlombok.org/download.html) (Useful annotations)
* [maven-natives](mavennatives.googlecode.com) (Automatically links lwjgl natives)

Then simply click "File > Import > Exising maven project" and then browse for the root directory of this project. Finally click Finish.
