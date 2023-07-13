import { Component } from '@angular/core';

import { ArticleListConfig } from '../core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent  {
  listConfig: ArticleListConfig = {
    type: 'all',
    filters: {}
  };
}
