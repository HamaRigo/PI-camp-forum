import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Article, ArticlesService, UserService } from '../core';

import Swal from 'sweetalert2';

@Component({
  selector: 'app-editor-page',
  templateUrl: './editor.component.html'
})
export class EditorComponent implements OnInit {
  article: Article = {} as Article;
  errors: Object = {};
  isSubmitting = false;
  articleForm: FormGroup;
   
  constructor(
    private articlesService: ArticlesService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder
  ) {
    // use the FormBuilder to create a form group
    const urlRegex = /^(?:http(s)?:\/\/)?[\w.-]+(?:\.[\w\.-]+)+[\w\-\._~:/?#[\]@!\$&'\(\)\*\+,;=.]+$/;

    this.articleForm = this.fb.group({      
      title: ['', Validators.required ],
      imageUrl: ['', [Validators.required, Validators.pattern(urlRegex)]],
      content: ['', Validators.required ]
    });
  }

  ngOnInit() {
    // If there's an article prefetched, load it
    this.route.data.subscribe((data: { article: Article }) => {
      if (data.article) {
        this.article = data.article;
        this.articleForm.patchValue(data.article);
      }
    });
  }
  submitForm() {
    this.isSubmitting = true;
    let action = 'updated';

    // update the model
    this.updateArticle(this.articleForm.value);

    // new post => assign current user to post
    if(this.article.user == null) {
      action = 'added';
      this.userService.currentUser.subscribe(
        (userData) => {
          this.article.user = userData;
        }
      );
    }
    // post the changes
    this.articlesService.save(this.article).subscribe(
      article => {
        this.router.navigateByUrl('/');
        Swal.fire({
          position: 'top-end',
          icon: 'success',
          title: `Your article has been ${action}`,
          showConfirmButton: false,
          timer: 1500
        });
      },
      err => {
        this.errors = err;
        this.isSubmitting = false;
      }
    );
  }

  updateArticle(values: Object) {
    Object.assign(this.article, values);
  }
}
