import { Article } from "./article.model";

export interface User {
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
}
