export interface ArticleListConfig {
  type: string;

  filters: {
    userId?: number,
    favorited?: string,
    limit?: number,
    page?: number,
  };
}
