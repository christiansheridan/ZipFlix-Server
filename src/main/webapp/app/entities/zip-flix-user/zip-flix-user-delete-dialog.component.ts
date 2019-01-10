import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZipFlixUser } from 'app/shared/model/zip-flix-user.model';
import { ZipFlixUserService } from './zip-flix-user.service';

@Component({
    selector: 'jhi-zip-flix-user-delete-dialog',
    templateUrl: './zip-flix-user-delete-dialog.component.html'
})
export class ZipFlixUserDeleteDialogComponent {
    zipFlixUser: IZipFlixUser;

    constructor(
        private zipFlixUserService: ZipFlixUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.zipFlixUserService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'zipFlixUserListModification',
                content: 'Deleted an zipFlixUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-zip-flix-user-delete-popup',
    template: ''
})
export class ZipFlixUserDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zipFlixUser }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ZipFlixUserDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.zipFlixUser = zipFlixUser;
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
