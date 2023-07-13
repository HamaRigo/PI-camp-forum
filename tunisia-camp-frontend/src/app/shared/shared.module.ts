import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { ArticleListComponent, ArticleMetaComponent, ArticlePreviewComponent } from './article-helpers';
import { FavoriteButtonComponent } from './buttons';
import { ListErrorsComponent } from './list-errors.component';
import { ShowAuthedDirective } from './show-authed.directive';
import { ReactButtonComponent } from './buttons/react-button.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule
  ],
  declarations: [
    ArticleListComponent,
    ArticleMetaComponent,
    ArticlePreviewComponent,
    FavoriteButtonComponent,
    ReactButtonComponent,
    ListErrorsComponent,
    ShowAuthedDirective
  ],
  exports: [
    ArticleListComponent,
    ArticleMetaComponent,
    ArticlePreviewComponent,
    CommonModule,
    FavoriteButtonComponent,
    ReactButtonComponent,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    ListErrorsComponent,
    RouterModule,
    ShowAuthedDirective
  ]
})
export class SharedModule {}
