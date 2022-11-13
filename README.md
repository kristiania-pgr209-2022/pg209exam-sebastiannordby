[![Java CI with Maven](https://github.com/kristiania-pgr209-2022/pg209exam-sebastiannordby/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr209-2022/pg209exam-sebastiannordby/actions/workflows/maven.yml)

# PG209 Backend programmering eksamen

## Hvordan vi jobber
Under denne oppgaven har vi valgt og jobbe sammen ved å bruke kommuniseringsplatformen Discord. 
Når vi jobber deler vi skjermen med hverandre så hvis en commit'er mer kode enn den andre er det fordi vi jobber på denne måten. 
Vi parprogrammerer med delt skjerm.

## ER-diagram for databasen vår:
![image](https://user-images.githubusercontent.com/97464729/201485230-6e8d5f54-622c-492a-b480-cabb2b422608.png)

Link til Github-repo:
https://github.com/kristiania-pgr209-2022/pg209exam-sebastiannordby

Link til Azure:
https://pg209exam-sebastiannordby.azurewebsites.net/

## Sjekkliste for innleveringen
* [X] Dere har lest eksamensteksten
* [x] Koden er sjekket inn på github.com/pg209-2022 repository
* [ ] Dere har lastet opp en ZIP-fil lastet ned fra Github
* [X] Dere har committed kode med begge prosjektdeltagernes GitHub-konto (alternativt: README beskriver hvordan dere har jobbet)

## README.md
* [ ] Inneholder link til Azure Websites deployment
* [x] Inneholder en korrekt badge til GitHub Actions
* [ ] Beskriver hva dere har løst utover minimum
* [x] Inneholder et diagram over databasemodellen

## Koden
* [ ] Oppfyller Java kodestandard med hensyn til indentering og navngiving
* [x] Er deployet korrekt til Azure Websites
* [x] Inneholder tester av HTTP og database-logikk
* [x] Bruker Flyway DB for å sette opp databasen
* [x] Skriver ut nyttige logmeldinger

## Basisfunksjonalitet
* [X] Kan velge hvilken bruker vi skal opptre som
* [x] Viser eksisterende meldinger til brukeren
* [x] Lar brukeren opprette en ny melding
* [x] Lar brukeren svare på meldinger
* [x] For A: Kan endre navn og annen informasjon om bruker
* [ ] For A: Meldingslisten viser navnet på avsender og mottakere

## Kvalitet
* [x] Datamodellen er *normalisert* - dvs at for eksempel navnet på en meldingsavsender ligger i brukertallen, ikke i meldingstabellen
* [x] Når man henter informasjon fra flere tabellen brukes join, i stedet for 1-plus-N queries (et for hovedlisten og et per svar for tilleggsinformasjon)
* [ ] Det finnes test for alle JAX-RS endpoints og alle DAO-er

## Plan for backend
* [X] En Jetty Webserver
  * [X] Servere en React applikasjon
* [X] Microsoft SQL Database med Flyway migreringer
* [ ] Databasen skal inneholde følgende tabeller:
  * [X] Bruker
  * [ ] Melding; innhold og avsenderId(bruker), mottakerId?(bruker), gruppeId?(Gruppe), dato
  * [ ] Gruppe: Navn
  * [ ] GruppeDeltaker: GruppeId(Gruppe), BrukerId(bruker)

## Plan for frontend
* [X] Når man åpner applikasjonen skal man kunne gjøre følgende:
  * [X] Velge fra et sett med brukere
  * [X] Opprette en ny bruker
* [X] Applikasjonen er delt opp i 2 komponenter: 
  * [X] Sendingspanel: En vertikal boks med valg for personer/grupper
  * [X] Meldingspanel: En vertikal boks der du ser meldinger som er hentet basert på valg i sendingspanel
* [ x Sendingspanel: viser personer/grupper du har meldinger i
  * [ ] Man skal kunne "opprette" en ny melding enten til person eller gruppe
* [x] Meldingspanel: viser meldingene innenfor personen/gruppen du har valgt på venstre side
* [x] Øverst i applikasjonen er den en knapp for innstillinger med følgende:
  * [x] Endre opplysninger for pålogget bruker
  * [x] Logge ut

## Brukerveiledning

###Landingsside
Når du kommer til landingssiden har du følgende valg:
- Opprette bruker
- Logge inn
- 
![image](https://user-images.githubusercontent.com/24465003/201512272-27ecc9f5-8a61-4417-a21c-6bf2a60e004d.png)

#### Opprette bruker
For å opprette bruker kan du fylle inn følgende felter:
- Navn
- E-post
- Nickname/kallenavn
- BIO - Kort beskrivelse
Trykk så opprett for å opprette bruker

![image](https://user-images.githubusercontent.com/24465003/201512312-22bfe519-8d32-4241-9cde-a08267b52c57.png)

#### Login
Når du har en bruker opprettet kan du logge inn
Man velger bruker ved å trykke på et av bruker-kortene:

![image](https://user-images.githubusercontent.com/24465003/201512375-d53a4e60-9220-4257-937a-b68463df16e1.png)

#### Meldinger
Når du har logget inn kommer man til følgende side:

![image](https://user-images.githubusercontent.com/24465003/201512403-ca20497c-47db-476e-ad31-da997a57f9e6.png)

Her kan man:
- Opprette ny meldingstråd
- Oppdatere/redigere bruker
- Logge ut

![image](https://user-images.githubusercontent.com/24465003/201512486-9f3b849d-dbc3-45c2-bf48-15b839ddf027.png)

### Oppdatere bruker
![image](https://user-images.githubusercontent.com/24465003/201512783-d7051585-2ac2-49da-84d3-ecd62a9d4b2b.png)
![image](https://user-images.githubusercontent.com/24465003/201512796-fcf180f7-7a36-4472-915e-61de68805c63.png)

### Ny meldingstråd:
![image](https://user-images.githubusercontent.com/24465003/201538674-da30604e-d090-4426-b5d9-f058f1b84e58.png)
![image](https://user-images.githubusercontent.com/24465003/201538689-98b52d9f-9672-4c03-8c7c-ac7a98ebdf13.png)
![image](https://user-images.githubusercontent.com/24465003/201538739-63589dfe-56a2-40cb-9c91-e0a93f97f6f1.png)
![image](https://user-images.githubusercontent.com/24465003/201538759-147e7d3b-ccb8-4699-abb9-442793a37c13.png)
![image](https://user-images.githubusercontent.com/24465003/201538790-b9185904-9942-4465-9b93-995a67d2902e.png)
![image](https://user-images.githubusercontent.com/24465003/201538821-ec6ca1d6-d8e4-4552-a183-33680f4db58a.png)


