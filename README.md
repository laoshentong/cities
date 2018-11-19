# cities
Spring boot app which determines if two cities are connected.  Two cities are considered connected if there’s a series of roads that can be traveled from one city to another. 

List of roads is available in a file.

File contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities.

download project and run maven to build: mvn clean install

execute app: java -jar target/cities-0.0.1-SNAPSHOT.jar
             or mvn spring-boot:run

It will be deployed as a spring-boot app and expose one endpoint:

http://localhost:8080/connected?origin=city1&destination=city2

Example:

city.txt content is:

Boston, New York

Philadelphia, Newark

Newark, Boston

Trenton, Albany

Toronto, New York

Ottawa, Boston
 

http://localhost:8080/connected?origin=Boston&destination=Newark

Should return yes

http://localhost:8080/connected?origin=Boston&destination=Philadelphia

Should return yes

http://localhost:8080/connected?origin=Philadelphia&destination=Albany

Should return no

http://localhost:8080/connected?origin=Philadelphia&destination=Philadelphia

Should return bad request httpcode: 400 
