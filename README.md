# knappen för att användare enbart ska se reklamen en gång är ur funktion
# alla anonser kan enbart ses en gång. 
# Om man ska ha en reklam synas fler gånger -> lägg upp samma reklam fler gånger
-------------------------------------
# platsbaserad-annons-David

Detta är ett project som gjordes HT 2017 på Blekinge Tekniska Högskola i kursen Individuelt project. 
Jag som utvecklare har lagt ner ca 160 timmar på detta project vilket motsvarar 7.5 högskolepoäng.
Projectet har varit välldigt lärorikt då jag inte jobbat med mycket av det som ingår i projectet innan.
Det tog ett tag innan jag lyckades lista ut hur alla programen fungerade och hur man kopplar samman de olika delarna. Vilket ledde till att den mästa märkbara framstegen gjorde jag de sista veckorna i projectet då de första veckorna gick åt att få allting att köra (hadde lite problem med eclips och maven) och förstå hur det skulle funka. 

-------------------------------------
# Java

länkar och vad de gör

/addad/{longitude}/{latitude}/{radius}/{company}/{summary}/{days}/{nrViews}/query
ex: http://localhost:8080/addad/15.591926/56.181154/500/Spotify/nagonText/20/200/query?url=www.bild.se

*lägger till reklam på utsatt position med*

/addapp/{appname}/{company}
ex:  http://localhost:8080/addapp/Spotify/google

*lägga in att en app som godkänner att ett företag får lägga reklam på applikationen*

/addcompany/{app}/{companyname}
ex: http://localhost:8080/addcompany/Spotify/google

*lägga in ett företag som godkänner att en app får vissa reklamen från företaget*

/hello
ex: http://localhost:8080/hello

*ger ett unikt id, används en gång för varje användare så att servern vet om användaren redan har tittat på reklamen*

/getad/{longitude}/{latitude}/{id}/{app}
ex: http://localhost:8080/getad/15.591926/56.181154/9/Spotiffy

*ger en närbeliggande reklam om sådan finns*

/getapps
ex: http://localhost:8080/getapps

*ger en array av alla appar som har godkänt något företag*

/getcompanies
ex: http://localhost:8080/getcompanies

*ger en array av alla företag som har godkänt någon app*

/getcurnrviews/{company}
ex: http://localhost:8080/getcurnrviews/google

*ger en array av hur många som har tittat på deras olika reklamer*

-------------------------------------

# Angular

angular är programerad att nå servern på port 8080

det blir fel i konsolen när angular inte får information från databasen direct men detta fel fixar sig själv. Detta kan lösas med att använda "promice".

--------------------------------------

utvecklare: Felix Kozma
kund: David Eriksson
