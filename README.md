# SpringBoot_CQRS_Command

## Table Of Content
1. [Run Process](#run-process)
2. [Development Process](#development-process)
3. [Design Document](#design-document)
4. [Highlight Functionality](#highlight-functionality)

## Run Process with Docker
```bash
#1 Prepare directory and clone from github
git clone https://github.com/Thee5176/SpringBoot_CQRS_Command.git
cd SpringBoot_CQRS_Command

#2 Run DB Container and make migration
docker compose up test_command_postgres -d
chmod +x mvnw
./mvnw flyway:migrate

#3  Build pakcage and run process with docker
./mvnw clean package
docker compose up -d --build

```
Check the running project at [port 8186](http://localhost:8186)

## Development Process
see [Main Repository](https://github.com/Thee5176/Accounting_CQRS_Project)

## Design Document

![Latest Design](https://github.com/user-attachments/assets/1b3d1503-7582-4724-86ee-6806e1fff05a)



# Highlight Functionality

## Springboot Command Service

| Feature                      | Description                                                                                                                                                                               | Reference Link                                                                                                                                                         |
|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Custom Object Mapper         | Configure ModelMapper Beans to customize field mapping between DTO and two Domain Entities                                                                                                | [ModelMapperConfig.java](https://github.com/Thee5176/SpringBoot_CQRS_Command/blob/develop/src/main/java/com/thee5176/ledger_command/Application/config/ModelMapperConfig.java) |
| Double Input Validation Logic| - **BalanceCheck**: Validate if sum of "amount" per "balanceType" matches (via custom validation class comparing with BigDecimal.ZERO)<br>- **Unique "Code of Account" Check**: Ensure no duplicates in LedgerItems list using DTO method + Hibernate UniqueElements | - [BalanceCheckValidator.java](https://github.com/Thee5176/SpringBoot_CQRS_Command/blob/develop/src/main/java/com/thee5176/ledger_command/Application/validation/BalanceCheckValidator.java)<br>- [LedgersEntryDTO.java - unique COA](https://github.com/Thee5176/SpringBoot_CQRS_Command/blob/develop/src/main/java/com/thee5176/ledger_command/Application/dto/LedgersEntryDTO.java#L37) |
| Transaction Management      | - **Create Transaction**: Manage create entity transaction incl. aggregated entities<br>- **Replacement Update Transaction**: Upsert logic via Java Stream for update/create/delete steps | - [Create Transaction](https://github.com/Thee5176/SpringBoot_CQRS_Command/blob/develop/src/main/java/com/thee5176/ledger_command/Domain/service/LedgerCommandService.java#L33)<br>- [Update Transaction](https://github.com/Thee5176/SpringBoot_CQRS_Command/blob/develop/src/main/java/com/thee5176/ledger_command/Domain/service/LedgerCommandService.java#L56) |

### Sequence Diagrams for Transaction Management

**Create Transaction**
```mermaid
sequenceDiagram
    actor User
    participant Controller as LedgerController
    participant Service as LedgerCommandService
    participant Repo as LedgerRepository
    participant ItemRepo as LedgerItemsRepository
    participant Mapper as LedgerMapper
    participant ItemsMapper as LedgerItemsMapper

    User->>Controller: POST /ledger (CreateLedgerDTO)
    Controller->>Service: createLedger(CreateLedgerDTO)
    Service->>Mapper: map(CreateLedgerDTO) -> Ledgers
    Service->>Repo: createLedger(Ledgers)
    Service->>ItemsMapper: map(CreateLedgerDTO) -> List<LedgerItems>
    loop for each LedgerItems
        Service->>ItemRepo: createLedgerItems(LedgerItems)
    end
    Service-->>Controller: (void)
    Controller-->>User: 200 OK / error
```

**Replacement Update Transaction**
```mermaid
sequenceDiagram
    actor User
    participant Controller as LedgerController
    participant Service as LedgerCommandService
    participant Mapper as LedgerItemsMapper
    participant Repo as LedgerItemRepository

    User->>Controller: POST /ledger (CreateLedgerDTO)
    Controller->>Service: updateLedger(LedgerEntryDTO)
    Service->>Repo: getLedgerItemsByLedgerId(ledgerEntryDTO.id)
    Service->>Mapper: map(ledgerEntryDTO)
    Service->>Service: Map existing items by COA
    Service->>Service: For each update item:
    alt COA exists
        Service->>Repo: updateLedgerItems(item)
    else COA does not exist
        Service->>Repo: createLedgerItems(item)
    end
    Service->>Service: For each existing item not in update list
    Service->>Repo: deleteLedgerItems(item.id)
```
## Test Result:
- Unit Test Coverage(JUnit): 
  ✔ ModelMapper
  <img width="387" height="246" alt="image" src="https://github.com/user-attachments/assets/6fe95d0d-22d4-4b88-ade2-c93a87c6b02d" />
  <img width="387" height="181" alt="image" src="https://github.com/user-attachments/assets/ff80ea47-3a82-4f23-873c-782367fa083f" />

  ✔ DTO
  <img width="387" height="118" alt="image" src="https://github.com/user-attachments/assets/2e079d17-7cb5-4e56-a5dd-ee5987088d87" />


Integration Test(Mockito):
  ✔ Service - Create Transaction
  ✘ Service - Update Transaction

API Test Case:
  ✔ Validation
  <img width="845" height="959" alt="image" src="https://github.com/user-attachments/assets/d7c1ceef-36e4-467b-9446-e564910f086d" />
  <img width="845" height="959" alt="image" src="https://github.com/user-attachments/assets/6b44cac0-d37b-4b49-887f-7523bbe943f7" />
  <img width="845" height="959" alt="image" src="https://github.com/user-attachments/assets/f214e9e3-8604-448d-973e-50d53a682c5d" />

  
  ✔ RestAPI Controller
  <img width="896" height="711" alt="image" src="https://github.com/user-attachments/assets/9b92ac0e-effe-433f-94d5-a10f45eb98f7" />
