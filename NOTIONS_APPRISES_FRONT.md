# Notions apprises — Frontend

## Angular

### Composants standalone

Les composants Angular peuvent déclarer directement leurs dépendances dans la propriété `imports` du décorateur `@Component`.

### Traductions et i18n

Les textes métier sont centralisés dans les fichiers JSON de langue et affichés avec le pipe `translate`.

```html
{{ 'auth.email' | translate }}
```

Une clé de base comme `readonly tKey = 'auth'` évite de répéter le préfixe dans les templates.

## Formulaires

### Formulaires réactifs

`FormBuilder` permet de construire un `FormGroup` et de centraliser les valeurs et validations dans le TypeScript.

```ts
readonly form = this.formBuilder.nonNullable.group({
  email: ['', [Validators.required, Validators.email]],
  password: ['', Validators.required]
});
```

Les champs HTML sont reliés au formulaire avec `formGroup` et `formControlName`.

### `ngSubmit` et `click`

`(ngSubmit)` est préférable pour soumettre un formulaire : il fonctionne avec le bouton de soumission et avec la touche `Entrée`, tout en respectant la sémantique HTML. Un `(click)` ne couvre que le clic direct sur le bouton.

### `mat-error`

`mat-error` affiche un message lié à un validator Angular dans un `mat-form-field`. Les messages doivent être traduits et affichés lorsque le champ est invalide et touché.

### `autocomplete`

L’attribut `autocomplete` aide le navigateur et les gestionnaires de mots de passe à identifier le contenu attendu :

- `given-name` : prénom ;
- `family-name` : nom ;
- `email` : adresse e-mail ;
- `current-password` : mot de passe existant lors d’une connexion ;
- `new-password` : mot de passe à créer lors d’une inscription.

## Angular Material

Les champs utilisent `mat-form-field` et `matInput`, et les boutons utilisent les directives Material comme `mat-flat-button`.

Les modules Material nécessaires doivent être importés dans le composant standalone :

```ts
imports: [MatButtonModule, MatFormFieldModule, MatInputModule]
```

Un thème Material doit être déclaré globalement dans `angular.json` ou dans les styles globaux.

## Modèles et DTO frontend

Les modèles TypeScript décrivent les objets échangés avec le backend :

- `LoginRequest` décrit les données envoyées pour une connexion ;
- `RegisterRequest` décrit les données envoyées pour une inscription ;
- `UserDto` décrit un utilisateur retourné par l’API ;
- `AuthResponse` décrit les tokens retournés après connexion.

Le champ `confirmPassword` sert uniquement à la validation frontend et ne fait pas partie de `RegisterRequest`.

Les types JSON sont adaptés entre Java et TypeScript : `Long` devient généralement `number` et `Instant` devient une chaîne ISO (`string`).

Les enums frontend doivent rester alignées avec celles du backend :

```ts
export enum Role {
  ADMIN = 'ADMIN',
  PLAYER = 'PLAYER'
}
```

## Réponses génériques

Le frontend utilise `GenericResponse<T>` pour typer l’enveloppe commune des réponses de l’API :

```ts
export interface GenericResponse<T> {
  status: number;
  message: string;
  result: T;
}
```

Le type générique permet de réutiliser la même enveloppe avec différents résultats :

```ts
Observable<GenericResponse<UserDto>>
Observable<GenericResponse<AuthResponse>>
```

## Services HTTP

Un service Angular centralise les appels à l’API. `HttpClient` est injecté avec `inject()` et les méthodes retournent des `Observable`.

Les observables `HttpClient` sont froids : l’appel HTTP est exécuté lorsqu’un composant s’abonne avec `subscribe()`.

### Gestion des retours avec une Snackbar

La présentation des succès et des erreurs d’appels API est centralisée dans `SnackbarService`. `AuthService` appelle `handleSuccess` dans `tap` et `handleError` dans `catchError`, afin que tous les composants bénéficient du même comportement.

Le composant déclenche uniquement l’appel du service et ne duplique pas la logique d’affichage des notifications.

## SCSS et organisation des styles

Les styles propres à un composant restent dans son fichier SCSS. Les règles générales et réutilisables sont centralisées dans `souli-web/src/styles/_mixins.scss`.

Les composants incluent ensuite le mixin global :

```scss
@use '../../../../../styles/mixins' as app;

@include app.auth-form-layout(430px, 58px);
```

Les styles spécifiques, comme la grille prénom/nom et son comportement mobile, restent dans le SCSS du composant `register`.

Le responsive doit être prévu avec des media queries adaptées aux petits écrans.

## Proxy Angular et CORS

En développement, le proxy Angular est défini dans `proxy.conf.json` et chargé par le script `npm start`.

L’URL d’API doit rester relative :

```ts
apiUrl: '/api'
```

Le navigateur appelle ainsi le frontend sur `http://localhost:4200/api/...`. Le serveur Angular transmet ensuite la requête au backend (`http://backend:8080` dans Docker), ce qui évite une requête cross-origin dans le navigateur.

Le proxy est une solution de développement. En production, si le frontend et l’API sont servis depuis des origines différentes, CORS doit être configuré côté backend.
