import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISearch } from 'app/shared/model/search.model';
import { Principal } from 'app/core';
import { SearchService } from './search.service';

@Component({
    selector: 'jhi-search',
    templateUrl: './search.component.html'
})
export class SearchComponent implements OnInit, OnDestroy {
    searches: ISearch[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private searchService: SearchService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.searchService.query().subscribe(
            (res: HttpResponse<ISearch[]>) => {
                this.searches = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSearches();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISearch) {
        return item.id;
    }

    registerChangeInSearches() {
        this.eventSubscriber = this.eventManager.subscribe('searchListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
