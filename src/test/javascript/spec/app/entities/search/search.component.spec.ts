/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ZipVidzTestModule } from '../../../test.module';
import { SearchComponent } from 'app/entities/search/search.component';
import { SearchService } from 'app/entities/search/search.service';
import { Search } from 'app/shared/model/search.model';

describe('Component Tests', () => {
    describe('Search Management Component', () => {
        let comp: SearchComponent;
        let fixture: ComponentFixture<SearchComponent>;
        let service: SearchService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [SearchComponent],
                providers: []
            })
                .overrideTemplate(SearchComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SearchComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Search('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.searches[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
