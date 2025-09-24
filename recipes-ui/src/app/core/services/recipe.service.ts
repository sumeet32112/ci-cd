import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { Recipe } from 'src/app/features/recipes/models/recipe.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
private apiUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {}

  // returns observable of { content: Recipe[], totalElements: number }
  searchRecipes(query: string, page = 0, size = 200): Observable<{ content: Recipe[]; totalElements: number }> {
    const params = new HttpParams()
      .set('query', query)
      .set('page', String(page))
      .set('size', String(size));
    return this.http.get<any>(`${this.apiUrl}/search`, { params }).pipe(
      map(res => {
        // adapt to either Page object or plain array
        if (res && Array.isArray(res)) {
          return { content: res as Recipe[], totalElements: res.length };
        } else if (res && res.content) {
          return { content: res.content as Recipe[], totalElements: res.totalElements ?? res.content.length };
        } else {
          return { content: [], totalElements: 0 };
        }
      })
    );
  }

  getById(id: number) {
    return this.http.get<Recipe>(`${this.apiUrl}/${id}`);
  }

  loadRecipes(): Observable<any> {
    return this.http.post(`${this.apiUrl}/load`, {});
  }
}