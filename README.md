# Java Stream Libs

## What's in the box

Java Concurrent libraries components, reported in following modules :


* [Library dependencies](/bom)
* [Concurrent Common](/common)
* [Concurrent Channel](/channel)
* [Concurrent File Utilities](/files)





## Requirements:

To include and use current library you need:
* Java 8+
* SFL4J implementation dependency, for logging purposes
* Maven 3.x+



## How to build library

To build library and create jar you can run following Maven command: 



### Windows

Execute following command from command prompt :

mvn.bat clean package install



### Linux / Mac


Execute following command from command shell :

mvn clean package install


### Other available Maven goals:

* Cobertura Report (cobertura:cobertura)
* Javadocs jar (javadoc:jar)
* Source jar (source:jar)  


### Skipping tests available maven command line properties:

* Skip Unit Tests and Cobertura Report (-Dskip.unit.tests=true)
* Skip Integration Tests (-Dskip.integration.tests=true)
* Skip All Tests (-Dskip.tests=true)


  
## License


Copyright (c) 2016-2017 [Fabrizio Torelli](https://www.linkedin.com/in/fabriziotorelli/)

Licensed under the [LGPL](/LICENSE) License (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[https://www.gnu.org/licenses/lgpl-3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html)

You may also obtain distribution or production use written authorization or support, contacting creator at

[Personal Email Address](mailto:hellgate75@gmail.com)


Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied,
further limitations in the license body.
See the License for the specific language governing permissions and
limitations under the License.