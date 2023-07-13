import { Profile } from './profile.model';
import { User } from './user.model';

export interface React {
  id: number;
  type: string;
  user: User;
}
