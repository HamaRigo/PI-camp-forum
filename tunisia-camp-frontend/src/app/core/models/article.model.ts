import { Comment } from './comment.model';
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
  likesCount: number;
  dislikesCount: number;
}
