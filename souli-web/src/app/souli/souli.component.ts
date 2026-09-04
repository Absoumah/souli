import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TranslatePipe, TranslateService } from '@ngx-translate/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-souli',
  imports: [FormsModule, TranslatePipe, RouterLink],
  templateUrl: './souli.component.html',
  styleUrl: './souli.component.scss'
})
export class SouliComponent {
  private readonly translate = inject(TranslateService);

  city = '';
  matchType = '5v5';
  searchMessageKey = '';
  searchMessageParams: Record<string, string> = {};

  searchFields(): void {
    const location = this.city.trim() || this.translate.instant('hero.defaultLocation');
    this.searchMessageKey = 'hero.searchMessage';
    this.searchMessageParams = { location };
  }
}
