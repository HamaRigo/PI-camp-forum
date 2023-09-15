import { Article } from './article.model';
import { User } from './user.model';

export interface Comment {
  id?: number;
  content: string;
  dateTimeOfComment?: string;
  user: User;
  post?: Article;
}
