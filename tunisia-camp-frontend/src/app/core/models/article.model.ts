import { Profile } from './profile.model';

export interface Article {
  slug?: string;
  id: string;
  title: string;
  description?: string;
  content: string;
  body?: string;
  createdAt?: string;
  dateTimeOfPost: string;
  imageUrl: string;
  updatedAt?: string;
  favorited?: boolean;
  favoritesCount?: number;
  ratingPoints: number;

  author?: Profile;
  user: Profile;
}
