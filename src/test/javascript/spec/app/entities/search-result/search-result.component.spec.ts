/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ZipVidzTestModule } from '../../../test.module';
import { SearchResultComponent } from 'app/entities/search-result/search-result.component';
import { SearchResultService } from 'app/entities/search-result/search-result.service';
import { SearchResult } from 'app/shared/model/search-result.model';

describe('Component Tests', () => {
    describe('SearchResult Management Component', () => {
        let comp: SearchResultComponent;
        let fixture: ComponentFixture<SearchResultComponent>;
        let service: SearchResultService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [SearchResultComponent],
                providers: []
            })
                .overrideTemplate(SearchResultComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SearchResultComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchResultService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SearchResult('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.searchResults[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
