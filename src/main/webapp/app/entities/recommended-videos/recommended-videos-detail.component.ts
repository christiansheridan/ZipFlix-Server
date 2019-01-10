import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';

@Component({
    selector: 'jhi-recommended-videos-detail',
    templateUrl: './recommended-videos-detail.component.html'
})
export class RecommendedVideosDetailComponent implements OnInit {
    recommendedVideos: IRecommendedVideos;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recommendedVideos }) => {
            this.recommendedVideos = recommendedVideos;
        });
    }

    previousState() {
        window.history.back();
    }
}
