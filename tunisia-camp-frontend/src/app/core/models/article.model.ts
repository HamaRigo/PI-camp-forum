import { Comment } from './comment.model';
import { Profile } from './profile.model';
import { React } from './react.model';
import { User } from './user.model';

export interface Article {
  id: number;
  title: string;
  content: string;
  dateTimeOfPost: string;
  imageUrl: string;
  ratingPoints: number;
  user: User;
  comments: Comment[];
  reacts: React[];

  slug?: string;
  description?: string;
  body?: string;
  createdAt?: string;
  updatedAt?: string;
  favorited?: boolean;
  favoritesCount?: number;
  author?: Profile;
}
