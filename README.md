# bankwithmint

To run this application. Please follow the instruction

1,  Clone the application

2,  cd to card folder then docker-compose up -d to run the kafka configuration

3,  on card folder, mvn spring-boot:run to start the application 
Then call the endpoint  
http://localhost:8091/card-scheme/verify/45717360 this will verify and publish the scheme card to queue.

4, For the consuming service, cd consumingservice folder, mvn spring-boot:run. This is to listener to receive the scheme card from queue
