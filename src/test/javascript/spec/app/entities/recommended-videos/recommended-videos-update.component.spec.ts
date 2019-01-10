/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ZipVidzTestModule } from '../../../test.module';
import { RecommendedVideosUpdateComponent } from 'app/entities/recommended-videos/recommended-videos-update.component';
import { RecommendedVideosService } from 'app/entities/recommended-videos/recommended-videos.service';
import { RecommendedVideos } from 'app/shared/model/recommended-videos.model';

describe('Component Tests', () => {
    describe('RecommendedVideos Management Update Component', () => {
        let comp: RecommendedVideosUpdateComponent;
        let fixture: ComponentFixture<RecommendedVideosUpdateComponent>;
        let service: RecommendedVideosService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [RecommendedVideosUpdateComponent]
            })
                .overrideTemplate(RecommendedVideosUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RecommendedVideosUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecommendedVideosService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new RecommendedVideos('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.recommendedVideos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new RecommendedVideos();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.recommendedVideos = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
