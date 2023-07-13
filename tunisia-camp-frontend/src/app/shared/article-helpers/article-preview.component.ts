import { Component, Input } from '@angular/core';

import { Article } from '../../core';

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
