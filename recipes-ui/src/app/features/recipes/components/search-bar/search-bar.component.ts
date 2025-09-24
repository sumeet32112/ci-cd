import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.scss']
})
export class SearchBarComponent implements OnInit {

  query = '';
  @Output() search = new EventEmitter<string>();

  constructor() { }

  ngOnInit(): void {
  }

  onSearch() {
    const q = this.query?.trim();
    if (q && q.length >= 3) {
      this.search.emit(q);
    }
  }
}
