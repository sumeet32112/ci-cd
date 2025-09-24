import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RecipeService } from './recipe.service';

describe('RecipeService', () => {
  let service: RecipeService;
  let httpMock: HttpTestingController;

beforeEach(() => {
    TestBed.configureTestingModule({ imports: [HttpClientTestingModule], providers: [RecipeService] });
    service = TestBed.inject(RecipeService);
    httpMock = TestBed.inject(HttpTestingController);
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should call search endpoint with query', () => {
    service.searchRecipes('pizza').subscribe(res => {
      expect(res.content.length).toBe(1);
    });
    const req = httpMock.expectOne(req => req.url.includes('/search') && req.params.get('query') === 'pizza');
    expect(req.request.method).toBe('GET');
    req.flush({ content: [{ id: 1, name: 'Pizza' }], totalElements: 1 });
  });

  afterEach(() => httpMock.verify());
  
});
