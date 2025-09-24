import { Component, OnInit } from '@angular/core';
import { RecipeService } from 'src/app/core/services/recipe.service';
import { Recipe } from '../../models/recipe.model';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.scss']
})
export class RecipeListComponent implements OnInit {
  allRecipes: Recipe[] = [];
  displayed: Recipe[] = [];
  availableTags: string[] = [];
  selectedTags = new Set<string>();
  sortOrder: '' | 'asc' | 'desc' = '';
  loading = false;
  totalResults: number | null = null;

  // pagination (client-side)
  pageIndex = 0;
  pageSize = 9;
  loadingLoad = false;
  loadMessage: string | null = null;

  constructor(private recipeService: RecipeService) {}

  ngOnInit() {
    // optionally fetch initial dataset
  }

  onSearch(query: string) {
    this.loading = true;
    this.recipeService.searchRecipes(query, 0, 200).subscribe({
      next: res => {
        this.allRecipes = res.content || [];
        this.totalResults = res.totalElements ?? this.allRecipes.length;
        this.buildTags();
        this.applySortAndFilter();
      },
      error: err => {
        console.error('Search failed', err);
        this.allRecipes = [];
        this.displayed = [];
        this.totalResults = 0;
      },
      complete: () => this.loading = false
    });
  }

  buildTags() {
    const set = new Set<string>();
    this.allRecipes.forEach(r => (r.tags || []).forEach(t => set.add(t)));
    this.availableTags = Array.from(set).sort();
  }

  toggleTag(tag: string) {
    if (this.selectedTags.has(tag)) this.selectedTags.delete(tag);
    else this.selectedTags.add(tag);
    this.applySortAndFilter();
  }

  clearTags() {
    this.selectedTags.clear();
    this.applySortAndFilter();
  }

  applySortAndFilter() {
    let arr = [...this.allRecipes];
    // filter
    if (this.selectedTags.size > 0) {
      arr = arr.filter(r => (r.tags || []).some(t => this.selectedTags.has(t)));
    }
    // sort
    if (this.sortOrder === 'asc') {
      arr.sort((a, b) => (a.cookTimeMinutes || 0) - (b.cookTimeMinutes || 0));
    } else if (this.sortOrder === 'desc') {
      arr.sort((a, b) => (b.cookTimeMinutes || 0) - (a.cookTimeMinutes || 0));
    }
    this.displayed = arr;
    this.pageIndex = 0;
  }

  setSort(order: '' | 'asc' | 'desc') {
    this.sortOrder = order;
    this.applySortAndFilter();
  }

  // pagination helpers
  get totalPages() {
    return Math.max(1, Math.ceil(this.displayed.length / this.pageSize));
  }
  prevPage() { if (this.pageIndex > 0) this.pageIndex--; }
  nextPage() { if (this.pageIndex + 1 < this.totalPages) this.pageIndex++; }

  // slice for template
  get pageItems() {
    const start = this.pageIndex * this.pageSize;
    return this.displayed.slice(start, start + this.pageSize);
  }

  loadRecipes() {
    this.loadingLoad = true;
    this.loadMessage = null;

    this.recipeService.loadRecipes().subscribe({
      next: (res) => {
        this.loadingLoad = false;
        this.loadMessage = `${res.message} (${res.loadedCount} recipes)`;
      },
      error: (err) => {
        this.loadingLoad = false;
        this.loadMessage = 'Failed to load recipes: ' + (err.error?.message || 'Server error');
      }
    });
  }
}