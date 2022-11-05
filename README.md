# PG209 Backend programmering eksamen

## Hvordan vi jobber
Under denne oppgaven har vi valgt og jobbe sammen ved å bruke kommuniseringsplatformen Discord. 
Når vi jobber deler vi skjermen med hverandre så hvis en commit'er mer kode enn den andre er det fordi vi jobber på denne måten. 
Vi parprogrammerer med delt skjerm.

## Sjekkliste for innleveringen
* [ ] Dere har lest eksamensteksten
* [ ] Koden er sjekket inn på github.com/pg209-2022 repository
* [ ] Dere har lastet opp en ZIP-fil lastet ned fra Github
* [ ] Dere har committed kode med begge prosjektdeltagernes GitHub-konto (alternativt: README beskriver hvordan dere har jobbet)

## README.md
* [ ] Inneholder link til Azure Websites deployment
* [ ] Inneholder en korrekt badge til GitHub Actions
* [ ] Beskriver hva dere har løst utover minimum
* [ ] Inneholder et diagram over databasemodellen

## Koden
* [ ] Oppfyller Java kodestandard med hensyn til indentering og navngiving
* [ ] Er deployet korrekt til Azure Websites
* [ ] Inneholder tester av HTTP og database-logikk
* [ ] Bruker Flyway DB for å sette opp databasen
* [ ] Skriver ut nyttige logmeldinger

## Basisfunksjonalitet
* [X] Kan velge hvilken bruker vi skal opptre som
* [ ] Viser eksisterende meldinger til brukeren
* [ ] Lar brukeren opprette en ny melding
* [ ] Lar brukeren svare på meldinger
* [ ] For A: Kan endre navn og annen informasjon om bruker
* [ ] For A: Meldingslisten viser navnet på avsender og mottakere

## Kvalitet
* [ ] Datamodellen er *normalisert* - dvs at for eksempel navnet på en meldingsavsender ligger i brukertallen, ikke i meldingstabellen
* [ ] Når man henter informasjon fra flere tabellen brukes join, i stedet for 1-plus-N queries (et for hovedlisten og et per svar for tilleggsinformasjon)
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
* [ ] Sendingspanel: viser personer/grupper du har meldinger i
  * [ ] Man skal kunne "opprette" en ny melding enten til person eller gruppe
* [ ] Meldingspanel: viser meldingene innenfor personen/gruppen du har valgt på venstre side
* [ ] Øverst i applikasjonen er den en knapp for innstillinger med følgende:
  * [ ] Endre opplysninger for pålogget bruker
  * [ ] Logge ut
