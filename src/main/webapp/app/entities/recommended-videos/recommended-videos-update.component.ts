import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';
import { RecommendedVideosService } from './recommended-videos.service';

@Component({
    selector: 'jhi-recommended-videos-update',
    templateUrl: './recommended-videos-update.component.html'
})
export class RecommendedVideosUpdateComponent implements OnInit {
    recommendedVideos: IRecommendedVideos;
    isSaving: boolean;

    constructor(private recommendedVideosService: RecommendedVideosService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recommendedVideos }) => {
            this.recommendedVideos = recommendedVideos;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recommendedVideos.id !== undefined) {
            this.subscribeToSaveResponse(this.recommendedVideosService.update(this.recommendedVideos));
        } else {
            this.subscribeToSaveResponse(this.recommendedVideosService.create(this.recommendedVideos));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecommendedVideos>>) {
        result.subscribe((res: HttpResponse<IRecommendedVideos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
