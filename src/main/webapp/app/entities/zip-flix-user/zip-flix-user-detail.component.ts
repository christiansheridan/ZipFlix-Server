import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZipFlixUser } from 'app/shared/model/zip-flix-user.model';

@Component({
    selector: 'jhi-zip-flix-user-detail',
    templateUrl: './zip-flix-user-detail.component.html'
})
export class ZipFlixUserDetailComponent implements OnInit {
    zipFlixUser: IZipFlixUser;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ zipFlixUser }) => {
            this.zipFlixUser = zipFlixUser;
        });
    }

    previousState() {
        window.history.back();
    }
}
