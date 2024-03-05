# Warglaive
Warglaive is an online tool to track World of Warcraft Wrath of The Lich King Classic leaderboards, statictics, character gear and talents.

# Setup
To run this application you will need:
 - Java 17 installed on your machine
 - Battle.net client

To create Battle.net client:
 1. Login or create Battle.net account - you can create one here: https://develop.battle.net/
 2. Create a client through the API Access menu button
 3. Generate a secret for the client


Run application with
 - Maven: `mvn spring-boot:run -Dspring-boot.run.arguments=--blizzard.client-id=YourClientID,--blizzard.client-secret=YourClientSecret`

 - Maven wrapper `mvnw spring-boot:run -Dspring-boot.run.arguments=--blizzard.client-id=YourClientID,--blizzard.client-secret=YourClientSecret`

 - JAR file `java -jar warglaive.jar --blizzard.client-id=YourClientID --blizzard.client-secret=YourClientSecret`


#### TODO list
- [x] Display leaderboards
- [x] Character search
- [x] searchbar autocomplete
- [x] Scheduled Leaderboards update
- [ ] Implement pagination for leaderboards
- [ ] Refactor update funtion
- [ ] Add Character talents
- [ ] Add Character items
