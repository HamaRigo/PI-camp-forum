import { Component, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import Swal from 'sweetalert2';

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
  commentControl = new FormControl('', Validators.required);
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
        this.canModify = (this.article.user.id === this.currentUser.id);
    }
    );
  }

  onToggleFavorite(favorited: boolean) {
    if (favorited) {
      this.article.ratingPoints++;
    }
  }

  deleteArticle() {
    Swal.fire({
      title: 'Are you sure ?',
      text: "You won't be able to revert this !",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.isDeleting = true;
    
        this.articlesService.destroy(this.article.id).subscribe(
        success => {
          this.router.navigateByUrl('/');
          this.isDeleting = false;
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Your article has been deleted',
            showConfirmButton: false,
            timer: 1500
          });
        },
        err => this.isDeleting = false,
      );
      }
    });
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
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Your comment has been saved',
            showConfirmButton: false,
            timer: 1500
          });
        },
        errors => {
          this.isSubmitting = false;
          this.commentFormErrors = errors;
        }
      );
  }

  onEditComment(comment) {
    this.commentsService.edit(comment)
      .subscribe(
        editedComment => {
          //Object.assign(comment, editedComment);
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Your comment has been updated',
            showConfirmButton: false,
            timer: 1500
          });
        }
      );
  }

  onDeleteComment(comment) {
    this.commentsService.destroy(comment.id)
      .subscribe(
        success => {
          this.comments = this.comments.filter((item) => item !== comment);
          this.article.comments = this.comments;
          Swal.fire({
            position: 'top-end',
            icon: 'success',
            title: 'Your comment has been deleted',
            showConfirmButton: false,
            timer: 1500
          });
        }
      );
  }
}
