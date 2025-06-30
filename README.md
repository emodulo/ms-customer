# ms-customer

O `ms-customer` é o microserviço responsável pelo gerenciamento dos clientes da plataforma ShopCar. Ele permite o cadastro, edição, exclusão e consulta de informações do cliente, mantendo a integridade e a segurança dos dados pessoais.

## Funcionalidades

- Cadastro de cliente vinculado ao `sub` do usuário Cognito
- Edição, consulta e remoção do próprio cliente autenticado
- Inclusão de endereço e e-mail no perfil do cliente
- Consulta protegida por autenticação
- Exposição via API REST

## Arquitetura

Este serviço foi desenvolvido com:

- **Spring Boot** com arquitetura **Hexagonal (Ports and Adapters)**
- Banco de dados **MongoDB**
- Autenticação via **AWS Cognito** (JWT)
- Implantação via **Kubernetes** com HPA, ConfigMap, Secrets, Ingress
- CI/CD com **GitHub Actions**

## Estrutura do Banco de Dados

Banco: `emodulo` (MongoDB)

Documento: `customer`

Exemplo de estrutura:

```json
{
  "id": "68631ade7dea532b9347e4fa",
  "name": "João Carlos",
  "document": "1234567890",
  "email": "kj.juniorzinho@gmail.com",
  "authProvider": "cognito",
  "externalId": "54c88468-60d1-70d3-cae6-3be9e1e3b574",
  "address": {
    "street": "Avenida Industrial",
    "number": "100",
    "city": "Santo André",
    "state": "São Paulo",
    "zip": "09080-501"
  }
}
```

## Autenticação

O `ms-customer` utiliza **AWS Cognito** como Identity Provider. Todas as operações são feitas em nome do usuário autenticado (baseado no `sub` do token JWT).

## 📡 Endpoints Disponíveis

| Método | Rota                     | Descrição                                 | Autenticação |
|--------|--------------------------|--------------------------------------------|-------------|
| POST   | `/api/v1/customers`      | Cadastra um novo cliente                   | ✅          |
| GET    | `/api/v1/customers`      | Recupera os dados do cliente autenticado   | ✅          |
| GET    | `/api/v1/customers/{id}` | Recupera os dados do cliente autenticado   | ✅          |
| PUT    | `/api/v1/customers/{id}` | Atualiza os dados do cliente autenticado   | ✅          |
| DELETE | `/api/v1/customers/{id}` | Remove o próprio cadastro                  | ✅          |

Para acessar a documentação da api localmente, acessar o endpoint: http://localhost:8081/swagger-ui/index.html

## Kubernetes

O serviço está preparado para rodar em Kubernetes com os seguintes recursos:

- `Deployment` com probes
- `Service` tipo `ClusterIP`
- `PersistentVolumeClaim` para o MongoDB
- `ConfigMap` e `Secret` para variáveis e senhas
- `Ingress` com roteamento por hostname/path
- `HPA` com uso de CPU/memória

## Como instalar via Docker (modo local)

> Repositório de infraestrutura: [`emodulo/shopcar-infra`](https://github.com/emodulo/shopcar-infra)

```powershell
powershell -ExecutionPolicy Bypass -File shopcar-via-docker.ps1
```

## Como instalar via Kubernetes (modo cluster)

```powershell
powershell -ExecutionPolicy Bypass -File shopcar-via-kubernetes.ps1
```

---

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Security + JWT
- MongoDB
- Kubernetes (YAML)
- AWS Cognito
- GitHub Actions
- Docker

---

## Estrutura do Projeto

```
ms-customer/
├── domain/
├── application/
├── adapter-in/
├── adapter-out-database/
├── port-in/
├── port-out/
├── config/
└── kubernetes/
```

---

## Contribuidores

- Kreverson Silva – Arquiteto e Desenvolvedor Principal