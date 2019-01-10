import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IZipFlixUser } from 'app/shared/model/zip-flix-user.model';
import { Principal } from 'app/core';
import { ZipFlixUserService } from './zip-flix-user.service';

@Component({
    selector: 'jhi-zip-flix-user',
    templateUrl: './zip-flix-user.component.html'
})
export class ZipFlixUserComponent implements OnInit, OnDestroy {
    zipFlixUsers: IZipFlixUser[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private zipFlixUserService: ZipFlixUserService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.zipFlixUserService.query().subscribe(
            (res: HttpResponse<IZipFlixUser[]>) => {
                this.zipFlixUsers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInZipFlixUsers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IZipFlixUser) {
        return item.id;
    }

    registerChangeInZipFlixUsers() {
        this.eventSubscriber = this.eventManager.subscribe('zipFlixUserListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
