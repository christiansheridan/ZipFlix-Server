import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';
import { RecommendedVideosService } from './recommended-videos.service';

@Component({
    selector: 'jhi-recommended-videos-delete-dialog',
    templateUrl: './recommended-videos-delete-dialog.component.html'
})
export class RecommendedVideosDeleteDialogComponent {
    recommendedVideos: IRecommendedVideos;

    constructor(
        private recommendedVideosService: RecommendedVideosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.recommendedVideosService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'recommendedVideosListModification',
                content: 'Deleted an recommendedVideos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recommended-videos-delete-popup',
    template: ''
})
export class RecommendedVideosDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recommendedVideos }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RecommendedVideosDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.recommendedVideos = recommendedVideos;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
