import { inject, Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class SnackbarService {
  private readonly snackBar = inject(MatSnackBar);
  private readonly translate = inject(TranslateService);

  handleSuccess(message: string): void {
    this.snackBar.open(
      message,
      this.translate.instant('common.actions.close'),
      { duration: 5000, panelClass: ['snackbar-success'] }
    );
  }

  handleError(message: string): void {
    this.snackBar.open(
      message,
      this.translate.instant('common.actions.close'),
      { duration: 7000, panelClass: ['snackbar-error'] }
    );
  }
}
