import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVideo } from 'app/shared/model/video.model';
import { VideoService } from './video.service';
import { IGenre } from 'app/shared/model/genre.model';
import { GenreService } from 'app/entities/genre';
import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';
import { RecommendedVideosService } from 'app/entities/recommended-videos';
import { ISearch } from 'app/shared/model/search.model';
import { SearchService } from 'app/entities/search';
import { ISearchResult } from 'app/shared/model/search-result.model';
import { SearchResultService } from 'app/entities/search-result';

@Component({
    selector: 'jhi-video-update',
    templateUrl: './video-update.component.html'
})
export class VideoUpdateComponent implements OnInit {
    video: IVideo;
    isSaving: boolean;

    genres: IGenre[];

    recommendedvideos: IRecommendedVideos[];

    searches: ISearch[];

    searchresults: ISearchResult[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private videoService: VideoService,
        private genreService: GenreService,
        private recommendedVideosService: RecommendedVideosService,
        private searchService: SearchService,
        private searchResultService: SearchResultService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ video }) => {
            this.video = video;
        });
        this.genreService.query().subscribe(
            (res: HttpResponse<IGenre[]>) => {
                this.genres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.recommendedVideosService.query().subscribe(
            (res: HttpResponse<IRecommendedVideos[]>) => {
                this.recommendedvideos = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.searchService.query().subscribe(
            (res: HttpResponse<ISearch[]>) => {
                this.searches = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.searchResultService.query().subscribe(
            (res: HttpResponse<ISearchResult[]>) => {
                this.searchresults = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.video.id !== undefined) {
            this.subscribeToSaveResponse(this.videoService.update(this.video));
        } else {
            this.subscribeToSaveResponse(this.videoService.create(this.video));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVideo>>) {
        result.subscribe((res: HttpResponse<IVideo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackGenreById(index: number, item: IGenre) {
        return item.id;
    }

    trackRecommendedVideosById(index: number, item: IRecommendedVideos) {
        return item.id;
    }

    trackSearchById(index: number, item: ISearch) {
        return item.id;
    }

    trackSearchResultById(index: number, item: ISearchResult) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
