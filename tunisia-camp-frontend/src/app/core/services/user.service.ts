import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject ,  ReplaySubject } from 'rxjs';

import { ApiService } from './api.service';
import { User } from '../models';
import { distinctUntilChanged } from 'rxjs/operators';


@Injectable()
export class UserService {
  private currentUserSubject = new BehaviorSubject<User>({} as User);
  public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(1);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  constructor (
    private apiService: ApiService,
    private http: HttpClient
  ) {}

  // Verify JWT in localstorage with server & load user's info.
  // This runs once on application startup.
  populate() {
    this.setDefaultUser();
  }

  getCurrentUser(): User {
    return this.currentUserSubject.value;
  }

  setDefaultUser() {   
    this.apiService.get('/defaultUser')
    .subscribe(
      (userData) => {
        this.currentUserSubject.next(userData);
        this.isAuthenticatedSubject.next(true);
      });
  }
}
