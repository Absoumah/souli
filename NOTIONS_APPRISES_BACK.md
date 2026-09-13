# Notions apprises — Backend

## DTO et réponses API

Les DTO séparent les objets exposés par l’API des entités internes de l’application. Ils décrivent les données entrantes et sortantes des contrôleurs.

Le backend utilise `ApiResponseDto<T>` pour envelopper ses réponses :

```java
public record ApiResponseDto<T>(
        int status,
        String message,
        T result
) { }
```

Cette structure est générique : `result` peut contenir un `UserDto`, un `AuthResponseDto` ou un autre objet.

## Authentification

Le contrôleur d’authentification expose notamment :

- `POST /api/auth/register` pour créer un utilisateur ;
- `POST /api/auth/login` pour obtenir les tokens ;
- `POST /api/auth/refresh` pour renouveler les tokens.

`LoginRequestDto` reçoit l’e-mail et le mot de passe. `RegisterRequestDto` reçoit l’e-mail, le mot de passe, le prénom, le nom et éventuellement le téléphone.

`AuthResponseDto` retourne l’access token, le refresh token, le type de token et sa durée de validité.

## CORS

### Origine

Une origine est définie par le protocole, le domaine et le port. Ces deux adresses sont différentes :

- frontend : `http://localhost:4200` ;
- backend : `http://localhost:8080`.

### Rôle de CORS

CORS (*Cross-Origin Resource Sharing*) permet au backend d’indiquer quels frontends peuvent appeler son API depuis un navigateur.

Dans `application.yaml`, une origine peut être déclarée :

```yaml
cors:
  allowed-origins:
    - ${CORS_ALLOWED_ORIGIN:http://localhost:4200}
```

Cette propriété ne configure pas CORS à elle seule. Elle doit être lue par une configuration Spring (`CorsConfigurationSource`, `WebMvcConfigurer`, etc.) et CORS doit être activé dans Spring Security.

### Prévalidation `OPTIONS`

Pour une requête JSON, le navigateur peut envoyer d’abord une requête `OPTIONS` appelée *preflight*. Le backend doit répondre avec les en-têtes CORS attendus, notamment :

```http
Access-Control-Allow-Origin: http://localhost:4200
```

Sinon, le navigateur bloque la réponse, même si l’endpoint est autorisé par Spring Security.

### Proxy Angular

En développement, un proxy Angular peut éviter cette contrainte en faisant passer les appels par le même domaine que le frontend. CORS reste nécessaire lorsque le frontend et l’API sont réellement servis depuis des origines différentes, notamment en production.

## Spring Security

Autoriser une route avec `permitAll()` permet son accès sans authentification Spring Security, mais cela ne configure pas CORS. Les deux mécanismes sont distincts :

- CORS contrôle les appels cross-origin effectués par le navigateur ;
- Spring Security contrôle l’authentification et les autorisations de l’API.
