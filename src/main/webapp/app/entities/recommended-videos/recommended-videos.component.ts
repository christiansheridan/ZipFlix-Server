import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';
import { Principal } from 'app/core';
import { RecommendedVideosService } from './recommended-videos.service';

@Component({
    selector: 'jhi-recommended-videos',
    templateUrl: './recommended-videos.component.html'
})
export class RecommendedVideosComponent implements OnInit, OnDestroy {
    recommendedVideos: IRecommendedVideos[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private recommendedVideosService: RecommendedVideosService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.recommendedVideosService.query().subscribe(
            (res: HttpResponse<IRecommendedVideos[]>) => {
                this.recommendedVideos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRecommendedVideos();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRecommendedVideos) {
        return item.id;
    }

    registerChangeInRecommendedVideos() {
        this.eventSubscriber = this.eventManager.subscribe('recommendedVideosListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
