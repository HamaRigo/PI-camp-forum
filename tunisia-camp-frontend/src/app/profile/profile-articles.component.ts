import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ArticleListConfig, Profile, User, UserService } from '../core';

@Component({
  selector: 'app-profile-articles',
  templateUrl: './profile-articles.component.html'
})
export class ProfileArticlesComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
  ) {}

  profile: Profile;
  articlesConfig: ArticleListConfig = {
    type: 'all',
    filters: {}
  };

  ngOnInit() {
    /*this.route.parent.data.subscribe(
      (data: {profile: User}) => {
        //console.log(data);
        //this.profile = data.profile;
        this.articlesConfig = {
          type: 'all',
          filters: {}
        }; // Only method I found to refresh article load on swap
        this.articlesConfig.filters.user = this.profile.username;
      }
    );*/

    return this.userService.currentUser.subscribe((
      (userData: User) => {
        this.profile = userData;
        this.articlesConfig = {
          type: 'all',
          filters: {}
        }; // Only method I found to refresh article load on swap
        //this.articlesConfig.filters.user = this.profile;
        this.articlesConfig.filters.userId = this.profile.id;
      }
    ));
  }
}
