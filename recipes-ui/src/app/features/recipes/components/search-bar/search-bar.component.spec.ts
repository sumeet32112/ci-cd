import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchBarComponent } from './search-bar.component';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

describe('SearchBarComponent', () => {
  let comp: SearchBarComponent;
  let fixture: ComponentFixture<SearchBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({ declarations: [SearchBarComponent], imports: [FormsModule] }).compileComponents();
    fixture = TestBed.createComponent(SearchBarComponent);
    comp = fixture.componentInstance;
  });

  it('should create', () => {
    expect(comp).toBeTruthy();
  });

  it('should not emit search for <3 chars', () => {
    spyOn(comp.search, 'emit');
    comp.query = 'ab';
    fixture.detectChanges();
    fixture.debugElement.query(By.css('button')).nativeElement.click();
    expect(comp.search.emit).not.toHaveBeenCalled();
  });

  it('should emit search for >=3 chars', () => {
    spyOn(comp.search, 'emit');
    comp.query = 'piz';
    fixture.detectChanges();
    fixture.debugElement.query(By.css('button')).nativeElement.click();
    expect(comp.search.emit).toHaveBeenCalledWith('piz');
  });

});
