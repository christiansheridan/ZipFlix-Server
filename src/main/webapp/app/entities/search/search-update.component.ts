import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISearch } from 'app/shared/model/search.model';
import { SearchService } from './search.service';

@Component({
    selector: 'jhi-search-update',
    templateUrl: './search-update.component.html'
})
export class SearchUpdateComponent implements OnInit {
    search: ISearch;
    isSaving: boolean;

    constructor(private searchService: SearchService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ search }) => {
            this.search = search;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.search.id !== undefined) {
            this.subscribeToSaveResponse(this.searchService.update(this.search));
        } else {
            this.subscribeToSaveResponse(this.searchService.create(this.search));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISearch>>) {
        result.subscribe((res: HttpResponse<ISearch>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
