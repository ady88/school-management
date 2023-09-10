import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOther } from '../other.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-other-detail',
  templateUrl: './other-detail.component.html',
})
export class OtherDetailComponent implements OnInit {
  other: IOther | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ other }) => {
      this.other = other;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
