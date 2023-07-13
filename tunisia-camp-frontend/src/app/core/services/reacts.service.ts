import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { React } from '../models/react.model';

@Injectable()
export class ReactsService {
  constructor (
    private apiService: ApiService
  ) {}

  add(react: React): Observable<React> {
    return this.apiService.post('/reactions', react);
  }

  edit(react: React): Observable<React> {
    return this.apiService.put('/reactions', react);
  }

  destroy(reactId: number) {
    return this.apiService.delete(`/reactions/${reactId}`);
  }
}
