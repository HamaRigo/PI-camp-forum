import { Component, Input, OnInit } from '@angular/core';

import { Article, UserService } from '../../core';

@Component({
  selector: 'app-article-preview',
  templateUrl: './article-preview.component.html'
})
export class ArticlePreviewComponent {
  @Input() article: Article;

  onToggleFavorite(favorited: boolean) {
    if (favorited) {
      this.article.ratingPoints++;
    }
  }
}
