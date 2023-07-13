export interface ArticleListConfig {
  type: string;

  filters: {
    userId?: number,
    limit?: number,
    page?: number,
  };
}
