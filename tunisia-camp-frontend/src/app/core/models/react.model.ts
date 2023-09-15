import { Article } from './article.model';
import { TypeReact } from './type-react.model';
import { User } from './user.model';

export interface React {
  id?: number;
  type: TypeReact;
  user: User;
  post?: Article;
}
