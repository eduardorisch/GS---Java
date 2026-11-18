## 1. Integrantes

| Nome completo         | RM     |
| --------------------- | ------ |
| Eduardo Augusto Risch | 555212 |
| Davi Queiroz Santos   | 557483 |

---

## 2. O problema

Buracos nas vias, iluminação pública queimada, lixo acumulado, calçadas quebradas e bueiros entupidos fazem parte do cotidiano das cidades. Quem convive com o problema, principalmente nos bairros de periferia, onde o retorno do poder público costuma ser mais lento, enfrenta três dificuldades:
- **Reportar é desorganizado:** os pedidos se espalham por telefone, redes sociais e aplicativos diferentes, sem padrão.
- **Falta de acompanhamento:** o cidadão registra o problema e nunca sabe se alguém foi designado ou se ele foi resolvido.
- **Dificuldade de priorizar:** quem coordena o atendimento não tem uma visão única do que está aberto, de quem está atendendo e de quem está livre.
**Cidadãos beneficiados:** moradores de bairros urbanos que querem registrar e acompanhar problemas do espaço público, e agentes públicos ou empresas contratadas que precisam organizar o atendimento.

## 3. A solução

O **ZeladoriaPovo** é uma plataforma colaborativa de zeladoria de espaços públicos. O cidadão registra uma **ocorrência** (por exemplo, um buraco na avenida), e as **equipes de campo**, cadastradas por especialidade, assumem e finalizam o atendimento. O status da ocorrência e da equipe é atualizado de forma coordenada entre os dois serviços, e o cidadão consegue acompanhar o andamento.

### Ciclo de vida do atendimento

1. O cidadão registra uma ocorrência, que nasce com status **ABERTA** e sem equipe.
2. Uma equipe **assume** a ocorrência. O `ms-equipe` valida (via OpenFeign) se a ocorrência existe, está ABERTA e se a categoria combina com a especialidade da equipe. Se estiver tudo certo, a ocorrência passa para **EM_ATENDIMENTO** e a equipe também.
3. Ao concluir o serviço, a equipe **finaliza** a ocorrência. A ocorrência vira **RESOLVIDA** e a equipe volta a **DISPONIVEL** e o ocorrencia_id fica nulo.
4. Se o `ms-ocorrencia` estiver fora do ar, o Circuit Breaker aciona o **fallback**: a equipe continua disponível, nada é alterado e a API devolve uma mensagem.

### ODS relacionados

- **ODS 11:** Cidades e comunidades sustentáveis
- **ODS 10:** Redução das desigualdades (atenção a bairros com retorno mais lento do poder público)
- **ODS 9:** Indústria, inovação e infraestrutura

---

## 4. Pré-requisitos
Clone o repositório:

```bash
git clone https://github.com/eduardorisch/GS---Java.git
```

---

## 5. Como executar para teste
Na raiz do repositório, execute nesta ordem:

**1. Construir as imagens** (a primeira vez demora alguns minutos, porque o Maven baixa as dependências dentro do container):
```bash
docker build -t eureka-server:latest ./eureka-server
docker build -t api-gateway:latest ./api-gateway
docker build -t ms-ocorrencia:latest ./ms-ocorrencia
docker build -t ms-equipe:latest ./ms-equipe
```

**2. Criar a rede Docker**:
```bash
docker network create microservices-net
```

**3. Subir tudo:**
```bash
docker compose up -d
```

**4. Depois disso podera testar com o swagger os Microserviços**

**Microserviço de Equipes**

http://localhost:8083/swagger-ui/index.html 

**Microserviço de Ocorrências**

http://localhost:8081/swagger-ui/index.html