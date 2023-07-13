import { Component, OnInit } from '@angular/core';
import { UntypedFormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import {
  Article,
  Comment,
  User,
  ArticlesService,
  CommentsService,
  UserService
} from '../core';

@Component({
  selector: 'app-article-page',
  templateUrl: './article.component.html'
})
export class ArticleComponent implements OnInit {
  article: Article;
  currentUser: User;
  canModify: boolean;
  comments: Comment[];
  commentControl = new UntypedFormControl();
  commentFormErrors = {};
  isSubmitting = false;
  isDeleting = false;

  constructor(
    private route: ActivatedRoute,
    private articlesService: ArticlesService,
    private commentsService: CommentsService,
    private router: Router,
    private userService: UserService,
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('slug');
    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );
    this.articlesService.get(id).subscribe(
      (data) => {
        this.article = data;
        this.comments = data.comments;
        if(this.article.user.id == this.currentUser.id) {
          this.canModify = true;
        }
    }
    );
  }

  onToggleFavorite(favorited: boolean) {
    if (favorited) {
      this.article.ratingPoints++;
    }
  }

  deleteArticle() {
    this.isDeleting = true;

    this.articlesService.destroy(this.article.id)
      .subscribe(
        success => {
          this.router.navigateByUrl('/');
        }
      );
  }

  addComment() {
    this.isSubmitting = true;
    this.commentFormErrors = {};

    const newComment : Comment = {
      content: this.commentControl.value,
      user: this.currentUser,
      post: this.article,
    };

    this.commentsService.add(newComment)
    .subscribe(
        comment => {
          this.comments.unshift(comment);
          this.commentControl.reset('');
          this.isSubmitting = false;
        },
        errors => {
          this.isSubmitting = false;
          this.commentFormErrors = errors;
        }
      );
  }

  onDeleteComment(comment) {
    this.commentsService.destroy(comment.id)
      .subscribe(
        success => {
          this.comments = this.comments.filter((item) => item !== comment);
        }
      );
  }

  onEditComment(comment) {
    this.commentsService.edit(comment)
      .subscribe(
        editedComment => {
          Object.assign(comment, editedComment);
        }
      );
  }
}
