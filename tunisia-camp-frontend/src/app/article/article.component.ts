import { Component, OnInit } from '@angular/core';
import { UntypedFormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import {
  Article,
  ArticlesService,
  Comment,
  CommentsService,
  User,
  UserService
} from '../core';
import {logging} from 'protractor';

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
    // console.log(this.route.snapshot.paramMap.get('slug'));
    // Retreive the prefetched article
    // this.route.data.subscribe(
    //   (data: { article: Article }) => {
    //     console.log(data);
    //     this.article = data.article;
    //
    //     // Load the comments on this article
    //     this.populateComments();
    //   }
    // );
    const id = this.route.snapshot.paramMap.get('slug');
    this.articlesService.get(id).subscribe(
      (data) => {
        this.article = data ;
        this.populateComments();
        if(this.article.user.id === 1) {
          this.canModify = true;
        }
    }
    );
    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
      }
    );
  }

  onToggleFavorite(favorited: boolean) {
    this.article.favorited = favorited;

    if (favorited) {
      this.article.favoritesCount++;
    } else {
      this.article.favoritesCount--;
    }
  }

  onToggleFollowing(following: boolean) {
    this.article.author.following = following;
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

  populateComments() {
    this.commentsService.getAll(this.article.id)
      .subscribe(comments => this.comments = comments);
  }

  addComment() {
    this.isSubmitting = true;
    this.commentFormErrors = {};

    const commentBody = this.commentControl.value;
    this.commentsService
      .add(this.article.id, commentBody)
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
    this.commentsService.destroy(comment.id, this.article.id)
      .subscribe(
        success => {
          this.comments = this.comments.filter((item) => item !== comment);
        }
      );
  }

}
