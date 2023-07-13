import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { ApiService } from './api.service';
import { Comment } from '../models';


@Injectable()
export class CommentsService {
  constructor (
    private apiService: ApiService
  ) {}

  add(comment: Comment): Observable<Comment> {
    return this.apiService.post('/comments', comment);
  }

  edit(comment: Comment): Observable<Comment> {
    return this.apiService.put('/comments', comment);
  }

  destroy(commentId: number) {
    return this.apiService.delete(`/comments/${commentId}`);
  }
}
