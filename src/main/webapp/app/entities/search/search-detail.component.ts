import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISearch } from 'app/shared/model/search.model';

@Component({
    selector: 'jhi-search-detail',
    templateUrl: './search-detail.component.html'
})
export class SearchDetailComponent implements OnInit {
    search: ISearch;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ search }) => {
            this.search = search;
        });
    }

    previousState() {
        window.history.back();
    }
}
