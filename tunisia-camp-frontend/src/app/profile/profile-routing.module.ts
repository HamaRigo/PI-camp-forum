import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileArticlesComponent } from './profile-articles.component';
import { ProfileLikedComponent } from './profile-liked.component';
import { ProfileComponent } from './profile.component';


const routes: Routes = [
  {
    path: ':username',
    component: ProfileComponent,
    children: [
      {
        path: '',
        component: ProfileArticlesComponent
      },
      {
        path: 'liked',
        component: ProfileLikedComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule {}
