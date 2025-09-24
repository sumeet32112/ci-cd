export interface Recipe {
  id: number;
  name: string;
  cuisine?: string;
  cookTimeMinutes?: number;
  prepTimeMinutes?: number;
  servings?: number;
  difficulty?: string;
  caloriesPerServing?: number;
  tags?: string[];
  image?: string;
  rating?: number;
  reviewCount?: number;
  mealType?: string[];
}
