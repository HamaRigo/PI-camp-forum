import { Article } from "./article.model";

export interface Profile {
  id: number;
  username: string;
  connected: boolean;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phoneNumber: number;
  image: string;
  role: string;
  posts: Article[];

  bio?: string;
  following?: boolean;
}
