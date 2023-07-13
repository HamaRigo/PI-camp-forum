import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ProfileArticlesComponent } from './profile-articles.component';
import { ProfileComponent } from './profile.component';
import { ProfileResolver } from './profile-resolver.service';
import { SharedModule } from '../shared';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileLikedComponent } from './profile-liked.component';

@NgModule({
  imports: [
    SharedModule,
    ProfileRoutingModule
  ],
  declarations: [
    ProfileArticlesComponent,
    ProfileComponent,
    ProfileLikedComponent
  ],
  providers: [
    ProfileResolver
  ]
})
export class ProfileModule {}
