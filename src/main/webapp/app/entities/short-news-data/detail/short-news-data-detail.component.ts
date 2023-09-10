import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IShortNewsData } from '../short-news-data.model';

@Component({
  selector: 'jhi-short-news-data-detail',
  templateUrl: './short-news-data-detail.component.html',
})
export class ShortNewsDataDetailComponent implements OnInit {
  shortNewsData: IShortNewsData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ shortNewsData }) => {
      this.shortNewsData = shortNewsData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
