import { Component, Input, OnInit } from '@angular/core';

import { Article, ReactsService, User, UserService } from '../../core';
import { TypeReact } from '../../core/models/type-react.model';
import { React } from '../../core/models/react.model';

@Component({
  selector: 'app-react-button',
  templateUrl: './react-button.component.html'
})
export class ReactButtonComponent implements OnInit {
  constructor(
    private reactsService: ReactsService,
    private userService: UserService
  ) {}

  @Input() article: Article;
  
  canReact: boolean;
  userReact: React;
  articleLiked: boolean;
  articleDisliked: boolean;
  isSubmitting = false;
  currentUser: User;

  ngOnInit() {
    this.userService.currentUser.subscribe(
      (userData) => {
        this.currentUser = userData;
        this.canReact = (this.article.user.id != userData.id);
        this.userReact = this.article.reacts.find(react => react.user.id === userData.id);
        if(this.userReact) {
          this.articleLiked = this.userReact.type === TypeReact.LIKE;
          this.articleDisliked = this.userReact.type === TypeReact.DISLIKE;
        }
        else {
          this.articleLiked = false;
          this.articleDisliked = false;
        }
      }
    );
  }

  onToggleReact(like: boolean) {
    this.isSubmitting = true;

    if(!this.articleLiked && !this.articleDisliked) { //new react
      const newReact = {
        type: like ? TypeReact.LIKE : TypeReact.DISLIKE,
        user: this.currentUser,
        post: this.article,
      };
      this.reactsService.add(newReact).subscribe(
        data => {
          this.isSubmitting = false;
          this.userReact = data;
          /*if(this.userReact == undefined) 
            this.userReact = data;
          else 
            Object.assign(this.userReact, data);*/

          this.article.reacts.push(data);
          this.updateLikeDislikeCounts(data.type, null, null);
        },
        err => this.isSubmitting = false
      );
    }
    else if ((this.articleDisliked && !like) || (this.articleLiked && like)) { //delete react
      this.reactsService.destroy(this.userReact.id).subscribe(
        data => {
          this.isSubmitting = false;
          this.userReact = undefined;
          this.article.reacts = this.article.reacts.filter((item) => item !== this.userReact);
          this.updateLikeDislikeCounts(null, like, null); 
        },
        err => this.isSubmitting = false
      );
    }
    else { //update react
      this.userReact.type = like ? TypeReact.LIKE : TypeReact.DISLIKE;
      const reactPost = { ...this.article };
      delete reactPost.reacts;
      this.userReact.post = reactPost;
      this.reactsService.edit(this.userReact).subscribe(
        data => {
          this.isSubmitting = false;
          Object.assign(this.userReact, data);
          this.updateLikeDislikeCounts(data.type, null, true);
        },
        err => this.isSubmitting = false
      );
    }
  }

  private updateLikeDislikeCounts(type: string, like: boolean, update: boolean) {
    if (type === TypeReact.LIKE) { //add like 
      this.articleLiked = true;
      this.articleDisliked = false;
      this.article.likesCount++;
      if(update) {
        this.article.dislikesCount = Math.max(0, this.article.dislikesCount - 1); //for update
      }
    } else if (type === TypeReact.DISLIKE) { //add dislike 
      this.articleLiked = false;
      this.articleDisliked = true;
      this.article.dislikesCount++;
      if(update) {
        this.article.likesCount = Math.max(0, this.article.likesCount - 1); //for update
      }
    } else { //delete react
      this.articleLiked = false;
      this.articleDisliked = false;
      if(like)
        this.article.likesCount = Math.max(0, this.article.likesCount - 1); //delete like
      else 
        this.article.dislikesCount = Math.max(0, this.article.dislikesCount - 1); //delete dislike
    }
  } 
}
