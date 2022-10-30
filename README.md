# Java Chat | Leonardo Di Carlo | 5C-IA

## Tecnologie Utilizzate
- Java 18 (Maven)
- Visual Studio Code

## Architettura Messaggi
I messaggi saranno serializzati interamente in JSON

### Client
Questa sezione definisce tutti gli oggetti che saranno trasmessi dal Client verso il Server

#### Notifica di connessione
Questa notifica verrà inviata dal Client al momento della connessione
```json
{
 "type":"notification",
 "user": "Foo",
 "action": "connect"
}
```
#### Notifica di disconnessione
Questa notifica verrà inviata dal Client al momento della disconnessione
```json
{
 "type":"notification",
 "user": "Foo",
 "action": "disconnect"
}
```

#### Messaggio generico pubblico
In questo caso il client con nome "Foo" sta inviando un messaggio pubblico visibile da tutti
```json
{
 "type":"message",
 "author": "Foo",
 "target": "*",
 "content": "This is an example message"
}
```
#### Messaggio generico privato
In questo caso il client con nome "Foo" sta inviando un messaggio privato che solo "Bar" riceverà
```json
{
 "type":"message",
 "author": "Foo",
 "target": "Bar",
 "content": "This is an example of a private message"
}
```

### Server
Questa sezione definisce tutti gli oggetti che saranno trasmessi dal Server verso il Client

#### Tabella Status
| Status | Significato  |
|:-:|---|
| 200  | Richiesta valida e processata correttamente  |
| 403 | Richiesta valida con parametri non consentiti |
| 422 | Richiesta non valida |

#### Risposta alle notifiche
In questo caso il Server ha accettato la connessione del Client
```json
{
 "status": 200,
 "response": "OK"
}
```
In questo caso il Server ha rifiutato la connessione del Client comunicando il motivo; il nome inserito dall'utente è gia stato utilizzato
```json
{
 "status": 404,
 "response": "This name is already in use, try changing it"
}
```
In questo caso il Server ha rifiutato la connessione del Client comunicando il motivo; 
il formato della richiesta è errato
```json
{
 "status": 422,
 "response": "Could not parse request object"
}
```

### Risposta ai messaggi
In questo caso il Server ha ricevuto il messaggio dal Client e l'ha inoltrato al target
```json
{
 "status": 200,
 "response": "OK"
}
```
In questo caso il Server ha ricevuto il messaggio dal Client ma non è riuscito ad inoltrarlo al client.
Questa condizione potrebbe verificarsi quando solo un client è connesso oppure quando il target non esiste
```json
{
 "status": 404,
 "response": "Target not found, the specified target may not exist or you're the only client connected"
}
```

## Diagrammi

### Diagramma della connessione
```mermaid
sequenceDiagram
    participant Client
    participant Server
    participant Partecipanti
    Client->>Server: richiede la connessione
        Server->>Server: controlla validità username
    Server->>Client: accetta la connessione
        Server ->> Partecipanti: notifica del nuovo client connesso
```
---
### Diagramma della disconnessione
```mermaid
sequenceDiagram
    participant Client
    participant Server
    participant Partecipanti
    Client->>Server: avvisa della disconnessione
    Server->>Client: disconnette
        Server ->> Partecipanti: notifica del client disconesso
```
---
### Diagramma del messaggio pubblico
```mermaid
sequenceDiagram
    participant Client
    participant Server
    participant Partecipanti
    Client->>Server: invia il messaggio
        Server->>Server: controlla la validità
    Server ->> Partecipanti: inoltra il messaggio
    Server->>Client: messaggio inviato
```
---
### Diagramma del messaggio privato
```mermaid
sequenceDiagram
    participant Client
    participant Server
    Client->>Server: invia il messaggio
        Server->>Server: controlla la validità
        Server->>Server: cerca il target
    Server ->> Target: inoltra il messaggio
    Server->>Client: messaggio inviato
```
