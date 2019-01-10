/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ZipVidzTestModule } from '../../../test.module';
import { RecommendedVideosDetailComponent } from 'app/entities/recommended-videos/recommended-videos-detail.component';
import { RecommendedVideos } from 'app/shared/model/recommended-videos.model';

describe('Component Tests', () => {
    describe('RecommendedVideos Management Detail Component', () => {
        let comp: RecommendedVideosDetailComponent;
        let fixture: ComponentFixture<RecommendedVideosDetailComponent>;
        const route = ({ data: of({ recommendedVideos: new RecommendedVideos('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [RecommendedVideosDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(RecommendedVideosDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecommendedVideosDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.recommendedVideos).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
