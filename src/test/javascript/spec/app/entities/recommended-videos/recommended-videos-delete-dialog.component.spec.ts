/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ZipVidzTestModule } from '../../../test.module';
import { RecommendedVideosDeleteDialogComponent } from 'app/entities/recommended-videos/recommended-videos-delete-dialog.component';
import { RecommendedVideosService } from 'app/entities/recommended-videos/recommended-videos.service';

describe('Component Tests', () => {
    describe('RecommendedVideos Management Delete Component', () => {
        let comp: RecommendedVideosDeleteDialogComponent;
        let fixture: ComponentFixture<RecommendedVideosDeleteDialogComponent>;
        let service: RecommendedVideosService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [RecommendedVideosDeleteDialogComponent]
            })
                .overrideTemplate(RecommendedVideosDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(RecommendedVideosDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RecommendedVideosService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
