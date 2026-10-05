import { Routes } from '@angular/router';
import { SouliComponent } from './souli/souli.component';
import { LoginComponent } from './souli/features/auth/login/login.component';
import { RegisterComponent } from './souli/features/auth/register/register.component';

export const routes: Routes = [
  { path: '', component: SouliComponent },
  {
    path: 'auth',
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'register' },
      { path: 'login', component: LoginComponent },
      { path: 'register', component: RegisterComponent }
    ]
  }
];
