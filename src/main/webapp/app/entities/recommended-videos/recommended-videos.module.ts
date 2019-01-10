import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZipVidzSharedModule } from 'app/shared';
import {
    RecommendedVideosComponent,
    RecommendedVideosDetailComponent,
    RecommendedVideosUpdateComponent,
    RecommendedVideosDeletePopupComponent,
    RecommendedVideosDeleteDialogComponent,
    recommendedVideosRoute,
    recommendedVideosPopupRoute
} from './';

const ENTITY_STATES = [...recommendedVideosRoute, ...recommendedVideosPopupRoute];

@NgModule({
    imports: [ZipVidzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecommendedVideosComponent,
        RecommendedVideosDetailComponent,
        RecommendedVideosUpdateComponent,
        RecommendedVideosDeleteDialogComponent,
        RecommendedVideosDeletePopupComponent
    ],
    entryComponents: [
        RecommendedVideosComponent,
        RecommendedVideosUpdateComponent,
        RecommendedVideosDeleteDialogComponent,
        RecommendedVideosDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZipVidzRecommendedVideosModule {}
