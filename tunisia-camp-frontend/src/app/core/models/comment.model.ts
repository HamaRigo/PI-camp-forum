import { Profile } from './profile.model';

export interface Comment {
  id: number;
  body?: string;
  content: string;
  createdAt?: string;
  dateTimeOfComment: string;
  author?: Profile;
  user: Profile;
}
