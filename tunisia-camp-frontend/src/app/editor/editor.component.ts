import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, UntypedFormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { Article, ArticlesService } from '../core';

@Component({
  selector: 'app-editor-page',
  templateUrl: './editor.component.html'
})
export class EditorComponent implements OnInit {
  article: Article = {} as Article;
  articleForm: UntypedFormGroup;
  tagField = new UntypedFormControl();
  errors: Object = {};
  isSubmitting = false;

  constructor(
    private articlesService: ArticlesService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: UntypedFormBuilder
  ) {
    // use the FormBuilder to create a form group
    this.articleForm = this.fb.group({
      title: '',
      imageUrl: '',
      content: ''
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

    // update the model
    this.updateArticle(this.articleForm.value);
    console.log(this.article);
    // console.log(this.articleForm.value);

    // post the changes
    this.articlesService.save(this.article).subscribe(
      article => this.router.navigateByUrl('/'),
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
