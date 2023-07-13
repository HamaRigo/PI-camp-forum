import { Article } from './article.model';
import { Profile } from './profile.model';
import { User } from './user.model';

export interface Comment {
  id?: number;
  content: string;
  dateTimeOfComment?: Date;
  user: User;
  post: Article;

  body?: string;
  createdAt?: string;
  author?: Profile;
}
