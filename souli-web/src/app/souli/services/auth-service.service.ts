import { Injectable } from '@angular/core';
import {RegisterRequest} from '../models/souli.model';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  private http = inject(Http)

  register(register: RegisterRequest) {

  }

}
