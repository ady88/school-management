import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStaffData } from '../staff-data.model';

@Component({
  selector: 'jhi-staff-data-detail',
  templateUrl: './staff-data-detail.component.html',
})
export class StaffDataDetailComponent implements OnInit {
  staffData: IStaffData | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ staffData }) => {
      this.staffData = staffData;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
