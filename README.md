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
- 
![image](https://user-images.githubusercontent.com/24465003/201512486-9f3b849d-dbc3-45c2-bf48-15b839ddf027.png)

### Oppdatere bruker
![image](https://user-images.githubusercontent.com/24465003/201512783-d7051585-2ac2-49da-84d3-ecd62a9d4b2b.png)
![image](https://user-images.githubusercontent.com/24465003/201512796-fcf180f7-7a36-4472-915e-61de68805c63.png)

### Ny meldingstråd:
Fyll inn følgende:
- Tittel
- Melding
- Velg mottaker(e)
- 
![image](https://user-images.githubusercontent.com/24465003/201512806-020c8e97-1d3f-4c13-b7ca-40f02a513de2.png)
![image](https://user-images.githubusercontent.com/24465003/201512858-833a401c-e119-4368-9ff2-fcdf2113e86d.png)

Meldingstråden vil så dukke opp her, og du kan velge den(klikke på den) for å se meldinger:

![image](https://user-images.githubusercontent.com/24465003/201512967-c6e74d61-2a0c-4e83-b9f0-617658d33b0a.png)
![image](https://user-images.githubusercontent.com/24465003/201512995-da7e69dc-0a82-465c-8ee1-d3fbe3fe46e2.png)

### Bytt til annen bruker for å se effekt:
Hvis du går inn på en annen bruker vil den ha fått opp et merke som viser hvor mange uleste meldinger man har innenfor tråden:

![image](https://user-images.githubusercontent.com/24465003/201513029-f7195f0e-1e6d-42b1-9f61-95678b338976.png)
![image](https://user-images.githubusercontent.com/24465003/201513043-f6a90342-24a8-474b-96fa-38d65f9494d3.png)

### Bytt tilbake til orginal:
![image](https://user-images.githubusercontent.com/24465003/201513054-906719da-63ed-449d-b915-aacabc7b1d87.png)
![image](https://user-images.githubusercontent.com/24465003/201513056-369502c8-3c90-476a-8e48-24cedba899b9.png)
![image](https://user-images.githubusercontent.com/24465003/201513069-748628eb-9108-492a-9656-34488a44176f.png)

Trykk enter eller på send knappen for å sende en melding.

![image](https://user-images.githubusercontent.com/24465003/201513079-36df78d4-81ef-4200-b270-df1a5e74c6e7.png)


