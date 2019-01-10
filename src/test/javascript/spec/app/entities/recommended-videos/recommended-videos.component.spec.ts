/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ZipVidzTestModule } from '../../../test.module';
import { RecommendedVideosComponent } from 'app/entities/recommended-videos/recommended-videos.component';
import { RecommendedVideosService } from 'app/entities/recommended-videos/recommended-videos.service';
import { RecommendedVideos } from 'app/shared/model/recommended-videos.model';

describe('Component Tests', () => {
    describe('RecommendedVideos Management Component', () => {
        let comp: RecommendedVideosComponent;
        let fixture: ComponentFixture<RecommendedVideosComponent>;
        let service: RecommendedVideosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [RecommendedVideosComponent],
                providers: []
            })
                .overrideTemplate(RecommendedVideosComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecommendedVideosComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecommendedVideosService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new RecommendedVideos('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.recommendedVideos[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
