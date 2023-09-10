import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGeneral } from '../general.model';

@Component({
  selector: 'jhi-general-detail',
  templateUrl: './general-detail.component.html',
})
export class GeneralDetailComponent implements OnInit {
  general: IGeneral | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ general }) => {
      this.general = general;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
