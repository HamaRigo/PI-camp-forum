import { Component, OnInit } from '@angular/core';

import { User, UserService } from '../core';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  constructor(
    private userService: UserService
  ) { }

  profile: User;
  currentUser: User;
  isUser: boolean;

  ngOnInit() {
    return this.userService.currentUser.subscribe((
      (userData: User) => {
        this.currentUser = userData;
        this.profile = userData;
        this.isUser = (this.currentUser.username === this.profile.username);
      }
    ));
  }
}
