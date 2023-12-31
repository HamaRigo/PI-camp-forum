import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

import { Article, ArticlesService } from '../core';
import { catchError ,  map } from 'rxjs/operators';

@Injectable()
export class EditableArticleResolver implements Resolve<Article> {
  constructor(
    private articlesService: ArticlesService,
    private router: Router,
  ) { }

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<any> {
    return this.articlesService.get(route.params['slug'])
      .pipe(
        map(
          article => {
            return article;
          }
        ),
        catchError((err) => this.router.navigateByUrl('/'))
      );
  }
}
