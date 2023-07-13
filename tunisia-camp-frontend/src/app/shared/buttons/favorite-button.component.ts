import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';

import { Article, ArticlesService, UserService } from '../../core';

@Component({
  selector: 'app-favorite-button',
  templateUrl: './favorite-button.component.html'
})
export class FavoriteButtonComponent implements OnInit {
  constructor(
    private articlesService: ArticlesService,
    private router: Router,
    private userService: UserService
  ) {}

  @Input() article: Article;
  @Output() toggle = new EventEmitter<boolean>();
  isSubmitting = false;
  canModify: boolean;

  ngOnInit() {
    this.userService.currentUser.subscribe(
      (userData) => {
        this.canModify = (this.article.user.id === userData.id);
      }
    );
  }

  toggleFavorite() {
    this.isSubmitting = true;

    return this.articlesService.rate(this.article.id).subscribe(
      data => {
        this.isSubmitting = false;
        this.toggle.emit(true);
      },
      err => this.isSubmitting = false
    );
  }
}
