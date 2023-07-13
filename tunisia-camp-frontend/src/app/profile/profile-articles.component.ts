import { Component, OnInit } from '@angular/core';

import { ArticleListConfig, User, UserService } from '../core';

@Component({
  selector: 'app-profile-articles',
  templateUrl: './profile-articles.component.html'
})
export class ProfileArticlesComponent implements OnInit {
  constructor(
    private userService: UserService,
  ) {}

  profile: User;
  articlesConfig: ArticleListConfig = {
    type: 'all',
    filters: {}
  };

  ngOnInit() {
    return this.userService.currentUser.subscribe((
      (userData: User) => {
        this.profile = userData;
        this.articlesConfig = {
          type: 'all',
          filters: {}
        };
        this.articlesConfig.filters.userId = this.profile.id;
      }
    ));
  }
}
