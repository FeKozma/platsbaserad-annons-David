# platsbaserad-annons-David

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
