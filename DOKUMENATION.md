# Dokumentation: Kristoffer Larsson

### Beskrivning av projektet
Projektet gick ut på att använda kafka tillsammans med java och någon databaslösning, ett backend-API och en klientapplikation.

I mitt projekt har jag valt att lagra recept i en SQL-databas, använda Spring Boot för ett backend-API, en klientapplikation som också är en Spring Boot tillsammans med JavaFX för att både få tillgång till Spring:s framework och JavaFX:s GUI-bibliotek.

Jag valde att också implementera WebSockets i backend och klienten för att implementera en chatapplikation (med olika rum kopplade till recepten) för att få mer användning av kafka, då det kändes lite att bara skicka JSON-objekt genom kafka och spara dem i en databas, en chatt skulle teoretiskt kunna producera mycket mer realtidsdata för att kafka skulle kunna flexa sina muskler lite mer.

## Arbetet och dess genomförande
### Beskriv lite olika lösningar du gjort
Databaslösning: SQL, eftersom datan är relationell och ser ut på ett uniformt sätt kändes det som den mest naturliga databaslösningen. Det hade såklart gått att lösa med NoSQL, t.ex. MongoDB också, men alla dokument hade sett likadana ut så jag tror inte man hade fått ut någon extra funktionalitet av den lösningen.

Backendlösning: Spring Boot. Enormt paket av depedencies som är bra och vältestade, väldokumenterade etc. Oerhört smidigt system av dependency injektion i sina moduler, extremt effektiv uppsättning av anotationer etc.

Frontendlösning: Spring boot + JavaFX, samma anledning som ovan samt JavaFX också väldokumenterat och många bra inbyggda GUI-klasser.

Chatlösning: STOMP + WebSocket, verkar vara den mest använda lösningen när det gäller realtidskommunikation i text. Bra inbyggda bibliotek i Spring för båda så enkelt att implementera och använda.

Message broker: Kafka, i.om. att det var en del av uppgiften. Kafka används både för att skicka ut recept från API:et till en consumer som sparar till databas samt skicka och ta emot WebSocket-datan för att spara loggar av chatten i en databas, detta gör att man om man deployar och får enormt mycket trafik enkelt kan bygga appen som en microservice-app där man delar upp ansvaret att ta emot data och spara till databasen till två separata applikationer. 

### Vad som varit svårt
Det som tog mest tid var att få ordning på klientapplikationen, dels för att jag inte är så bra på att bygga UI:s generellt, dels för att jag sällan använt JavaFX tidigare och aldrig använt JavaFX tillsammans med Spring Boot. Men när jag väl fick igång min frontend-modul som ett Spring-projekt gick det ganska smidigt. Jag var tvungen att lära mig lite mer om hur Spring annoterar och gör depedency injections iom. att jag blev tvungen att skriva egna komponenter ovanpå Spring-depedencies, men när jag förstod tankesättet gick också det helt OK.

Framförallt var det väldigt smidigt att kunna använda Springs STOMP/WebSocket-dependencies.

### Beskriv om du fått byta lösning och varför i sådana fall
När jag började med klientapplikationen började jag att köra ett JavaFX-projekt som inte byggde på Spring Boot - men när jag började implementera WebSocket-chatten mot backenden insåg jag att det var rätt mycket jobb att bygga en STOMP-klient från grunden så valde att göra om klienten till att bygga på Spring Boot - det gjorde också att jag kunde använda Springs kafka-konsument i klientapplikationen så det underlättade ganska mycket.

Jag funderade i början också på att bygga chatten med endast kafka, och att varje klient kunde ha varsin consumer som uppdaterade chatten, men tänkte att det inte riktigt verkar vara kafka:s uppgift att göra på det viset, iom. begränsningarna kafka-consumers har med grupper etc. Hade i så fall behövt se till att varje samtidigt consumer fick ett unikt grupp-id, typ UUID, spara det i en databas och kolla så de inte återanvänds samtidigt etc., då kändes det mycket snyggare med en WebSocket-lösning.

## Slutsatser

### Vad gick bra
Tycker projektet blev bra i slutändan. Tycker lösningarna jag valde känns som bra lösningar som är anpassade efter syftet, koden är straight forward och ganska lättförståelig. JavaFX tycker jag i sig är rätt fult dock, mycket olika objekt som ligger nestade i varandra, men det är så JavaFX är uppbyggt. Jag hade kunnat använda FXML också, men vem gillar att titta på XML? Så det hade nog inte blivit så mycket snyggare som helhet av det.

Tyckte också SceneBuilder som många verkar gilla att använda med FXML var lite "låst" i hur man kunde bygga upp GUI:t, och det blir också en till felkälla då man båda kan göra fel i Java-koden samt XML-koden då de bara identifieras med strängar som kan ha typos etc. i.
### Vad gick dåligt
Bara en klient åt gången kan ha en kafka-consumer iom. att de annars får konflikt på samma grupp, men anledningen att jag har en kafka-klient i klienten är att det var ett krav från uppgiften, den fyller inte jättemycket mer funktion. Jag kan lika gärna lyssna på API:ets svarskod som Kafka-topicen då det är i princip samma information, så ser det egentligen inte som en nackdel om man skulle se på projeketet som ett skarpt projekt som skulle deployas hade man bara tagit bort kafka-consumern i klienten.
### Vad har du lärt dig
Har lärt mig mycket om hur Spring fungerar iom. klient-applikationen som krävde att jag skrev många fler egna komponenter än vad jag gjort tidigare. Lärde mig också mycket om Kafka, WebSockets och STOMP vilket känns väldigt användbart.
### Vad hade ni gjort annorlunda om ni gjort om projektet
Jag hade byggt klienten som Spring boot redan från början. Jag hade nog inte gjort kafka-consumern som jag gjorde om det inte krävdes av uppgiftsbeskrivningen.
Jag hade nog också byggt upp Kafka-klienten i frontend-applikationen anorlunda, och KafkaConsumern hade använt ett Listenable-interface och registrerat LoggerBox som en Listener på det för att göra det enklare att bygga ut med fler funktioner som lyssnar på Kafka-topicen.
### Vilka möjligheter ser du med de kunskaper du fått under kursen.
Bra kunskaper att ha, framförallt när det gäller BackEnd. Tror nog att mer och mer klient/frontend går över till webb, så javafx kanske inte är superduperanvänt idag, men såklart kan samma tankesätt användas i även andra frontend-lösningar.