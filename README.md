# 💳 Bank Transactions Microservice

This microservice is responsible for managing **bank accounts** and **financial transactions**. It collaborates with the `bank-clients` microservice to validate and process transactions using **asynchronous messaging** and **reactive HTTP** communication.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot 3
- Gradle
- PostgreSQL
- WebFlux WebClient
- RabbitMQ
- Docker (deployed via `bank-clients`)

---

## 🧩 Architecture

- **Template Method Pattern** to organize service logic.
- **Saga Choreography Pattern** for distributed transactions.
- Uses **RabbitMQ** for messaging between services.
- Uses **WebClient** for non-blocking HTTP calls to `bank-clients`.

---

## ✅ Testing

- ✅ Unit Tests
- ✅ Integration Tests

---

## 🔁 Transaction Flow

This service does **not** expose a direct controller to create transactions.

Instead:

1. A transaction is initiated via a **REST endpoint on `bank-clients`**.
2. `bank-clients`:
    - Validates the client and transaction data.
    - If valid, **publishes the transaction to a RabbitMQ queue**.
3. `bank-transactions`:
    - **Listens to the queue via a RabbitMQ listener**.
    - Processes the transaction when it is received from the queue.

This ensures that all validation logic resides in `bank-clients`, while transaction execution is handled solely by `bank-transactions`.

---

## 🐳 Deployment

This service is **automatically deployed** using the `deploy.sh` script from the `bank-clients` repository.

> ⚠️ **Note:** Both projects must be in the **same root directory** before running the script.

### 🔧 To Deploy

```bash
cd bank-clients
./deploy.sh
