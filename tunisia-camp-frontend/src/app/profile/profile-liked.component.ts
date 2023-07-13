import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ArticleListConfig, Profile, User, UserService } from '../core';

@Component({
  selector: 'app-profile-liked',
  templateUrl: './profile-liked.component.html'
})
export class ProfileLikedComponent implements OnInit {
  constructor(
    private userService: UserService,
  ) {}

  profile: Profile;
  LikedConfig: ArticleListConfig = {
    type: 'like',
    filters: {}
  };

  ngOnInit() {
    return this.userService.currentUser.subscribe((
      (userData: User) => {
        this.profile = userData;
        this.LikedConfig.filters.userId = this.profile.id;
      }
    ));
  }
}
