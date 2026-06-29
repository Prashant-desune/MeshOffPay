# MeshOffPay

MeshOffPay is an offline-first UPI-style payment prototype built with Spring Boot. It demonstrates how an encrypted payment instruction can be created without internet, passed through a nearby device mesh, and settled when a bridge device reaches the backend.

This project is designed as a clear portfolio/project-review implementation of deferred offline payment settlement, mesh packet forwarding, encryption, replay protection, and idempotent backend processing.

## Project Details

| Item | Details |
| --- | --- |
| Project name | MeshOffPay |
| Author | Prashant-desune |
| Email | prashant.desune@gmail.com |
| GitHub | [Prashant-desune](https://github.com/Prashant-desune) |
| Java package | `com.prashant.meshPayOff` |
| Maven group | `com.prashant.meshPayOff` |
| Artifact | `meshoffpay` |
| Backend | Spring Boot |
| UI | Thymeleaf dashboard |
| Database | H2 in-memory database |

## What This Project Does

MeshOffPay models this flow:

1. A sender creates an offline payment packet.
2. The packet is encrypted using hybrid cryptography.
3. The encrypted packet moves through a simulated local mesh network.
4. A bridge device with internet uploads the packet to the backend.
5. The backend checks for duplicate/replayed/tampered packets.
6. The transaction is settled exactly once.
7. The dashboard shows device state, account balances, settlement results, and activity logs.

## Key Features

- Offline payment packet creation
- Hybrid RSA + AES-GCM encryption
- Mesh device simulation
- Bridge upload simulation
- Idempotency protection for duplicate packet delivery
- Replay/freshness validation
- Tamper detection through authenticated encryption
- Transaction ledger
- Account balance tracking
- Clean Spring Boot + Thymeleaf dashboard
- H2 database for zero-setup local execution
- JUnit tests for crypto and idempotent settlement behavior

## Tech Stack

| Layer | Technology |
| --- | --- |
| Language | Java 17 |
| Framework | Spring Boot 3.3.5 |
| Web | Spring Web |
| UI | Thymeleaf, HTML, CSS, JavaScript |
| Persistence | Spring Data JPA |
| Database | H2 in-memory DB |
| Build tool | Maven Wrapper |
| Testing | JUnit / Spring Boot Test |
| Crypto | RSA-OAEP + AES-GCM |

## Architecture

```text
Sender Device
    |
    | create payment instruction
    | encrypt payload
    v
Mesh Packet
    |
    | gossip through virtual devices
    v
Bridge Device
    |
    | upload when internet is available
    v
Spring Boot Backend
    |
    | hash packet
    | check idempotency
    | decrypt
    | validate freshness
    | settle transaction
    v
Transaction Ledger
```

## Main Modules

```text
src/main/java/com/prashant/meshPayOff/
├── MeshOffPayApplication.java
├── config/
│   └── AppConfig.java
├── controller/
│   ├── ApiController.java
│   └── DashboardController.java
├── crypto/
│   ├── HybridCryptoService.java
│   └── ServerKeyHolder.java
├── model/
│   ├── Account.java
│   ├── AccountRepository.java
│   ├── MeshPacket.java
│   ├── PaymentInstruction.java
│   ├── Transaction.java
│   └── TransactionRepository.java
└── service/
    ├── BridgeIngestionService.java
    ├── IdempotencyService.java
    ├── MeshSimulatorService.java
    ├── PacketFactoryService.java
    ├── SettlementService.java
    └── VirtualDevice.java
```

## UI

The UI is a Spring Boot Thymeleaf page:

```text
src/main/resources/templates/dashboard.html
```

It includes:

- Step-by-step payment flow
- Offline packet creation
- Mesh gossip action
- Bridge upload action
- Mesh devices panel
- Account balances table
- Transaction ledger
- Activity log

Open the dashboard at:

```text
http://localhost:8080
```

## API Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| `GET` | `/` | Dashboard UI |
| `GET` | `/api/server-key` | Server public key information |
| `POST` | `/api/payments/offline` | Create and queue encrypted offline payment packet |
| `GET` | `/api/mesh/state` | Current mesh state |
| `POST` | `/api/mesh/gossip` | Run one gossip round |
| `POST` | `/api/mesh/flush` | Upload bridge-held packets to backend |
| `POST` | `/api/mesh/reset` | Reset mesh state and idempotency cache |
| `POST` | `/api/bridge/ingest` | Ingest one mesh packet |
| `GET` | `/api/accounts` | List account balances |
| `GET` | `/api/transactions` | List recent transactions |

## How To Run Locally

### Prerequisites

- Java 17 or newer
- No external database required
- Maven installation is not required because the project includes Maven Wrapper

Check Java:

```bash
java -version
```

### Start The App

```bash
./mvnw spring-boot:run
```

Then open:

```text
http://localhost:8080
```

### Build The Project

```bash
./mvnw clean package -DskipTests
```

### Run Tests

```bash
./mvnw test
```

## Example Dashboard Flow

1. Choose sender, receiver, amount, and PIN.
2. Click `Create Packet`.
3. Click `Run Gossip`.
4. Click `Upload to Backend`.
5. Check the Transaction Ledger.
6. Check account balance changes.
7. Try bridge upload again to see duplicate protection.

## Security Concepts Demonstrated

### Encryption

The payment payload is encrypted before it enters the mesh. Intermediary devices only carry opaque ciphertext.

The project uses:

- RSA-OAEP to wrap the AES key
- AES-GCM for authenticated payload encryption

### Idempotency

The backend hashes the ciphertext and claims that hash before settlement. This prevents the same packet from being settled more than once if several bridge devices upload it.

### Replay Protection

Payment instructions include timestamps and nonces. Old packets are rejected by the backend freshness check.

### Tamper Detection

AES-GCM detects modified ciphertext. A tampered packet fails decryption and does not reach settlement.

## Testing Focus

The test suite covers:

- Concurrent duplicate packet delivery
- Exactly-once settlement behavior
- Invalid/tampered ciphertext rejection
- Crypto round-trip behavior

Run:

```bash
./mvnw test
```

## Deployment Notes

For cloud hosting, use a platform that can run a Java Spring Boot backend.

Recommended free/simple options:

- Render
- Railway
- Koyeb
- Fly.io

GitHub Pages is not enough for the full app because this project needs a running Java backend.

### Render Example

Build command:

```bash
./mvnw clean package -DskipTests
```

Start command:

```bash
java -jar target/meshoffpay-0.0.1-SNAPSHOT.jar
```

For hosted environments, use:

```properties
server.port=${PORT:8080}
```

## Important Note

This is a project prototype and simulation. It is not a production UPI implementation and does not connect to real banking/UPI infrastructure. It demonstrates backend design ideas such as encryption, mesh forwarding simulation, idempotency, and deferred settlement.

## Author

**Prashant-desune**

- GitHub: [https://github.com/Prashant-desune](https://github.com/Prashant-desune)
- Email: prashant.desune@gmail.com
