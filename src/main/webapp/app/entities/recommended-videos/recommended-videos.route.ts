import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RecommendedVideos } from 'app/shared/model/recommended-videos.model';
import { RecommendedVideosService } from './recommended-videos.service';
import { RecommendedVideosComponent } from './recommended-videos.component';
import { RecommendedVideosDetailComponent } from './recommended-videos-detail.component';
import { RecommendedVideosUpdateComponent } from './recommended-videos-update.component';
import { RecommendedVideosDeletePopupComponent } from './recommended-videos-delete-dialog.component';
import { IRecommendedVideos } from 'app/shared/model/recommended-videos.model';

@Injectable({ providedIn: 'root' })
export class RecommendedVideosResolve implements Resolve<IRecommendedVideos> {
    constructor(private service: RecommendedVideosService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RecommendedVideos> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RecommendedVideos>) => response.ok),
                map((recommendedVideos: HttpResponse<RecommendedVideos>) => recommendedVideos.body)
            );
        }
        return of(new RecommendedVideos());
    }
}

export const recommendedVideosRoute: Routes = [
    {
        path: 'recommended-videos',
        component: RecommendedVideosComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.recommendedVideos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recommended-videos/:id/view',
        component: RecommendedVideosDetailComponent,
        resolve: {
            recommendedVideos: RecommendedVideosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.recommendedVideos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recommended-videos/new',
        component: RecommendedVideosUpdateComponent,
        resolve: {
            recommendedVideos: RecommendedVideosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.recommendedVideos.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recommended-videos/:id/edit',
        component: RecommendedVideosUpdateComponent,
        resolve: {
            recommendedVideos: RecommendedVideosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.recommendedVideos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recommendedVideosPopupRoute: Routes = [
    {
        path: 'recommended-videos/:id/delete',
        component: RecommendedVideosDeletePopupComponent,
        resolve: {
            recommendedVideos: RecommendedVideosResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.recommendedVideos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
