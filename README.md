# PicPay Checkout SDK for Java (picpay4j)

SDK Java para integração **backend** com o [PicPay Checkout Padrão e Lightbox](https://developers-business.picpay.com/checkout/docs/default-checkout-and-lightbox-checkout). Permite criar pagamentos no servidor e obter a URL para redirecionar o consumidor (Checkout Padrão) ou exibir o pagamento em iframe (Checkout Lightbox).

## Requisitos

- Java 17+
- Maven 3.6+

## Instalação

Adicione a dependência ao seu `pom.xml` (ou instale localmente com `mvn install`):

```xml
<dependency>
    <groupId>balbucio.picpay4j</groupId>
    <artifactId>picpay4j</artifactId>
    <version>1.0.0</version>
</dependency>
```

[![](https://img.shields.io/badge/HyperPowered-Use%20the%20official%20repository-yellow?color=%23279BF8&cacheSeconds=3600)](https://maven.dev.hyperpowered.net/#/releases/balbucio/picpay4j/picpay4j/)

## Configuração

A API E-commerce do PicPay (Checkout Padrão) usa um **token do lojista** no header `x-picpay-token`. Obtenha o token no [Painel Lojista PicPay](https://painel-empresas.picpay.com/).

```java
import com.picpay.checkout.PicPayCheckoutConfig;
import com.picpay.checkout.PicPayCheckoutClient;

PicPayCheckoutConfig config = PicPayCheckoutConfig.sellerToken("SEU_TOKEN_PICPAY")
    .build();

PicPayCheckoutClient client = new PicPayCheckoutClient(config);
```

Para uso com a API de Checkout (OAuth 2.0), use as credenciais geradas em **Integrações > Checkout > Gerar Token** no Painel:

```java
PicPayCheckoutConfig config = PicPayCheckoutConfig.oauth("client_id", "client_secret")
    .build();
PicPayCheckoutClient client = new PicPayCheckoutClient(config);
```

## Uso: Criar pagamento (Checkout Padrão)

A requisição de criação de checkout **deve ser feita somente no backend**, nunca no frontend, para não expor credenciais.

1. Monte a requisição com dados do pedido e do comprador.
2. Chame `createPayment`.
3. Redirecione o consumidor para `response.getPaymentUrl()`.

```java
import com.picpay.checkout.PicPayCheckoutClient;
import com.picpay.checkout.model.*;

// Comprador
Buyer buyer = Buyer.builder()
    .firstName("João")
    .lastName("Silva")
    .document("12345678909")  // CPF sem formatação
    .build();

// Requisição de pagamento
CreatePaymentRequest request = CreatePaymentRequest.builder()
    .referenceId("PEDIDO-12345")           // ID único do pedido no seu sistema
    .callbackUrl("https://seusite.com/webhook/picpay")  // URL para notificações
    .returnUrl("https://seusite.com/obrigado")           // URL após pagamento
    .value(new BigDecimal("99.90"))
    .buyer(buyer)
    .build();

CreatePaymentResponse response = client.createPayment(request);

// Redirecione o usuário para a página de pagamento do PicPay
String urlCheckout = response.getPaymentUrl();  // usar em redirect HTTP 302
String idTransacao = response.getPicpayTransactionId();
```

## Uso: Checkout Lightbox

No **Checkout Lightbox** o pagamento ocorre dentro de um **iframe** na sua página, sem redirecionar o usuário. O backend usa a **mesma API**; a diferença é só no frontend (exibir a URL em iframe em vez de redirecionar).

1. No backend, chame `createPaymentForLightbox` com a mesma requisição do Checkout Padrão.
2. Retorne ao frontend a URL de `LightboxCheckoutSession.getIframePaymentUrl()`.
3. No frontend, exiba essa URL dentro de um iframe (ou modal com iframe).

```java
// Backend: mesma requisição do Checkout Padrão
CreatePaymentRequest request = CreatePaymentRequest.builder()
    .referenceId("PEDIDO-12345")
    .callbackUrl("https://seusite.com/webhook/picpay")
    .returnUrl("https://seusite.com/obrigado")
    .value(new BigDecimal("99.90"))
    .buyer(buyer)
    .build();

LightboxCheckoutSession session = client.createPaymentForLightbox(request);

// Envie ao frontend (ex.: JSON na resposta da sua API):
// - session.getIframePaymentUrl()  → src do iframe
// - session.getReferenceId()
// - session.getPicpayTransactionId()
```

### Exemplo de frontend (iframe)

Seu backend pode retornar um JSON com `iframePaymentUrl`. No frontend (HTML/JS), exiba o checkout em um overlay:

```html
<div id="picpay-lightbox" style="position:fixed;inset:0;background:rgba(0,0,0,0.5);display:none;align-items:center;justify-content:center;z-index:9999;">
  <div style="background:#fff;width:90%;max-width:500px;height:80vh;border-radius:8px;overflow:hidden;">
    <iframe id="picpay-iframe" src="" style="width:100%;height:100%;border:0;"></iframe>
  </div>
</div>

<script>
function openPicPayLightbox(iframePaymentUrl) {
  document.getElementById('picpay-iframe').src = iframePaymentUrl;
  document.getElementById('picpay-lightbox').style.display = 'flex';
}
// Quando o usuário concluir o pagamento, o PicPay pode redirecionar dentro do iframe
// ou comunicar via postMessage; feche o lightbox e redirecione conforme sua returnUrl.
</script>
```

### Diferença entre Padrão e Lightbox

| | Checkout Padrão | Checkout Lightbox |
|---|------------------|-------------------|
| **Backend** | `createPayment(request)` | `createPaymentForLightbox(request)` |
| **Resposta** | `CreatePaymentResponse` | `LightboxCheckoutSession` |
| **Frontend** | Redirect do usuário para `getPaymentUrl()` | Iframe com `getIframePaymentUrl()` |
| **Experiência** | Usuário sai da sua página | Usuário permanece na sua página |

O webhook (`callbackUrl`) e o fluxo de notificação são os mesmos nos dois modos.

### Expiração

Opcionalmente defina data/hora de expiração do pagamento:

```java
request = CreatePaymentRequest.builder()
    // ... outros campos
    .expiresAt(OffsetDateTime.now().plusHours(1))
    .build();
```

## Fluxo do pagamento

1. **Backend**: seu servidor chama `createPayment` (ou `createPaymentForLightbox`) e recebe a URL e os IDs.
2. **Checkout Padrão**: redirecione o usuário para `paymentUrl`. **Lightbox**: exiba `iframePaymentUrl` em um iframe.
3. **Pagamento**: o usuário paga com cartão, Wallet PicPay ou PIX na página/iframe do PicPay.
4. **Webhook**: o PicPay envia um POST para sua `callbackUrl` com o status do pagamento.
5. **Retorno**: o usuário é redirecionado para sua `returnUrl` após concluir (ou o iframe pode ser fechado).

Configure a URL de notificação (webhook) no Painel ou use a `callbackUrl` da requisição. A notificação virá com dados da transação para você atualizar o pedido.

## Webhook (notificações)

O PicPay envia um POST para a URL configurada (Painel ou `callbackUrl`) a cada mudança de status. O SDK oferece **objetos tipados** para o payload e um **parser** que não depende de framework: você passa o body e o header `event-type` e recebe um `WebhookEvent`.

### Objetos do evento

- **`WebhookEvent`** – evento raiz: `type`, `eventDate`, `id`, `merchantCode`, etc.
- **`ChargeEventData`** – quando `event-type: TransactionUpdateMessage` (Wallet, Credit, PIX): `status`, `amount`, `customer`, `merchantChargeId`, `transactions[]`.
- **`Challenge3dsData`** – quando `event-type: Challenge3dsUpdateMessage`: `status`, `paresStatus`, `eciRaw`, `merchantChargeId`, `chargeId`.
- **`WebhookTransaction`** – cada item em `transactions`, com `paymentType`, `status`, `wallet` / `credit` / `pix` conforme o método de pagamento.

### Parser (body + header)

Use com qualquer framework (Servlet, Spring, JAX-RS, etc.): basta o **body** (String ou InputStream) e o **header** `event-type`.

```java
import com.picpay.checkout.webhook.*;

// No seu endpoint (ex.: request.getInputStream(), request.getHeader("event-type"))
WebhookEvent event = client.parseWebhookEvent(bodyString, eventTypeHeader);

// Ou sem o client (parser estático, sem dependência de framework):
WebhookEvent event = WebhookParser.parse(bodyString, eventTypeHeader);
// Com InputStream: WebhookParser.parseFromStream(bodyInputStream, eventTypeHeader);

if (event.isTransactionUpdate()) {
    ChargeEventData data = event.getChargeData();
    String merchantChargeId = data.getMerchantChargeId();
    String status = data.getStatus(); // ex.: PAID, AUTHORIZED, CAPTURED
    for (WebhookTransaction tx : data.getTransactions()) {
        String paymentType = tx.getPaymentType(); // WALLET, CREDIT, PIX
        String txStatus = tx.getStatus();
    }
} else if (event.isChallenge3ds()) {
    Challenge3dsData data = event.getChallenge3dsData();
    String status = data.getStatus(); // ex.: Approved
}
```

**InputStream:**

```java
WebhookEvent event = client.parseWebhookEvent(request.getInputStream(), request.getHeader("event-type"));
```

**Autenticação:** o PicPay envia o token no header `Authorization`. Valide comparando com o token exibido no Painel (Ajustes > Meu checkout > URL de notificação). O SDK não valida o token; isso fica a cargo do seu endpoint.

Documentação: [Webhook PicPay](https://developers-business.picpay.com/checkout/docs/webhook).

## Tratamento de erros

```java
import com.picpay.checkout.exception.PicPayApiException;

try {
    CreatePaymentResponse response = client.createPayment(request);
    // ...
} catch (PicPayApiException e) {
    int status = e.getHttpStatus();
    String body = e.getResponseBody();
    String code = e.getBusinessCode();
    // tratar conforme status/código
}
```

## Documentação PicPay

- [Checkout Padrão e Lightbox](https://developers-business.picpay.com/checkout/docs/default-checkout-and-lightbox-checkout)
- [Autenticação](https://developers-business.picpay.com/checkout/docs/authentication)
- [Webhook](https://developers-business.picpay.com/checkout/docs/webhook)

## Licença

Este projeto não é oficial do PicPay. Use conforme a documentação e termos do PicPay.
