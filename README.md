[![Java CI with Maven](https://github.com/kristiania-pgr209-2022/pg209exam-sebastiannordby/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr209-2022/pg209exam-sebastiannordby/actions/workflows/maven.yml)

# PG209 Backend programmering eksamen

## Hvordan vi jobber
Under denne oppgaven har vi valgt og jobbe sammen ved å bruke kommuniseringsplatformen Discord. 
Når vi jobber deler vi skjermen med hverandre så hvis en commit'er mer kode enn den andre er det fordi vi jobber på denne måten. 
Vi parprogrammerer med delt skjerm.

Uml-diagram for databasemodellen vår:
![image](https://user-images.githubusercontent.com/97464729/201485230-6e8d5f54-622c-492a-b480-cabb2b422608.png)

link til githup-repo:
https://github.com/kristiania-pgr209-2022/pg209exam-sebastiannordby


## Sjekkliste for innleveringen
* [ ] Dere har lest eksamensteksten
* [x] Koden er sjekket inn på github.com/pg209-2022 repository
* [ ] Dere har lastet opp en ZIP-fil lastet ned fra Github
* [ ] Dere har committed kode med begge prosjektdeltagernes GitHub-konto (alternativt: README beskriver hvordan dere har jobbet)

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
