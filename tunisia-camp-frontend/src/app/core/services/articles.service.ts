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
    
    if(config.type == 'like') 
      config.filters.limit = 10; // most 10 liked

    Object.keys(config.filters)
    .forEach((key) => {
      params[key] = config.filters[key];
    });
    if(config.type != 'all') {
      params['type'] = config.type;
    }
    
    if(config.type == 'like') 
      return this.apiService.get('/articles/liked', new HttpParams({ fromObject: params }));
    else if(config.filters.userId != null)
      return this.apiService.get('/articles/paginatedByUser', new HttpParams({ fromObject: params }));
    return this.apiService.get('/articles/paginated', new HttpParams({ fromObject: params }));
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
