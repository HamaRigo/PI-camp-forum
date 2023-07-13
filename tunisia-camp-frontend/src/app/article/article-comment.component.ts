import { Component, EventEmitter, Input, Output, OnInit } from '@angular/core';

import { Article, Comment, UserService } from '../core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';

@Component({
  selector: 'app-article-comment',
  templateUrl: './article-comment.component.html'
})
export class ArticleCommentComponent implements OnInit {
  constructor(
    private userService: UserService,
    private fb: UntypedFormBuilder
  ) {
    this.editCommentForm = this.fb.group({
      id: null,
      content: null,  
      user: null,
      post: null,
    });
  }

  @Input() comment: Comment;
  @Input() post: Article;
  @Output() editComment = new EventEmitter<boolean>();
  @Output() deleteComment = new EventEmitter<boolean>();

  editCommentForm: UntypedFormGroup;
  canModify: boolean;
  isEditing: boolean;
  isSubmitting: boolean;

  ngOnInit() {
    // Load the current user's data
    this.userService.currentUser.subscribe(
      (userData) => {
        this.canModify = (userData.id === this.comment.user.id);
      }
    );
  }

  deleteClicked() {
    this.deleteComment.emit(true);
  }
  
  editClicked(comment: Comment) {
    this.editCommentForm.patchValue(comment);
    this.isEditing = true;
  }

  editHandled() {
    this.isSubmitting = true;
    Object.assign(this.comment, this.editCommentForm.value);
    const clonedPost = { ...this.post };
    delete clonedPost.comments;
    this.comment.post = clonedPost;
    this.editComment.emit(true);
    this.isSubmitting = false;
    this.isEditing = false;
  }
}
