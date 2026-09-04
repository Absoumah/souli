import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideTranslateLoader, provideTranslateService, TranslateLoader } from '@ngx-translate/core';
import { Observable, of } from 'rxjs';
import fr from './i18n/fr.json';

import { routes } from './app.routes';

class StaticTranslateLoader extends TranslateLoader {
  override getTranslation(_lang: string): Observable<any> {
    return of(fr);
  }
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideTranslateService({
      fallbackLang: 'fr',
      loader: provideTranslateLoader(() => new StaticTranslateLoader())
    }),
    provideHttpClient(),
  ]
};
