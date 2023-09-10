import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { GeneralService } from '../service/general.service';

import { GeneralComponent } from './general.component';

describe('General Management Component', () => {
  let comp: GeneralComponent;
  let fixture: ComponentFixture<GeneralComponent>;
  let service: GeneralService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'general', component: GeneralComponent }]), HttpClientTestingModule],
      declarations: [GeneralComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(GeneralComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(GeneralComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(GeneralService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.generals?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to generalService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getGeneralIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getGeneralIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
