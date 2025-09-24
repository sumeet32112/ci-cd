import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RecipesRoutingModule } from './recipes-routing.module';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { RecipeListComponent } from './components/recipe-list/recipe-list.component';
import { RecipeCardComponent } from './components/recipe-card/recipe-card.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    SearchBarComponent,
    RecipeListComponent,
    RecipeCardComponent
  ],
  imports: [
    CommonModule,
    RecipesRoutingModule,
    FormsModule
  ]
})
export class RecipesModule { }
