/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZipVidzTestModule } from '../../../test.module';
import { SearchDetailComponent } from 'app/entities/search/search-detail.component';
import { Search } from 'app/shared/model/search.model';

describe('Component Tests', () => {
    describe('Search Management Detail Component', () => {
        let comp: SearchDetailComponent;
        let fixture: ComponentFixture<SearchDetailComponent>;
        const route = ({ data: of({ search: new Search('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [SearchDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SearchDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SearchDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.search).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
