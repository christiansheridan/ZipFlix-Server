import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISearchResult } from 'app/shared/model/search-result.model';
import { Principal } from 'app/core';
import { SearchResultService } from './search-result.service';

@Component({
    selector: 'jhi-search-result',
    templateUrl: './search-result.component.html'
})
export class SearchResultComponent implements OnInit, OnDestroy {
    searchResults: ISearchResult[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private searchResultService: SearchResultService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.searchResultService.query().subscribe(
            (res: HttpResponse<ISearchResult[]>) => {
                this.searchResults = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSearchResults();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISearchResult) {
        return item.id;
    }

    registerChangeInSearchResults() {
        this.eventSubscriber = this.eventManager.subscribe('searchResultListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
