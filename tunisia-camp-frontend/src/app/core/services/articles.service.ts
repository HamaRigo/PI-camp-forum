import { Injectable } from '@angular/core';
import { HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Article, ArticleListConfig } from '../models';

@Injectable()
export class ArticlesService {
  constructor (
    private apiService: ApiService
  ) {}

  query(config: ArticleListConfig): Observable<{articles: Article[], articlesCount: number}> {
    // Convert any filters over to Angular's URLSearchParams
    const params = {};

    Object.keys(config.filters)
    .forEach((key) => {
      params[key] = config.filters[key];
    });
    return this.apiService
    .get('/articles', new HttpParams({ fromObject: params })
    );
  }

  get(id: number | string): Observable<Article> {
    return this.apiService.get('/articles/' + id);
  }

  save(article: Article): Observable<Article> {
    // If we're updating an existing article
    if (article.id) {
      return this.apiService.put('/articles', article);
    // Otherwise, create a new article
    } else {
      return this.apiService.post('/articles', article);
    }
  }

  rate(id: number): Observable<Article> {
    return this.apiService.put('/articles/' + id + '/rate',  1);
  }

  destroy(id: number) {
    return this.apiService.delete('/articles/' + id);
  }
}
