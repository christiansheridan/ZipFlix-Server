import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IZipFlixUser } from 'app/shared/model/zip-flix-user.model';
import { ZipFlixUserService } from './zip-flix-user.service';

@Component({
    selector: 'jhi-zip-flix-user-update',
    templateUrl: './zip-flix-user-update.component.html'
})
export class ZipFlixUserUpdateComponent implements OnInit {
    zipFlixUser: IZipFlixUser;
    isSaving: boolean;
    dateOfBirthDp: any;

    constructor(private zipFlixUserService: ZipFlixUserService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ zipFlixUser }) => {
            this.zipFlixUser = zipFlixUser;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.zipFlixUser.id !== undefined) {
            this.subscribeToSaveResponse(this.zipFlixUserService.update(this.zipFlixUser));
        } else {
            this.subscribeToSaveResponse(this.zipFlixUserService.create(this.zipFlixUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IZipFlixUser>>) {
        result.subscribe((res: HttpResponse<IZipFlixUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
